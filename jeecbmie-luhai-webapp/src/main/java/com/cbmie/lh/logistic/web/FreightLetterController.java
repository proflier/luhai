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
import com.cbmie.lh.logistic.entity.FreightLetter;
import com.cbmie.lh.logistic.service.FreightLetterService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("logistic/freightLetter")
public class FreightLetterController extends BaseController {

	@Autowired
	private FreightLetterService letterService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private BaseInfoUtilService bius;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/freightLetterList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<FreightLetter> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = letterService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		FreightLetter freightLetter = new FreightLetter();
		User currentUser = UserUtil.getCurrentUser();
		freightLetter.setCreaterNo(currentUser.getLoginName());
		freightLetter.setCreaterName(currentUser.getName());
		freightLetter.setCreaterDept(currentUser.getOrganization().getOrgName());
		freightLetter.setCreateDate(new Date());
		model.addAttribute("freightLetter", freightLetter);
		model.addAttribute("action", "create");
		return "logistic/freightLetterForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid FreightLetter freightLetter, Model model) throws JsonProcessingException {
		try {
			if (!letterService.checkCodeUique(freightLetter)) {
				return setReturnData("fail","编号重复",freightLetter.getId());
			}
			freightLetter.setSummary("[运价函]["+ bius.getAffiBaseInfoByCode(freightLetter.getLineTo()) +"]" +  (freightLetter.getUnitPrice() == null ? "" : ("[结算单价" + freightLetter.getUnitPrice().longValue() + "元]")));
			letterService.save(freightLetter);
			return setReturnData("success","保存成功",freightLetter.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return setReturnData("fail","保存失败",freightLetter.getId());
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
		model.addAttribute("freightLetter", letterService.get(id));
		model.addAttribute("action", "update");
		return "logistic/freightLetterForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody FreightLetter freightLetter,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		freightLetter.setUpdaterNo(currentUser.getLoginName());
		freightLetter.setUpdaterName(currentUser.getName());
		freightLetter.setUpdateDate(new Date());
		freightLetter.setSummary("[运价函]["+ bius.getAffiBaseInfoByCode(freightLetter.getLineTo()) +"]" +  (freightLetter.getUnitPrice() == null ? "" : ("[结算单价" + freightLetter.getUnitPrice().longValue() + "元]")));
		letterService.update(freightLetter);
		return setReturnData("success","保存成功",freightLetter.getId());
	}
	
	@ModelAttribute
	public void getFreightLetter(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("freightLetter", letterService.get(id));
		}
	}
	
	/**
	 * 删除合同
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		letterService.delete(id);
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
		model.addAttribute("freightLetter", letterService.get(id));
		model.addAttribute("action", "view");
		return "logistic/freightLetterDetail";
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
		FreightLetter freightLetter = letterService.get(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(FreightLetter.class,freightLetter);
		if(processKey!=null){
			result = activitiService.startWorkflow(freightLetter, processKey, variables,
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
				FreightLetter freightLetter = letterService.get(id);
				freightLetter.setProcessInstanceId(null);
				freightLetter.setState(ActivitiConstant.ACTIVITI_DRAFT);
				letterService.save(freightLetter);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	@RequestMapping(value = "toContractList",method=RequestMethod.GET)
	public String toShipList(){
		return "logistic/freightRelContracts";
	}
}
