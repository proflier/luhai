package com.cbmie.system.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.DictChild;

/**
 * 字典子表DAO
 */
@Repository
public class DictChildDao extends HibernateDao<DictChild, Integer>{

	public DictChild getKHLX(String value,Integer id) {
		String sql = "SELECT * FROM dict_child WHERE code = ? AND PID = ?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(DictChild.class);
		sqlQuery.setParameter(0, value);
		sqlQuery.setParameter(1, id);
		return (DictChild) sqlQuery.uniqueResult();
	}
	
	public DictChild getDictName(Integer mainId, String value) {
		String sql = "SELECT * FROM dict_child WHERE ID = ? AND PID = ?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(DictChild.class);
		sqlQuery.setParameter(0, value);
		sqlQuery.setParameter(1, mainId);
		return (DictChild) sqlQuery.uniqueResult();
	}
	
}
