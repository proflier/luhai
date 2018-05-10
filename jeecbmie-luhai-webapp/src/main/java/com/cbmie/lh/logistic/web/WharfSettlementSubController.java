package com.cbmie.lh.logistic.web;

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
import com.cbmie.lh.logistic.entity.WharfSettlement;
import com.cbmie.lh.logistic.entity.WharfSettlementSub;
import com.cbmie.lh.logistic.service.WharfSettlementService;
import com.cbmie.lh.logistic.service.WharfSettlementSubService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="logistic/wharfSub")
public class WharfSettlementSubController extends BaseController {

	@Autowired
	private WharfSettlementSubService subService;
	@Autowired
	private WharfSettlementService settleService;
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<WharfSettlementSub> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = subService.search(page, filters);
		Map<String, Object> result = getEasyUIData(page);
		List<WharfSettlementSub> footer = new ArrayList<WharfSettlementSub>();
		List<WharfSettlementSub> list = page.getResult();
		if(list!=null && list.size()>0){
			double shouldPay_t = 0.0;
			
			WharfSettlementSub sub_footer = new WharfSettlementSub();
			footer.add(sub_footer);
			for(WharfSettlementSub sub : list){
				shouldPay_t += sub.getTotalPrice()==null?0:sub.getTotalPrice();
			}
			sub_footer.setTotalPrice(shouldPay_t);
			sub_footer.setSettleQuantity(null);
			sub_footer.setUnitPrice(null);
		}
		result.put("footer", footer);
		return result;
	}
	public void backFillWharfSettlement(Long settleId){
		WharfSettlement settle = settleService.get(settleId);
		List<WharfSettlementSub> list = settle.getSubList();
		double shouldPay_t = 0.0;
		for(WharfSettlementSub sub : list){
			shouldPay_t += sub.getTotalPrice()==null?0:sub.getTotalPrice();
		}
		Double prePay = settle.getPrePay()==null?0:settle.getPrePay();
		settle.setShouldPay(shouldPay_t-prePay);
		Double alreadyBill = settle.getAlreadyBill()==null?0:settle.getAlreadyBill();
		settle.setShouldBill(shouldPay_t-alreadyBill);
		settleService.update(settle);
	}
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{settlementId}", method = RequestMethod.GET)
	public String createForm(@PathVariable(value="settlementId") Long settlementId,Model model) throws ParseException {
		WharfSettlementSub wharfSettlementSub = new WharfSettlementSub();
		wharfSettlementSub.setWharfSettlementId(settlementId);
		model.addAttribute("wharfSettlementSub", wharfSettlementSub);
		model.addAttribute("actionSub", "create");
		return "logistic/wharfSubForm";
	}
	
	/**
	 * 添加
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WharfSettlementSub wharfSettlementSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		wharfSettlementSub.setCreaterNo(currentUser.getLoginName());
		wharfSettlementSub.setCreaterName(currentUser.getName());
		wharfSettlementSub.setCreaterDept(currentUser.getOrganization().getOrgName());
		wharfSettlementSub.setCreateDate(new Date());
		subService.save(wharfSettlementSub);
		backFillWharfSettlement(wharfSettlementSub.getWharfSettlementId());
		return setReturnData("success","保存成功",wharfSettlementSub.getId());
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
		model.addAttribute("wharfSettlementSub", subService.get(id));
		model.addAttribute("actionSub", "update");
		return "logistic/wharfSubForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WharfSettlementSub wharfSettlementSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		wharfSettlementSub.setUpdaterNo(currentUser.getLoginName());
		wharfSettlementSub.setUpdaterName(currentUser.getName());
		wharfSettlementSub.setUpdateDate(new Date());
		subService.update(wharfSettlementSub);
		backFillWharfSettlement(wharfSettlementSub.getWharfSettlementId());
		return setReturnData("success","保存成功",wharfSettlementSub.getId());
	}
	
	@ModelAttribute
	public void getWharfSettlementSub(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("wharfSettlementSub", subService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		WharfSettlementSub wharfSettlementSub = subService.get(id);
		subService.delete(id);
		backFillWharfSettlement(wharfSettlementSub.getWharfSettlementId());
		return setReturnData("success","删除成功",id);
	}
	
	@RequestMapping(value = "toShipList/{tradeCategory}",method=RequestMethod.GET)
	public String toShipList(@PathVariable("tradeCategory") String tradeCategory, Model model){
		model.addAttribute("tradeCategory", tradeCategory);
		return "logistic/selectShipList";
	}
}
