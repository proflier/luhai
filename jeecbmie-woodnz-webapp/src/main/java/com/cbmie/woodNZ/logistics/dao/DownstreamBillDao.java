package com.cbmie.woodNZ.logistics.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.logistics.entity.DownstreamBill;
import com.cbmie.woodNZ.stock.entity.OutStock;

 
/**
 * 下游到单DAO 
 */ 
@Repository
public class DownstreamBillDao extends HibernateDao<DownstreamBill, Long>{

	/**
	 * @param contractId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DownstreamBill> getInvoiceNoByConId(String contractId) {
		String sql = "select * from WOOD_DOWNSTREAM_BILL where confirm='1' and LOCATE('"+contractId +"',relation_sale_contarte)>0";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(DownstreamBill.class);
		return sqlQuery.list();
	}

	public String getChose(Long id) {
		List list = getSession().createCriteria(OutStock.class) 
			.add( Restrictions.eq("downBillsId", id) ) 
			.list(); 
		if(list.size() > 0){
			return "1";
		}else{
			return "0";
		}
	}

	public DownstreamBill findByNo(DownstreamBill downstreamBill) {
			Criteria criteria = getSession().createCriteria(DownstreamBill.class);
			if (downstreamBill.getId() != null) {
				criteria.add(Restrictions.ne("id", downstreamBill.getId()));
			}
			criteria.add(Restrictions.eq("billNo", downstreamBill.getBillNo()));
			return (DownstreamBill)criteria.uniqueResult();
		
	}
} 
 