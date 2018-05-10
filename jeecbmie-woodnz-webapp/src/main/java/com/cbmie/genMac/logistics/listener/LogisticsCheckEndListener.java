package com.cbmie.genMac.logistics.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.logistics.entity.LogisticsCheck;
import com.cbmie.genMac.logistics.service.LogisticsCheckService;

public class LogisticsCheckEndListener implements ExecutionListener {
	
	@Autowired
	LogisticsCheckService  logisticsCheckService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		LogisticsCheck logisticsCheck =  logisticsCheckService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			logisticsCheck.setState("作废");
		} else {
			logisticsCheck.setState("生效");
		}
		logisticsCheckService.save(logisticsCheck);
	}

}
