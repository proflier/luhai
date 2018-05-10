package com.cbmie.lh.logistic.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.logistic.entity.InsuranceContract;
import com.cbmie.lh.logistic.service.InsuranceContractService;

public class InsuranceContractEndListener implements ExecutionListener{

	@Autowired
	InsuranceContractService  insuranceContractService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		InsuranceContract insuranceContract =  insuranceContractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(insuranceContract.getPid()!=null){
				InsuranceContract parent = insuranceContractService.get(insuranceContract.getPid());
				parent.setChangeState("1"); //恢复状态
				insuranceContractService.update(parent);
				insuranceContract.setChangeState("0");
			}
			insuranceContract.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			insuranceContract.setState(ActivitiConstant.ACTIVITI_EFFECT);
			insuranceContract.setChangeState("1");
		}
		insuranceContractService.update(insuranceContract);
	}
}
