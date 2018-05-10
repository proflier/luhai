package com.cbmie.baseinfo.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.baseinfo.entity.WoodAffiBankInfo;
import com.cbmie.common.persistence.HibernateDao;

/**
 * 关联信息__银行信息DAO
 */
@Repository
public class AffiBankInfoDao extends HibernateDao<WoodAffiBankInfo, Long> {

	@SuppressWarnings("unchecked")
	public List<WoodAffiBankInfo> getdataListByParentId(Long id) {
		String sql = "SELECT * FROM wood_affi_bank_info WHERE 1=1 and parent_id='"+id+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiBankInfo.class);
		return sqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	public List<WoodAffiBankInfo> getByParentCode(String pCode) {
		String sql = "select * from wood_affi_bank_info where wood_affi_bank_info.parent_id = (select wood_affi_base_info.id from wood_affi_base_info WHERE wood_affi_base_info.customer_code ='"+pCode+"')";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodAffiBankInfo.class);
		return  sqlQuery.list();
	}

	public List<WoodAffiBankInfo> findByEffect(String parentId) {
		String hql = "from WoodAffiBankInfo a where a.parentId=:parentId  and status=1";
		List<WoodAffiBankInfo> list = this.createQuery(hql).setParameter("parentId", parentId).list();
		return list;
	} 

}
