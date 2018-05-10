package com.cbmie.genMac.financial.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.financial.entity.PaymentConfirm;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
@Repository
public class PaymentConfirmDao extends HibernateDao<PaymentConfirm, Long> {
	
	public PaymentConfirm findByConfirmNo(PaymentConfirm paymentConfirm){
		Criteria criteria = getSession().createCriteria(PaymentConfirm.class);
		if (paymentConfirm.getId() != null) {
			criteria.add(Restrictions.ne("id", paymentConfirm.getId()));
		}
		criteria.add(Restrictions.eq("confirmNo", paymentConfirm.getConfirmNo()));
		return (PaymentConfirm)criteria.uniqueResult();
	}
}
