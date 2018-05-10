package com.cbmie.lh.sale.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleOfferIndex;

/**
 * 销售报盘-指标dao
 */
@Repository
public class SaleOfferIndexDao extends HibernateDao<SaleOfferIndex, Long>{
	
	@SuppressWarnings("unchecked")
	public List<SaleOfferIndex> getByPid (Long pid) {
		String sql = "select * from LH_SALE_OFFER_INDEX where pid = :pid";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SaleOfferIndex.class);
		sqlQuery.setParameter("pid", pid);
		return sqlQuery.list();
	}

}
