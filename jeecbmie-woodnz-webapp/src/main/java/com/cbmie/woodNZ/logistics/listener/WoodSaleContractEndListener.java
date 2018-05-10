package com.cbmie.woodNZ.logistics.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.woodNZ.logistics.entity.InsuranceContract;
import com.cbmie.woodNZ.logistics.service.InsuranceContractService;

public class WoodSaleContractEndListener implements ExecutionListener {
	
	@Autowired
	InsuranceContractService  insuranceContractService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		InsuranceContract insuranceContract =  insuranceContractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			insuranceContract.setState("作废"); 
		} else { 
			insuranceContract.setState("生效");
		}
		insuranceContractService.save(insuranceContract);
	}

}
