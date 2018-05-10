package com.cbmie.lh.sale.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;

/**
 * 放货商品
 */
@Repository
public class SaleDeliveryGoodsDao extends HibernateDao<SaleDeliveryGoods, Long>{

	public void deleteEntityBySaleDeliveryId(Long saleDeliveryId){
		String hql = "delete from SaleDeliveryGoods a where a.saleDeliveryId=:saleDeliveryId";
		this.createQuery(hql).setParameter("saleDeliveryId", saleDeliveryId).executeUpdate();
	}
	
	public List<Object[]> getOutStockGoodsList(String saleContractNo){
		String sql = "select a.contract_No,b.receipt_Code,a.goods_Name,a.voy,b.out_Stock_Date,a.quantity,a.port "
				+ " from LH_SALE_DELIVERY_GOODS a join LH_OUT_STOCK b on a.OUT_STOCK_ID=b.id "
				+ " where a.contract_No=:0";
		return this.createSQLQuery(sql, saleContractNo).list();
	}

	public Integer deleteByparentId(String parentId) {
		String sql = "delete from lh_sale_delivery_goods where out_stock_id=?";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, parentId);
		return sqlQuery.executeUpdate();
	}

	public List<SaleDeliveryGoods> getByDeliveryNo(String deliveryNo) {
		String sql = "SELECT * FROM LH_SALE_DELIVERY WHERE delivery_release_no='"+deliveryNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SaleDelivery.class);
		List<SaleDeliveryGoods> goodsList = ((SaleDelivery) sqlQuery.list().get(0)).getSalesDeliveryGoodsList();
		return goodsList;
	}
}
