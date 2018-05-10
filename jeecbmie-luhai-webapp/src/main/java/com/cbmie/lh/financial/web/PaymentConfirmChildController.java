package com.cbmie.lh.financial.web;

import java.util.ArrayList;
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
import com.cbmie.lh.financial.entity.PaymentConfirmChild;
import com.cbmie.lh.financial.entity.PaymentTypeRelation;
import com.cbmie.lh.financial.service.PaymentConfirmChildService;
import com.cbmie.lh.financial.service.PaymentConfirmService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 付款确认明细controller
 */
@Controller
@RequestMapping("financial/paymentConfirmChild")
public class PaymentConfirmChildController extends BaseController {

	@Autowired
	private PaymentConfirmChildService paymentConfirmChildService;
	
	@Autowired
	private PaymentConfirmService paymentConfirmService;


	/**
	 * 根据id获取明细
	 */
	@RequestMapping(value = "getChilds/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request, @PathVariable("id") String id) {
		Page<PaymentConfirmChild> page = getPage(request);
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		PropertyFilter filter = new PropertyFilter("EQL_paymentConfirmId", id);
		filterList.add(filter);
		page = paymentConfirmChildService.searchNoPermission(page, filterList);
		return getEasyUIData(page);
	}

	/**
	 * 添加明细跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model, @PathVariable("id") long id) {
		PaymentConfirmChild paymentConfirmChild = new PaymentConfirmChild();
		paymentConfirmChild.setPaymentConfirmId(id);
		model.addAttribute("paymentConfirmChild", paymentConfirmChild);
		model.addAttribute("actionChild", "create");
		return "financial/paymentConfirmChildForm";
	}

	/**
	 * 添加明细
	 * 
	 * @param paymentConfirmChild
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PaymentConfirmChild paymentConfirmChild, Model mode) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		paymentConfirmChild.setCreaterNo(currentUser.getLoginName());
		paymentConfirmChild.setCreaterName(currentUser.getName());
		paymentConfirmChild.setCreateDate(new Date());
		paymentConfirmChild.setUpdateDate(new Date());
		paymentConfirmChild.setCreaterDept(currentUser.getOrganization().getOrgName());
		paymentConfirmChildService.save(paymentConfirmChild);
		return setReturnData("success", "保存成功", paymentConfirmChild.getId());
	}

	/**
	 * 修改明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("paymentConfirmChild", paymentConfirmChildService.get(id));
		model.addAttribute("actionChild", "update");
		return "financial/paymentConfirmChildForm";
	}

	/**
	 * 修改明细
	 * 
	 * @param paymentConfirmChild
	 * @param model
	 * @param goodsIndexJson
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PaymentConfirmChild paymentConfirmChild, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		paymentConfirmChild.setUpdaterNo(currentUser.getLoginName());
		paymentConfirmChild.setUpdaterName(currentUser.getName());
		paymentConfirmChild.setUpdateDate(new Date());
		paymentConfirmChildService.update(paymentConfirmChild);
		return setReturnData("success", "保存成功", paymentConfirmChild.getId());
	}

	/**
	 * 删除明细
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		paymentConfirmChildService.delete(id);
		return "success";
	}
	
	/**
	 * 删除明细
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteByPid/{pid}")
	@ResponseBody
	public String deleteByPid(@PathVariable("pid") Long pid) throws Exception {
		paymentConfirmChildService.deleteByPid(pid);
		return "success";
	}
	
	/**
	 * 付款关联
	 * */
	@RequestMapping(value="getPaymentRelation/{value}",method = RequestMethod.GET)
	@ResponseBody
	public PaymentTypeRelation getSelectItemOfGoods(@PathVariable("value") String value){
		PaymentTypeRelation paymentTypeRelation = paymentConfirmChildService.getPaymentRelation(value);
		return paymentTypeRelation;
	}
	
	
	

	@ModelAttribute
	public void getPaymentConfirmChild(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("paymentConfirmChild", paymentConfirmChildService.get(id));
		}
	}

}
