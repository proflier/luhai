package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.OrderShipContractSubDao;
import com.cbmie.lh.logistic.entity.OrderShipContractSub;
@Service
public class OrderShipContractSubService extends BaseService<OrderShipContractSub, Long> {
	@Autowired
	private OrderShipContractSubDao subDao;
	@Override
	public HibernateDao<OrderShipContractSub, Long> getEntityDao() {
		return subDao;
	}

}
