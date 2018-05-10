package com.cbmie.lh.feedback.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.feedback.entity.FeedbackTheme;
import com.cbmie.lh.feedback.entity.ThemeMember;
import com.cbmie.lh.feedback.service.FeedbackThemeService;
import com.cbmie.lh.feedback.service.ThemeMemberService;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="feedback/theme")
public class FeedbackThemeController extends BaseController {

	@Autowired
	private FeedbackThemeService themeService;
	@Autowired
	private UserService userService;
	@Autowired
	private ThemeMemberService memberService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model){
		User currentUser = UserUtil.getCurrentUser();
		model.addAttribute("curUserId", currentUser.getId());
		return "feedback/themeList";
	} 
	
	@RequestMapping(value = "listRel",method = RequestMethod.GET)
	public String listRel(Model model){
		User currentUser = UserUtil.getCurrentUser();
		model.addAttribute("curUserId", currentUser.getId());
		return "feedback/contentBack";
	} 
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupListBySelf(HttpServletRequest request) {
		Page<FeedbackTheme> page = getPage(request);
		Map<String,Object> queryParam = new HashMap<String,Object>();
		User currentUser = UserUtil.getCurrentUser();
		themeService.getList(page, queryParam, currentUser.getId());
		return getEasyUIData(page);
	}
	
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupList(HttpServletRequest request) {
		Page<FeedbackTheme> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = themeService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @throws Exception 
	 */
	/*@RequestMapping(value = "jsonAll", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupListNoPer(HttpServletRequest request) {
		Page<FeedbackTheme> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = themeService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}*/
	
	/**
	 * 添加
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		FeedbackTheme feedbackTheme = new FeedbackTheme();
		User currentUser = UserUtil.getCurrentUser();
		feedbackTheme.setCreaterNo(currentUser.getLoginName());
		feedbackTheme.setCreaterName(currentUser.getName());
		feedbackTheme.setCreaterDept(currentUser.getOrganization().getOrgName());
		feedbackTheme.setCreateDate(new Date());
		feedbackTheme.setDutyUser(String.valueOf(currentUser.getId()));
		feedbackTheme.setDutyDept(currentUser.getOrganization().getId());
		model.addAttribute("feedbackTheme", feedbackTheme);
		model.addAttribute("action", "create");
		return "feedback/themeForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid FeedbackTheme feedbackTheme, Model model) throws JsonProcessingException {
		try {
			themeService.save(feedbackTheme);
			setMember(feedbackTheme);
			themeService.update(feedbackTheme);
			return setReturnData("success","保存成功",feedbackTheme.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",feedbackTheme.getId());
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
		FeedbackTheme feedbackTheme = themeService.get(id);
		User currentUser = UserUtil.getCurrentUser();
		feedbackTheme.setUpdaterNo(currentUser.getLoginName());
		feedbackTheme.setUpdaterName(currentUser.getName());
		feedbackTheme.setUpdateDate(new Date());
		getMember(feedbackTheme);
		model.addAttribute("feedbackTheme",feedbackTheme );
		model.addAttribute("action", "update");
		return "feedback/themeForm";
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
	public String update(@Valid @ModelAttribute @RequestBody FeedbackTheme feedbackTheme, Model model) throws JsonProcessingException {
		setMember(feedbackTheme);
		themeService.update(feedbackTheme);
		return setReturnData("success","更新成功",feedbackTheme.getId());
	}
	@ModelAttribute
	public void getFeedbackTheme(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("feedbackTheme", themeService.get(id));
		}
	}
	public void getMember(FeedbackTheme feedbackTheme){
		 String themeMemberIds = "";
		 String themeMembers = "";
		 String themeMemberKeyIds = "";
		 String themeMemberKeys = "";
		List<ThemeMember> members = feedbackTheme.getMembers();
		for(ThemeMember member : members){
			themeMemberIds = themeMemberIds+member.getUser().getId()+",";
			themeMembers = themeMembers + member.getUser().getName()+",";
			if("1".equals(member.getKeyFlag())){
				themeMemberKeyIds = themeMemberKeyIds+member.getUser().getId()+",";
				themeMemberKeys = themeMemberKeys+member.getUser().getName()+",";
			}
		}
		feedbackTheme.setThemeMemberIds(themeMemberIds.equals("")?"":themeMemberIds.substring(0, themeMemberIds.length()-1));
		feedbackTheme.setThemeMembers(themeMembers.equals("")?"":themeMembers.substring(0, themeMembers.length()-1));
		feedbackTheme.setThemeMemberKeyIds(themeMemberKeyIds.equals("")?"":themeMemberKeyIds.substring(0, themeMemberKeyIds.length()-1));
		feedbackTheme.setThemeMemberKeys(themeMemberKeys.equals("")?"":themeMemberKeys.substring(0, themeMemberKeys.length()-1));
	}
	public void setMember(FeedbackTheme feedbackTheme){
		List<ThemeMember> members = feedbackTheme.getMembers();
		String themeMemberIds = feedbackTheme.getThemeMemberIds();
		String themeMemberKeyIds = feedbackTheme.getThemeMemberKeyIds();
		String[] themeMemberIds_array =StringUtils.isNotBlank(themeMemberIds)? themeMemberIds.split(","):new String[0];
		String[] themeMemberKeyIds_array = StringUtils.isNotBlank(themeMemberKeyIds)? themeMemberKeyIds.split(","):new String[0];
		List<ThemeMember> member_keep = new ArrayList<ThemeMember>();
		for(String userId : themeMemberIds_array){
			boolean flag = false;
			a: for(int i =0;i<members.size();i++){
				if(members.get(i).getUser().getId().toString().equals(userId)){
					flag = true;
					member_keep.add(members.get(i));
					members.get(i).setKeyFlag("0");
					b:for(String keyId : themeMemberKeyIds_array){
						if(userId.equals(keyId)){
							members.get(i).setKeyFlag("1");
							break b;
						}
					}
					break a;
				}
			}
			if(!flag){
				ThemeMember member_new = new ThemeMember();
				member_keep.add(member_new);
				User user = userService.get(Integer.parseInt(userId));
				member_new.setThemeId(feedbackTheme.getId());
				member_new.setUser(user);
				b:for(String keyId : themeMemberKeyIds_array){
					if(userId.equals(keyId)){
						member_new.setKeyFlag("1");
						break b;
					}
				}
			}
		}
		for(int j=0;j<members.size();j++){
			ThemeMember cur = members.get(j);
			boolean flag = true;
			c: for(ThemeMember m : member_keep){
				if(cur.equals(m)){
					flag = false;
					break c;
				}
			}
			if(flag){
				members.remove(cur);
				memberService.delete(cur);
				j--;
			}
		}
		feedbackTheme.getMembers().clear();
		feedbackTheme.setMembers(member_keep);
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
		themeService.delete(id);
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
		FeedbackTheme feedbackTheme = themeService.get(id);
		getMember(feedbackTheme);
		model.addAttribute("feedbackTheme", feedbackTheme);
		model.addAttribute("action", "view");
		return "feedback/themeForm";
	}
	@RequestMapping(value = "end/{id}")
	@ResponseBody
	public String endTheme(@PathVariable("id") Long id){
		FeedbackTheme feedbackTheme = themeService.get(id);
		feedbackTheme.setState("10990003");
		User currentUser = UserUtil.getCurrentUser();
		feedbackTheme.setCloseUser(currentUser.getId());
		themeService.update(feedbackTheme);
		return "success";
	}
	@RequestMapping(value = "toSelectUsers/{themeId}")
	public String toSelectUsers(@PathVariable("themeId") Long themeId,Model model){
		model.addAttribute("themeId", themeId);
		return "feedback/selectUsers";
	}
}
