package com.cbmie.woodNZ.purchaseAgreement.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.woodNZ.purchaseAgreement.entity.PurchaseAgreement;
import com.cbmie.woodNZ.purchaseAgreement.service.PurchaseAgreementService;

public class PurchaseAgreementEndListener implements ExecutionListener {
	
	@Autowired
	PurchaseAgreementService  purchaseAgreementService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PurchaseAgreement purchaseAgreement =  purchaseAgreementService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			purchaseAgreement.setState("作废");
		} else {
			purchaseAgreement.setState("生效");
		}
		purchaseAgreementService.save(purchaseAgreement);
	}

}
