package com.cbmie.woodNZ.salesContract.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.offerManagement.entity.ProflitLossAccounting;
import com.cbmie.woodNZ.salesContract.dao.PurchaseSaleSameDao;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSame;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSameSub;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;



/**
 * 采销同批主表service
 */
@Service
@Transactional
public class PurchaseSaleSameService extends BaseService<PurchaseSaleSame, Long> {

	@Autowired 
	private PurchaseSaleSameDao purchaseSaleSameDao;

	@Override
	public HibernateDao<PurchaseSaleSame, Long> getEntityDao() {
		return purchaseSaleSameDao;
	}

	public List<ProflitLossAccounting> findProflitLossByContractNO(String hth) {
		return purchaseSaleSameDao.findProflitLossByContractNO(hth);
	}

	public List<WoodCghtJk> getAllPurchaseList(Long mainId, String hth) {
		return purchaseSaleSameDao.getAllPurchaseList(mainId,hth);
	}

	public List<WoodSaleContract> getAllSaleList(Long mainId, String hth) {
		return purchaseSaleSameDao.getAllSaleList(mainId,hth);
	}

	public List<WoodSaleContract> getCount4Cancel() {
		return purchaseSaleSameDao.getCount4Cancel();
	}
 
	public List<PurchaseSaleSameSub> judge4Cancel(Long id) {
		return purchaseSaleSameDao.judge4Cancel(id);
	}

	public List<WoodCghtJk> getCount4CancelCg() {
		return purchaseSaleSameDao.getCount4CancelCg(); 
	}
 
	public List<PurchaseSaleSameSub> judge4CancelCg(Long id) {
		return purchaseSaleSameDao.judge4CancelCg(id);
	}

}
