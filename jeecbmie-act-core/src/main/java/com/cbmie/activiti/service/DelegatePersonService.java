package com.cbmie.activiti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.dao.DelegatePersonDao;
import com.cbmie.activiti.entity.DelegatePerson;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
@Service
@Transactional
public class DelegatePersonService extends BaseService<DelegatePerson, Long> {

	@Autowired
	private DelegatePersonDao personDao;
	@Override
	public HibernateDao<DelegatePerson, Long> getEntityDao() {
		return personDao;
	}

	public boolean checkLegal(DelegatePerson delegatePerson){
		return personDao.checkLegal(delegatePerson);
	}
	
}
