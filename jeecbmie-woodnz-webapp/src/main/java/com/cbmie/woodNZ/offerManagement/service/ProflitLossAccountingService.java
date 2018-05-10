package com.cbmie.woodNZ.offerManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.offerManagement.dao.ProflitLossAccountingDao;
import com.cbmie.woodNZ.offerManagement.entity.ProflitLossAccounting;


/**
 *  盈亏核算
 * @author linxiaopeng
 * 2016年7月8日
 */
@Service
@Transactional
public class ProflitLossAccountingService extends BaseService<ProflitLossAccounting, Long> {
	
	@Autowired
	private ProflitLossAccountingDao proflitLossAccountingDao;

	@Override
	public HibernateDao<ProflitLossAccounting, Long> getEntityDao() {
		return proflitLossAccountingDao;
	}
	
	
}
