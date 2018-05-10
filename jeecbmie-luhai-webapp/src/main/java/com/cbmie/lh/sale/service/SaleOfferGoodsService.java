package com.cbmie.lh.sale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleOfferGoodsDao;
import com.cbmie.lh.sale.entity.SaleOfferGoods;

/**
 * 销售报盘商品
 */
@Service
@Transactional
public class SaleOfferGoodsService extends BaseService<SaleOfferGoods, Long> {

	@Autowired
	private SaleOfferGoodsDao saleOfferGoodsDao;

	@Override
	public HibernateDao<SaleOfferGoods, Long> getEntityDao() {
		return saleOfferGoodsDao;
	}
	
	public List<SaleOfferGoods> getByPid (Long pid) {
		return saleOfferGoodsDao.getByPid(pid);
	}
	
}
