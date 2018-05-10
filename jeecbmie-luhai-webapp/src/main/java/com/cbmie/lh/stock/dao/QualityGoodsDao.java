package com.cbmie.lh.stock.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.stock.entity.QualityGoods;

/**
 * 质检商品dao
 */
@Repository
public class QualityGoodsDao extends HibernateDao< QualityGoods, Long>{
	
	@SuppressWarnings("unchecked")
	public List<QualityGoods> getByPid (Long pid) {
		String sql = "select * from LH_QUALITY_GOODS where pid = :pid";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(QualityGoods.class);
		sqlQuery.setParameter("pid", pid);
		return sqlQuery.list();
	}

}
