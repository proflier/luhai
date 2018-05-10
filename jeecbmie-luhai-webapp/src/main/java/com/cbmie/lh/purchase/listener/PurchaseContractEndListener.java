package com.cbmie.lh.purchase.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.purchase.service.PurchaseContractService;

public class PurchaseContractEndListener implements ExecutionListener {
	
	@Autowired
	PurchaseContractService  purchaseContractService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		PurchaseContract purchaseContract =  purchaseContractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		ExecutionListenerExecution e = (ExecutionListenerExecution) execution;
		if(Enum.valueOf(AbolishReason.class, "GIVEUP").getValue().equals(e.getDeleteReason())){
			return;
		}
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			purchaseContract.setState("0");
			if(purchaseContract.getPid()!=null){
				PurchaseContract purchaseContractParent = purchaseContractService.get(purchaseContract.getPid());
				purchaseContractParent.setChangeState("1"); //恢复状态
				purchaseContractService.save(purchaseContractParent);
			}
			purchaseContract.setState(ActivitiConstant.ACTIVITI_ABOLISH);
			purchaseContract.setChangeState("0");
		} else {
			//流程通过申请
			purchaseContract.setState("1");
			//如果存在变更 设置变更状态生效
			purchaseContract.setChangeState("1");
		}
		purchaseContractService.save(purchaseContract);
	}

}
