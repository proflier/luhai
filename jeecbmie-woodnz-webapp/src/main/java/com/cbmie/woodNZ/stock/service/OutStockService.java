package com.cbmie.woodNZ.stock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods;
import com.cbmie.woodNZ.stock.dao.OutStockDao;
import com.cbmie.woodNZ.stock.entity.InStockGoods;
import com.cbmie.woodNZ.stock.entity.OutStock;
import com.cbmie.woodNZ.stock.entity.OutStockCache;
import com.cbmie.woodNZ.stock.entity.StockStatistic;


/**
 * 出库信息service
 */
@Service
@Transactional
public class OutStockService extends BaseService<OutStock, Long> {

	@Autowired 
	private OutStockDao outstockDao;

	@Override
	public HibernateDao<OutStock, Long> getEntityDao() {
		return outstockDao;
	}

	public List<DeliveryStockRelation> getDeliverStockBydeliveryId(Long deliveryId) {
		List<DeliveryStockRelation> resultList =  outstockDao.getDeliverStockBydeliveryId(deliveryId);
		return resultList;
	}

}
