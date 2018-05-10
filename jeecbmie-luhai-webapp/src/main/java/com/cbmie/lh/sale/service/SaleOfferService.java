package com.cbmie.lh.sale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleOfferDao;
import com.cbmie.lh.sale.entity.SaleOffer;

/**
 * 销售报盘
 */
@Service
@Transactional
public class SaleOfferService extends BaseService<SaleOffer, Long> {

	@Autowired
	private SaleOfferDao saleOfferDao;

	@Override
	public HibernateDao<SaleOffer, Long> getEntityDao() {
		return saleOfferDao;
	}

	public SaleOffer findByNo(SaleOffer saleOffer) {
		return saleOfferDao.findByNo(saleOffer);
	}
	
}
