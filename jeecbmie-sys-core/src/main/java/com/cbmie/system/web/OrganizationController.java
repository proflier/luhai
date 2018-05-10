package com.cbmie.system.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.OrganizationService;
import com.cbmie.system.utils.UserUtil;

/**
 * 机构信息controller
 */
@Controller
@RequestMapping("system/organization")
public class OrganizationController extends BaseController{

	@Autowired
	private OrganizationService organizationService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/organization/orgList";
	}
	
	/**
	 * 获取机构信息json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<Organization> areaInfoList(HttpServletRequest request) {
		List<Organization> organizations=organizationService.getAll();
		return organizations;
	}
	
	/**
	 * 获取机构信息json
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	@ResponseBody
	public List<Organization> orgList(HttpServletRequest request) {
		List<Organization> organizations=organizationService.getAllByTree();
		return organizations;
	}
	
	/**
	 * 添加机构信息跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("organization", new Organization());
		User currentUser = UserUtil.getCurrentUser();
		model.addAttribute("loginName", currentUser.getLoginName());
		model.addAttribute("action", "create");
		return "system/organization/orgForm";
	}

	/**
	 * 添加机构信息
	 * 
	 * @param organization
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Organization organization, Model model) {
		organizationService.save(organization);
		return "success";
	}

	/**
	 * 修改机构信息跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("organization", organizationService.get(id));
		User currentUser = UserUtil.getCurrentUser();
		model.addAttribute("loginName", currentUser.getLoginName());
		model.addAttribute("action", "update");
		return "system/organization/orgForm";
	}

	/**
	 * 修改机构信息
	 * 
	 * @param goodsType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Organization organization,Model model) {
		organizationService.update(organization);
		organizationService.getEntityDao().getSession().flush();
		return "success";
	}

	/**
	 * 删除机构信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		List<User> userList = organizationService.getUsersByOrg(id);
		if(userList!=null){
			StringBuffer returnValue = new StringBuffer();
			returnValue.append("机构含有以下用户不能删除：<br/>");
			returnValue.append("<table width='98%' class='tableClass'><tr><th>用户名</th><th>账号</th><th>状态</th></tr>");
			for(User user : userList){
				returnValue.append("<tr><td>" + user.getName()
						+ "</td><td>" + user.getLoginName() + "</td><td>"
						+ (user.getLoginStatus()==1?"启用":"停用")
						+ "</td></tr>");
			}
			returnValue.append("</table>");
			return returnValue.toString();
		}
		organizationService.delete(id);
		organizationService.getEntityDao().getSession().flush();
		return "success";
	}
	
	@RequestMapping(value = "orgNameByIds/{orgIds}", method = RequestMethod.GET)
	@ResponseBody
	public String update(@PathVariable("orgIds") String orgIds) {
		if(StringUtils.isBlank(orgIds)){
			return "";
		}else{
			return organizationService.getOrgNameByIds(orgIds);
		}
	}
	@ModelAttribute
	public void getOrg(@RequestParam(value = "id", defaultValue = "-1") Integer id,Model model) {
		if (id != -1) {
			model.addAttribute("organization", organizationService.get(id));
		}
	}
}
