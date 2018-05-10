package com.cbmie.lh.logistic.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.logistic.entity.ProxyAgreement;
import com.cbmie.lh.logistic.service.ProxyAgreementService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 报关代理协议controller
 */
@Controller
@RequestMapping("logistic/proxyAgreement")
public class ProxyAgreementController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProxyAgreementService proxyAgreementService;

	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	 
	@Autowired
	private BaseInfoUtilService baseInfoUtilService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/proxyAgreementList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getListNoPermission(HttpServletRequest request) {
		Page<ProxyAgreement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = proxyAgreementService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<ProxyAgreement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = proxyAgreementService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		ProxyAgreement proxyAgreement = new ProxyAgreement();
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001028");
		String innerContractNo = sequenceMap.get("returnStr");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		proxyAgreement.setInnerContractNo(innerContractNo);
		proxyAgreement.setCreateDate(new Date());
		proxyAgreement.setUpdateDate(new Date());
		model.addAttribute("proxyAgreement", proxyAgreement);
		model.addAttribute("action", "create");
		return "logistic/proxyAgreementForm";
	}

	/**
	 * 添加
	 * 
	 * @param proxyAgreement
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ProxyAgreement proxyAgreement, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (proxyAgreementService.findByContractNo(proxyAgreement) != null) {
			return setReturnData("fail","合同编号重复",proxyAgreement.getId());
		}
		if (proxyAgreementService.findByNo(proxyAgreement) != null) {
			String innerContractNo = sequenceCodeService.getNextCode("001028").get("returnStr");
			proxyAgreement.setInnerContractNo(innerContractNo);
			currnetSequence = innerContractNo;
		}
		User currentUser = UserUtil.getCurrentUser();
		proxyAgreement.setCreaterNo(currentUser.getLoginName());
		proxyAgreement.setCreaterName(currentUser.getName());
		proxyAgreement.setCreaterDept(currentUser.getOrganization().getOrgName());
		proxyAgreement.setCreateDate(new Date());
		//调用获取下个sequence方法，将当前sequence保存
		if(proxyAgreement.getId()==null){
			sequenceCodeService.getNextSequence("001028");
		}
		proxyAgreementService.save(proxyAgreement);
		return setReturnData("success", currnetSequence!=null?"报关代理协议内部合同号，已自动生成不重复编码并保存":"保存成功", proxyAgreement.getId(),currnetSequence);
	}

	/**
	 * 修改跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		model.addAttribute("proxyAgreement", proxyAgreementService.get(id));
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001028");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		model.addAttribute("action", "update");
		return "logistic/proxyAgreementForm";
	}

	/**
	 * 修改
	 * 
	 * @param proxyAgreement
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ProxyAgreement proxyAgreement, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		proxyAgreement.setUpdaterNo(currentUser.getLoginName());
		proxyAgreement.setUpdaterName(currentUser.getName());
		proxyAgreement.setCreaterDept(currentUser.getOrganization().getOrgName());
		proxyAgreement.setUpdateDate(new Date());
		proxyAgreementService.update(proxyAgreement);
		return setReturnData("success", "保存成功", proxyAgreement.getId());
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
		proxyAgreementService.delete(id);
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
		model.addAttribute("proxyAgreement", proxyAgreementService.get(id));
		model.addAttribute("action", "view");
		return "logistic/proxyAgreementDetail";
	}

	@ModelAttribute
	public void getProxyAgreement(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("proxyAgreement", proxyAgreementService.get(id));
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
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			User user = UserUtil.getCurrentUser();
			ProxyAgreement proxyAgreement = proxyAgreementService.get(id);
			proxyAgreement.setUserId((user.getLoginName()).toString());
			proxyAgreement.setState(ActivitiConstant.ACTIVITI_COMMIT);
			String summary = "[报关代理协议][" + baseInfoUtilService.getAffiBaseInfoByCode(proxyAgreement.getAgreementUnit())+"][货代费"+proxyAgreement.getFreightFee()+DictUtils.getDictLabelByCode(proxyAgreement.getCurrency(), "currency", "")+"]";
			proxyAgreement.setSummary(summary);
			proxyAgreementService.save(proxyAgreement);
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("deptId", proxyAgreement.getDeptId());
			//获取流程标识
			String processKey = activitiService.getCurrentProcessKey(ProxyAgreement.class,proxyAgreement);
			if(processKey!=null){
				result = activitiService.startWorkflow(proxyAgreement, processKey, variables,
						(user.getLoginName()).toString());
			}else{
				return "流程未部署、不存在或存在多个，请联系管理员！";
			}
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
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
				ProxyAgreement proxyAgreement = proxyAgreementService.get(id);
				proxyAgreement.setProcessInstanceId(null);
				proxyAgreement.setState(ActivitiConstant.ACTIVITI_DRAFT);
				proxyAgreementService.save(proxyAgreement);
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
