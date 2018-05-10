package com.cbmie.common.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.GlobalParam;
import com.cbmie.system.utils.UserUtil;


/**
 * 基础service 所有service继承该类
 * 提供基础的实体操作方法
 * 使用HibernateDao<T,PK>进行业务对象的CRUD操作,子类需重载getEntityDao()函数提供该DAO.
 * @param <T>
 * @param <PK>
 */
public abstract class BaseService<T,PK extends Serializable > {
	
	/**
	 * 子类需要实现该方法，提供注入的dao
	 * @return
	 */
	public abstract HibernateDao<T, PK> getEntityDao();
	
	
	@Transactional(readOnly = true)
	public T get(final PK id) {
		return getEntityDao().find(id);
	}
	
	@Transactional(readOnly = true)
	public T getNoLoad(final PK id) {
		return getEntityDao().get(id);
	}

	@Transactional(readOnly = false)
	public void save(final T entity) {
		if(BaseEntity.class.isAssignableFrom(entity.getClass())){
			BaseEntity base = (BaseEntity)entity;
			if(base.getId()==null){
				User currentUser = UserUtil.getCurrentUser();
				base.setUserId(currentUser.getId().toString());
				base.setDeptId(currentUser.getOrganization().getId());
				base.setCompanyId(getCompany(currentUser.getOrganization()).getId());
			}
		}
		getEntityDao().save(entity);
	}
	
