package com.cbmie.system.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.CountryArea;


/**
 * 国别区域DAO
 */
@Repository
public class CountryAreaDao extends HibernateDao<CountryArea, Integer>{

	public CountryArea findByName(CountryArea countryArea) {
		Criteria criteria = getSession().createCriteria(CountryArea.class);
		if (countryArea.getId() != null) {
			criteria.add(Restrictions.ne("id", countryArea.getId()));
		}
		criteria.add(Restrictions.eq("name", countryArea.getName()));
		return (CountryArea)criteria.uniqueResult();
	}

	public CountryArea findByNo(CountryArea countryArea) {
		Criteria criteria = getSession().createCriteria(CountryArea.class);
		if (countryArea.getId() != null) {
			criteria.add(Restrictions.ne("id", countryArea.getId()));
		}
		criteria.add(Restrictions.eq("code", countryArea.getCode()));
		return (CountryArea)criteria.uniqueResult();
	}

}
