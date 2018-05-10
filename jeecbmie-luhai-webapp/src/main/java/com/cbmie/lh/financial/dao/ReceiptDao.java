package com.cbmie.lh.financial.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.financial.entity.Receipt;

/**
 * 预收票DAO
 */
@Repository
public class ReceiptDao extends HibernateDao<Receipt, Long>{
	
	public Receipt findByNo(Receipt receipt) {
		Criteria criteria = getSession().createCriteria(Receipt.class);
		if (receipt.getId() != null) {
			criteria.add(Restrictions.ne("id", receipt.getId()));
		}
		criteria.add(Restrictions.eq("receiptNo", receipt.getReceiptNo()));
		return (Receipt)criteria.uniqueResult();
	}
	
}
