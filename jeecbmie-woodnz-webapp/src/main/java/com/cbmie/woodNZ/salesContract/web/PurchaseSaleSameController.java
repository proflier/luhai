package com.cbmie.woodNZ.salesContract.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.ActivitiException;
import org.apache.commons.lang3.StringEscapeUtils;
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

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkBakService;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkMxBakService;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkMxService;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkService;
import com.cbmie.woodNZ.offerManagement.entity.ProflitLossAccounting;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSame;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSameSub;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.cbmie.woodNZ.salesContract.service.PurchaseSaleSameService;
import com.cbmie.woodNZ.salesContract.service.PurchaseSaleSameSubService;
import com.cbmie.woodNZ.salesContract.service.SaleContractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 采销同批controller
 */
@Controller
@RequestMapping("sale/purchaseSaleSame")
public class PurchaseSaleSameController extends BaseController{
	 
	
	@Autowired
	private PurchaseSaleSameService purchaseSaleSameService;
	
	@Autowired
	private SaleContractService saleContractService;
	
	@Autowired
	private WoodCghtJkService woodCghtJkService;
	
	@Autowired
	private PurchaseSaleSameSubService purchaseSaleSameSubService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private WoodCghtJkBakService woodCghtJkBakService;
	
	@Autowired
	private WoodCghtJkMxService woodCghtJkMxService;
	
	@Autowired
	private WoodCghtJkMxBakService woodCghtJkMxBakService;
	
