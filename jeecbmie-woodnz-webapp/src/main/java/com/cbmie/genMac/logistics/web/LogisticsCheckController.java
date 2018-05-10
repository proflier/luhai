package com.cbmie.genMac.logistics.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.logistics.entity.LogisticsCheck;
import com.cbmie.genMac.logistics.service.LogisticsCheckService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 物流合作公司评审controller
 */
@Controller
@RequestMapping("logistics/logisticsCheck")
public class LogisticsCheckController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LogisticsCheckService logisticsCheckService;

	@Autowired
	protected ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistics/logisticsCheckList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> enterpriseStockCheckList(HttpServletRequest request) {
		Page<LogisticsCheck> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = logisticsCheckService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("logisticsCheck", new LogisticsCheck());
		model.addAttribute("action", "create");
		return "logistics/logisticsCheckForm";
	}

	/**
	 * 添加物流合作公司评审
	 * @param logisticsCheck
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid LogisticsCheck logisticsCheck, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		logisticsCheck.setSummary("物流合作公司评审[" + logisticsCheck.getCompanyName() + "--"+logisticsCheck.getCheckId()+"]");
		logisticsCheck.setCreaterNo(currentUser.getLoginName());
		logisticsCheck.setCreaterName(currentUser.getName());
		logisticsCheck.setCreateDate(new Date());
		logisticsCheck.setCreaterDept(currentUser.getOrganization().getOrgName());
		logisticsCheckService.save(logisticsCheck);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(logisticsCheck.getId());
		}
		return "success";
	}

	/**
	 * 修改物流合作公司评审跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		LogisticsCheck logisticsCheck = new LogisticsCheck();
		logisticsCheck = logisticsCheckService.get(id);
		model.addAttribute("logisticsCheck", logisticsCheck);
		model.addAttribute("action", "update");
		return "logistics/logisticsCheckForm";
	}

	/**
	 * 修改物流合作公司评审
	 * 
	 * @param logisticsCheck
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody LogisticsCheck logisticsCheck, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		logisticsCheck.setSummary("物流合作公司评审[" + logisticsCheck.getCompanyName() + "--"+logisticsCheck.getCheckId()+"]");
		logisticsCheck.setUpdaterNo(currentUser.getLoginName());
		logisticsCheck.setUpdaterName(currentUser.getName());
		logisticsCheck.setUpdateDate(new Date());
		logisticsCheckService.update(logisticsCheck);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(logisticsCheck.getId());
		}
		return "success";
	}

	/**
	 * 删除物流合作公司评审
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		logisticsCheckService.delete(id);
		return "success";
	}

	/**
	 * 物流合作公司评审明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		LogisticsCheck logisticsCheck = new LogisticsCheck();
		logisticsCheck = logisticsCheckService.get(id);
		model.addAttribute("logisticsCheck", logisticsCheck);
		model.addAttribute("action", "detail");
		return "logistics/logisticsCheckDetail";
	}


	
	
	/**
	 * 提交流程申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "apply/{id}")
	@ResponseBody
	public String apply(@PathVariable("id") Long id) {
		try {
			User user = UserUtil.getCurrentUser();
			LogisticsCheck logisticsCheck = logisticsCheckService.get(id);
//			logisticsCheck.setUserId((user.getLoginName()).toString());
			logisticsCheck.setState("已提交");
			logisticsCheckService.save(logisticsCheck);
			Map<String, Object> variables = new HashMap<String, Object>();
			activitiService.startWorkflow(logisticsCheck, "wf_logisticsCheck", variables, (user.getLoginName()).toString());

			return "success";
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				logger.warn("没有部署流程!", e);
				return "no deployment";
			} else {
				logger.error("启动流程失败：", e);
				return "start fail";
			}
		} catch (Exception e) {
			logger.error("启动流程失败：", e);
			return "start fail";
		}
	}
	
	/**
	 * 撤回流程申请
	 * @return
	 */
	@RequestMapping(value = "callBack/{id}/{processInstanceId}")
	@ResponseBody
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId, HttpSession session) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId)) {
				LogisticsCheck logisticsCheck  = logisticsCheckService.get(id);
				logisticsCheck.setProcessInstanceId(null);
				logisticsCheck.setState("草稿");
				logisticsCheckService.save(logisticsCheck);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}

	@ModelAttribute
	public void getLogisticsCheck(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("logisticsCheck", logisticsCheckService.get(id));
		}
	}

}
