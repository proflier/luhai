package com.cbmie.genMac.foreignTrade.service;

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
import com.cbmie.genMac.foreignTrade.dao.CommodityDao;
import com.cbmie.genMac.foreignTrade.entity.Commodity;
import com.cbmie.genMac.foreignTrade.entity.ImportContract;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 进口商品service
 */
@Service
@Transactional
public class CommodityService extends BaseService<Commodity, Long> {

	@Autowired
	private CommodityDao commodityDao;

	@Override
	public HibernateDao<Commodity, Long> getEntityDao() {
		return commodityDao;
	}

	
	public void save(ImportContract importContract, String commodityChildJson) {
		// 转成标准的json字符串
		commodityChildJson = StringEscapeUtils.unescapeHtml4(commodityChildJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<Commodity> commodityList = new ArrayList<Commodity>();
		try {
			JsonNode jsonNode = objectMapper.readTree(commodityChildJson);
			for (JsonNode jn : jsonNode) {
				Commodity commodity = objectMapper.readValue(jn.toString(), Commodity.class);
				commodityList.add(commodity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取commodity中Commodity持久化对象
		List<Commodity> dataCommodityList = importContract.getCommodityList();
		// 将数据库数据放入映射
		Map<Long, Commodity> dataCommodityMap = new HashMap<Long, Commodity>(); 
		for (Commodity dataCommodity : dataCommodityList) {
			dataCommodityMap.put(dataCommodity.getId(), dataCommodity);
		}
		// 排除没有发生改变的
		for (Commodity dataCommodity : dataCommodityList) {
			if (commodityList.contains(dataCommodity)) {
				commodityList.remove(dataCommodity);
				dataCommodityMap.remove(dataCommodity.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (Commodity commodity : commodityList) {
			if (commodity.getId() == null || getNoLoad(commodity.getId()) == null) {
				insert(importContract.getId(), commodity, dataCommodityList); // 新增 
			}
			Commodity dataCommodity = dataCommodityMap.get(commodity.getId());
			if (dataCommodity != null) {
				update(dataCommodity, commodity); // 修改
				dataCommodityMap.remove(commodity.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, Commodity> entry : dataCommodityMap.entrySet()) {
			dataCommodityList.remove(entry.getValue());
			commodityDao.delete(entry.getKey());
		}
	}

	private void update(Commodity dataCommodity, Commodity commodityChild) {
		User currentUser = UserUtil.getCurrentUser();
		dataCommodity.setGoodsCategory(commodityChild.getGoodsCategory());
		dataCommodity.setSpecification(commodityChild.getSpecification());
		dataCommodity.setOrigin(commodityChild.getOrigin());
		dataCommodity.setEname(commodityChild.getEname());
		dataCommodity.setAmount(commodityChild.getAmount());
		dataCommodity.setUnit(commodityChild.getUnit());
		dataCommodity.setPrice(commodityChild.getPrice());
		dataCommodity.setOriginal(commodityChild.getOriginal());
		dataCommodity.setTax(commodityChild.getTax());
		dataCommodity.setTaxRate(commodityChild.getTaxRate());
		dataCommodity.setSaleTax(commodityChild.getSaleTax());
		dataCommodity.setSaleTaxRate(commodityChild.getSaleTaxRate());
		dataCommodity.setVat(commodityChild.getVat());
		dataCommodity.setVatRate(commodityChild.getVatRate());
		dataCommodity.setCname(commodityChild.getCname());
		dataCommodity.setUpdaterNo(currentUser.getLoginName());
		dataCommodity.setUpdaterName(currentUser.getName());
		dataCommodity.setUpdateDate(new Date());
	}
	
	private void insert(Long pid, Commodity commodity, List<Commodity> dataCommodityList) {
		User currentUser = UserUtil.getCurrentUser();
		commodity.setParentId(pid);
		commodity.setCreaterNo(currentUser.getLoginName());
		commodity.setCreaterName(currentUser.getName());
		commodity.setCreateDate(new Date());
		dataCommodityList.add(commodity);
	}
	
	
	
	
	
}
