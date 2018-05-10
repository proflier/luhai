package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.WharfSettlementSubDao;
import com.cbmie.lh.logistic.entity.WharfSettlementSub;
@Service
public class WharfSettlementSubService extends BaseService<WharfSettlementSub, Long> {

	@Autowired
	private WharfSettlementSubDao subDao;
	@Override
	public HibernateDao<WharfSettlementSub, Long> getEntityDao() {
		return subDao;
	}

}
