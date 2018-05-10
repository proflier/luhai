package com.cbmie.lh.logistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.LhBillsGoodsDao;
import com.cbmie.lh.logistic.entity.LhBillsGoods;
@Service
public class LhBillsGoodsService extends BaseService<LhBillsGoods, Long> {

	@Autowired
	private LhBillsGoodsDao goodDao;
	@Override
	public HibernateDao<LhBillsGoods, Long> getEntityDao() {
		return goodDao;
	}

	public void deleteEntityByParentId(String parentId){
		goodDao.deleteEntityByParentId(parentId);
	}
}
