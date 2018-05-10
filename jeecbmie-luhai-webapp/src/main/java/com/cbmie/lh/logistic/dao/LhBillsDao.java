package com.cbmie.lh.logistic.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.LhBills;
 
/**
 * 到单DAO 
 */ 
@Repository
public class LhBillsDao extends HibernateDao<LhBills, Long>{

	public boolean validateBillNo(String billNo, Long id) {
		Criteria criteria = getSession().createCriteria(LhBills.class);
		if (id != null) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("billNo", billNo));
		List list = criteria.list();
		if(list!=null && list.size()>0){//当前提单号和其他提单号重复
			return true;//提单号重复
		}
		return false;//提单号不重复
	}

	public String getOurOrCustomer(String loginName) {
		String sql =" select DISTINCT customer_code from (select  DISTINCT customer_code  from customer_perssion where LOCATE('"+loginName+"',ranges) or business_manager='"+loginName+"' UNION select customer_code from wood_affi_base_info where  customer_type like '%10230001%' and customer_type not like '%10230003%' ) a ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		List<String> returnList = sqlQuery.list();
		String returnValue ="";
		if(returnList.size()>0){
			returnValue = StringUtils.join(returnList.toArray(), ",");
		}
		return returnValue;
	}

	public List<LhBills> findByContractNo(String innerContractNo) {
		String sql =" select * from lh_bills  where lh_bills.purchase_contract_ids like '%"+innerContractNo+"%' ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(LhBills.class);
		return sqlQuery.list();
	}
} 
 