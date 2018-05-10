package com.cbmie.lh.feedback.dao;

import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.lh.feedback.entity.FeedbackTheme;
@Repository
public class FeedbackThemeDao extends HibernateDao<FeedbackTheme, Long> {

	public void getList(Page<FeedbackTheme> page,Map<String,Object> queryParam,Integer userId){
		String sql = "select a.* from LH_FEEDBACK_THEME a "
				+ " where (a.feedback_Public='1' or a.userId=:userId or a.duty_User=:userId "
				+ " or exists(select 1 from LH_THEME_MEMBER c "
				+ " where a.id=c.theme_Id and c.USER_ID=:userId and a.feedback_Public='0'))";
		if(queryParam.containsKey("title")){
			sql += " and a.title like '%" + queryParam.get("title").toString() + "%'";
		}
		sql += " order by a.id desc ";
		Query query = this.createSQLQuery(sql).addEntity(FeedbackTheme.class).setParameter("userId", userId);
		page.setTotalCount(query.list().size());
		query.setFirstResult((page.getPageNo()-1)*page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setResult(query.list());
	}
}
