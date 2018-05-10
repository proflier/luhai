package com.cbmie.system.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.system.entity.User;
import com.cbmie.system.entity.UserRole;


/**
 * 用户角色DAO
 */
@Repository
public class UserRoleDao extends HibernateDao<UserRole, Integer>{

	/**
	 * 删除用户角色
	 * @param userId
	 * @param roleId
	 */
	public void deleteUR(Integer userId,Integer roleId){
		String hql="delete UserRole ur where ur.user.id=?0 and ur.role.id=?1";
		batchExecute(hql, userId,roleId);
	}
	
	/**
	 * 查询用户拥有的角色id集合
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findRoleIds(Integer userId){
		String hql="select ur.role.id from UserRole ur where ur.user.id=?0";
		Query query= createQuery(hql, userId);
		return query.list();
	}
	
	public List<User> getUsersByRoleName(String roleName){
		String hql = "select ur.user from UserRole ur where ur.role.name=?0 ";
		List<User> users = this.createQuery(hql, roleName).list();
		return users;
	}
	
	public List<User> getUsersByRoleCode(String roleCode){
		String hql = "select ur.user from UserRole ur where ur.role.roleCode=?0 ";
		List<User> users = this.createQuery(hql, roleCode).list();
		return users;
	}

	public List<User> getUserInRole(Integer id) {
		String hql = "select ur.user from UserRole ur where ur.role.id=?0 ";
		List<User> users = this.createQuery(hql, id).list();
		return users;
	}

	public void deleteFromRole(Integer roleId, Integer userId) {
		String hql="delete  from UserRole ur where ur.role.id=?0 and ur.user.id=?1";
		Query query= createQuery(hql, roleId,userId);
		query.executeUpdate();
	}

	public List<Integer> searchUserIds(Integer roleId) {
		String hql = "select ur.user.id from UserRole ur where ur.role.id=?0 ";
		Query query= createQuery(hql, roleId);
		return query.list();
	}
	
	public void getUserRoleByRoleId(Page<UserRole> page,Integer roleId){
		String hql = "select ur from UserRole ur  left join fetch ur.user user left join fetch ur.role role where ur.role.id=?0 ";
		Query query= createQuery(hql, roleId);
		page.setTotalCount(query.list().size());
		page.setResult(query.setFirstResult(page.getFirst()-1).setMaxResults(page.getPageSize()).list());
	}
	
	public Map<String,String> getUsersByRoleDept(String roleCode,Integer deptId){
		String hql = "select t.loginName,t.name from"
				+ "(select ifnull(ur.relation_Depts,u.ORG_ID) deptids,u.login_Name loginName,u.name name,r.role_Code roleCode "
				+ " from User_Role ur join user u on ur.USER_ID=u.id "
				+ " left join role r on r.id=ur.ROLE_ID) t"
				+ " where t.roleCode=:roleCode and t.deptids REGEXP '^([,0-9]*,)*"+deptId+"(,[,0-9]*)*$'";
		List<Object[]> list = this.createSQLQuery(hql).setParameter("roleCode", roleCode).list();
		Map<String,String> map = new HashMap<String,String>();
		for(Object[] obj : list){
			map.put(obj[0].toString(), obj[1].toString());
		}
		return map;
	}
}
