package com.cbmie.woodNZ.importShips.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.importShips.dao.ShipRegDao;
import com.cbmie.woodNZ.importShips.entity.ShipReg;

/**
 * 船舶信息登记
 * @author linxiaopeng
 * 2016年7月6日
 */
@Service
@Transactional
public class ShipRegService extends BaseService<ShipReg, Long> {
	
	@Autowired
	private ShipRegDao shipRegDao;

	@Override
	public HibernateDao<ShipReg, Long> getEntityDao() {
		return shipRegDao;
	}
	
	public ShipReg findByNo(ShipReg shipReg) {
		return shipRegDao.findByNo(shipReg);
	}
}
