package com.cbmie.woodNZ.stock.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.stock.entity.InStockGoods;

/**
 * 入库商品
 * @author linxiaopeng
 * 2016年6月28日
 */
@Repository
public class InStockGoodsDao extends HibernateDao<InStockGoods, Long>{

	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InStockGoods> getByParentId(Long id) {
		String sql = "SELECT * FROM wood_in_stock_goods WHERE  parent_id='"+String.valueOf(id)+"' ORDER BY box_no,goods_no";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(InStockGoods.class);
		return sqlQuery.list();
	}
}
