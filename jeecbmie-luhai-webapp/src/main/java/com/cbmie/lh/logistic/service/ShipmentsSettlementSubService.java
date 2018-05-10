package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.ShipmentsSettlementSubDao;
import com.cbmie.lh.logistic.entity.ShipmentsSettlementSub;
@Service
public class ShipmentsSettlementSubService extends BaseService<ShipmentsSettlementSub, Long> {

	@Autowired
	private ShipmentsSettlementSubDao subDao;
	@Override
	public HibernateDao<ShipmentsSettlementSub, Long> getEntityDao() {
		return subDao;
	}

}
