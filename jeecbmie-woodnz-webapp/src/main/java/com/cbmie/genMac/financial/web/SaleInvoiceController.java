package com.cbmie.genMac.financial.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.financial.entity.SaleInvoice;
import com.cbmie.genMac.financial.entity.SaleInvoiceSub;
import com.cbmie.genMac.financial.service.SaleInvoiceService;
import com.cbmie.genMac.financial.service.SaleInvoiceSubService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping("financial/saleInvoice")
public class SaleInvoiceController extends BaseController {
	
	@Autowired
	private SaleInvoiceService invoiceService;
	@Autowired
	private SaleInvoiceSubService subService;
	private Long mainId=null;
	private List<SaleInvoiceSub> subCacheList = new ArrayList<SaleInvoiceSub>();
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		mainId=null;
		return "financial/saleInvoiceList";
	}
	
	/**
	 * 获取json 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> outList(HttpServletRequest request) {
		Page<SaleInvoice> page = getPage(request);
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
//			page = invoiceService.searchByPermission(page, filters,null);
			page = invoiceService.search(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getEasyUIData(page);
	}
	
	/**
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		mainId=null;
		SaleInvoice invoice = new SaleInvoice();
		User currentUser = UserUtil.getCurrentUser();
		invoice.setCreaterName(currentUser.getName());
		invoice.setCreateDate(new Date());
		model.addAttribute("saleInvoice", invoice);
		model.addAttribute("action", "create");
		return "financial/saleInvoiceForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param port
	 * @param model
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleInvoice saleInvoice, @RequestParam("saleInvoiceSubJson") String subJson,Model model) throws ParseException, JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		saleInvoice.setCreaterNo(currentUser.getLoginName());
		saleInvoice.setCreaterName(currentUser.getName());
		saleInvoice.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleInvoice.setCreateDate(new Date());
//		saleInvoice.setUserId(currentUser.getId().toString());
//		saleInvoice.setDeptId(currentUser.getOrganization().getId());
//		saleInvoice.setCompanyId(invoiceService.getCompany(currentUser.getOrganization()).getId());
		invoiceService.save(saleInvoice);
		if(StringUtils.isNotBlank(subJson)){
			subService.save(saleInvoice, subJson);
		}
		mainId = saleInvoice.getId();//当前的出库主表id
		return setReturnData("success","保存成功",saleInvoice.getId());
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		invoiceService.delete(id);
		return "success";
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		mainId=id;
		subCacheList.clear();
		model.addAttribute("saleInvoice", invoiceService.get(id));
		model.addAttribute("action", "update");
		return "financial/saleInvoiceForm";
	}
	
	/**
	 * 修改
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleInvoice saleInvoice,
			@RequestParam("saleInvoiceSubJson") String subJson,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		saleInvoice.setUpdaterName(currentUser.getLoginName());
		saleInvoice.setUpdateDate(new Date());
		invoiceService.update(saleInvoice);
		if(StringUtils.isNotBlank(subJson)){
			try{
				subService.save(saleInvoice, subJson);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return setReturnData("success","保存成功",saleInvoice.getId());
	}
	/**
	 * 明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleInvoice", invoiceService.get(id));
		model.addAttribute("action", "detail");
		return "financial/saleInvoiceDetail";
	}
	/**
	 * 获取明细列表
	 */
	@RequestMapping(value="getSaleInvoiceSubList",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getGoodsList(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> total = new HashMap<String, Object>();
		total.put("shipNo", "汇总");
		List<Map> totalList = new ArrayList<Map>();
		totalList.add(total);
		try {
			if(mainId!=null){
				SaleInvoice invoice = (SaleInvoice)invoiceService.get(mainId);
				List<SaleInvoiceSub> subs = invoice.getSubs();
				for(SaleInvoiceSub sub : subs){
					total.put("metricTon", (sub.getMetricTon()==null?0.0:sub.getMetricTon())+(total.get("metricTon")==null?0.0:(Double)total.get("metricTon")));
					total.put("dryTon", (sub.getDryTon()==null?0.0:sub.getDryTon())+(total.get("dryTon")==null?0.0:(Double)total.get("dryTon")));
					total.put("money", (sub.getMoney()==null?0.0:sub.getMoney())+(total.get("money")==null?0.0:(Double)total.get("money")));
					total.put("totalMoney", (sub.getTotalMoney()==null?0.0:sub.getTotalMoney())+(total.get("totalMoney")==null?0.0:(Double)total.get("totalMoney")));
				}
				result.put("total", subs.size());
				result.put("rows", subs);
				result.put("footer", totalList);
			}else if(mainId == null){
				result.put("total", 0);
				result.put("rows", subCacheList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} 
	
	@ModelAttribute
	public void getSaleInvoice(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleInvoice", invoiceService.get(id));
		}
	}

}
