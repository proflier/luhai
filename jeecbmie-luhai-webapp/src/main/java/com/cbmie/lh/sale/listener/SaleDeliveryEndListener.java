package com.cbmie.lh.sale.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.service.SaleDeliveryService;

public class SaleDeliveryEndListener implements ExecutionListener{

	private static final long serialVersionUID = -2077944014128704999L;
	
	@Autowired
	private SaleDeliveryService service;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		SaleDelivery saleDelivery =  service.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(saleDelivery.getPid()!=null){
				SaleDelivery parent = service.get(saleDelivery.getPid());
				parent.setChangeState("1"); //恢复状态
				service.update(parent);
				saleDelivery.setChangeState("0");
			}
			saleDelivery.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			saleDelivery.setState(ActivitiConstant.ACTIVITI_EFFECT);
			saleDelivery.setChangeState("1");
		}
		service.update(saleDelivery);
	}

}
