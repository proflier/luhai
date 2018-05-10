package com.cbmie.genMac.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.logistics.dao.InvoiceRegDao;
import com.cbmie.genMac.logistics.entity.InvoiceReg;

/**
 * 到单登记service
 */
@Service
@Transactional
public class InvoiceRegService extends BaseService<InvoiceReg, Long> {

	@Autowired
	private InvoiceRegDao invoiceRegDao;

	@Override
	public HibernateDao<InvoiceReg, Long> getEntityDao() {
		return invoiceRegDao;
	}
	
	public InvoiceReg findByNo(InvoiceReg invoiceReg){
		return invoiceRegDao.findByNo(invoiceReg);
	}
	
}
