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
import com.cbmie.genMac.credit.entity.ChangeCredit;
import com.cbmie.genMac.credit.service.ChangeCreditService;
import com.cbmie.genMac.credit.service.OpenCreditHistoryService;
import com.cbmie.genMac.credit.service.OpenCreditService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 改证controller
 */
@Controller
@RequestMapping("credit/changeCredit")
public class ChangeCreditController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChangeCreditService changeCreditService;
	
	@Autowired
	private OpenCreditService openCreditService;
	
	@Autowired
	private OpenCreditHistoryService openCreditHistoryService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "credit/changeCreditList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> changeCreditList(HttpServletRequest request) {
		Page<ChangeCredit> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = changeCreditService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("changeCredit", new ChangeCredit());
		model.addAttribute("action", "create");
		return "credit/changeCreditForm";
	}

	/**
	 * 添加
	 * 
	 * @param changeCredit
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ChangeCredit changeCredit, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		changeCredit.setCreaterNo(currentUser.getLoginName());
		changeCredit.setCreaterName(currentUser.getName());
		changeCredit.setCreaterDept(currentUser.getOrganization().getOrgName());
		changeCredit.setCreateDate(new Date());
		changeCredit.setSummary("改证申请[" + changeCredit.getContractNo() + "]");
		changeCreditService.save(changeCredit);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(changeCredit.getId());
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
		model.addAttribute("changeCredit", changeCreditService.get(id));
		model.addAttribute("action", "update");
		return "credit/changeCreditForm";
	}

	/**
	 * 修改
	 * 
	 * @param changeCredit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ChangeCredit changeCredit, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		changeCredit.setUpdaterNo(currentUser.getLoginName());
		changeCredit.setUpdaterName(currentUser.getName());
		changeCredit.setCreaterDept(currentUser.getOrganization().getOrgName());
		changeCredit.setUpdateDate(new Date());
		changeCredit.setSummary("改证申请[" + changeCredit.getContractNo() + "]");
		changeCreditService.update(changeCredit);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(changeCredit.getId());
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
		changeCreditService.delete(id);
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
		model.addAttribute("changeCredit", changeCreditService.get(id));
		return "credit/changeCreditDetail";
	}
	
	/**
	 * 办理查看业务信息页面跳转
	 */
	@RequestMapping(value = "detail/work/{id}", method = RequestMethod.GET)
	public String detailWork(@PathVariable("id") Long id, Model model) {
		model.addAttribute("changeCredit", changeCreditService.get(id));
		return "credit/changeCreditDetailWork";
	}
	
	@ModelAttribute
	public void getChangeCredit(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("changeCredit", changeCreditService.get(id));
		}
	}
	
	/**
	 * 配置改证跳转
	 */
	@RequestMapping(value = "config", method = RequestMethod.GET)
	public String toConfig() {
		return "credit/changeCreditConfig";
	}
	
	/**
	 * 配置-before
	 */
	@RequestMapping(value = "config/{id}", method = RequestMethod.GET)
	public String config(@PathVariable("id") Long id, Model model) {
		model.addAttribute("openCredit", openCreditService.get(id));
		return "credit/changeCreditConfigInfo";
	}
	
	/**
	 * 配置-before-history
	 */
	@RequestMapping(value = "config/history/{id}", method = RequestMethod.GET)
	public String configHistory(@PathVariable("id") Long id, Model model) {
		model.addAttribute("openCredit", openCreditHistoryService.get(id));
		return "credit/changeCreditConfigInfo";
	}
	
	/**
	 * 配置-after
	 */
	@RequestMapping(value = "configForm/{action}/{id}", method = RequestMethod.GET)
	public String configForm(@PathVariable("action") String action, @PathVariable("id") Long id, Model model) {
		if (action.equals("create")) {
			model.addAttribute("openCredit", openCreditService.get(id));
		} else {
			model.addAttribute("openCredit", changeCreditService.get(id));
		}
		return "credit/changeCreditConfigForm";
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
			ChangeCredit changeCredit = changeCreditService.get(id);
//			changeCredit.setUserId((user.getLoginName()).toString());
			changeCredit.setState("已提交");
			changeCreditService.save(changeCredit);
			Map<String, Object> variables = new HashMap<String, Object>();
			activitiService.startWorkflow(changeCredit, "wf_changeCredit", variables, (user.getLoginName()).toString());
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
				ChangeCredit changeCredit = changeCreditService.get(id);
				changeCredit.setProcessInstanceId(null);
				changeCredit.setState("草稿");
				changeCreditService.save(changeCredit);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	
}
