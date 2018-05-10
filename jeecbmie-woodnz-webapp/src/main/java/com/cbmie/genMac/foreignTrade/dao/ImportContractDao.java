package com.cbmie.genMac.foreignTrade.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.foreignTrade.entity.ImportContract;

/**
 * 进口合同DAO
 */
@Repository
public class ImportContractDao extends HibernateDao<ImportContract, Long> {

	public ImportContract findByNo(ImportContract importContract) {
		Criteria criteria = getSession().createCriteria(ImportContract.class);
		if (importContract.getId() != null) {
			criteria.add(Restrictions.ne("id", importContract.getId()));
		}
		criteria.add(Restrictions.eq("contractNO", importContract.getContractNO()));
		return (ImportContract) criteria.uniqueResult();
	}

	/**
	 * 找出满足催缴保证金的进口合同
	 */
	@SuppressWarnings("unchecked")
	public List<ImportContract> findMarginInform() {
		String sql = "SELECT * FROM improt_contract where state = '生效' "
				+ "and datediff(open_credit_date, sysdate()) < 5 "
				+ "and contract_no not in(SELECT contract_no FROM serial WHERE serial_category = '保证金')";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(ImportContract.class);
		return sqlQuery.list();
	}

	/**
	 * 找出满足到单登记的进口合同
	 */
	@SuppressWarnings("unchecked")
	public List<ImportContract> findInvoiceReg() {
		String sql = "SELECT * FROM improt_contract WHERE state = '生效' "
				+ "AND contract_no IN(SELECT contract_no FROM open_credit WHERE !ISNULL(credit_no)) "
				+ "AND contract_no NOT IN(SELECT contract_no FROM open_credit WHERE ISNULL(credit_no))";//有已登记的信用证，没有未登记的信用证
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(ImportContract.class);
		return sqlQuery.list();
	}

}
