package com.cbmie.lh.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbmie.accessory.service.AccessoryService;
import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.lh.sale.service.SaleContractService;
import com.cbmie.lh.sale.service.SaleSettlementService;

@Controller
@RequestMapping("mobile/link")
public class Link {
	
	@Autowired
	private SaleContractService saleContractService;
	
	@Autowired
	private SaleSettlementService settlementService;
	
	@Autowired
	private AccessoryService accessoryService;
	
	@RequestMapping(value = "detail/{type}/{value}", method = RequestMethod.GET)
	public String saleContract(@PathVariable("type") String type, @PathVariable("value") String value, Model model) {
		Object obj = null;
		ApprovalOpinion approval = new ApprovalOpinion();
		if (type.equals("saleContract")) {
			obj = saleContractService.getSaleContractByNo(value);//销售合同
			approval.setKey("wf_saleContract");
		} else if (type.equals("saleSettlement")) {
			obj = settlementService.getSaleSettlementBySettlementNo(value);//销售结算
			approval.setKey("wf_saleSettlement");
		}
		model.addAttribute("approval", approval);
		model.addAttribute("obj", obj);
		model.addAttribute("accList", accessoryService.getByObj(obj));//附件
		return "mobile/detail/link";
	}
	
}
