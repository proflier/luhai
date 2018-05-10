package com.cbmie.lh.sale.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleInvoiceSubDao;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleInvoiceSub;
@Service
public class SaleInvoiceSubService extends BaseService<SaleInvoiceSub, Long> {

	@Autowired
	private SaleInvoiceSubDao subDao;
	
	@Autowired
	private SaleInvoiceService invoiceService;
	
	@Override
	public HibernateDao<SaleInvoiceSub, Long> getEntityDao() {
		return subDao;
	}

	public List<SaleInvoiceSub> getListBySaleContractNo(String saleContractNo){
		return subDao.getListBySaleContractNo(saleContractNo);
	}
	
	public List<SaleInvoiceSub> getOldListByPid(Long saleInvoiceId) {
		List<SaleInvoiceSub> rows = new ArrayList<SaleInvoiceSub>();
		SaleInvoice saleInvoice = invoiceService.get(saleInvoiceId);
		List<SaleInvoiceSub> t = subDao.getListBySaleContractNoWoCur(saleInvoice.getSaleContractNo(),saleInvoiceId);
		if(t!=null && t.size()>0){
			rows.addAll(t);
		}
		return rows;
	}
}
