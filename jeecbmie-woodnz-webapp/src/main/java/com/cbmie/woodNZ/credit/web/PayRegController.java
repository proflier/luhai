package com.cbmie.woodNZ.credit.web;

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
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.credit.entity.PayReg;
import com.cbmie.woodNZ.credit.service.PayRegService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 付款登记controller
 */
@Controller
@RequestMapping("credit/payReg")
public class PayRegController extends BaseController {
	
	
	@Autowired
	private PayRegService payRegService;
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "credit/payRegList";
	}
	
	/**
	 * 开证费登记页面
	 */
	@RequestMapping(value = "payRegFeeList", method = RequestMethod.GET)
	public String openCreditRegList() {
		return "credit/payRegFeeList";
	}
	
	/**
	 * 开证费登记跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "payRegFee/{id}", method = RequestMethod.GET)
	public String regForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("action", "payRegFee");
		model.addAttribute("payReg", payRegService.get(id));
		return "credit/payRegFeeForm";
	}
	
	/**
	 * 开证费登记
	 * 
	 * @param payReg
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "payRegFee", method = RequestMethod.POST)
	@ResponseBody
	public String payRegFee(@Valid @ModelAttribute @RequestBody PayReg payReg, Model model) throws JsonProcessingException {
		payRegService.update(payReg);
		return setReturnData("success","保存成功",payReg.getId());
	}
	
	/**
	 * 开证费明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "payRegFeeDetail/{id}", method = RequestMethod.GET)
	public String payRegFeeDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("payReg", payRegService.get(id));
		return "credit/payRegFeeDetail";
	}
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> openCreditList(HttpServletRequest request) {
		Page<PayReg> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = payRegService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("payReg", new PayReg());
		model.addAttribute("action", "create");
		return "credit/payRegForm";
	}

	/**
	 * 添加
	 * 
	 * @param payReg
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PayReg payReg, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		payReg.setCreaterNo(currentUser.getLoginName());
		payReg.setCreaterName(currentUser.getName());
		payReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		payReg.setCreateDate(new Date());
		payRegService.save(payReg);
		return setReturnData("success","保存成功",payReg.getId());
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
		model.addAttribute("payReg", payRegService.get(id));
		model.addAttribute("action", "update");
		return "credit/payRegForm";
	}

	/**
	 * 修改
	 * 
	 * @param payReg
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PayReg payReg, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		payReg.setUpdaterNo(currentUser.getLoginName());
		payReg.setUpdaterName(currentUser.getName());
		payReg.setUpdaterDept(currentUser.getOrganization().getOrgName());
		payReg.setUpdateDate(new Date());
		payRegService.update(payReg);
		return setReturnData("success","保存成功",payReg.getId());
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
		payRegService.delete(id);
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
		model.addAttribute("payReg", payRegService.get(id));
		return "credit/payRegDetail";
	}
	
	@ModelAttribute
	public void getPayReg(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("payReg", payRegService.get(id));
		}
	}
	
	
}
