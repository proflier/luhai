package com.cbmie.lh.sale.service;

import java.util.ArrayList;
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
import com.cbmie.lh.sale.dao.SaleOfferIndexDao;
import com.cbmie.lh.sale.entity.SaleOfferGoods;
import com.cbmie.lh.sale.entity.SaleOfferIndex;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 销售报盘商品-指标
 */
@Service
@Transactional
public class SaleOfferIndexService extends BaseService<SaleOfferIndex, Long> {

	@Autowired
	private SaleOfferIndexDao saleOfferIndexDao;

	@Override
	public HibernateDao<SaleOfferIndex, Long> getEntityDao() {
		return saleOfferIndexDao;
	}

	public void save(SaleOfferGoods saleOfferGoods, String saleOfferIndexJson) {
		// 转成标准的json字符串
		saleOfferIndexJson = StringEscapeUtils.unescapeHtml4(saleOfferIndexJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<SaleOfferIndex> agreementGoodsList = new ArrayList<SaleOfferIndex>();
		try {
			JsonNode jsonNode = objectMapper.readTree(saleOfferIndexJson);
			for (JsonNode jn : jsonNode) {
				SaleOfferIndex agreementGoods = objectMapper.readValue(jn.toString(), SaleOfferIndex.class);
				agreementGoodsList.add(agreementGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取saleOfferGoods中agreementGoods持久化对象
		List<SaleOfferIndex> dataSaleOfferIndexList = saleOfferGoods.getIndexList();
		// 将数据库数据放入映射
		Map<Long, SaleOfferIndex> dataSaleOfferIndexMap = new HashMap<Long, SaleOfferIndex>(); 
		for (SaleOfferIndex dataSaleOfferIndex : dataSaleOfferIndexList) {
			dataSaleOfferIndexMap.put(dataSaleOfferIndex.getId(), dataSaleOfferIndex);
		}
		// 排除没有发生改变的
		for (SaleOfferIndex dataSaleOfferIndex : dataSaleOfferIndexList) {
			if (agreementGoodsList.contains(dataSaleOfferIndex)) {
				agreementGoodsList.remove(dataSaleOfferIndex);
				dataSaleOfferIndexMap.remove(dataSaleOfferIndex.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (SaleOfferIndex agreementGoods : agreementGoodsList) {
			if (agreementGoods.getId() == null) {
				insert(saleOfferGoods.getId(), agreementGoods, dataSaleOfferIndexList); // 新增 
			}
			SaleOfferIndex dataSaleOfferIndex = dataSaleOfferIndexMap.get(agreementGoods.getId());
			if (dataSaleOfferIndex != null) {
				update(dataSaleOfferIndex, agreementGoods); // 修改
				dataSaleOfferIndexMap.remove(agreementGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, SaleOfferIndex> entry : dataSaleOfferIndexMap.entrySet()) {
			dataSaleOfferIndexList.remove(entry.getValue());
			saleOfferIndexDao.delete(entry.getKey());
		}
	}

	private void update(SaleOfferIndex dataSaleOfferIndex, SaleOfferIndex saleOfferIndex) {
		dataSaleOfferIndex.setIndexNameEn(saleOfferIndex.getIndexNameEn());
		dataSaleOfferIndex.setIndexName(saleOfferIndex.getIndexName());
		dataSaleOfferIndex.setUnit(saleOfferIndex.getUnit());
		dataSaleOfferIndex.setInspectionValue(saleOfferIndex.getInspectionValue());
		dataSaleOfferIndex.setDescription(saleOfferIndex.getDescription());
	}
	
	private void insert(Long pid, SaleOfferIndex saleOfferIndex, List<SaleOfferIndex> dataSaleOfferIndexList) {
		saleOfferIndex.setPid(pid);
		dataSaleOfferIndexList.add(saleOfferIndex);
	}
	
}
