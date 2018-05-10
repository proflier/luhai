package com.cbmie.lh.feedback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.feedback.dao.DiscussMemberDao;
import com.cbmie.lh.feedback.entity.DiscussMember;
@Service
public class DiscussMemberService extends BaseService<DiscussMember, Long> {

	@Autowired
	private DiscussMemberDao memberDao;
	@Override
	public HibernateDao<DiscussMember, Long> getEntityDao() {
		return memberDao;
	}

}
