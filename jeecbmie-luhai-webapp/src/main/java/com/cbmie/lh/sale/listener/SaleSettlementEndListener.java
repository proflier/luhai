package com.cbmie.lh.sale.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.lh.sale.service.SaleInvoiceService;
import com.cbmie.lh.sale.service.SaleSettlementService;

public class SaleSettlementEndListener implements ExecutionListener{

	private static final long serialVersionUID = 4262608625982776695L;

	@Autowired
	private SaleSettlementService service;
	@Autowired
	private SaleInvoiceService saleInvoiceService;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		SaleSettlement saleSettlement =  service.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			List<SaleInvoice> saleInvoices = saleInvoiceService.getSaleInvoiceBySettlement(saleSettlement.getSaleSettlementNo());
			for(SaleInvoice invoice : saleInvoices){
				if("1".equals(invoice.getPreBilling())){
					invoice.setInvoiceNo(null);
					saleInvoiceService.update(invoice);
				}
			}
			saleSettlement.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			saleSettlement.setState(ActivitiConstant.ACTIVITI_EFFECT);
		}
		service.save(saleSettlement);
	}

}
