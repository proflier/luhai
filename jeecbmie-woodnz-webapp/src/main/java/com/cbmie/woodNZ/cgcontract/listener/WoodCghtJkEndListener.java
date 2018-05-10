package com.cbmie.woodNZ.cgcontract.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkService;

public class WoodCghtJkEndListener implements ExecutionListener {
	
	@Autowired
	 WoodCghtJkService  woodCghtJkService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		WoodCghtJk woodCghtJk =  woodCghtJkService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			woodCghtJk.setState("作废");
		} else {
			woodCghtJk.setState("生效");
		}
		woodCghtJkService.save(woodCghtJk);
	}

}
