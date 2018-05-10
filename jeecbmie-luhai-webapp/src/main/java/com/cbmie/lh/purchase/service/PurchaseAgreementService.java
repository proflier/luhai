package com.cbmie.lh.purchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.purchase.dao.PurchaseAgreementDao;
import com.cbmie.lh.purchase.entity.PurchaseAgreement;

/**
 * 采购协议service
 */
@Service
@Transactional
public class PurchaseAgreementService extends BaseService<PurchaseAgreement, Long> {

	@Autowired
	private PurchaseAgreementDao purchaseAgreementDao;

	@Override
	public HibernateDao<PurchaseAgreement, Long> getEntityDao() {
		return purchaseAgreementDao;
	}

}
