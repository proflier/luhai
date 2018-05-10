package com.cbmie.lh.feedback.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.feedback.dao.ThemeMemberDao;
import com.cbmie.lh.feedback.entity.ThemeMember;
@Service
public class ThemeMemberService extends BaseService<ThemeMember, Long> {
	@Autowired
	private ThemeMemberDao themeMemberDao;
	@Override
	public HibernateDao<ThemeMember, Long> getEntityDao() {
		return themeMemberDao;
	}

	public List<Map<String,Object>> getMembersByThemeId(Long themeId){
		return themeMemberDao.getMembersByThemeId(themeId);
	}
}
