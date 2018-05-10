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
import com.cbmie.lh.logistic.entity.OrderShipContract;
import com.cbmie.lh.logistic.entity.OrderShipContractSub;
import com.cbmie.lh.logistic.entity.PortContract;
import com.cbmie.lh.logistic.service.OrderShipContractService;
import com.cbmie.lh.logistic.service.OrderShipContractSubService;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("logistic/orderShipContract")
public class OrderShipContractController extends BaseController {

	@Autowired
	private OrderShipContractService contractService;
	
	@Autowired
	private OrderShipContractSubService orderShipContractSubService;
	
	
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
		return "logistic/orderShipContractList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<OrderShipContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		contractService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = contractService.search(page, filters);
		return getEasyUIData(page);
	}
	
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> jsonNoPermission(HttpServletRequest request) {
		Page<OrderShipContract> page = getPage(request);
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
		OrderShipContract orderShipContract = new OrderShipContract();
		User currentUser = UserUtil.getCurrentUser();
		orderShipContract.setCreaterNo(currentUser.getLoginName());
		orderShipContract.setCreaterName(currentUser.getName());
		orderShipContract.setCreaterDept(currentUser.getOrganization().getOrgName());
		orderShipContract.setCreateDate(new Date());
		orderShipContract.setUpdateDate(new Date());
		orderShipContract.setApplyDate(new Date());
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001004");
		String serialnumber = sequenceMap.get("returnStr");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		orderShipContract.setInnerContractNo(serialnumber+"(OV)");
		model.addAttribute("orderShipContract", orderShipContract);
		model.addAttribute("action", "create");
		return "logistic/orderShipContractForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid OrderShipContract orderShipContract, Model model) throws JsonProcessingException {
		try {
			if (!contractService.checkCodeUique(orderShipContract)) {
				return setReturnData("fail","编码重复",orderShipContract.getId());
			}
			String  currentSequence = null;
			while(!contractService.checkInnerCodeUique(orderShipContract)){
				currentSequence = sequenceCodeService.getNextSequence("001004").toString();
				if("10850002".equals(orderShipContract.getOrderShipType())){
					currentSequence+="(OV)";
				}else{
					currentSequence+="(DM)";
				}
				orderShipContract.setInnerContractNo(currentSequence);
			}
			orderShipContract.setSummary("[订船合同审批][" + bius.getAffiBaseInfoByCode(orderShipContract.getTraderName()) +"]" +  (orderShipContract.getMoney() == null ? "" : ("[合同金额" + orderShipContract.getMoney()+DictUtils.getDictLabelsByCodes(orderShipContract.getMoneyCurrencyCode(), "currency", "") + "]")));
			//设置相关人员与默认值创建人
			String themeMemberIds = request.getParameter("themeMemberIds");
			orderShipContract.setRelLoginNames(themeMemberIds);
			contractService.save(orderShipContract);
			//调用获取下个sequence方法，将当前sequence保存
			if(currentSequence==null){
				sequenceCodeService.getNextSequence("001004");
			}
			return setReturnData("success",currentSequence!=null?"内部合同号重复，已自动生成不重复编码并保存":"保存成功",orderShipContract.getId(),currentSequence);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return setReturnData("fail","保存失败",orderShipContract.getId());
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
		OrderShipContract orderShipContract = contractService.get(id);
		model.addAttribute("orderShipContract", orderShipContract);
		model.addAttribute("action", "update");
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001004");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(orderShipContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/orderShipContractForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OrderShipContract orderShipContract,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		orderShipContract.setUpdaterNo(currentUser.getLoginName());
		orderShipContract.setUpdaterName(currentUser.getName());
		orderShipContract.setUpdateDate(new Date());
		orderShipContract.setSummary("[订船合同审批][" + bius.getAffiBaseInfoByCode(orderShipContract.getTraderName()) +"]" +  (orderShipContract.getMoney() == null ? "" : ("[合同金额" + orderShipContract.getMoney()+DictUtils.getDictLabelsByCodes(orderShipContract.getMoneyCurrencyCode(), "currency", "") + "]")));
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		orderShipContract.setRelLoginNames(themeMemberIds);
		contractService.update(orderShipContract);
		return setReturnData("success","保存成功",orderShipContract.getId());
	}
	
	/**
	 * 变更订船合同
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "change/{id}", method = RequestMethod.GET)
	public String changeForm(@PathVariable("id") Long id, Model model) throws IllegalAccessException, InvocationTargetException {
		OrderShipContract orderShipContract_source = contractService.getNoLoad(id);
		OrderShipContract orderShipContract = copyProperties(orderShipContract_source);
		model.addAttribute("orderShipContract", orderShipContract);
		model.addAttribute("action", "update");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(orderShipContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/orderShipContractForm";
	}
	
	private OrderShipContract copyProperties(OrderShipContract orderShipContract_source) throws IllegalAccessException, InvocationTargetException {
		OrderShipContract orderShipContract_dest = new OrderShipContract();
		List<OrderShipContractSub> goodList_dest = new ArrayList<OrderShipContractSub>(); //子表
		BeanUtils.copyProperties(orderShipContract_dest, orderShipContract_source);
		orderShipContract_dest.setId(null);
		orderShipContract_dest.setSourceId(orderShipContract_source.getSourceId()==null?orderShipContract_source.getId():orderShipContract_source.getSourceId());
		orderShipContract_dest.setPid(orderShipContract_source.getId());
		orderShipContract_dest.setChangeState("2");
		orderShipContract_dest.setProcessInstanceId(null);
		orderShipContract_dest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		orderShipContract_dest.setShipSubs(null);
		orderShipContract_dest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		orderShipContract_dest.setCreaterNo(currentUser.getLoginName());
		orderShipContract_dest.setCreaterName(currentUser.getName());
		orderShipContract_dest.setCreaterDept(currentUser.getOrganization().getOrgName());
		orderShipContract_dest.setCreateDate(new Date());
		orderShipContract_dest.setUpdateDate(new Date());
		orderShipContract_source.setChangeState("0");
		contractService.update(orderShipContract_source);
		contractService.save(orderShipContract_dest);
		List<OrderShipContractSub> goods_sources = orderShipContract_source.getShipSubs();
		for(OrderShipContractSub good_source : goods_sources){
			OrderShipContractSub goods_dest = new OrderShipContractSub();
			BeanUtils.copyProperties(goods_dest, good_source);
			goods_dest.setId(null);
			goods_dest.setOrderShipContractId(orderShipContract_dest.getId());
			orderShipContractSubService.save(goods_dest);
			goodList_dest.add(goods_dest);
		}
		orderShipContract_dest.setShipSubs(goodList_dest );
		//更新附件信息
		 List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(orderShipContract_source.getId().toString(),"com_cbmie_lh_logistic_entity_OrderShipContract");
		 accessoryService.copyAttach(accessoryList,orderShipContract_dest.getId()+"",null);
		return orderShipContract_dest;
	}

	/**
	 * 获取备份信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getOrderShipContractBak/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<OrderShipContract> getOrderShipContractBak(@PathVariable("id") long id){
		return contractService.getOrderShipContractBak(id);
	}
	
	@ModelAttribute
	public void getContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("orderShipContract", contractService.get(id));
		}
	}
	
	/**
	 * 删除订船合同
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		OrderShipContract orderShipContract = contractService.get(id);
		if(orderShipContract.getPid()!=null){
			OrderShipContract orderShipContractOld = contractService.get(orderShipContract.getPid());
			orderShipContractOld.setChangeState("1");
			contractService.update(orderShipContractOld);
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
		OrderShipContract orderShipContract = contractService.get(id);
		model.addAttribute("orderShipContract", orderShipContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(orderShipContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/orderShipContractDetail";
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
		OrderShipContract orderShipContract = contractService.get(id);
		model.addAttribute("orderShipContractChange", orderShipContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(orderShipContract.getRelLoginNames());
		//相关业务员回显
		model.addAttribute("themeMembersChange", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/orderShipContractDetailChange";
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
		OrderShipContract orderShipContract = contractService.get(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("orderShipType", orderShipContract.getOrderShipType());
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(OrderShipContract.class,orderShipContract);
		if(processKey!=null){
			result = activitiService.startWorkflow(orderShipContract, processKey, variables,
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
				OrderShipContract orderShipContract = contractService.get(id);
				orderShipContract.setProcessInstanceId(null);
				orderShipContract.setState(ActivitiConstant.ACTIVITI_DRAFT);
				contractService.save(orderShipContract);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	
	@RequestMapping(value = "getOrderShipContractByNo/{contractNo}")
	@ResponseBody
	public OrderShipContract getOrderShipContractByNo(@PathVariable(value="contractNo") String contractNo){
		return contractService.getOrderShipContractByNo(contractNo);
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPDF/{contractNo}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("contractNo") String contractNo, HttpServletRequest request, HttpServletResponse response) {
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "orderShipContract.html", "MSYH.TTF", "style.css");
		Map<String, Object> exportMap = contractService.exportPDF(contractNo);
		jtph.entrance(exportMap, response);
	}
	
	@RequestMapping(value = "getShipNameById/{id}")
	@ResponseBody
	public String getShipNameById(@PathVariable("id") Long id) {
		return contractService.getShipNameById(id);
	}
}
