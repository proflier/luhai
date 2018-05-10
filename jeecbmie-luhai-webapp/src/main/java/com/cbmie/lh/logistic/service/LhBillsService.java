package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.LhBillsDao;
import com.cbmie.lh.logistic.entity.LhBills;


/**
 * 到单信息service
 */
@Service
@Transactional(readOnly = true)
public class LhBillsService extends BaseService<LhBills, Long> {

	@Autowired 
	private LhBillsDao billsDao;

	@Override
	public HibernateDao<LhBills, Long> getEntityDao() {
		return billsDao;
	}

	public boolean validateBillNo(String billNo, Long id) {
		return billsDao.validateBillNo(billNo,id);
	}

	public String getOurOrCustomer(String loginName) {
		return billsDao.getOurOrCustomer(loginName);
	}

}
