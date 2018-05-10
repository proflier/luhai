package com.cbmie.lh.administration.dao;


import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.administration.entity.ContractManagement;

/**
 * 合同管理
 * @author Administrator
 *
 */
@Repository
public class ContractManagementDao extends HibernateDao<ContractManagement, Long> {

	public List<Object[]> getContract(String contractCategory) {
		String sql = "select * from v_all_contract v where v.category like ?  and v.inner_contract_no not in( select m.inner_contract_no from lh_contract_management m)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, contractCategory);
		return sqlQuery.list();
	}
	
	
}
