package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.ExtraExpenseDao;
import com.cbmie.lh.logistic.entity.ExtraExpense;
@Service
public class ExtraExpenseService extends BaseService<ExtraExpense, Long> {

	@Autowired
	private ExtraExpenseDao extraDao;
	@Override
	public HibernateDao<ExtraExpense, Long> getEntityDao() {
		return extraDao;
	}

}
