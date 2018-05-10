package com.cbmie.lh.sale.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleOffer;

/**
 * 销售报盘dao
 */
@Repository
public class SaleOfferDao extends HibernateDao<SaleOffer, Long>{
	
	public SaleOffer findByNo(SaleOffer saleOffer) {
		Criteria criteria = getSession().createCriteria(SaleOffer.class);
		if (saleOffer.getId() != null) {
			criteria.add(Restrictions.ne("id", saleOffer.getId()));
		}
		criteria.add(Restrictions.eq("offerNo", saleOffer.getOfferNo()));
		return (SaleOffer)criteria.uniqueResult();
	}
	
}
