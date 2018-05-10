package com.cbmie.lh.feedback.web;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.cbmie.lh.feedback.entity.DiscussMember;
import com.cbmie.lh.feedback.service.DiscussGroupService;
import com.cbmie.lh.feedback.service.DiscussMemberService;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="discuss/member")
public class DiscussMemberController extends BaseController {

	@Autowired
	private DiscussMemberService memberService;
	@Autowired
	private DiscussGroupService groupService;
	@Autowired
	private UserService userService;
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupList(HttpServletRequest request) {
		Page<DiscussMember> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = memberService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 */
	/*@RequestMapping(value = "create/{groupId}", method = RequestMethod.GET)
	public String createForm(@PathVariable("groupId") Long groupId,Model model) {
		DiscussMember discussMember = new DiscussMember();
		discussMember.setDiscussGroupId(groupId);
		model.addAttribute("discussMember", discussMember);
		model.addAttribute("action", "create");
		return "discuss/memberForm";
	}*/
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	/*@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid DiscussMember discussMember, Model model) throws JsonProcessingException {
		try {
			memberService.save(discussMember);
			return setReturnData("success","保存成功",discussMember.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",discussMember.getId());
		}
	}*/
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("discussMember", memberService.get(id));
		model.addAttribute("action", "update");
		return "discuss/memberForm";
	}*/
	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	/*@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody DiscussMember discussMember, Model model) throws JsonProcessingException {
		memberService.update(discussMember);
		return setReturnData("success","更新成功",discussMember.getId());
	}
	@ModelAttribute
	public void getDiscussMember(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("discussMember", memberService.get(id));
		}
	}*/
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	/*@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws  Exception {
		memberService.delete(id);
		return setReturnData("success","删除成功",id);
	}*/
	
	/**
	 * 
	 * @param model
	 */
	@RequestMapping(value = "add/{groupId}", method = RequestMethod.GET)
	public String addForm(@PathVariable("groupId") Long groupId,Model model) {
		List<DiscussMember> members = groupService.get(groupId).getMembers();
		List<Integer> userIds = new ArrayList<Integer>();
		for(DiscussMember member : members){
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
		DiscussGroup group = groupService.get(groupId);
		if(allUsers.size()==0){
			group.getMembers().clear();
			groupService.update(group);
			return setReturnData("success","保存成功",group.getId());
		}
		List<DiscussMember> members = group.getMembers();
		List<DiscussMember> member_removed = new ArrayList<DiscussMember>();
		for(DiscussMember member : members){
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
		for(DiscussMember m_r : member_removed){
			members.remove(m_r);
		}
		for(Integer i : allUsers){
			User user = userService.get(i);
			DiscussMember newMember = new DiscussMember();
			newMember.setDiscussGroupId(groupId);
			newMember.setUser(user);
			members.add(newMember);
		}
		groupService.update(group);
		return setReturnData("success","保存成功",group.getId());
	}
	/**
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	/*@RequestMapping(value = "batchSet/{groupId}", method = RequestMethod.POST)
	@ResponseBody
	public String batchSet(@PathVariable("groupId") Long groupId,@RequestBody List<Long> memberIds) throws JsonProcessingException {
		DiscussGroup group = groupService.get(groupId);
		List<DiscussMember> members = group.getMembers();
		for(DiscussMember member : members){
			member.setKeyFlag("0");
		n:	for(Long memberId : memberIds){
				if(member.getId().longValue() == memberId.longValue()){
					member.setKeyFlag("1");
					break n;
				}
			}
		}
		groupService.update(group);
		return setReturnData("success","保存成功",group.getId());
	}*/
}
