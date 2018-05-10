package com.cbmie.lh.logistic.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.cbmie.lh.logistic.entity.ExtraExpense;
import com.cbmie.lh.logistic.entity.OperateExpense;
import com.cbmie.lh.logistic.entity.PortContract;
import com.cbmie.lh.logistic.service.ExtraExpenseService;
import com.cbmie.lh.logistic.service.OperateExpenseService;
import com.cbmie.lh.logistic.service.PortContractService;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("logistic/portContract")
public class PortContractController extends BaseController {

	@Autowired
	private PortContractService contractService;
	
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
	private OperateExpenseService operateService;
	
	@Autowired
	private ExtraExpenseService extraService;
	
	@Autowired
	private AccessoryService accessoryService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/portContractList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<PortContract> page = getPage(request);
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
	public Map<String, Object> searchNoPermission(HttpServletRequest request) {
		Page<PortContract> page = getPage(request);
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
		PortContract contract = new PortContract();
		User currentUser = UserUtil.getCurrentUser();
		contract.setCreaterNo(currentUser.getLoginName());
		contract.setCreaterName(currentUser.getName());
		contract.setCreaterDept(currentUser.getOrganization().getOrgName());
		contract.setCreateDate(new Date());
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001018");
		String contractNo = sequenceMap.get("returnStr");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		contract.setContractNo(contractNo);
		model.addAttribute("portContract", contract);
		model.addAttribute("action", "create");
		return "logistic/portContractForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PortContract contract, Model model) throws JsonProcessingException {
		try {
			String  currnetSequence = null;
			if (!contractService.checkCodeUique(contract)) {
				String contractNo = sequenceCodeService.getNextCode("001018").get("returnStr");
				contract.setContractNo(contractNo);
				currnetSequence = contractNo;
			}
			contract.setSummary("[码头合同审批][" + bius.getAffiBaseInfoByCode(contract.getSignAffiliate()) + "]");
			if(contract.getId()==null){
				//调用获取下个sequence方法，将当前sequence保存
				sequenceCodeService.getNextSequence("001018");
			}
			//设置相关人员与默认值创建人
			String themeMemberIds = request.getParameter("themeMemberIds");
			contract.setRelLoginNames(themeMemberIds);
			contractService.save(contract);
			return setReturnData("success", currnetSequence!=null?"合同号重复，已自动生成不重复编码并保存":"保存成功", contract.getId(),currnetSequence);
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
		PortContract portContract = contractService.get(id);
		model.addAttribute("portContract", portContract);
		model.addAttribute("action", "update");
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001018");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(portContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/portContractForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PortContract portContract,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		portContract.setUpdaterNo(currentUser.getLoginName());
		portContract.setUpdaterName(currentUser.getName());
		portContract.setUpdateDate(new Date());
		portContract.setSummary("[码头合同审批][" + bius.getAffiBaseInfoByCode(portContract.getSignAffiliate()) + "]");
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		portContract.setRelLoginNames(themeMemberIds);
		contractService.update(portContract);
		return setReturnData("success","保存成功",portContract.getId());
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
		PortContract portContract_source = contractService.getNoLoad(id);
		PortContract portContract = copyProperties(portContract_source);
		model.addAttribute("portContract", portContract);
		model.addAttribute("action", "update");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(portContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/portContractForm";
	}
	
	private PortContract copyProperties(PortContract portContract_source) throws IllegalAccessException, InvocationTargetException {
		PortContract portContract_dest = new PortContract();
		List<OperateExpense> operateExpenseList_dest = new ArrayList<OperateExpense>();
		List<ExtraExpense> extraExpenseList_dest = new ArrayList<ExtraExpense>();
		BeanUtils.copyProperties(portContract_dest, portContract_source);
		portContract_dest.setId(null);
		portContract_dest.setSourceId(portContract_source.getSourceId()==null?portContract_source.getId():portContract_source.getSourceId());
		portContract_dest.setPid(portContract_source.getId());
		portContract_dest.setOperateExpenseSubs(null);
		portContract_dest.setExtraExpenseSubs(null);
		portContract_dest.setChangeState("2");
		portContract_dest.setProcessInstanceId(null);
		portContract_dest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		portContract_dest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		portContract_dest.setCreaterNo(currentUser.getLoginName());
		portContract_dest.setCreaterName(currentUser.getName());
		portContract_dest.setCreaterDept(currentUser.getOrganization().getOrgName());
		portContract_dest.setCreateDate(new Date());
		portContract_dest.setUpdateDate(new Date());
		portContract_source.setChangeState("0");
		contractService.update(portContract_source);
		contractService.save(portContract_dest);
		//子表信息
		List<OperateExpense> operateExpense_sources = portContract_source.getOperateExpenseSubs();
		for(OperateExpense operateExpense_source : operateExpense_sources){
			OperateExpense operateExpense_dest = new OperateExpense();
			BeanUtils.copyProperties(operateExpense_dest,operateExpense_source);
			operateExpense_dest.setId(null);
			operateExpense_dest.setPortContractId(portContract_dest.getId());
			operateService.save(operateExpense_dest);
			operateExpenseList_dest.add(operateExpense_dest);
		}
		portContract_dest.setOperateExpenseSubs(operateExpenseList_dest);
		//子表信息
		List<ExtraExpense> extraExpense_sources = portContract_source.getExtraExpenseSubs();
		for(ExtraExpense extraExpense_source : extraExpense_sources){
			ExtraExpense extraExpense_dest = new ExtraExpense();
			BeanUtils.copyProperties(extraExpense_dest,extraExpense_source);
			extraExpense_dest.setId(null);
			extraExpense_dest.setPortContractId(portContract_dest.getId());
			extraService.save(extraExpense_dest);
			extraExpenseList_dest.add(extraExpense_dest);
		}
		portContract_dest.setExtraExpenseSubs(extraExpenseList_dest);
		//更新附件信息
		 List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(portContract_source.getId().toString(),"com_cbmie_lh_logistic_entity_PortContract");
		 accessoryService.copyAttach(accessoryList,portContract_dest.getId()+"",null);
		return portContract_dest;
	}

	/**
	 * 获取备份信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getPortContractBak/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<PortContract> getPortContractBak(@PathVariable("id") long id){
		return contractService.getPortContractBak(id);
	}
	
	
	@ModelAttribute
	public void getPortContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("portContract", contractService.get(id));
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
		PortContract portContract = contractService.get(id);
		if(portContract.getPid()!=null){
			PortContract portContractOld = contractService.get(portContract.getPid());
			portContractOld.setChangeState("1");
			contractService.update(portContractOld);
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
		PortContract portContract = contractService.get(id);
		model.addAttribute("portContract", portContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(portContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/portContractDetail";
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
		PortContract portContract = contractService.get(id);
		model.addAttribute("portContractChange", portContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(portContract.getRelLoginNames());
		//相关业务员回显
		model.addAttribute("themeMembersChange", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/portContractDetailChange";
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
		PortContract portContract = contractService.get(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(PortContract.class,portContract);
		if(processKey!=null){
			result = activitiService.startWorkflow(portContract, processKey, variables,
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
				PortContract portContract = contractService.get(id);
				portContract.setProcessInstanceId(null);
				portContract.setState(ActivitiConstant.ACTIVITI_DRAFT);
				contractService.save(portContract);
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
