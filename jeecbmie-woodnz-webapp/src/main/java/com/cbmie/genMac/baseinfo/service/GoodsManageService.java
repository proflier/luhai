package com.cbmie.genMac.baseinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.baseinfo.dao.GoodsManageDao;
import com.cbmie.genMac.baseinfo.entity.GoodsManage;

/**
 * 商品管理service
 */
@Service
@Transactional
public class GoodsManageService extends BaseService<GoodsManage, Long> {

	@Autowired
	private GoodsManageDao goodsManageDao;

	@Override
	public HibernateDao<GoodsManage, Long> getEntityDao() {
		return goodsManageDao;
	}
	
}
