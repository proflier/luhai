package com.cbmie.genMac.foreignTrade.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.foreignTrade.entity.AgencyAgreement;
import com.cbmie.genMac.foreignTrade.service.AgencyAgreementService;

public class AgencyAgreementEndListener implements ExecutionListener {
	
	/***
	 * 需使用expression来配置监听器，否则spring管理不到agencyAgreementService
	 * */
	@Autowired
	AgencyAgreementService agencyAgreementService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		AgencyAgreement agencyAgreement = agencyAgreementService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			agencyAgreement.setState("作废");
		} else {
			agencyAgreement.setState("生效");
		}
		agencyAgreementService.save(agencyAgreement);
		
	}

}
