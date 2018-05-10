package com.cbmie.genMac.financial.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.financial.entity.PayTaxes;

/**
 * 交税DAO
 */
@Repository
public class PayTaxesDao extends HibernateDao<PayTaxes, Long>{
	
	public PayTaxes findByNo(PayTaxes payTaxes){
		Criteria criteria = getSession().createCriteria(PayTaxes.class);
		if (payTaxes.getId() != null) {
			criteria.add(Restrictions.ne("id", payTaxes.getId()));
		}
		criteria.add(Restrictions.eq("taxNo", payTaxes.getTaxNo()));
		return (PayTaxes)criteria.uniqueResult();
	}
	
}
