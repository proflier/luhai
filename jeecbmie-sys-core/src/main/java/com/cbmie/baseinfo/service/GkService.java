package com.cbmie.baseinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.baseinfo.dao.GkDao;
import com.cbmie.baseinfo.entity.WoodGk;

/**
 * 港口service
 */
@Service
@Transactional(readOnly = true)
public class GkService extends BaseService<WoodGk, Long> {

	@Autowired
	private GkDao woodGkDao;

	@Override
	public HibernateDao<WoodGk, Long> getEntityDao() {
		return woodGkDao;
	}

	public List<WoodGk> getByGJtype(String num) {
		return woodGkDao.getByGJtype(num);
	}
	
}
