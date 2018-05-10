package com.cbmie.lh.sale.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleInvoiceSub;
import com.cbmie.lh.sale.service.SaleInvoiceService;
import com.cbmie.lh.sale.service.SaleSettlementService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("sale/saleInvoice")
public class SaleInvoiceController extends BaseController {

	@Autowired
	private SaleInvoiceService invoiceService;
	
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private BaseInfoUtilService bius;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sale/saleInvoiceList";
	}

	/**
	 * 获取
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<SaleInvoice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		invoiceService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = invoiceService.search(page, filters);
		return getEasyUIData(page);
	}
	
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityAllList(HttpServletRequest request) {
		Page<SaleInvoice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = invoiceService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		SaleInvoice saleInvoice = new SaleInvoice();
		User currentUser = UserUtil.getCurrentUser();
		saleInvoice.setCreaterNo(currentUser.getLoginName());
		saleInvoice.setCreaterName(currentUser.getName());
		saleInvoice.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleInvoice.setCreateDate(new Date());
		saleInvoice.setUpdateDate(new Date());
		model.addAttribute("saleInvoice", saleInvoice);
		model.addAttribute("action", "create");
		return "sale/saleInvoiceForm";
	}
	/**
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleInvoice saleInvoice, Model model) throws JsonProcessingException {
		String summary = "[销售发票]["+ bius.getAffiBaseInfoByCode(saleInvoice.getCustomerName()) +"]";
		List<SaleInvoiceSub> subs = saleInvoice.getSaleInvoiceSubs();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(SaleInvoiceSub sub : subs){
				if(sub.getBillMoney()!=null)
				total = total.add(new BigDecimal(sub.getBillMoney()));
			}
			summary = summary+"[开票金额"+total.doubleValue()+"元]";
		}
		saleInvoice.setSummary(summary);
		invoiceService.save(saleInvoice);
		invoiceService.fillOutRel(saleInvoice);
		return setReturnData("success", "保存成功", saleInvoice.getId());
	}
	
	/**
	 * 修改销售发票
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleInvoice", invoiceService.get(id));
		model.addAttribute("action", "update");
		return "sale/saleInvoiceForm";
	}

	/**
	 * 修改销售发票
	 * 
	 * @param purchaseContract
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleInvoice saleInvoice, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		saleInvoice.setUpdaterNo(currentUser.getLoginName());
		saleInvoice.setUpdaterName(currentUser.getName());
		saleInvoice.setUpdateDate(new Date());
		String summary = "[销售发票]["+ bius.getAffiBaseInfoByCode(saleInvoice.getCustomerName()) +"]";
		List<SaleInvoiceSub> subs = saleInvoice.getSaleInvoiceSubs();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(SaleInvoiceSub sub : subs){
				if(sub.getBillMoney()!=null)
				total = total.add(new BigDecimal(sub.getBillMoney()));
			}
			summary = summary+"[开票金额"+total.doubleValue()+"元]";
		}
		saleInvoice.setSummary(summary);
		invoiceService.update(saleInvoice);
		invoiceService.fillOutRel(saleInvoice);
		return setReturnData("success", "保存成功", saleInvoice.getId());
	}
	
	@ModelAttribute
	public void getSaleInvoice(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleInvoice", invoiceService.get(id));
		}
	}
	
	/**
	 * 查看明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleInvoice", invoiceService.get(id));
		return "sale/saleInvoiceDetail";
	}
	
	/**
	 * 提交流程申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "apply/{id}")
	@ResponseBody
	public String apply(@PathVariable("id") Long id) {
		Map<String,Object> result = new HashMap<String,Object>();
		User user = UserUtil.getCurrentUser();
		SaleInvoice saleInvoice = invoiceService.get(id);
		String summary = "[销售发票]["+ bius.getAffiBaseInfoByCode(saleInvoice.getCustomerName()) +"]";
		List<SaleInvoiceSub> subs = saleInvoice.getSaleInvoiceSubs();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(SaleInvoiceSub sub : subs){
				if(sub.getBillMoney()!=null)
				total = total.add(new BigDecimal(sub.getBillMoney()));
			}
			summary = summary+"[开票金额"+total.longValue()+"元]";
		}
		saleInvoice.setSummary(summary);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("preBilling", saleInvoice.getPreBilling());
		variables.put("manageMode", saleInvoice.getManageMode());
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(SaleInvoice.class,saleInvoice);
		if(processKey!=null){
			result = activitiService.startWorkflow(saleInvoice, processKey, variables,
					(user.getLoginName()).toString());
		}else{
			return "流程未部署、不存在或存在多个，请联系管理员！";
		}
		ObjectMapper objectMapper = new ObjectMapper(); 
		try {
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 撤回流程申请
	 * @return
	 */
	@RequestMapping(value = "callBack/{id}/{processInstanceId}")
	@ResponseBody
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId, HttpSession session) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId)) {
				SaleInvoice saleInvoice = invoiceService.get(id);
				saleInvoice.setProcessInstanceId(null);
				saleInvoice.setState(ActivitiConstant.ACTIVITI_DRAFT);
				invoiceService.save(saleInvoice);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
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
		invoiceService.delete(id);
		return setReturnData("success","删除成功",id);
	}
}
