package com.cbmie.genMac.logistics.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.logistics.entity.InvoiceReg;

/**
 * 到单登记DAO
 */
@Repository
public class InvoiceRegDao extends HibernateDao<InvoiceReg, Long> {
	
	public InvoiceReg findByNo(InvoiceReg invoiceReg){
		Criteria criteria = getSession().createCriteria(InvoiceReg.class);
		if (invoiceReg.getId() != null) {
			criteria.add(Restrictions.ne("id", invoiceReg.getId()));
		}
		criteria.add(Restrictions.eq("invoiceNo", invoiceReg.getInvoiceNo()));
		return (InvoiceReg)criteria.uniqueResult();
	}
	
}
