package com.cbmie.lh.sale.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.sale.entity.SaleSettlementGoods;
import com.cbmie.lh.sale.service.SaleSettlementGoodsService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping("sale/saleSettlementGoods")
public class SaleSettlementGoodsController extends BaseController {
	@Autowired
	private SaleSettlementGoodsService settlementGoodsService;
	
	@RequestMapping("list/{saleSettlementId}")
	@ResponseBody
	public Map<String, Object> getEntityListBySaleSettlementId(@PathVariable("saleSettlementId") Long saleSettlementId){
		Map<String, Object> result = new HashMap<String,Object>();
		List<SaleSettlementGoods> list = settlementGoodsService.getSaleSettlementGoodsByPid(saleSettlementId);
		if(list!=null && list.size()>0){
			result.put("total", list.size());
			result.put("rows", list);
			double sendQuantity_t = 0.0;
			double receiveQuantity_t = 0.0;
			double settlementQuantity_t = 0.0;
			double deductionPrice_t = 0.0;
			double receivedPrice_t = 0.0;
			double receivablePrice_t = 0.0;
			for(SaleSettlementGoods goods : list){
				sendQuantity_t += goods.getSendQuantity()==null?0.0:goods.getSendQuantity();
				receiveQuantity_t += goods.getReceiveQuantity()==null?0.0:goods.getReceiveQuantity();
				settlementQuantity_t += goods.getSettlementQuantity()==null?0.0:goods.getSettlementQuantity();
				deductionPrice_t += goods.getDeductionPrice()==null?0.0:goods.getDeductionPrice();
				receivedPrice_t += goods.getReceivedPrice()==null?0.0:goods.getReceivedPrice();
				receivablePrice_t += goods.getReceivablePrice()==null?0.0:goods.getReceivablePrice();
			}
			SaleSettlementGoods totle = new SaleSettlementGoods();
			totle.setSendQuantity(sendQuantity_t);
			totle.setReceiveQuantity(receiveQuantity_t);
			totle.setSettlementQuantity(settlementQuantity_t);
			totle.setDeductionPrice(deductionPrice_t);
			totle.setReceivedPrice(receivedPrice_t);
			totle.setReceivablePrice(receivablePrice_t);
			List<SaleSettlementGoods> footer = new ArrayList<SaleSettlementGoods>();
			footer.add(totle);
			result.put("footer", footer);
		}else{
			result.put("total", 0);
			result.put("rows", new ArrayList<SaleSettlementGoods>() );
			result.put("footer", new ArrayList<SaleSettlementGoods>() );
		}
		return result;
	}

	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{saleSettlementId}", method = RequestMethod.GET)
	public String createForm(@PathVariable("saleSettlementId") Long saleSettlementId,Model model) throws ParseException {
		SaleSettlementGoods saleSettlementGoods = new SaleSettlementGoods();
		saleSettlementGoods.setSaleSettlementId(saleSettlementId);
		User currentUser = UserUtil.getCurrentUser();
		saleSettlementGoods.setCreaterNo(currentUser.getLoginName());
		saleSettlementGoods.setCreaterName(currentUser.getName());
		saleSettlementGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleSettlementGoods.setCreateDate(new Date());
		model.addAttribute("saleSettlementGoods", saleSettlementGoods);
		model.addAttribute("actionGoods", "create");
		return "sale/saleSettlementGoodsForm";
	}
	
	/**
	 * 添加
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleSettlementGoods saleSettlementGoods, Model model) throws JsonProcessingException {
		try {
			settlementGoodsService.save(saleSettlementGoods);
			return setReturnData("success","保存成功",saleSettlementGoods.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",saleSettlementGoods.getId());
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
		model.addAttribute("saleSettlementGoods", settlementGoodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "sale/saleSettlementGoodsForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleSettlementGoods saleSettlementGoods,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		saleSettlementGoods.setUpdaterNo(currentUser.getLoginName());
		saleSettlementGoods.setUpdaterName(currentUser.getName());
		saleSettlementGoods.setUpdateDate(new Date());
		settlementGoodsService.update(saleSettlementGoods);
		return setReturnData("success","保存成功",saleSettlementGoods.getId());
	}
	
	@ModelAttribute
	public void getSaleSettlementGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleSettlementGoods", settlementGoodsService.get(id));
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
		settlementGoodsService.delete(id);
		return setReturnData("success","删除成功",id);
	}
}
