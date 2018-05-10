package com.cbmie.lh.logistic.web;

import java.text.ParseException;
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
import com.cbmie.lh.logistic.entity.OrderShipContractSub;
import com.cbmie.lh.logistic.service.OrderShipContractSubService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping("logistic/orderShipContractSub")
public class OrderShipContractSubController extends BaseController {

	@Autowired
	private OrderShipContractSubService subService;
	
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<OrderShipContractSub> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = subService.searchNoPermission(page, filters);
		Map<String, Object> result = getEasyUIData(page);
		return result;
	}
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{orderShipId}", method = RequestMethod.GET)
	public String createForm(@PathVariable(value="orderShipId") Long orderShipId,Model model) throws ParseException {
		OrderShipContractSub orderShipContractSub = new OrderShipContractSub();
		orderShipContractSub.setOrderShipContractId(orderShipId);
		model.addAttribute("orderShipContractSub", orderShipContractSub);
		model.addAttribute("actionSub", "create");
		return "logistic/orderShipContractSubForm";
	}
	
	/**
	 * 添加
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid OrderShipContractSub orderShipContractSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		orderShipContractSub.setCreaterNo(currentUser.getLoginName());
		orderShipContractSub.setCreaterName(currentUser.getName());
		orderShipContractSub.setCreaterDept(currentUser.getOrganization().getOrgName());
		orderShipContractSub.setCreateDate(new Date());
		subService.save(orderShipContractSub);
		return setReturnData("success","保存成功",orderShipContractSub.getId());
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("orderShipContractSub", subService.get(id));
		model.addAttribute("actionSub", "update");
		return "logistic/orderShipContractSubForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OrderShipContractSub orderShipContractSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		orderShipContractSub.setUpdaterNo(currentUser.getLoginName());
		orderShipContractSub.setUpdaterName(currentUser.getName());
		orderShipContractSub.setUpdateDate(new Date());
		subService.update(orderShipContractSub);
		return setReturnData("success","保存成功",orderShipContractSub.getId());
	}
	
	@ModelAttribute
	public void getOrderShipContractSub(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("orderShipContractSub", subService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		subService.delete(id);
		return setReturnData("success","删除成功",id);
	}
}
