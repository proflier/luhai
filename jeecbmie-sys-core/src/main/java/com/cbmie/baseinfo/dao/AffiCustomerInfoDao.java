package com.cbmie.baseinfo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.baseinfo.entity.WoodAffiCustomerInfo;

/**
 * 关联信息__客户评审DAO
 */
@Repository
public class AffiCustomerInfoDao extends HibernateDao<WoodAffiCustomerInfo, Long> {
	
	@SuppressWarnings("unchecked")
	public List<WoodAffiCustomerInfo> getdataListByParentId(Long id) {
		String sql = "SELECT * FROM wood_affi_customer_info WHERE 1=1 and parent_id='"+id+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiCustomerInfo.class);
		return sqlQuery.list();
	} 

}
