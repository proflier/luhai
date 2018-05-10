package com.cbmie.woodNZ.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.logistics.dao.InsuranceContractDao;
import com.cbmie.woodNZ.logistics.entity.InsuranceContract;


/**
 * 保险合同service
 */
@Service
@Transactional
public class InsuranceContractService extends BaseService<InsuranceContract, Long> {

	@Autowired 
	private InsuranceContractDao insuranceContractDao;

	@Override
	public HibernateDao<InsuranceContract, Long> getEntityDao() {
		return insuranceContractDao;
	} 

}
  