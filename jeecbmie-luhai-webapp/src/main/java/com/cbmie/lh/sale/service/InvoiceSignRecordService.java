package com.cbmie.lh.sale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.InvoiceSignRecordDao;
import com.cbmie.lh.sale.entity.InvoiceSignRecord;
@Service
public class InvoiceSignRecordService extends BaseService<InvoiceSignRecord, Long> {

	@Autowired
	private InvoiceSignRecordDao signRecordDao;
	@Override
	public HibernateDao<InvoiceSignRecord, Long> getEntityDao() {
		return signRecordDao;
	}

}
