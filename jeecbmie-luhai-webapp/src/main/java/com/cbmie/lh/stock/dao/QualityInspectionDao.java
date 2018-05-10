package com.cbmie.lh.stock.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.stock.entity.QualityInspection;

/**
 * 质检dao
 */
@Repository
public class QualityInspectionDao extends HibernateDao<QualityInspection, Long>{
	
	public QualityInspection findByNo(QualityInspection qualityInspection) {
		Criteria criteria = getSession().createCriteria(QualityInspection.class);
		if (qualityInspection.getId() != null) {
			criteria.add(Restrictions.ne("id", qualityInspection.getId()));
		}
		criteria.add(Restrictions.eq("inspectionNo", qualityInspection.getInspectionNo()));
		return (QualityInspection)criteria.uniqueResult();
	}
	
}
