package com.cbmie.lh.stock.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.stock.dao.InAndOutAccountDao;
import com.cbmie.lh.stock.entity.InAndOutAccount;

/**
 *  进销存台账
 * 
 */
@Service
@Transactional
public class InAndOutAccountService extends BaseService<InAndOutAccount, Long> {

	@Autowired
	private InAndOutAccountDao inAndOutAccountDao;

	@Override
	public HibernateDao<InAndOutAccount, Long> getEntityDao() {
		return inAndOutAccountDao;
	}

	public Map<String, Object> getCurrency(Map<String, Object> showParams) {
		return inAndOutAccountDao.getDataByParams(showParams);
	}
	
	public Double getSurplusQuantity(Map<String, Object> showParams){
		return inAndOutAccountDao.getSurplusQuantity(showParams);
	}
}
