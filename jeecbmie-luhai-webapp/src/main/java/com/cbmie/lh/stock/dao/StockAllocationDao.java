package com.cbmie.lh.stock.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.stock.entity.StockAllocation;

/**
 * 仓储调拨DAO
 */
@Repository
public class StockAllocationDao extends HibernateDao<StockAllocation, Long>{

		public StockAllocation findByNo(StockAllocation stockAllocation) {
			Criteria criteria = getSession().createCriteria(StockAllocation.class);
			if (stockAllocation.getId() != null) {
				criteria.add(Restrictions.ne("id", stockAllocation.getId()));
			}
			criteria.add(Restrictions.eq("allocationNo", stockAllocation.getAllocationNo()));
			return (StockAllocation)criteria.uniqueResult();
		}
	
}
