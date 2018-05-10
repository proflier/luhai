package com.cbmie.system.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.cbmie.system.entity.OrgUser;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.OrganizationService;
import com.cbmie.system.service.UserRoleService;
import com.cbmie.system.service.UserService;

/**
 * 用户controller
 */
@Controller
@RequestMapping("system/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/userList";
	}

	/**
	 * 获取用户json
	 */
//	@RequiresPermissions("sys:user:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<User> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = userService.search(page, filters);
		return getEasyUIData(page);
	}
	
	
	@RequestMapping(value="jsonNew",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> jsonNew(HttpServletRequest request) {
		Page<User> page = getPage(request);
		//构造过滤条件
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(request.getParameter("filter_LIKES_name"))){
			params.put("name", request.getParameter("filter_LIKES_name"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("filter_LIKES_phone"))){
			params.put("phone", request.getParameter("filter_LIKES_phone"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("filter_EQI_loginStatus"))){
			params.put("loginStatus", request.getParameter("filter_EQI_loginStatus"));
		}else{
			params.put("loginStatus", "1");
		}
		if(StringUtils.isNotEmpty(request.getParameter("filter_EQI_orgId"))){
			params.put("orgId", request.getParameter("filter_EQI_orgId"));
		}
		userService.findEntityList(page, params);
		return getEasyUIData(page);
	}
	
	
	/**
	 */
	@RequestMapping(value="select",method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUserSelectItem(HttpServletRequest request) {
		List<User> users=userService.getAll();
		return users;
	}
	
	
	/**
	 * 添加用户跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("action", "create");
		return "system/userForm";
	}
	
	@RequestMapping(value = "toImg/{id}", method = RequestMethod.GET)
	public String toImg(Model model,@PathVariable("id") Long id) {
		model.addAttribute("currentPid", id);
		return "system/toUserImg";
	}


	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid User user,@RequestParam Integer newOrganazationId, Model model) {
		user.setOrganization(organizationService.get(newOrganazationId));
		userService.save(user);
		return "success";
	}

	/**
	 * 修改用户跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.get(id));
		model.addAttribute("action", "update");
		return "system/userForm";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody User user,@RequestParam Integer newOrganazationId,Model model) {
		user.setOrganization(organizationService.get(newOrganazationId));
		userService.update(user);
		return "success";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		userService.delete(id);
		return "success";
	}

	/**
	 * 弹窗页-用户拥有的角色
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{userId}/userRole")
	public String getUserRole(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/userRoleList";
	}
	/**
	 * 弹窗页-用户所在机构
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:orgView")
	@RequestMapping(value = "{userId}/userOrg")
	public String getUserOrg(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/userOrgList";
	}

	/**
	 * 获取用户拥有的角色ID集合
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{userId}/role")
	@ResponseBody
	public String getRoleIdList(@PathVariable("userId") Integer id) {
		List<Integer> list = userRoleService.getRoleIdList(id);
		return userRoleService.idAjaxJson(list);
	}
	
	/**
	 * 获取用户拥有的机构ID集合
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:orgView")
	@RequestMapping(value = "{userId}/org")
	@ResponseBody
	public String getOrgIdList(@PathVariable("userId") Integer id) {
		User user = userService.get(id);
		Organization org = user.getOrganization();
		List<Integer> list = new ArrayList<Integer>();
		if (org != null) {
			list.add(org.getId());
		}
		return userRoleService.idAjaxJson(list);
	}

	
	/**
	 * 获取用户列表
	 * @param value  角色所对应的id
	 * @return
	 */
	@RequestMapping(value = "getRelativeUser/{value}")
	@ResponseBody
	public List<User> getUser(@PathVariable("value") String value) {
		return userService.getSupplier(value);
	}
	
	
	/**
	 * 获取某一角色下的用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getUserInRole/{id}",method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUserSelectItem(@PathVariable("id") Integer id) {
		List<User> users=userService.getUserInRole(id);
		return users;
	}
	
	/**
	 * 为某一角色添加用户跳转
	 * @return 
	 */
	@RequestMapping(value = "createUserRole")
	public String createUserRole() {
		return "system/userTree";
	}
	
	/**
	 * 获取机构和用户信息
	 */
	@RequestMapping(value="orgUserList",method = RequestMethod.GET)
	@ResponseBody
	public List<OrgUser>  orgUserList(HttpServletRequest request) {
		List<OrgUser> orgUsers = new ArrayList<OrgUser>();
		List<User> users = new ArrayList<User>();
		String userName = request.getParameter("filter_LIKES_userName");
		if(StringUtils.isNotEmpty(userName)){
			users = userService.getUsersByNameLike(userName);
		}else{
			List<Organization> organizations=organizationService.getAll();
			for(Organization organization :organizations){
				OrgUser orgUser = new OrgUser();
				orgUser.setId(organization.getId().toString()+"_");
				orgUser.setName(organization.getOrgName());
				orgUser.setParendId(organization.getPid()!=null?organization.getPid().toString()+"_":"");
				orgUser.setFlag("O");
				orgUsers.add(orgUser);
			}
			users=userService.getEffectUsers();
		}
		for(User user: users){
			OrgUser orgUser = new OrgUser();
			orgUser.setId(user.getId().toString());
			orgUser.setName(user.getName());
			orgUser.setFlag("U");
			orgUser.setParendId(user.getOrganization().getId()!=null?user.getOrganization().getId().toString()+"_":"");
			orgUsers.add(orgUser);
		}
		return orgUsers;
	}
	
	/**
	 * 获取机构和用户登陆账号
	 */
	@RequestMapping(value="orgUserLoginNameList",method = RequestMethod.GET)
	@ResponseBody
	public List<OrgUser>  orgUserLoginNameList(HttpServletRequest request) {
		List<OrgUser> orgUsers = new ArrayList<OrgUser>();
		List<User> users = new ArrayList<User>();
		String userName = request.getParameter("filter_LIKES_userName");
		if(StringUtils.isNotEmpty(userName)){
			users = userService.getUsersByNameLike(userName);
		}else{
			List<Organization> organizations=organizationService.getAll();
			for(Organization organization :organizations){
				OrgUser orgUser = new OrgUser();
				orgUser.setId(organization.getId().toString()+"_");
				orgUser.setName(organization.getOrgName());
				orgUser.setParendId(organization.getPid()!=null?organization.getPid().toString()+"_":"");
				orgUser.setFlag("O");
				orgUsers.add(orgUser);
			}
			users=userService.getEffectUsers();
		}
		for(User user: users){
			OrgUser orgUser = new OrgUser();
			orgUser.setId(user.getLoginName().toString());
			orgUser.setName(user.getName());
			orgUser.setFlag("U");
			orgUser.setParendId(user.getOrganization().getId()!=null?user.getOrganization().getId().toString()+"_":"");
			orgUsers.add(orgUser);
		}
		return orgUsers;
	}
	
	/**
	 * 获取某一角色下的用户ids
	 * @param id roleId
	 * @return
	 */
	@RequestMapping("{roleId}/searchUser")
	@ResponseBody
	public List<Integer> searchUserIds(@PathVariable("roleId") Integer roleId){
		List<Integer> userIds=userService.searchUserIds(roleId);
		return userIds;
	}
	
	/**
	 * 更新角色下用户
	 * @param roleId 
	 * @param newUserIds
	 * @return
	 */
	@RequestMapping(value = "{roleId}/updateUserInRole")
	@ResponseBody
	public String updateUserInRole(@PathVariable("roleId") Integer roleId,@RequestBody List<String> newUserIds){
		List<Integer> oldUserIds = userService.searchUserIds(roleId);
		Iterator<String> it = newUserIds.iterator();
		while (it.hasNext()) {
			String val =  it.next();
			if(val.contains("_")){
				it.remove();
			}
		}
		userService.updateUserInRole(roleId,oldUserIds,newUserIds);
		return "success";
	}
	
	/**
	 * 删除角色下的某一用户
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequestMapping(value = "deleteFromRole/{roleId}/{userId}")
	@ResponseBody
	public void deleteFromRole(@PathVariable("roleId") Integer roleId,@PathVariable("userId") Integer userId) {
		userRoleService.deleteFromRole(roleId, userId);
	}
	
	
	/**
	 * 修改用户拥有的角色
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:roleUpd")
	@RequestMapping(value = "{id}/updateRole")
	@ResponseBody
	public String updateUserRole(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleList) {
		userRoleService.updateUserRole(id, userRoleService.getRoleIdList(id),newRoleList);
		return "success";
	}
	/**
	 * 修改用户所在的部门
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:orgUpd")
	@RequestMapping(value = "{id}/updateOrg")
	@ResponseBody
	public String updateUserOrg(@PathVariable("id") Integer id,@RequestBody List<Integer> newRoleList) {
		User user = userService.get(id);
		if (newRoleList != null && newRoleList.size() > 0)
			userService.updateUserOrg(user, organizationService.get(newRoleList.get(0)));
		return "success";
	}

	/**
	 * 修改密码跳转
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.GET)
	public String updatePwdForm(Model model, HttpSession session) {
		model.addAttribute("user", (User) session.getAttribute("user"));
		return "system/updatePwd";
	}
	
	/**
	 * 修改资料跳转
	 */
	@RequestMapping(value = "toUpdateCurrent", method = RequestMethod.GET)
	public String updateCurrent(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		//重新查询防止session影响
		user = userService.getNoLoad(user.getId());
		model.addAttribute("user", user);
		model.addAttribute("id", user.getId());
		model.addAttribute("action", "updateCurrent");
		return "system/userForm";
	}
	
	/**
	 * 修改资料
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateCurrent", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody User user,Model model) {
		userService.update(user);
		return "success";
	}


	/**
	 * 修改密码
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public String updatePwd(String oldPassword,@Valid @ModelAttribute @RequestBody User user, HttpSession session) {
		if (userService.checkPassword((User) session.getAttribute("user"),oldPassword)) {
			userService.updatePwd(user);
			session.setAttribute("user", user);
			return "success";
		} else {
			return "fail";
		}

	}
	
	//初始化密码
	@RequestMapping(value = "initPwd/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String initPwd(@PathVariable("id") Integer id) {
		User user = userService.getUser(id);
		user.setPlainPassword("123456");
		userService.updatePwd(user);
		return "success";

	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(String loginName) {
		if (userService.getUser(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}
	
	@RequestMapping(value = "getUserByLoginName/{loginName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public User getUserByLoginName(@PathVariable("loginName") String loginName) {
		return userService.getUser(loginName);
	}

	/**
	 * ajax请求校验原密码是否正确
	 * 
	 * @param oldPassword
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "checkPwd")
	@ResponseBody
	public String checkPwd(String oldPassword, HttpSession session) {
		if (userService.checkPassword((User) session.getAttribute("user"),oldPassword)) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Integer id,Model model) {
		if (id != -1) {
			model.addAttribute("user", userService.get(id));
		}
	}
	@RequestMapping(value = "getDeptName/{userId}")
	@ResponseBody
	public String getDeptName(@PathVariable("userId") Integer userId){
		String deptName = "";
		User u = userService.get(userId);
		if(u!=null){
			deptName = u.getOrganization()==null?"":u.getOrganization().getOrgName();
		}
		return deptName;
	}
	
	/**
	 * 获取公司名称
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "getCompName/{userId}")
	@ResponseBody
	public String getCompName(@PathVariable("userId") Integer userId){
		String compName = "";
		User u = userService.get(userId);
		compName = userService.getCompany(u.getOrganization()).getOrgName();
		if(StringUtils.isNotBlank(compName)){
			return compName;
		}else{
			return "";
		}
	}
	
	@RequestMapping(value = "getUserNameByLogin/{loginName}")
	@ResponseBody
	public String getUserNameByLogin(@PathVariable("loginName") String loginName){
		User u = userService.getUserByLoginName(loginName);
		if(u!=null){
			return u.getName();
		}else{
			return "";
		}
	}
	
	/**
	 * 跳转用户相关关联单位
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toUserRelation4Affi/{loginName}", method = RequestMethod.GET)
	public String toUserRelation4Affi(Model model,@PathVariable("loginName") String loginName) {
		List<Long> affiCodes = userService.getCurrentRelation4Affi(loginName);
		model.addAttribute("affiCodes", affiCodes);
		return "system/userRelation4Affi";
	}
	
	/**
	 * 更新用户相关关联单位
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequestMapping(value = "setUserRelation4Affi/{id}/{codes}")
	@ResponseBody
	public String setUserRelation4Affi(@PathVariable("id") Long id,@PathVariable("codes") String codes ) {
		userRoleService.setUserRelation4Affi(id, codes);
		return "success";
	}
	
	/**
	 * 跳转用户相关采购
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toUserRelation4Purchase/{loginName}", method = RequestMethod.GET)
	public String toUserRelation4Purchase(Model model,@PathVariable("loginName") String loginName) {
		List<Long> purchaseIds = userService.getCurrentRelation4Purchase(loginName);
		model.addAttribute("purchaseIds", purchaseIds);
		return "system/userRelation4Purchase";
	}
	
	/**
	 * 跳转用户相关销售
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toUserRelation4Sale/{loginName}", method = RequestMethod.GET)
	public String toUserRelation4Sale(Model model,@PathVariable("loginName") String loginName) {
		List<Long> saleIds = userService.getCurrentRelation4Sale(loginName);
		model.addAttribute("saleIds", saleIds);
		return "system/userRelation4Sale";
	}
	
	/**
	 * 跳转用户相关订船合同
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toUserRelation4OrderShip/{loginName}", method = RequestMethod.GET)
	public String toUserRelation4OrderShip(Model model,@PathVariable("loginName") String loginName) {
		List<Long> orderShipIds = userService.getCurrentRelation4OrderShip(loginName);
		model.addAttribute("orderShipIds", orderShipIds);
		return "system/userRelation4OrderShip";
	}
	
	/**
	 * 跳转用户相关订船合同
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toUserRelation4Insurance/{loginName}", method = RequestMethod.GET)
	public String toUserRelation4Insurance(Model model,@PathVariable("loginName") String loginName) {
		List<Long> insuranceIds = userService.getCurrentRelation4Insurance(loginName);
		model.addAttribute("insuranceIds", insuranceIds);
		return "system/userRelation4Insurance";
	}
	
	/**
	 * 跳转用户相关订船合同
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toUserRelation4Highway/{loginName}", method = RequestMethod.GET)
	public String toUserRelation4Highway(Model model,@PathVariable("loginName") String loginName) {
		List<Long> highwayIds = userService.getCurrentRelation4Highway(loginName);
		model.addAttribute("highwayIds", highwayIds);
		return "system/userRelation4Highway";
	}
	
	/**
	 * 跳转用户相关订船合同
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toUserRelation4Railway/{loginName}", method = RequestMethod.GET)
	public String toUserRelation4Railway(Model model,@PathVariable("loginName") String loginName) {
		List<Long> railwayIds = userService.getCurrentRelation4Railway(loginName);
		model.addAttribute("railwayIds", railwayIds);
		return "system/userRelation4Railway";
	}
	
	/**
	 * 跳转用户相关码头合同
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toUserRelation4Port/{loginName}", method = RequestMethod.GET)
	public String toUserRelation4Port(Model model,@PathVariable("loginName") String loginName) {
		List<Long> portIds = userService.getCurrentRelation4Port(loginName);
		model.addAttribute("portIds", portIds);
		return "system/userRelation4Port";
	}
	
	
}
