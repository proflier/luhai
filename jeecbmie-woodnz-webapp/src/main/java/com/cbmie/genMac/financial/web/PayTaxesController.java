package com.cbmie.genMac.financial.web;

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
import com.cbmie.genMac.financial.entity.PayTaxes;
import com.cbmie.genMac.financial.service.PayTaxesService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;


/**
 * 交税controller
 */
@Controller
@RequestMapping("financial/payTaxes")
public class PayTaxesController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PayTaxesService payTaxesService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/payTaxesList";
	}

	/**
	 * 获取交税json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<PayTaxes> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = payTaxesService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加交税跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("payTaxes", new PayTaxes());
		model.addAttribute("action", "create");
		return "financial/payTaxesForm";
	}

	/**
	 * 添加交税
	 * 
	 * @param payTaxes
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PayTaxes payTaxes, Model model) {
		if (payTaxesService.findByNo(payTaxes) != null) {
			return "税号重复！";
		}
		User currentUser = UserUtil.getCurrentUser();
		payTaxes.setCreaterNo(currentUser.getLoginName());
		payTaxes.setCreaterName(currentUser.getName());
		payTaxes.setCreateDate(new Date());
		payTaxes.setSummary("交税[" + payTaxes.getTaxNo() + "]");
		payTaxes.setCreaterDept(currentUser.getOrganization().getOrgName());
		payTaxesService.save(payTaxes);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(payTaxes.getId());
		}
		return "success";
	}

	/**
	 * 修改交税跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("payTaxes", payTaxesService.get(id));
		model.addAttribute("action", "update");
		return "financial/payTaxesForm";
	}

	/**
	 * 修改交税
	 * 
	 * @param payTaxes
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PayTaxes payTaxes, Model model) {
		if (payTaxesService.findByNo(payTaxes) != null) {
			return "税号重复！";
		}
		User currentUser = UserUtil.getCurrentUser();
		payTaxes.setUpdaterNo(currentUser.getLoginName());
		payTaxes.setUpdaterName(currentUser.getName());
		payTaxes.setUpdateDate(new Date());
		payTaxes.setSummary("交税[" + payTaxes.getTaxNo() + "]");
		payTaxesService.update(payTaxes);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(payTaxes.getId());
		}
		return "success";
	}
	
	/**
	 * 删除交税
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		payTaxesService.delete(id);
		return "success";
	}
	
	/**
	 * 查看交税明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("payTaxes", payTaxesService.get(id));
		return "financial/payTaxesDetail";
	}
	
	@ModelAttribute
	public void getPayTaxes(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("payTaxes", payTaxesService.get(id));
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
			PayTaxes payTaxes = payTaxesService.get(id);
//			payTaxes.setUserId((user.getLoginName()).toString());
			payTaxes.setState("已提交");
			payTaxesService.save(payTaxes);
			Map<String, Object> variables = new HashMap<String, Object>();
			activitiService.startWorkflow(payTaxes, "wf_payTaxes", variables, (user.getLoginName()).toString());
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
				PayTaxes payTaxes = payTaxesService.get(id);
				payTaxes.setProcessInstanceId(null);
				payTaxes.setState("草稿");
				payTaxesService.save(payTaxes);
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
