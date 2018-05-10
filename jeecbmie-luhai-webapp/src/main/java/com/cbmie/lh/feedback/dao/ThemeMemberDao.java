package com.cbmie.lh.feedback.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.feedback.entity.ThemeMember;
@Repository
public class ThemeMemberDao extends HibernateDao<ThemeMember, Long> {
	public List<Map<String,Object>> getMembersByThemeId(Long themeId){
		String sql = "select a.id id,b.id userId,b.name userName,a.key_Flag keyFlag from LH_THEME_MEMBER a join user b on a.USER_ID=b.id "
				+ " where a.theme_Id=:themeId";
		 SQLQuery query = this.createSQLQuery(sql);
		 query.setParameter("themeId", themeId);
	     query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
	     List<Map<String,Object>> result=query.list();
		return result;
	}
}
