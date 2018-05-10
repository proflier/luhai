package com.cbmie.lh.logistic.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.CustomsDeclarationDao;
import com.cbmie.lh.logistic.entity.CustomsDeclaration;

@Service
public class CustomsDeclarationService extends BaseService<CustomsDeclaration, Long> {
	
	@Autowired
	private CustomsDeclarationDao customsDeclarationDao;
	
	@Override
	public HibernateDao<CustomsDeclaration, Long> getEntityDao() {
		return customsDeclarationDao;
	}

	public void exportPdf(Map<String, Object> data, CustomsDeclaration customsDeclaration) {
		
	}
	
}
