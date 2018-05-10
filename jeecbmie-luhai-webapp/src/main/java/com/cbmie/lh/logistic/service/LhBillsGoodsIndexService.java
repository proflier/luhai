package com.cbmie.lh.logistic.service;

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
import com.cbmie.lh.logistic.dao.LhBillsGoodsIndexDao;
import com.cbmie.lh.logistic.entity.LhBillsGoods;
import com.cbmie.lh.logistic.entity.LhBillsGoodsIndex;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class LhBillsGoodsIndexService extends BaseService<LhBillsGoodsIndex, Long> {
	@Autowired
	private LhBillsGoodsIndexDao indexDao;

	@Override
	public HibernateDao<LhBillsGoodsIndex, Long> getEntityDao() {
		return indexDao;
	}
	
	public void deleteEntityByParentId(String parentId){
		indexDao.deleteEntityByParentId(parentId);
	}
	
	/**
	 * 
	 * @param purchaseContractGoods
	 * @param goodsIndexJson
	 */
	public void save(LhBillsGoods billsGoods, String goodsIndexJson) {
		// 转成标准的json字符串
		goodsIndexJson = StringEscapeUtils.unescapeHtml4(goodsIndexJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<LhBillsGoodsIndex> billsGoodsIndexList = new ArrayList<LhBillsGoodsIndex>();
		try {
			JsonNode jsonNode = objectMapper.readTree(goodsIndexJson);
			for (JsonNode jn : jsonNode) {
				LhBillsGoodsIndex billsGoodsIndex = objectMapper.readValue(jn.toString(), LhBillsGoodsIndex.class);
				billsGoodsIndexList.add(billsGoodsIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取purchaseContractGoods中dataLhBillsGoodsIndexList持久化对象
		List<LhBillsGoodsIndex> dataLhBillsGoodsIndexList = billsGoods.getGoodsIndexList();
		// 将数据库数据放入映射
		Map<Long, LhBillsGoodsIndex> dataLhBillsGoodsIndexMap = new HashMap<Long, LhBillsGoodsIndex>(); 
		for (LhBillsGoodsIndex dataLhBillsGoodsIndex : dataLhBillsGoodsIndexList) {
			dataLhBillsGoodsIndexMap.put(dataLhBillsGoodsIndex.getId(), dataLhBillsGoodsIndex);
		}
		for (LhBillsGoodsIndex dataLhBillsGoodsIndex : dataLhBillsGoodsIndexList) {
			if (billsGoodsIndexList.contains(dataLhBillsGoodsIndex)) {
				billsGoodsIndexList.remove(dataLhBillsGoodsIndex);
				dataLhBillsGoodsIndexMap.remove(dataLhBillsGoodsIndex.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (LhBillsGoodsIndex lhBillsGoodsIndex : billsGoodsIndexList) {
			if (lhBillsGoodsIndex.getId() == null) {
				insert(billsGoods.getId(), lhBillsGoodsIndex, dataLhBillsGoodsIndexList); // 新增 
			}
			LhBillsGoodsIndex dataLhBillsGoodsIndex = dataLhBillsGoodsIndexMap.get(lhBillsGoodsIndex.getId());
			if (dataLhBillsGoodsIndex != null) {
				update(dataLhBillsGoodsIndex, lhBillsGoodsIndex); // 修改
				dataLhBillsGoodsIndexMap.remove(lhBillsGoodsIndex.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, LhBillsGoodsIndex> entry : dataLhBillsGoodsIndexMap.entrySet()) {
			dataLhBillsGoodsIndexList.remove(entry.getValue());
			indexDao.delete(entry.getKey());
		}
	}
	
	private void update(LhBillsGoodsIndex dataGoodsIndex, LhBillsGoodsIndex goodsIndex) {
		User currentUser = UserUtil.getCurrentUser();
		dataGoodsIndex.setIndexName(goodsIndex.getIndexName());
		dataGoodsIndex.setIndexNameE(goodsIndex.getIndexNameE());
		dataGoodsIndex.setUnit(goodsIndex.getUnit());
		dataGoodsIndex.setConventions(goodsIndex.getConventions());
		dataGoodsIndex.setTerms(goodsIndex.getTerms());
		dataGoodsIndex.setRejectionValue(goodsIndex.getRejectionValue());
		goodsIndex.setUpdaterDept(currentUser.getOrganization().getOrgName());
		dataGoodsIndex.setUpdaterNo(currentUser.getLoginName());
		dataGoodsIndex.setUpdaterName(currentUser.getName());
		dataGoodsIndex.setUpdateDate(new Date());
	}
	
	private void insert(Long parentId, LhBillsGoodsIndex goodsIndex, List<LhBillsGoodsIndex> goodsIndexList) {
		User currentUser = UserUtil.getCurrentUser();
		goodsIndex.setParentId(Long.toString(parentId));
		goodsIndex.setCreaterNo(currentUser.getLoginName());
		goodsIndex.setCreaterName(currentUser.getName());
		goodsIndex.setCreateDate(new Date());
		goodsIndex.setCreaterDept(currentUser.getOrganization().getOrgName());
		goodsIndexList.add(goodsIndex);
	}

}
