package com.cbmie.woodNZ.cgcontract.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;

/**
 * 采购合同-进口DAO
 */
@Repository
public class WoodCghtJkDao extends HibernateDao<WoodCghtJk, Long>{
	
	public WoodCghtJk findByNo(WoodCghtJk woodCghtJk){
		Criteria criteria = getSession().createCriteria(WoodCghtJk.class);
		if (woodCghtJk.getId() != null) {
			criteria.add(Restrictions.ne("id", woodCghtJk.getId()));
		}
		criteria.add(Restrictions.eq("hth", woodCghtJk.getHth()));
		return (WoodCghtJk)criteria.uniqueResult();
	}
}
