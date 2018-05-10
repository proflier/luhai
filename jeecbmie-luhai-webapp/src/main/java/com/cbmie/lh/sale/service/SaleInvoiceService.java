package com.cbmie.lh.sale.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleInvoiceDao;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleInvoiceOutRel;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.lh.sale.entity.SaleSettlementGoods;
import com.cbmie.lh.sale.entity.SaleSettlementOutRel;
@Service
@Transactional
public class SaleInvoiceService extends BaseService<SaleInvoice, Long> {

	@Autowired
	private SaleInvoiceDao invoiceDao;
	@Autowired
	private SaleSettlementService settlementService;
	@Autowired
	private SaleInvoiceOutRelService invoiceOutRelService;
	@Override
	public HibernateDao<SaleInvoice, Long> getEntityDao() {
		return invoiceDao;
	}
	
	public boolean checkCodeUique(SaleInvoice saleInvoice){
		return invoiceDao.checkCodeUique(saleInvoice);
	}
	
	public void fillOutRel(SaleInvoice saleInvoice){
		String preBilling = saleInvoice.getPreBilling();
		if("0".equals(preBilling)){
			String settlementNo = saleInvoice.getInvoiceNo();
			SaleSettlement settlement = settlementService.getSaleSettlementBySettlementNo(settlementNo);
			List<SaleInvoiceOutRel> outRelList_invoice = saleInvoice.getOutRelList();
			List<SaleSettlementOutRel> outRelList_settlement = settlement.getOutRelList();
			List<Long> outIds_add = new ArrayList<Long>();
			for(int i = 0;i<outRelList_invoice.size();i++){
				boolean keepFlag = false;
			a:	for(SaleSettlementOutRel settlementRel : outRelList_settlement){
					if(outRelList_invoice.get(i).getOutstockDetailId().longValue()==settlementRel.getOutstockDetailId().longValue()){
						keepFlag = true;
						break a;
					}
				}
				if(!keepFlag){
					outRelList_invoice.remove(i);
					invoiceOutRelService.delete(outRelList_invoice.get(i));
					i--;
				}
			}
			for(SaleSettlementOutRel settlementRel : outRelList_settlement){
				boolean addFlag = true;
			b:	for(SaleInvoiceOutRel invoiceRel : outRelList_invoice){
					if(settlementRel.getOutstockDetailId().longValue()==invoiceRel.getOutstockDetailId().longValue()){
						addFlag = false;
						break b;
					}
				}
				if(addFlag){
					SaleInvoiceOutRel rel = new SaleInvoiceOutRel();
					rel.setInvoiceId(saleInvoice.getId());
					rel.setOutstockDetailId(settlementRel.getOutstockDetailId());
					invoiceOutRelService.save(rel);
					outRelList_invoice.add(rel);
				}
			}
		}
	}
	
	public List<SaleInvoice> getSaleInvoiceBySettlement(String settlementNo){
		return invoiceDao.getSaleInvoiceBySettlement(settlementNo);
	}
	public List<SaleInvoice> getSaleInvoiceBySettleAndSaleNo(String saleContractNo,String settlementNo){
		return invoiceDao.getSaleInvoiceBySettleAndSaleNo(saleContractNo,settlementNo);
	}
	
	public List<SaleSettlementGoods> SaleInvoicepubList(Long invoiceId) {
		List<SaleSettlementGoods> rows = new ArrayList<SaleSettlementGoods>();
		SaleInvoice saleInvoice = get(invoiceId);
		if(invoiceId!=null){
			SaleSettlement settlement = settlementService.getSaleSettlementBySettlementNo(saleInvoice.getInvoiceNo());
			if(settlement!=null){
				List<SaleSettlementGoods> goods =  settlement.getSaleSettlementSubList();
				for(SaleSettlementGoods good : goods){
					good.setSaleContractNo(settlement.getSaleContractNo());
					rows.add(good);
				}
			}
		}
		return rows;
	}

}
