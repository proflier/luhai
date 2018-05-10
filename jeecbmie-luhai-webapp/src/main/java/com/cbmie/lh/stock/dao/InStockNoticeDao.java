package com.cbmie.lh.stock.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.stock.entity.InStockNotice;

/**
 * 入库通知DAO
 */
@Repository
public class InStockNoticeDao extends HibernateDao<InStockNotice, Long>{

	public InStockNotice findByNo(InStockNotice inStockNotice) {
		Criteria criteria = getSession().createCriteria(InStockNotice.class);
		if (inStockNotice.getId() != null) {
			criteria.add(Restrictions.ne("id", inStockNotice.getId()));
		}
		criteria.add(Restrictions.eq("noticeNo", inStockNotice.getNoticeNo()));
		return (InStockNotice)criteria.uniqueResult();
	}

	
}
