package com.cbmie.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.InformDao;
import com.cbmie.system.entity.Inform;

/**
 * 系统通知service
 */
@Service
@Transactional(readOnly=true)
public class InformService extends BaseService<Inform, Integer> {
	
	@Autowired
	private InformDao informDao;

	@Override
	public HibernateDao<Inform, Integer> getEntityDao() {
		return informDao;
	}
	
	public Inform findBusinessInform(String className, Long businessId){
		return informDao.findBusinessInform(className, businessId);
	}
	
}
