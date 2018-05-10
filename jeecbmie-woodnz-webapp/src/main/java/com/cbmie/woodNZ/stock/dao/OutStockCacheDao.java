package com.cbmie.woodNZ.stock.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.stock.entity.InStock;
import com.cbmie.woodNZ.stock.entity.OutStock;
import com.cbmie.woodNZ.stock.entity.OutStockCache;

 
/**
 * 出库 - 合并集合DAO 
 */ 
@Repository
public class OutStockCacheDao extends HibernateDao<OutStockCache, Long>{

	public List<OutStockCache> getDeliverAndDownBills() {
		try {
			String sql = "SELECT id as out_id,customer_unit as customer_unit FROM WOOD_SALES_DELIVERY UNION ALL SELECT id as out_id,supplier as customer_unit FROM WOOD_DOWNSTREAM_BILL";
			SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(OutStockCache.class);;
			return sqlQuery.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
} 
 