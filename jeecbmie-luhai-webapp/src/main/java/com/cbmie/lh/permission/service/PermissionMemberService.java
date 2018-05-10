package com.cbmie.lh.permission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.permission.dao.PermissionMemberDao;
import com.cbmie.lh.permission.entity.PermissionMember;
@Service
public class PermissionMemberService extends BaseService<PermissionMember, Long> {

	@Autowired
	private PermissionMemberDao memberDao;
	@Override
	public HibernateDao<PermissionMember, Long> getEntityDao() {
		return memberDao;
	}

}
