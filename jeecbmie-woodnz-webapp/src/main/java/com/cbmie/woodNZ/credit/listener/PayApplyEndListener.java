package com.cbmie.woodNZ.credit.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.woodNZ.credit.entity.PayApply;
import com.cbmie.woodNZ.credit.service.PayApplyService;

public class PayApplyEndListener implements ExecutionListener {
	
	@Autowired
	PayApplyService  payApplyService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PayApply payApply =  payApplyService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			payApply.setState("作废");
		} else {
			payApply.setState("生效");
		}
		payApplyService.save(payApply);
	}

}
