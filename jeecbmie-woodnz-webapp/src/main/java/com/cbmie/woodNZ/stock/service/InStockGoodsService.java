package com.cbmie.woodNZ.stock.service;

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
import com.cbmie.woodNZ.stock.dao.InStockGoodsDao;
import com.cbmie.woodNZ.stock.entity.InStock;
import com.cbmie.woodNZ.stock.entity.InStockGoods;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 入库商品
 * @author linxiaopeng
 * 2016年6月28日
 */
@Service
@Transactional
public class InStockGoodsService extends BaseService<InStockGoods, Long> {

	@Autowired
	private InStockGoodsDao inStockGoodsDao;

	@Override
	public HibernateDao<InStockGoods, Long> getEntityDao() {
		return inStockGoodsDao;
	}
	
	/**
	 * @param id
	 * @return
	 */
	public List<InStockGoods> getByParentId(Long id) {
		return inStockGoodsDao.getByParentId(id);
	}
	
	public void save(InStock inStock, String inStockGoodsJson, User currentUser) {
		try {
			// 转成标准的json字符串
			inStockGoodsJson = StringEscapeUtils.unescapeHtml4(inStockGoodsJson);
			// 把json转成对象
			ObjectMapper objectMapper = new ObjectMapper();
			List<InStockGoods> inStockGoodsList = new ArrayList<InStockGoods>();
			try {
				JsonNode jsonNode = objectMapper.readTree(inStockGoodsJson);
				for (JsonNode jn : jsonNode) {
					InStockGoods inStockGoods = objectMapper.readValue(jn.toString(), InStockGoods.class);
					inStockGoodsList.add(inStockGoods);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 获取invoiceReg中invoiceGoods持久化对象
			List<InStockGoods> dataInStockGoodsList = inStock.getInStockGoods();
			// 将数据库数据放入映射
			Map<Long, InStockGoods> dataInStockGoodsMap = new HashMap<Long, InStockGoods>(); 
			for (InStockGoods dataInStockGoods : dataInStockGoodsList) {
				dataInStockGoodsMap.put(dataInStockGoods.getId(), dataInStockGoods);
			}
			// 排除没有发生改变的
			for (InStockGoods dataInStockGoods : dataInStockGoodsList) {
				if (inStockGoodsList.contains(dataInStockGoods)) {
					inStockGoodsList.remove(dataInStockGoods);
					dataInStockGoodsMap.remove(dataInStockGoods.getId()); //从映射中移除未变化的数据
				}
			}
			// 保存数据
			for (InStockGoods inStockGoods : inStockGoodsList) {
				if (inStockGoods.getId() == null || getNoLoad(inStockGoods.getId()) == null) {
					insert(inStock.getId(), inStockGoods, dataInStockGoodsList, currentUser); // 新增 
				}
				InStockGoods dataInStockGoods = dataInStockGoodsMap.get(inStockGoods.getId());
				if (dataInStockGoods != null) {
					update(dataInStockGoods, inStockGoods, currentUser); // 修改
					dataInStockGoodsMap.remove(inStockGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
				}
			}
			// 删除数据
			for (Entry<Long, InStockGoods> entry : dataInStockGoodsMap.entrySet()) {
				dataInStockGoodsList.remove(entry.getValue());
				inStockGoodsDao.delete(entry.getKey());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void update(InStockGoods dataInStockGoods, InStockGoods inStockGoods, User currentUser) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(dataInStockGoods, inStockGoods);
	}
	
	private void insert(Long pid, InStockGoods inStockGoods, List<InStockGoods> dataInStockGoodsList, User currentUser) {
		inStockGoods.setId(null);
		inStockGoods.setCreaterNo(currentUser.getLoginName());
		inStockGoods.setCreaterName(currentUser.getName());
		inStockGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		inStockGoods.setCreateDate(new Date());
		inStockGoods.setParentId(String.valueOf(pid));
		dataInStockGoodsList.add(inStockGoods);
	}
	
}
