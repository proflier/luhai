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
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.logistic.entity.TransportSettlement;
import com.cbmie.lh.logistic.service.TransportSettlemenService;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping(value="logistic/transport")
public class TransportSettlementController extends BaseController {
	@Autowired
	private TransportSettlemenService transportService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private BaseInfoUtilService bius;
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BusinessPerssionService businessPerssionService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/transportList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<TransportSettlement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		transportService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = transportService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		TransportSettlement transportSettlement = new TransportSettlement();
		User currentUser = UserUtil.getCurrentUser();
		transportSettlement.setCreaterNo(currentUser.getLoginName());
		transportSettlement.setCreaterName(currentUser.getName());
		transportSettlement.setCreaterDept(currentUser.getOrganization().getOrgName());
		transportSettlement.setCreateDate(new Date());
		model.addAttribute("transportSettlement", transportSettlement);
		model.addAttribute("action", "create");
		return "logistic/transportForm";
	}
	
	/**
	 * 添加
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid TransportSettlement transportSettlement) throws JsonProcessingException {
			transportSettlement.setSummary("[汽/铁运结算][" + bius.getAffiBaseInfoByCode(transportSettlement.getTransporter()) +"]" + (transportSettlement.getShouldPay() == null ? "" : ("[应付金额" + transportSettlement.getShouldPay().longValue()+DictUtils.getDictLabelsByCodes(transportSettlement.getMoneyUnit(), "moneyUnit", "") + "]")));
			//设置相关人员与默认值创建人
			String themeMemberIds = request.getParameter("themeMemberIds");
			transportSettlement.setRelLoginNames(themeMemberIds);
			transportService.save(transportSettlement);
			return setReturnData("success","保存成功",transportSettlement.getId());
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
		TransportSettlement transportSettlement = transportService.get(id);
		model.addAttribute("transportSettlement", transportSettlement);
		model.addAttribute("action", "update");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(transportSettlement.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/transportForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody TransportSettlement transportSettlement) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		transportSettlement.setUpdaterNo(currentUser.getLoginName());
		transportSettlement.setUpdaterName(currentUser.getName());
		transportSettlement.setUpdateDate(new Date());
		transportSettlement.setSummary("[汽/铁运结算][" + bius.getAffiBaseInfoByCode(transportSettlement.getTransporter()) +"]" + (transportSettlement.getShouldPay() == null ? "" : ("[应付金额" + transportSettlement.getShouldPay().longValue()+DictUtils.getDictLabelsByCodes(transportSettlement.getMoneyUnit(), "moneyUnit", "") + "]")));
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		transportSettlement.setRelLoginNames(themeMemberIds);
		transportService.update(transportSettlement);
		return setReturnData("success","保存成功",transportSettlement.getId());
	}
	
	@ModelAttribute
	public void getTransportSettlement(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("transportSettlement", transportService.get(id));
		}
	}
	
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		TransportSettlement transportSettlement = transportService.get(id);
		model.addAttribute("transportSettlement", transportSettlement);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(transportSettlement.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/transportDetail";
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		transportService.delete(id);
		return setReturnData("success","删除成功",id);
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
		TransportSettlement transportSettlement = transportService.get(id);
		transportSettlement.setSummary("[汽/铁运结算][" + bius.getAffiBaseInfoByCode(transportSettlement.getTransporter()) +"]" + (transportSettlement.getShouldPay() == null ? "" : ("[应付金额" + transportSettlement.getShouldPay().longValue()+DictUtils.getDictLabelsByCodes(transportSettlement.getMoneyUnit(), "moneyUnit", "") + "]")));
		transportService.update(transportSettlement);
		Map<String, Object> variables = new HashMap<String, Object>();
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(TransportSettlement.class,transportSettlement);
		if(processKey!=null){
			result = activitiService.startWorkflow(transportSettlement, processKey, variables,
					(user.getLoginName()).toString());
		}else{
			return "流程未部署、不存在或存在多个，请联系管理员！";
		}
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
				TransportSettlement transportSettlement = transportService.get(id);
				transportSettlement.setProcessInstanceId(null);
				transportSettlement.setState(ActivitiConstant.ACTIVITI_DRAFT);
				transportService.update(transportSettlement);
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
