package com.cbmie.lh.logistic.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.logistic.entity.HighwayContract;
import com.cbmie.lh.logistic.service.HighwayContractService;

public class HighwayContractContractEndListener implements ExecutionListener{

	private static final long serialVersionUID = 86585276518235453L;

	@Autowired
	private HighwayContractService contractService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		HighwayContract highwayContract =  contractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(highwayContract.getPid()!=null){
				HighwayContract parent = contractService.get(highwayContract.getPid());
				parent.setChangeState("1"); //恢复状态
				contractService.update(parent);
				highwayContract.setChangeState("0");
			}
			highwayContract.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			highwayContract.setState(ActivitiConstant.ACTIVITI_EFFECT);
			highwayContract.setChangeState("1");
		}
		contractService.update(highwayContract);
	}

}
