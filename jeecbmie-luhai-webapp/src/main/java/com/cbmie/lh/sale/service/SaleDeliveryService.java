package com.cbmie.lh.sale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.entity.PortContract;
import com.cbmie.lh.sale.dao.SaleDeliveryDao;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;

@Service
public class SaleDeliveryService extends BaseService<SaleDelivery, Long> {

	@Autowired
	private SaleDeliveryDao saleDeliveryDao;
	
	@Override
	public HibernateDao<SaleDelivery, Long> getEntityDao() {
		return saleDeliveryDao;
	}
	
	public boolean checkContractNoUnique(SaleDelivery saleDelivery) {
		return saleDeliveryDao.checkDeliveryReleaseNoUnique(saleDelivery);
	}

	public SaleDelivery findByNo(SaleDelivery saleDelivery) {
		return saleDeliveryDao.findByNo(saleDelivery);
	}

	public Double getMaxQuantity(SaleDeliveryGoods goods_sub) {
		return saleDeliveryDao.getMaxQuantity(goods_sub);
	}

	public Double getAllQuantity(SaleDeliveryGoods goods_sub) {
		return saleDeliveryDao.getAllQuantity(goods_sub);
	}

	public List<SaleDelivery> getSaleDeliveryBaks(Long id) {
		SaleDelivery saleDelivery = saleDeliveryDao.get(id);
		return saleDeliveryDao.getSaleDeliveryBaks(saleDelivery.getSourceId(),saleDelivery.getId());
	}
	

}
