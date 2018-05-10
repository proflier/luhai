package com.cbmie.genMac.foreignTrade.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.foreignTrade.entity.AgencyAgreement;

/**
 * 代理协议DAO
 */
@Repository
public class AgencyAgreementDao extends HibernateDao<AgencyAgreement, Long> {
	
	public AgencyAgreement findByNo(AgencyAgreement agencyAgreement){
		Criteria criteria = getSession().createCriteria(AgencyAgreement.class);
		if (agencyAgreement.getId() != null) {
			criteria.add(Restrictions.ne("id", agencyAgreement.getId()));
		}
		criteria.add(Restrictions.eq("agencyAgreementNo", agencyAgreement.getAgencyAgreementNo()));
		return (AgencyAgreement)criteria.uniqueResult();
	}

}
