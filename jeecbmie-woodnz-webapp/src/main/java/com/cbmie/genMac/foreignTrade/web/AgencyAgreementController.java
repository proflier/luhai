package com.cbmie.genMac.foreignTrade.web;

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
import com.cbmie.genMac.foreignTrade.entity.AgencyAgreement;
import com.cbmie.genMac.foreignTrade.service.AgencyAgreementService;
import com.cbmie.genMac.foreignTrade.service.AgreementGoodsService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 代理协议controller
 */
@Controller
@RequestMapping("foreignTrade/agencyAgreement")
public class AgencyAgreementController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AgencyAgreementService agencyAgreementService;
	
	@Autowired
	private AgreementGoodsService agreementGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "foreignTrade/agencyAgreementList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> agencyAgreementList(HttpServletRequest request) {
		Page<AgencyAgreement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = agencyAgreementService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("agencyAgreement", new AgencyAgreement());
		model.addAttribute("action", "create");
		return "foreignTrade/agencyAgreementForm";
	}

	/**
	 * 添加
	 * 
	 * @param agencyAgreement
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid AgencyAgreement agencyAgreement, Model model, @RequestParam("agreementGoodsJson") String agreementGoodsJson) {
		if (agencyAgreementService.findByNo(agencyAgreement) != null) {
			return "代理协议号重复！";
		}
		User currentUser = UserUtil.getCurrentUser();
		agencyAgreement.setCreaterNo(currentUser.getLoginName());
		agencyAgreement.setCreaterName(currentUser.getName());
		agencyAgreement.setCreaterDept(currentUser.getOrganization().getOrgName());
		agencyAgreement.setCreateDate(new Date());
		agencyAgreement.setSummary("代理协议[" + agencyAgreement.getAgencyAgreementNo() + "]");
		agencyAgreementService.save(agencyAgreement);
		agreementGoodsService.save(agencyAgreement, agreementGoodsJson, currentUser);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(agencyAgreement.getId());
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
		model.addAttribute("agencyAgreement", agencyAgreementService.get(id));
		model.addAttribute("action", "update");
		return "foreignTrade/agencyAgreementForm";
	}

	/**
	 * 修改
	 * 
	 * @param agencyAgreement
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody AgencyAgreement agencyAgreement, Model model,
			@RequestParam("agreementGoodsJson") String agreementGoodsJson) {
		if (agencyAgreementService.findByNo(agencyAgreement) != null) {
			return "代理协议号重复！";
		}
		User currentUser = UserUtil.getCurrentUser();
		agencyAgreement.setUpdaterNo(currentUser.getLoginName());
		agencyAgreement.setUpdaterName(currentUser.getName());
		agencyAgreement.setCreaterDept(currentUser.getOrganization().getOrgName());
		agencyAgreement.setUpdateDate(new Date());
		agencyAgreement.setSummary("代理协议[" + agencyAgreement.getAgencyAgreementNo() + "]");
		agreementGoodsService.save(agencyAgreement, agreementGoodsJson, currentUser);
		agencyAgreementService.update(agencyAgreement);
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(agencyAgreement.getId());
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
		agencyAgreementService.delete(id);
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
		model.addAttribute("agencyAgreement", agencyAgreementService.get(id));
		return "foreignTrade/agencyAgreementDetail";
	}
	
	@ModelAttribute
	public void getAgencyAgreement(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("agencyAgreement", agencyAgreementService.get(id));
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
			AgencyAgreement agencyAgreement = agencyAgreementService.get(id);
//			agencyAgreement.setUserId((user.getLoginName()).toString());
			agencyAgreement.setState("已提交");
			agencyAgreementService.save(agencyAgreement);
			Map<String, Object> variables = new HashMap<String, Object>();
			activitiService.startWorkflow(agencyAgreement, "wf_protocol", variables, (user.getLoginName()).toString());
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
				AgencyAgreement agencyAgreement = agencyAgreementService.get(id);
				agencyAgreement.setProcessInstanceId(null);
				agencyAgreement.setState("草稿");
				agencyAgreementService.save(agencyAgreement);
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
