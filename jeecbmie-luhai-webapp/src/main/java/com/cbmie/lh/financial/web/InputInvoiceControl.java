package com.cbmie.lh.financial.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.ActivitiException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.financial.entity.InputInvoice;
import com.cbmie.lh.financial.entity.InputInvoiceSub;
import com.cbmie.lh.financial.service.InputInvoiceService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 进项发票controller
 */
@Controller
@RequestMapping("financial/inputInvoice")
public class InputInvoiceControl extends BaseController  {

	@Autowired
	private InputInvoiceService inputInvoiceService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BaseInfoUtilService bius;
	@Autowired
	private ActivitiService activitiService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/inputInvoiceList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<InputInvoice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//设置相关人员
		inputInvoiceService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		//获取当前可见客户
		Session session =SecurityUtils.getSubject().getSession();
		String customerCode = (String) session.getAttribute("customerCode");
		//根据某一字段设置权限
		inputInvoiceService.setCurrentPermission(filters, "INQ_userName", customerCode);
		page = inputInvoiceService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		InputInvoice inputInvoice = new InputInvoice();
		inputInvoice.setCreateDate(new Date());
		inputInvoice.setUpdateDate(new Date());
		model.addAttribute("inputInvoice", inputInvoice);
		model.addAttribute("action", "create");
		return "financial/inputInvoiceForm";
	}

	/**
	 * 添加进项发票管理
	 * 
	 * @param sealApproval
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InputInvoice inputInvoice, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		inputInvoice.setSummary("[进项发票][" + inputInvoice.getInvoiceTypeModel() +"]" + "["+ bius.getAffiBaseInfoByCode(inputInvoice.getIssuingOffice()) +"]" );
		inputInvoice.setCreaterNo(currentUser.getLoginName());
		inputInvoice.setCreaterName(currentUser.getName());
		inputInvoice.setCreateDate(new Date());
		inputInvoice.setCreaterDept(currentUser.getOrganization().getOrgName());
		inputInvoiceService.save(inputInvoice);
		return setReturnData("success", "保存成功", inputInvoice.getId());
	}

	/**
	 * 修改进项发票跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inputInvoice", inputInvoiceService.get(id));
		model.addAttribute("action", "update");
		return "financial/inputInvoiceForm";
	}

	/**
	 * 修改印章管理
	 * 
	 * @param sealApproval
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InputInvoice inputInvoice, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		inputInvoice.setSummary("[进项发票][" + inputInvoice.getInvoiceTypeModel() +"]" + "["+ bius.getAffiBaseInfoByCode(inputInvoice.getIssuingOffice()) +"]" );
		inputInvoice.setUpdaterNo(currentUser.getLoginName());
		inputInvoice.setUpdaterName(currentUser.getName());
		inputInvoice.setUpdateDate(new Date());
		inputInvoiceService.update(inputInvoice);
		return setReturnData("success", "保存成功", inputInvoice.getId());
	}

	/**
	 * 删除印章管理
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		inputInvoiceService.delete(id);
		return setReturnData("success", "保存成功", null);
	}

	/**
	 * 查看印章管理明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inputInvoice", inputInvoiceService.get(id));
		model.addAttribute("action", "view");
		return "financial/inputInvoiceDetail";
	}
	
	

	@ModelAttribute
	public void getInputInvoice(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inputInvoice", inputInvoiceService.get(id));
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
			InputInvoice inputInvoice = inputInvoiceService.get(id);
			// purchaseAgreement.setUserId((user.getLoginName()).toString());
			inputInvoice.setState(ActivitiConstant.ACTIVITI_COMMIT);
			List<InputInvoiceSub> list = inputInvoice.getSettleSubs();
			Double allPricesAdd = 0.0; 
			for (InputInvoiceSub lists : list) {
				if(lists.getAllPrices()!=null){
					allPricesAdd += lists.getAllPrices();
				}
			}
			inputInvoice.setSummary("[进项发票][" + inputInvoice.getInvoiceTypeModel() +"]" + "["+ bius.getAffiBaseInfoByCode(inputInvoice.getIssuingOffice()) +"]"+ "[" + allPricesAdd.longValue() +"]" );
			inputInvoiceService.update(inputInvoice);
			Map<String, Object> variables = new HashMap<String, Object>();
			Map<String, Object> result = new HashMap<String, Object>();
			// return "success";
			//获取流程标识
			String processKey = activitiService.getCurrentProcessKey(InputInvoice.class,inputInvoice);
			if(processKey!=null){
				result = activitiService.startWorkflow(inputInvoice, processKey, variables,
						(user.getLoginName()).toString());
			}else{
				return "流程未部署、不存在或存在多个，请联系管理员！";
			}
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
	 * 
	 * @return
	 */
	@RequestMapping(value = "callBack/{id}/{processInstanceId}")
	@ResponseBody
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId,
			HttpSession session) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId)) {
				InputInvoice inputInvoice = inputInvoiceService.get(id);
				inputInvoice.setProcessInstanceId(null);
				inputInvoice.setState(ActivitiConstant.ACTIVITI_DRAFT);
				inputInvoiceService.save(inputInvoice);
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
