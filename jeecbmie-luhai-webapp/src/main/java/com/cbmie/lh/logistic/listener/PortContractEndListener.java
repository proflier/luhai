package com.cbmie.lh.logistic.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.logistic.entity.PortContract;
import com.cbmie.lh.logistic.service.PortContractService;

public class PortContractEndListener implements ExecutionListener{

	private static final long serialVersionUID = 86585276518235453L;

	@Autowired
	private PortContractService contractService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PortContract portContract =  contractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(portContract.getPid()!=null){
				PortContract parent = contractService.get(portContract.getPid());
				parent.setChangeState("1"); //恢复状态
				contractService.update(parent);
				portContract.setChangeState("0");
			}
			portContract.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			portContract.setState(ActivitiConstant.ACTIVITI_EFFECT);
			portContract.setChangeState("1");
		}
		contractService.update(portContract);
	}

}
