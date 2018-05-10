package com.cbmie.lh.permission.web;

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
import com.cbmie.lh.permission.entity.PermissionGroup;
import com.cbmie.lh.permission.service.PermissionGroupService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
/**
 * 业务权限组
 * @author admin
 *
 */
@Controller
@RequestMapping(value="permission/group")
public class PermissionGroupController extends BaseController {

	@Autowired
	private PermissionGroupService groupService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "permission/businessGroupList";
	} 
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupList(HttpServletRequest request) {
		Page<PermissionGroup> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		User currentUser = UserUtil.getCurrentUser();
		PropertyFilter filter = new PropertyFilter("EQS_userId",currentUser.getId().toString());
		filters.add(filter);
		page = groupService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "jsonAll", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupListNoPer(HttpServletRequest request) {
		Page<PermissionGroup> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = groupService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		PermissionGroup permissionGroup = new PermissionGroup();
		User currentUser = UserUtil.getCurrentUser();
		permissionGroup.setCreaterNo(currentUser.getLoginName());
		permissionGroup.setCreaterName(currentUser.getName());
		permissionGroup.setCreaterDept(currentUser.getOrganization().getOrgName());
		permissionGroup.setCreateDate(new Date());
		permissionGroup.setUpdateDate(new Date());
		model.addAttribute("permissionGroup", permissionGroup);
		model.addAttribute("action", "create");
		return "permission/businessGroupForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PermissionGroup permissionGroup, Model model) throws JsonProcessingException {
		try {
			if (!groupService.checkNameUique(permissionGroup)) {
				return setReturnData("fail","名称重复",permissionGroup.getId());
			}
			groupService.save(permissionGroup);
			return setReturnData("success","保存成功",permissionGroup.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",permissionGroup.getId());
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
		model.addAttribute("permissionGroup", groupService.get(id));
		model.addAttribute("action", "update");
		return "permission/businessGroupForm";
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
	public String update(@Valid @ModelAttribute @RequestBody PermissionGroup permissionGroup, Model model) throws JsonProcessingException {
		if (!groupService.checkNameUique(permissionGroup)) {
			return setReturnData("fail","业务权限名称重复",permissionGroup.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		permissionGroup.setUpdaterNo(currentUser.getLoginName());
		permissionGroup.setUpdaterName(currentUser.getName());
		permissionGroup.setUpdateDate(new Date());
		groupService.update(permissionGroup);
		return setReturnData("success","更新成功",permissionGroup.getId());
	}
	@ModelAttribute
	public void getPermissionGroup(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("permissionGroup", groupService.get(id));
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
		groupService.delete(id);
		return setReturnData("success","删除成功",id);
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
		model.addAttribute("permissionGroup", groupService.get(id));
		model.addAttribute("action", "view");
		return "permission/businessGroupForm";
	}
	
	/**
	 * get permission groups for  theme
	 * */
	@RequestMapping(value = "groupsSelect", method = RequestMethod.GET)
	@ResponseBody
	public List<PermissionGroup> getRelPermissionGroups(){
		return groupService.getRelPermissionGroups();
	}
	
	@RequestMapping(value = "groupName/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getGroupNameById(@PathVariable("id") Long id){
		PermissionGroup group = groupService.get(id);
		if(group!=null){
			return group.getGroupName();
		}else{
			return null;
		}
	}
	@RequestMapping(value = "groupsSelf", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getGroupBySelf(){
		User currentUser = UserUtil.getCurrentUser();
		return groupService.getGroupBySelf(currentUser.getLoginName());
	}
}
