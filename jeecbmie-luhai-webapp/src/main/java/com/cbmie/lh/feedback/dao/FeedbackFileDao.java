package com.cbmie.lh.feedback.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.feedback.entity.FeedbackFile;
@Repository
public class FeedbackFileDao extends HibernateDao<FeedbackFile, Long> {

	public List<FeedbackFile> getFilesByThemeId(Long themeId){
		String sql = "from FeedbackFile a where a.themeId=:themeId";
		return this.createQuery(sql).setParameter("themeId", themeId).list();
	}
}
