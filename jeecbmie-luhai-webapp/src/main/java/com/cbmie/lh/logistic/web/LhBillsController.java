package com.cbmie.lh.logistic.web;


import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.LhBillsGoods;
import com.cbmie.lh.logistic.entity.LhBillsGoodsIndex;
import com.cbmie.lh.logistic.service.LhBillsGoodsIndexService;
import com.cbmie.lh.logistic.service.LhBillsGoodsService;
import com.cbmie.lh.logistic.service.LhBillsService;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;
import com.cbmie.lh.purchase.entity.PurchaseGoodsIndex;
import com.cbmie.lh.purchase.service.PurchaseContractService;
import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.service.InStockGoodsService;
import com.cbmie.lh.stock.service.InStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 到单信息controller
 */
@Controller
@RequestMapping("logistic/bills")
public class LhBillsController extends BaseController{
	

	@Autowired
	private LhBillsService billsService;
	
	@Autowired
	private PurchaseContractService purchaseContractService;
	@Autowired
	private LhBillsGoodsService billsGoodsService;
	@Autowired
	private LhBillsGoodsIndexService billsGoodsIndexService;
	
	@Autowired
	private InStockGoodsService inStockGoodsService;

	@Autowired
	private InStockService inStockService;
//	@Autowired
//	private BillsGoodsService billsGoodsService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;

	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/billsList";
	}
	
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<LhBills> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		billsService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = billsService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="jsonNoPermission",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityListNoPermission(HttpServletRequest request) {
		Page<LhBills> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = billsService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加到单跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		try {
			LhBills lhBills = new LhBills();
			User currentUser = UserUtil.getCurrentUser();
			lhBills.setCreaterNo(currentUser.getLoginName());
			lhBills.setCreaterName(currentUser.getName());
			lhBills.setCreaterDept(currentUser.getOrganization().getOrgName());
			String billNo = sequenceCodeService.getNextCode("001015").get("returnStr");
			lhBills.setBillNo(billNo);
			model.addAttribute("lhBills", lhBills);
			model.addAttribute("action", "create");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logistic/billsForm";
	}
	
	/**
	 * 添加到单
	 * 
	 * @param port
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid LhBills lhBills, Model model,@RequestParam("purchaseContractIdsOld") String purchaseContractIdsOld) throws JsonProcessingException {
		try {
			String  currentSequence = null;
			while (billsService.validateBillNo(lhBills.getBillNo(),lhBills.getId())) {
				currentSequence = sequenceCodeService.getNextSequence("001015").toString();
				lhBills.setBillNo(currentSequence);
			}
			lhBills.setCreateDate(new Date());
			billsService.save(lhBills);
			if(currentSequence==null){
				sequenceCodeService.getNextSequence("001015");
			}
			if(lhBills.getPurchaseContractIds()!=null && !lhBills.getPurchaseContractIds().equals(purchaseContractIdsOld)){
				deleteGoodsAndIndex(lhBills);
				fillGoodsAndIndex(lhBills);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("returnFlag", "success");
			map.put("returnId", lhBills.getId());
			if(currentSequence!=null){
				map.put("currentSequence", currentSequence);
			}
			map.put("returnMsg", currentSequence==null?"保存成功":"提单号重复，已自动生成不重复编码并保存");
			map.put("numbers", lhBills.getNumbers());
			map.put("invoiceAmount", lhBills.getInvoiceAmount());
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",lhBills.getId());
		}
	}
	/**
	 * 修改到单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		LhBills lhBills = billsService.get(id);
		model.addAttribute("lhBills", lhBills);
		model.addAttribute("purchaseContractIdsOld", lhBills.getPurchaseContractIds());
		model.addAttribute("action", "update");
		return "logistic/billsForm";
	}
	
	@ModelAttribute
	public void getBills(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			LhBills lhBills = billsService.get(id);
			model.addAttribute("purchaseContractIdsOld", lhBills.getPurchaseContractIds());
			model.addAttribute("lhBills", lhBills);
		}
	}

	/**
	 * 修改到单
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody LhBills lhBills,Model model,@RequestParam("purchaseContractIdsOld") String purchaseContractIdsOld) throws JsonProcessingException {
		if (billsService.validateBillNo(lhBills.getBillNo(),lhBills.getId())) {
			return setReturnData("fail","编码重复",lhBills.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		lhBills.setUpdaterNo(currentUser.getLoginName());
		lhBills.setUpdaterName(currentUser.getName());
		lhBills.setUpdateDate(new Date());
		if(lhBills.getPurchaseContractIds()!=null && !lhBills.getPurchaseContractIds().equals(purchaseContractIdsOld)){
			deleteGoodsAndIndex(lhBills);
			fillGoodsAndIndex(lhBills);
		}
		billsService.update(lhBills);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", "success");
		map.put("returnId", lhBills.getId());
		map.put("returnMsg", "保存成功");
		map.put("numbers", lhBills.getNumbers());
		map.put("invoiceAmount", lhBills.getInvoiceAmount());
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
	}
	/**
	 * 删除good和index
	 * 
	 * @return
	 */
	public void deleteGoodsAndIndex(LhBills bills){
		List<LhBillsGoods> goodList = bills.getGoodsList();
		for(LhBillsGoods goods : goodList){
			billsGoodsIndexService.deleteEntityByParentId(goods.getId().toString());
		}
		billsGoodsService.deleteEntityByParentId(bills.getId().toString());
		bills.getGoodsList().clear();
	}
	
	/**
	 * 更新good和index
	 * 
	 * @return
	 */
	public void fillGoodsAndIndex(LhBills bills){
		String contractIds = bills.getPurchaseContractIds();
		if(StringUtils.isNotBlank(contractIds)){
			String[] ids = contractIds.split(",");
			for(String id : ids){
				PurchaseContract ct = purchaseContractService.findByInnerContractNo(id);
				if(ct!=null){
					fillGoodsAndIndex(ct,bills);
				}
			}
			
		}
		Double goodsQuantity_t = 0.0;
		Double amount_t = 0.0;
		List<LhBillsGoods> goods = bills.getGoodsList();
		for(LhBillsGoods billsGoods:goods){
			if(StringUtils.isNotBlank(billsGoods.getGoodsQuantity())){
				goodsQuantity_t += Double.parseDouble(billsGoods.getGoodsQuantity());
			}
			if(StringUtils.isNotBlank(billsGoods.getAmount())){
				amount_t += Double.parseDouble(billsGoods.getAmount());
			}
		}
		bills.setInvoiceAmount(amount_t);
		bills.setNumbers(goodsQuantity_t);
	}
	/**
	 * 更新good和index
	 * 
	 * @return
	 */
	public void fillGoodsAndIndex(PurchaseContract contract,LhBills bills){
		List<PurchaseContractGoods> goods = contract.getPurchaseContractGoodsList();
		for(PurchaseContractGoods good : goods){
			LhBillsGoods billsGood = new LhBillsGoods();
			bills.getGoodsList().add(billsGood);
			try {
				BeanUtils.copyProperties(billsGood, good);
				billsGood.setPurchaseContractNo(contract.getPurchaseContractNo());
				billsGood.setInnerContractNo(contract.getInnerContractNo());
				billsGood.setParentId(bills.getId().toString());
				List<PurchaseGoodsIndex> indexList = good.getPurchaseGoodsIndexList();
				billsGood.setId(null);
				billsGoodsService.save(billsGood);
				for(PurchaseGoodsIndex index : indexList){
					LhBillsGoodsIndex bill_good_index = new LhBillsGoodsIndex();
					BeanUtils.copyProperties(bill_good_index, index);
					bill_good_index.setParentId(billsGood.getId().toString());
					bill_good_index.setId(null);
					billsGoodsIndexService.save(bill_good_index);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 删除到单
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		billsService.delete(id);
//		deleteGoodsAndIndex(billsService.get(id));
		return setReturnData("success","删除成功",id);
	}
	
	/**
	 * 到单明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		LhBills lhBills = billsService.get(id);
		model.addAttribute("action", "view");
		model.addAttribute("lhBills", lhBills);
		model.addAttribute("purchaseContractIdsOld", lhBills.getPurchaseContractIds());
		return "logistic/billsDetail";
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
		return "logistic/billsPurchaseListForm";
	}

	/**
	 * 选择采购合同后执行此方法
	 * 
	 * @param purchaseArray
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "setGoods", method = RequestMethod.POST)
	@ResponseBody
	public String setGoods(Model model,@RequestParam("purchaseArray") List<String> purchaseArray) throws JsonProcessingException {
		String purchaseContractIds = "";
		Iterator<String> it = purchaseArray.iterator();
		while(it.hasNext()){
			purchaseContractIds=purchaseContractIds+it.next()+",";
		}
		if(purchaseContractIds.endsWith(",")){
			purchaseContractIds = purchaseContractIds.substring(0, purchaseContractIds.length()-1);
		}
//		PurchaseContract param = new PurchaseContract();
//		param.setPurchaseContractNo(purchaseArray.get(0));
		PurchaseContract pContract = purchaseContractService.findByInnerContractNo(purchaseArray.get(0));
		Map<String,Object> map = new HashMap<String,Object>();
		String deliveryUnit = pContract.getDeliveryUnit()==null?"":pContract.getDeliveryUnit();//供货单位
		String orderUnit = pContract.getOrderUnit()==null?"":pContract.getOrderUnit();//订货单位
		String receivingUnit = pContract.getReceivingUnit()==null?"":pContract.getReceivingUnit();//收货单位
		String currency = pContract.getCurrency()==null?"":pContract.getCurrency();//币种
		String checkOrg = pContract.getCheckOrg()==null?"":pContract.getCheckOrg();
		String checkStandard = pContract.getCheckStandard()==null?"":pContract.getCheckStandard();
		String relLoginNames = purchaseContractService.getRelLoginNames(purchaseArray);
		map.put("deliveryUnit", deliveryUnit);
		map.put("orderUnit", orderUnit);
		map.put("receivingUnit",  receivingUnit);
		map.put("currency", currency);
//		map.put("", "");
		map.put("purchaseContractIds", purchaseContractIds);
		map.put("checkOrg", checkOrg);
		map.put("checkStandard", checkStandard);
		map.put("state",  "success");
		map.put("relLoginNames",  relLoginNames);
		ObjectMapper mapper = new ObjectMapper();
		String stringJson = mapper.writeValueAsString(map);
		return stringJson;
	}
	
	/**
	 * 到单确认
	 * 
	 * @param id
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "confirm/{id}")
	@ResponseBody
	public String confirm(@PathVariable("id") Long id) throws IllegalAccessException, InvocationTargetException {
		LhBills bills = billsService.get(id);
		//判断到单对应采购是否是整船业务，并对整船类型的直接添加入库
		if(checkBills(bills)){
			InStock instock = new InStock();
			instock.setBillNo(bills.getBillNo());
			instock.setNumbers(bills.getNumbers());
			instock.setShipName(bills.getShipName());
			instock.setShipNo(bills.getShipNo());
			instock.setConfirm("1");
			instock.setDetermineTime(new Date());
			instock.setCreateStockDate(new Date());
			instock.setInStockDate(new Date());
			instock.setInStockId(bills.getBillNo());
			instock.setInStockType("10670003");
			User currentUser = UserUtil.getCurrentUser();
			instock.setCreaterNo(currentUser.getLoginName());
			instock.setCreaterName(currentUser.getName());
			instock.setCreateDate(new Date());
			instock.setCreaterDept(currentUser.getOrganization().getOrgName());
			instock.setDetermineName(currentUser.getName());
			inStockService.save(instock);
			inStockGoodsService.saveGood(bills.getBillNo(), instock.getId().toString(),"GL99999");
		}
		bills.setConfirm("1");
		billsService.save(bills);
		return "success";
	}
	
	/**
	 * 到单复核
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "review/{id}")
	@ResponseBody
	public String review(@PathVariable("id") Long id) {
		LhBills bills = billsService.get(id);
		bills.setReview("1");
		billsService.save(bills);
		return "success";
	}
	
	/**
	 * 取消确认
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "cancleConfirm/{id}")
	@ResponseBody
	public String cancleConfirm(@PathVariable("id") Long id) {
		LhBills bills = billsService.get(id);
		bills.setConfirm("0");
		//判断到单对应采购是否是整船业务，并对整船类型的删除入库
		if(checkBills(bills)){
			InStock instock = new InStock();
			instock = inStockService.findByBillNo(bills.getBillNo());
			inStockGoodsService.deleteByparentId(instock.getId().toString());
			inStockService.delete(instock);
		}
		billsService.save(bills);
		return "success";
	}
	
	/**
	 * 取消复核
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "cancelReview/{id}")
	@ResponseBody
	public String cancelReview(@PathVariable("id") Long id) {
		LhBills bills = billsService.get(id);
		bills.setReview("0");
		billsService.save(bills);
		return "success";
	}
	
	
	/**
	 * 检查对应采购合同是否是整船业务
	 * @param bills
	 * @return
	 */
	public boolean checkBills(LhBills bills){
		boolean returnFlag = false;
		List<LhBillsGoods> goodsList = new ArrayList<LhBillsGoods>();
		goodsList = bills.getGoodsList();
		if(goodsList.size()>0){
			for(LhBillsGoods LhBillsGoods : goodsList){
				PurchaseContract purchaseContract = purchaseContractService.findByInnerContractNo(LhBillsGoods.getInnerContractNo());
				List<PurchaseContractGoods> purchaseContractGoodsList = new ArrayList<PurchaseContractGoods>();
				purchaseContractGoodsList = purchaseContract.getPurchaseContractGoodsList();
				if(purchaseContractGoodsList.size()>0){
					for(PurchaseContractGoods purchaseContractGoods:purchaseContractGoodsList){
						if(purchaseContractGoods.getSaleContractGoodsId()!=null){
							returnFlag=true;
							break;
						}
					}
				}
			}
		}
		return returnFlag;
	}
}
