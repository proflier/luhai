package com.cbmie.woodNZ.baseInfo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.baseinfo.entity.ExchangeRate;
import com.cbmie.woodNZ.baseInfo.entity.WoodRerate;

/**
 * 汇率DAO
 */
@Repository
public class ExchangeRateNzDao extends HibernateDao<WoodRerate, Long>{

	/**
	 * 找出最新所有币种最新汇率信息
	 */
	@SuppressWarnings("unchecked")
	public List<WoodRerate> getNewExchangeRate() {
		String sql = "SELECT * FROM woodrerate a,"
				+ "(SELECT currency,MAX(show_time) maxTime FROM woodrerate GROUP BY currency) b "
				+ "WHERE a.currency = b.currency AND a.SHOW_TIME = b.maxTime";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(ExchangeRate.class);
		return sqlQuery.list();
	}
	
	/**
	 * 找出该币种的最新汇率信息
	 */
	public WoodRerate getThisNewExchangeRate(String currency) {
		String sql = "SELECT * FROM woodrerate WHERE CURRENCY = ? ORDER BY SHOW_TIME DESC LIMIT 0,1";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(ExchangeRate.class);
		sqlQuery.setParameter(0, currency);
		return (WoodRerate) sqlQuery.uniqueResult();
	}
	
}
