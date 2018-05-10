package com.cbmie.lh.sale.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.service.SaleInvoiceOutRelService;
import com.cbmie.lh.sale.service.SaleInvoiceService;
@Controller
@RequestMapping("sale/invoiceOutRel")
public class SaleInvoiceOutRelController extends BaseController {

	@Autowired
	private SaleInvoiceOutRelService relService;
	@Autowired
	private SaleInvoiceService invoiceService;
	
	@RequestMapping(value="listOutsForInvoice/{invoiceId}")
	@ResponseBody
	public Map<String,Object> getOutStackList(@PathVariable(value="invoiceId") Long invoiceId){
		Map<String, Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> outStackList = relService.getOutStackList(invoiceId);
		List<Map<String,Object>> footer = new ArrayList<Map<String,Object>>();
		Map<String,Object> footerMap = new HashMap<String,Object>();
		BigDecimal quantity = new BigDecimal(0);
		for(Map<String,Object> outStack : outStackList) {
			quantity = quantity.add(new BigDecimal(outStack.get("quantity").toString()));
		}
		footerMap.put("quantity", quantity.setScale(3, BigDecimal.ROUND_HALF_UP).toString());
		footer.add(footerMap);
		
		result.put("total", outStackList.size());
		result.put("rows", outStackList);
		result.put("footer", footer);
		
		return result;
	}
	
	@RequestMapping(value="getOutsSelectItem/{saleContractNo}/{invoiceId}")
	@ResponseBody
	public List<Map<String,Object>> getRemainOutStackList(@PathVariable(value="saleContractNo") String saleContractNo,@PathVariable(value="invoiceId") Long invoiceId){
		return relService.getRemainOutStackList(saleContractNo,invoiceId);
	}
	
	@RequestMapping(value="toOutsSelectPage/{saleContractNo}/{invoiceId}")
	public String toOutStockSelect(@PathVariable(value="saleContractNo") String saleContractNo,@PathVariable(value="invoiceId") Long invoiceId,Model model){
		model.addAttribute("saleContractNo", saleContractNo);
		model.addAttribute("invoiceId", invoiceId);
		return "sale/saleInvoiceOutsSelectPage";
	}
	
	@RequestMapping(value = "saveRel/{invoiceId}")
	@ResponseBody
	public String changeRelation(@PathVariable("invoiceId") Long invoiceId,@RequestBody List<Long> outStockIds){
		SaleInvoice saleInvoice = invoiceService.get(invoiceId);
		relService.changeRelation(saleInvoice, outStockIds);
		return "success";
	}
}
