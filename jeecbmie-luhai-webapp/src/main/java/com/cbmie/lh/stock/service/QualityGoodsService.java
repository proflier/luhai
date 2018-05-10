package com.cbmie.lh.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.stock.dao.QualityGoodsDao;
import com.cbmie.lh.stock.entity.QualityGoods;

/**
 * 质检商品
 */
@Service
@Transactional
public class QualityGoodsService extends BaseService<QualityGoods, Long> {

	@Autowired
	private QualityGoodsDao qualityGoodsDao;

	@Override
	public HibernateDao<QualityGoods, Long> getEntityDao() {
		return qualityGoodsDao;
	}
	
	public List<QualityGoods> getByPid (Long pid) {
		return qualityGoodsDao.getByPid(pid);
	}
	
}
