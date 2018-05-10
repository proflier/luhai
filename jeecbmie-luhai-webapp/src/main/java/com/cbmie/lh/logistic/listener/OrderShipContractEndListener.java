package com.cbmie.lh.logistic.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.logistic.entity.OrderShipContract;
import com.cbmie.lh.logistic.service.OrderShipContractService;

public class OrderShipContractEndListener implements ExecutionListener{

	private static final long serialVersionUID = 86585276518235453L;

	@Autowired
	private OrderShipContractService contractService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		OrderShipContract orderShipContract =  contractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(orderShipContract.getPid()!=null){
				OrderShipContract parent = contractService.get(orderShipContract.getPid());
				parent.setChangeState("1"); //恢复状态
				contractService.update(parent);
				orderShipContract.setChangeState("0");
			}
			orderShipContract.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			orderShipContract.setState(ActivitiConstant.ACTIVITI_EFFECT);
			orderShipContract.setChangeState("1");
		}
		contractService.update(orderShipContract);
	}

}
