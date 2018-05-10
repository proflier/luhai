package com.cbmie.lh.administration.web;

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

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.DateUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.administration.entity.SealApproval;
import com.cbmie.lh.administration.service.SealApprovalService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 印章管理
 */
@Controller
@RequestMapping("administration/sealApproval")
public class SealApprovalController extends BaseController {
	
	@Autowired
	private SealApprovalService sealApprovalService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ActivitiService activitiService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "administration/lhSealApprovalList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<SealApproval> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = sealApprovalService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		SealApproval sealApproval = new SealApproval();
		sealApproval.setCreateDate(new Date());
		sealApproval.setUpdateDate(new Date());
		model.addAttribute("sealApproval", sealApproval);
		model.addAttribute("action", "create");
		return "administration/lhSealApprovalForm";
	}

	/**
	 * 添加印章管理
	 * 
	 * @param sealApproval
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SealApproval sealApproval, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		if(("外借".equals(sealApproval.getSealModel())) && (sealApproval.getBackDate() != null)){
			sealApproval.setLendState("11050001");
		}else if(("外借".equals(sealApproval.getSealModel())) && (sealApproval.getBackDate() == null)){
			sealApproval.setLendState("11050002");
		}else{
			sealApproval.setLendState("11050003");
		}
		sealApproval.setSummary("[用印管理][" + sealApproval.getTypeName() +"]" + "["+ sealApproval.getSealModel()+ "]" + ("".equals(sealApproval.getCauseSubmission()) ? "" : ("[" + sealApproval.getCauseSubmission() + "]")));
		sealApproval.setCreaterNo(currentUser.getLoginName());
		sealApproval.setCreaterName(currentUser.getName());
		sealApproval.setCreateDate(new Date());
		sealApproval.setCreaterDept(currentUser.getOrganization().getOrgName());
		sealApprovalService.save(sealApproval);
		return setReturnData("success", "保存成功", sealApproval.getId());
	}

	/**
	 * 修改印章管理跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("sealApproval", sealApprovalService.get(id));
		model.addAttribute("action", "update");
		return "administration/lhSealApprovalForm";
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
	public String update(@Valid @ModelAttribute @RequestBody SealApproval sealApproval, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		if(("外借".equals(sealApproval.getSealModel())) && (sealApproval.getBackDate() != null)){
			sealApproval.setLendState("11050001");
		}else if(("外借".equals(sealApproval.getSealModel())) && (sealApproval.getBackDate() == null)){
			sealApproval.setLendState("11050002");
		}else{
			sealApproval.setLendState("11050003");
		}
		sealApproval.setSummary("[用印管理][" + sealApproval.getTypeName() +"]" + "["+ sealApproval.getSealModel()+ "]" + ("".equals(sealApproval.getCauseSubmission()) ? "" : ("[" + sealApproval.getCauseSubmission() + "]")));
		sealApproval.setUpdaterNo(currentUser.getLoginName());
		sealApproval.setUpdaterName(currentUser.getName());
		sealApproval.setUpdateDate(new Date());
		sealApprovalService.update(sealApproval);
		return setReturnData("success", "保存成功", sealApproval.getId());
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
		sealApprovalService.delete(id);
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
		model.addAttribute("sealApproval", sealApprovalService.get(id));
		model.addAttribute("action", "view");
		return "administration/lhSealApprovalDetail";
	}
	
	

	@ModelAttribute
	public void getSealApproval(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("sealApproval", sealApprovalService.get(id));
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
			SealApproval sealApproval = sealApprovalService.get(id);
			// purchaseAgreement.setUserId((user.getLoginName()).toString());
			sealApproval.setState(ActivitiConstant.ACTIVITI_COMMIT);
			sealApprovalService.save(sealApproval);
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("sealCode", sealApproval.getSealCode());
			Map<String, Object> result = new HashMap<String, Object>();
			result = activitiService.startWorkflow(sealApproval, "wf_sealApproval", variables,
					(user.getLoginName()).toString());
			// return "success";
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
				SealApproval sealApproval = sealApprovalService.get(id);
				sealApproval.setProcessInstanceId(null);
				sealApproval.setState(ActivitiConstant.ACTIVITI_DRAFT);
				sealApprovalService.save(sealApproval);
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
	 * 用印状态修改
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "outLend/{id}")
	@ResponseBody
	public String outLend(@PathVariable("id") Long id, Model model)
			throws JsonProcessingException {
		SealApproval sealApproval = sealApprovalService.get(id);
		sealApproval.setBackDate(DateUtils.getDate());
		sealApproval.setLendState("11050001");
		sealApprovalService.update(sealApproval);
		return "success";
	}
	

}
