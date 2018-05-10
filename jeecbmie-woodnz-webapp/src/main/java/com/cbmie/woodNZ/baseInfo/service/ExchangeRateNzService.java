package com.cbmie.woodNZ.baseInfo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.baseInfo.dao.ExchangeRateNzDao;
import com.cbmie.woodNZ.baseInfo.entity.WoodRerate;

/**
 * 汇率service
 */
@Service
@Transactional(readOnly = true)
public class ExchangeRateNzService extends BaseService<WoodRerate, Long> {

	@Autowired
	private ExchangeRateNzDao exchangeRateDao;

	@Override
	public HibernateDao<WoodRerate, Long> getEntityDao() {
		return exchangeRateDao;
	}
	
	public List<WoodRerate> getNewExchangeRate() {
		return exchangeRateDao.getNewExchangeRate();
	}
	
	public WoodRerate getThisNewExchangeRate(String currency) {
		return exchangeRateDao.getThisNewExchangeRate(currency);
	}

}
