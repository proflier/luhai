package com.cbmie.lh.stock.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.stock.entity.OutStockDetail;

/**
 * 出库详细DAO
 */
@Repository
public class OutStockDetailDao extends HibernateDao<OutStockDetail, Long>{

	public Integer delByOutStockId() {
		String sql = "delete from LH_OUT_STOCK_DETAIL";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.executeUpdate();
		return sqlQuery.executeUpdate();
	}
	
}
