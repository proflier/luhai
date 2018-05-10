package com.cbmie.genMac.credit.web;

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
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.service.OpenCreditService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 开证controller
 */
@Controller
@RequestMapping("credit/openCredit")
public class OpenCreditController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OpenCreditService openCreditService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "credit/openCreditApplyList";
	}
	
	/**
	 * 开证登记页面
	 */
	@RequestMapping(value = "regList", method = RequestMethod.GET)
	public String openCreditRegList() {
		return "credit/openCreditRegList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> openCreditList(HttpServletRequest request) {
		Page<OpenCredit> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = openCreditService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("openCredit", new OpenCredit());
		model.addAttribute("action", "create");
		return "credit/openCreditApplyForm";
	}

	/**
	 * 添加
	 * 
	 * @param openCredit
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid OpenCredit openCredit, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		openCredit.setCreaterNo(currentUser.getLoginName());
		openCredit.setCreaterName(currentUser.getName());
		openCredit.setCreaterDept(currentUser.getOrganization().getOrgName());
		openCredit.setCreateDate(new Date());
		openCredit.setSummary("开证申请[" + openCredit.getContractNo() + "]");
		openCreditService.save(openCredit);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(openCredit.getId());
		}
		return "success";
	}

	/**
	 * 修改跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("openCredit", openCreditService.get(id));
		model.addAttribute("action", "update");
		return "credit/openCreditApplyForm";
	}

	/**
	 * 修改
	 * 
	 * @param openCredit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OpenCredit openCredit, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		openCredit.setUpdaterNo(currentUser.getLoginName());
		openCredit.setUpdaterName(currentUser.getName());
		openCredit.setCreaterDept(currentUser.getOrganization().getOrgName());
		openCredit.setUpdateDate(new Date());
		openCredit.setSummary("开证申请[" + openCredit.getContractNo() + "]");
		openCreditService.update(openCredit);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(openCredit.getId());
		}
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		openCreditService.delete(id);
		return "success";
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
		model.addAttribute("openCredit", openCreditService.get(id));
		return "credit/openCreditDetail";
	}
	
	@ModelAttribute
	public void getOpenCredit(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("openCredit", openCreditService.get(id));
		}
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
			OpenCredit openCredit = openCreditService.get(id);
//			openCredit.setUserId((user.getLoginName()).toString());
			openCredit.setState("已提交");
			openCreditService.save(openCredit);
			Map<String, Object> variables = new HashMap<String, Object>();
			activitiService.startWorkflow(openCredit, "wf_openCredit", variables, (user.getLoginName()).toString());
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
				OpenCredit openCredit = openCreditService.get(id);
				openCredit.setProcessInstanceId(null);
				openCredit.setState("草稿");
				openCreditService.save(openCredit);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	
	/**
	 * 登记跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "reg/{id}", method = RequestMethod.GET)
	public String regForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("openCredit", openCreditService.get(id));
		return "credit/openCreditRegForm";
	}
	
}
