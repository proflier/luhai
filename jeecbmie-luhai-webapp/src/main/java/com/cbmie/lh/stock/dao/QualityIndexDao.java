package com.cbmie.lh.stock.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.stock.entity.QualityIndex;

/**
 * 质检-指标dao
 */
@Repository
public class QualityIndexDao extends HibernateDao<QualityIndex, Long>{
	
	@SuppressWarnings("unchecked")
	public List<QualityIndex> getByPid (Long pid) {
		String sql = "select * from LH_QUALITY_INDEX where pid = :pid";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(QualityIndex.class);
		sqlQuery.setParameter("pid", pid);
		return sqlQuery.list();
	}

}
