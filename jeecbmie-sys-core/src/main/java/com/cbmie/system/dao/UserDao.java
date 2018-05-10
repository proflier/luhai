package com.cbmie.system.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.system.entity.User;


/**
 * 用户DAO
 */
@Repository
public class UserDao extends HibernateDao<User, Integer>{
	 
	
	@SuppressWarnings("unchecked")
	public List<User> getSupplier(String num) {
		String sql = "select * from user where id in (select user_id from user_role where role_id=?);";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(User.class);
		sqlQuery.setParameter(0, num);
		List<User> list = sqlQuery.list();
		return list;
	}
	
	public List<User> getUsersByIds(Integer[] ids){
		String sql = "select * from user a where a.id in (:ids)";
		List<User> list = getSession().createSQLQuery(sql).addEntity(User.class).setParameterList("ids", ids).list();
		return list;
	}
	
	public List<User> getUsersByStatus(Integer status){
		String sql = "select * from user a where a.login_status = :status";
		List<User> list = getSession().createSQLQuery(sql).addEntity(User.class).setParameter("status", status).list();
		return list;
	}
	
	public List<User> getUsersByLoginNames(String[] loginNames){
		String sql = "select * from user a where a.login_Name in (:loginNames)";
		List<User> list = getSession().createSQLQuery(sql).addEntity(User.class).setParameterList("loginNames", loginNames).list();
		return list;
	}
	
	public User getUserByLoginName(String loginName){
		User user = null;
		String sql = "select * from user a where a.login_Name=:loginName";
		List<User> list = createSQLQuery(sql).addEntity(User.class).setParameter("loginName", loginName).list();
		if(list!=null && list.size()>0){
			user = list.get(0);
		}
		return user;
	}

	public void updateImg(User user) {
		String sql = "UPDATE user SET img =? WHERE id = ?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(User.class);
		sqlQuery.setParameter(0, user.getImg());
		sqlQuery.setParameter(1, user.getId());
		sqlQuery.executeUpdate();
	}
	
	public List<Integer> getAllChild(Integer orgId){
		List<Integer> returnValue = new ArrayList<Integer>();
		returnValue.add(orgId);
		String sql  = " select id from organization where organization.pid = "+orgId;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		List<Integer> queryList = sqlQuery.list();
		if(sqlQuery.list().size()>0){
			returnValue.addAll(queryList);
			for(Integer i : queryList){
				returnValue.addAll(getAllChild(i));
			}
		}
		return returnValue;
	}

	public void findEntityList(Page<User> page, Map<String, Object> params) {
		String sql = " select * from user where 1=1 ";
		if(params.containsKey("name")){
			String name = params.get("name").toString();
			sql = sql+" and user.name like '%"+name+"%' ";
		}
		if(params.containsKey("phone")){
			String phone = params.get("phone").toString();
			sql = sql+" and user.phone like '%"+phone+"%' ";
		}
		if(params.containsKey("loginStatus")){
			Integer loginStatus = Integer.valueOf( params.get("loginStatus").toString());
			sql = sql+" and user.login_status = "+loginStatus+" ";
		}
		if(params.containsKey("orgId")){
			Integer orgId = Integer.valueOf( params.get("orgId").toString());
			List<Integer> orgList = getAllChild(orgId);
			sql = sql+ "and  user.org_id in ( "+StringUtils.join(orgList.toArray(), ",")+") ";
			
		}
		page.setTotalCount(this.createSQLQuery(sql).addEntity(User.class).list().size());
		List<User> list = this.createSQLQuery(sql).addEntity(User.class).setFirstResult(page.getFirst() - 1).setMaxResults(page.getPageSize()).list();
		page.setResult(list);
	}

	public List<Long> getIdRelation4Affi(String loginName) {
		String sql = " select w.id from customer_perssion c join wood_affi_base_info w on c.customer_code = w.customer_code where c.ranges like '%"+loginName+"%' ";
		List<Long > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}
	
	public List<String> getCodeRelation4Affi(String loginName) {
		String sql = " select w.customer_code from customer_perssion c join wood_affi_base_info w on c.customer_code = w.customer_code where c.ranges like '%"+loginName+"%' ";
		List<String > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}

	public List<User> getUsersByNameLike(String userName) {
		String sql = "select * from user a where a.name like '%"+userName+"%'";
		List<User> list = getSession().createSQLQuery(sql).addEntity(User.class).list();
		if(list!=null){
			return list;
		}else{
			return null;
		}
		
	}
	
	public String getUserName (String loginName){
		String sql = " select name from user  where LOGIN_NAME ='"+loginName+"' and login_status='1' ";
		String  returnValue = (String) getSession().createSQLQuery(sql).uniqueResult();
		if(returnValue!=null){
			return returnValue;
		}
		return "";
	}

	public List<Long> getIdRelation4Purchase(String loginName) {
		String sql = " select id  from lh_purchase_contract where rel_login_names like '%"+loginName+"%' ";
		List<Long > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}

	public List<Long> getCurrentRelation4Sale(String loginName) {
		String sql = " select id  from LH_SALE_CONTRACT where rel_login_names like '%"+loginName+"%' ";
		List<Long > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}

	public List<Long> getCurrentRelation4OrderShip(String loginName) {
		String sql = " select id  from LH_ORDERSHIP_CONTRACT where rel_login_names like '%"+loginName+"%' ";
		List<Long > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}

	public List<Long> getCurrentRelation4Insurance(String loginName) {
		String sql = " select id  from LH_INSURANCE_CONTRACT where rel_login_names like '%"+loginName+"%' ";
		List<Long > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}

	public List<Long> getCurrentRelation4Highway(String loginName) {
		String sql = " select id  from LH_HIGHWAY_CONTRACT where rel_login_names like '%"+loginName+"%' ";
		List<Long > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}

	public List<Long> getCurrentRelation4Railway(String loginName) {
		String sql = " select id  from LH_RAILWAY_CONTRACT where rel_login_names like '%"+loginName+"%' ";
		List<Long > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}

	public List<Long> getCurrentRelation4Port(String loginName) {
		String sql = " select id  from LH_LOGISTIC_PORTCONTRACT where rel_login_names like '%"+loginName+"%' ";
		List<Long > returnValue = getSession().createSQLQuery(sql).list();
		return returnValue;
	}

}
