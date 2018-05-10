package com.cbmie.lh.credit.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.credit.service.PayApplyService;

public class PayApplyEndListener implements ExecutionListener {
	
	@Autowired
	PayApplyService  payApplyService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PayApply payApply =  payApplyService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(payApply.getPid()!=null){
				PayApply parent = payApplyService.get(payApply.getPid());
				parent.setChangeState("1"); //恢复状态
				payApplyService.update(parent);
			}
			payApply.setState(ActivitiConstant.ACTIVITI_ABOLISH);
			payApply.setChangeState("0");
		}else{ //生效
			payApply.setState(ActivitiConstant.ACTIVITI_EFFECT);
			payApply.setChangeState("1");
		}
		payApplyService.update(payApply);
	}

}
