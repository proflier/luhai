package com.cbmie.genMac.shop.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.shop.entity.Goods;
import com.cbmie.genMac.shop.service.GoodsService;

public class GoodsEndListener implements ExecutionListener {
	
	/***
	 * 需使用expression来配置监听器，否则spring管理不到goodsService
	 * */
	@Autowired
	GoodsService goodsService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4439817514082863143L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Goods goods = goodsService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INIT").getValue())){
			goods.setState("作废");
		} else {
			goods.setState("生效");
		}
		goodsService.save(goods);
		
	}

}
