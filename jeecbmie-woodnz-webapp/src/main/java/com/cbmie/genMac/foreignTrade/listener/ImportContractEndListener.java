package com.cbmie.genMac.foreignTrade.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.foreignTrade.entity.ImportContract;
import com.cbmie.genMac.foreignTrade.service.ImportContractService;

public class ImportContractEndListener implements ExecutionListener {
	
	/***
	 * 需使用expression来配置监听器，否则spring管理不到improtContractService
	 * */
	@Autowired
	ImportContractService improtContractService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		ImportContract importContract = improtContractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			importContract.setState("作废");
		} else {
			importContract.setState("生效");
		}
		improtContractService.save(importContract);
		
	}

}
