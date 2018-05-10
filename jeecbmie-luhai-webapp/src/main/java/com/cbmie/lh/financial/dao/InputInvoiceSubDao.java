package com.cbmie.lh.financial.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.financial.entity.InputInvoiceSub;
@Repository
public class InputInvoiceSubDao extends HibernateDao<InputInvoiceSub, Long> {

	public List<InputInvoiceSub> getInputInvoiceSubBySaleNo(String saleConctractNo){
		String sql = "select a.* from LH_INPUT_INVOICESUB a join LH_INPUT_INVOICE b on a.input_Invoice_Sub_Id=b.id"
				+ " where b.state='1' and a.related_Sales_Order=:saleConctractNo order by a.id asc";
		return this.createSQLQuery(sql).addEntity(InputInvoiceSub.class).setParameter("saleConctractNo", saleConctractNo).list();
	}
}
