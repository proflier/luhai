package com.cbmie.woodNZ.salesContract.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.cbmie.woodNZ.salesContract.service.SaleContractService;

public class WoodSaleContractEndListener implements ExecutionListener {
	
	@Autowired
	SaleContractService  saleContractService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		WoodSaleContract woodSaleContract =  saleContractService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			woodSaleContract.setState("作废");
		} else {
			woodSaleContract.setState("生效");
		}
		saleContractService.save(woodSaleContract);
	}

}
