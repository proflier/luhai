package com.cbmie.lh.sale.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleInvoiceOutRelDao;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleInvoiceOutRel;
import com.cbmie.lh.sale.entity.SaleSettlementOutRel;
@Service
@Transactional
public class SaleInvoiceOutRelService extends BaseService<SaleInvoiceOutRel, Long> {

	@Autowired
	private SaleInvoiceOutRelDao relDao;
	@Override
	public HibernateDao<SaleInvoiceOutRel, Long> getEntityDao() {
		return relDao;
	}
	
	public List<Map<String,Object>> getOutStackList(Long invoiceId){
		return relDao.getOutStackList(invoiceId);
	}

	public List<Map<String,Object>> getRemainOutStackList(String saleContractNo,Long invoiceId){
		return relDao.getRemainOutStackList(saleContractNo,invoiceId);
	}
	
	public void changeRelation(SaleInvoice saleInvoice, List<Long> outStockIds){
		List<SaleInvoiceOutRel> rels = saleInvoice.getOutRelList();
		for(int i=0;i<rels.size();i++){
			SaleInvoiceOutRel cur = rels.get(i);
			boolean keepFlag = false;
			a: for(int j=0;j<outStockIds.size();j++){
				if(cur.getId().longValue()==outStockIds.get(j).longValue()){
					outStockIds.remove(j);
					keepFlag = true;
					break a;
				}
			}
			if(!keepFlag){
				rels.remove(cur);
				this.delete(cur);
				i--;
			}
		}
		if(outStockIds!=null && outStockIds.size()>0){
			for(Long outId : outStockIds){
				SaleInvoiceOutRel rel = new SaleInvoiceOutRel();
				rel.setInvoiceId(saleInvoice.getId());
				rel.setOutstockDetailId(outId);
				rels.add(rel);
				this.save(rel);
			}
		}
	}
}
