package com.cbmie.woodNZ.stock.dao;


import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.stock.entity.InStockOutStockRelation;

 
/**
 * 出库DAO 
 */ 
@Repository
public class InStockOutStockRelationDao extends HibernateDao<InStockOutStockRelation, Long>{

	public List<DeliveryStockRelation> deleteByOutStockSubId(Long outSubId) {
		try {
			String sql = "delete  FROM WOOD_INSTOCK_OUTSTOCK_RELATION WHERE 1=1 and OUT_STOCK_ID='"+outSubId+"'";
			getSession().createSQLQuery(sql).executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

} 
 