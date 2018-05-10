package com.cbmie.lh.feedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.feedback.dao.FeedbackContentDao;
import com.cbmie.lh.feedback.entity.FeedbackContent;
@Service
public class FeedbackContentService extends BaseService<FeedbackContent, Long> {
	@Autowired
	private FeedbackContentDao contentDao;
	@Override
	public HibernateDao<FeedbackContent, Long> getEntityDao() {
		return contentDao;
	}

	public boolean checkKeyPeaple(Long themeId){
		return contentDao.checkKeyPeaple(themeId);
	}
}
