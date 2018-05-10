package com.cbmie.woodNZ.logistics.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.logistics.entity.DownstreamGoods;
import com.cbmie.woodNZ.logistics.entity.WoodBillsGoods;

 
/**
 * 下游到到单子表 - 货物明细DAO 
 */ 
@Repository
public class DownstreamGoodsDao extends HibernateDao<DownstreamGoods, Long>{

	@SuppressWarnings("unchecked")
	public List<DownstreamGoods> getGoodsListByParentId(Long mainId) {
		String sql = "SELECT * FROM WOOD_DOWNSTREAM_GOODS WHERE 1=1 and parent_id='"+mainId+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodBillsGoods.class);
		return sqlQuery.list();
	}
	
} 
 