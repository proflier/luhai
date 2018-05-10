package com.cbmie.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.CountryAreaDao;
import com.cbmie.system.entity.CountryArea;

/**
 * 国别区域service
 */
@Service
@Transactional(readOnly=true)
public class CountryAreaService extends BaseService<CountryArea, Integer>{
	
	@Autowired
	private CountryAreaDao countryAreaDao;
	
	@Override
	public HibernateDao<CountryArea, Integer> getEntityDao() {
		return countryAreaDao;
	}

	public List<CountryArea> getEffectArea() {
		return countryAreaDao.findBy("status", 1);
	}

	public CountryArea findByName(CountryArea countryArea) {
		return countryAreaDao.findByName(countryArea);
	}

	public CountryArea findByNo(CountryArea countryArea) {
		return countryAreaDao.findByNo(countryArea);
	}

}
