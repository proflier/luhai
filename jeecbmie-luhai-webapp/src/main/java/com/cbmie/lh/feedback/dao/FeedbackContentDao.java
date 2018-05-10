package com.cbmie.lh.feedback.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.feedback.entity.FeedbackContent;
@Repository
public class FeedbackContentDao extends HibernateDao<FeedbackContent, Long> {
	
	public boolean checkKeyPeaple(Long themeId){
		String sql = "select a.* from LH_FEEDBACK_THEME a join LH_THEME_MEMBER b on a.id=b.theme_id "
				+ " where b.key_Flag='1' "
				+ " and not exists"
				+ " (select 1 from LH_FEEDBACK_CONTENT d where d.feedback_Theme_Id=a.id and d.user_Id=b.USER_ID) "
				+ " and a.id=:themeId";
		List list = this.createSQLQuery(sql).setParameter("themeId", themeId).list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
}
