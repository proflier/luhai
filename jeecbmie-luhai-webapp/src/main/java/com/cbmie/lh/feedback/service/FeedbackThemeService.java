package com.cbmie.lh.feedback.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.feedback.dao.FeedbackThemeDao;
import com.cbmie.lh.feedback.entity.FeedbackTheme;
@Service
public class FeedbackThemeService extends BaseService<FeedbackTheme, Long> {

	@Autowired
	private FeedbackThemeDao themeDao;
	@Override
	public HibernateDao<FeedbackTheme, Long> getEntityDao() {
		return themeDao;
	}

	public void getList(Page<FeedbackTheme> page,Map<String,Object> queryParam,Integer userId){
		 themeDao.getList(page, queryParam, userId);
	}
}
