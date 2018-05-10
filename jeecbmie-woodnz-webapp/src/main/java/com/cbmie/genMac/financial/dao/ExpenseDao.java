package com.cbmie.genMac.financial.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.financial.entity.Expense;

/**
 * 费用DAO
 */
@Repository
public class ExpenseDao extends HibernateDao<Expense, Long>{
	
	public Expense findByNo(Expense expense){
		Criteria criteria = getSession().createCriteria(Expense.class);
		if (expense.getId() != null) {
			criteria.add(Restrictions.ne("id", expense.getId()));
		}
		criteria.add(Restrictions.eq("expenseId", expense.getExpenseId()));
		return (Expense)criteria.uniqueResult();
	}
}
