package com.cbmie.lh.logistic.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.logistic.entity.RailwayContract;
import com.cbmie.lh.logistic.service.RailwayContractService;

public class RailwayContractEndListener implements ExecutionListener{

	private static final long serialVersionUID = 86585276518235453L;

	@Autowired
	private RailwayContractService contractService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		RailwayContract railwayContract =  contractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(railwayContract.getPid()!=null){
				RailwayContract parent = contractService.get(railwayContract.getPid());
				parent.setChangeState("1"); //恢复状态
				contractService.update(parent);
				railwayContract.setChangeState("0");
			}
			railwayContract.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			railwayContract.setState(ActivitiConstant.ACTIVITI_EFFECT);
			railwayContract.setChangeState("1");
		}
		contractService.update(railwayContract);
	}

}
