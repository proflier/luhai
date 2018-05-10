package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.TransportSettlementDao;
import com.cbmie.lh.logistic.entity.TransportSettlement;
@Service
public class TransportSettlemenService extends BaseService<TransportSettlement, Long> {

	@Autowired
	private TransportSettlementDao settleDao;
	@Override
	public HibernateDao<TransportSettlement, Long> getEntityDao() {
		return settleDao;
	}

}
