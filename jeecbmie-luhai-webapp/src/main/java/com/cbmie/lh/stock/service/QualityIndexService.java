package com.cbmie.lh.stock.service;

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
import com.cbmie.lh.stock.dao.QualityIndexDao;
import com.cbmie.lh.stock.entity.QualityGoods;
import com.cbmie.lh.stock.entity.QualityIndex;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 质检-指标
 */
@Service
@Transactional
public class QualityIndexService extends BaseService<QualityIndex, Long> {

	@Autowired
	private QualityIndexDao qualityIndexDao;

	@Override
	public HibernateDao<QualityIndex, Long> getEntityDao() {
		return qualityIndexDao;
	}

	public void save(QualityGoods qualityGoods, String qualityIndexJson) {
		// 转成标准的json字符串
		qualityIndexJson = StringEscapeUtils.unescapeHtml4(qualityIndexJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<QualityIndex> agreementGoodsList = new ArrayList<QualityIndex>();
		try {
			JsonNode jsonNode = objectMapper.readTree(qualityIndexJson);
			for (JsonNode jn : jsonNode) {
				QualityIndex agreementGoods = objectMapper.readValue(jn.toString(), QualityIndex.class);
				agreementGoodsList.add(agreementGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取qualityGoods中agreementGoods持久化对象
		List<QualityIndex> dataQualityIndexList = qualityGoods.getIndexList();
		// 将数据库数据放入映射
		Map<Long, QualityIndex> dataQualityIndexMap = new HashMap<Long, QualityIndex>(); 
		for (QualityIndex dataQualityIndex : dataQualityIndexList) {
			dataQualityIndexMap.put(dataQualityIndex.getId(), dataQualityIndex);
		}
		// 排除没有发生改变的
		for (QualityIndex dataQualityIndex : dataQualityIndexList) {
			if (agreementGoodsList.contains(dataQualityIndex)) {
				agreementGoodsList.remove(dataQualityIndex);
				dataQualityIndexMap.remove(dataQualityIndex.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (QualityIndex agreementGoods : agreementGoodsList) {
			if (agreementGoods.getId() == null) {
				insert(qualityGoods.getId(), agreementGoods, dataQualityIndexList); // 新增 
			}
			QualityIndex dataQualityIndex = dataQualityIndexMap.get(agreementGoods.getId());
			if (dataQualityIndex != null) {
				update(dataQualityIndex, agreementGoods); // 修改
				dataQualityIndexMap.remove(agreementGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, QualityIndex> entry : dataQualityIndexMap.entrySet()) {
			dataQualityIndexList.remove(entry.getValue());
			qualityIndexDao.delete(entry.getKey());
		}
	}

	private void update(QualityIndex dataQualityIndex, QualityIndex qualityIndex) {
		dataQualityIndex.setIndexNameEn(qualityIndex.getIndexNameEn());
		dataQualityIndex.setIndexName(qualityIndex.getIndexName());
		dataQualityIndex.setUnit(qualityIndex.getUnit());
		dataQualityIndex.setInspectionValue(qualityIndex.getInspectionValue());
	}
	
	private void insert(Long pid, QualityIndex qualityIndex, List<QualityIndex> dataQualityIndexList) {
		qualityIndex.setPid(pid);
		dataQualityIndexList.add(qualityIndex);
	}
	
}
