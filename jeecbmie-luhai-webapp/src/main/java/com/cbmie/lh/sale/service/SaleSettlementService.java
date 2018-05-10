package com.cbmie.lh.sale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleSettlementDao;
import com.cbmie.lh.sale.entity.SaleSettlement;
@Service
public class SaleSettlementService extends BaseService<SaleSettlement, Long> {
	@Autowired
	private SaleSettlementDao settlementDao;
	@Override
	public HibernateDao<SaleSettlement, Long> getEntityDao() {
		return settlementDao;
	}
	
	public boolean checkCodeUique(SaleSettlement saleSettlement){
		return settlementDao.checkCodeUique(saleSettlement);
	}
	
	public List<SaleSettlement> getSaleSettlementByContractNo(String saleContractNo){
		return settlementDao.getSaleSettlementByContractNo(saleContractNo);
	}
	
	public SaleSettlement getSaleSettlementBySettlementNo(String saleSettlementNo){
		return settlementDao.getSaleSettlementBySettlementNo(saleSettlementNo);
	}
	
}
