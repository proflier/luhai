package com.cbmie.woodNZ.importShips.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.woodNZ.importShips.entity.BookShip;
import com.cbmie.woodNZ.importShips.service.BookShipService;
/**
 * 定船合同审批
 * @author linxiaopeng
 * 2016年7月5日
 */
public class BookShipEndListener implements ExecutionListener {
	
	@Autowired
	BookShipService  bookShipService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		BookShip bookShip =  bookShipService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			bookShip.setState("作废");
		} else {
			bookShip.setState("生效");
		}
		bookShipService.save(bookShip);
	}

}
