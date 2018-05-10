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
import com.cbmie.genMac.financial.entity.Acceptance;
import com.cbmie.genMac.financial.service.AcceptanceService;
import com.cbmie.genMac.financial.service.SerialService;
import com.cbmie.genMac.logistics.entity.InvoiceReg;
import com.cbmie.genMac.logistics.service.InvoiceRegService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;


/**
 * 承兑controller
 */
@Controller
@RequestMapping("financial/acceptance")
public class AcceptanceController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AcceptanceService acceptanceService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private SerialService serialService;
	
	@Autowired
	private InvoiceRegService invoiceRegService;
	
	@Autowired
	private  HttpServletRequest request;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/acceptanceList";
	}

	/**
	 * 获取承兑json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<Acceptance> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = acceptanceService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加承兑跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("acceptance", new Acceptance());
		model.addAttribute("action", "create");
		return "financial/acceptanceForm";
	}

	/**
	 * 添加承兑
	 * 
	 * @param acceptance
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Acceptance acceptance, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		acceptance.setCreaterNo(currentUser.getLoginName());
		acceptance.setCreaterName(currentUser.getName());
		acceptance.setCreateDate(new Date());
		acceptance.setSummary("承兑[" + acceptance.getCreditNo() + "]");
		acceptance.setCreaterDept(currentUser.getOrganization().getOrgName());
		acceptanceService.save(acceptance);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(acceptance.getId());
		}
		return "success";
	}

	/**
	 * 修改承兑跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("acceptance", acceptanceService.get(id));
		model.addAttribute("action", "update");
		return "financial/acceptanceForm";
	}

	/**
	 * 修改承兑
	 * 
	 * @param acceptance
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Acceptance acceptance, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		acceptance.setUpdaterNo(currentUser.getLoginName());
		acceptance.setUpdaterName(currentUser.getName());
		acceptance.setUpdateDate(new Date());
		acceptance.setSummary("承兑[" + acceptance.getCreditNo() + "]");
		acceptanceService.update(acceptance);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(acceptance.getId());
		}
		return "success";
	}
	
	/**
	 * 删除承兑
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		acceptanceService.delete(id);
		return "success";
	}
	
	/**
	 * 查看承兑明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("acceptance", acceptanceService.get(id));
		return "financial/acceptanceDetail";
	}
	
	@ModelAttribute
	public void getAcceptance(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("acceptance", acceptanceService.get(id));
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
			Acceptance acceptance = acceptanceService.get(id);
//			acceptance.setUserId((user.getLoginName()).toString());
			acceptance.setState("已提交");
			acceptanceService.save(acceptance);
			Map<String, Object> variables = new HashMap<String, Object>();
			
			InvoiceReg invoiceReg = new InvoiceReg();
			invoiceReg.setInvoiceNo(acceptance.getInvoiceNo());
			//Double sumAcceptance = 
			serialService.sumAcceptance(invoiceRegService.findByNo(invoiceReg).getContractNo());//水单总金额
			
			activitiService.startWorkflow(acceptance, "wf_acceptance", variables, (user.getLoginName()).toString());
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
				Acceptance acceptance = acceptanceService.get(id);
				acceptance.setProcessInstanceId(null);
				acceptance.setState("草稿");
				acceptanceService.save(acceptance);
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
