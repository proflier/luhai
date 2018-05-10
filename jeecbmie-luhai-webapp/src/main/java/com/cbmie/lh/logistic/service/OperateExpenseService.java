package com.cbmie.lh.logistic.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.OperateExpenseDao;
import com.cbmie.lh.logistic.entity.OperateExpense;
@Service
public class OperateExpenseService extends BaseService<OperateExpense, Long> {

	@Autowired
	private OperateExpenseDao operateDao;
	@Override
	public HibernateDao<OperateExpense, Long> getEntityDao() {
		return operateDao;
	}

	public List<Map<String,Object>> getValidPort(){
		return operateDao.getValidPort();
	}
}
