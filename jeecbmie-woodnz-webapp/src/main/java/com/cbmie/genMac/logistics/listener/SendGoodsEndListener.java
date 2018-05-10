package com.cbmie.genMac.logistics.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.logistics.entity.SendGoods;
import com.cbmie.genMac.logistics.service.SendGoodsService;

public class SendGoodsEndListener implements ExecutionListener {
	
	@Autowired
	SendGoodsService sendGoodsService;

	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		SendGoods sendGoods = sendGoodsService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			sendGoods.setState("作废");
		} else {
			sendGoods.setState("生效");
		}
		sendGoodsService.save(sendGoods);
		
	}

}
