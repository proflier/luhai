package com.cbmie.baseinfo.dao;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.baseinfo.entity.SequenceCode;
import com.cbmie.common.persistence.HibernateDao;

/**
 * 编码生成-DAO
 */
@Repository
public class SequenceCodeDao extends HibernateDao<SequenceCode, Long> {


	public int getSequence(String param) {
		String sql = "select currval(?)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, param);
		return (Integer)sqlQuery.uniqueResult();
	}
	
	public int getNextSequence(String param) {
		String sql = "select nextval(?)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, param);
		return (Integer)sqlQuery.uniqueResult();
	}
	
	public SequenceCode findBySequenceCode(SequenceCode sequenceCode) {
		Criteria criteria = getSession().createCriteria(SequenceCode.class);
		if (sequenceCode.getId() != null) {
			criteria.add(Restrictions.ne("id", sequenceCode.getId()));
		}
		criteria.add(Restrictions.eq("sequenceName", sequenceCode.getSequenceName()));
		return (SequenceCode) criteria.uniqueResult();
	}

	public void updateSequence(String param, Integer currentSquence) {
		String hql="UPDATE t_sequence SET value=:currentSquence where sequence_name=:param ";
		this.createSQLQuery(hql).setParameter("currentSquence", currentSquence).setParameter("param", param).executeUpdate();
	}

}
