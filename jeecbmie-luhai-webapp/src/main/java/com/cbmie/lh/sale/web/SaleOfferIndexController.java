package com.cbmie.lh.sale.web;

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
import com.cbmie.lh.sale.entity.SaleOfferIndex;
import com.cbmie.lh.sale.service.SaleOfferIndexService;

/**
 * 销售报盘商品-指标controller
 */
@Controller
@RequestMapping("sale/saleOfferIndex")
public class SaleOfferIndexController extends BaseController {

	@Autowired
	private SaleOfferIndexService saleOfferIndexService;

	/**
	 * 添加跳转
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model, @PathVariable("id") Long id) {
		SaleOfferIndex saleOfferIndex = new SaleOfferIndex();
		saleOfferIndex.setPid(id);
		model.addAttribute("saleOfferIndex", saleOfferIndex);
		model.addAttribute("actionIndex", "create");
		return "sale/saleOfferIndexForm";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleOfferIndex saleOfferIndex, Model mode) {
		saleOfferIndexService.save(saleOfferIndex);
		return setReturnData("success", "保存成功", saleOfferIndex.getId());
	}

	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleOfferIndex", saleOfferIndexService.get(id));
		model.addAttribute("actionIndex", "update");
		return "sale/saleOfferIndexForm";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleOfferIndex saleOfferIndex, Model model) {
		saleOfferIndexService.update(saleOfferIndex);
		return setReturnData("success", "保存成功", saleOfferIndex.getId());
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		saleOfferIndexService.delete(id);
		return "success";
	}

	@ModelAttribute
	public void getSaleOfferIndex(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleOfferIndex", saleOfferIndexService.get(id));
		}
	}

}
