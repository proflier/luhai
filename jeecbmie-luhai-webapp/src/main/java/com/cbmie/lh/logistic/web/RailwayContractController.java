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
import com.cbmie.lh.logistic.entity.RailwayContract;
import com.cbmie.lh.logistic.service.RailwayContractService;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("logistic/railwayContract")
public class RailwayContractController extends BaseController {

	@Autowired
	private RailwayContractService contractService;
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
		return "logistic/railwayContractList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<RailwayContract> page = getPage(request);
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
		Page<RailwayContract> page = getPage(request);
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
		RailwayContract contract = new RailwayContract();
		User currentUser = UserUtil.getCurrentUser();
		contract.setCreaterNo(currentUser.getLoginName());
		contract.setCreaterName(currentUser.getName());
		contract.setCreaterDept(currentUser.getOrganization().getOrgName());
		contract.setCreateDate(new Date());
		contract.setApplyDate(new Date());

		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001017");
		String innerContractNo = sequenceMap.get("returnStr");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		contract.setInnerContractNo(innerContractNo);
		model.addAttribute("railwayContract", contract);
		model.addAttribute("action", "create");
		return "logistic/railwayContractForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid RailwayContract contract, Model model) throws JsonProcessingException {
		try {
			String  currnetSequence = null;
			if (!contractService.checkCodeUique(contract)) {
				String innerContractNo = sequenceCodeService.getNextCode("001017").get("returnStr");
				contract.setInnerContractNo(innerContractNo);
				currnetSequence = innerContractNo;
			}
			contract.setSummary("[铁运合同审批]["+ bius.getAffiBaseInfoByCode(contract.getTraderName()) +"]" +  (contract.getMoney() == null ? "" : ("[金额" + contract.getMoney().longValue()+DictUtils.getDictLabelsByCodes(contract.getMoneyCurrencyCode(), "currency", "") + "]")));
			if(contract.getId()==null){
				//调用获取下个sequence方法，将当前sequence保存
				sequenceCodeService.getNextSequence("001017");
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
		RailwayContract railwayContract = contractService.get(id);
		model.addAttribute("railwayContract", railwayContract);
		model.addAttribute("action", "update");
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001017");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(railwayContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/railwayContractForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody RailwayContract railwayContract,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		railwayContract.setUpdaterNo(currentUser.getLoginName());
		railwayContract.setUpdaterName(currentUser.getName());
		railwayContract.setUpdateDate(new Date());
		railwayContract.setSummary("[铁运合同审批]["+ bius.getAffiBaseInfoByCode(railwayContract.getTraderName()) +"]" +  (railwayContract.getMoney() == null ? "" : ("[金额" + railwayContract.getMoney().longValue()+DictUtils.getDictLabelsByCodes(railwayContract.getMoneyCurrencyCode(), "currency", "") + "]")));
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		railwayContract.setRelLoginNames(themeMemberIds);
		contractService.update(railwayContract);
		return setReturnData("success","保存成功",railwayContract.getId());
	}
	
	@ModelAttribute
	public void getRailwayContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("railwayContract", contractService.get(id));
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
		RailwayContract railwayContract_source = contractService.getNoLoad(id);
		RailwayContract railwayContract = copyProperties(railwayContract_source);
		model.addAttribute("railwayContract", railwayContract);
		model.addAttribute("action", "update");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(railwayContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/railwayContractForm";
	}
	
	private RailwayContract copyProperties(RailwayContract railwayContract_source) throws IllegalAccessException, InvocationTargetException {
		RailwayContract railwayContract_dest = new RailwayContract();
		BeanUtils.copyProperties(railwayContract_dest, railwayContract_source);
		railwayContract_dest.setId(null);
		railwayContract_dest.setSourceId(railwayContract_source.getSourceId()==null?railwayContract_source.getId():railwayContract_source.getSourceId());
		railwayContract_dest.setPid(railwayContract_source.getId());
		railwayContract_dest.setChangeState("2");
		railwayContract_dest.setProcessInstanceId(null);
		railwayContract_dest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		railwayContract_dest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		railwayContract_dest.setCreaterNo(currentUser.getLoginName());
		railwayContract_dest.setCreaterName(currentUser.getName());
		railwayContract_dest.setCreaterDept(currentUser.getOrganization().getOrgName());
		railwayContract_dest.setCreateDate(new Date());
		railwayContract_dest.setUpdateDate(new Date());
		railwayContract_source.setChangeState("0");
		contractService.update(railwayContract_source);
		contractService.save(railwayContract_dest);
		//更新附件信息
		 List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(railwayContract_source.getId().toString(),"com_cbmie_lh_logistic_entity_RailwayContract");
		 accessoryService.copyAttach(accessoryList,railwayContract_dest.getId()+"",null);
		return railwayContract_dest;
	}

	/**
	 * 获取备份信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getRailwayContractBak/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<RailwayContract> getRailwayContractBak(@PathVariable("id") long id){
		return contractService.getRailwayContractBak(id);
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
		RailwayContract railwayContract = contractService.get(id);
		if(railwayContract.getPid()!=null){
			RailwayContract railwayContractOld = contractService.get(railwayContract.getPid());
			railwayContractOld.setChangeState("1");
			contractService.update(railwayContractOld);
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
		RailwayContract railwayContract = contractService.get(id);
		model.addAttribute("railwayContract", railwayContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(railwayContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/railwayContractDetail";
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
		RailwayContract railwayContract = contractService.get(id);
		model.addAttribute("railwayContractChange", railwayContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(railwayContract.getRelLoginNames());
		//相关业务员回显
		model.addAttribute("themeMembersChange", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/railwayContractDetailChange";
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
		RailwayContract contract = contractService.get(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(RailwayContract.class,contract);
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
				RailwayContract contract = contractService.get(id);
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
