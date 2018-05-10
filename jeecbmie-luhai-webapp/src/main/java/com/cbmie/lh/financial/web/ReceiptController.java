package com.cbmie.lh.financial.web;

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
import com.cbmie.lh.financial.entity.Receipt;
import com.cbmie.lh.financial.service.ReceiptService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 预收票controller
 */
@Controller
@RequestMapping("financial/receipt")
public class ReceiptController extends BaseController {

	@Autowired
	private ReceiptService receiptService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/receiptList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<Receipt> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//获取当前可见客户
		Session session =SecurityUtils.getSubject().getSession();
		String customerCode = (String) session.getAttribute("customerCode");
		//根据某一字段设置权限
		receiptService.setCurrentPermission(filters, "INQ_supply", customerCode);
		page = receiptService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加预收票跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		Receipt receipt = new Receipt();
		receipt.setCreateDate(new Date());
		receipt.setUpdateDate(new Date());
		model.addAttribute("receipt", receipt);
		model.addAttribute("action", "create");
		return "financial/receiptForm";
	}

	/**
	 * 添加预收票
	 * 
	 * @param receipt
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Receipt receipt, Model model) throws JsonProcessingException {
		if (receiptService.findByNo(receipt) != null) {
			return setReturnData("fail", "预收票编号重复！", receipt.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		receipt.setCreaterNo(currentUser.getLoginName());
		receipt.setCreaterName(currentUser.getName());
		receipt.setCreateDate(new Date());
		receipt.setCreaterDept(currentUser.getOrganization().getOrgName());
		receiptService.save(receipt);
		return setReturnData("success", "保存成功", receipt.getId());
	}

	/**
	 * 修改预收票跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("receipt", receiptService.get(id));
		model.addAttribute("action", "update");
		return "financial/receiptForm";
	}

	/**
	 * 修改预收票
	 * 
	 * @param receipt
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Receipt receipt, Model model)
			throws JsonProcessingException {
		if (receiptService.findByNo(receipt) != null) {
			return setReturnData("fail", "预收票号重复！", receipt.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		receipt.setUpdaterNo(currentUser.getLoginName());
		receipt.setUpdaterName(currentUser.getName());
		receipt.setUpdateDate(new Date());
		receiptService.update(receipt);
		return setReturnData("success", "保存成功", receipt.getId());
	}

	/**
	 * 删除预收票
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		receiptService.delete(id);
		return "success";
	}

	/**
	 * 查看预收票明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("receipt", receiptService.get(id));
		return "financial/receiptDetail";
	}

	@ModelAttribute
	public void getReceipt(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("receipt", receiptService.get(id));
		}
	}

}
