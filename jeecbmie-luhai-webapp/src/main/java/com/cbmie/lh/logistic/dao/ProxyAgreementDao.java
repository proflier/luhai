package com.cbmie.lh.logistic.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.ProxyAgreement;
 
/**
 * 报关代理协议DAO 
 */ 
@Repository
public class ProxyAgreementDao extends HibernateDao<ProxyAgreement, Long>{

	public ProxyAgreement findByNo(ProxyAgreement proxyAgreement) {
		Criteria criteria = getSession().createCriteria(ProxyAgreement.class);
		if (proxyAgreement.getId() != null) {
			criteria.add(Restrictions.ne("id", proxyAgreement.getId()));
		}
		criteria.add(Restrictions.eq("innerContractNo", proxyAgreement.getInnerContractNo()));
		return (ProxyAgreement)criteria.uniqueResult();
	}
	
	public ProxyAgreement findByContractNo(ProxyAgreement proxyAgreement) {
		Criteria criteria = getSession().createCriteria(ProxyAgreement.class);
		if (proxyAgreement.getId() != null) {
			criteria.add(Restrictions.ne("id", proxyAgreement.getId()));
		}
		criteria.add(Restrictions.eq("contractNo", proxyAgreement.getContractNo()));
		return (ProxyAgreement)criteria.uniqueResult();
	}
} 
 