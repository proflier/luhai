package com.cbmie.genMac.logistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.genMac.logistics.dao.SendGoodsDao;
import com.cbmie.genMac.logistics.entity.SendGoods;

/**
 * 放货service
 */
@Service
@Transactional
public class SendGoodsService extends BaseService<SendGoods, Long> {

	@Autowired
	private SendGoodsDao sendGoodsDao;

	@Override
	public HibernateDao<SendGoods, Long> getEntityDao() {
		return sendGoodsDao;
	}
	
	public List<SendGoods> findBy(String propertyName, String value) {
		return sendGoodsDao.findBy(propertyName, value);
	}
	
	public SendGoods findByNo(SendGoods sendGoods){
		return sendGoodsDao.findByNo(sendGoods);
	}
	
}
