package com.cbmie.lh.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.accessory.service.AccessoryService;
import com.cbmie.activiti.entity.ApprovalOpinion;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.activiti.service.ProcessEntityService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("mobile/procPass")
public class ProcPass extends BaseController {
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ProcessEntityService procEntityService;
	
	@Autowired
	private AccessoryService accessoryService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("loginName", UserUtil.getCurrentUser().getLoginName());
		return "mobile/procPass/list";
	}
	
	@RequestMapping(value = "initList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Map<String,Object>> initList(HttpServletRequest request) throws Exception {
		Page<Map<String, Object>> page = getPage(request);
		List<Map<String, Object>> result = activitiService.getProcPassList(request);
		createPageByResult(page, result);
		return page.getResult();
	}
	
	/**
	 * 初始化详细
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public String detail(HttpServletRequest request, Model model) throws Exception {
		ApprovalOpinion approval = new ApprovalOpinion();
		BeanUtils.copyProperties(approval, request.getParameterMap());
		//初始化
		Class<?> clazz = Class.forName(procEntityService.getClazzFullName(approval.getKey()));
		Object obj = activitiService.getReflectionObject(clazz, Long.valueOf(approval.getBusinessKey()));
		model.addAttribute("obj", obj);
		model.addAttribute("approval", approval);
		
		//附件
		model.addAttribute("accList", accessoryService.getByObj(obj));
		
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/procPass/todo";
	}
	
}
