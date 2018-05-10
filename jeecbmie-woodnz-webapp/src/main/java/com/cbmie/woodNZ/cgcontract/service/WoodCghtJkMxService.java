package com.cbmie.woodNZ.cgcontract.service;

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
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.cgcontract.dao.WoodCghtJkMxDao;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMx;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 采购合同-进口-子表信息service
 */
@Service
@Transactional
public class WoodCghtJkMxService extends BaseService<WoodCghtJkMx, Long> {

	@Autowired
	private WoodCghtJkMxDao woodCghtJkMxDao;

	@Override
	public HibernateDao<WoodCghtJkMx, Long> getEntityDao() {
		return woodCghtJkMxDao;
	}
	
	/**
	 * 
	 * @param woodCghtJk
	 * @param woodCghtJkMxJson
	 */
	public void save(WoodCghtJk woodCghtJk, String woodCghtJkMxJson) {
		// 转成标准的json字符串
		woodCghtJkMxJson = StringEscapeUtils.unescapeHtml4(woodCghtJkMxJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<WoodCghtJkMx> woodCghtJkMxList = new ArrayList<WoodCghtJkMx>();
		try {
			JsonNode jsonNode = objectMapper.readTree(woodCghtJkMxJson);
			for (JsonNode jn : jsonNode) {
				WoodCghtJkMx woodCghtJkMx = objectMapper.readValue(jn.toString(), WoodCghtJkMx.class);
				woodCghtJkMxList.add(woodCghtJkMx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取woodCghtJk中dataWoodCghtJkMxList持久化对象
		List<WoodCghtJkMx> dataWoodCghtJkMxList = woodCghtJk.getWoodCghtJkMxList();
		// 将数据库数据放入映射
		Map<Long, WoodCghtJkMx> dataWoodCghtJkMxMap = new HashMap<Long, WoodCghtJkMx>(); 
		for (WoodCghtJkMx dataWoodCghtJkMx : dataWoodCghtJkMxList) {
			dataWoodCghtJkMxMap.put(dataWoodCghtJkMx.getId(), dataWoodCghtJkMx);
		}
		// 排除没有发生改变的
		for (WoodCghtJkMx dataWoodCghtJkMx : dataWoodCghtJkMxList) {
			if (woodCghtJkMxList.contains(dataWoodCghtJkMx)) {
				woodCghtJkMxList.remove(dataWoodCghtJkMx);
				dataWoodCghtJkMxMap.remove(dataWoodCghtJkMx.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (WoodCghtJkMx woodCghtJkMx : woodCghtJkMxList) {
			if (woodCghtJkMx.getId() == null) {
				insert(woodCghtJk.getId(), woodCghtJkMx, dataWoodCghtJkMxList); // 新增 
			}
			WoodCghtJkMx dataWoodCghtJkMx = dataWoodCghtJkMxMap.get(woodCghtJkMx.getId());
			if (dataWoodCghtJkMx != null) {
				update(dataWoodCghtJkMx, woodCghtJkMx); // 修改
				dataWoodCghtJkMxMap.remove(woodCghtJkMx.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, WoodCghtJkMx> entry : dataWoodCghtJkMxMap.entrySet()) {
			dataWoodCghtJkMxList.remove(entry.getValue());
			woodCghtJkMxDao.delete(entry.getKey());
		}
	}

	private void update(WoodCghtJkMx dataWoodCghtJkMx, WoodCghtJkMx woodCghtJkMx) {
		User currentUser = UserUtil.getCurrentUser();
		dataWoodCghtJkMx.setSpbm(woodCghtJkMx.getSpbm());
		dataWoodCghtJkMx.setSpmc(woodCghtJkMx.getSpmc());
		dataWoodCghtJkMx.setSfbwwz(woodCghtJkMx.getSfbwwz());
		dataWoodCghtJkMx.setSpms(woodCghtJkMx.getSpms());
		dataWoodCghtJkMx.setDptj(woodCghtJkMx.getDptj());
		dataWoodCghtJkMx.setPs(woodCghtJkMx.getPs());
		dataWoodCghtJkMx.setJs(woodCghtJkMx.getJs());
		dataWoodCghtJkMx.setLfs(woodCghtJkMx.getLfs());
		dataWoodCghtJkMx.setSldw(woodCghtJkMx.getSldw());
		dataWoodCghtJkMx.setCgdj(woodCghtJkMx.getCgdj());
		dataWoodCghtJkMx.setCgbz(woodCghtJkMx.getCgbz());
		dataWoodCghtJkMx.setCgje(woodCghtJkMx.getCgje());
		dataWoodCghtJkMx.setYjhdgg(woodCghtJkMx.getYjhdgg());
		dataWoodCghtJkMx.setMs(woodCghtJkMx.getMs());
		woodCghtJkMx.setUpdaterDept(currentUser.getOrganization().getOrgName());
		dataWoodCghtJkMx.setUpdaterNo(currentUser.getLoginName());
		dataWoodCghtJkMx.setUpdaterName(currentUser.getName());
		dataWoodCghtJkMx.setUpdateDate(new Date());
	}
	
	private void insert(Long parentId, WoodCghtJkMx woodCghtJkMx, List<WoodCghtJkMx> dataWoodCghtJkMxList) {
		User currentUser = UserUtil.getCurrentUser();
		woodCghtJkMx.setParentId(Long.toString(parentId));
		woodCghtJkMx.setCreaterNo(currentUser.getLoginName());
		woodCghtJkMx.setCreaterName(currentUser.getName());
		woodCghtJkMx.setCreateDate(new Date());
		woodCghtJkMx.setCreaterDept(currentUser.getOrganization().getOrgName());
		dataWoodCghtJkMxList.add(woodCghtJkMx);
	}
	
}
