package com.cbmie.lh.stock.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;
import com.cbmie.lh.stock.entity.OutStock;

/**
 * 出库DAO
 */
@Repository
public class OutStockDao extends HibernateDao<OutStock, Long>{
	
	public OutStock findByNo(OutStock outStock) {
		Criteria criteria = getSession().createCriteria(OutStock.class);
		if (outStock.getId() != null) {
			criteria.add(Restrictions.ne("id", outStock.getId()));
		}
		criteria.add(Restrictions.eq("outStockNo", outStock.getOutStockNo()));
		return (OutStock)criteria.uniqueResult();
	}

	public List<Object[]> stockTrack(String goodsName,String deliveryNo,String contractNo,String receiptCode){
		String sql = "SELECT *, 0 as quantityHave,0 as quantityless from v_stock_track where goods_name like ? and delivery_no like ? and contract_no like ? and receipt_code like ? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, goodsName);
		sqlQuery.setParameter(1, deliveryNo);
		sqlQuery.setParameter(2, contractNo);
		sqlQuery.setParameter(3, receiptCode);
		return sqlQuery.list();
	}

	public double getMaxQuantity(SaleDeliveryGoods saleDeliveryGoods) {
		String sql = "select CAST(quantity AS decimal(10,3)) from v_current_stock_goods where goods_name=? and warehouse_name=? and ship_no =? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, saleDeliveryGoods.getGoodsName());
		sqlQuery.setParameter(1, saleDeliveryGoods.getPort());
		sqlQuery.setParameter(2, saleDeliveryGoods.getVoy());
		if(sqlQuery.uniqueResult()!=null){
			return ((BigDecimal)sqlQuery.uniqueResult()).doubleValue();
		}else{
			return 0.00;
		}
	}

	public Double getAllQuantity(SaleDeliveryGoods saleDeliveryGoods) {
		String sql = "select SUM(quantity) from lh_sale_delivery_goods where out_stock_id =? and  port =? and voy =? and goods_name =? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, saleDeliveryGoods.getOutStockId());
		sqlQuery.setParameter(1, saleDeliveryGoods.getPort());
		sqlQuery.setParameter(2, saleDeliveryGoods.getVoy());
		sqlQuery.setParameter(3, saleDeliveryGoods.getGoodsName());
		if(sqlQuery.uniqueResult()!=null){
			return (Double)sqlQuery.uniqueResult();
		}else{
			return 0.00;
		}
	}

	
}
