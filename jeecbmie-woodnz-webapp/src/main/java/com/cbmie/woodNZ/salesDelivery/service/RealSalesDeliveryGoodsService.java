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
import com.cbmie.woodNZ.salesDelivery.dao.RealSalesDeliveryGoodsDao;
import com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery;
import com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 销售放货申请商品
 * @author linxiaopeng
 * 2016年6月30日
 */
@Service
@Transactional
public class RealSalesDeliveryGoodsService extends BaseService<RealSalesDeliveryGoods, Long> {

	@Autowired
	private RealSalesDeliveryGoodsDao realSalesDeliveryGoods;

	@Override
	public HibernateDao<RealSalesDeliveryGoods, Long> getEntityDao() {
		return realSalesDeliveryGoods;
	}
	
	public void save(SalesDelivery salesDelivery, String realSalesDeliveryGoodsJson, User currentUser) {
		try {
			// 转成标准的json字符串
			realSalesDeliveryGoodsJson = StringEscapeUtils.unescapeHtml4(realSalesDeliveryGoodsJson);
			// 把json转成对象
			ObjectMapper objectMapper = new ObjectMapper();
			List<RealSalesDeliveryGoods> realSalesDeliveryGoodsList = new ArrayList<RealSalesDeliveryGoods>();
			try {
				JsonNode jsonNode = objectMapper.readTree(realSalesDeliveryGoodsJson);
				for (JsonNode jn : jsonNode) {
					RealSalesDeliveryGoods realSalesDeliveryGoods = objectMapper.readValue(jn.toString(), RealSalesDeliveryGoods.class);
					realSalesDeliveryGoodsList.add(realSalesDeliveryGoods);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 获取invoiceReg中invoiceGoods持久化对象
			List<RealSalesDeliveryGoods> dataRealSalesDeliveryGoodsList = salesDelivery.getRealSalesDeliveryGoodsList();
			// 将数据库数据放入映射
			Map<Long, RealSalesDeliveryGoods> dataRealSalesDeliveryGoodsMap = new HashMap<Long, RealSalesDeliveryGoods>(); 
			for (RealSalesDeliveryGoods dataRealSalesDeliveryGoods : dataRealSalesDeliveryGoodsList) {
				dataRealSalesDeliveryGoodsMap.put(dataRealSalesDeliveryGoods.getId(), dataRealSalesDeliveryGoods);
			}
			// 排除没有发生改变的
			for (RealSalesDeliveryGoods dataRealSalesDeliveryGoods : dataRealSalesDeliveryGoodsList) {
				if (realSalesDeliveryGoodsList.contains(dataRealSalesDeliveryGoods)) {
					realSalesDeliveryGoodsList.remove(dataRealSalesDeliveryGoods);
					dataRealSalesDeliveryGoodsMap.remove(dataRealSalesDeliveryGoods.getId()); //从映射中移除未变化的数据
				}
			}
			// 保存数据
			for (RealSalesDeliveryGoods realSalesDeliveryGoods : realSalesDeliveryGoodsList) {
				if (realSalesDeliveryGoods.getId() == null || getNoLoad(realSalesDeliveryGoods.getId()) == null) {
					insert(salesDelivery.getId(), realSalesDeliveryGoods, dataRealSalesDeliveryGoodsList, currentUser); // 新增 
				}
				RealSalesDeliveryGoods dataRealSalesDeliveryGoods = dataRealSalesDeliveryGoodsMap.get(realSalesDeliveryGoods.getId());
				if (dataRealSalesDeliveryGoods != null) {
					update(dataRealSalesDeliveryGoods, realSalesDeliveryGoods, currentUser); // 修改
					dataRealSalesDeliveryGoodsMap.remove(realSalesDeliveryGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
				}
			}
			// 删除数据
			for (Entry<Long, RealSalesDeliveryGoods> entry : dataRealSalesDeliveryGoodsMap.entrySet()) {
				dataRealSalesDeliveryGoodsList.remove(entry.getValue());
				realSalesDeliveryGoods.delete(entry.getKey());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(RealSalesDeliveryGoods dataSalesDeliveryGoods, RealSalesDeliveryGoods salesDeliveryGoods, User currentUser) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(dataSalesDeliveryGoods, salesDeliveryGoods);
	}
	
	private void insert(Long pid, RealSalesDeliveryGoods salesDeliveryGoods, List<RealSalesDeliveryGoods> dataSalesDeliveryGoodsList, User currentUser) {
		salesDeliveryGoods.setId(null);
		salesDeliveryGoods.setCreaterNo(currentUser.getLoginName());
		salesDeliveryGoods.setCreaterName(currentUser.getName());
		salesDeliveryGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		salesDeliveryGoods.setCreateDate(new Date());
		salesDeliveryGoods.setParentId(pid);
		dataSalesDeliveryGoodsList.add(salesDeliveryGoods);
	}
	
}
