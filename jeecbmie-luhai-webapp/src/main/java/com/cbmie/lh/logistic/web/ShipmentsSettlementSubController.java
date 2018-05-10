package com.cbmie.lh.logistic.web;

import java.text.ParseException;
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
import com.cbmie.lh.logistic.entity.ShipmentsSettlement;
import com.cbmie.lh.logistic.entity.ShipmentsSettlementSub;
import com.cbmie.lh.logistic.service.ShipmentsSettlementService;
import com.cbmie.lh.logistic.service.ShipmentsSettlementSubService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="logistic/shipmentsSub")
public class ShipmentsSettlementSubController extends BaseController {

	@Autowired
	private ShipmentsSettlementSubService subService;
	@Autowired
	private ShipmentsSettlementService settleService;
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<ShipmentsSettlementSub> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = subService.search(page, filters);
		return getEasyUIData(page);
	}
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{settlementId}", method = RequestMethod.GET)
	public String createForm(@PathVariable(value="settlementId") Long settlementId,Model model) throws ParseException {
		ShipmentsSettlementSub shipmentsSettlementSub = new ShipmentsSettlementSub();
		ShipmentsSettlement settlement = settleService.get(settlementId);
		shipmentsSettlementSub.setShipmentsSettleId(settlementId);
		model.addAttribute("shipmentsSettlementSub", shipmentsSettlementSub);
		model.addAttribute("actionSub", "create");
		model.addAttribute("tradeCategory", settlement.getTradeCategory());
		return "logistic/shipmentsSubForm";
	}
	
	/**
	 * 添加
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ShipmentsSettlementSub shipmentsSettlementSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		shipmentsSettlementSub.setCreaterNo(currentUser.getLoginName());
		shipmentsSettlementSub.setCreaterName(currentUser.getName());
		shipmentsSettlementSub.setCreaterDept(currentUser.getOrganization().getOrgName());
		shipmentsSettlementSub.setCreateDate(new Date());
		subService.save(shipmentsSettlementSub);
		return setReturnData("success","保存成功",shipmentsSettlementSub.getId());
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
		ShipmentsSettlementSub sub = subService.get(id);
		ShipmentsSettlement settlement = settleService.get(sub.getShipmentsSettleId());
		model.addAttribute("shipmentsSettlementSub", sub);
		model.addAttribute("actionSub", "update");
		model.addAttribute("tradeCategory", settlement.getTradeCategory());
		return "logistic/shipmentsSubForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ShipmentsSettlementSub shipmentsSettlementSub) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		shipmentsSettlementSub.setUpdaterNo(currentUser.getLoginName());
		shipmentsSettlementSub.setUpdaterName(currentUser.getName());
		shipmentsSettlementSub.setUpdateDate(new Date());
		subService.update(shipmentsSettlementSub);
		return setReturnData("success","保存成功",shipmentsSettlementSub.getId());
	}
	
	@ModelAttribute
	public void getShipmentsSettlementSub(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("shipmentsSettlementSub", subService.get(id));
		}
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		subService.delete(id);
		return "success";
	}
	@RequestMapping(value = "toShipList/{tradeCategory}",method=RequestMethod.GET)
	public String toShipList(@PathVariable("tradeCategory") String tradeCategory, Model model){
		model.addAttribute("tradeCategory", tradeCategory);
		return "logistic/selectShipList";
	}
}
