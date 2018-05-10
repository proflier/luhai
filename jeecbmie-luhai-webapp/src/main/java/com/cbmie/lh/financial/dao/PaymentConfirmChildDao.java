package com.cbmie.lh.financial.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.financial.entity.PaymentConfirmChild;
@Repository
public class PaymentConfirmChildDao extends HibernateDao<PaymentConfirmChild, Long> {

	public Integer deleteByPid(Long pid) {
		String sql = "delete from LH_PAYMENT_CONFIRM_CHILD where payment_confirm_id=?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, pid);
		return sqlQuery.executeUpdate();
	}
	
}
