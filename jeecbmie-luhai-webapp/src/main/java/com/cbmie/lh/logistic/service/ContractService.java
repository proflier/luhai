package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.ContractDao;
import com.cbmie.lh.logistic.entity.Contract;
@Service
public class ContractService extends BaseService<Contract,Long> {

	@Autowired
	private ContractDao contractDao;

	@Override
	public HibernateDao<Contract, Long> getEntityDao() {
		return contractDao;
	}
	public boolean checkCodeUique(Contract contract){
		return contractDao.checkCodeUique(contract);
	}
}
