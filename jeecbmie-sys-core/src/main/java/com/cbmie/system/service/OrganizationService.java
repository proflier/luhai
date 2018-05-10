package com.cbmie.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.system.dao.OrganizationDao;
import com.cbmie.system.entity.DictMain;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.OrgUserUtils;

/**
 * 区域service
 */
@Service
@Transactional(readOnly=true)
public class OrganizationService extends BaseService<Organization, Integer>{
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Override
	public HibernateDao<Organization, Integer> getEntityDao() {
		return organizationDao;
	}
	@Transactional(readOnly=false)
	public void save(final Organization entity) {
		super.save(entity);
		EhCacheUtils.remove(OrgUserUtils.ORGUSERBEAN);
	}
	
	public void update(final Organization entity){
		super.update(entity);
		EhCacheUtils.remove(OrgUserUtils.ORGUSERBEAN);
	}
	
	public List<Organization> getAllByTree(){
		List<Organization> roots = organizationDao.getRoots();
		getAllSons(roots);
		return roots;
	}
	public void getAllSons(List<Organization> roots){
		for(Organization org : roots){
			List<Organization> sons = organizationDao.getAllByPid(org.getId());
			if(sons!=null && sons.size()>0){
				org.getChildren().addAll(sons);
				getAllSons(sons);
			}
		}
	}
	
	public String getOrgNameByIds(String ids){
		return organizationDao.getOrgNameByIds(ids);
	}
	
	public String getOrgPrefix(Integer userId) {
		return organizationDao.getOrgPrefix(userId);
	}
	public List<User> getUsersByOrg(Integer id) {
		return organizationDao.getUsersByOrg(id);
	}
}


