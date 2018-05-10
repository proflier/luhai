package com.cbmie.genMac.foreignTrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.foreignTrade.dao.AgencyAgreementDao;
import com.cbmie.genMac.foreignTrade.entity.AgencyAgreement;

/**
 * 代理协议service
 */
@Service
@Transactional
public class AgencyAgreementService extends BaseService<AgencyAgreement, Long> {

	@Autowired
	private AgencyAgreementDao agencyAgreementDao;

	@Override
	public HibernateDao<AgencyAgreement, Long> getEntityDao() {
		return agencyAgreementDao;
	}
	
	public AgencyAgreement findByNo(AgencyAgreement agencyAgreement){
		return agencyAgreementDao.findByNo(agencyAgreement);
	}
	
}
