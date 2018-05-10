package com.cbmie.lh.logistic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.CustomsDeclarationGoodsDao;
import com.cbmie.lh.logistic.entity.CustomsDeclarationGoods;

@Service
public class CustomsDeclarationGoodsService extends BaseService<CustomsDeclarationGoods, Long> {
	
	@Autowired
	private CustomsDeclarationGoodsDao goodsDao;
	
	@Override
	public HibernateDao<CustomsDeclarationGoods, Long> getEntityDao() {
		return goodsDao;
	}
	
	public List<CustomsDeclarationGoods> getByPid (Long pid) {
		return goodsDao.getByPid(pid);
	}

}
