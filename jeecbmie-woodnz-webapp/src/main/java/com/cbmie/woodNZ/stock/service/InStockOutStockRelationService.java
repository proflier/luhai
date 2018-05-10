package com.cbmie.woodNZ.stock.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods;
import com.cbmie.woodNZ.stock.dao.InStockOutStockRelationDao;
import com.cbmie.woodNZ.stock.dao.OutStockDao;
import com.cbmie.woodNZ.stock.entity.InStockGoods;
import com.cbmie.woodNZ.stock.entity.InStockOutStockRelation;
import com.cbmie.woodNZ.stock.entity.OutStock;


/**
 * 出库信息service
 */
@Service
@Transactional
public class InStockOutStockRelationService extends BaseService<InStockOutStockRelation, Long> {

	@Autowired 
	private InStockOutStockRelationDao inStockOutStockRelationDao;

	@Override
	public HibernateDao<InStockOutStockRelation, Long> getEntityDao() {
		return inStockOutStockRelationDao;
	}

	public void deleteByOutStockSubId(Long id) {
		// TODO Auto-generated method stub
		inStockOutStockRelationDao.deleteByOutStockSubId(id);
	}


}
