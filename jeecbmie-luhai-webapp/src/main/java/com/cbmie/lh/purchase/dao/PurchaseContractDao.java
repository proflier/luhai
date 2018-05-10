package com.cbmie.lh.purchase.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.purchase.entity.PurchaseContract;

/**
 * 采购合同-DAO
 */
@Repository
public class PurchaseContractDao extends HibernateDao<PurchaseContract, Long> {

	public PurchaseContract findByContractNo(PurchaseContract purchaseContract) {
		Criteria criteria = getSession().createCriteria(PurchaseContract.class);
		if (purchaseContract.getId() != null) {
			criteria.add(Restrictions.ne("id", purchaseContract.getId()));
		}
		criteria.add(Restrictions.eq("purchaseContractNo", purchaseContract.getPurchaseContractNo()));
		//如果合同存在变更需要判断状态
		criteria.add(Restrictions.eq("changeState", "1"));
		return (PurchaseContract) criteria.uniqueResult();
	}
	
	public PurchaseContract findByInnerContractNo(PurchaseContract purchaseContract) {
		Criteria criteria = getSession().createCriteria(PurchaseContract.class);
		if (purchaseContract.getId() != null) {
			criteria.add(Restrictions.ne("id", purchaseContract.getId()));
		}
		criteria.add(Restrictions.eq("innerContractNo", purchaseContract.getInnerContractNo()));
		//如果合同存在变更需要判断状态
		criteria.add(Restrictions.eq("changeState", "1"));
		return (PurchaseContract) criteria.uniqueResult();
	}

	public int getSequence(String param) {
		String sql = "select currval(?)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, param);
		return (Integer)sqlQuery.uniqueResult();
	}
	
	public int getNextSequence(String param) {
		String sql = "select nextval(?)";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, param);
		return (Integer)sqlQuery.uniqueResult();
	}

	public List<PurchaseContract> getAllBak(Long sourceId) {
		String sql = "SELECT * FROM LH_PURCHASE_CONTRACT WHERE  source_id='"+sourceId+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseContract.class);
		return sqlQuery.list();
	}

	public PurchaseContract findByContractNo(String contractNo) {
		String sql = "SELECT * FROM LH_PURCHASE_CONTRACT WHERE change_state=1 and purchase_contract_no='"+contractNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseContract.class);
		List<PurchaseContract> list = sqlQuery.list();
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	public List<Object[]> getSaleShip(String contractNo){
		String sql = "select * from v_sale_ship where contract_no like ? ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter(0, contractNo);
		return sqlQuery.list();
	}

	public PurchaseContract findNoRuleByContractNo(String contractNo) {
		String sql = "SELECT * FROM LH_PURCHASE_CONTRACT WHERE  purchase_contract_no='"+contractNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseContract.class);
		List<PurchaseContract> list = sqlQuery.list();
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	public PurchaseContract findByInnerContractNo(String innerContractNo) {
		String sql = "SELECT * FROM LH_PURCHASE_CONTRACT WHERE change_state=1 and inner_contract_no='"+innerContractNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseContract.class);
		List<PurchaseContract> list = sqlQuery.list();
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	public String getRelLoginNames(List<String> params) {
		String sql = " select GROUP_CONCAT(rel_login_names) from lh_purchase_contract where state=1 and change_state=1 and close_or_open=1 and inner_contract_no in(:params) ";
		String returnValue = (String) getSession().createSQLQuery(sql).setParameterList("params", params).uniqueResult();
		if(StringUtils.isNotBlank(returnValue)){
			return returnValue;
		}else{
			return "";
		}
	}
	
	public List<String> findHavePermission(String loginName) {
		String sql = "SELECT id FROM LH_PURCHASE_CONTRACT WHERE  REL_LOGIN_NAMES like '%"+loginName+"%'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		List<String > returnVlaue = new ArrayList<String >();
		if(sqlQuery.list().size()>0){
			for(Object o : sqlQuery.list()){
				returnVlaue.add(o.toString());
			}
		}
		return returnVlaue;
	}

}
