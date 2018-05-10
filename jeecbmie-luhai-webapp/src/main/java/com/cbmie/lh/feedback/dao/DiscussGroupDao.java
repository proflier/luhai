package com.cbmie.lh.feedback.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.feedback.entity.DiscussGroup;
@Repository
public class DiscussGroupDao extends HibernateDao<DiscussGroup, Long> {
	public boolean checkNameUique(DiscussGroup discussGroup){
		boolean result = true;
		String hql = "from DiscussGroup a where a.groupName=:name and a.createrNo=:createrNo";
		if(discussGroup.getId() != null){
			hql += " and a.id<>"+discussGroup.getId();
		}
		List list = this.createQuery(hql).setParameter("name", discussGroup.getGroupName()).setParameter("createrNo", discussGroup.getCreaterNo()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
	
	public List<DiscussGroup> getRelDiscussGroups(){
		String sql = "from DiscussGroup a order by a.id asc";
		List<DiscussGroup> groups = this.createQuery(sql).list();
		return groups;
	}
	
	public List<Map<String,Object>> getGroupBySelf(String loginName){
		String sql = "select concat('G_',e.id) id, e.group_Name name,'G_0' pid,'G' flag from LH_DISCUSS_GROUP e where e.createrNo=:loginName "
				+ " union "
				+ " select a.id id,a.name name,concat('G_',c.id) pid,'U' flag from user a join LH_DISCUSS_MEMBER b on a.id=b.user_id "
				+ " join LH_DISCUSS_GROUP c on c.id=b.discuss_Group_Id where c.createrNo=:loginName";
		SQLQuery query = this.createSQLQuery(sql);
		query.setParameter("loginName", loginName);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String,Object>> result=query.list();
		return result;
	}
}
