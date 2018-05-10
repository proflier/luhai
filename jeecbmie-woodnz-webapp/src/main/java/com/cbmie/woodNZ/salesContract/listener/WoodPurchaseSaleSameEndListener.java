package com.cbmie.woodNZ.salesContract.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSame;
import com.cbmie.woodNZ.salesContract.service.PurchaseSaleSameService;

public class WoodPurchaseSaleSameEndListener implements ExecutionListener {
	
	@Autowired
	PurchaseSaleSameService  purchaseSaleSameService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PurchaseSaleSame purchaseSaleSame =  purchaseSaleSameService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			purchaseSaleSame.setState("作废");
		} else {
			purchaseSaleSame.setState("生效");
		}
		purchaseSaleSameService.save(purchaseSaleSame);
	}

}
