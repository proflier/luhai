package com.cbmie.woodNZ.baseInfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.baseInfo.dao.CompanyDao;
import com.cbmie.woodNZ.baseInfo.entity.WoodBusinessCompany;


/**
 * 商品信息service
 */
@Service
@Transactional(readOnly = true)
public class CompanyService extends BaseService<WoodBusinessCompany, Long> {

	@Autowired
	private CompanyDao companyDao;

	@Override
	public HibernateDao<WoodBusinessCompany, Long> getEntityDao() {
		return companyDao;
	}


	
}
