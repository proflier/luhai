package com.cbmie.woodNZ.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.stock.dao.StockStatisticDao;
import com.cbmie.woodNZ.stock.entity.StockStatistic;

@Service
@Transactional
public class StockStatisticService extends BaseService<StockStatistic, Long>{
	@Autowired
	private StockStatisticDao statisticDao;
	
	@Override
	public HibernateDao<StockStatistic, Long> getEntityDao() {
		return statisticDao;
	}
	
	@Transactional(readOnly = true)
	public Page<StockStatistic> search(final Page<StockStatistic> page, final List<PropertyFilter> filters) {
		return getEntityDao().findPage(page, filters);
	}
}
