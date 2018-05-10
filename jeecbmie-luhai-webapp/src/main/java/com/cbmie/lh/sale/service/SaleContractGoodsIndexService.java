package com.cbmie.lh.sale.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleContractGoodsIndexDao;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.lh.sale.entity.SaleContractGoodsIndex;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class SaleContractGoodsIndexService extends BaseService<SaleContractGoodsIndex, Long> {

	@Autowired
	private SaleContractGoodsIndexDao goodsIndexDao;
	
	@Override
	public HibernateDao<SaleContractGoodsIndex, Long> getEntityDao() {
		return goodsIndexDao;
	}

	/**
	 * 
	 * @param saleContractGoods
	 * @param goodsIndexJson
	 */
	public void save(SaleContractGoods saleContractGoods, String goodsIndexJson) {
		// 转成标准的json字符串
		goodsIndexJson = StringEscapeUtils.unescapeHtml4(goodsIndexJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<SaleContractGoodsIndex> saleContractGoodsIndexList = new ArrayList<SaleContractGoodsIndex>();
		try {
			JsonNode jsonNode = objectMapper.readTree(goodsIndexJson);
			for (JsonNode jn : jsonNode) {
				SaleContractGoodsIndex saleGoodsIndex = objectMapper.readValue(jn.toString(), SaleContractGoodsIndex.class);
				saleContractGoodsIndexList.add(saleGoodsIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取purchaseContractGoods中dataPurchaseGoodsIndexList持久化对象
		List<SaleContractGoodsIndex> dataSaleGoodsIndexList = saleContractGoods.getGoodsIndexList();
		// 将数据库数据放入映射
		Map<Long, SaleContractGoodsIndex> dataSaleGoodsIndexMap = new HashMap<Long, SaleContractGoodsIndex>(); 
		for (SaleContractGoodsIndex dataSaleGoodsIndex : dataSaleGoodsIndexList) {
			dataSaleGoodsIndexMap.put(dataSaleGoodsIndex.getId(), dataSaleGoodsIndex);
		}
		// 排除没有发生改变的
		for (SaleContractGoodsIndex dataSaleGoodsIndex : dataSaleGoodsIndexList) {
			if (saleContractGoodsIndexList.contains(dataSaleGoodsIndex)) {
				saleContractGoodsIndexList.remove(dataSaleGoodsIndex);
				dataSaleGoodsIndexMap.remove(dataSaleGoodsIndex.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (SaleContractGoodsIndex saleGoodsIndex : saleContractGoodsIndexList) {
			if (saleGoodsIndex.getId() == null) {
				insert(saleContractGoods.getId(), saleGoodsIndex, dataSaleGoodsIndexList); // 新增 
			}
			SaleContractGoodsIndex dataSaleGoodsIndex = dataSaleGoodsIndexMap.get(saleGoodsIndex.getId());
			if (dataSaleGoodsIndex != null) {
				update(dataSaleGoodsIndex, saleGoodsIndex); // 修改
				dataSaleGoodsIndexMap.remove(saleGoodsIndex.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, SaleContractGoodsIndex> entry : dataSaleGoodsIndexMap.entrySet()) {
			dataSaleGoodsIndexList.remove(entry.getValue());
			goodsIndexDao.delete(entry.getKey());
		}
	}

	private void update(SaleContractGoodsIndex dataSaleContractGoodsIndex, SaleContractGoodsIndex saleContractGoodsIndex) {
		User currentUser = UserUtil.getCurrentUser();
		dataSaleContractGoodsIndex.setIndexName(saleContractGoodsIndex.getIndexName());
		dataSaleContractGoodsIndex.setIndexNameE(saleContractGoodsIndex.getIndexNameE());
		dataSaleContractGoodsIndex.setUnit(saleContractGoodsIndex.getUnit());
		dataSaleContractGoodsIndex.setConventions(saleContractGoodsIndex.getConventions());
		dataSaleContractGoodsIndex.setTerms(saleContractGoodsIndex.getTerms());
		dataSaleContractGoodsIndex.setRejectionValue(saleContractGoodsIndex.getRejectionValue());
		saleContractGoodsIndex.setUpdaterDept(currentUser.getOrganization().getOrgName());
		dataSaleContractGoodsIndex.setUpdaterNo(currentUser.getLoginName());
		dataSaleContractGoodsIndex.setUpdaterName(currentUser.getName());
		dataSaleContractGoodsIndex.setUpdateDate(new Date());
	}
	
	private void insert(Long saleContractGoodsId, SaleContractGoodsIndex saleContractGoodsIndex, List<SaleContractGoodsIndex> dataWoodCghtJkMxList) {
		User currentUser = UserUtil.getCurrentUser();
		saleContractGoodsIndex.setSaleContractGoodsId(saleContractGoodsId);
		saleContractGoodsIndex.setCreaterNo(currentUser.getLoginName());
		saleContractGoodsIndex.setCreaterName(currentUser.getName());
		saleContractGoodsIndex.setCreateDate(new Date());
		saleContractGoodsIndex.setCreaterDept(currentUser.getOrganization().getOrgName());
		dataWoodCghtJkMxList.add(saleContractGoodsIndex);
	}
}
