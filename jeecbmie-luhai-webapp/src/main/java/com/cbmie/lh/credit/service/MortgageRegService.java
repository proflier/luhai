package com.cbmie.lh.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.credit.dao.MortgageRegDao;
import com.cbmie.lh.credit.entity.MortgageReg;

/**
 * 押汇登记service
 */
@Service
@Transactional
public class MortgageRegService extends BaseService<MortgageReg, Long> {
	
	@Autowired
	private MortgageRegDao mortgageRegDao;

	@Override
	public HibernateDao<MortgageReg, Long> getEntityDao() {
		return mortgageRegDao;
	}
	
	
}
