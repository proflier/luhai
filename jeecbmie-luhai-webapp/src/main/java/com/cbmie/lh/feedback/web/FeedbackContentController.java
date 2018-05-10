package com.cbmie.lh.feedback.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.feedback.entity.FeedbackContent;
import com.cbmie.lh.feedback.entity.FeedbackTheme;
import com.cbmie.lh.feedback.entity.ThemeMember;
import com.cbmie.lh.feedback.service.FeedbackContentService;
import com.cbmie.lh.feedback.service.FeedbackThemeService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="feedback/content")
public class FeedbackContentController extends BaseController {

	@Autowired
	private FeedbackThemeService themeService;
	@Autowired
	private FeedbackContentService contentService;
	
	
	@RequestMapping(value = "list/{themeId}",method = RequestMethod.GET)
	public String list(@PathVariable("themeId") Long themeId,Model model){
		FeedbackTheme feedbackTheme = themeService.get(themeId);
		getMember(feedbackTheme);
		model.addAttribute("feedbackTheme", feedbackTheme);
		return "feedback/contentList";
	} 
	
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> groupList(HttpServletRequest request) {
		Page<FeedbackContent> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = contentService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加
	 * @param model
	 */
	@RequestMapping(value = "create/{themeId}", method = RequestMethod.GET)
	public String createForm(@PathVariable("themeId") Long themeId,Model model) {
		FeedbackContent feedbackContent = new FeedbackContent();
		User currentUser = UserUtil.getCurrentUser();
		feedbackContent.setFeedbackThemeId(themeId);
		feedbackContent.setUserId(currentUser.getId());
		feedbackContent.setDeptId(currentUser.getOrganization().getId());
		feedbackContent.setPublishDate(new Date());
		model.addAttribute("feedbackContent", feedbackContent);
		model.addAttribute("action", "create");
		return "feedback/contentForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid FeedbackContent feedbackContent, Model model) throws JsonProcessingException {
		try {
			contentService.save(feedbackContent);
			return setReturnData("success","保存成功",feedbackContent.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",feedbackContent.getId());
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
	
	@RequestMapping(value = "checkKey/{themeId}", method = RequestMethod.GET)
	@ResponseBody
	public String checkKeyPeaple(@PathVariable("themeId") Long themeId){
		boolean result = contentService.checkKeyPeaple(themeId);
		if(result){
			return "1";
		}else{
			return "0";
		}
	}
}
