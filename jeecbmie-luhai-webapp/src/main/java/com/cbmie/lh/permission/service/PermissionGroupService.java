package com.cbmie.lh.permission.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.permission.dao.PermissionGroupDao;
import com.cbmie.lh.permission.entity.PermissionGroup;
@Service
public class PermissionGroupService extends BaseService<PermissionGroup, Long> {

	@Autowired
	private PermissionGroupDao groupDao;
	@Override
	public HibernateDao<PermissionGroup, Long> getEntityDao() {
		return groupDao;
	}
	
	public boolean checkNameUique(PermissionGroup permissionGroup){
		return groupDao.checkNameUique(permissionGroup);
	}

	public List<PermissionGroup> getRelPermissionGroups(){
		List<PermissionGroup> groups = groupDao.getRelPermissionGroups();
		return groups;
	}
	
	public List<Map<String,Object>> getGroupBySelf(String loginName){
		return groupDao.getGroupBySelf(loginName);
	}
}
