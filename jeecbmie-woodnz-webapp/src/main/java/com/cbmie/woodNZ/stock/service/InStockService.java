package com.cbmie.woodNZ.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.stock.dao.InStockDao;
import com.cbmie.woodNZ.stock.entity.InStock;

/**
 * 入库
 * @author linxiaopeng
 * 2016年6月28日
 */
@Service
@Transactional
public class InStockService extends BaseService<InStock, Long> {
	
	@Autowired
	private InStockDao inStockDao;

	@Override
	public HibernateDao<InStock, Long> getEntityDao() {
		return inStockDao;
	}
	
	public InStock findByNo(InStock inStock) {
		return inStockDao.findByNo(inStock);
	}
	
	public InStock findByBillId(InStock inStock) {
		return inStockDao.findByBillId(inStock);
	}
	
	public InStock findByBillId(String billId) {
		return inStockDao.findByBillId(billId);
	}
	
	
	public List<InStock> findAllConfirm() {
		return inStockDao.findAllConfirm();
	}

}
