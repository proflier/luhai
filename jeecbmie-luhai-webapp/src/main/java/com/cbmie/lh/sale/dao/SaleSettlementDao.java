package com.cbmie.lh.sale.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleSettlement;
@Repository
public class SaleSettlementDao extends HibernateDao<SaleSettlement, Long> {
	public boolean checkCodeUique(SaleSettlement saleSettlement){
		boolean result = true;
		String hql = "from SaleSettlement a where a.saleSettlementNo=:saleSettlementNo ";
		if(saleSettlement.getId() != null){
			hql += " and a.id<>"+saleSettlement.getId();
		}
		List list = this.createQuery(hql).setParameter("saleSettlementNo", saleSettlement.getSaleSettlementNo()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
	
	public List<SaleSettlement> getSaleSettlementByContractNo(String saleContractNo){
		String hql = "from SaleSettlement a where a.saleContractNo=:saleContractNo and a.state='1'";
		List<SaleSettlement> list = this.createQuery(hql).setParameter("saleContractNo", saleContractNo).list();
		return list;
	}
	
	public SaleSettlement getSaleSettlementBySettlementNo(String saleSettlementNo){
		String hql = "from SaleSettlement a where a.saleSettlementNo=:saleSettlementNo";
		List<SaleSettlement> list = this.createQuery(hql).setParameter("saleSettlementNo", saleSettlementNo).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
