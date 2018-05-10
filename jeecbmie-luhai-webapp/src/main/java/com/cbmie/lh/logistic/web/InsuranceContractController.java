package com.cbmie.lh.logistic.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.ActivitiException;
import org.apache.commons.beanutils.BeanUtils;
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

import com.cbmie.accessory.entity.Accessory;
import com.cbmie.accessory.service.AccessoryService;
import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.JavaToPdfHtml;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.logistic.entity.InsuranceContract;
import com.cbmie.lh.logistic.service.InsuranceContractService;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 保险合同controller
 */
@Controller
@RequestMapping("logistic/insurance")
public class InsuranceContractController extends BaseController{
	
	
	@Autowired
	private InsuranceContractService insuranceContractService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private BaseInfoUtilService bius;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BusinessPerssionService businessPerssionService;
	

	@Autowired
	private AccessoryService accessoryService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/insuranceContractList";
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
			insuranceContractService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
			page = insuranceContractService.search(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getEasyUIData(page);
	}
	
	@RequestMapping(value="jsonNoPermission",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> jsonNoPermission(HttpServletRequest request) {
		Page<InsuranceContract> page = getPage(request);
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = insuranceContractService.searchNoPermission(page, filters);
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
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		InsuranceContract insuranctContract = new InsuranceContract();
		User currentUser = UserUtil.getCurrentUser();
		insuranctContract.setCreaterNo(currentUser.getLoginName());
		insuranctContract.setCreaterName(currentUser.getName());
		insuranctContract.setCreaterDept(currentUser.getOrganization().getOrgName());
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001005");
		String innerContractNo = sequenceMap.get("returnStr");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		insuranctContract.setInnerContractNo(innerContractNo+"(IT)");
		model.addAttribute("insuranceContract", insuranctContract);
		model.addAttribute("action", "create");
		return "logistic/insuranceContractForm";
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
		String  currentSequence = null;
		while(!insuranceContractService.checkInnerContractNoUnique(insuranctContract)){
			currentSequence = sequenceCodeService.getNextSequence("001005").toString();
			insuranctContract.setInnerContractNo(currentSequence);
		}
		insuranctContract.setCreateDate(new Date());
		insuranctContract.setSummary("[保险合同审批][" + bius.getAffiBaseInfoByCode(insuranctContract.getInsuranceCompany()) +"]" +  (insuranctContract.getAmount() == null ? "" : ("[保费" + insuranctContract.getAmount().longValue()+DictUtils.getDictLabelsByCodes(insuranctContract.getMoneyCurrency(), "currency", "") + "]")));
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		insuranctContract.setRelLoginNames(themeMemberIds);
		insuranceContractService.save(insuranctContract);
		//调用获取下个sequence方法，将当前sequence保存
		if(currentSequence==null){
			sequenceCodeService.getNextSequence("001005");
		}
		return setReturnData("success",currentSequence!=null?"内部合同号重复，已自动生成不重复编码并保存":"保存成功",insuranctContract.getId(),currentSequence);
	}

	/**
	 * 修改保险合同跳转
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
		InsuranceContract insuranctContract = insuranceContractService.get(id);
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001005");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		model.addAttribute("insuranceContract", insuranctContract);
		model.addAttribute("action", "update");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(insuranctContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/insuranceContractForm";
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
		insuranceContract.setSummary("[保险合同审批][" + bius.getAffiBaseInfoByCode(insuranceContract.getInsuranceCompany()) +"]" +  (insuranceContract.getAmount() == null ? "" : ("[保费" + insuranceContract.getAmount().longValue()+DictUtils.getDictLabelsByCodes(insuranceContract.getMoneyCurrency(), "currency", "") + "]")));
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		insuranceContract.setRelLoginNames(themeMemberIds);
		insuranceContractService.update(insuranceContract);
		return setReturnData("success","保存成功",insuranceContract.getId());
	}

	/**
	 * 变更保险合同
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "change/{id}", method = RequestMethod.GET)
	public String changeForm(@PathVariable("id") Long id, Model model) throws IllegalAccessException, InvocationTargetException {
		InsuranceContract insuranceContract_source = insuranceContractService.getNoLoad(id);
		InsuranceContract insuranceContract = copyProperties(insuranceContract_source);
		model.addAttribute("insuranceContract", insuranceContract);
		model.addAttribute("action", "update");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(insuranceContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/insuranceContractForm";
	}
	
	private InsuranceContract copyProperties(InsuranceContract insuranceContract_source) throws IllegalAccessException, InvocationTargetException {
		InsuranceContract insuranceContract_dest = new InsuranceContract();
		BeanUtils.copyProperties(insuranceContract_dest, insuranceContract_source);
		insuranceContract_dest.setId(null);
		insuranceContract_dest.setSourceId(insuranceContract_source.getSourceId()==null?insuranceContract_source.getId():insuranceContract_source.getSourceId());
		insuranceContract_dest.setPid(insuranceContract_source.getId());
		insuranceContract_dest.setChangeState("2");
		insuranceContract_dest.setProcessInstanceId(null);
		insuranceContract_dest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		insuranceContract_dest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		insuranceContract_dest.setCreaterNo(currentUser.getLoginName());
		insuranceContract_dest.setCreaterName(currentUser.getName());
		insuranceContract_dest.setCreaterDept(currentUser.getOrganization().getOrgName());
		insuranceContract_dest.setCreateDate(new Date());
		insuranceContract_dest.setUpdateDate(new Date());
		insuranceContract_source.setChangeState("0");
		insuranceContractService.update(insuranceContract_source);
		insuranceContractService.save(insuranceContract_dest);
		//更新附件信息
		 List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(insuranceContract_source.getId().toString(),"com_cbmie_lh_logistic_entity_InsuranceContract");
		 accessoryService.copyAttach(accessoryList,insuranceContract_dest.getId()+"",null);
		return insuranceContract_dest;
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
		InsuranceContract insuranctContract = insuranceContractService.get(id);
		if(insuranctContract.getPid()!=null){
			InsuranceContract insuranctContractOld = insuranceContractService.get(insuranctContract.getPid());
			insuranctContractOld.setChangeState("1");
			insuranceContractService.update(insuranctContractOld);
		}
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
		InsuranceContract insuranctContract = insuranceContractService.get(id);
		model.addAttribute("insuranceContract", insuranctContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(insuranctContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/insuranceContractDetail";
	}
	
	/**
	 * 获取备份信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getInsuranceContractBak/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<InsuranceContract> getPortContractBak(@PathVariable("id") Long id){
		return insuranceContractService.getPortContractBak(id);
	}
	
	
	/**
	 * 查看采购合同-进口明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showChange/{id}", method = RequestMethod.GET)
	public String showChange(@PathVariable("id") Long id, Model model) {
		InsuranceContract insuranceContract = insuranceContractService.get(id);
		model.addAttribute("insuranceContractChange", insuranceContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(insuranceContract.getRelLoginNames());
		//相关业务员回显
		model.addAttribute("themeMembersChange", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/insuranceContractDetailChange";
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
			insuranctContract.setState(ActivitiConstant.ACTIVITI_COMMIT);
			insuranceContractService.save(insuranctContract);
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("tradeCategory", insuranctContract.getTradeCategory());
			//获取流程标识
			String processKey = activitiService.getCurrentProcessKey(InsuranceContract.class,insuranctContract);
			if(processKey!=null){
				result = activitiService.startWorkflow(insuranctContract, processKey, variables,
						(user.getLoginName()).toString());
			}else{
				return "流程未部署、不存在或存在多个，请联系管理员！";
			}
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
				insuranceContract.setState(ActivitiConstant.ACTIVITI_DRAFT);
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
	
	@RequestMapping(value = "toShipList/{tradeCategory}",method=RequestMethod.GET)
	public String toShipList(@PathVariable("tradeCategory") String tradeCategory, Model model){
		model.addAttribute("tradeCategory", tradeCategory);
		return "logistic/selectShipList";
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPDF/{contractNo}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("contractNo") String contractNo, HttpServletRequest request, HttpServletResponse response) {
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "saleContract.html", "MSYH.TTF", "style.css");
		Map<String, Object> exportMap = insuranceContractService.exportPDF(contractNo);
		jtph.entrance(exportMap, response);
	}
}
