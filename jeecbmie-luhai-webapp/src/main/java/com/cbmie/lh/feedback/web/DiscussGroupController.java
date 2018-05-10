package com.cbmie.lh.feedback.web;

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
import com.cbmie.lh.feedback.entity.DiscussGroup;
import com.cbmie.lh.feedback.service.DiscussGroupService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="discuss/group")
public class DiscussGroupController extends BaseController {

	@Autowired
	private DiscussGroupService groupService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "feedback/groupList";
	} 
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupList(HttpServletRequest request) {
		Page<DiscussGroup> page = getPage(request);
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
		Page<DiscussGroup> page = getPage(request);
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
		DiscussGroup discussGroup = new DiscussGroup();
		User currentUser = UserUtil.getCurrentUser();
		discussGroup.setCreaterNo(currentUser.getLoginName());
		discussGroup.setCreaterName(currentUser.getName());
		discussGroup.setCreaterDept(currentUser.getOrganization().getOrgName());
		discussGroup.setCreateDate(new Date());
		discussGroup.setUpdateDate(new Date());
		model.addAttribute("discussGroup", discussGroup);
		model.addAttribute("action", "create");
		return "feedback/groupForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid DiscussGroup discussGroup, Model model) throws JsonProcessingException {
		try {
			if (!groupService.checkNameUique(discussGroup)) {
				return setReturnData("fail","讨论组名称重复",discussGroup.getId());
			}
			groupService.save(discussGroup);
			return setReturnData("success","保存成功",discussGroup.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",discussGroup.getId());
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
		model.addAttribute("discussGroup", groupService.get(id));
		model.addAttribute("action", "update");
		return "feedback/groupForm";
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
	public String update(@Valid @ModelAttribute @RequestBody DiscussGroup discussGroup, Model model) throws JsonProcessingException {
		if (!groupService.checkNameUique(discussGroup)) {
			return setReturnData("fail","讨论组名称重复",discussGroup.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		discussGroup.setUpdaterNo(currentUser.getLoginName());
		discussGroup.setUpdaterName(currentUser.getName());
		discussGroup.setUpdateDate(new Date());
		groupService.update(discussGroup);
		return setReturnData("success","更新成功",discussGroup.getId());
	}
	@ModelAttribute
	public void getDiscussGroup(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("discussGroup", groupService.get(id));
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
		model.addAttribute("discussGroup", groupService.get(id));
		model.addAttribute("action", "view");
		return "feedback/groupForm";
	}
	
	/**
	 * get discuss groups for feedback theme
	 * */
	@RequestMapping(value = "groupsSelect", method = RequestMethod.GET)
	@ResponseBody
	public List<DiscussGroup> getRelDiscussGroups(){
		return groupService.getRelDiscussGroups();
	}
	
	@RequestMapping(value = "groupName/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getGroupNameById(@PathVariable("id") Long id){
		DiscussGroup group = groupService.get(id);
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
