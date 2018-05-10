package com.cbmie.baseinfo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.baseinfo.entity.WoodAffiContactInfo;

/**
 * 关联信息__联系人信息DAO
 */
@Repository
public class AffiContactInfoDao extends HibernateDao<WoodAffiContactInfo, Long> {
	
	@SuppressWarnings("unchecked")
	public List<WoodAffiContactInfo> getdataListByParentId(Long id) {
		String sql = "SELECT * FROM wood_affi_contact_info WHERE 1=1 and parent_id='"+id+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiContactInfo.class);
		return sqlQuery.list();
	} 


}
