package com.cbmie.woodNZ.stock.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.stock.entity.InStock;

/**
 * 入库DAO
 * @author linxiaopeng
 * 2016年6月28日
 */
@Repository
public class InStockDao extends HibernateDao<InStock, Long>{

	public InStock findByNo(InStock inStock) {
		Criteria criteria = getSession().createCriteria(InStock.class);
		if (inStock.getId() != null) {
			criteria.add(Restrictions.ne("id", inStock.getId()));
		}
		criteria.add(Restrictions.eq("inStockId", inStock.getInStockId()));
		return (InStock)criteria.uniqueResult();
	}
	
	public InStock findByBillId(InStock inStock) {
		Criteria criteria = getSession().createCriteria(InStock.class);
		if (inStock.getId() != null) {
			criteria.add(Restrictions.ne("id", inStock.getId()));
		}
		criteria.add(Restrictions.eq("billId", inStock.getBillId()));
		return (InStock)criteria.uniqueResult();
	}
	
	public InStock findByBillId(String billId) {
		Criteria criteria = getSession().createCriteria(InStock.class);
		criteria.add(Restrictions.eq("billId", billId));
		return (InStock)criteria.uniqueResult();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<InStock> findByReceiveWarehouse(String receiveWarehouse) {
		String sql = "SELECT * FROM wood_in_stock WHERE 1=1 and receive_warehouse='"+receiveWarehouse+"' order by createrdate asc";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(InStock.class);
		return sqlQuery.list();
	}

	/**
	 * @param inStock
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InStock> findAllConfirm() {
		String sql = "SELECT * FROM wood_in_stock WHERE confirm=1";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(InStock.class);
		return sqlQuery.list();
	}
}
