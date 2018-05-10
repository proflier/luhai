package com.cbmie.lh.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.feedback.dao.FeedbackFileDao;
import com.cbmie.lh.feedback.entity.FeedbackFile;
@Service
public class FeedbackFileService extends BaseService<FeedbackFile, Long> {
	@Autowired
	private FeedbackFileDao fileDao;
	@Override
	public HibernateDao<FeedbackFile, Long> getEntityDao() {
		return fileDao;
	}

	public List<FeedbackFile> getFilesByThemeId(Long themeId){
		return fileDao.getFilesByThemeId(themeId);
	}
}
