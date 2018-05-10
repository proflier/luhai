package com.cbmie.lh.mobile;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
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
import com.cbmie.activiti.web.ActivitiController;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("mobile/taskTodo")
public class TaskTodo {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private ActivitiController activitiController;
	
	@Autowired
	private ProcessEntityService procEntityService;
	
	@Autowired
	private AccessoryService accessoryService;
	
	/**
	 * 获取待办任务总条数
	 */
	@RequestMapping(value = "count", method = RequestMethod.GET)
	@ResponseBody
	public String count(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> list = activitiService.getTodoList(request);
		return String.valueOf(list.size());
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("loginName", UserUtil.getCurrentUser().getLoginName());
		return "mobile/todo/list";
	}
	
	@RequestMapping(value = "initList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Map<String,Object>> initList(HttpServletRequest request) throws Exception {
		return activitiService.getTodoList(request);
	}
	
	/**
	 * 初始化详细
	 */
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	public String detail(HttpServletRequest request, Model model) throws Exception {
		ApprovalOpinion approval = new ApprovalOpinion();
		BeanUtils.copyProperties(approval, request.getParameterMap());
		//签收
		if("待签收".equals(request.getParameter("status"))){
			taskService.claim(approval.getTaskId(), request.getParameter("loginName"));
		}
		//初始化
		Class<?> clazz = Class.forName(procEntityService.getClazzFullName(approval.getKey()));
		Object obj = activitiService.getReflectionObject(clazz, Long.valueOf(approval.getBusinessKey()));
		model.addAttribute("obj", obj);
		model.addAttribute("approval", approval);
		
		//附件
		model.addAttribute("accList", accessoryService.getByObj(obj));
		
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/todo/todo";
	}
	
	/**
	 * 审批跳转
	 */
	@RequestMapping(value = "approval", method = RequestMethod.POST)
	public String approvalForm(HttpServletRequest request, Model model) throws Exception {
		//初始化参数
		ApprovalOpinion approval = new ApprovalOpinion();
		BeanUtils.copyProperties(approval, request.getParameterMap());
		//请求工作流
		activitiController.toApprovalFormDeal(approval.getProcessInstanceId(), approval.getTaskId(),
				approval.getBusinessKey(), approval.getKey(), model);
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/todo/do";
	}
	
	/**
	 * 发送
	 */
	@RequestMapping(value = "send", method = RequestMethod.POST)
	public String agree(HttpServletRequest request, Model model) throws Exception {
		ApprovalOpinion approval = new ApprovalOpinion();
		BeanUtils.copyProperties(approval, request.getParameterMap());
		//请求工作流
		activitiController.sendActiviti(approval, model, "APP");
		model.addAttribute("taskList", activitiService.getTodoList(request));
		model.addAttribute("loginName", request.getParameter("loginName"));
		return "mobile/todo/list";
	}
	
	/**
	 * 流程跟踪
	 * @throws Exception 
	 */
	@RequestMapping(value = "trace", method = RequestMethod.POST)
	public String trace(HttpServletRequest request, Model model) throws Exception {
		String pid = request.getParameter("processInstanceId");
		model.addAttribute("processDefinitionId", activitiService.findProcessDefinitionEntityByProcessInstanceId(pid).getProcessDefinition().getId());
		//获取流程跟踪列表
		model.addAttribute("traceList", activitiController.trace(request, pid).get("rows"));
		return "mobile/trace";
	}

}
