package com.cbmie.lh.sale.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.financial.entity.InputInvoice;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.lh.sale.entity.SaleContractGoodsIndex;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.lh.sale.service.SaleContractGoodsIndexService;
import com.cbmie.lh.sale.service.SaleContractGoodsService;
import com.cbmie.lh.sale.service.SaleContractService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("sale/saleContract")
public class SaleContractController extends BaseController {

	@Autowired
	private SaleContractService contractService;
	@Autowired
	private SaleContractGoodsService contractGoodsService;
	@Autowired
	private SaleContractGoodsIndexService indexService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private AccessoryService accessoryService;
	@Autowired
	private SequenceCodeService sequenceCodeService;
	@Autowired
	private BusinessPerssionService businessPerssionService;
	@Autowired
	private BaseInfoUtilService bius;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sale/saleContractList";
	}

	/**
	 * 获取采购合同-进口json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<SaleContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		contractService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = contractService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取采购合同-进口jsonNoPermission
	 */
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityListNoPermission(HttpServletRequest request) {
		Page<SaleContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = contractService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加采购合同-进口跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		SaleContract saleContract = new SaleContract();
		User currentUser = UserUtil.getCurrentUser();
		saleContract.setCreaterNo(currentUser.getLoginName());
		saleContract.setCreaterName(currentUser.getName());
		saleContract.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleContract.setCreateDate(new Date());
		saleContract.setUpdateDate(new Date());
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001002");
		String contranctNo = sequenceMap.get("returnStr");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		saleContract.setContractNo(contranctNo+"(DM)");
		model.addAttribute("saleContract", saleContract);
		model.addAttribute("action", "create");
		return "sale/saleContractForm";
	}
	
	/**
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleContract saleContract, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (!contractService.checkContractNoUnique(saleContract)) {
			String contranctNo = sequenceCodeService.getNextCode("001002").get("returnStr");
			saleContract.setContractNo(contranctNo);
			currnetSequence = contranctNo;
		}
		saleContract.setSummary("[销售合同审批]["+ bius.getAffiBaseInfoByCode(saleContract.getSeller()) +"]" +  (saleContract.getContractAmount() == null ? "" : ("[合同金额" + saleContract.getContractAmount() + "元]")));
		if(saleContract.getId()==null){
			sequenceCodeService.getNextSequence("001002");
		}
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		saleContract.setRelLoginNames(themeMemberIds);
		contractService.save(saleContract);
		saleContract.setSourceId(saleContract.getId());
		contractService.update(saleContract);
		return setReturnData("success", currnetSequence!=null?"销售合同号重复，已自动生成不重复编码并保存":"保存成功", saleContract.getId(),currnetSequence);
	}
	
	/**
	 * 修改采购合同-进口跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		SaleContract saleContract = contractService.get(id);
		model.addAttribute("saleContract", saleContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(saleContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "update");
		return "sale/saleContractForm";
	}

	/**
	 * 修改采购合同-进口
	 * 
	 * @param purchaseContract
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleContract saleContract, Model model) throws JsonProcessingException {
		if (saleContract.getPid()==null && !contractService.checkContractNoUnique(saleContract)) {
			return setReturnData("fail", "销售合同号重复", saleContract.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		saleContract.setUpdaterNo(currentUser.getLoginName());
		saleContract.setUpdaterName(currentUser.getName());
		saleContract.setUpdateDate(new Date());
		saleContract.setSummary("[销售合同审批]["+ bius.getAffiBaseInfoByCode(saleContract.getSeller()) +"]" +  (saleContract.getContractAmount() == null ? "" : ("[合同金额" + saleContract.getContractAmount() + "元]")));
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		saleContract.setRelLoginNames(themeMemberIds);
		contractService.update(saleContract);
		return setReturnData("success", "保存成功", saleContract.getId());
	}
	

	@ModelAttribute
	public void getSaleContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleContract", contractService.get(id));
		}
	}
	
	/**
	 * 变更采购合同-进口跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "change/{id}", method = RequestMethod.GET)
	public String changeForm(@PathVariable("id") Long id, Model model) throws IllegalAccessException, InvocationTargetException {
		SaleContract saleContract_source = contractService.getNoLoad(id);
		SaleContract saleContract = copyProperties(saleContract_source);
		model.addAttribute("saleContract", saleContract);
		model.addAttribute("action", "update");
		return "sale/saleContractForm";
	}
	
	public SaleContract copyProperties(SaleContract saleContract_source) throws IllegalAccessException, InvocationTargetException{
		SaleContract saleContract_dest = new SaleContract();  //主表
		List<SaleContractGoods> goodList_dest = new ArrayList<SaleContractGoods>(); //子表
		BeanUtils.copyProperties(saleContract_dest, saleContract_source);
		saleContract_dest.setId(null);
		saleContract_dest.setSourceId(saleContract_source.getSourceId()==null?saleContract_source.getId():saleContract_source.getSourceId());
		saleContract_dest.setPid(saleContract_source.getId());
		saleContract_dest.setSaleContractSubList(null);
		saleContract_dest.setChangeState("2");
		saleContract_dest.setProcessInstanceId(null);
		saleContract_dest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		saleContract_dest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		saleContract_dest.setCreaterNo(currentUser.getLoginName());
		saleContract_dest.setCreaterName(currentUser.getName());
		saleContract_dest.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleContract_dest.setCreateDate(new Date());
		saleContract_dest.setUpdateDate(new Date());
		saleContract_source.setChangeState("0");
		contractService.update(saleContract_source);
		contractService.save(saleContract_dest);
		List<SaleContractGoods> goods_sources = saleContract_source.getSaleContractSubList();
		for(SaleContractGoods good_source : goods_sources){
			List<SaleContractGoodsIndex> goodsIndexList_dest = new ArrayList<SaleContractGoodsIndex>();
			List<SaleContractGoodsIndex> goodsIndexList_source = good_source.getGoodsIndexList();
			SaleContractGoods goods_dest = new SaleContractGoods();
			BeanUtils.copyProperties(goods_dest, good_source);
			goods_dest.setGoodsIndexList(null);
			goods_dest.setId(null);
			goods_dest.setSaleContractId(saleContract_dest.getId());
			contractGoodsService.save(goods_dest);
			goodList_dest.add(goods_dest);
			for(SaleContractGoodsIndex index_source : goodsIndexList_source){
				SaleContractGoodsIndex index_dest = new SaleContractGoodsIndex();
				BeanUtils.copyProperties(index_dest, index_source);
				index_dest.setId(null);
				index_dest.setSaleContractGoodsId(goods_dest.getId());
				indexService.save(index_dest);
				goodsIndexList_dest.add(index_dest);
			}
			goods_dest.setGoodsIndexList(goodsIndexList_dest);
		}
		saleContract_dest.setSaleContractSubList(goodList_dest );
		//更新附件信息
		 List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(saleContract_source.getId().toString(),"com_cbmie_lh_sale_entity_SaleContract");
		 accessoryService.copyAttach(accessoryList,saleContract_dest.getId()+"",null);
		return saleContract_dest; 
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
		SaleContract saleContract = contractService.get(id);
		model.addAttribute("saleContract", saleContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(saleContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "sale/saleContractDetail";
	}
	
	/**
	 * 查看明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detailByNo/{contractNo}", method = RequestMethod.GET)
	public String detailByNo(@PathVariable("contractNo") String contractNo, Model model) {
		model.addAttribute("saleContract", contractService.getSaleContractByNo(contractNo));
		return "sale/saleContractView";
	}
	
	@RequestMapping(value = "getByNo/{contractNo}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SaleContract getByNo(@PathVariable("contractNo") String contractNo) {
		return contractService.getSaleContractByNo(contractNo);
	}
	
	@RequestMapping(value = "getById/{id}", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SaleContract getById(@PathVariable("id") Long id) {
		return contractService.getNoLoad(id);
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
		SaleContract saleContract = contractService.get(id);
		saleContract.setSummary("[销售合同审批]["+ bius.getAffiBaseInfoByCode(saleContract.getSeller()) +"]" +  (saleContract.getContractAmount() == null ? "" : ("[合同金额" + saleContract.getContractAmount().longValue() + "元]")));
		contractService.update(saleContract);
		//此处可以增加业务验证
		boolean flag = true; //业务是否发起流程（默认发起）
		if(saleContract.getSaleContractSubList().size()<1){
			result.put("msg", "货物明细列表不能为空");
			flag = false;
		}
		if(flag){
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("businessManager", saleContract.getBusinessManager());
			variables.put("manageMode", saleContract.getManageMode());
			//获取流程标识
			String processKey = activitiService.getCurrentProcessKey(SaleContract.class,saleContract);
			if(processKey!=null){
				result = activitiService.startWorkflow(saleContract, processKey, variables,
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
				SaleContract saleContract = contractService.get(id);
				saleContract.setProcessInstanceId(null);
				saleContract.setState(ActivitiConstant.ACTIVITI_DRAFT);
				contractService.save(saleContract);
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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		SaleContract saleContract = contractService.get(id);
		if(saleContract.getPid()!=null){
			SaleContract parent = contractService.get(saleContract.getPid());
			parent.setChangeState("1");
			contractService.update(parent);
		}
		contractService.delete(id);
		return setReturnData("success","删除成功",id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "closeContract/{id}")
	@ResponseBody
	public String closeContract(@PathVariable("id") Long id) {
		SaleContract saleContract = contractService.get(id);
		saleContract.setClosedFlag("1");
		contractService.update(saleContract);
		return "success";
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "startContract/{id}")
	@ResponseBody
	public String startContract(@PathVariable("id") Long id) {
		SaleContract saleContract = contractService.get(id);
		saleContract.setClosedFlag("0");
		contractService.update(saleContract);
		return "success";
	}
	
	/**
	 * 获取所有生效 未关闭的销售合同
	 * @return
	 */
	@RequestMapping(value = "getAllEffectSaleContract", method = RequestMethod.GET)
	@ResponseBody
	public List<SaleContract> getAllEffectSaleContract(){
		return contractService.getAllEffectSaleContract();
	}
	
	/**
	 * 获取生效 销售合同号和客户名称
	 * @return
	 */
	@RequestMapping(value = "getContractNoCustomer/{contractNo}", method = RequestMethod.GET)
	@ResponseBody
	public String getContractNoCustomer(@PathVariable("contractNo") String contractNo){
		return contractService.getContractNoCustomer(contractNo);
	}
	
	//销售发货选择
	@RequestMapping(value = "selectGoods", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectGoodsForDelivery(HttpServletRequest request) {
		Page<SaleContract> page = getPage(request);
		contractService.getEffectSaleContractSubs(page,new HashMap<String,Object>());
		return getEasyUIData(page);
	}
	@RequestMapping(value = "saleHistory/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SaleContract> getSaleContractHistory(@PathVariable(value="id") Long id){
		SaleContract saleContract = contractService.get(id);
		return contractService.getSaleContractHistory(saleContract.getSourceId(),saleContract.getId());
	}
	@RequestMapping(value = "showContractHistory/{id}", method = RequestMethod.GET)
	public String showContractHistory(@PathVariable(value="id") Long id,Model model){
		model.addAttribute("saleContractHis", contractService.get(id));
		return "sale/saleContractHistoryDetail";
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPDF/{id}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "saleContract.html", "MSYH.TTF", "style.css");
		Map<String, Object> exportMap = contractService.exportPDF(id);
		jtph.entrance(exportMap, response);
	}
	
	/**
	 * 查看销售相关信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "saleRelation/{id}", method = RequestMethod.GET)
	public String saleRelation(@PathVariable("id") Long id, Model model) throws JsonProcessingException {
		SaleContract saleContract = contractService.get(id);
		List<LhBills> billList = contractService.getBills(saleContract.getContractNo());
		List<ShipTrace> shipTracts = contractService.getShipTracts(saleContract.getContractNo());
		List<Map<String, Object>> saleDelarys = contractService.getSaleDelarys(saleContract.getContractNo());
		List<PurchaseContract> purchaseContracts = contractService.getPurchaseContracts(saleContract.getContractNo());
		List<SaleSettlement> saleSettlements = contractService.getSaleSettlements(saleContract.getContractNo());
		List<InputInvoice> inputInvoices = contractService.getInputInvoices(saleContract.getContractNo());
		List<PayApply> payApplys = contractService.getPayApplys(saleContract.getContractNo());
		List<SaleInvoice> saleInvoices = contractService.getSaleInvoices(saleContract.getContractNo());
		List<PaymentConfirm> paymentConfirms = contractService.getPaymentConfirms(saleContract.getContractNo());
		model.addAttribute("saleContract", saleContract);
		model.addAttribute("billList", changeToJson(billList));
		model.addAttribute("billListSize", billList.size());
		model.addAttribute("shipTracts", changeToJson(shipTracts));
		model.addAttribute("shipTractsSize", shipTracts.size());
		model.addAttribute("saleDelarys", changeToJson(saleDelarys));
		model.addAttribute("saleDelarysSize", saleDelarys.size());
		model.addAttribute("purchaseContracts", changeToJson(purchaseContracts));
		model.addAttribute("purchaseContractsSize", purchaseContracts.size());
		model.addAttribute("saleSettlements", changeToJson(saleSettlements));
		model.addAttribute("saleSettlementsSize", saleSettlements.size());
		model.addAttribute("inputInvoices", changeToJson(inputInvoices));
		model.addAttribute("inputInvoicesSize", inputInvoices.size());
		model.addAttribute("payApplys", changeToJson(payApplys));
		model.addAttribute("payApplysSize", payApplys.size());
		model.addAttribute("saleInvoices", changeToJson(saleInvoices));
		model.addAttribute("saleInvoicesSize", saleInvoices.size());
		model.addAttribute("paymentConfirms", changeToJson(paymentConfirms));
		model.addAttribute("paymentConfirmsSize", paymentConfirms.size());
		
		return "sale/saleRelation";
	}
	
	
	public <T> String changeToJson(List<T> o) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		String ss = objectMapper.writeValueAsString(o);
		ss.replace("\\r\\n", "");
		return ss.replace("\\r\\n", "");
	}
	
	@RequestMapping(value = "getShipNameById/{id}")
	@ResponseBody
	public String getShipNameById(@PathVariable("id") Long id) {
		return contractService.getShipNameById(id);
	}
}
