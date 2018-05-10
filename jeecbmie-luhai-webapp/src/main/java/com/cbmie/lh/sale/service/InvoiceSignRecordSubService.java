package com.cbmie.lh.sale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.InvoiceSignRecordSubDao;
import com.cbmie.lh.sale.entity.InvoiceSignRecordSub;
@Service
public class InvoiceSignRecordSubService extends BaseService<InvoiceSignRecordSub, Long> {

	@Autowired
	private InvoiceSignRecordSubDao signRecordSubDao;
	@Override
	public HibernateDao<InvoiceSignRecordSub, Long> getEntityDao() {
		return signRecordSubDao;
	}

}
