package com.cbmie.lh.sale.web;

import java.math.BigDecimal;
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
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;
import com.cbmie.lh.sale.service.SaleDeliveryGoodsService;
import com.cbmie.lh.sale.service.SaleDeliveryService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 销售放货明细controller
 */
@Controller
@RequestMapping("sale/saleDeliveryGoods")
public class SaleDeliveryGoodsController extends BaseController {

	@Autowired
	private SaleDeliveryGoodsService saleDeliveryGoodsService;
	@Autowired
	private SaleDeliveryService saleDeliveryService;


	/**
	 * 根据id获取明细
	 * @param request
	 * @param id 主表id
	 * @return
	 */
	@RequestMapping(value = "getGoods/{param}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSalesDeliveryGoods(HttpServletRequest request,@PathVariable("param") String param,@PathVariable("id") String id) {
		Page<SaleDeliveryGoods> page = getPage(request);
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		PropertyFilter filter = new PropertyFilter(param, id);
		filterList.add(filter);
		page = saleDeliveryGoodsService.searchNoPermission(page, filterList);
		//按未分页计算
		List<SaleDeliveryGoods> result = page.getResult();
		List<SaleDeliveryGoods> footer = new ArrayList<SaleDeliveryGoods>();
		BigDecimal total = BigDecimal.valueOf(0.00);
		for(SaleDeliveryGoods goods : result){
			total = total.add(BigDecimal.valueOf(goods.getQuantity()));
		}
		SaleDeliveryGoods footer_=new SaleDeliveryGoods();
		footer_.setQuantity(total.doubleValue());
		footer.add(footer_);
		 Map<String, Object> r =  getEasyUIData(page);
		 r.put("footer", footer);
		return r;
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
		SaleDelivery saleDelivery = saleDeliveryService.get(id);
		if(param.equals("EQL_saleDeliveryId")){
			saleDeliveryGoods.setSaleDeliveryId(id);
			saleDeliveryGoods.setContractNo(saleDelivery.getSaleContractNo());
		}else{
			saleDeliveryGoods.setOutStockId(id);
		}
		model.addAttribute("saleDeliveryGoods", saleDeliveryGoods);
		model.addAttribute("actionGoods", "create");
		return "sale/saleDeliveryGoodsForm";
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
		saleDeliveryGoodsService.save(saleDeliveryGoods);
		return setReturnData("success", "保存成功", saleDeliveryGoods.getId());
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
		model.addAttribute("saleDeliveryGoods", saleDeliveryGoodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "sale/saleDeliveryGoodsForm";
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
		saleDeliveryGoodsService.update(saleDeliveryGoods);
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
		saleDeliveryGoodsService.delete(id);
		return "success";
	}

	
	@ModelAttribute
	public void getSaleDeliveryGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleDeliveryGoods", saleDeliveryGoodsService.get(id));
		}
	}

}
