package com.cbmie.lh.financial.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.financial.service.PaymentConfirmService;

public class PaymentConfirmEndListener implements ExecutionListener {

	private static final long serialVersionUID = 8119625195579356474L;
	@Autowired
	private PaymentConfirmService confrimService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PaymentConfirm paymentConfirm =  confrimService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(paymentConfirm.getPid()!=null){
				PaymentConfirm parent = confrimService.get(paymentConfirm.getPid());
				parent.setChangeState("1"); //恢复状态
				confrimService.update(parent);
				paymentConfirm.setChangeState("0");
			}
			paymentConfirm.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			paymentConfirm.setState(ActivitiConstant.ACTIVITI_EFFECT);
			paymentConfirm.setChangeState("1");
		}
		confrimService.update(paymentConfirm);
	}

}
