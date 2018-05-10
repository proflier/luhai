package com.cbmie.lh.sale.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleContractGoodsDao;
import com.cbmie.lh.sale.entity.SaleContractGoods;
@Service
public class SaleContractGoodsService extends BaseService<SaleContractGoods, Long> {

	@Autowired
	private SaleContractGoodsDao goodsDao;
	
	@Override
	public HibernateDao<SaleContractGoods, Long> getEntityDao() {
		return goodsDao;
	}

	public void deleteEntityBySaleContractId(Long saleContractId){
		goodsDao.deleteEntityBySaleContractId(saleContractId);
	}
	
	public void getSaleContractFGoodsFetchContract(Page<SaleContractGoods> page,Map<String,Object> queryParams){
		goodsDao.getSaleContractFGoodsFetchContract(page, queryParams);
	}
}
