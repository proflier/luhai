package com.cbmie.genMac.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.stock.dao.PlanStockDetailDao;
import com.cbmie.genMac.stock.entity.PlanStockDetail;

/**
 * 盘库明细service
 */
@Service
@Transactional
public class PlanStockDetailService extends BaseService<PlanStockDetail, Long> {
	
	@Autowired
	private PlanStockDetailDao planStockDetail;

	@Override
	public HibernateDao<PlanStockDetail, Long> getEntityDao() {
		return planStockDetail;
	}

}
