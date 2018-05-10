package com.cbmie.lh.sale.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleInvoiceSub;
@Repository
public class SaleInvoiceSubDao extends HibernateDao<SaleInvoiceSub, Long> {

	public List<SaleInvoiceSub> getListBySaleContractNo(String saleContractNo){
		String sql = "select a.* from LH_SALE_INVOICE_SUB a join LH_SALE_INVOICE b on a.sale_Invoice_Id=b.id"
				+ " and b.state='1' and a.sale_Contract_No=:saleContractNo order by a.bill_Date asc";
		return this.createSQLQuery(sql).addEntity(SaleInvoiceSub.class).setParameter("saleContractNo", saleContractNo).list();
	}
	
	public List<SaleInvoiceSub> getListBySaleContractNoWoCur(String saleContractNo,Long invoiceId){
		String sql = "select a.* from LH_SALE_INVOICE_SUB a join LH_SALE_INVOICE b on a.sale_Invoice_Id=b.id"
				+ " and b.state='1' and b.sale_Contract_No=:saleContractNo and b.id<>:invoiceId order by a.bill_Date asc";
		return this.createSQLQuery(sql).addEntity(SaleInvoiceSub.class).setParameter("saleContractNo", saleContractNo).setParameter("invoiceId", invoiceId).list();
	}
}