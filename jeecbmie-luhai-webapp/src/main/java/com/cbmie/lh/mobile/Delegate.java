package com.cbmie.lh.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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
import com.cbmie.activiti.web.DelegatePersonController;
import com.cbmie.activiti.web.ProcessController;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.utils.MyTagUtil;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("mobile/delegate")
public class Delegate {
	
	@Autowired
	private DelegatePersonService personService;
	
	@Autowired
	private DelegatePersonController delegatePersonController;
	
	@Autowired
	private ProcessController processController;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		return "mobile/delegate/list";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "initList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<DelegatePerson> initList(HttpServletRequest request) throws Exception {
		List<DelegatePerson> list = (List<DelegatePerson>) delegatePersonController.getData(request).get("rows");
		List<DelegatePerson> returnList = new ArrayList<DelegatePerson>();
		for (DelegatePerson d : list) {
			DelegatePerson dp = new DelegatePerson();
			BeanUtils.copyProperties(dp, d);
			String moduleName = processController.getModuleNameByKey(dp.getProcDefKey());
			dp.setProcDefKey(moduleName);
			if (StringUtils.isBlank(dp.getProcDefKey())) {
				dp.setProcDefKey("全部");
			}
			dp.setConsigner(MyTagUtil.getUserByLoginName(dp.getConsigner()).getName());
			dp.setMandatary(MyTagUtil.getUserByLoginName(dp.getMandatary()).getName());
			returnList.add(dp);
		}
		return returnList;
	}
	
	/**
	 * 跳转form
	 */
	@RequestMapping(value = "todo/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		DelegatePerson delegatePerson = new DelegatePerson();
		if (id > 0) {
			delegatePerson = personService.get(id);
		} else {
			User currentUser = UserUtil.getCurrentUser();
			delegatePerson.setCreaterNo(currentUser.getLoginName());
			delegatePerson.setCreaterName(currentUser.getName());
			delegatePerson.setCreaterDept(currentUser.getOrganization().getOrgName());
			delegatePerson.setCreateDate(new Date());
			delegatePerson.setUpdateDate(new Date());
			delegatePerson.setConsigner(currentUser.getLoginName());
		}
		model.addAttribute("obj", delegatePerson);
		return "mobile/delegate/todo";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute @RequestBody DelegatePerson delegatePerson, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		delegatePerson.setProcDefKey(delegatePerson.getProcDefKey().replace(";", ","));
		if (delegatePerson.getId() == null) {
			delegatePerson.setCreaterNo(currentUser.getLoginName());
			delegatePerson.setCreaterName(currentUser.getName());
			delegatePerson.setCreaterDept(currentUser.getOrganization().getOrgName());
			delegatePerson.setCreateDate(new Date());
			personService.save(delegatePerson);
		} else {
			if (delegatePerson.getActivateFlag().equals("0")) {//终止
				delegatePerson.setTerminatingDate(new Date());
			}
			delegatePerson.setUpdaterNo(currentUser.getLoginName());
			delegatePerson.setUpdaterName(currentUser.getName());
			delegatePerson.setUpdateDate(new Date());
			personService.update(delegatePerson);
		}
		return "mobile/delegate/list";
	}
	
	@ModelAttribute
	public void getDelegatePerson(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("delegatePerson", personService.get(id));
		}
	}

}
