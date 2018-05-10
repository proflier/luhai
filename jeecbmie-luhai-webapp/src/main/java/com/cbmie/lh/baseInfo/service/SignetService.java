package com.cbmie.lh.baseInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.baseInfo.dao.SignetDao;
import com.cbmie.lh.baseInfo.entity.Signet;
@Service
public class SignetService extends BaseService<Signet, Long> {

	@Autowired
	private SignetDao signetDao;
	
	@Override
	public HibernateDao<Signet, Long> getEntityDao() {
		return signetDao;
	}

	public boolean checkCodeUique(Signet signet){
		return signetDao.checkCodeUique(signet);
	}
	
	public Signet getEntityByCode(String code){
		return signetDao.getEntityByCode(code);
	}

	public List<Signet> getEffectSignet() {
		return signetDao.findBy("status", 1);
	}
}
