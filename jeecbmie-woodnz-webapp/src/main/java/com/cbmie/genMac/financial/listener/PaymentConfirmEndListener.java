package com.cbmie.genMac.financial.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.financial.entity.PaymentConfirm;
import com.cbmie.genMac.financial.service.PaymentConfirmService;

public class PaymentConfirmEndListener implements ExecutionListener {

	private static final long serialVersionUID = 8119625195579356474L;
	@Autowired
	private PaymentConfirmService confrimService;
	@Override
	public void notify(DelegateExecution arg0) throws Exception {
				PaymentConfirm confirm = confrimService.get(Long.parseLong(arg0.getProcessBusinessKey()));
				if(null != arg0.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
					confirm.setState("作废");
				} else {
					confirm.setState("生效");
				}
				confrimService.save(confirm);

	}

}
