package com.cbmie.lh.financial.web;

import java.text.ParseException;
import java.util.ArrayList;
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

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.financial.entity.InputInvoiceSub;
import com.cbmie.lh.financial.service.InputInvoiceService;
import com.cbmie.lh.financial.service.InputInvoiceSubService;
import com.cbmie.lh.logistic.entity.TransportSettlementSub;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value="financial/inputInvoiceSub")
public class InputInvoiceSubController extends BaseController {

	@Autowired
	private InputInvoiceSubService subService;
	
	@Autowired
	private InputInvoiceService inputInvoiceService;
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<InputInvoiceSub> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = subService.search(page, filters);
		Map<String, Object> result = getEasyUIData(page);
		List<InputInvoiceSub> list = page.getResult();
		if(list!=null && list.size()>0){
			int mount_t = 0;
			double prices_t = 0.0;
			double allPrices_t = 0.0;
			double taxMoney_t = 0.0;
			List<InputInvoiceSub> footer = new ArrayList<InputInvoiceSub>();
			InputInvoiceSub sub_footer = new InputInvoiceSub();
			footer.add(sub_footer);
			for(InputInvoiceSub sub : list){
				mount_t += sub.getMount()==0?0:sub.getMount();
				prices_t += sub.getPrices()==null?0:sub.getPrices();
				allPrices_t += sub.getAllPrices()==null?0:sub.getAllPrices();
				taxMoney_t += sub.getTaxMoney()==null?0:sub.getTaxMoney();
			}
			sub_footer.setMount(mount_t);
			sub_footer.setPrices(prices_t);
			sub_footer.setAllPrices(allPrices_t);
			sub_footer.setTaxMoney(taxMoney_t);
			sub_footer.setPurchaseOrderNumber(null);
			sub_footer.setRelatedSalesOrder(null);
			sub_footer.setInvoiceNo(null);
			sub_footer.setProductName(null);
			sub_footer.setBillingDate(null);
			result.put("footer", footer);
		}
		return result;
	}
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{inputInvoiceSubId}", method = RequestMethod.GET)
	public String createForm(@PathVariable(value="inputInvoiceSubId") Long inputInvoiceSubId,Model model) throws ParseException {
		InputInvoiceSub inputInvoiceSub = new InputInvoiceSub();
		inputInvoiceSub.setInputInvoiceSubId(inputInvoiceSubId);
		model.addAttribute("inputInvoiceSub", inputInvoiceSub);
		model.addAttribute("actionSub", "create");
		return "financial/inputInvoiceSubForm";
	}
	
	/**
	 * 添加
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InputInvoiceSub inputInvoiceSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		inputInvoiceSub.setCreaterNo(currentUser.getLoginName());
		inputInvoiceSub.setCreaterName(currentUser.getName());
		inputInvoiceSub.setCreaterDept(currentUser.getOrganization().getOrgName());
		inputInvoiceSub.setCreateDate(new Date());
		subService.save(inputInvoiceSub);
		inputInvoiceService.saveRelLoginNames(inputInvoiceSub);
		return setReturnData("success","保存成功",inputInvoiceSub.getId());
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
		InputInvoiceSub sub = subService.get(id);
		model.addAttribute("inputInvoiceSub", sub);
		model.addAttribute("actionSub", "update");
		return "financial/inputInvoiceSubForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InputInvoiceSub inputInvoiceSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		inputInvoiceSub.setUpdaterNo(currentUser.getLoginName());
		inputInvoiceSub.setUpdaterName(currentUser.getName());
		inputInvoiceSub.setUpdateDate(new Date());
		subService.update(inputInvoiceSub);
		inputInvoiceService.saveRelLoginNames(inputInvoiceSub);
		return setReturnData("success","保存成功",inputInvoiceSub.getId());
	}
	
	@ModelAttribute
	public void getInputInvoiceSub(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inputInvoiceSub", subService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		subService.delete(id);
		return "success";
	}
}
