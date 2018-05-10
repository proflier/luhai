package com.cbmie.system.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.CustomerPerssionDao;
import com.cbmie.system.dao.UserDao;
import com.cbmie.system.entity.CustomerPerssion;
import com.itextpdf.text.log.SysoCounter;

/**
 * 客户相关用户权限service
 */
@Service
@Transactional
public class CustomerPerssionService extends BaseService<CustomerPerssion, Long> {

	@Autowired
	private CustomerPerssionDao customerPerssionDao;

	@Autowired
	private UserDao userDao;

	@Override
	public HibernateDao<CustomerPerssion, Long> getEntityDao() {
		return customerPerssionDao;
	}

	public CustomerPerssion findByCus(String customerCode) {
		return customerPerssionDao.findUniqueBy("customerCode", customerCode);
	}

	public List<String> getCurrenCustomerCode(String username) {
		return customerPerssionDao.getCurrenCustomerCode(username);
	}

	@Transactional(readOnly = false)
	public void save(CustomerPerssion CustomerPerssion) {
		customerPerssionDao.save(CustomerPerssion);
	}

	public String getBusinessManager(String customerCode) {
		return customerPerssionDao.getUserNameByCode(customerCode);
	}

	public String getUserLoginsByCode(String customerCode) {
		return customerPerssionDao.getUserLoginsByCode(customerCode);
	}

	public String getRanges(String customerCode) {
		CustomerPerssion customerPerssion = customerPerssionDao.findUniqueBy("customerCode", customerCode);
		if (customerPerssion != null) {
			String ranges = customerPerssion.getRanges();
			if (StringUtils.isNotBlank(ranges)) {
				String[] params = ranges.split(",");
				return customerPerssionDao.getUserNamesByLoginNames(params);
			} else {
				return "";
			}
		} else {
			return "";
		}

	}

	public void saveByUser(String loginName, String[] customerCodes) {
		List<String> customerCodesOrg = userDao.getCodeRelation4Affi(loginName);
		List<String> addString = new ArrayList<String>();
		List<String> deleteString = new ArrayList<String>();
		List<String> updateString = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		//两个集合不判断空，自动过滤，最后变更数据
		for (String string : customerCodesOrg) {
			map.put(string, 1);
		}
		if(customerCodes!=null){
			for (String string : customerCodes) {
				if (map.get(string) != null) {
					updateString.add(string);
					map.put(string, 2);
				} else {
					addString.add(string);
				}
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				deleteString.add(entry.getKey());
			}
		}
		for(String add: addString){
			CustomerPerssion customerPerssion  = customerPerssionDao.findUniqueBy("customerCode", add);
			if(customerPerssion!=null){
				if(StringUtils.isNotBlank(customerPerssion.getRanges())){
					customerPerssion.setRanges(customerPerssion.getRanges()+","+loginName);
				}else{
					customerPerssion.setRanges(loginName);
				}
			}else{
				customerPerssion = new CustomerPerssion();
				customerPerssion.setRanges(loginName);
				customerPerssion.setCustomerCode(add);
				customerPerssion.setBusinessManager(loginName);
			}
			customerPerssionDao.save(customerPerssion);
			customerPerssionDao.getSession().flush();
		}
		for(String delete: deleteString){
			CustomerPerssion customerPerssion = new CustomerPerssion();
			customerPerssion = customerPerssionDao.findUniqueBy("customerCode", delete);
			String ranges = customerPerssion.getRanges();
			List<String > rageList = new ArrayList<String>(Arrays.asList(ranges.split(",")));
			rageList.remove(loginName);
			customerPerssion.setRanges(StringUtils.join(rageList, ","));
			customerPerssionDao.save(customerPerssion);
			customerPerssionDao.getSession().flush();
		}
		
	}

	public Map<String, String> initSelect(String customerCode) {
		return customerPerssionDao.initSelect(customerCode);
	}
	

}
