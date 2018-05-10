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
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.financial.entity.PaymentConfirm;
import com.cbmie.genMac.financial.service.PaymentConfirmService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("financial/paymentConfirm")
public class PaymentConfirmController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PaymentConfirmService confirmService;
	@Autowired
	private  HttpServletRequest request;
	@Autowired
	private ActivitiService activitiService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/paymentConfirmList";
	}
	
	/**
	 * 获取付款确认单json
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<PaymentConfirm> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = confirmService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		PaymentConfirm paymentConfirm = new PaymentConfirm();
		User currentUser = UserUtil.getCurrentUser();
		paymentConfirm.setCreaterNo(currentUser.getLoginName());
		paymentConfirm.setCreaterName(currentUser.getName());
		paymentConfirm.setCreaterDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("paymentConfirm", paymentConfirm);
		model.addAttribute("action", "create");
		return "financial/paymentConfirmForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param woodCghtJk
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PaymentConfirm paymentConfirm, Model model) throws JsonProcessingException {
		try {
			if (confirmService.findByConfirmNo(paymentConfirm) != null) {
				return setReturnData("fail","确认单号重复",paymentConfirm.getId());
			}
			User currentUser = UserUtil.getCurrentUser();
			paymentConfirm.setCreaterNo(currentUser.getLoginName());
			paymentConfirm.setCreaterName(currentUser.getName());
			paymentConfirm.setCreateDate(new Date());
			paymentConfirm.setSummary("付款确认单[" + paymentConfirm.getConfirmNo() + "]");
			paymentConfirm.setCreaterDept(currentUser.getOrganization().getOrgName());
			confirmService.save(paymentConfirm);
			return setReturnData("success","保存成功",paymentConfirm.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",paymentConfirm.getId());
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
		User user = UserUtil.getCurrentUser();
		PaymentConfirm paymentConfirm = confirmService.get(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("payMoneyXiao", paymentConfirm.getPayMoneyXiao()==null?0:paymentConfirm.getPayMoneyXiao());
		variables.put("goodsClasses", paymentConfirm.getGoodsClasses());
		result = activitiService.startWorkflow(paymentConfirm, "wf_paymentConfirm", variables, (user.getLoginName()).toString());
		ObjectMapper objectMapper = new ObjectMapper(); 
		try {
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
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
		model.addAttribute("paymentConfirm", confirmService.get(id));
		model.addAttribute("action", "update");
		return "financial/paymentConfirmForm";
	}
	
	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PaymentConfirm paymentConfirm, Model model) throws JsonProcessingException {
			if (confirmService.findByConfirmNo(paymentConfirm) != null) {
				return setReturnData("fail","合同号重复",paymentConfirm.getId());
			}
			User currentUser = UserUtil.getCurrentUser();
			paymentConfirm.setUpdaterNo(currentUser.getLoginName());
			paymentConfirm.setUpdaterName(currentUser.getName());
			paymentConfirm.setUpdateDate(new Date());
			paymentConfirm.setSummary("付款确认单[" + paymentConfirm.getConfirmNo() + "]");
			confirmService.update(paymentConfirm);
			return setReturnData("success","保存成功",paymentConfirm.getId());
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws  Exception {
		confirmService.delete(id);
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
		model.addAttribute("paymentConfirm", confirmService.get(id));
		return "financial/paymentConfirmDetail";
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
				PaymentConfirm paymentConfirm = confirmService.get(id);
				paymentConfirm.setProcessInstanceId(null);
				paymentConfirm.setState("草稿");
				confirmService.save(paymentConfirm);
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
		return "financial/paymentConfirmContract";
	}
	
	@ModelAttribute
	public void getPaymentConfirm(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("paymentConfirm", confirmService.get(id));
		}
	}

}
