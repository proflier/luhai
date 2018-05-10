package com.cbmie.woodNZ.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.credit.dao.ReceiveCreditRegDao;
import com.cbmie.woodNZ.credit.entity.ReceiveCreditReg;

/**
 * 信用证收证登记
 * @author linxiaopeng
 * 2016年6月28日
 */
@Service
@Transactional
public class ReceiveCreditRegService extends BaseService<ReceiveCreditReg, Long> {
	
	@Autowired
	private ReceiveCreditRegDao receiveCreditRegDao;

	@Override
	public HibernateDao<ReceiveCreditReg, Long> getEntityDao() {
		return receiveCreditRegDao;
	}
	
	
}
