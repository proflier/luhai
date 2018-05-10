package com.cbmie.lh.logistic.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.OrderShipContract;
@Repository
public class OrderShipContractDao extends HibernateDao<OrderShipContract, Long> {
	public boolean checkCodeUique(OrderShipContract contract){
		boolean result = true;
		String hql = "from OrderShipContract a where a.contractNo=:code ";
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
	
	public boolean checkInnerCodeUique(OrderShipContract contract){
		boolean result = true;
		String hql = "from OrderShipContract a where a.innerContractNo=:code ";
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
	
	public OrderShipContract getOrderShipContractByNo(String contractNo){
		String hql = "from OrderShipContract a where a.contractNo=:contractNo";
		List<OrderShipContract> list = this.createQuery(hql).setParameter("contractNo", contractNo).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public OrderShipContract getOrderShipContractByInnerNo(String innerNo){
		String hql = "from OrderShipContract a where a.innerContractNo=:innerNo";
		List<OrderShipContract> list = this.createQuery(hql).setParameter("innerNo", innerNo).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<String> findHavePermission(String loginName) {
		String sql = "SELECT id FROM LH_ORDERSHIP_CONTRACT WHERE  REL_LOGIN_NAMES like '%"+loginName+"%'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		List<String > returnVlaue = new ArrayList<String >();
		if(sqlQuery.list().size()>0){
			for(Object o : sqlQuery.list()){
				returnVlaue.add(o.toString());
			}
		}
		return returnVlaue;
	}

	public List<OrderShipContract> getOrderShipContractBak(Long sourceId, Long curId) {
		String sql = "select * from LH_ORDERSHIP_CONTRACT a where 1=1 and "
				+ " ((a.change_State!='2' and a.source_Id=:sourceId and a.id<>:curId ) or id=:sourceId ) order by id asc ";
		List<OrderShipContract> list = this.createSQLQuery(sql).addEntity(OrderShipContract.class)
				.setParameter("sourceId", sourceId).setParameter("curId", curId).list();
		return list;
	}

	public String getShipNameById(Long id) {
		String sql ="select GROUP_CONCAT(CONCAT(b.ship_no,IFNULL(c.ship_name,''))) from lh_ordership_contract a "+
					"join lh_ordership_contractsub b on a.id = b.order_ship_contract_id "+
					"left join lh_ship_trace c on b.ship_no= c.ship_no "+
					"where a.id="+id ;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return (String) sqlQuery.uniqueResult();
	}
}
