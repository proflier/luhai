package com.cbmie.genMac.financial.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.financial.entity.Expense;
import com.cbmie.genMac.financial.service.ExpenseService;

public class ExpenseEndListener implements ExecutionListener {
	
	@Autowired
	ExpenseService  expenseService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Expense expense =  expenseService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			expense.setState("作废");
		} else {
			expense.setState("生效");
		}
		expenseService.save(expense);
	}

}
