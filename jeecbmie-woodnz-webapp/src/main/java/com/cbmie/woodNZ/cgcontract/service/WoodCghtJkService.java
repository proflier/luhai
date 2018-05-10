package com.cbmie.woodNZ.cgcontract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.cgcontract.dao.WoodCghtJkDao;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;

/**
 * 采购合同-进口service
 */
@Service
@Transactional
public class WoodCghtJkService extends BaseService<WoodCghtJk, Long> {
	
	@Autowired
	private WoodCghtJkDao woodCghtJkDao;

	@Override
	public HibernateDao<WoodCghtJk, Long> getEntityDao() {
		return woodCghtJkDao;
	}
	
	public WoodCghtJk findByNo(WoodCghtJk woodCghtJk){
		return woodCghtJkDao.findByNo(woodCghtJk);
	}
	
}
