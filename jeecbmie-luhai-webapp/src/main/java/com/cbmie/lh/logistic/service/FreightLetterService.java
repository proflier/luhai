package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.FreightLetterDao;
import com.cbmie.lh.logistic.entity.Contract;
import com.cbmie.lh.logistic.entity.FreightLetter;
@Service
public class FreightLetterService extends BaseService<FreightLetter, Long> {

	@Autowired
	private FreightLetterDao dao;
	@Override
	public HibernateDao<FreightLetter, Long> getEntityDao() {
		return dao;
	}
	public boolean checkCodeUique(FreightLetter letter){
		return dao.checkCodeUique(letter);
	}
}
