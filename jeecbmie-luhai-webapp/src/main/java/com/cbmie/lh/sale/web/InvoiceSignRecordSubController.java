package com.cbmie.lh.sale.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.sale.entity.InvoiceSignRecord;
import com.cbmie.lh.sale.entity.InvoiceSignRecordSub;
import com.cbmie.lh.sale.service.InvoiceSignRecordService;
import com.cbmie.lh.sale.service.InvoiceSignRecordSubService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="sale/invoiceSignRecordSub")
public class InvoiceSignRecordSubController extends BaseController {

	@Autowired
	private InvoiceSignRecordSubService signRecordSubService;
	@Autowired
	private InvoiceSignRecordService signRecordService;
	
	@RequestMapping(value = "list/{invoiceSignRecordId}")
	@ResponseBody
	public Map<String, Object> getSaleSettlementGoodsList(@PathVariable("invoiceSignRecordId") Long invoiceSignRecordId,HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<InvoiceSignRecordSub> rows = new ArrayList<InvoiceSignRecordSub>();
		List<InvoiceSignRecordSub> footer = new ArrayList<InvoiceSignRecordSub>();
		InvoiceSignRecord invoiceSignRecord = signRecordService.get(invoiceSignRecordId);
		List<InvoiceSignRecordSub> list = invoiceSignRecord.getSignRecordSubs();
		rows.addAll(list);
		result.put("rows", rows);
		Double total_quanlity = 0.0;
		Double total_price = 0.0;
		for(InvoiceSignRecordSub sub :list){
			total_quanlity += sub.getQuantity()==null?0.0:sub.getQuantity();
			total_price += sub.getTotalPrice()==null?0.0:sub.getTotalPrice();
		}
		if(list!=null && list.size()>0){
			InvoiceSignRecordSub total = new InvoiceSignRecordSub();
			total.setQuantity(total_quanlity);
			total.setTotalPrice(total_price);
			footer.add(total);
			result.put("footer", footer);
		}
		return result;
	}
	
	/**
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create/{invoiceSignRecordId}", method = RequestMethod.GET)
	public String createForm(@PathVariable("invoiceSignRecordId") Long invoiceSignRecordId,Model model) {
		InvoiceSignRecordSub invoiceSignRecordSub = new InvoiceSignRecordSub();
		invoiceSignRecordSub.setInvoiceSignRecordId(invoiceSignRecordId);
		model.addAttribute("invoiceSignRecordSub", invoiceSignRecordSub);
		model.addAttribute("actionSub", "create");
		return "sale/invoiceSignRecordSubForm";
	}
	/**
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InvoiceSignRecordSub invoiceSignRecordSub, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		invoiceSignRecordSub.setCreaterNo(currentUser.getLoginName());
		invoiceSignRecordSub.setCreaterName(currentUser.getName());
		invoiceSignRecordSub.setCreateDate(new Date());
		signRecordSubService.save(invoiceSignRecordSub);
		return setReturnData("success", "保存成功", invoiceSignRecordSub.getId());
	}
	
	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("invoiceSignRecordSub", signRecordSubService.get(id));
		model.addAttribute("actionSub", "update");
		return "sale/invoiceSignRecordSubForm";
	}

	/**
	 * 修改采购合同-进口
	 * 
	 * @param purchaseContract
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InvoiceSignRecordSub invoiceSignRecordSub, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		invoiceSignRecordSub.setUpdaterNo(currentUser.getLoginName());
		invoiceSignRecordSub.setUpdaterName(currentUser.getName());
		invoiceSignRecordSub.setUpdateDate(new Date());
		signRecordSubService.update(invoiceSignRecordSub);
		return setReturnData("success", "保存成功", invoiceSignRecordSub.getId());
	}
	

	@ModelAttribute
	public void getInvoiceSignRecordSub(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("invoiceSignRecordSub", signRecordSubService.get(id));
		}
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		signRecordSubService.delete(id);
		return setReturnData("success","删除成功",id);
	}
}
