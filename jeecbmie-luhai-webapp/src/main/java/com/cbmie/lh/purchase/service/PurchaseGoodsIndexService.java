package com.cbmie.lh.purchase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.purchase.dao.PurchaseGoodsIndexDao;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;
import com.cbmie.lh.purchase.entity.PurchaseGoodsIndex;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 采购合同-进口-子表信息service
 */
@Service
@Transactional
public class PurchaseGoodsIndexService extends BaseService<PurchaseGoodsIndex, Long> {

	@Autowired
	private PurchaseGoodsIndexDao purchaseGoodsIndexDao;

	@Override
	public HibernateDao<PurchaseGoodsIndex, Long> getEntityDao() {
		return purchaseGoodsIndexDao;
	}

	/**
	 * 
	 * @param purchaseContractGoods
	 * @param goodsIndexJson
	 */
	public void save(PurchaseContractGoods purchaseContractGoods, String goodsIndexJson) {
		// 转成标准的json字符串
		goodsIndexJson = StringEscapeUtils.unescapeHtml4(goodsIndexJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<PurchaseGoodsIndex> purchaseGoodsIndexList = new ArrayList<PurchaseGoodsIndex>();
		try {
			JsonNode jsonNode = objectMapper.readTree(goodsIndexJson);
			for (JsonNode jn : jsonNode) {
				PurchaseGoodsIndex purchaseGoodsIndex = objectMapper.readValue(jn.toString(), PurchaseGoodsIndex.class);
				purchaseGoodsIndexList.add(purchaseGoodsIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取purchaseContractGoods中dataPurchaseGoodsIndexList持久化对象
		List<PurchaseGoodsIndex> dataPurchaseGoodsIndexList = purchaseContractGoods.getPurchaseGoodsIndexList();
		// 将数据库数据放入映射
		Map<Long, PurchaseGoodsIndex> dataPurchaseGoodsIndexMap = new HashMap<Long, PurchaseGoodsIndex>();
		for (PurchaseGoodsIndex dataPurchaseGoodsIndex : dataPurchaseGoodsIndexList) {
			dataPurchaseGoodsIndexMap.put(dataPurchaseGoodsIndex.getId(), dataPurchaseGoodsIndex);
		}
		// 排除没有发生改变的
		for (PurchaseGoodsIndex dataPurchaseGoodsIndex : dataPurchaseGoodsIndexList) {
			if (purchaseGoodsIndexList.contains(dataPurchaseGoodsIndex)) {
				purchaseGoodsIndexList.remove(dataPurchaseGoodsIndex);
				dataPurchaseGoodsIndexMap.remove(dataPurchaseGoodsIndex.getId()); // 从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (PurchaseGoodsIndex purchaseGoodsIndex : purchaseGoodsIndexList) {
			if (purchaseGoodsIndex.getId() == null) {
				insert(purchaseContractGoods.getId(), purchaseGoodsIndex, dataPurchaseGoodsIndexList); // 新增
			}
			PurchaseGoodsIndex dataPurchaseGoodsIndex = dataPurchaseGoodsIndexMap.get(purchaseGoodsIndex.getId());
			if (dataPurchaseGoodsIndex != null) {
				update(dataPurchaseGoodsIndex, purchaseGoodsIndex); // 修改
				dataPurchaseGoodsIndexMap.remove(purchaseGoodsIndex.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, PurchaseGoodsIndex> entry : dataPurchaseGoodsIndexMap.entrySet()) {
			dataPurchaseGoodsIndexList.remove(entry.getValue());
			purchaseGoodsIndexDao.delete(entry.getKey());
		}
	}

	private void update(PurchaseGoodsIndex dataPurchaseGoodsIndex, PurchaseGoodsIndex purchaseGoodsIndex) {
		User currentUser = UserUtil.getCurrentUser();
		dataPurchaseGoodsIndex.setIndexName(purchaseGoodsIndex.getIndexName());
		dataPurchaseGoodsIndex.setIndexNameE(purchaseGoodsIndex.getIndexNameE());
		dataPurchaseGoodsIndex.setUnit(purchaseGoodsIndex.getUnit());
		dataPurchaseGoodsIndex.setConventions(purchaseGoodsIndex.getConventions());
		dataPurchaseGoodsIndex.setTerms(purchaseGoodsIndex.getTerms());
		dataPurchaseGoodsIndex.setRejectionValue(purchaseGoodsIndex.getRejectionValue());
		purchaseGoodsIndex.setUpdaterDept(currentUser.getOrganization().getOrgName());
		dataPurchaseGoodsIndex.setUpdaterNo(currentUser.getLoginName());
		dataPurchaseGoodsIndex.setUpdaterName(currentUser.getName());
		dataPurchaseGoodsIndex.setUpdateDate(new Date());
	}

	private void insert(Long parentId, PurchaseGoodsIndex woodCghtJkMx, List<PurchaseGoodsIndex> dataWoodCghtJkMxList) {
		User currentUser = UserUtil.getCurrentUser();
		woodCghtJkMx.setParentId(Long.toString(parentId));
		woodCghtJkMx.setCreaterNo(currentUser.getLoginName());
		woodCghtJkMx.setCreaterName(currentUser.getName());
		woodCghtJkMx.setCreateDate(new Date());
		woodCghtJkMx.setCreaterDept(currentUser.getOrganization().getOrgName());
		dataWoodCghtJkMxList.add(woodCghtJkMx);
	}

}
