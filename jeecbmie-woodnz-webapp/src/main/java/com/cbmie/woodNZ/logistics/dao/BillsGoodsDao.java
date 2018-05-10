package com.cbmie.woodNZ.logistics.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.logistics.entity.WoodBillsGoods;

 
/**
 * 到单子表 - 货物明细DAO 
 */ 
@Repository
public class BillsGoodsDao extends HibernateDao<WoodBillsGoods, Long>{

	@SuppressWarnings("unchecked")
	public List<WoodBillsGoods> getGoodsListByParentId(Long mainId) {
		String sql = "SELECT * FROM wood_bills_goods WHERE 1=1 and parent_id='"+String.valueOf(mainId)+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodBillsGoods.class);
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<WoodBillsGoods> getGoodsListByCGHTH(String hth) {
		String sql = "SELECT * FROM wood_bills_goods WHERE cg_hth='"+hth+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodBillsGoods.class);
		return sqlQuery.list();
	}
	
} 
 