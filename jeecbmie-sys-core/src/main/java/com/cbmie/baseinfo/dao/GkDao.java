package com.cbmie.baseinfo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.baseinfo.entity.WoodGk;

/**
 * 港口DAO
 */
@Repository
public class GkDao extends HibernateDao<WoodGk, Long>{
	
	@SuppressWarnings("unchecked")
	public List<WoodGk> getByGJtype(String num) {
		String sql = "SELECT * FROM woodgk WHERE  LOCATE(?, gj)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodGk.class);
		sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
}  
 