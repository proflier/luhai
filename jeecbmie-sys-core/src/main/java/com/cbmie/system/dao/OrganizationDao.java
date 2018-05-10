package com.cbmie.system.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;


/**
 * 机构DAO
 */
@Repository
public class OrganizationDao extends HibernateDao<Organization, Integer>{

	public List<Organization> getAllByPid(Integer id){
		String sql = "select * from Organization a where a.pid=:pid order by  a.org_Sort asc";
		return this.createSQLQuery(sql).addEntity(Organization.class).setParameter("pid", id).list();
	}
	public List<Organization> getRoots(){
		String sql = "select * from Organization a where a.pid is null order by  a.org_Sort asc";
		return this.createSQLQuery(sql).addEntity(Organization.class).list();
	}
	public String getOrgNameByIds(String ids){
		String result = "";
		String sql = "select a.org_name from Organization a where a.id in ("+ids+")";
		List<Object> list = this.createSQLQuery(sql).list();
		for(Object obj : list){
			result+=obj.toString()+",";
		}
		if(result.length()>0){
			result = result.substring(0, result.length()-1);
		}
		return result;
	}
	public String getOrgPrefix(Integer userId) {
		String returnValue="";
		String sql = " select org_prefix from organization join user on organization.id = user.org_id where user.id="+userId+" ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		returnValue = (String) sqlQuery.uniqueResult();
		if(StringUtils.isNotBlank(returnValue)){
			return returnValue;
		}else{
			return "";
		}
	}
	public List<User> getUsersByOrg(Integer id) {
		String sql = " select * from user join organization on user.org_id=organization.id where organization.pid="+id+" or organization.id="+id ;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(User.class);
		if(sqlQuery!=null&&sqlQuery.list().size()>0){
			return sqlQuery.list();
		}
		return null;
	}
}
