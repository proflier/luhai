package com.cbmie.woodNZ.credit.web;

import java.text.SimpleDateFormat;
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

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.credit.entity.PayApply;
import com.cbmie.woodNZ.credit.service.PayApplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 付款申请controller
 */
@Controller
@RequestMapping("credit/payApply")
public class PayApplyController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PayApplyService payApplyService;
	
	@Autowired
	private ActivitiService activitiService;
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "credit/payApplyList";
	}
	
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<PayApply> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = payApplyService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		PayApply payApply = new PayApply();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss-SS");
		payApply.setPayApplyNo("L/C"+format.format(date));
		model.addAttribute("payApply", payApply);
		model.addAttribute("action", "create");
		return "credit/payApplyForm";
	}

	/**
	 * 添加
	 * 
	 * @param payApply
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PayApply payApply, Model model) throws JsonProcessingException {
		if (payApplyService.findByNo(payApply) != null) {
			return setReturnData("fail","开证申请号重复！",payApply.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		payApply.setCreaterNo(currentUser.getLoginName());
		payApply.setCreaterName(currentUser.getName());
		payApply.setCreaterDept(currentUser.getOrganization().getOrgName());
		payApply.setCreateDate(new Date());
		payApply.setSummary("开证申请[" + payApply.getContractNo() + "]");
		payApplyService.save(payApply);
		return setReturnData("success","保存成功",payApply.getId());
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
		model.addAttribute("payApply", payApplyService.get(id));
		model.addAttribute("action", "update");
		return "credit/payApplyForm";
	}

	/**
	 * 修改
	 * 
	 * @param payApply
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PayApply payApply, Model model) throws JsonProcessingException {
		if (payApplyService.findByNo(payApply) != null) {
			return setReturnData("fail","开证申请号重复！",payApply.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		payApply.setUpdaterNo(currentUser.getLoginName());
		payApply.setUpdaterName(currentUser.getName());
		payApply.setCreaterDept(currentUser.getOrganization().getOrgName());
		payApply.setUpdateDate(new Date());
		payApply.setSummary("开证申请[" + payApply.getContractNo() + "]");
		payApplyService.update(payApply);
		return setReturnData("success","保存成功",payApply.getId());
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
		payApplyService.delete(id);
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
		model.addAttribute("payApply", payApplyService.get(id));
		return "credit/payApplyDetail";
	}
	
	@ModelAttribute
	public void getPayApply(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("payApply", payApplyService.get(id));
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
		Map<String,Object> result = new HashMap<String,Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			User user = UserUtil.getCurrentUser();
			PayApply payApply = payApplyService.get(id);
//			payApply.setUserId((user.getLoginName()).toString());
			payApply.setState("已提交");
			payApplyService.save(payApply);
			Map<String, Object> variables = new HashMap<String, Object>();
			result = activitiService.startWorkflow(payApply, "wf_payApply", variables, (user.getLoginName()).toString());
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
				PayApply payApply = payApplyService.get(id);
				payApply.setProcessInstanceId(null);
				payApply.setState("草稿");
				payApplyService.save(payApply);
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
	 * 采购合同号配置跳转
	 * @return
	 */
	@RequestMapping(value = "toContract", method = RequestMethod.GET)
	public String toAgencyConfig() {
		return "credit/payApplyContract";
	}
	
	
}
