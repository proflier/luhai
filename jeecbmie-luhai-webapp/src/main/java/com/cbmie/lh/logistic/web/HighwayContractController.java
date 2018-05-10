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
import com.cbmie.lh.logistic.entity.HighwayContract;
import com.cbmie.lh.logistic.service.HighwayContractService;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("logistic/highwayContract")
public class HighwayContractController extends BaseController {

	@Autowired
	private HighwayContractService contractService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@Autowired
	private BaseInfoUtilService bius;
	
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
		return "logistic/highwayContractList";
	}
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<HighwayContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		contractService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = contractService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> jsonNoPermission(HttpServletRequest request) {
		Page<HighwayContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = contractService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @param model
	 * @throws ParseException 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		HighwayContract contract = new HighwayContract();
		User currentUser = UserUtil.getCurrentUser();
		contract.setCreaterNo(currentUser.getLoginName());
		contract.setCreaterName(currentUser.getName());
		contract.setCreaterDept(currentUser.getOrganization().getOrgName());
		contract.setCreateDate(new Date());
		contract.setApplyDate(new Date());
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001016");
		String innerContractNo = sequenceMap.get("returnStr");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		contract.setInnerContractNo(innerContractNo);
		model.addAttribute("highwayContract", contract);
		model.addAttribute("action", "create");
		return "logistic/highwayContractForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid HighwayContract contract, Model model) throws JsonProcessingException {
		try {
			
			String  currnetSequence = null;
			if (!contractService.checkCodeUique(contract)) {
				String innerContractNo = sequenceCodeService.getNextCode("001016").get("returnStr");
				contract.setInnerContractNo(innerContractNo);
				currnetSequence = innerContractNo;
			}
			contract.setSummary("[汽运合同审批]["+ bius.getAffiBaseInfoByCode(contract.getTraderName()) +"]" +  (contract.getMoney() == null ? "" : ("[金额" + contract.getMoney().longValue()+DictUtils.getDictLabelsByCodes(contract.getMoneyCurrencyCode(), "currency", "") + "]")));
			if(contract.getId()==null){
				//调用获取下个sequence方法，将当前sequence保存
				sequenceCodeService.getNextSequence("001016");
			}
			//设置相关人员与默认值创建人
			String themeMemberIds = request.getParameter("themeMemberIds");
			contract.setRelLoginNames(themeMemberIds);
			contractService.save(contract);
			return setReturnData("success", currnetSequence!=null?"内部合同号重复，已自动生成不重复编码并保存":"保存成功", contract.getId(),currnetSequence);
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
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		HighwayContract highwayContract = contractService.get(id);
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001016");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		model.addAttribute("highwayContract", highwayContract);
		model.addAttribute("action", "update");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(highwayContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/highwayContractForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody HighwayContract highwayContract,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		highwayContract.setUpdaterNo(currentUser.getLoginName());
		highwayContract.setUpdaterName(currentUser.getName());
		highwayContract.setUpdateDate(new Date());
		highwayContract.setSummary("[汽运合同审批]["+ bius.getAffiBaseInfoByCode(highwayContract.getTraderName()) +"]" +  (highwayContract.getMoney() == null ? "" : ("[金额" + highwayContract.getMoney().longValue()+DictUtils.getDictLabelsByCodes(highwayContract.getMoneyCurrencyCode(), "currency", "") + "]")));
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		highwayContract.setRelLoginNames(themeMemberIds);
		contractService.update(highwayContract);
		return setReturnData("success","保存成功",highwayContract.getId());
	}
	
	@ModelAttribute
	public void getHighwayContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("highwayContract", contractService.get(id));
		}
	}
	
	/**
	 * 变更码头合同
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "change/{id}", method = RequestMethod.GET)
	public String changeForm(@PathVariable("id") Long id, Model model) throws IllegalAccessException, InvocationTargetException {
		HighwayContract highwayContract_source = contractService.getNoLoad(id);
		HighwayContract highwayContract = copyProperties(highwayContract_source);
		model.addAttribute("highwayContract", highwayContract);
		model.addAttribute("action", "update");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(highwayContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/highwayContractForm";
	}
	
	private HighwayContract copyProperties(HighwayContract highwayContract_source) throws IllegalAccessException, InvocationTargetException {
		HighwayContract highwayContract_dest = new HighwayContract();
		BeanUtils.copyProperties(highwayContract_dest, highwayContract_source);
		highwayContract_dest.setId(null);
		highwayContract_dest.setSourceId(highwayContract_source.getSourceId()==null?highwayContract_source.getId():highwayContract_source.getSourceId());
		highwayContract_dest.setPid(highwayContract_source.getId());
		highwayContract_dest.setChangeState("2");
		highwayContract_dest.setProcessInstanceId(null);
		highwayContract_dest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		highwayContract_dest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		highwayContract_dest.setCreaterNo(currentUser.getLoginName());
		highwayContract_dest.setCreaterName(currentUser.getName());
		highwayContract_dest.setCreaterDept(currentUser.getOrganization().getOrgName());
		highwayContract_dest.setCreateDate(new Date());
		highwayContract_dest.setUpdateDate(new Date());
		highwayContract_source.setChangeState("0");
		contractService.update(highwayContract_source);
		contractService.save(highwayContract_dest);
		//更新附件信息
		 List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(highwayContract_source.getId().toString(),"com_cbmie_lh_logistic_entity_HighwayContract");
		 accessoryService.copyAttach(accessoryList,highwayContract_dest.getId()+"",null);
		return highwayContract_dest;
	}

	/**
	 * 获取备份信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getHighwayContractBak/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<HighwayContract> getHighwayContractBak(@PathVariable("id") long id){
		return contractService.getHighwayContractBak(id);
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
		HighwayContract highwayContract = contractService.get(id);
		if(highwayContract.getPid()!=null){
			HighwayContract highwayContractOld = contractService.get(highwayContract.getPid());
			highwayContractOld.setChangeState("1");
			contractService.update(highwayContractOld);
		}
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
		HighwayContract highwayContract = contractService.get(id);
		model.addAttribute("highwayContract", highwayContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(highwayContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/highwayContractDetail";
	}
	
	/**
	 * 查看变更明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showChange/{id}", method = RequestMethod.GET)
	public String showChange(@PathVariable("id") Long id, Model model) {
		HighwayContract highwayContract = contractService.get(id);
		model.addAttribute("highwayContractChange", highwayContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(highwayContract.getRelLoginNames());
		//相关业务员回显
		model.addAttribute("themeMembersChange", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/highwayContractDetailChange";
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
		HighwayContract contract = contractService.get(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(HighwayContract.class,contract);
		if(processKey!=null){
			result = activitiService.startWorkflow(contract, processKey, variables,
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
				HighwayContract contract = contractService.get(id);
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
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPDF/{contractNo}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("contractNo") String contractNo, HttpServletRequest request, HttpServletResponse response) {
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "saleContract.html", "MSYH.TTF", "style.css");
		Map<String, Object> exportMap = contractService.exportPDF(contractNo);
		jtph.entrance(exportMap, response);
	}
}
