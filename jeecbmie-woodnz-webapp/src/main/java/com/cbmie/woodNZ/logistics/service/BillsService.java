package com.cbmie.woodNZ.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.logistics.dao.BillsDao;
import com.cbmie.woodNZ.logistics.entity.WoodBills;


/**
 * 到单信息service
 */
@Service
@Transactional(readOnly = true)
public class BillsService extends BaseService<WoodBills, Long> {

	@Autowired 
	private BillsDao billsDao;

	@Override
	public HibernateDao<WoodBills, Long> getEntityDao() {
		return billsDao;
	}

	public boolean validateBillNo(String billNo, Long id) {
		return billsDao.validateBillNo(billNo,id);
	}

}
