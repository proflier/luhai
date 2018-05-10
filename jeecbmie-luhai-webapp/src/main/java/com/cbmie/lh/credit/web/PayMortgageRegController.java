package com.cbmie.lh.credit.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.cbmie.lh.credit.entity.PayMortgageReg;
import com.cbmie.lh.credit.service.PayMortgageRegService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 付汇登记controller
 */
@Controller
@RequestMapping("credit/payMortgageReg")
public class PayMortgageRegController extends BaseController {

	@Autowired
	private PayMortgageRegService payMortgageRegService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "credit/payMortgageRegList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<PayMortgageReg> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		payMortgageRegService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = payMortgageRegService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		PayMortgageReg payMortgageReg = new PayMortgageReg();
		payMortgageReg.setCreateDate(new Date());
		payMortgageReg.setUpdateDate(new Date());
		model.addAttribute("payMortgageReg", payMortgageReg);
		model.addAttribute("action", "create");
		return "credit/payMortgageRegForm";
	}

	/**
	 * 添加
	 * 
	 * @param payMortgageReg
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PayMortgageReg payMortgageReg, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		payMortgageReg.setCreaterNo(currentUser.getLoginName());
		payMortgageReg.setCreaterName(currentUser.getName());
		payMortgageReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		payMortgageReg.setCreateDate(new Date());
		payMortgageRegService.save(payMortgageReg);
		return setReturnData("success", "保存成功", payMortgageReg.getId());
	}

	/**
	 * 修改跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("payMortgageReg", payMortgageRegService.get(id));
		model.addAttribute("action", "update");
		return "credit/payMortgageRegForm";
	}

	/**
	 * 修改
	 * 
	 * @param payMortgageReg
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PayMortgageReg payMortgageReg, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		payMortgageReg.setUpdaterNo(currentUser.getLoginName());
		payMortgageReg.setUpdaterName(currentUser.getName());
		payMortgageReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		payMortgageReg.setUpdateDate(new Date());
		payMortgageRegService.update(payMortgageReg);
		return setReturnData("success", "保存成功", payMortgageReg.getId());
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		payMortgageRegService.delete(id);
		return "success";
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
		model.addAttribute("payMortgageReg", payMortgageRegService.get(id));
		return "credit/payMortgageRegDetail";
	}

	@ModelAttribute
	public void getPayMortgageReg(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("payMortgageReg", payMortgageRegService.get(id));
		}
	}

}
