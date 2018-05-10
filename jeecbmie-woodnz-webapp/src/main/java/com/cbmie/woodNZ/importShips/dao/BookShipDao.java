package com.cbmie.woodNZ.importShips.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.importShips.entity.BookShip;

/**
 *  定船合同审批DAO
 * @author linxiaopeng
 * 2016年7月5日
 */
@Repository
public class BookShipDao extends HibernateDao<BookShip, Long>{
	
	public BookShip findByNo(BookShip bookShip) {
		Criteria criteria = getSession().createCriteria(BookShip.class);
		if (bookShip.getId() != null) {
			criteria.add(Restrictions.ne("id", bookShip.getId()));
		}
		criteria.add(Restrictions.eq("contractNo", bookShip.getContractNo()));
		return (BookShip)criteria.uniqueResult();
	}
	
}
