package com.cbmie.lh.sale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleSettlementGoodsDao;
import com.cbmie.lh.sale.entity.SaleSettlementGoods;
@Service
public class SaleSettlementGoodsService extends BaseService<SaleSettlementGoods, Long> {

	@Autowired
	private SaleSettlementGoodsDao settlementGoodsDao;
	@Override
	public HibernateDao<SaleSettlementGoods, Long> getEntityDao() {
		return settlementGoodsDao;
	}

	public List<SaleSettlementGoods> getSaleSettlementGoodsByPid(Long saleSettlementId){
		return settlementGoodsDao.getSaleSettlementGoodsByPid(saleSettlementId);
	}
}
