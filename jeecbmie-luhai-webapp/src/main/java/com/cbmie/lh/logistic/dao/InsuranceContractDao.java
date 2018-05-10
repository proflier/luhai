package com.cbmie.lh.logistic.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.InsuranceContract;

/**
 * 保险合同DAO 
 */ 
@Repository
public class InsuranceContractDao extends HibernateDao<InsuranceContract, Long>{
	
	public boolean checkInnerContractNoUnique(InsuranceContract contract){
		boolean result = true;
		String hql = "from InsuranceContract a where a.innerContractNo=:code ";
		if(contract.getId() != null){
			hql += " and a.id<>"+contract.getId();
		}
		@SuppressWarnings("rawtypes")
		List list = this.createQuery(hql).setParameter("code", contract.getInnerContractNo()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
	
	public InsuranceContract findByContractNo(String contractNo) {
		Criteria criteria = getSession().createCriteria(InsuranceContract.class);
		criteria.add(Restrictions.eq("contractNo", contractNo));
		criteria.add(Restrictions.ne("changeState", "0"));
		return (InsuranceContract)criteria.uniqueResult();
	}
	
	public InsuranceContract getByInnerNo(String innerContractNo) {
		Criteria criteria = getSession().createCriteria(InsuranceContract.class);
		criteria.add(Restrictions.eq("innerContractNo", innerContractNo));
		criteria.add(Restrictions.ne("changeState", "0"));
		return (InsuranceContract)criteria.uniqueResult();
	}

	public List<InsuranceContract> getPortContractBak(Long sourceId, Long curId) {
		String sql = "select * from LH_INSURANCE_CONTRACT a where 1=1 and "
				+ " ((a.change_State!='2' and a.source_Id=:sourceId and a.id<>:curId ) or id=:sourceId ) order by id asc ";
		List<InsuranceContract> list = this.createSQLQuery(sql).addEntity(InsuranceContract.class)
				.setParameter("sourceId", sourceId).setParameter("curId", curId).list();
		return list;
	}

	public List<String> findHavePermission(String loginName) {
		String sql = "SELECT id FROM LH_INSURANCE_CONTRACT WHERE  REL_LOGIN_NAMES like '%"+loginName+"%'";
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
 