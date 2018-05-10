package com.cbmie.woodNZ.stock.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.stock.entity.InStock;
import com.cbmie.woodNZ.stock.entity.OutStock;

 
/**
 * 出库DAO 
 */ 
@Repository
public class OutStockDao extends HibernateDao<OutStock, Long>{

	public List<DeliveryStockRelation> getDeliverStockBydeliveryId(Long deliveryId) {
		try {
			String sql = "SELECT * FROM WOOD_DELIVERY_STOCK_RELATION WHERE 1=1 and DELIVERY_ID='"+deliveryId+"' order by id asc";
			SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(DeliveryStockRelation.class);;
			return sqlQuery.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
} 
 