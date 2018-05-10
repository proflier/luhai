package com.cbmie.lh.sale.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleInvoiceSub;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.lh.sale.entity.SaleSettlementGoods;
import com.cbmie.lh.sale.service.SaleInvoiceService;
import com.cbmie.lh.sale.service.SaleSettlementService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping("sale/saleSettlement")
public class SaleSettlementController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SaleSettlementService settlementService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private BaseInfoUtilService bius;

	private BaseService<SaleSettlement, Long> contractService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	@Autowired
	private SaleInvoiceService saleInvoiceService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sale/saleSettlementList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<SaleSettlement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		settlementService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = settlementService.search(page, filters);
		return getEasyUIData(page);
	}
	
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityListNoPermission(HttpServletRequest request) {
		Page<SaleSettlement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = settlementService.searchNoPermission(page, filters);
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
		SaleSettlement saleSettlement = new SaleSettlement();
		User currentUser = UserUtil.getCurrentUser();
		saleSettlement.setCreaterNo(currentUser.getLoginName());
		saleSettlement.setCreaterName(currentUser.getName());
		saleSettlement.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleSettlement.setCreateDate(new Date());
		saleSettlement.setUpdateDate(new Date());
		String saleSettlementNo = sequenceCodeService.getNextCode("001024").get("returnStr");
		saleSettlement.setSaleSettlementNo(saleSettlementNo);
		model.addAttribute("saleSettlement", saleSettlement);
		model.addAttribute("action", "create");
		return "sale/saleSettlementForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleSettlement saleSettlement, Model model) throws JsonProcessingException {
		try {
			String  currnetSequence = null;
			if (!settlementService.checkCodeUique(saleSettlement)) {
				String saleSettlementNo = sequenceCodeService.getNextCode("001024").get("returnStr");
				saleSettlement.setSaleSettlementNo(saleSettlementNo);
				currnetSequence = saleSettlementNo;
			}
			String summary = "[销售结算]["+ bius.getAffiBaseInfoByCode(saleSettlement.getReceivingUnit()) +"]";
			List<SaleSettlementGoods> subs = saleSettlement.getSaleSettlementSubList();
			if(subs!=null && subs.size()>0){
				BigDecimal total = new BigDecimal(0.0);
				for(SaleSettlementGoods sub : subs){
					if(sub.getReceivablePrice()!=null)
					total = total.add(new BigDecimal(sub.getReceivablePrice()));
				}
				summary = summary+"[应收金额"+total.doubleValue()+"元]";
			}
			saleSettlement.setSummary(summary);
			if(saleSettlement.getId()==null){
				//调用获取下个sequence方法，将当前sequence保存
				sequenceCodeService.getNextSequence("001024");
			}
			settlementService.save(saleSettlement);
			return setReturnData("success", currnetSequence!=null?"编码重复，已自动生成不重复编码并保存":"保存成功", saleSettlement.getId(),currnetSequence);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return setReturnData("fail","保存失败",saleSettlement.getId());
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
		model.addAttribute("saleSettlement", settlementService.get(id));
		model.addAttribute("action", "update");
		return "sale/saleSettlementForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleSettlement saleSettlement,Model model) throws JsonProcessingException {
		if (!settlementService.checkCodeUique(saleSettlement)) {
			return setReturnData("fail","编码重复",saleSettlement.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		saleSettlement.setUpdaterNo(currentUser.getLoginName());
		saleSettlement.setUpdaterName(currentUser.getName());
		saleSettlement.setUpdateDate(new Date());
		String summary = "[销售结算]["+ bius.getAffiBaseInfoByCode(saleSettlement.getReceivingUnit()) +"]";
		List<SaleSettlementGoods> subs = saleSettlement.getSaleSettlementSubList();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(SaleSettlementGoods sub : subs){
				if(sub.getReceivablePrice()!=null)
				total = total.add(new BigDecimal(sub.getReceivablePrice()));
			}
			summary = summary+"[应收金额"+total.doubleValue()+"元]";
		}
		saleSettlement.setSummary(summary);
		settlementService.update(saleSettlement);
		return setReturnData("success","保存成功",saleSettlement.getId());
	}
	
	@ModelAttribute
	public void getSaleSettlement(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleSettlement", settlementService.get(id));
		}
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		SaleSettlement saleSettlement = settlementService.get(id);
		List<SaleInvoice> saleInvoices = saleInvoiceService.getSaleInvoiceBySettlement(saleSettlement.getSaleSettlementNo());
		for(SaleInvoice invoice : saleInvoices){
			if("1".equals(invoice.getPreBilling())){
				invoice.setInvoiceNo(null);
				saleInvoiceService.update(invoice);
			}
		}
		settlementService.delete(id);
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
		model.addAttribute("saleSettlement", settlementService.get(id));
		model.addAttribute("action", "view");
		return "sale/saleSettlementDetail";
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
		SaleSettlement saleSettlement = settlementService.get(id);
		String summary = "[销售结算]["+ bius.getAffiBaseInfoByCode(saleSettlement.getReceivingUnit()) +"]";
		List<SaleSettlementGoods> subs = saleSettlement.getSaleSettlementSubList();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(SaleSettlementGoods sub : subs){
				if(sub.getReceivablePrice()!=null)
				total = total.add(new BigDecimal(sub.getReceivablePrice()));
			}
			summary = summary+"[应收金额"+total.longValue()+"元]";
		}
		saleSettlement.setSummary(summary);
		settlementService.update(saleSettlement);
		//此处可以增加业务验证
		boolean flag = true; //业务是否发起流程（默认发起）
		if(saleSettlement.getOutRelList().size()<1){
			result.put("msg", "出库信息不能为空");
			flag = false;
		}
		if(flag){
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("manageMode", saleSettlement.getManageMode());
			//获取流程标识
			String processKey = activitiService.getCurrentProcessKey(SaleSettlement.class,saleSettlement);
			if(processKey!=null){
				result = activitiService.startWorkflow(saleSettlement, processKey, variables,
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
				SaleSettlement saleSettlement = settlementService.get(id);
				saleSettlement.setProcessInstanceId(null);
				saleSettlement.setState(ActivitiConstant.ACTIVITI_DRAFT);
				settlementService.save(saleSettlement);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	@RequestMapping(value = "toSaleContractList")
	public String toSaleContractList(){
		return "sale/saleDeliverySaleContractList";
	}
	@RequestMapping(value="toPreInvoicePage/{settlementId}")
	public String toPreInvoicePage(@PathVariable(value="settlementId") Long settlementId,Model model){
		SaleSettlement saleSettlement = settlementService.get(settlementId);
		List<SaleInvoice> saleInvoices = saleInvoiceService.getSaleInvoiceBySettlement(saleSettlement.getSaleSettlementNo());
		if(saleInvoices!=null && saleInvoices.size()>0){
			List<Long> invoiceIds = new ArrayList<Long>();
			for(SaleInvoice s : saleInvoices){
				invoiceIds.add(s.getId());
			}
			model.addAttribute("invoiceIds", invoiceIds);
		}
		model.addAttribute("saleContractNo", saleSettlement.getSaleContractNo());
		model.addAttribute("settlementId", settlementId);
		return "sale/salePreInvoiceSelectPage";
	}
	@RequestMapping(value = "getSaleInvoiceSelectItems/{settlementId}")
	@ResponseBody
	public List<SaleInvoice> getSaleInvoiceSelectItems(@PathVariable(value="settlementId") Long settlementId){
		SaleSettlement saleSettlement = settlementService.get(settlementId);
		List<SaleInvoice> list = saleInvoiceService.getSaleInvoiceBySettleAndSaleNo(saleSettlement.getSaleContractNo(), saleSettlement.getSaleSettlementNo());
		return list;
	}
	
	@RequestMapping(value = "backRelationInvoice/{settlementId}")
	@ResponseBody
	public String changeRelationDepts(@PathVariable("settlementId") Long settlementId,@RequestBody List<Long> invoiceIds){
		SaleSettlement saleSettlement = settlementService.get(settlementId);
		List<SaleInvoice> saleInvoices = saleInvoiceService.getSaleInvoiceBySettlement(saleSettlement.getSaleSettlementNo());
		for(SaleInvoice invoice : saleInvoices){
			if("1".equals(invoice.getPreBilling())){
				invoice.setInvoiceNo(null);
				saleInvoiceService.update(invoice);
			}
		}
		for(Long id : invoiceIds){
			SaleInvoice saleInvoice = saleInvoiceService.get(id);
			if("1".equals(saleInvoice.getPreBilling())){
				saleInvoice.setInvoiceNo(saleSettlement.getSaleSettlementNo());
				saleInvoiceService.update(saleInvoice);
			}
		}
		return "success";
	}
	
}
