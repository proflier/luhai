package com.cbmie.lh.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.financial.dao.ReceiptDao;
import com.cbmie.lh.financial.entity.Receipt;

/**
 * 预收票service
 */
@Service
@Transactional
public class ReceiptService extends BaseService<Receipt, Long> {

	@Autowired
	private ReceiptDao receiptDao;

	@Override
	public HibernateDao<Receipt, Long> getEntityDao() {
		return receiptDao;
	}

	public Receipt findByNo(Receipt receipt) {
		return receiptDao.findByNo(receipt);
	}

}
