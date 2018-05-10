package com.cbmie.activiti.web;

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

import com.cbmie.activiti.entity.DelegatePerson;
import com.cbmie.activiti.service.DelegatePersonService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="activiti/delegate")
public class DelegatePersonController extends BaseController {

	@Autowired
	private DelegatePersonService personService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		User currentUser = UserUtil.getCurrentUser();
		model.addAttribute("curLoginName", currentUser.getLoginName());
		return "activiti/delegateList";
	}

	/**
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<DelegatePerson> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		personService.setCurrentPermission(filters, "EQS_consigner_OR_mandatary");
		page = personService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		DelegatePerson delegatePerson = new DelegatePerson();
		User currentUser = UserUtil.getCurrentUser();
		delegatePerson.setCreaterNo(currentUser.getLoginName());
		delegatePerson.setCreaterName(currentUser.getName());
		delegatePerson.setCreaterDept(currentUser.getOrganization().getOrgName());
		delegatePerson.setCreateDate(new Date());
		delegatePerson.setUpdateDate(new Date());
		delegatePerson.setConsigner(currentUser.getLoginName());
		model.addAttribute("delegatePerson", delegatePerson);
		model.addAttribute("action", "create");
		return "activiti/delegatePersonForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid DelegatePerson delegatePerson, Model model) throws JsonProcessingException {
		try {
			if(!personService.checkLegal(delegatePerson)){
				return setReturnData("fail","该期间已经委托他人",delegatePerson.getId());
			}
			personService.save(delegatePerson);
			return setReturnData("success","保存成功",delegatePerson.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",delegatePerson.getId());
		}
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
		model.addAttribute("delegatePerson", personService.get(id));
		model.addAttribute("action", "update");
		return "activiti/delegatePersonForm";
	}
	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody DelegatePerson delegatePerson, Model model) throws JsonProcessingException {
		if(!personService.checkLegal(delegatePerson)){
			return setReturnData("fail","该期间已经委托他人",delegatePerson.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		delegatePerson.setUpdaterNo(currentUser.getLoginName());
		delegatePerson.setUpdaterName(currentUser.getName());
		delegatePerson.setUpdateDate(new Date());
		personService.update(delegatePerson);
		return setReturnData("success","保存成功",delegatePerson.getId());
	}
	@ModelAttribute
	public void getDelegatePerson(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("delegatePerson", personService.get(id));
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws  Exception {
		personService.delete(id);
		return "success";
	}
	
	/**
	 * 终止
	 */
	@RequestMapping(value = "stop")
	@ResponseBody
	public String stop(@Valid @ModelAttribute DelegatePerson delegatePerson) {
		delegatePerson.setTerminatingDate(new Date());
		delegatePerson.setActivateFlag("0");
		personService.update(delegatePerson);
		return "success";
	}
	
	
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("delegatePerson", personService.get(id));
		model.addAttribute("action", "view");
		return "activiti/delegatePersonForm";
	}
	
}
