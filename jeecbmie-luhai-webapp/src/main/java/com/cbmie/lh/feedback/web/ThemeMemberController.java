package com.cbmie.lh.feedback.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.feedback.entity.DiscussGroup;
import com.cbmie.lh.feedback.entity.DiscussMember;
import com.cbmie.lh.feedback.entity.ThemeMember;
import com.cbmie.lh.feedback.service.DiscussGroupService;
import com.cbmie.lh.feedback.service.ThemeMemberService;
@Controller
@RequestMapping(value="feedback/theme/member")
public class ThemeMemberController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ThemeMemberController.class);
	@Autowired
	private ThemeMemberService themeMemberService;
	@Autowired
	private DiscussGroupService groupService;
	
	@RequestMapping(value="createMembersByGroupId/{discussGroupId}")
	public List<ThemeMember> getThemeMemberByDiscussGroup(@PathVariable(value="discussGroupId") Long discussGroupId){
		DiscussGroup group = groupService.get(discussGroupId);
		List<ThemeMember> themeMemebers = new ArrayList<ThemeMember>();
		try{
			if(group!=null){
				List<DiscussMember> discussMembers = group.getMembers();
				for(DiscussMember discussMember : discussMembers){
					ThemeMember themeMember = new ThemeMember();
					BeanUtils.copyProperties(themeMember, discussMember);
					themeMember.setId(null);
					themeMemebers.add(themeMember);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("the operation of selecting discussGroup is error!");
		}
		
		return themeMemebers;
	}
	@RequestMapping(value="getMembersByThemeId/{themeId}")
	@ResponseBody
	public List<Map<String,Object>> getMembersByThemeId(@PathVariable(value="themeId") Long themeId){
		return themeMemberService.getMembersByThemeId(themeId);
	}
}
