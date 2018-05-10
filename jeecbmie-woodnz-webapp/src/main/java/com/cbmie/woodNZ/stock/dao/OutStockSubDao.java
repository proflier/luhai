package com.cbmie.woodNZ.stock.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.stock.entity.OutStockSub;

 
/**
 * 出库子表DAO 
 */ 
@Repository
public class OutStockSubDao extends HibernateDao<OutStockSub, Long>{

	public List<OutStockSub> getOutStockByMainId(Long id) {
		try {
			String sql = "SELECT * FROM wood_out_stock_sub WHERE 1=1 and PARENT_ID='"+id+"' order by id asc";
			SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(OutStockSub.class);;
			return sqlQuery.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
} 
 