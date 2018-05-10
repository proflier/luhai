package com.cbmie.lh.permission.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.permission.entity.BusinessPerssion;
import com.cbmie.system.entity.User;


/**
 * 客户相关用户权限
 */
@Repository
public class BusinessPerssionDao extends HibernateDao<BusinessPerssion, Long>{


	public Map<String, String> getUserNamesString(String[] params) {
		Map<String, String> returnValue = new HashMap<String, String>();
		String themeMemberIds ="";
		String themeMembers ="";
		String sql = " select * from  user  where user.login_name in(:params) and user.login_status='1' ";
		SQLQuery query = this.createSQLQuery(sql);
		query.setParameterList("params", params);
		query.addEntity(User.class);
		List<User> userList = query.list();
		if(userList.size()>0){
			for(User user : userList){
				if(themeMemberIds!=""){
					themeMemberIds = themeMemberIds +","+user.getLoginName();
					themeMembers = themeMembers +","+user.getName();
				}else{
					themeMemberIds = user.getLoginName();
					themeMembers = user.getName();
				}
				
			}
		}
		returnValue.put("themeMemberIds", themeMemberIds);
		returnValue.put("themeMembers", themeMembers);
		return returnValue;
	}

	public List<BusinessPerssion> findByBusinessCode(String businessId, String businessCode) {
		String sql = " select * from LH_BUSINESS_PERSSION where BUSINESS_CODE ='"+businessCode+"' and BUSINESS_ID='"+businessId+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(BusinessPerssion.class);
		return sqlQuery.list();
	}
	
	public List<String> findLoginIdBy(String businessId, String businessCode) {
		String sql = " select Distinct LOGIN_ID from LH_BUSINESS_PERSSION where BUSINESS_CODE ='"+businessCode+"' and BUSINESS_ID='"+businessId+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	public List<String> findLoginNameBy(String businessId, String businessCode) {
		String sql = " select Distinct user.name from LH_BUSINESS_PERSSION join user on user.id = LH_BUSINESS_PERSSION.login_id  where BUSINESS_CODE ='"+businessCode+"' and BUSINESS_ID='"+businessId+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	public List<String> findVisible(String businessFlag, Integer id) {
		String sql = " select Distinct business_code from LH_BUSINESS_PERSSION where BUSINESS_FLAG ='"+businessFlag+"' and BUSINESS_ID='"+id+"'  ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}


}
