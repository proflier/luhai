package com.cbmie.woodNZ.salesDelivery.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;

/**
 * 放货库存关系dao
 * @author linxiaopeng
 * 2016年8月10日
 */
@Repository
public class DeliveryStockRelationDao extends HibernateDao<DeliveryStockRelation, Long>{

	/**
	 * @param id
	 * @return 
	 */
	public void deleteByRealGoods(Long id) {
		String sql="delete from WOOD_DELIVERY_STOCK_RELATION where DELIVERY_ID="+id;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(DeliveryStockRelation.class);
		sqlQuery.executeUpdate();
	}

	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DeliveryStockRelation> getAllInStockGoodsByRealGoods(Long id) {
		String sql = "SELECT * FROM WOOD_DELIVERY_STOCK_RELATION WHERE IN_STOCK_ID='"+id+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(DeliveryStockRelation.class);
		return sqlQuery.list();
	}
	
	
}
