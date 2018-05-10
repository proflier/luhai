package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.TransportSettlementSubDao;
import com.cbmie.lh.logistic.entity.TransportSettlementSub;
@Service
public class TransportSettlementSubService extends BaseService<TransportSettlementSub, Long> {

	@Autowired
	private TransportSettlementSubDao subDao;
	@Override
	public HibernateDao<TransportSettlementSub, Long> getEntityDao() {
		return subDao;
	}

}
