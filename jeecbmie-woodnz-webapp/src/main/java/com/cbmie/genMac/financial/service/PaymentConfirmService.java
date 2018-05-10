package com.cbmie.genMac.financial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.financial.dao.PaymentConfirmDao;
import com.cbmie.genMac.financial.entity.PaymentConfirm;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
@Service
@Transactional
public class PaymentConfirmService extends BaseService<PaymentConfirm, Long> {
	@Autowired
	private PaymentConfirmDao confirmDao;
	@Override
	public HibernateDao<PaymentConfirm, Long> getEntityDao() {
		return confirmDao;
	}

	public PaymentConfirm findByConfirmNo(PaymentConfirm paymentConfirm){
		return confirmDao.findByConfirmNo(paymentConfirm);
	}
}
