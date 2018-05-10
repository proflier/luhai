package com.cbmie.lh.purchase.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.purchase.entity.PurchaseAgreement;
import com.cbmie.lh.purchase.service.PurchaseAgreementService;

public class PurchaseAgreementEndListener implements ExecutionListener {
	
	@Autowired
	PurchaseAgreementService  purchaseAgreementService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PurchaseAgreement purchaseAgreement =  purchaseAgreementService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			purchaseAgreement.setState("0");
		} else {
			purchaseAgreement.setState("1");
		}
		purchaseAgreementService.save(purchaseAgreement);
	}

}
