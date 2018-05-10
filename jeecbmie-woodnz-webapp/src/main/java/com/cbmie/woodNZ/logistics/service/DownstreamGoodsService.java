package com.cbmie.woodNZ.logistics.service;

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
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.logistics.dao.DownstreamGoodsDao;
import com.cbmie.woodNZ.logistics.entity.DownstreamBill;
import com.cbmie.woodNZ.logistics.entity.DownstreamGoods;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 到单子表- 货物明细service
 */
@Service
@Transactional
public class DownstreamGoodsService extends BaseService<DownstreamGoods, Long> {


	@Autowired 
	private DownstreamGoodsDao downstreamGoodsDao;
	
	@Override
	public HibernateDao<DownstreamGoods, Long> getEntityDao() {
		return downstreamGoodsDao;
	}
	
	public void save(DownstreamBill downstreamBill, String billGoodsJson) throws IllegalAccessException, InvocationTargetException {
		// 转成标准的json字符串
		billGoodsJson = StringEscapeUtils.unescapeHtml4(billGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<DownstreamGoods> downstreamGoodsList = new ArrayList<DownstreamGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(billGoodsJson);
			for (JsonNode jn : jsonNode) {
				DownstreamGoods downstreamGoods = objectMapper.readValue(jn.toString(), DownstreamGoods.class);
				downstreamGoodsList.add(downstreamGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取woodCghtJk中dataWoodCghtJkMxList持久化对象
		List<DownstreamGoods> dataDownstreamGoodsList = downstreamBill.getDownstreamGoodsList();
		// 将数据库数据放入映射
		Map<Long, DownstreamGoods> dataDownstreamGoodsMap = new HashMap<Long, DownstreamGoods>(); 
		for (DownstreamGoods dataDownstreamGoods : dataDownstreamGoodsList) {
			dataDownstreamGoodsMap.put(dataDownstreamGoods.getId(), dataDownstreamGoods);
		}
		// 排除没有发生改变的
		for (DownstreamGoods dataDownstreamGoods : dataDownstreamGoodsList) {
			if (downstreamGoodsList.contains(dataDownstreamGoods)) {
				downstreamGoodsList.remove(dataDownstreamGoods);
				dataDownstreamGoodsMap.remove(dataDownstreamGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (DownstreamGoods downstreamGoods : downstreamGoodsList) {
			if (downstreamGoods.getId() == null || getNoLoad(downstreamGoods.getId()) == null) {
				insert(downstreamBill.getId(), downstreamGoods, dataDownstreamGoodsList); // 新增 
			}
			DownstreamGoods dataDownstreamGoods = dataDownstreamGoodsMap.get(downstreamGoods.getId());
			if (dataDownstreamGoods != null) {
				update(dataDownstreamGoods, downstreamGoods); // 修改
				dataDownstreamGoodsMap.remove(downstreamGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, DownstreamGoods> entry : dataDownstreamGoodsMap.entrySet()) {
			dataDownstreamGoodsList.remove(entry.getValue());
			downstreamGoodsDao.delete(entry.getKey());
		}
	}
	
	
	private void update(DownstreamGoods dataDownstreamGoods, DownstreamGoods downstreamGoods) throws IllegalAccessException, InvocationTargetException {
		User currentUser = UserUtil.getCurrentUser();
		BeanUtils.copyProperties(dataDownstreamGoods, downstreamGoods);
		downstreamGoods.setUpdaterDept(currentUser.getOrganization().getOrgName());
		dataDownstreamGoods.setUpdaterNo(currentUser.getLoginName());
		dataDownstreamGoods.setUpdaterName(currentUser.getName());
		dataDownstreamGoods.setUpdateDate(new Date());
	}
	
	private void insert(Long parentId, DownstreamGoods downstreamGoods, List<DownstreamGoods> dataDownstreamGoodsList) {
		User currentUser = UserUtil.getCurrentUser();
		downstreamGoods.setId(null);
		downstreamGoods.setParentId(Long.toString(parentId));
		downstreamGoods.setCreaterNo(currentUser.getLoginName());
		downstreamGoods.setCreaterName(currentUser.getName());
		downstreamGoods.setCreateDate(new Date());
		downstreamGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		dataDownstreamGoodsList.add(downstreamGoods);
	}

	/**
	 * @param id
	 * @return
	 */
	public List<DownstreamGoods> getByParentId(Long id) {
		return downstreamGoodsDao.getGoodsListByParentId(id);
	}

}
