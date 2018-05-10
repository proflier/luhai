package com.cbmie.lh.credit.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.credit.entity.PayApply;

/**
 * 付款申请DAO
 */
@Repository
public class PayApplyDao extends HibernateDao<PayApply, Long>{

	/**
	 * @param payApply
	 * @return
	 */
	public PayApply findByNo(PayApply payApply) {
		Criteria criteria = getSession().createCriteria(PayApply.class);
		if (payApply.getId() != null) {
			criteria.add(Restrictions.ne("id", payApply.getId()));
		}
		criteria.add(Restrictions.eq("payApplyNo", payApply.getPayApplyNo()));
		criteria.add(Restrictions.eq("changeState", "1"));
		return (PayApply)criteria.uniqueResult();
	}
	
}
