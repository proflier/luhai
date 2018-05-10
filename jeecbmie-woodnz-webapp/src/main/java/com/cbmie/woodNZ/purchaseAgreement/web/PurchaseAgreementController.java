package com.cbmie.woodNZ.purchaseAgreement.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.purchaseAgreement.entity.PurchaseAgreement;
import com.cbmie.woodNZ.purchaseAgreement.service.PurchaseAgreementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 代购协议controller
 */
@Controller
@RequestMapping("purchase/purchaseAgreement")
public class PurchaseAgreementController extends BaseController{	
	
	@Autowired
	private PurchaseAgreementService purchaseAgreementService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "purchaseAgreement/purchaseAgreementList";
	}
	 
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<PurchaseAgreement> page = getPage(request);
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = purchaseAgreementService.search(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getEasyUIData(page);
	}
	
	/**
	 * 添加采购协议跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		PurchaseAgreement info = new PurchaseAgreement();
		User currentUser = UserUtil.getCurrentUser();
		info.setCreaterNo(currentUser.getLoginName());
		info.setCreaterName(currentUser.getName());
		info.setCreaterDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("purchaseAgreement", info);
		model.addAttribute("action", "create");
		return "purchaseAgreement/purchaseAgreementForm";
	}

	/**
	 * 添加采购协议
	 * 
	 * @param port
	 * @param model
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PurchaseAgreement purchaseAgreement, Model model) throws ParseException, JsonProcessingException {
		purchaseAgreement.setCreateDate(new Date());
		purchaseAgreement.setSummary("采购协议合同-[" + purchaseAgreement.getAgreementNo() + "]");
		purchaseAgreementService.save(purchaseAgreement);
//		if (Boolean.valueOf(request.getParameter("apply"))) {
//			apply(purchaseAgreement.getId());
//		}
		return setReturnData("success","保存成功",purchaseAgreement.getId());
	}

	/**
	 * 修改采购协议跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("purchaseAgreement", purchaseAgreementService.get(id));
		model.addAttribute("action", "update");
		return "purchaseAgreement/purchaseAgreementForm";
	}

	/**
	 * 修改采购协议
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PurchaseAgreement purchaseAgreement,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		purchaseAgreement.setUpdaterNo(currentUser.getLoginName());
		purchaseAgreement.setUpdaterName(currentUser.getName());
		purchaseAgreement.setUpdateDate(new Date());
		purchaseAgreement.setSummary("采购协议合同-[" + purchaseAgreement.getAgreementNo() + "]");
		purchaseAgreementService.update(purchaseAgreement);
//		if (Boolean.valueOf(request.getParameter("apply"))) {
//			apply(purchaseAgreement.getId());
//		}
		return setReturnData("success","保存成功",purchaseAgreement.getId());
	}

	/**
	 * 删除采购协议
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		purchaseAgreementService.delete(id);
		return "success";
	}
	
	/**
	 * 采购协议明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("purchaseAgreement", purchaseAgreementService.get(id));
		model.addAttribute("action", "detail");
		return "purchaseAgreement/purchaseAgreementDetail";
	}
	
	
	@ModelAttribute
	public void getPurchaseAgreement(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("purchaseAgreement", purchaseAgreementService.get(id));
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
			PurchaseAgreement purchaseAgreement = purchaseAgreementService.get(id);
//			purchaseAgreement.setUserId((user.getLoginName()).toString());
			purchaseAgreement.setState("已提交");
			purchaseAgreementService.save(purchaseAgreement);
			Map<String, Object> variables = new HashMap<String, Object>();
			Map<String,Object> result = new HashMap<String,Object>();
			result =  activitiService.startWorkflow(purchaseAgreement, "wf_purchaseAgreement", variables, (user.getLoginName()).toString());
//			return "success";
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(result);
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
				PurchaseAgreement purchaseAgreement = purchaseAgreementService.get(id);
				purchaseAgreement.setProcessInstanceId(null);
				purchaseAgreement.setState("草稿");
				purchaseAgreementService.save(purchaseAgreement);
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




