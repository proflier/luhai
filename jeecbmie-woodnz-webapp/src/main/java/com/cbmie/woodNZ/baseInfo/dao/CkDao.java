package com.cbmie.woodNZ.baseInfo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.baseInfo.entity.WoodCk;

/**
 * 仓库DAO
 */
@Repository
public class CkDao extends HibernateDao<WoodCk, Long>{

	
	@SuppressWarnings("unchecked")
	public List<WoodCk> getByCKNationality(String num) {
		String sql = "SELECT * FROM woodck WHERE  LOCATE(?, nationality)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodCk.class);
		sqlQuery.setParameter(0, num);
		return sqlQuery.list();
	}
	
}
