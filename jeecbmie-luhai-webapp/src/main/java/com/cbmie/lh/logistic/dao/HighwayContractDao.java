package com.cbmie.lh.logistic.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.HighwayContract;
@Repository
public class HighwayContractDao extends HibernateDao<HighwayContract, Long> {
	public boolean checkCodeUique(HighwayContract contract){
		boolean result = true;
		String hql = "from HighwayContract a where a.contractNo=:code ";
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
	
	public HighwayContract getByInnerNo(String contractNo) {
		String hql = "from HighwayContract a where a.innerContractNo=:code order by a.id desc";
		List<HighwayContract> list = this.createQuery(hql).setParameter("code", contractNo).list();
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	public List<HighwayContract> getHighwayContractBak(Long sourceId, Long curId) {
		String sql = "select * from LH_HIGHWAY_CONTRACT a where 1=1 and "
				+ " ((a.change_State!='2' and a.source_Id=:sourceId and a.id<>:curId ) or id=:sourceId ) order by id asc ";
		List<HighwayContract> list = this.createSQLQuery(sql).addEntity(HighwayContract.class)
				.setParameter("sourceId", sourceId).setParameter("curId", curId).list();
		return list;
	}

	public List<String> findHavePermission(String loginName) {
		String sql = "SELECT id FROM LH_HIGHWAY_CONTRACT WHERE  REL_LOGIN_NAMES like '%"+loginName+"%'";
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
