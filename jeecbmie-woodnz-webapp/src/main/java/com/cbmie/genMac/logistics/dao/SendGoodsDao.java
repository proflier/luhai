package com.cbmie.genMac.logistics.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.logistics.entity.SendGoods;

/**
 * 放货DAO
 */
@Repository
public class SendGoodsDao extends HibernateDao<SendGoods, Long> {
	
	public SendGoods findByNo(SendGoods sendGoods){
		Criteria criteria = getSession().createCriteria(SendGoods.class);
		if (sendGoods.getId() != null) {
			criteria.add(Restrictions.ne("id", sendGoods.getId()));
		}
		criteria.add(Restrictions.eq("sendGoodsNo", sendGoods.getSendGoodsNo()));
		return (SendGoods)criteria.uniqueResult();
	}
	
}
