package com.cbmie.lh.logistic.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.logistic.entity.ProxyAgreement;
import com.cbmie.lh.logistic.service.ProxyAgreementService;

public class ProxyAgreementEndListener implements ExecutionListener{

	private static final long serialVersionUID = 86585276518235453L;

	@Autowired
	private ProxyAgreementService proxyAgreementService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		ProxyAgreement insuranceContract =  proxyAgreementService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			insuranceContract.setState("0"); 
		} else { 
			insuranceContract.setState("1");
		}
		proxyAgreementService.save(insuranceContract);
	}

}
