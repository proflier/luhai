package com.cbmie.lh.permission.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.permission.entity.PermissionGroup;
import com.cbmie.lh.permission.entity.PermissionMember;
import com.cbmie.lh.permission.service.PermissionGroupService;
import com.cbmie.lh.permission.service.PermissionMemberService;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
/**
 * 业务权限组成员
 * @author admin
 *
 */
@Controller
@RequestMapping(value="permission/member")
public class PermissionMemberController extends BaseController {

	@Autowired
	private PermissionMemberService memberService;
	@Autowired
	private PermissionGroupService groupService;
	@Autowired
	private UserService userService;
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupList(HttpServletRequest request) {
		Page<PermissionMember> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = memberService.search(page, filters);
		return getEasyUIData(page);
	}
	
	
	/**
	 * 
	 * @param model
	 */
	@RequestMapping(value = "add/{groupId}", method = RequestMethod.GET)
	public String addForm(@PathVariable("groupId") Long groupId,Model model) {
		List<PermissionMember> members = groupService.get(groupId).getMembers();
		List<Integer> userIds = new ArrayList<Integer>();
		for(PermissionMember member : members){
			userIds.add(member.getUser().getId());
		}
		model.addAttribute("groupId", groupId);
		model.addAttribute("defaultUserIds", userIds);
		return "system/selectUserTree";
	}
	
	/**
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "batchUpdate/{groupId}", method = RequestMethod.POST)
	@ResponseBody
	public String batchUpdate(@PathVariable("groupId") Long groupId,@RequestBody List<Integer> allUsers) throws JsonProcessingException {
		PermissionGroup group = groupService.get(groupId);
		if(allUsers.size()==0){
			group.getMembers().clear();
			groupService.update(group);
			return setReturnData("success","保存成功",group.getId());
		}
		List<PermissionMember> members = group.getMembers();
		List<PermissionMember> member_removed = new ArrayList<PermissionMember>();
		for(PermissionMember member : members){
			boolean flag = true;
			Iterator<Integer> userids = allUsers.iterator();
		n:	while(userids.hasNext()){
				if(userids.next().intValue()==member.getUser().getId()){
					userids.remove();
					flag = false;
					break n;
				}
			}
			if(flag){
				member_removed.add(member);
			}
		}
		for(PermissionMember m_r : member_removed){
			members.remove(m_r);
		}
		for(Integer i : allUsers){
			User user = userService.get(i);
			PermissionMember newMember = new PermissionMember();
			newMember.setPermissionGroupId(groupId);
			newMember.setUser(user);
			members.add(newMember);
		}
		groupService.update(group);
		return setReturnData("success","保存成功",group.getId());
	}
	
}
