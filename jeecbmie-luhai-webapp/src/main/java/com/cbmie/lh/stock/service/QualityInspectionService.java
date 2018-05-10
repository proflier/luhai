package com.cbmie.lh.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.stock.dao.QualityInspectionDao;
import com.cbmie.lh.stock.entity.QualityInspection;

/**
 * 质检
 */
@Service
@Transactional
public class QualityInspectionService extends BaseService<QualityInspection, Long> {

	@Autowired
	private QualityInspectionDao qualityInspectionDao;

	@Override
	public HibernateDao<QualityInspection, Long> getEntityDao() {
		return qualityInspectionDao;
	}

	public QualityInspection findByNo(QualityInspection qualityInspection) {
		return qualityInspectionDao.findByNo(qualityInspection);
	}
	
}
