package com.cbmie.woodNZ.salesDelivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.salesDelivery.dao.SalesDeliveryDao;
import com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery;

/**
 * 销售放货申请service
 */
@Service
@Transactional
public class SalesDeliveryService extends BaseService<SalesDelivery, Long> {
	
	@Autowired
	private SalesDeliveryDao salesDeliveryDao;

	@Override
	public HibernateDao<SalesDelivery, Long> getEntityDao() {
		return salesDeliveryDao;
	}
	
	
}
