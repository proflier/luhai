package com.cbmie.lh.logistic.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.logistic.service.ShipTraceService;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("logistic/shipTrace")
public class ShipTraceController extends BaseController {
	@Autowired
	private ShipTraceService shipTraceService;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BusinessPerssionService businessPerssionService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "logistic/shipTraceList";
	} 
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<ShipTrace> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		shipTraceService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = shipTraceService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityListNoPermission(HttpServletRequest request) {
		Page<ShipTrace> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = shipTraceService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		ShipTrace shipTrace = new ShipTrace();
		User currentUser = UserUtil.getCurrentUser();
		shipTrace.setCreaterNo(currentUser.getLoginName());
		shipTrace.setCreaterName(currentUser.getName());
		shipTrace.setCreaterDept(currentUser.getOrganization().getOrgName());
		shipTrace.setCreateDate(new Date());
		/*String shipNo = sequenceCodeService.getNextCode("001014");
		shipTrace.setShipNo(shipNo);*/
		model.addAttribute("shipTrace", shipTrace);
		model.addAttribute("action", "create");
		return "logistic/shipTraceForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ShipTrace shipTrace, Model model,String shipNoOld) throws JsonProcessingException {
		try {
			String  currentSequence = null;
			boolean flag = !shipTrace.getShipNo().equals(shipNoOld);
			boolean flag_sequence = true;
			while(!shipTraceService.checkShipNoUnique(shipTrace)){
				if("10850002".equals(shipTrace.getTradeCategory())){
					currentSequence=sequenceCodeService.getNextCode("001014").get("returnStr");
				}else{
					currentSequence=sequenceCodeService.getNextCode("001027").get("returnStr");
				}
				shipTrace.setShipNo(currentSequence);
				if(flag){
					flag_sequence = false;
					if("10850002".equals(shipTrace.getTradeCategory())){
						sequenceCodeService.getNextSequence("001014");
					}else{
						sequenceCodeService.getNextSequence("001027");
					}
				}
			}
			if(flag && flag_sequence){
				if("10850002".equals(shipTrace.getTradeCategory())){
					sequenceCodeService.getNextSequence("001014");
				}else{
					sequenceCodeService.getNextSequence("001027");
				}
			}
			//设置相关人员与默认值创建人
			String themeMemberIds = request.getParameter("themeMemberIds");
			shipTrace.setRelLoginNames(themeMemberIds);
			shipTraceService.save(shipTrace);
			EhCacheUtils.remove(DictUtils.CACHE_SHIP_NO_NAME );
			//调用获取下个sequence方法，将当前sequence保存
			return setReturnData("success",currentSequence!=null?"船编号重复，已自动生成不重复编号并保存":"保存成功",shipTrace.getId(),currentSequence);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",shipTrace.getId());
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
		ShipTrace shipTrace = shipTraceService.get(id);
		boolean flag = shipTraceService.checkIfUsed(shipTrace.getShipNo());
		model.addAttribute("shipTrace", shipTrace);
		model.addAttribute("action", "update");
		model.addAttribute("shipNoEditFlag", flag?"0":"1");
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(shipTrace.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "logistic/shipTraceForm";
	}
	
	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ShipTrace shipTrace, Model model,String shipNoOld) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		User currentUser = UserUtil.getCurrentUser();
		shipTrace.setUpdaterNo(currentUser.getLoginName());
		shipTrace.setUpdaterName(currentUser.getName());
		shipTrace.setUpdateDate(new Date());
		String  currentSequence = null;
		boolean flag = !shipTrace.getShipNo().equals(shipNoOld);
		boolean flag_sequence = true;
		if(flag){
			while(!shipTraceService.checkShipNoUnique(shipTrace)){
				if("10850002".equals(shipTrace.getTradeCategory())){
					currentSequence=sequenceCodeService.getNextCode("001014").get("returnStr");
				}else{
					currentSequence=sequenceCodeService.getNextCode("001027").get("returnStr");
				}
				flag_sequence = false;
				if("10850002".equals(shipTrace.getTradeCategory())){
					sequenceCodeService.getNextSequence("001014");
				}else{
					sequenceCodeService.getNextSequence("001027");
				}
				shipTrace.setShipNo(currentSequence);
			}
			if(flag_sequence){
				if("10850002".equals(shipTrace.getTradeCategory())){
					sequenceCodeService.getNextSequence("001014");
				}else{
					sequenceCodeService.getNextSequence("001027");
				}
			}
		}
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		shipTrace.setRelLoginNames(themeMemberIds);
		shipTraceService.update(shipTrace);
		EhCacheUtils.remove(DictUtils.CACHE_SHIP_NO_NAME );
		return setReturnData("success",currentSequence!=null?"船编号重复，已自动生成不重复编号并保存":"保存成功",shipTrace.getId(),currentSequence);
	}
	@ModelAttribute
	public void getShipTrace(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("shipTrace", shipTraceService.get(id));
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws  Exception {
		ShipTrace shipTrace = shipTraceService.get(id);
		boolean flag = shipTraceService.checkIfUsed(shipTrace.getShipNo());
		if(!flag){
			shipTraceService.delete(id);
			EhCacheUtils.remove(DictUtils.CACHE_SHIP_NO_NAME );
			return setReturnData("success","删除成功",id);
		}else{
			return setReturnData("success","船编号已用，不能删除",id);
		}
	
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
		ShipTrace shipTrace = shipTraceService.get(id);
		model.addAttribute("shipTrace", shipTrace);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(shipTrace.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "logistic/shipTraceDetail";
	}
	
	@RequestMapping(value="toPurchaseList", method = RequestMethod.GET)
	public String toOrderShipContractList(){
		return "logistic/relationPurchaseContractList";
	}
	@RequestMapping(value="outerShipTrace", method = RequestMethod.GET)
	@ResponseBody
	public List<ShipTrace> getOuterShipTrace(){
		return shipTraceService.getOuterShipTrace();
	}
	
	@RequestMapping(value="allShipTrace", method = RequestMethod.GET)
	@ResponseBody
	public List<ShipTrace> getAllShipTrace(){
		return shipTraceService.getAllShipTrace();
	}
	
	@RequestMapping(value="shipNameShow/{shipNo}", method = RequestMethod.GET)
	@ResponseBody
	public String shipNameShow(@PathVariable(value="shipNo") String shipNo){
		ShipTrace ship = shipTraceService.getShipByNo(shipNo);
		if(ship!=null){
			return ship.getNoAndName();
		}
		return null;
	}
	
	/**
	 * 查看明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "shipRelation/{id}", method = RequestMethod.GET)
	public String shipRelation(@PathVariable("id") Long id, Model model) throws JsonProcessingException {
		ShipTrace shipTrace = shipTraceService.get(id);
		List<SaleContract> saleContracts = shipTraceService.getSaleContracts(shipTrace.getShipNo());
		List<PurchaseContract> purchaseContracts = shipTraceService.getPurchaseContracts(shipTrace.getShipNo());
		List<PaymentConfirm> paymentConfirms = shipTraceService.getPaymentConfirms(shipTrace.getShipNo());
		List<SaleSettlement> saleSettlements = shipTraceService.getSaleSettlements(shipTrace.getShipNo());
		model.addAttribute("saleContracts", changeToJson(saleContracts));
		model.addAttribute("saleContractsSize", saleContracts.size());
		model.addAttribute("purchaseContracts", changeToJson(purchaseContracts));
		model.addAttribute("purchaseContractsSize", purchaseContracts.size());
		model.addAttribute("paymentConfirms", changeToJson(paymentConfirms));
		model.addAttribute("paymentConfirmsSize", paymentConfirms.size());
		model.addAttribute("saleSettlements", changeToJson(saleSettlements));
		model.addAttribute("saleSettlementsSize", saleSettlements.size());
		return "logistic/shipTrackRelation";
	}
	
	
	public <T> String changeToJson(List<T> o) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		String ss = objectMapper.writeValueAsString(o);
		ss.replace("\\r\\n", "");
		return ss.replace("\\r\\n", "");
	}
}
