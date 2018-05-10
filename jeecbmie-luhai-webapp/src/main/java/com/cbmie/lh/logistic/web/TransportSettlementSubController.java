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
import com.cbmie.lh.logistic.entity.TransportSettlementSub;
import com.cbmie.lh.logistic.service.TransportSettlementSubService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="logistic/transportSub")
public class TransportSettlementSubController extends BaseController {
	
	@Autowired
	private TransportSettlementSubService subService;
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<TransportSettlementSub> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = subService.search(page, filters);
		Map<String, Object> result = getEasyUIData(page);
		List<TransportSettlementSub> list = page.getResult();
		if(list!=null && list.size()>0){
			double shouldPay_t = 0.0;
			double differPay_t = 0.0;
			double elsePay_t = 0.0;
			double realPay_t = 0.0;
			List<TransportSettlementSub> footer = new ArrayList<TransportSettlementSub>();
			TransportSettlementSub sub_footer = new TransportSettlementSub();
			footer.add(sub_footer);
			for(TransportSettlementSub sub : list){
				shouldPay_t += sub.getShouldPay()==null?0:sub.getShouldPay();
				differPay_t += sub.getDifferPay()==null?0:sub.getDifferPay();
				elsePay_t += sub.getElsePay()==null?0:sub.getElsePay();
				realPay_t += sub.getRealPay()==null?0:sub.getRealPay();
			}
			sub_footer.setShouldPay(shouldPay_t);
			sub_footer.setDifferPay(differPay_t);
			sub_footer.setElsePay(elsePay_t);
			sub_footer.setRealPay(realPay_t);
			sub_footer.setOutStockQuantity(null);
			sub_footer.setUnitPrice(null);
			sub_footer.setArrivalQuantity(null);
			sub_footer.setSettleQuantity(null);
			result.put("footer", footer);
		}
		
		return result;
	}
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{settlementId}", method = RequestMethod.GET)
	public String createForm(@PathVariable(value="settlementId") Long settlementId,Model model) throws ParseException {
		TransportSettlementSub transportSettlementSub = new TransportSettlementSub();
		transportSettlementSub.setTransportSettlementId(settlementId);
		model.addAttribute("transportSettlementSub", transportSettlementSub);
		model.addAttribute("actionSub", "create");
		return "logistic/transportSubForm";
	}
	
	/**
	 * 添加
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid TransportSettlementSub transportSettlementSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		transportSettlementSub.setCreaterNo(currentUser.getLoginName());
		transportSettlementSub.setCreaterName(currentUser.getName());
		transportSettlementSub.setCreaterDept(currentUser.getOrganization().getOrgName());
		transportSettlementSub.setCreateDate(new Date());
		subService.save(transportSettlementSub);
		return setReturnData("success","保存成功",transportSettlementSub.getId());
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
		model.addAttribute("transportSettlementSub", subService.get(id));
		model.addAttribute("actionSub", "update");
		return "logistic/transportSubForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody TransportSettlementSub transportSettlementSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		transportSettlementSub.setUpdaterNo(currentUser.getLoginName());
		transportSettlementSub.setUpdaterName(currentUser.getName());
		transportSettlementSub.setUpdateDate(new Date());
		subService.update(transportSettlementSub);
		return setReturnData("success","保存成功",transportSettlementSub.getId());
	}
	
	@ModelAttribute
	public void getTransportSettlementSub(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("transportSettlementSub", subService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		subService.delete(id);
		return setReturnData("success","删除成功",id);
	}
}
