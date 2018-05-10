package com.cbmie.lh.stock.web;

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
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;
import com.cbmie.lh.sale.service.SaleDeliveryGoodsService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
@Controller
@RequestMapping(value="stock/outStockGoods")
public class OutStockGoodsController extends BaseController {

	@Autowired
	private SaleDeliveryGoodsService outStockGoodsService;
	
	@RequestMapping(value="listGoodsForSettlement/{saleContractNo}")
	@ResponseBody
	public Map<String,Object> getOutStockGoodsList(@PathVariable(value="saleContractNo") String saleContractNo){
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list =  outStockGoodsService.getOutStockGoodsList(saleContractNo);
		result.put("rows", list);
		return result;
	}
	
	
	
	/**
	 * 添加放货明细跳转
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "create/{param}/{id}", method = RequestMethod.GET)
	public String createFormSalesDeliveryGoods(Model model,@PathVariable("param") String param,@PathVariable("id") Long id) {
		SaleDeliveryGoods saleDeliveryGoods = new SaleDeliveryGoods();
		if(param.equals("EQL_saleDeliveryId")){
			saleDeliveryGoods.setSaleDeliveryId(id);
		}else{
			saleDeliveryGoods.setOutStockId(id);
		}
		model.addAttribute("saleDeliveryGoods", saleDeliveryGoods);
		model.addAttribute("actionGoods", "create");
		return "stock/outStockGoodsForm";
	}
	
	
	/**
	 * 修改放货明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleDeliveryGoods", outStockGoodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "stock/outStockGoodsForm";
	}
	
	/**
	 * 添加放货明细
	 * @param saleDeliveryGoods
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleDeliveryGoods saleDeliveryGoods, Model mode) throws JsonProcessingException   {
		User currentUser = UserUtil.getCurrentUser();
		saleDeliveryGoods.setCreaterNo(currentUser.getLoginName());
		saleDeliveryGoods.setCreaterName(currentUser.getName());
		saleDeliveryGoods.setCreateDate(new Date());
		saleDeliveryGoods.setUpdateDate(new Date());
		saleDeliveryGoods.setSummary("放货-放货明细[" + saleDeliveryGoods.getGoodsName() + "]");
		saleDeliveryGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		outStockGoodsService.save(saleDeliveryGoods);
		return setReturnData("success", "保存成功", saleDeliveryGoods.getId());
	}
	
	/**
	 * 修改放货明细
	 * @param saleDeliveryGoods
	 * @param model
	 * @param goodsIndexJson
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleDeliveryGoods saleDeliveryGoods, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		saleDeliveryGoods.setUpdaterNo(currentUser.getLoginName());
		saleDeliveryGoods.setUpdaterName(currentUser.getName());
		saleDeliveryGoods.setUpdateDate(new Date());
		saleDeliveryGoods.setSummary("放货-放货明细[" + saleDeliveryGoods.getGoodsName() + "]");
		outStockGoodsService.update(saleDeliveryGoods);
		return setReturnData("success", "保存成功", saleDeliveryGoods.getId());
	}
	
	/**
	 * 删除放货明细
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		outStockGoodsService.delete(id);
		return "success";
	}
	
	@ModelAttribute
	public void getSaleDeliveryGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleDeliveryGoods", outStockGoodsService.get(id));
		}
	}

}
