package com.cbmie.lh.logistic.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.logistic.entity.Contract;
import com.cbmie.lh.logistic.service.ContractService;

public class ContractEndListener implements ExecutionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 269185271918904230L;
	@Autowired
	private ContractService contractService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Contract contract =  contractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			contract.setState("0"); 
		} else { 
			contract.setState("1");
		}
		contractService.save(contract);
	}
}
