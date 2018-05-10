package com.cbmie.genMac.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.SaleInvoiceDao;
import com.cbmie.genMac.financial.entity.SaleInvoice;
@Service
@Transactional
public class SaleInvoiceService extends BaseService<SaleInvoice, Long> {

	@Autowired
	private SaleInvoiceDao invoiceDao;
	@Override
	public HibernateDao<SaleInvoice, Long> getEntityDao() {
		return invoiceDao;
	}

}
