package com.cbmie.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.CustomerPerssion;


/**
 * 客户相关用户权限
 */
@Repository
public class CustomerPerssionDao extends HibernateDao<CustomerPerssion, Long>{
	
	
	@Autowired
	private UserDao userDao;

	public List<String> getCurrenCustomerCode(String username) {
		String sql ="select  DISTINCT customer_code  from customer_perssion where LOCATE('"+username+"',ranges) or business_manager='"+username+"' ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	public String getUserNameByCode(String customerCode) {
		String sql ="select user.name from user JOIN customer_perssion on customer_perssion.business_manager = user.LOGIN_NAME where customer_perssion.customer_code='"+ customerCode+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return (String) sqlQuery.uniqueResult();
	}

	public String getUserNamesByLoginNames(String[] params) {
		String sql = "select GROUP_CONCAT(user.name) from  user where user.login_name in(:params) and user.login_status='1'";
		String returnValue = (String) getSession().createSQLQuery(sql).setParameterList("params", params).uniqueResult();
		if(StringUtils.isNotBlank(returnValue)){
			return returnValue;
		}else{
			return "";
		}
	}
	
	public String getUserLoginsByCode(String customerCode) {
		String sql =" select GROUP_CONCAT(CONCAT_WS(',',ranges,business_manager)) from customer_perssion  where customer_code='"+customerCode+"' ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return (String) sqlQuery.uniqueResult();
	}
	

	public Map<String, String> initSelect(String customerCode) {
		String themeMemberKeyIds = "";
		String themeMemberKeys = "";
		String themeMemberIds = "";
		String themeMembers = "";
		Map<String, String> returnValue = new HashMap<String, String>();
		CustomerPerssion customerPerssion = findUniqueBy("customerCode", customerCode);
		if(customerPerssion!=null){
			themeMemberKeys = userDao.getUserName(customerPerssion.getBusinessManager());
			if(themeMemberKeys!=""){
				themeMemberKeyIds = customerPerssion.getBusinessManager();
			}else{
				themeMemberKeys = "";
			}
			if(StringUtils.isNotBlank(customerPerssion.getRanges())){
				String[] params = customerPerssion.getRanges().split(",");
				for(String param : params){
					String name = userDao.getUserName(param);
					if(name!=""){
						if(themeMemberIds!=""){
							themeMemberIds = themeMemberIds +","+param;
							themeMembers = themeMembers +","+name;
						}else{
							themeMemberIds = param;
							themeMembers = name;
						}
					}
				}
			}
			
		}
		returnValue.put("themeMemberIds", themeMemberIds);
		returnValue.put("themeMembers", themeMembers);
		returnValue.put("themeMemberKeyIds", themeMemberKeyIds);
		returnValue.put("themeMemberKeys", themeMemberKeys);
		return returnValue;
	}

}
