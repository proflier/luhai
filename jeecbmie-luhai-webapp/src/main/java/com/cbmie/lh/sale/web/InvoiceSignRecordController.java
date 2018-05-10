package com.cbmie.lh.sale.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import com.cbmie.lh.sale.entity.InvoiceSignRecord;
import com.cbmie.lh.sale.service.InvoiceSignRecordService;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="sale/invoiceSignRecord")
public class InvoiceSignRecordController extends BaseController {

	@Autowired
	private InvoiceSignRecordService signReordService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sale/invoiceSignRecordList";
	}
	

	/**
	 * json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<InvoiceSignRecord> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//获取当前可见客户
		Session session =SecurityUtils.getSubject().getSession();
		String customerCode = (String) session.getAttribute("customerCode");
		//根据某一字段设置权限
		signReordService.setCurrentPermission(filters, "INQ_customer", customerCode);
		page = signReordService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		InvoiceSignRecord invoiceSignRecord = new InvoiceSignRecord();
		User currentUser = UserUtil.getCurrentUser();
		invoiceSignRecord.setCreaterNo(currentUser.getLoginName());
		invoiceSignRecord.setCreaterName(currentUser.getName());
		invoiceSignRecord.setCreaterDept(currentUser.getOrganization().getOrgName());
		invoiceSignRecord.setCreateDate(new Date());
		invoiceSignRecord.setUpdateDate(new Date());
		model.addAttribute("invoiceSignRecord", invoiceSignRecord);
		model.addAttribute("action", "create");
		return "sale/invoiceSignRecordForm";
	}
	/**
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InvoiceSignRecord invoiceSignRecord, Model model) throws JsonProcessingException {
		signReordService.save(invoiceSignRecord);
		return setReturnData("success", "保存成功", invoiceSignRecord.getId());
	}
	
	/**
	 * 修改采购合同-进口跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("invoiceSignRecord", signReordService.get(id));
		model.addAttribute("action", "update");
		return "sale/invoiceSignRecordForm";
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
	public String update(@Valid @ModelAttribute @RequestBody InvoiceSignRecord invoiceSignRecord, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		invoiceSignRecord.setUpdaterNo(currentUser.getLoginName());
		invoiceSignRecord.setUpdaterName(currentUser.getName());
		invoiceSignRecord.setUpdateDate(new Date());
		signReordService.update(invoiceSignRecord);
		return setReturnData("success", "保存成功", invoiceSignRecord.getId());
	}
	

	@ModelAttribute
	public void getInvoiceSignRecord(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("invoiceSignRecord", signReordService.get(id));
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
		model.addAttribute("invoiceSignRecord", signReordService.get(id));
		model.addAttribute("action", "view");
		return "sale/invoiceSignRecordDetail";
	}
	
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		signReordService.delete(id);
		return setReturnData("success","删除成功",id);
	}
	
	@RequestMapping(value = "toPrintPage/{invoiceSignId}", method = RequestMethod.GET)
	public String toPrintPage(@PathVariable("invoiceSignId") Long invoiceSignId, Model model){
		model.addAttribute("invoiceSignRecord", signReordService.get(invoiceSignId));
		return "sale/invoiceSignPrint";
	}
}
