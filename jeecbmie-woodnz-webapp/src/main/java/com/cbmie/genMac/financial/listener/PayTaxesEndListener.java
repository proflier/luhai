package com.cbmie.genMac.financial.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.financial.entity.PayTaxes;
import com.cbmie.genMac.financial.service.PayTaxesService;

public class PayTaxesEndListener implements ExecutionListener {
	
	@Autowired
	PayTaxesService  payTaxesService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PayTaxes payTaxes =  payTaxesService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			payTaxes.setState("作废");
		} else {
			payTaxes.setState("生效");
		}
		payTaxesService.save(payTaxes);
	}

}
