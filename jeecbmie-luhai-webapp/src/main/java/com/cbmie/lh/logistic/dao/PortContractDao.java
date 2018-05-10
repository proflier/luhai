package com.cbmie.lh.logistic.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.PortContract;

@Repository
public class PortContractDao extends HibernateDao<PortContract, Long> {

	public boolean checkCodeUique(PortContract contract){
		boolean result = true;
		String hql = "from PortContract a where a.contractNo=:code ";
		if(contract.getId() != null){
			hql += " and a.id<>"+contract.getId();
		}
		@SuppressWarnings("rawtypes")
		List list = this.createQuery(hql).setParameter("code", contract.getContractNo()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
	
	public PortContract findByContractNo(String contractNo) {
		Criteria criteria = getSession().createCriteria(PortContract.class);
		criteria.add(Restrictions.eq("contractNo", contractNo));
		criteria.add(Restrictions.eq("changeState", "1"));
		return (PortContract)criteria.uniqueResult();
	}
	
	
	public List<PortContract> getPortContractBak(Long sourceId,Long curId){
		String sql = "select * from LH_LOGISTIC_PORTCONTRACT a where 1=1 and "
				+ " ((a.change_State!='2' and a.source_Id=:sourceId and a.id<>:curId ) or id=:sourceId ) order by id asc ";
		List<PortContract> list = this.createSQLQuery(sql).addEntity(PortContract.class)
				.setParameter("sourceId", sourceId).setParameter("curId", curId).list();
		return list;
	}

	public List<String> findHavePermission(String loginName) {
		String sql = "SELECT id FROM LH_LOGISTIC_PORTCONTRACT WHERE  REL_LOGIN_NAMES like '%"+loginName+"%'";
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
