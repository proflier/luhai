package com.cbmie.lh.sale.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.service.SaleInvoiceService;

public class SaleInvoiceEndListener implements ExecutionListener{

	private static final long serialVersionUID = 3039327568618354809L;
	@Autowired
	private SaleInvoiceService service;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		SaleInvoice saleInvoice =  service.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			saleInvoice.setState("0"); 
		} else { 
			saleInvoice.setState("1");
		}
		service.save(saleInvoice);
	}
}
