package com.cbmie.genMac.financial.dao;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.financial.entity.Serial;

/**
 * 流水DAO
 */
@Repository
public class SerialDao extends HibernateDao<Serial, Long>{
	
	public Serial findByNo(Serial serial) {
		Criteria criteria = getSession().createCriteria(Serial.class);
		if (serial.getId() != null) {
			criteria.add(Restrictions.ne("id", serial.getId()));
		}
		criteria.add(Restrictions.eq("serialNumber", serial.getSerialNumber()));
		return (Serial)criteria.uniqueResult();
	}
	
	/**
	 * 找出对应合同下承兑的水单总金额
	 */
	public Double sumAcceptance(String contractNo) {
		String sql = "SELECT SUM(money) FROM serial WHERE contract_no = ? AND serial_category = '货款'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, contractNo);
		return (Double)sqlQuery.uniqueResult();
	}
	
}
