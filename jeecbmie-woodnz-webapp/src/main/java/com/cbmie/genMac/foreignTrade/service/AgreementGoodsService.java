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
import com.cbmie.genMac.foreignTrade.dao.AgreementGoodsDao;
import com.cbmie.genMac.foreignTrade.entity.AgencyAgreement;
import com.cbmie.genMac.foreignTrade.entity.AgreementGoods;
import com.cbmie.system.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 协议商品service
 */
@Service
@Transactional
public class AgreementGoodsService extends BaseService<AgreementGoods, Long> {

	@Autowired
	private AgreementGoodsDao agreementGoodsDao;

	@Override
	public HibernateDao<AgreementGoods, Long> getEntityDao() {
		return agreementGoodsDao;
	}
	
	public void save(AgencyAgreement agencyAgreement, String agreementGoodsJson, User currentUser) {
		// 转成标准的json字符串
		agreementGoodsJson = StringEscapeUtils.unescapeHtml4(agreementGoodsJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<AgreementGoods> agreementGoodsList = new ArrayList<AgreementGoods>();
		try {
			JsonNode jsonNode = objectMapper.readTree(agreementGoodsJson);
			for (JsonNode jn : jsonNode) {
				AgreementGoods agreementGoods = objectMapper.readValue(jn.toString(), AgreementGoods.class);
				agreementGoodsList.add(agreementGoods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获取agencyAgreement中agreementGoods持久化对象
		List<AgreementGoods> dataAgreementGoodsList = agencyAgreement.getAgreementGoods();
		// 将数据库数据放入映射
		Map<Long, AgreementGoods> dataAgreementGoodsMap = new HashMap<Long, AgreementGoods>(); 
		for (AgreementGoods dataAgreementGoods : dataAgreementGoodsList) {
			dataAgreementGoodsMap.put(dataAgreementGoods.getId(), dataAgreementGoods);
		}
		// 排除没有发生改变的
		for (AgreementGoods dataAgreementGoods : dataAgreementGoodsList) {
			if (agreementGoodsList.contains(dataAgreementGoods)) {
				agreementGoodsList.remove(dataAgreementGoods);
				dataAgreementGoodsMap.remove(dataAgreementGoods.getId()); //从映射中移除未变化的数据
			}
		}
		// 保存数据
		for (AgreementGoods agreementGoods : agreementGoodsList) {
			if (agreementGoods.getId() == null) {
				insert(agencyAgreement.getId(), agreementGoods, dataAgreementGoodsList, currentUser); // 新增 
			}
			AgreementGoods dataAgreementGoods = dataAgreementGoodsMap.get(agreementGoods.getId());
			if (dataAgreementGoods != null) {
				update(dataAgreementGoods, agreementGoods, currentUser); // 修改
				dataAgreementGoodsMap.remove(agreementGoods.getId()); // 从映射中移除已经修改的数据，剩下要删除数据；
			}
		}
		// 删除数据
		for (Entry<Long, AgreementGoods> entry : dataAgreementGoodsMap.entrySet()) {
			dataAgreementGoodsList.remove(entry.getValue());
			agreementGoodsDao.delete(entry.getKey());
		}
	}

	private void update(AgreementGoods dataAgreementGoods, AgreementGoods agreementGoods, User currentUser) {
		dataAgreementGoods.setUpdaterNo(currentUser.getLoginName());
		dataAgreementGoods.setUpdaterName(currentUser.getName());
		dataAgreementGoods.setUpdaterDept(currentUser.getOrganization().getOrgName());
		dataAgreementGoods.setUpdateDate(new Date());
		dataAgreementGoods.setGoodsCategory(agreementGoods.getGoodsCategory());
		dataAgreementGoods.setSpecification(agreementGoods.getSpecification());
		dataAgreementGoods.setOrigin(agreementGoods.getOrigin());
		dataAgreementGoods.setEname(agreementGoods.getEname());
		dataAgreementGoods.setCname(agreementGoods.getCname());
		dataAgreementGoods.setAmount(agreementGoods.getAmount());
		dataAgreementGoods.setUnit(agreementGoods.getUnit());
		dataAgreementGoods.setPrice(agreementGoods.getPrice());
		dataAgreementGoods.setOriginal(agreementGoods.getOriginal());
		dataAgreementGoods.setRmb(agreementGoods.getRmb());
	}
	
	private void insert(Long pid, AgreementGoods agreementGoods, List<AgreementGoods> dataAgreementGoodsList, User currentUser) {
		agreementGoods.setCreaterNo(currentUser.getLoginName());
		agreementGoods.setCreaterName(currentUser.getName());
		agreementGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		agreementGoods.setCreateDate(new Date());
		agreementGoods.setPid(pid);
		dataAgreementGoodsList.add(agreementGoods);
	}
	
}
