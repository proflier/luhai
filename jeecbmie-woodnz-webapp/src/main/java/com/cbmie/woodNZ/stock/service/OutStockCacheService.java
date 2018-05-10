package com.cbmie.woodNZ.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.stock.dao.OutStockCacheDao;
import com.cbmie.woodNZ.stock.entity.OutStockCache;


/**
 * 出库- 合并集合表service
 */
@Service
@Transactional
public class OutStockCacheService extends BaseService<OutStockCache, Long> {

	@Autowired 
	private OutStockCacheDao outStockCacheDao;

	@Override
	public HibernateDao<OutStockCache, Long> getEntityDao() {
		return outStockCacheDao;
	}

	public List<OutStockCache> getDeliverAndDownBills() {
		return outStockCacheDao.getDeliverAndDownBills();
	}
	
//	@Transactional(readOnly = true)
//	public Page<OutStockCache> search(final Page<OutStockCache> page, final List<PropertyFilter> filters) {
//		return getEntityDao().findPage(page, filters);
//	}
}
