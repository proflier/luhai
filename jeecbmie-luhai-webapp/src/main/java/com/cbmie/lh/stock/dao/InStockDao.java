package com.cbmie.lh.stock.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.stock.entity.InStock;

/**
 * 入库DAO
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
		criteria.add(Restrictions.eq("billNo", inStock.getBillNo()));
		return (InStock)criteria.uniqueResult();
	}
	
	public InStock findByBillId(String billNo,Long id) {
		Criteria criteria = getSession().createCriteria(InStock.class);
		if (id != null) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("billNo", billNo));
		return (InStock)criteria.uniqueResult();
	}

	public List<String> getAllShipNo() {
		String sql = "select ship_no from LH_SHIP_TRACE ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	public List<String> getAllGoodsNo() {
		String sql = "select info_code from LH_GOODSINFORMATION ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}
	

	public List<String> getAllWarehouseNo() {
		String sql = "select customer_code from WOOD_AFFI_BASE_INFO where customer_type like '%10230007%' ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return sqlQuery.list();
	}

	public List<LhBills> selectBillsNoRepeict(String billNo) {
		String sql = " select * from lh_bills where lh_bills.bill_no not in (select lh_in_stock.bill_no from lh_in_stock) and lh_bills.confirm=1 UNION select * from lh_bills where lh_bills.bill_no ='"+billNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(LhBills.class);
		return sqlQuery.list();
	}
}
