package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.ProxyAgreementDao;
import com.cbmie.lh.logistic.entity.ProxyAgreement;


/**
 * 报关代理协议service
 */
@Service
@Transactional(readOnly = true)
public class ProxyAgreementService extends BaseService<ProxyAgreement, Long> {

	@Autowired 
	private ProxyAgreementDao proxyAgreementDao;

	@Override
	public HibernateDao<ProxyAgreement, Long> getEntityDao() {
		return proxyAgreementDao;
	}

	public ProxyAgreement findByNo(ProxyAgreement proxyAgreement) {
		return proxyAgreementDao.findByNo(proxyAgreement);
	}
	
	public ProxyAgreement findByContractNo(ProxyAgreement proxyAgreement) {
		return proxyAgreementDao.findByContractNo(proxyAgreement);
	}

}
