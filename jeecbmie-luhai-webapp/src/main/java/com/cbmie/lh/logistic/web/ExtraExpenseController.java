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
import com.cbmie.lh.logistic.entity.ExtraExpense;
import com.cbmie.lh.logistic.service.ExtraExpenseService;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping("logistic/portContract/extra")
public class ExtraExpenseController extends BaseController {

	@Autowired
	private ExtraExpenseService extraService;
	
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<ExtraExpense> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = extraService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加到单跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{parentId}", method = RequestMethod.GET)
	public String createForm(@PathVariable("parentId") Long parentId,Model model) throws ParseException {
		try {
			ExtraExpense extraExpense = new ExtraExpense();
			extraExpense.setPortContractId(parentId);
			model.addAttribute("extraExpense", extraExpense);
			model.addAttribute("actionExtra", "create");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logistic/extraExpenseForm";
	}
	
	/**
	 * 添加到单
	 * 
	 * @param port
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ExtraExpense extraExpense, Model model) throws JsonProcessingException {
		try {
			extraExpense.setCreateDate(new Date());
			extraService.save(extraExpense);
			return setReturnData("success","保存成功",extraExpense.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",extraExpense.getId());
		}
	}
	
	/**
	 * 修改到单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("extraExpense", extraService.get(id));
		model.addAttribute("actionExtra", "update");
		return "logistic/extraExpenseForm";
	}
	
	@ModelAttribute
	public void getExtraExpense(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("extraExpense", extraService.get(id));
		}
	}

	/**
	 * 修改到单
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ExtraExpense extraExpense,Model model) throws JsonProcessingException {
		extraExpense.setUpdateDate(new Date());
		extraService.update(extraExpense);
		return setReturnData("success","保存成功",extraExpense.getId());
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		extraService.delete(id);
		return setReturnData("success","删除成功",id);
	}
}
