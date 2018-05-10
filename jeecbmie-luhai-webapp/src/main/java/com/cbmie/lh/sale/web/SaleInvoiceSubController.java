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

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.financial.entity.InputInvoiceSub;
import com.cbmie.lh.financial.entity.Serial;
import com.cbmie.lh.financial.service.InputInvoiceSubService;
import com.cbmie.lh.financial.service.SerialService;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleInvoiceSub;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.lh.sale.entity.SaleSettlementGoods;
import com.cbmie.lh.sale.service.SaleInvoiceService;
import com.cbmie.lh.sale.service.SaleInvoiceSubService;
import com.cbmie.lh.sale.service.SaleSettlementService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping("sale/saleInvoiceSub")
public class SaleInvoiceSubController extends BaseController {

	@Autowired
	private SaleInvoiceSubService invoiceSubService;
	@Autowired
	private SaleSettlementService settlementService;
	@Autowired
	private SaleInvoiceService invoiceService;
	@Autowired
	private InputInvoiceSubService inputInvoiceSubService;
	@Autowired
	private SerialService serialService;
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<SaleInvoiceSub> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = invoiceSubService.search(page, filters);
		return getEasyUIData(page);
	}
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityListNoPermission(HttpServletRequest request) {
		Page<SaleInvoiceSub> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = invoiceSubService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}
	@RequestMapping(value = "oldJson/{saleInvoiceId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOldListByPid(@PathVariable("saleInvoiceId") Long saleInvoiceId){
		Map<String,Object> result = new HashMap<String,Object>();
		List<SaleInvoiceSub> rows = invoiceSubService.getOldListByPid(saleInvoiceId);
		result.put("rows", rows);
		result.put("total", rows.size());
		return result;
	}
	
	/**
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create/{saleInvoiceId}", method = RequestMethod.GET)
	public String createForm(@PathVariable("saleInvoiceId") Long saleInvoiceId,Model model) {
		SaleInvoiceSub saleInvoiceSub = new SaleInvoiceSub();
		saleInvoiceSub.setSaleInvoiceId(saleInvoiceId);
		model.addAttribute("saleInvoiceSub", saleInvoiceSub);
		model.addAttribute("actionSub", "create");
		return "sale/saleInvoiceSubForm";
	}
	/**
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleInvoiceSub saleInvoiceSub, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		saleInvoiceSub.setCreaterNo(currentUser.getLoginName());
		saleInvoiceSub.setCreaterName(currentUser.getName());
		saleInvoiceSub.setCreateDate(new Date());
		invoiceSubService.save(saleInvoiceSub);
		return setReturnData("success", "保存成功", saleInvoiceSub.getId());
	}
	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleInvoiceSub", invoiceSubService.get(id));
		model.addAttribute("actionSub", "update");
		return "sale/saleInvoiceSubForm";
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
	public String update(@Valid @ModelAttribute @RequestBody SaleInvoiceSub saleInvoiceSub, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		saleInvoiceSub.setUpdaterNo(currentUser.getLoginName());
		saleInvoiceSub.setUpdaterName(currentUser.getName());
		saleInvoiceSub.setUpdateDate(new Date());
		invoiceSubService.update(saleInvoiceSub);
		return setReturnData("success", "保存成功", saleInvoiceSub.getId());
	}
	

	@ModelAttribute
	public void getSaleInvoiceSub(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleInvoiceSub", invoiceSubService.get(id));
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
		invoiceSubService.delete(id);
		return setReturnData("success","删除成功",id);
	}
	@RequestMapping(value = "toSaleContractList/{customer}")
	public String toSaleContractList(@PathVariable(value="customer") String customer,Model model){
		model.addAttribute("customer", customer);
		return "sale/saleInvoiceContractList";
	}
	
	@RequestMapping(value = "saleSettlementGoodsList/{invoiceId}")
	@ResponseBody
	public Map<String, Object> getSaleSettlementGoodsList(@PathVariable("invoiceId") Long invoiceId,HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<SaleSettlementGoods> rows = invoiceService.SaleInvoicepubList(invoiceId);
		result.put("rows", rows);
		return result;
	}
	
	@RequestMapping(value = "inputInvoiceList/{invoiceId}")
	@ResponseBody
	public List<InputInvoiceSub> getInputInvoiceSubBySaleNo(@PathVariable("invoiceId") Long invoiceId,String saleConctractNo){
		SaleInvoice saleInvoice = invoiceService.get(invoiceId);
		return inputInvoiceSubService.getInputInvoiceSubBySaleNo(saleInvoice.getSaleContractNo());
	}
	@RequestMapping(value = "serialDetailList/{invoiceId}")
	@ResponseBody
	public List<Serial> getSerialsBySaleNo(@PathVariable("invoiceId") Long invoiceId,String saleConctractNo){
		if(invoiceId==0){
			return new ArrayList<Serial>();
		}
		SaleInvoice saleInvoice = invoiceService.get(invoiceId);
		return serialService.getSerialBySaleNo(saleInvoice.getSaleContractNo());
	}
}
