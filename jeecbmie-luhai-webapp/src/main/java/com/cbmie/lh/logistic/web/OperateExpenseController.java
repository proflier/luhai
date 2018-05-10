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
import com.cbmie.lh.logistic.entity.OperateExpense;
import com.cbmie.lh.logistic.service.OperateExpenseService;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping("logistic/portContract/operate")
public class OperateExpenseController extends BaseController {

	@Autowired
	private OperateExpenseService operateService;
	
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<OperateExpense> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = operateService.search(page, filters);
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
			OperateExpense operateExpense = new OperateExpense();
			operateExpense.setPortContractId(parentId);
			model.addAttribute("operateExpense", operateExpense);
			model.addAttribute("actionOperate", "create");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logistic/operateExpenseForm";
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
	public String create(@Valid OperateExpense operateExpense, Model model) throws JsonProcessingException {
		try {
			operateExpense.setCreateDate(new Date());
			operateService.save(operateExpense);
			return setReturnData("success","保存成功",operateExpense.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",operateExpense.getId());
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
		model.addAttribute("operateExpense", operateService.get(id));
		model.addAttribute("actionOperate", "update");
		return "logistic/operateExpenseForm";
	}
	
	@ModelAttribute
	public void getOperateExpense(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("operateExpense", operateService.get(id));
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
	public String update(@Valid @ModelAttribute @RequestBody OperateExpense operateExpense,Model model) throws JsonProcessingException {
		operateExpense.setUpdateDate(new Date());
		operateService.update(operateExpense);
		return setReturnData("success","保存成功",operateExpense.getId());
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
		operateService.delete(id);
		return setReturnData("success","删除成功",id);
	}
	/**
	 * 获取有效港口
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "validPort")
	@ResponseBody
	public List<Map<String,Object>> getValidPort(){
		return operateService.getValidPort();
	}
}
