package com.cbmie.lh.sale.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleOfferGoods;

/**
 * 销售报盘商品dao
 */
@Repository
public class SaleOfferGoodsDao extends HibernateDao<SaleOfferGoods, Long>{
	
	@SuppressWarnings("unchecked")
	public List<SaleOfferGoods> getByPid (Long pid) {
		String sql = "select * from LH_SALE_OFFER_GOODS where pid = :pid";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SaleOfferGoods.class);
		sqlQuery.setParameter("pid", pid);
		return sqlQuery.list();
	}

}
