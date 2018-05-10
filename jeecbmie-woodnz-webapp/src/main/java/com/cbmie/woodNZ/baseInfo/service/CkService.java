package com.cbmie.woodNZ.baseInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.baseInfo.dao.CkDao;
import com.cbmie.woodNZ.baseInfo.entity.WoodCk;

/**
 * 仓库service
 */
@Service
@Transactional(readOnly = true)
public class CkService extends BaseService<WoodCk, Long> {

	@Autowired
	private CkDao woodCkDao;

	@Override
	public HibernateDao<WoodCk, Long> getEntityDao() {
		return woodCkDao; 
	}
	
	public List<WoodCk> getByCKNationality(String num) {
		return woodCkDao.getByCKNationality(num);
	}
}
