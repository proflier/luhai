package com.cbmie.genMac.stock.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.stock.entity.EnterpriseStockCheck;
import com.cbmie.genMac.stock.service.EnterpriseStockCheckService;

public class EnterpriseStockCheckEndListener implements ExecutionListener {
	
	@Autowired
	EnterpriseStockCheckService  enterpriseStockCheckService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		EnterpriseStockCheck enterpriseStockCheck =  enterpriseStockCheckService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			enterpriseStockCheck.setState("作废");
		} else {
			enterpriseStockCheck.setState("生效");
		}
		enterpriseStockCheckService.save(enterpriseStockCheck);
	}

}
