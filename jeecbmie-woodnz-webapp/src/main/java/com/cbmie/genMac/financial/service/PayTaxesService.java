package com.cbmie.genMac.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.PayTaxesDao;
import com.cbmie.genMac.financial.entity.PayTaxes;

/**
 * 交税service
 */
@Service
@Transactional
public class PayTaxesService extends BaseService<PayTaxes, Long> {
	
	@Autowired
	private PayTaxesDao payTaxesDao;

	@Override
	public HibernateDao<PayTaxes, Long> getEntityDao() {
		return payTaxesDao;
	}
	
	public PayTaxes findByNo(PayTaxes payTaxes){
		return payTaxesDao.findByNo(payTaxes);
	}
	
}
