package com.cbmie.lh.feedback.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.feedback.dao.DiscussGroupDao;
import com.cbmie.lh.feedback.entity.DiscussGroup;
@Service
public class DiscussGroupService extends BaseService<DiscussGroup, Long> {

	@Autowired
	private DiscussGroupDao groupDao;
	@Override
	public HibernateDao<DiscussGroup, Long> getEntityDao() {
		return groupDao;
	}
	
	public boolean checkNameUique(DiscussGroup discussGroup){
		return groupDao.checkNameUique(discussGroup);
	}

	public List<DiscussGroup> getRelDiscussGroups(){
		List<DiscussGroup> groups = groupDao.getRelDiscussGroups();
		return groups;
	}
	
	public List<Map<String,Object>> getGroupBySelf(String loginName){
		return groupDao.getGroupBySelf(loginName);
	}
}
