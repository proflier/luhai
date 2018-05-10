package com.cbmie.lh.sale.web;

import java.util.List;

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
import com.cbmie.lh.sale.entity.SaleOfferGoods;
import com.cbmie.lh.sale.service.SaleOfferGoodsService;
import com.cbmie.lh.sale.service.SaleOfferIndexService;

/**
 * 销售报盘商品controller
 */
@Controller
@RequestMapping("sale/saleOfferGoods")
public class SaleOfferGoodsController extends BaseController {

	@Autowired
	private SaleOfferGoodsService saleOfferGoodsService;
	
	@Autowired
	private SaleOfferIndexService saleOfferIndexService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sale/saleOfferGoodsList";
	}

	/**
	 * json
	 */
	@RequestMapping(value = "list/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public List<SaleOfferGoods> goodsList(@PathVariable("pid") Long pid) {
		return saleOfferGoodsService.getByPid(pid);
	}
	
	/**
	 * 添加跳转
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model, @PathVariable("id") Long id) {
		SaleOfferGoods saleOfferGoods = new SaleOfferGoods();
		saleOfferGoods.setPid(id);
		model.addAttribute("saleOfferGoods", saleOfferGoods);
		model.addAttribute("actionGoods", "create");
		return "sale/saleOfferGoodsForm";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleOfferGoods saleOfferGoods, Model model, 
			@RequestParam(value = "goodsIndexJson", defaultValue = "{}") String goodsIndexJson) {
		saleOfferGoodsService.save(saleOfferGoods);
		saleOfferIndexService.save(saleOfferGoods, goodsIndexJson);
		return setReturnData("success", "保存成功", saleOfferGoods.getId());
	}

	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleOfferGoods", saleOfferGoodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "sale/saleOfferGoodsForm";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleOfferGoods saleOfferGoods, Model model, 
			@RequestParam(value = "goodsIndexJson", defaultValue = "{}") String goodsIndexJson) {
		saleOfferIndexService.save(saleOfferGoods, goodsIndexJson);
		saleOfferGoodsService.update(saleOfferGoods);
		return setReturnData("success", "保存成功", saleOfferGoods.getId());
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		saleOfferGoodsService.delete(id);
		return setReturnData("success", "删除成功", 0l);
	}

	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleOfferGoods", saleOfferGoodsService.get(id));
		}
	}

}
