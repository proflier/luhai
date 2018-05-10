package com.cbmie.lh.purchase.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;
import com.cbmie.lh.purchase.entity.PurchaseGoodsIndex;
import com.cbmie.lh.purchase.service.PurchaseContractGoodsService;
import com.cbmie.lh.purchase.service.PurchaseContractService;
import com.cbmie.lh.purchase.service.PurchaseGoodsIndexService;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 采购合同-进口controller
 */
@Controller
@RequestMapping("purchase/purchaseContract")
public class PurchaseContractController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PurchaseContractService purchaseContractService;

	@Autowired
	private ActivitiService activitiService;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AccessoryService accessoryService;
	
	@Autowired
	private BaseInfoUtilService bius;
	
	@Autowired
	private PurchaseContractGoodsService purchaseContractGoodsService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@Autowired
	private BaseInfoUtilService baseInfoUtilService;
	
	@Autowired
	private PurchaseGoodsIndexService purchaseGoodsIndexService;
	
	@Autowired
	private BusinessPerssionService businessPerssionService;
	

	@Autowired
	private UserService userService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "purchase/contractList";
	}

	/**
	 * 获取采购合同-进口json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<PurchaseContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		//根据某一字段设置权限
		purchaseContractService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = purchaseContractService.search(page, filters);
		return getEasyUIData(page);
	}

	
	/**
	 * 获取采购合同-进口NoPermission
	 */
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsListNoPermission(HttpServletRequest request) {
		Page<PurchaseContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = purchaseContractService.searchNoPermission(page, filters);
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
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001001");
		String innerContract = sequenceMap.get("returnStr");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		PurchaseContract purchaseContract = new PurchaseContract();
		purchaseContract.setInnerContractNo(innerContract+"(OV)");
		purchaseContract.setCreateDate(new Date());
		purchaseContract.setUpdateDate(new Date());
		model.addAttribute("purchaseContract",purchaseContract);
		model.addAttribute("action", "create");
		return "purchase/contractForm";
	}

	/**
	 * 添加采购合同-进口
	 * 
	 * @param purchaseContract
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PurchaseContract purchaseContract, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		if (purchaseContractService.findByContractNo(purchaseContract) != null) {
			return setReturnData("fail", "采购合同号重复", purchaseContract.getId());
		}
		String  currnetSequence = null;
		if (purchaseContractService.findByInnerContractNo(purchaseContract) != null) {
			String innerContract = sequenceCodeService.getNextCode("001001").get("returnStr");
			purchaseContract.setInnerContractNo(innerContract);
			currnetSequence = innerContract;
		}
		User currentUser = UserUtil.getCurrentUser();
		purchaseContract.setCreaterNo(currentUser.getLoginName());
		purchaseContract.setCreaterName(currentUser.getName());
		purchaseContract.setCreateDate(new Date());
		String summary = "[采购合同]["+baseInfoUtilService.getAffiBaseInfoByCode(purchaseContract.getDeliveryUnit())+"][合同金额"+purchaseContract.getContractAmount()+DictUtils.getDictLabelByCode(purchaseContract.getCurrency(), "currency", "")+"]";
		purchaseContract.setSummary(summary);
		purchaseContract.setCreaterDept(currentUser.getOrganization().getOrgName());
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		purchaseContract.setRelLoginNames(themeMemberIds);
		if(purchaseContract.getId()==null){
			//调用获取下个sequence方法，将当前sequence保存
			sequenceCodeService.getNextSequence("001001");
		}
		purchaseContractService.save(purchaseContract);
		return setReturnData("success", currnetSequence!=null?"内部合同号重复，已自动生成不重复编码并保存":"保存成功", purchaseContract.getId(),currnetSequence);
	}

	/**
	 * 修改采购合同-进口跳转
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
		PurchaseContract purchaseContract = purchaseContractService.get(id);
		model.addAttribute("purchaseContract", purchaseContract);
		model.addAttribute("action", "update");
		Map<String, String> sequenceMap = sequenceCodeService.getNextCode("001001");
		if(sequenceMap.containsKey("keyWord")){
			model.addAttribute("keyWord", sequenceMap.get("keyWord"));
		}
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(purchaseContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		return "purchase/contractForm";
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
	public String update(@Valid @ModelAttribute @RequestBody PurchaseContract purchaseContract, Model model)
			throws JsonProcessingException {
		if (purchaseContractService.findByContractNo(purchaseContract) != null) {
			return setReturnData("fail", "采购合同号重复", purchaseContract.getId());
		}
		if (purchaseContractService.findByInnerContractNo(purchaseContract) != null) {
			return setReturnData("fail", "内部合同号重复", purchaseContract.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		purchaseContract.setUpdaterNo(currentUser.getLoginName());
		purchaseContract.setUpdaterName(currentUser.getName());
		purchaseContract.setUpdateDate(new Date());
		String summary = "[采购合同]["+baseInfoUtilService.getAffiBaseInfoByCode(purchaseContract.getDeliveryUnit())+"][合同金额"+purchaseContract.getContractAmount()+DictUtils.getDictLabelByCode(purchaseContract.getCurrency(), "currency", "")+"]";
		purchaseContract.setSummary(summary);
		//设置相关人员与默认值创建人
		String themeMemberIds = request.getParameter("themeMemberIds");
		purchaseContract.setRelLoginNames(themeMemberIds);
		purchaseContractService.update(purchaseContract);
		return setReturnData("success", "保存成功", purchaseContract.getId());
	}

	/**
	 * 变更采购合同-进口 跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "change/{id}", method = RequestMethod.GET)
	public String change(@PathVariable("id") Long id, Model model) throws IllegalAccessException, InvocationTargetException {
		PurchaseContract purchaseContractSource = purchaseContractService.getNoLoad(id);
		PurchaseContract purchaseContract = copyProperties(purchaseContractSource);
		model.addAttribute("purchaseContract", purchaseContract);
		model.addAttribute("action", "update");
		return "purchase/contractForm";
	}

	private PurchaseContract copyProperties(PurchaseContract purchaseContractSource) throws IllegalAccessException, InvocationTargetException {
		PurchaseContract purchaseContractDest = new PurchaseContract();  //主表
		List<PurchaseContractGoods> goodListDest = new ArrayList<PurchaseContractGoods>(); //子表
		BeanUtils.copyProperties(purchaseContractDest, purchaseContractSource);
		purchaseContractDest.setId(null);
		purchaseContractDest.setSourceId(purchaseContractSource.getSourceId()==null?purchaseContractSource.getId():purchaseContractSource.getSourceId());
		purchaseContractDest.setPid(purchaseContractSource.getId());
		purchaseContractDest.setPurchaseContractGoodsList(null);
		purchaseContractDest.setChangeState("2");
		purchaseContractDest.setProcessInstanceId(null);
		purchaseContractDest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		purchaseContractDest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		purchaseContractDest.setCreaterNo(currentUser.getLoginName());
		purchaseContractDest.setCreaterName(currentUser.getName());
		purchaseContractDest.setCreaterDept(currentUser.getOrganization().getOrgName());
		purchaseContractDest.setCreateDate(new Date());
		purchaseContractDest.setUpdateDate(new Date());
		purchaseContractSource.setChangeState("0");
		purchaseContractService.update(purchaseContractSource);
		purchaseContractService.save(purchaseContractDest);
		List<PurchaseContractGoods> goodsSources = purchaseContractSource.getPurchaseContractGoodsList();
		for(PurchaseContractGoods goodSource : goodsSources){
			List<PurchaseGoodsIndex> goodsIndexListDest = new ArrayList<PurchaseGoodsIndex>();
			List<PurchaseGoodsIndex> goodsIndexListSource = goodSource.getPurchaseGoodsIndexList();
			PurchaseContractGoods goodsDest = new PurchaseContractGoods();
			BeanUtils.copyProperties(goodsDest, goodSource);
			goodsDest.setPurchaseGoodsIndexList(null);
			goodsDest.setId(null);
			goodsDest.setPurchaseContractId(purchaseContractDest.getId());
			purchaseContractGoodsService.save(goodsDest);
			goodListDest.add(goodsDest);
			for(PurchaseGoodsIndex indexSource : goodsIndexListSource){
				PurchaseGoodsIndex indexDest = new PurchaseGoodsIndex();
				BeanUtils.copyProperties(indexDest, indexSource);
				indexDest.setId(null);
				indexDest.setParentId(goodsDest.getId().toString());
				purchaseGoodsIndexService.save(indexDest);
				goodsIndexListDest.add(indexDest);
			}
			goodsDest.setPurchaseGoodsIndexList(goodsIndexListDest);
		}
		purchaseContractDest.setPurchaseContractGoodsList(goodListDest );
		//更新附件信息
		 List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(purchaseContractSource.getId().toString(),"com_cbmie_lh_purchase_entity_PurchaseContract");
		 accessoryService.copyAttach(accessoryList,purchaseContractDest.getId()+"",null);
		return purchaseContractDest;
	}


	/**
	 * 删除采购合同-进口
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		PurchaseContract purchaseContract = purchaseContractService.get(id);
		if(purchaseContract.getPid()!=null){
			PurchaseContract purchaseContractOld = purchaseContractService.get(purchaseContract.getPid());
			purchaseContractOld.setChangeState("1");
			purchaseContractService.update(purchaseContractOld);
		}
		purchaseContractService.delete(id);
		
		return "success";
	}

	/**
	 * 查看采购合同-进口明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		PurchaseContract purchaseContract = purchaseContractService.get(id);
		model.addAttribute("purchaseContract", purchaseContract);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(purchaseContract.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "purchase/contractDetail";
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
		PurchaseContract purchaseContractBak = purchaseContractService.get(id);
		model.addAttribute("purchaseContractBak", purchaseContractBak);
		Map<String, String > mapValue = businessPerssionService.getUserNamesString(purchaseContractBak.getRelLoginNames());
		//相关业务员id设置
		model.addAttribute("themeMemberIds", mapValue.get("themeMemberIds"));
		//相关业务员回显
		model.addAttribute("themeMembers", mapValue.get("themeMembers"));
		model.addAttribute("action", "view");
		return "purchase/contractBakDetail";
	}

	/**
	 * 修改采购合同运行状态--跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "closeOrOpen/{id}", method = RequestMethod.GET)
	public String closeOrOpen(@PathVariable("id") Long id, Model model) {
		model.addAttribute("purchaseContract", purchaseContractService.get(id));
		model.addAttribute("action", "closeOrOpen");
		return "purchase/contractDetail";
	}

	/**
	 * 修改采购合同运行状态
	 * 
	 * @param purchaseContract
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "closeOrOpen", method = RequestMethod.POST)
	@ResponseBody
	public String closeOrOpen(@Valid @ModelAttribute @RequestBody PurchaseContract purchaseContract, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		purchaseContract.setUpdaterNo(currentUser.getLoginName());
		purchaseContract.setUpdaterName(currentUser.getName());
		purchaseContract.setUpdateDate(new Date());
		purchaseContractService.update(purchaseContract);
		return setReturnData("success", "保存成功", purchaseContract.getId());
	}

	@ModelAttribute
	public void getPurchaseContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("purchaseContract", purchaseContractService.get(id));
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
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = UserUtil.getCurrentUser();
		PurchaseContract purchaseContract = purchaseContractService.get(id);
		if(!(purchaseContract.getPurchaseContractGoodsList().size()>0)){
			return "采购详情为空，不能提交申请！";
		}
		if(!(StringUtils.isNotBlank(purchaseContract.getContractQuantity())&&Double.valueOf(purchaseContract.getContractQuantity())>0)){
			return "合同数量为0不能提交申请！";
		}
		if(!(StringUtils.isNotBlank(purchaseContract.getContractAmount())&&Double.valueOf(purchaseContract.getContractAmount())>0)){
			return "合同金额为0不能提交申请！";
		}
		// purchaseContract.setUserId((user.getLoginName()).toString());
		//设置状态 已提交
		purchaseContract.setState("3");
		String summary = "[采购合同]["+baseInfoUtilService.getAffiBaseInfoByCode(purchaseContract.getDeliveryUnit())+"][合同金额"+purchaseContract.getContractAmount()+DictUtils.getDictLabelByCode(purchaseContract.getCurrency(), "currency", "")+"]";
		purchaseContract.setSummary(summary);
		purchaseContractService.save(purchaseContract);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("contractCategory", purchaseContract.getContractCategory());
		variables.put("changeState", purchaseContract.getChangeState());
		variables.put("userId", purchaseContract.getUserId());
		variables.put("businessManager", purchaseContract.getBusinessManager());
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(PurchaseContract.class,purchaseContract);
		if(processKey!=null){
			result = activitiService.startWorkflow(purchaseContract, processKey, variables,
					(user.getLoginName()).toString());
		}else{
			return "流程未部署、不存在或存在多个，请联系管理员！";
		}
		try {
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
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
				PurchaseContract purchaseContract = purchaseContractService.get(id);
				purchaseContract.setProcessInstanceId(null);
				
				if(purchaseContract.getPid()!=null){
					//设置状态  变更中
					purchaseContract.setChangeState("2");
				}
				//设置状态  草稿
				purchaseContract.setState("2");
				purchaseContractService.save(purchaseContract);
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
	 * 采购协议配置跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadProtocol", method = RequestMethod.GET)
	public String toAgencyConfig() {
		return "purchase/loadProtocol";
	}
	
	@RequestMapping(value = "saveOnChangeGoods/{id}/{contractQuantity:.*}/{contractAmount:.*}")
	@ResponseBody
	public void saveOnChangeGoods(@PathVariable("id") Long id, @PathVariable("contractQuantity")String contractQuantity,@PathVariable("contractAmount")String contractAmount){
		PurchaseContract purchaseContract = purchaseContractService.get(id);
		purchaseContract.setContractAmount(contractAmount);
		purchaseContract.setContractQuantity(contractQuantity);
		purchaseContractService.update(purchaseContract);
	}

	/**
	 * 高级搜索
	 * 
	 * @return
	 */
	@RequestMapping(value = "superSearch", method = RequestMethod.GET)
	public String superSearch() {
		return "purchase/superSearch";
	}
	
	
	/**
	 * 获取备份信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getPurchaseContractBak/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseContract> getPurchaseContractBak(@PathVariable("id") long id){
		return purchaseContractService.getPurchaseContractBak(id);
	}
	
	/**
	 * 弹窗加载整船销售合同
	 */
	@RequestMapping(value = "loadSaleContract", method = RequestMethod.GET)
	public String loadSaleContract() {
		return "purchase/loadSaleContract";
	}
	
	
	/**
	 * 获取类型为整船的销售合同
	 */
	@RequestMapping(value = "getSaleShip", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getSaleShip(HttpServletRequest request) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		data = purchaseContractService.getSaleShip(showParams(request));
		return data;
	}
	
	
	/**
	 * 添加采购销售关联关系
	 */
	@RequestMapping(value = "saveSaleRelation/{purchaseNo}/{saleGoodsIds}", method = RequestMethod.GET)
	@ResponseBody
	public String saveSaleRelation(@PathVariable("purchaseNo") String purchaseNo,@PathVariable("saleGoodsIds") String saleGoodsIds) {
		purchaseContractService.saveSaleGoods(purchaseNo,saleGoodsIds);
		return "success";
	}
	

	/**
	 * 根据request获取过滤参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, Object> showParams(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		Set<Map.Entry<String, Object>> set = map.entrySet();
		for (Map.Entry entry : set) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		return map;
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPDF/{contractNo}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("contractNo") String contractNo, HttpServletRequest request, HttpServletResponse response) {
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "orderShipContract.html", "MSYH.TTF", "style.css");
		Map<String, Object> exportMap = purchaseContractService.exportPDF(contractNo);
		jtph.entrance(exportMap, response);
	}
	
	@RequestMapping(value = "getByNo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PurchaseContract getByNo(@RequestParam("purchaseNo") String purchaseNo) {
		return purchaseContractService.findByContractNo(purchaseNo);
	}
	
	@RequestMapping(value = "getById/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PurchaseContract getByNo(@PathVariable("id") Long id) {
		return purchaseContractService.getNoLoad(id);
	}
	
}
