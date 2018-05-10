package com.cbmie.lh.logistic.web;

import java.math.BigDecimal;
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
import com.cbmie.lh.logistic.entity.OrderShipContract;
import com.cbmie.lh.logistic.entity.OrderShipContractSub;
import com.cbmie.lh.logistic.entity.ShipmentsSettlement;
import com.cbmie.lh.logistic.entity.ShipmentsSettlementSub;
import com.cbmie.lh.logistic.service.OrderShipContractService;
import com.cbmie.lh.logistic.service.OrderShipContractSubService;
import com.cbmie.lh.logistic.service.ShipmentsSettlementService;
import com.cbmie.lh.logistic.service.ShipmentsSettlementSubService;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.DictMain;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.DictChildService;
import com.cbmie.system.service.DictMainService;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping(value="logistic/shipments")
public class ShipmentsSettlementController extends BaseController {

	@Autowired
	private ShipmentsSettlementService shipmentService;
	@Autowired
	private BaseInfoUtilService bius;
	@Autowired
	private OrderShipContractService orderShipContractService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private OrderShipContractService orderShipService;
	@Autowired
	private ShipmentsSettlementSubService shipSubService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/shipmentsList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<ShipmentsSettlement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		shipmentService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = shipmentService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		ShipmentsSettlement shipmentsSettlement = new ShipmentsSettlement();
		User currentUser = UserUtil.getCurrentUser();
		shipmentsSettlement.setCreaterNo(currentUser.getLoginName());
		shipmentsSettlement.setCreaterName(currentUser.getName());
		shipmentsSettlement.setCreaterDept(currentUser.getOrganization().getOrgName());
		shipmentsSettlement.setCreateDate(new Date());
		model.addAttribute("shipmentsSettlement", shipmentsSettlement);
		model.addAttribute("action", "create");
		return "logistic/shipmentsForm";
	}
	
	/**
	 * 添加
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ShipmentsSettlement shipmentsSettlement) throws JsonProcessingException {
			String contractNo = shipmentsSettlement.getContractNo();
			OrderShipContract  orderShipContract  = orderShipContractService.getOrderShipContractByNo(contractNo);
			String summary = "[船运结算]";
			if(orderShipContract!=null){
				summary=summary+"["+bius.getAffiBaseInfoByCode(orderShipContract.getTraderName())+"]";
			}
			List<ShipmentsSettlementSub> subs = shipmentsSettlement.getSettleSubs();
			if(subs!=null && subs.size()>0){
				BigDecimal total = new BigDecimal(0.0);
				for(ShipmentsSettlementSub sub : subs){
					if(sub.getTotalPrice()!=null)
					total = total.add(new BigDecimal(sub.getTotalPrice()));
				}
				summary = summary+"[金额"+total.doubleValue()+DictUtils.getDictLabelsByCodes(subs.get(0).getMoneyCurrencyCode(), "currency", "")+"]";
			}
			shipmentsSettlement.setSummary(summary);
			shipmentsSettlement.setContractNoOld(shipmentsSettlement.getContractNo());
			shipmentService.save(shipmentsSettlement);
			initShipSubs(shipmentsSettlement);
			return setReturnData("success","保存成功",shipmentsSettlement.getId());
	}
	
	private void initShipSubs(ShipmentsSettlement shipmentsSettlement){
		String orderContractNo = shipmentsSettlement.getContractNo();
		OrderShipContract contract = orderShipService.getOrderShipContractByInnerNo(orderContractNo);
		List<OrderShipContractSub> order_subs = contract.getShipSubs();
		for(OrderShipContractSub order_sub : order_subs){
			ShipmentsSettlementSub sub = new ShipmentsSettlementSub();
			sub.setShip(order_sub.getShip());
			sub.setShipNo(order_sub.getShipNo());
			sub.setMoneyCurrencyCode("CY001");
			sub.setShipmentsSettleId(shipmentsSettlement.getId());
			shipmentsSettlement.getSettleSubs().add(sub);
			shipSubService.save(sub);
		}
		String summary = shipmentService.getSummary(shipmentsSettlement);
		shipmentsSettlement.setSummary(summary);
		shipmentService.update(shipmentsSettlement);
	}
	private void removeSubs(ShipmentsSettlement shipmentsSettlement){
		/*List<ShipmentsSettlementSub>  list = shipmentsSettlement.getSettleSubs();
		for(ShipmentsSettlementSub sub : list){
			shipSubService.delete(sub);
		}*/
		shipmentsSettlement.getSettleSubs().clear();
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
		model.addAttribute("shipmentsSettlement", shipmentService.get(id));
		model.addAttribute("action", "update");
		return "logistic/shipmentsForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ShipmentsSettlement shipmentsSettlement) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		shipmentsSettlement.setUpdaterNo(currentUser.getLoginName());
		shipmentsSettlement.setUpdaterName(currentUser.getName());
		shipmentsSettlement.setUpdateDate(new Date());
		if(!shipmentsSettlement.getContractNo().equals(shipmentsSettlement.getContractNoOld())){
			removeSubs(shipmentsSettlement);
			initShipSubs(shipmentsSettlement);
		}
		String contractNo = shipmentsSettlement.getContractNo();
		OrderShipContract  orderShipContract  = orderShipContractService.getOrderShipContractByNo(contractNo);
		String summary = "[船运结算]";
		if(orderShipContract!=null){
			summary=summary+"["+bius.getAffiBaseInfoByCode(orderShipContract.getTraderName())+"]";
		}
		List<ShipmentsSettlementSub> subs = shipmentsSettlement.getSettleSubs();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(ShipmentsSettlementSub sub : subs){
				if(sub.getTotalPrice()!=null)
				total = total.add(new BigDecimal(sub.getTotalPrice()));
			}
			summary = summary+"[金额"+total.doubleValue()+DictUtils.getDictLabelsByCodes(subs.get(0).getMoneyCurrencyCode(), "currency", "")+"]";
		}
		shipmentsSettlement.setSummary(summary);
		shipmentsSettlement.setContractNoOld(shipmentsSettlement.getContractNo());
		shipmentService.update(shipmentsSettlement);
		return setReturnData("success","保存成功",shipmentsSettlement.getId());
	}
	
	@ModelAttribute
	public void getShipmentsSettlement(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("shipmentsSettlement", shipmentService.get(id));
		}
	}
	
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("shipmentsSettlement", shipmentService.get(id));
		model.addAttribute("action", "view");
		return "logistic/shipmentsDetail";
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		shipmentService.delete(id);
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
		ShipmentsSettlement shipmentsSettlement = shipmentService.get(id);
		String contractNo = shipmentsSettlement.getContractNo();
		OrderShipContract  orderShipContract  = orderShipContractService.getOrderShipContractByNo(contractNo);
		String summary = "[船运结算]";
		if(orderShipContract!=null){
			summary=summary+"["+bius.getAffiBaseInfoByCode(orderShipContract.getTraderName())+"]";
		}
		List<ShipmentsSettlementSub> subs = shipmentsSettlement.getSettleSubs();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(ShipmentsSettlementSub sub : subs){
				if(sub.getTotalPrice()!=null)
				total = total.add(new BigDecimal(sub.getTotalPrice()));
			}
			summary = summary+"[金额"+total.longValue()+DictUtils.getDictLabelsByCodes(subs.get(0).getMoneyCurrencyCode(), "currency", "")+"]";
		}
		shipmentsSettlement.setSummary(summary);
		//此处可以增加业务验证
		boolean flag = true; //业务是否发起流程（默认发起）
		if(shipmentsSettlement.getSettleSubs().size()<1){
			result.put("msg", "结算信息列表不能为空");
			flag = false;
		}
		if(flag){
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("tradeCategory", shipmentsSettlement.getTradeCategory());
			//获取流程标识
			String processKey = activitiService.getCurrentProcessKey(ShipmentsSettlement.class,shipmentsSettlement);
			if(processKey!=null){
				result = activitiService.startWorkflow(shipmentsSettlement, processKey, variables,
						(user.getLoginName()).toString());
			}else{
				return "流程未部署、不存在或存在多个，请联系管理员！";
			}
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
				ShipmentsSettlement shipmentsSettlement = shipmentService.get(id);
				shipmentsSettlement.setProcessInstanceId(null);
				shipmentsSettlement.setState(ActivitiConstant.ACTIVITI_DRAFT);
				shipmentService.update(shipmentsSettlement);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	@RequestMapping(value = "toContractList")
	public String toOrderShipContract(){
		return "logistic/selectOrderShipContracts";
	}
}
