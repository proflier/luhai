package com.cbmie.lh.logistic.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.ShipmentsSettlement;
@Repository
public class ShipmentsSettlementDao extends HibernateDao<ShipmentsSettlement, Long> {

	public String getSummary(ShipmentsSettlement shipmentsSettlement) {
		String sql = "select CONCAT((select name from dict_child where dict_child.pid=85 and code =?),'[',IFNULL((select cast(SUM(lh_shipments_settlementsub.total_price) as decimal(10,2)) from lh_shipments_settlementsub where lh_shipments_settlementsub.shipments_settle_id =?),0),'人民币]-',(select wood_affi_base_info.customer_name from wood_affi_base_info where wood_affi_base_info.customer_code=?))";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, shipmentsSettlement.getTradeCategory());
		sqlQuery.setParameter(1, shipmentsSettlement.getId());
		sqlQuery.setParameter(2, shipmentsSettlement.getAccountName());
		return (String)sqlQuery.uniqueResult();
	}

}
