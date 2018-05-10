package com.cbmie.lh.stock.web;

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
import com.cbmie.lh.stock.entity.QualityGoods;
import com.cbmie.lh.stock.service.QualityGoodsService;
import com.cbmie.lh.stock.service.QualityIndexService;

/**
 * 质检商品controller
 */
@Controller
@RequestMapping("stock/qualityGoods")
public class QualityGoodsController extends BaseController {

	//@Autowired
	//private QualityInspectionService qualityInspectionService;
	
	@Autowired
	private QualityGoodsService qualityGoodsService;
	
	@Autowired
	private QualityIndexService qualityIndexService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sale/qualityGoodsList";
	}

	/**
	 * json
	 */
	@RequestMapping(value = "list/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public List<QualityGoods> goodsList(@PathVariable("pid") Long pid) {
		return qualityGoodsService.getByPid(pid);
	}
	
	/**
	 * 添加跳转
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model, @PathVariable("id") Long id) {
		QualityGoods qualityGoods = new QualityGoods();
		qualityGoods.setPid(id);
		model.addAttribute("qualityGoods", qualityGoods);
		model.addAttribute("actionGoods", "create");
		return "stock/qualityGoodsForm";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid QualityGoods qualityGoods, Model model, 
			@RequestParam(value = "goodsIndexJson", defaultValue = "{}") String goodsIndexJson) {
		qualityGoodsService.save(qualityGoods);
		qualityIndexService.save(qualityGoods, goodsIndexJson);
		return setReturnData("success", "保存成功", qualityGoods.getId());
	}

	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("qualityGoods", qualityGoodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "stock/qualityGoodsForm";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody QualityGoods qualityGoods, Model model, 
			@RequestParam(value = "goodsIndexJson", defaultValue = "{}") String goodsIndexJson) {
		qualityIndexService.save(qualityGoods, goodsIndexJson);
		qualityGoodsService.update(qualityGoods);
		return setReturnData("success", "保存成功", qualityGoods.getId());
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		qualityGoodsService.delete(id);
		return setReturnData("success", "删除成功", 0l);
	}

	@ModelAttribute
	public void getObject(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("qualityGoods", qualityGoodsService.get(id));
		}
	}

}