	@Autowired
	private  HttpServletRequest request;
	
//	/** 采购合同list(临时变量)* */
//	public List<WoodCghtJk> purchaseContractList = new ArrayList<WoodCghtJk>();
//	/** 销售合同list(临时变量)* */
//	public List<WoodSaleContract> saleContractList = new ArrayList<WoodSaleContract>();
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "saleContract/purchaseSaleSameList";
	}
	 
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> outList(HttpServletRequest request) throws JsonProcessingException {
		try {
			Page<PurchaseSaleSame> page = getPage(request);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = purchaseSaleSameService.search(page, filters);
			List<WoodSaleContract> list = purchaseSaleSameService.getCount4Cancel();//获取废除的销售合同
			List<WoodCghtJk> listCg = purchaseSaleSameService.getCount4CancelCg();//获取废除的采购合同
			if(list.size() > 0){
				for(WoodSaleContract s:list){
					List<PurchaseSaleSameSub> list1 = purchaseSaleSameService.judge4Cancel(s.getId());//判断生效采销合同中是否有此销售合同号
					if(list1.isEmpty() || list1.size() == 0){
						purchaseSaleSameSubService.updateCxStateCancel(s);//设置合同状态为未选择
					}
				} 
			}
			if(listCg.size() > 0){
				for(WoodCghtJk c:listCg){ 
					List<PurchaseSaleSameSub> list2 = purchaseSaleSameService.judge4CancelCg(c.getId());//判断生效采销合同中是否有此采购合同号
					if(list2.isEmpty() || list2.size() == 0){ 
						purchaseSaleSameSubService.updateCxStateCancel(c);//设置合同状态为未选择
					}
				} 
			}
			return getEasyUIData(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加采销同批跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		PurchaseSaleSame info = new PurchaseSaleSame();
		User currentUser = UserUtil.getCurrentUser();
		info.setCreaterNo(currentUser.getLoginName());
		info.setCreaterName(currentUser.getName());
		info.setCreaterDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("purchaseSaleSame", info);
		model.addAttribute("action", "create");
		return "saleContract/purchaseSaleSameForm";
	}

	/**
	 * 添加采销同批
	 * 
	 * @param port
	 * @param model
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid @ModelAttribute @RequestBody PurchaseSaleSame purchaseSaleSame,Model model,
			@RequestParam("saleContractJson") String saleContractJson,
			@RequestParam("purchaseContractJson") String purchaseContractJson) throws JsonProcessingException {
		try {
			String validate = validateContract(saleContractJson,purchaseContractJson,purchaseSaleSame);
			if(validate.equals("1")){
				return setReturnData("fail","保存失败, 请选择合同！",purchaseSaleSame.getId());
			}
			if(validate.equals("2")){
				return setReturnData("fail","保存失败, 不允许同时选择多个采购和销售合同！",purchaseSaleSame.getId());
			}
			if(purchaseSaleSame !=null && purchaseSaleSame.getContent().length() > 3032){
				return setReturnData("fail","保存失败, 正文字数超过1000字！",purchaseSaleSame.getId());
			}
			purchaseSaleSame.setSummary("采销同批-[" + purchaseSaleSame.getKeynote() + "]");
			User currentUser = UserUtil.getCurrentUser();
			purchaseSaleSame.setCreaterNo(currentUser.getLoginName());
			purchaseSaleSame.setCreaterName(currentUser.getName());
			purchaseSaleSame.setCreaterDept(currentUser.getOrganization().getOrgName());
			purchaseSaleSame.setCreateDate(new Date());
			purchaseSaleSameService.save(purchaseSaleSame);
			if(StringUtils.isNotBlank(purchaseContractJson) && StringUtils.isNotBlank(saleContractJson)){
				purchaseSaleSameSubService.save(purchaseSaleSame,purchaseContractJson, saleContractJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if (Boolean.valueOf(request.getParameter("apply"))) {
//			apply(purchaseSaleSame.getId());
//		}
		return setReturnData("success","保存成功",purchaseSaleSame.getId());
	}
	
	//采销同批有两种情况：一个采购合同对应多个销售合同； 一个销售合同对应多个采购合同
	public String validateContract(String saleContractJson,String purchaseContractJson,PurchaseSaleSame purchaseSaleSame){
		// 转成标准的json字符串
		saleContractJson = StringEscapeUtils.unescapeHtml4(saleContractJson);
		purchaseContractJson = StringEscapeUtils.unescapeHtml4(purchaseContractJson);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<WoodCghtJk> purchaseContractList = new ArrayList<WoodCghtJk>();
		List<WoodSaleContract> saleContractList = new ArrayList<WoodSaleContract>();
		try {
			//采购合同json转换
			JsonNode jsonNodePurchase = objectMapper.readTree(purchaseContractJson);
			for (JsonNode jn : jsonNodePurchase) {
				WoodCghtJk woodPurchaseContract = objectMapper.readValue(jn.toString(), WoodCghtJk.class);
				purchaseContractList.add(woodPurchaseContract);
			}
			//销售合同json转换
			JsonNode jsonNodeSale = objectMapper.readTree(saleContractJson);
			for (JsonNode jn : jsonNodeSale) {
				WoodSaleContract woodSaleContract = objectMapper.readValue(jn.toString(), WoodSaleContract.class);
				saleContractList.add(woodSaleContract);
			}
			if(purchaseContractList.size()==0 || saleContractList.size()==0 ){
				return "1";
			}
			if(purchaseContractList.size()>1 && saleContractList.size()>1 ){
				return "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 修改采销同批跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		PurchaseSaleSame same = purchaseSaleSameService.get(id);
		model.addAttribute("purchaseSaleSame", same);
		model.addAttribute("action", "update");
		return "saleContract/purchaseSaleSameForm";
	}

	/**
	 * 去除重复
	 * @param request
	 * @return
	 */
	public List<WoodCghtJk>  removeDuplicateCg(List<WoodCghtJk> list) {
	      HashSet h = new HashSet(list);
	      list.clear();
	      list.addAll(h);
	      return list;
	}
	
	/**
	 * 去除重复
	 * @param request
	 * @return
	 */
	public List<WoodSaleContract>  removeDuplicateSale(List<WoodSaleContract> list) {
	      HashSet h = new HashSet(list);
	      list.clear();
	      list.addAll(h);
	      return list;
	}
	
	/**
	 * 修改采销同批
	 * 
	 * @param purchaseSaleSame
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PurchaseSaleSame purchaseSaleSame,Model model,
			@RequestParam("saleContractJson") String saleContractJson,
			@RequestParam("purchaseContractJson") String purchaseContractJson) throws JsonProcessingException {
		String validate = validateContract(saleContractJson,purchaseContractJson,purchaseSaleSame);
		if(validate.equals("1")){
			return setReturnData("fail","保存失败, 请选择合同！",purchaseSaleSame.getId());
		}
		if(validate.equals("2")){
			return setReturnData("fail","保存失败, 不允许同时选择多个采购和销售合同！",purchaseSaleSame.getId());
		}
		if(purchaseSaleSame !=null && purchaseSaleSame.getContent().length() > 3032){
			return setReturnData("fail","保存失败, 正文字数超过1000字！",purchaseSaleSame.getId());
		}
		validateContract(saleContractJson,purchaseContractJson,purchaseSaleSame);
		User currentUser = UserUtil.getCurrentUser();
		purchaseSaleSame.setUpdaterNo(currentUser.getLoginName());
		purchaseSaleSame.setUpdaterName(currentUser.getName());
		purchaseSaleSame.setUpdateDate(new Date());
		purchaseSaleSame.setSummary("采销同批-[" + purchaseSaleSame.getKeynote() + "]");
		purchaseSaleSameService.update(purchaseSaleSame);
		if(StringUtils.isNotBlank(purchaseContractJson) && StringUtils.isNotBlank(saleContractJson)){
			try {
				purchaseSaleSameSubService.save(purchaseSaleSame,purchaseContractJson, saleContractJson);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		if (Boolean.valueOf(request.getParameter("apply"))) {
//			apply(purchaseSaleSame.getId());
//		}
		return setReturnData("success","保存成功",purchaseSaleSame.getId());
	}

	/**
	 * 删除采销同批
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		PurchaseSaleSame same = purchaseSaleSameService.get(id);
		List<PurchaseSaleSameSub> list = same.getSaleContractSubList();
		for(PurchaseSaleSameSub sub:list){
			purchaseSaleSameSubService.updateCxStateCancel(sub.getWoodSaleContract());
			purchaseSaleSameSubService.updateCxStateCancel(sub.getWoodCghtJk());
		}
		purchaseSaleSameService.delete(id);
		return "success";
	}
	
	/**
	 * 采销同批明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("purchaseSaleSame", purchaseSaleSameService.get(id));
		model.addAttribute("action", "detail");
		return "saleContract/purchaseSaleSameDetail";
	}
	
	
	@ModelAttribute
	public void setPurchaseSaleSame(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("purchaseSaleSame", purchaseSaleSameService.get(id));
		}
	}
	
	
	/**
	 * 销售合同页面跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toSaleList", method = RequestMethod.GET)
	public String toSaleList() {
//		saleContractList.clear();//清空列表
		return "saleContract/allSaleContractListForm";
	}
	
	/**
	 * 获取销售合同数据列表
	 * 选择审批通过，状态为运行中，转口类型的销售合同
	 * @param id  采销同批主表id
	 */
	@RequestMapping(value="getAllSaleList/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAllSaleList(HttpServletRequest request,@PathVariable("id") Long mainId) {
		Page<WoodSaleContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = saleContractService.search(page, filters);
		PurchaseSaleSame main = purchaseSaleSameService.get(mainId);
		List<WoodSaleContract> result = new ArrayList<WoodSaleContract>();
		List<WoodSaleContract> resultPage = new ArrayList<WoodSaleContract>();
		if(main.getId() != null && main.getId() > 0){
			List<PurchaseSaleSameSub> list = main.getSaleContractSubList();
			for(PurchaseSaleSameSub sub:list){
				result.add(sub.getWoodSaleContract());
			}
			removeDuplicateSale(result);
		}
		resultPage = page.getResult();
		if(result != null && page.getTotalCount() < 10){
			resultPage.addAll(result);
		}
		resultPage = removeDuplicateSale(resultPage);
		page.setResult(resultPage);
		page.setTotalCount(page.getTotalCount()+result.size());
		return getEasyUIData(page);
	}
	
	
	/**
	 * 选择销售合同后执行此方法
	 * 
	 * @param saleArray 选择的销售合同主表id数组
	 * @param mainId  采销同批主表id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "setSaleContract", method = RequestMethod.POST)
	@ResponseBody
	public String setSaleContract(Model model,@RequestParam("saleArray") List<String> saleArray,
			@RequestParam("mainId") String mainId) throws JsonProcessingException {
		List<WoodSaleContract> listCache = new ArrayList<WoodSaleContract>();
		for(String id:saleArray){
			WoodSaleContract c = saleContractService.get(Long.valueOf(id));
			WoodSaleContract contract = new WoodSaleContract();
			contract.setId(c.getId());
			contract.setContractNo(c.getContractNo());
			contract.setDealNo(c.getDealNo());
			contract.setSeller(c.getSeller());
			contract.setPurchaser(c.getPurchaser());
			contract.setTotalMoney(c.getTotalMoney());
			contract.setNumUnit(c.getNumUnit());//数量单位
			contract.setTotal(c.getTotal());//数量
			listCache.add(contract);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", "success");
		map.put("returnData", listCache);
		map.put("returnMsg", "");
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
	}
	

	/**
	 * 判断销售合同是否已经在采销同批选择过，如果选择过，不允许选择，返回true,自己主表中可以选择之前选过的
	 * @param saleId
	 * @return boolean
	 */
	public boolean uniqueSale(String saleId,String mainId){
		List<PurchaseSaleSameSub> result = purchaseSaleSameSubService.findBySaleId(saleId,mainId);
		if(result.size() > 0){
			return true;//销售合同已经存在
		}else{
			return false;
		}
	}
	
	/**
	 * 判断采购合同是否已经在采销同批选择过，如果选择过，不允许选择，返回true,自己主表中可以选择之前选过的
	 * @param saleId
	 * @return boolean
	 */
	public boolean uniquePurchase(String purchaseId,String mainId){
		List<PurchaseSaleSameSub> result = purchaseSaleSameSubService.findByPurchaseId(purchaseId,mainId);
		if(result.size() > 0){
			return true;//销售合同已经存在
		}else{
			return false;
		}
	}
	
	/**
	 * 获取本身销售合同列表
	 * @param id 采销主表id，如果id为0，代表主表为空
	 */
	@RequestMapping(value = "getOwnSaleList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<WoodSaleContract> getOwnSaleList(HttpServletRequest request,@PathVariable("id") String id)  {
		if(!id.equals("0")){
			PurchaseSaleSame same = purchaseSaleSameService.get(Long.valueOf(id));
			List<PurchaseSaleSameSub> subList = same.getSaleContractSubList();
			List<WoodSaleContract> saleCacheList = new ArrayList<WoodSaleContract>();
			for(PurchaseSaleSameSub p:subList){
				saleCacheList.add(p.getWoodSaleContract());
			}
			saleCacheList = removeDuplicateSale(saleCacheList);
			return saleCacheList;
		}
		else {
			return  new ArrayList<WoodSaleContract>();
		}
	}
	
	
	
	
	/**
	 * 采购合同页面跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toPurchaseList", method = RequestMethod.GET)
	public String toPurchaseList() {
		return "saleContract/allPurchaseContractListForm";
	}
	
	/**
	 * 获取采购合同数据列表
	 * 选择审批通过，状态为运行中，转口类型的采购合同
	 * @param id 采销同批主表id
	 */
	@RequestMapping(value="getAllPurchaseList/{id}",method = RequestMethod.GET)
	@ResponseBody
	public  Map<String, Object> getAllPurchaseList(HttpServletRequest request,@PathVariable("id") Long mainId ) {
		Page<WoodCghtJk> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = woodCghtJkService.search(page, filters);
		PurchaseSaleSame main = purchaseSaleSameService.get(mainId);
		List<WoodCghtJk> result = new ArrayList<WoodCghtJk>();
		List<WoodCghtJk> resultPage = new ArrayList<WoodCghtJk>();
		if(main.getId() != null && main.getId() > 0){
			List<PurchaseSaleSameSub> list = main.getSaleContractSubList();
			for(PurchaseSaleSameSub sub:list){
				result.add(sub.getWoodCghtJk());
			}
			removeDuplicateCg(result);
		}
		resultPage = page.getResult();
		if(result != null && resultPage.size() < 10){
			resultPage.addAll(result);
		}
		resultPage = removeDuplicateCg(resultPage);
		page.setResult(resultPage);
		page.setTotalCount(page.getTotalCount()+result.size());
		return getEasyUIData(page);
	}
	
	
	/**
	 * 选择采购合同后执行此方法
	 * 
	 * @param purchaseArray
	 * @param mainId 采购同批主表id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "setPurchaseContract", method = RequestMethod.POST)
	@ResponseBody
	public String setPurchaseContract(Model model,@RequestParam("purchaseArray") List<String> purchaseArray,
			@RequestParam("mainId") String mainId) throws JsonProcessingException {
		List<WoodCghtJk> listCache = new ArrayList<WoodCghtJk>();
		for(String id:purchaseArray){
			WoodCghtJk c = woodCghtJkService.get(Long.valueOf(id));
			WoodCghtJk contract = new WoodCghtJk();
			contract.setId(c.getId());
			contract.setHth(c.getHth());
			contract.setXyh(c.getXyh());
			contract.setGhdw(c.getGhdw());
			contract.setHk(c.getHk());
			contract.setDhzl(c.getDhzl());//数量
			contract.setSldw(c.getSldw());//数量单位
			listCache.add(contract);
		}
		//获取盈亏核算列表
		List<ProflitLossAccounting> proflitList = new ArrayList<ProflitLossAccounting>();
		for(WoodCghtJk contract:listCache){
			List<ProflitLossAccounting> list = purchaseSaleSameService.findProflitLossByContractNO(contract.getHth());//通过采购合同号找到盈亏核算列表
			proflitList.addAll(list);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", "success");
		map.put("returnPurchaseData", listCache);
		map.put("returnProflitData", proflitList);
		map.put("returnMsg", "");
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
	}
	
	/**
	 * 获取本身采购合同列表
	 * @param id 采销主表id，如果id为0，代表主表为空
	 */
	@RequestMapping(value = "getOwnPurchaseList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<WoodCghtJk> getOwnPurchaseList(HttpServletRequest request,@PathVariable("id") String id)  {
		if(!id.equals("0")){
			PurchaseSaleSame same = purchaseSaleSameService.get(Long.valueOf(id));
			List<PurchaseSaleSameSub> subList = same.getSaleContractSubList();
			List<WoodCghtJk> purchaseCacheList = new ArrayList<WoodCghtJk>();
			for(PurchaseSaleSameSub p:subList){
				purchaseCacheList.add(p.getWoodCghtJk());
			}
			purchaseCacheList = removeDuplicateCg(purchaseCacheList);
			return purchaseCacheList;
		}
		else {
			return new ArrayList<WoodCghtJk>();
		}
	}
	
	
	/**
	 * 获取本身采购盈亏核算列表
	 *  @param id 采销主表id，如果id为0，代表主表为空
	 */
	@RequestMapping(value = "getOwnProflitLoss/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProflitLossAccounting> getOwnProflitLoss(HttpServletRequest request,@PathVariable("id") String id)  {
		if(!id.equals("0")){
			PurchaseSaleSame same = purchaseSaleSameService.get(Long.valueOf(id));
			List<ProflitLossAccounting> proflitList = new ArrayList<ProflitLossAccounting>();
			List<WoodCghtJk> cgList = new ArrayList<WoodCghtJk>();
			List<PurchaseSaleSameSub> subList = same.getSaleContractSubList();
			for(PurchaseSaleSameSub s:subList){
				cgList.add(s.getWoodCghtJk());
			}
			cgList = removeDuplicateCg(cgList);
			for(WoodCghtJk p:cgList){
				List<ProflitLossAccounting> list = 
						purchaseSaleSameService.findProflitLossByContractNO(p.getHth());//通过采购合同号找到盈亏核算列表
				proflitList.addAll(list);
			}
			return proflitList;
		}
		else {
			return new ArrayList<ProflitLossAccounting>();
		}
	}
	
	/**
	 * 销售合同明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getSaleDetail/{id}", method = RequestMethod.GET)
	public String getSaleDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleContract", saleContractService.get(id));
		return "saleContract/saleContractDetail";
	}
	
	/**
	 * 采购合同明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getPurchaseDetail/{id}", method = RequestMethod.GET)
	public String getPurchaseDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodCghtJk", woodCghtJkService.get(id));
		model.addAttribute("woodCghtJkBakList", woodCghtJkBakService.findAll(id));
		model.addAttribute("woodCghtJkMxBakList", woodCghtJkMxBakService.findAll(id));
		return "saleContract/woodCghtJKDetail";
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
			User user = UserUtil.getCurrentUser();
			PurchaseSaleSame purchaseSaleSame = purchaseSaleSameService.get(id);
//			purchaseSaleSame.setUserId((user.getLoginName()).toString());
			purchaseSaleSame.setState("已提交");
			purchaseSaleSameService.save(purchaseSaleSame);
			Map<String, Object> variables = new HashMap<String, Object>();
			Map<String,Object> result = new HashMap<String,Object>();
			result = activitiService.startWorkflow(purchaseSaleSame, "wf_woodPurchaseSaleSame", variables, (user.getLoginName()).toString());
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
				PurchaseSaleSame purchaseSaleSame = purchaseSaleSameService.get(id);
				purchaseSaleSame.setProcessInstanceId(null);
				purchaseSaleSame.setState("草稿");
				purchaseSaleSameService.save(purchaseSaleSame);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
}