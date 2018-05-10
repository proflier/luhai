package com.cbmie.genMac.logistics.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.logistics.entity.Freight;

/**
 * 货代DAO
 */
@Repository
public class FreightDao extends HibernateDao<Freight, Long> {
	
}
