package com.cbmie.genMac.baseinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.WarehouseDao;
import com.cbmie.genMac.baseinfo.entity.Warehouse;

/**
 * 仓库service
 */
@Service
@Transactional(readOnly = true)
public class WarehouseService extends BaseService<Warehouse, Long> {

	@Autowired
	private WarehouseDao warehouseDao;

	@Override
	public HibernateDao<Warehouse, Long> getEntityDao() {
		return warehouseDao;
	}

}
