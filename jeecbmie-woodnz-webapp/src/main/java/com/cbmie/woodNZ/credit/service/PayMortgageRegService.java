package com.cbmie.woodNZ.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.credit.dao.PayMortgageRegDao;
import com.cbmie.woodNZ.credit.entity.PayMortgageReg;

/**
 * 付汇登记service
 */
@Service
@Transactional
public class PayMortgageRegService extends BaseService<PayMortgageReg, Long> {
	
	@Autowired
	private PayMortgageRegDao payMortgageRegDao;

	@Override
	public HibernateDao<PayMortgageReg, Long> getEntityDao() {
		return payMortgageRegDao;
	}
	
	
}
