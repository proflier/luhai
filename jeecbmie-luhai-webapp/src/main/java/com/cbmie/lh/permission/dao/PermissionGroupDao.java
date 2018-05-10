package com.cbmie.lh.permission.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.permission.entity.PermissionGroup;
@Repository
public class PermissionGroupDao extends HibernateDao<PermissionGroup, Long> {
	public boolean checkNameUique(PermissionGroup permissionGroup){
		boolean result = true;
		String hql = "from PermissionGroup a where a.groupName=:name and a.createrNo=:createrNo";
		if(permissionGroup.getId() != null){
			hql += " and a.id<>"+permissionGroup.getId();
		}
		List list = this.createQuery(hql).setParameter("name", permissionGroup.getGroupName()).setParameter("createrNo", permissionGroup.getCreaterNo()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
	
	public List<PermissionGroup> getRelPermissionGroups(){
		String sql = "from PermissionGroup a order by a.id asc";
		List<PermissionGroup> groups = this.createQuery(sql).list();
		return groups;
	}
	
	public List<Map<String,Object>> getGroupBySelf(String loginName){
		String sql = "select concat('G_',e.id) id, e.group_Name name,'G_0' pid,'G' flag from LH_PERMISSION_GROUP e where e.createrNo=:loginName "
				+ " union "
				+ " select a.LOGIN_NAME id,a.name name,concat('G_',c.id) pid,'U' flag from user a join LH_PERMISSION_MEMBER b on a.id=b.user_id "
				+ " join LH_PERMISSION_GROUP c on c.id=b.permission_group_id where c.createrNo=:loginName";
		SQLQuery query = this.createSQLQuery(sql);
		query.setParameter("loginName", loginName);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String,Object>> result=query.list();
		return result;
	}
}
