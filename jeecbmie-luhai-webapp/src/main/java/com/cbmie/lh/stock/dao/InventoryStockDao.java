package com.cbmie.lh.stock.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.stock.entity.InventoryStock;

/**
 * 仓储盘点DAO
 */
@Repository
public class InventoryStockDao extends HibernateDao<InventoryStock, Long>{

		public InventoryStock findByNo(InventoryStock stockAllocation) {
			Criteria criteria = getSession().createCriteria(InventoryStock.class);
			if (stockAllocation.getId() != null) {
				criteria.add(Restrictions.ne("id", stockAllocation.getId()));
			}
			criteria.add(Restrictions.eq("inventoryNo", stockAllocation.getInventoryNo()));
			return (InventoryStock)criteria.uniqueResult();
		}
	
}
