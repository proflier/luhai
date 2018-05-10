package com.cbmie.lh.stock.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.stock.entity.InStockNoticeGoods;

/**
 * 入库通知商品
 */
@Repository
public class InStockNoticeGoodsDao extends HibernateDao<InStockNoticeGoods, Long>{

	public Integer deleteByNoticeId(String inStockNoticeId) {
		String sql = "delete from LH_IN_STOCK_NOTICE_GOODS where in_stock_notice_id=?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, inStockNoticeId);
		return sqlQuery.executeUpdate();
	}

}
