package com.cbmie.lh.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.accessory.service.AccessoryService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.feedback.entity.FeedbackContent;
import com.cbmie.lh.feedback.entity.FeedbackTheme;
import com.cbmie.lh.feedback.service.FeedbackContentService;
import com.cbmie.lh.feedback.service.FeedbackThemeService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("mobile/feedback")
public class Feedback extends BaseController {
	
	@Autowired
	private FeedbackThemeService themeService;
	
	@Autowired
	private AccessoryService accessoryService;
	
	@Autowired
	private FeedbackContentService contentService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		return "mobile/feedback/list";
	}
	
	@RequestMapping(value = "initList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<FeedbackTheme> initList(HttpServletRequest request) throws Exception {
		Page<FeedbackTheme> page = getPage(request);
		User currentUser = UserUtil.getCurrentUser();
		Map<String,Object> queryParam = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(request.getParameter("title"))) {
			queryParam.put("title", request.getParameter("title"));
		}
		themeService.getList(page, queryParam, currentUser.getId());
		return page.getResult();
	}
	
	/**
	 * 初始化详细
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public String detail(HttpServletRequest request, Model model) throws Exception {
		FeedbackTheme feedbackTheme = themeService.get(Long.valueOf(request.getParameter("themeId")));
		model.addAttribute("feedbackTheme", feedbackTheme);
		
		//附件
		model.addAttribute("accList", accessoryService.getByObj(feedbackTheme));
		
		return "mobile/feedback/todo";
	}
	
	/**
	 * 反馈跳转
	 */
	@RequestMapping(value = "create/{feedbackThemeId}", method = RequestMethod.GET)
	public String create(@PathVariable("feedbackThemeId") Long feedbackThemeId, Model model) {
		return "mobile/feedback/do";
	}
	
	/**
	 * 反馈添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(HttpServletRequest request, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		Long feedbackThemeId = Long.valueOf(request.getParameter("feedbackThemeId"));
		FeedbackContent feedbackContent = new FeedbackContent();
		feedbackContent.setFeedbackThemeId(feedbackThemeId);
		feedbackContent.setContent(request.getParameter("content"));
		feedbackContent.setUserId(currentUser.getId());
		feedbackContent.setDeptId(currentUser.getOrganization().getId());
		feedbackContent.setPublishDate(new Date());
		contentService.save(feedbackContent);
		FeedbackTheme feedbackTheme = themeService.get(feedbackThemeId);
		model.addAttribute("feedbackTheme", feedbackTheme);
		return "mobile/feedback/record";
	}

}