	@Transactional(readOnly = false)
	public void update(final T entity){
		getEntityDao().save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(final T entity){
		getEntityDao().delete(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(final PK id){
		getEntityDao().delete(id);
	}
	
	@Transactional(readOnly = true)
	public List<T> getAll(){
		return getEntityDao().findAll();
	}
	
	@Transactional(readOnly = true)
	public List<T> getAll(Boolean isCache){
		return getEntityDao().findAll(isCache);
	}
	
	
	public List<T> search(final List<PropertyFilter> filters){
		return getEntityDao().find(filters);
	}
	
	@Transactional(readOnly = true)
	public Page<T> sqlSearch(final Page<T> page, String sql, final Map<String, Object> filterMap) {
		return getEntityDao().sqlSearch(page, sql, filterMap);
	}
	
	@Transactional(readOnly = true)
	public Page<T> search(final Page<T> page, final List<PropertyFilter> filters) {
		Page<T> result = null;
		try{
			Type type = getClass().getGenericSuperclass();  
			Type trueType = ((ParameterizedType)type).getActualTypeArguments()[0];  
			
			/**目前先去除公司、部门、个人权限2017/7/11 linxiaopeng***/
//			filter(filters, trueType);
			result = getEntityDao().findPage(page, filters);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	private void filter(final List<PropertyFilter> filters, Type trueType) {
		if(BaseEntity.class.isAssignableFrom((Class<T>)trueType)){
			User user = UserUtil.getCurrentUser();
			//获取权限级别
			String dataRange = getDataRange(user);
			Organization dept = user.getOrganization();
			String ranges = getDataRanges(user);
			if(GlobalParam.ORG_TYPE_DEPT.equals(dataRange)){
				//获取当前用户角色相关部门（全选公司所有部门即代表整个公司）
				if(dept!=null&&ranges!=null){
					PropertyFilter comFilter = new PropertyFilter("INP_deptId",ranges);
					filters.add(comFilter);
				}
			}else if(GlobalParam.ORG_TYPE_SELF.equals(dataRange)){
				//先将用户加入权限(用户变更部门之后信息可见)
				PropertyFilter userFilter = new PropertyFilter("EQS_userId",user.getId().toString());
				filters.add(userFilter);
				//公司加入权限
				//获取当前用户角色相关部门
				if(dept!=null&&ranges!=null){
					PropertyFilter comFilter = new PropertyFilter("INP_deptId",ranges);
					filters.add(comFilter);
				}
			}
		}
	}
	
	@Transactional(readOnly = true)
	public Page<T> searchNoPermission(final Page<T> page, final List<PropertyFilter> filters) {
		Page<T> result = null;
		try{
			result = getEntityDao().findPage(page, filters);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 数据权限简单实现
	 * 目前只限制本公司、本部门、本人员
	 * @param params 预留,为以后做准备
	 * **/
	@Transactional(readOnly = true)
	public Page<T> searchByPermission(final Page<T> page, final List<PropertyFilter> filters,Map params) {
		User user = UserUtil.getCurrentUser();
		String dataRange = getDataRange(user);
		if(GlobalParam.ORG_TYPE_COMPANY.equals(dataRange)){
			Organization com = getCompany(user.getOrganization());
			if(com!=null){
				PropertyFilter comFilter = new PropertyFilter("EQI_companyId",com.getId().toString());
				filters.add(comFilter);
			}else{
				PropertyFilter comFilter = new PropertyFilter("EQS_userId",user.getId().toString());
				filters.add(comFilter);
			}
		}else if(GlobalParam.ORG_TYPE_DEPT.equals(dataRange)){
			Organization dept = user.getOrganization();
			if(dept!=null){
				PropertyFilter deptFilter = new PropertyFilter("EQI_deptId",dept.getId().toString());
				filters.add(deptFilter);
			}else{
				PropertyFilter deptFilter = new PropertyFilter("EQS_userId",user.getId().toString());
				filters.add(deptFilter);
			}
		}else{
			PropertyFilter userFilter = new PropertyFilter("EQS_userId",user.getId().toString());
			filters.add(userFilter);

		}
		
//		filters.add(dept);
		return getEntityDao().findPage(page, filters);
	}
	
	
	@Transactional(readOnly = true)
	public Page<T> search(final Page<T> page, final String hql,final Map<String, ?> values) {
		return getEntityDao().findPage(page, hql, values);
	}
	
	@Transactional(readOnly = true)
	public Page<T> search(final Page<T> page, final String hql,final Object... values) {
		return getEntityDao().findPage(page, hql, values);
	}
	public String getDataRange(User user) {
		String result = null;
		String sql = "select c.dataRange from user a "
				+ " join user_role b on a.id=b.USER_ID "
				+ " join role c on b.ROLE_ID=c.id "
				+ " where a.id=:id and c.dataRange is not null "
				+ " order by c.dataRange asc ";
		try{
			List<Object> list = getEntityDao().createSQLQuery(sql).setParameter("id", user.getId()).list();
			if(list!=null && list.size()>0){
				result = list.get(0).toString();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
				return result;
	}
	
	private String getDataRanges(User user) {
		String result = null;
		String sql = "select c.ranges from user a "
				+ " join user_role b on a.id=b.USER_ID "
				+ " join role c on b.ROLE_ID=c.id "
				+ " where a.id=:id and c.ranges is not null ";
		try{
			List<Object> list = getEntityDao().createSQLQuery(sql).setParameter("id", user.getId()).list();
			if(list!=null && list.size()>0){
				result = list.get(0).toString();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
				return result;
	}
	
	
	public Organization getCompany(Organization org){
		if(org==null) return null;
		if(GlobalParam.ORG_TYPE_COMPANY.equals(org.getOrgType())){
			return org;
		}else{
			if(org.getPid()!=null){
				Query query = getEntityDao().createQuery("from Organization a where a.id=:id");
				List<Organization> list = query.setParameter("id", org.getPid()).list();
				if(list!=null && list.size()>0){
					org = list.get(0);
				}else{
					org=null;
				}
				return getCompany(org);
			}else{
				return null;
			}
		}
	}
	
	public List<PropertyFilter> setCurrentPermission(List<PropertyFilter> filters, String propertyName) {
		User currentUser = UserUtil.getCurrentUser();
		if(currentUser.getId()==1||currentUser.getId()==106){
		}else{
			PropertyFilter comFilter = null;
			comFilter = new PropertyFilter(propertyName,currentUser.getLoginName());
			filters.add(comFilter);
		}
		return filters;
	}
	
	public List<PropertyFilter> setCurrentPermission(List<PropertyFilter> filters, String propertyName, String propertyValue) {
		User currentUser = UserUtil.getCurrentUser();
		if(currentUser.getId()!=1){
			PropertyFilter comFilter = null;
			if(StringUtils.isNotBlank(propertyValue)&&StringUtils.isNotBlank(propertyName)){
				comFilter = new PropertyFilter(propertyName,propertyValue);
			}else{
				comFilter = new PropertyFilter(propertyName,"-1");
			}
			filters.add(comFilter);
		}
		return filters;
	}
}
