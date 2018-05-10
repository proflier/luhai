package com.cbmie.genMac.foreignTrade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.foreignTrade.dao.ImportContractDao;
import com.cbmie.genMac.foreignTrade.entity.ImportContract;

/**
 * 进口合同service
 */
@Service
@Transactional
public class ImportContractService extends BaseService<ImportContract, Long> {

	@Autowired
	private ImportContractDao importContractDao;
	
	@Override
	public HibernateDao<ImportContract, Long> getEntityDao() {
		return importContractDao;
	}
	
	public ImportContract findByNo(ImportContract importContract){
		return importContractDao.findByNo(importContract);
	}
	
	public List<ImportContract> findMarginInform() {
		return importContractDao.findMarginInform();
	}
	
	public List<ImportContract> findInvoiceReg() {
		return importContractDao.findInvoiceReg();
	}

}
