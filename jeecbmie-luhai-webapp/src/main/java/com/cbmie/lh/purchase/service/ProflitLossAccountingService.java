package com.cbmie.lh.purchase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.purchase.dao.ProflitLossAccountingDao;
import com.cbmie.lh.purchase.entity.ProflitLossAccounting;

/**
 * 盈亏核算
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
