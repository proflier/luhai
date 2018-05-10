package com.cbmie.baseinfo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.service.DictChildService;
import com.cbmie.baseinfo.dao.AffiBaseInfoDao;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
/**
 * 关联单位__基本信息service
 */
@Service
@Transactional(readOnly = false)
public class AffiBaseInfoService extends BaseService<WoodAffiBaseInfo, Long> {
	
	@Autowired
	private DictChildService dictChildService;

	@Autowired
	private AffiBaseInfoDao affiBaseInfoDao;

	@Override
	public HibernateDao<WoodAffiBaseInfo, Long> getEntityDao() {
		return affiBaseInfoDao;
	}
	
	public List<WoodAffiBaseInfo> getByCustomerType(String num) {
		return affiBaseInfoDao.getByCustomerType(num);
	}
	
	public List<WoodAffiBaseInfo> getByCustomerTypes(String[] cTypes){
		return affiBaseInfoDao.getByCustomerTypes(cTypes);
	}

	//校验客户编码不能重复，如果重复，返回false
	public boolean validateCustomerNo(WoodAffiBaseInfo affiBaseInfo) {
		if(affiBaseInfoDao.getCodeByAffiBaseInfo(affiBaseInfo)>0)
			return false;
		else 
			return true;
	}

	public Object findByNo(WoodAffiBaseInfo affiBaseInfo) {
		return affiBaseInfoDao.findByNo(affiBaseInfo);
	}
	
	public void findEntityList(Page<WoodAffiBaseInfo> page,Map<String,Object> params){
		affiBaseInfoDao.findEntityList(page, params);
	}

	public WoodAffiBaseInfo findByCustomerName(WoodAffiBaseInfo affiBaseInfo) {
		return affiBaseInfoDao.findByCustomerName(affiBaseInfo);
	}

	public List<WoodAffiBaseInfo> getByCustomerTypes4UserPermission(String matchValue,String[] customerCode) {
		return affiBaseInfoDao.getByCustomerTypes4UserPermission(matchValue,customerCode);
	}

	public List<WoodAffiBaseInfo> getAffiOurUnitOrCustomer() {
		return affiBaseInfoDao.getAffiOurUnitOrCustomer();
	}

	public List<Map<String, Object>> getOurUnitAndCustomer(Map<String, Object> params) {
		List<Map<String, Object>> quaryDatas = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> returnValue = new ArrayList<Map<String, Object>>();
		quaryDatas = affiBaseInfoDao.getOurUnitAndCustomer(params);
		if(quaryDatas!=null&&quaryDatas.size()>0){
			for(Map<String, Object> quaryData:quaryDatas){
				Map<String, Object> returnMap = new HashMap<String, Object>();
				returnMap.putAll(quaryData);
				String customerType = dictChildService.findAllTypeNames(quaryData.get("customerType").toString(), "KHLX");
				returnMap.put("customerType", customerType);
				returnValue.add(returnMap);
			}
		}
		return returnValue;
	}
	
	public void initHistory() {
		List<WoodAffiBaseInfo> woodAffiBaseInfos = affiBaseInfoDao.findAll();
		for(WoodAffiBaseInfo woodAffiBaseInfo:woodAffiBaseInfos){
			String customerTypeOrg = woodAffiBaseInfo.getCustomerType();
			String customerTypeDest ="";
			String [] customerTypes = customerTypeOrg.split(",");
			for(String customerType :customerTypes){
				if(customerType.length()>1){
					customerType = "102300"+customerType;
				}else{
					customerType = "1023000"+customerType;
				}
				if(customerTypeDest!=""){
					customerTypeDest = customerTypeDest + "," + customerType;
				}else{
					customerTypeDest = customerType;
				}
			}
			woodAffiBaseInfo.setCustomerType(customerTypeDest);
			affiBaseInfoDao.save(woodAffiBaseInfo);
		}
		affiBaseInfoDao.getSession().flush();
	}
}
