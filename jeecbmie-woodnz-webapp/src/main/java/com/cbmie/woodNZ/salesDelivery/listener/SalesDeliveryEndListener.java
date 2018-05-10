package com.cbmie.woodNZ.salesDelivery.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery;
import com.cbmie.woodNZ.salesDelivery.service.SalesDeliveryService;

public class SalesDeliveryEndListener implements ExecutionListener {
	
	@Autowired
	SalesDeliveryService  salesDeliveryService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		SalesDelivery salesDelivery =  salesDeliveryService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			salesDelivery.setState("作废");
		} else {
			salesDelivery.setState("生效");
		}
		salesDeliveryService.save(salesDelivery);
	}

}
