package com.cbmie.lh.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.credit.dao.PayRegDao;
import com.cbmie.lh.credit.entity.PayReg;

/**
 * 付款登记service
 */
@Service
@Transactional
public class PayRegService extends BaseService<PayReg, Long> {
	
	@Autowired
	private PayRegDao payRegDao;

	@Override
	public HibernateDao<PayReg, Long> getEntityDao() {
		return payRegDao;
	}
	
	
}
