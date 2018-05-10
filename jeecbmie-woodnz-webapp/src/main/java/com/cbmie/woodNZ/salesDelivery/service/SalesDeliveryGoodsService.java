package com.cbmie.woodNZ.salesDelivery.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.entity.User;
import com.cbmie.woodNZ.salesDelivery.dao.SalesDeliveryGoodsDao;
import com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery;
import com.cbmie.woodNZ.salesDelivery.entity.SalesDeliveryGoods;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 销售放货申请商品
 * @author linxiaopeng
 * 2016年6月30日
 */
@Service
@Transactional
public class SalesDeliveryGoodsService extends BaseService<SalesDeliveryGoods, Long> {

	@Autowired
	private SalesDeliveryGoodsDao salesDeliveryGoods;

	@Override
	public HibernateDao<SalesDeliveryGoods, Long> getEntityDao() {
		return salesDeliveryGoods;
	}
	
	public void save(SalesDelivery salesDelivery, String salesDeliveryGoodsJson, User currentUser) {
		try {
			// 转成标准的json字符串
			salesDeliveryGoodsJson = StringEscapeUtils.unescapeHtml4(salesDeliveryGoodsJson);
			// 把json转成对象
			ObjectMapper objectMapper = new ObjectMapper();
			List<SalesDeliveryGoods> salesDeliveryGoodsList = new ArrayList<SalesDeliveryGoods>();
			try {
				JsonNode jsonNode = objectMapper.readTree(salesDeliveryGoodsJson);
				for (JsonNode jn : jsonNode) {
					SalesDeliveryGoods salesDeliveryGoods = objectMapper.readValue(jn.toString(), SalesDeliveryGoods.class);
					salesDeliveryGoodsList.add(salesDeliveryGoods);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 获取invoiceReg中invoiceGoods持久化对象
			List<SalesDeliveryGoods> dataSalesDeliveryGoodsList = salesDelivery.getSalesDeliveryGoodsList();
			// 将数据库数据放入映射
			Map<Long, SalesDeliveryGoods> dataSalesDeliveryGoodsMap = new HashMap<Long, SalesDeliveryGoods>(); 
			for (SalesDeliveryGoods dataSalesDeliveryGoods : dataSalesDeliveryGoodsList) {
				dataSalesDeliveryGoodsMap.put(dataSalesDeliveryGoods.getId(), dataSalesDeliveryGoods);
			}
			// 排除没有发生改变的
			for (SalesDeliveryGoods dataSalesDeliveryGoods : dataSalesDeliveryGoodsList) {
				if (salesDeliveryGoodsList.contains(dataSalesDeliveryGoods)) {
					salesDeliveryGoodsList.remove(dataSalesDeliveryGoods);
					dataSalesDeliveryGoodsMap.remove(dataSalesDeliveryGoods.getId()); //从映射中移除未变化的数据
				}
			}
			// 保存数据
			for (SalesDeliveryGoods salesDeliveryGoods : salesDeliveryGoodsList) {
				if (salesDeliveryGoods.getId() == null || getNoLoad(salesDeliveryGoods.getId()) == null) {
					insert(salesDelivery.getId(), salesDeliveryGoods, dataSalesDeliveryGoodsList, currentUser); // 新增 
				}
				SalesDeliveryGoods dataSalesDeliveryGoods = dataSalesDeliveryGoodsMap.get(salesDeliveryGoods.getId());
				if (dataSalesDeliveryGoods != null) {
					update(dataSalesDeliveryGoods, salesDeliveryGoods, currentUser); // 修改
					dataSalesDeliveryGoodsMap.remove(salesDeliveryGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
				}
			}
			// 删除数据
			for (Entry<Long, SalesDeliveryGoods> entry : dataSalesDeliveryGoodsMap.entrySet()) {
				dataSalesDeliveryGoodsList.remove(entry.getValue());
				salesDeliveryGoods.delete(entry.getKey());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(SalesDeliveryGoods dataSalesDeliveryGoods, SalesDeliveryGoods salesDeliveryGoods, User currentUser) throws IllegalAccessException, InvocationTargetException {
//		dataSalesDeliveryGoods.setUpdaterNo(currentUser.getLoginName());
//		dataSalesDeliveryGoods.setUpdaterName(currentUser.getName());
//		dataSalesDeliveryGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
//		dataSalesDeliveryGoods.setUpdateDate(new Date());
//		dataSalesDeliveryGoods.setContainerNo(salesDeliveryGoods.getContainerNo());
//		dataSalesDeliveryGoods.setContractNo(salesDeliveryGoods.getContractNo());
//		dataSalesDeliveryGoods.setCubeNumber(salesDeliveryGoods.getCubeNumber());
//		dataSalesDeliveryGoods.setGoodsCoding(salesDeliveryGoods.getGoodsCoding());
//		dataSalesDeliveryGoods.setGoodsLoss(salesDeliveryGoods.getGoodsLoss());
//		dataSalesDeliveryGoods.setGoodsName(salesDeliveryGoods.getGoodsName());
//		dataSalesDeliveryGoods.setGoodsNo(salesDeliveryGoods.getGoodsName());
//		dataSalesDeliveryGoods.setLockSellingPrice(salesDeliveryGoods.getLockSellingPrice());
//		dataSalesDeliveryGoods.setParentId(salesDeliveryGoods.getParentId());
//		dataSalesDeliveryGoods.setPurchasePrice(salesDeliveryGoods.getPurchasePrice());
//		dataSalesDeliveryGoods.setRealNumber(salesDeliveryGoods.getRealNumber());
//		dataSalesDeliveryGoods.setRealPiece(salesDeliveryGoods.getRealPiece());
//		dataSalesDeliveryGoods.setShouldNumber(salesDeliveryGoods.getShouldNumber());
//		dataSalesDeliveryGoods.setSummary(salesDeliveryGoods.getSummary());
		BeanUtils.copyProperties(dataSalesDeliveryGoods, salesDeliveryGoods);
	}
	
	private void insert(Long pid, SalesDeliveryGoods salesDeliveryGoods, List<SalesDeliveryGoods> dataSalesDeliveryGoodsList, User currentUser) {
		salesDeliveryGoods.setId(null);
		salesDeliveryGoods.setCreaterNo(currentUser.getLoginName());
		salesDeliveryGoods.setCreaterName(currentUser.getName());
		salesDeliveryGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		salesDeliveryGoods.setCreateDate(new Date());
		salesDeliveryGoods.setParentId(pid);
		dataSalesDeliveryGoodsList.add(salesDeliveryGoods);
	}
	
}
