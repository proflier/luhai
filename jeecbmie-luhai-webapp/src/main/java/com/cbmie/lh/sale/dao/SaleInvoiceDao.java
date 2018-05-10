package com.cbmie.lh.sale.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleInvoice;
@Repository
public class SaleInvoiceDao extends HibernateDao<SaleInvoice, Long> {
	public boolean checkCodeUique(SaleInvoice saleInvoice){
		boolean result = true;
		String hql = "from SaleInvoice a where a.invoiceNo=:invoiceNo ";
		if(saleInvoice.getId() != null){
			hql += " and a.id<>"+saleInvoice.getId();
		}
		List list = this.createQuery(hql).setParameter("invoiceNo", saleInvoice.getInvoiceNo()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
	
	public List<SaleInvoice> getSaleInvoiceBySettlement(String settlementNo){
		String hql = "from SaleInvoice a where a.invoiceNo=:settlementNo ";
		List list = this.createQuery(hql).setParameter("settlementNo", settlementNo).list();
		return list;
	}
	
	public List<SaleInvoice> getSaleInvoiceBySettleAndSaleNo(String saleContractNo,String settlementNo){
		String sql = "select * from LH_SALE_INVOICE a where a.state='1' and a.sale_Contract_No=:saleContractNo "
				+ " and (a.invoice_No is null or a.invoice_No=:settlementNo or a.invoice_No='') order by a.id asc ";
		List<SaleInvoice> list = this.createSQLQuery(sql).addEntity(SaleInvoice.class).setParameter("settlementNo", settlementNo)
		.setParameter("saleContractNo", saleContractNo).list();
		return list;
	}
}
