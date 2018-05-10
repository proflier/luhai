package com.cbmie.lh.sale.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.PortContract;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;

@Repository
public class SaleDeliveryDao extends HibernateDao<SaleDelivery, Long> {
	
	public boolean checkDeliveryReleaseNoUnique(SaleDelivery saleDelivery) {
		Criteria criteria = getSession().createCriteria(SaleDelivery.class);
		if (saleDelivery.getId() != null) {
			criteria.add(Restrictions.ne("id", saleDelivery.getId()));
		}
		criteria.add(Restrictions.eq("deliveryReleaseNo", saleDelivery.getDeliveryReleaseNo()));
		List list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据deliveryReleaseNo查询
	 * @param saleDelivery
	 * @return
	 */
	public SaleDelivery findByNo(SaleDelivery saleDelivery) {
		Criteria criteria = getSession().createCriteria(SaleDelivery.class);
		if (saleDelivery.getId() != null) {
			criteria.add(Restrictions.ne("id", saleDelivery.getId()));
		}
		criteria.add(Restrictions.eq("deliveryReleaseNo", saleDelivery.getDeliveryReleaseNo()));
		return (SaleDelivery)criteria.uniqueResult();
	}

	public Double getMaxQuantity(SaleDeliveryGoods goods_sub) {
		String sql = "select quantity from v_current_stock_goods where goods_name=? and warehouse_name=? and ship_no =? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, goods_sub.getGoodsName());
		sqlQuery.setParameter(1, goods_sub.getPort());
		sqlQuery.setParameter(2, goods_sub.getVoy());
		if(sqlQuery.uniqueResult()!=null){
			return (Double)sqlQuery.uniqueResult();
		}else{
			return 0.00;
		}
	}

	public Double getAllQuantity(SaleDeliveryGoods goods_sub) {
		String sql = "select SUM(quantity) from lh_sale_delivery_goods where SALE_DELIVERY_ID =? and  port =? and voy =? and goods_name =? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, goods_sub.getSaleDeliveryId());
		sqlQuery.setParameter(1, goods_sub.getPort());
		sqlQuery.setParameter(2, goods_sub.getVoy());
		sqlQuery.setParameter(3, goods_sub.getGoodsName());
		if(sqlQuery.uniqueResult()!=null){
			return (Double)sqlQuery.uniqueResult();
		}else{
			return 0.00;
		}
	}

	public List<SaleDelivery> getSaleDeliveryBaks(Long sourceId, Long curId) {
		String sql = "select * from LH_SALE_DELIVERY a where 1=1 and "
				+ " ((a.change_State!='2' and a.source_Id=:sourceId and a.id<>:curId ) or id=:sourceId ) order by id asc ";
		List<SaleDelivery> list = this.createSQLQuery(sql).addEntity(SaleDelivery.class)
				.setParameter("sourceId", sourceId).setParameter("curId", curId).list();
		return list;
	}
}
