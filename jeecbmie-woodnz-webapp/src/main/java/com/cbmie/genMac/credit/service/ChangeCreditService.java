package com.cbmie.genMac.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.credit.dao.ChangeCreditDao;
import com.cbmie.genMac.credit.entity.ChangeCredit;

/**
 * 改证service
 */
@Service
@Transactional
public class ChangeCreditService extends BaseService<ChangeCredit, Long> {

	@Autowired
	private ChangeCreditDao changeCreditDao;

	@Override
	public HibernateDao<ChangeCredit, Long> getEntityDao() {
		return changeCreditDao;
	}
	
}
