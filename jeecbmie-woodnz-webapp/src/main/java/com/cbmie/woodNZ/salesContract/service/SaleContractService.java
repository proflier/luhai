package com.cbmie.woodNZ.salesContract.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.salesContract.dao.SaleContractDao;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.cbmie.woodNZ.stock.entity.OutStockCache;



/**
 * 销售主表service
 */
@Service
@Transactional(readOnly = true)
public class SaleContractService extends BaseService<WoodSaleContract, Long> {

	@Autowired 
	private SaleContractDao saleContractDao;

	@Override
	public HibernateDao<WoodSaleContract, Long> getEntityDao() {
		return saleContractDao;
	}

	public Long getIdByContractNo(String saleContractNo) {
		return saleContractDao.getIdByContractNo(saleContractNo);
	}

	public List<OutStockCache> getCacheByContractNo(String saleContractNo) {
		return saleContractDao.getCacheByContractNo(saleContractNo);
	}

	public boolean validateContractNo(String contractNo, Long id) {
		return saleContractDao.validateContractNo( contractNo,  id);
	}
}
