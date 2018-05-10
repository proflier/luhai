package com.cbmie.lh.sale.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.service.SaleContractService;

public class SaleContractEndListener implements ExecutionListener{

	private static final long serialVersionUID = 86585276518235453L;

	@Autowired
	private SaleContractService contractService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		SaleContract saleContract =  contractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){  //废除
			if(saleContract.getPid()!=null){
				SaleContract parent = contractService.get(saleContract.getPid());
				parent.setChangeState("1"); //恢复状态
				contractService.update(parent);
				saleContract.setChangeState("0");
			}
			saleContract.setState(ActivitiConstant.ACTIVITI_ABOLISH);
		}else{ //生效
			saleContract.setState(ActivitiConstant.ACTIVITI_EFFECT);
			saleContract.setChangeState("1");
		}
		contractService.update(saleContract);
	}

}
