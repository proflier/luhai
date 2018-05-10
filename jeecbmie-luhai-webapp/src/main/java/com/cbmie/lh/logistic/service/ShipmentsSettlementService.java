package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.ShipmentsSettlementDao;
import com.cbmie.lh.logistic.entity.ShipmentsSettlement;
@Service
public class ShipmentsSettlementService extends BaseService<ShipmentsSettlement, Long> {

	@Autowired
	private ShipmentsSettlementDao settlementDao;
	@Override
	public HibernateDao<ShipmentsSettlement, Long> getEntityDao() {
		return settlementDao;
	}
	public String getSummary(ShipmentsSettlement shipmentsSettlement) {
		return settlementDao.getSummary(shipmentsSettlement);
	}

}
