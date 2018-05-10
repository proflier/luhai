package com.cbmie.lh.logistic.web;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.logistic.entity.Contract;
import com.cbmie.lh.logistic.service.ContractService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 合同controller
 */
@Controller
@RequestMapping("logistic/contract")
public class ContractController extends BaseController {

	@Autowired
	private ContractService contractService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ActivitiService activitiService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/contractList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<Contract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = contractService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		Contract contract = new Contract();
		User currentUser = UserUtil.getCurrentUser();
		contract.setCreaterNo(currentUser.getLoginName());
		contract.setCreaterName(currentUser.getName());
		contract.setCreaterDept(currentUser.getOrganization().getOrgName());
		contract.setCreateDate(new Date());
		contract.setApplyDate(new Date());
		model.addAttribute("contract", contract);
		model.addAttribute("action", "create");
		return "logistic/contractForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Contract contract, Model model) throws JsonProcessingException {
		try {
			if (!contractService.checkCodeUique(contract)) {
				return setReturnData("fail","编码重复",contract.getId());
			}
			contract.setSummary("物流合同审批-[" + contract.getContractNo() + "]");
			contractService.save(contract);
			return setReturnData("success","保存成功",contract.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return setReturnData("fail","保存失败",contract.getId());
		}
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("contract", contractService.get(id));
		model.addAttribute("action", "update");
		return "logistic/contractForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Contract contract,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		contract.setUpdaterNo(currentUser.getLoginName());
		contract.setUpdaterName(currentUser.getName());
		contract.setUpdateDate(new Date());
		contract.setSummary("物流合同审批-[" + contract.getContractNo() + "]");
		contractService.update(contract);
		return setReturnData("success","保存成功",contract.getId());
	}
	
	@ModelAttribute
	public void getContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("contract", contractService.get(id));
		}
	}
	
	/**
	 * 删除保险合同
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		contractService.delete(id);
		return setReturnData("success","删除成功",id);
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
		model.addAttribute("contract", contractService.get(id));
		model.addAttribute("action", "view");
		return "logistic/contractDetail";
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
		Map<String,Object> result = new HashMap<String,Object>();
		User user = UserUtil.getCurrentUser();
		Contract contract = contractService.get(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("tradeCategory", contract.getTradeCategory());
		result = activitiService.startWorkflow(contract, "wf_logisticContract", variables, (user.getLoginName()).toString());
		ObjectMapper objectMapper = new ObjectMapper(); 
		try {
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
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
				Contract contract = contractService.get(id);
				contract.setProcessInstanceId(null);
				contract.setState(ActivitiConstant.ACTIVITI_DRAFT);
				contractService.save(contract);
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
