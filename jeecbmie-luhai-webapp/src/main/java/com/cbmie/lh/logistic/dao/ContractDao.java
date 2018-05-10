package com.cbmie.lh.logistic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.Contract;
@Repository
public class ContractDao extends HibernateDao<Contract,Long> {
	public boolean checkCodeUique(Contract contract){
		boolean result = true;
		String hql = "from Contract a where a.contractNo=:code ";
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
}
