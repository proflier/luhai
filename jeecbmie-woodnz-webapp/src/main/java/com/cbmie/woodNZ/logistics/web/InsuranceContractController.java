package com.cbmie.woodNZ.logistics.web;

import java.text.ParseException;
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
import com.cbmie.genMac.financial.entity.PaymentConfirm;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.logistics.entity.InsuranceContract; 
import com.cbmie.woodNZ.logistics.service.InsuranceContractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 保险合同controller
 */
@Controller
@RequestMapping("logistics/insurance")
public class InsuranceContractController extends BaseController{
	
	
	@Autowired
	private InsuranceContractService insuranceContractService;
	
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
		return "logistics/insuranceContractList";
	}
	 
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<InsuranceContract> page = getPage(request);
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = insuranceContractService.search(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getEasyUIData(page);
	}
	
	/**
	 * 添加保险合同跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		InsuranceContract insuranctContract = new InsuranceContract();
		User currentUser = UserUtil.getCurrentUser();
		insuranctContract.setCreaterNo(currentUser.getLoginName());
		insuranctContract.setCreaterName(currentUser.getName());
		insuranctContract.setCreaterDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("insuranceContract", insuranctContract);
		model.addAttribute("action", "create");
		return "logistics/insuranceContractForm";
	}

	/**
	 * 添加保险合同
	 * 
	 * @param port
	 * @param model
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InsuranceContract insuranctContract, Model model) throws ParseException, JsonProcessingException {
		insuranctContract.setCreateDate(new Date());
		insuranctContract.setSummary("保险合同审批-[" + insuranctContract.getContractNo() + "]");
		insuranceContractService.save(insuranctContract);
//		if (Boolean.valueOf(request.getParameter("apply"))) {
//			apply(insuranctContract.getId());
//		}
		return setReturnData("success","保存成功",insuranctContract.getId());
	}

	/**
	 * 修改保险合同跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("insuranceContract", insuranceContractService.get(id));
		model.addAttribute("action", "update");
		return "logistics/insuranceContractForm";
	}

	/**
	 * 修改保险合同
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InsuranceContract insuranceContract,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		insuranceContract.setUpdaterNo(currentUser.getLoginName());
		insuranceContract.setUpdaterName(currentUser.getName());
		insuranceContract.setUpdateDate(new Date());
		insuranceContract.setSummary("保险合同审批-[" + insuranceContract.getContractNo() + "]");
		insuranceContractService.update(insuranceContract);
//		if (Boolean.valueOf(request.getParameter("apply"))) {
//			apply(insuranceContract.getId());
//		}
		return setReturnData("success","保存成功",insuranceContract.getId());
	}

	/**
	 * 删除保险合同
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		insuranceContractService.delete(id);
		return "success";
	}
	
	/**
	 * 保险合同明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("insuranceContract", insuranceContractService.get(id));
		model.addAttribute("action", "detail");
		return "logistics/insuranceContractDetail";
	}
	
	@ModelAttribute
	public void setInsuranceContract(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("insuranceContract", insuranceContractService.get(id));
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
			Map<String,Object> result = new HashMap<String,Object>();
			User user = UserUtil.getCurrentUser();
			InsuranceContract insuranctContract = insuranceContractService.get(id);
			insuranctContract.setState("已提交");
			insuranceContractService.save(insuranctContract);
			Map<String, Object> variables = new HashMap<String, Object>();
			result = activitiService.startWorkflow(insuranctContract, "wf_woodInsuranceContract", variables, (user.getLoginName()).toString());
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
				InsuranceContract insuranceContract = insuranceContractService.get(id);
				insuranceContract.setProcessInstanceId(null);
				insuranceContract.setState("草稿");
				insuranceContractService.save(insuranceContract);
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
