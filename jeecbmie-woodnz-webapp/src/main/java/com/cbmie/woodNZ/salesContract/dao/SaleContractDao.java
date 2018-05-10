package com.cbmie.woodNZ.salesContract.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.logistics.entity.WoodBills;
import com.cbmie.woodNZ.offerManagement.entity.ProflitLossAccounting;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContractSub;
import com.cbmie.woodNZ.stock.entity.OutStockCache;

 
/**
 * 销售主表DAO 
 */ 
@Repository
public class SaleContractDao extends HibernateDao<WoodSaleContract, Long>{

	public Long getIdByContractNo(String saleContractNo) {
		String sql = "select * from wood_sale_contract where 1=1 and contract_no='"+saleContractNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodSaleContract.class);
		List<WoodSaleContract>  list =  sqlQuery.list();
		if(list!=null && !list.isEmpty()){
			Long id = list.get(0).getId();
			return id;
		}
		return null;
	}

	public List<OutStockCache> getCacheByContractNo(String saleContractNo) {
		String sql = "select * from v_wood_out_stock_cache  where 1=1 and sale_contract_no like '%"+saleContractNo+"%'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(OutStockCache.class);
		List<OutStockCache>  list =  sqlQuery.list();
		if(list!=null && !list.isEmpty()){
			return list;
		}
		return null;
	}
	
	public boolean validateContractNo(String contractNo, Long id) {
		Criteria criteria = getSession().createCriteria(WoodSaleContract.class);
		if (id != null) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("contractNo", contractNo));
		List list = criteria.list();
		if(list!=null && list.size()>0){//当前合同号和其他合同号重复
			return true;//合同号重复
		}
		return false;//合同号不重复
	}
	
} 
 