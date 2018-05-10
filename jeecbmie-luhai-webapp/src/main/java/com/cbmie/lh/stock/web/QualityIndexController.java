package com.cbmie.lh.stock.web;

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
import com.cbmie.lh.stock.entity.QualityIndex;
import com.cbmie.lh.stock.service.QualityIndexService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 质检指标controller
 */
@Controller
@RequestMapping("stock/qualityIndex")
public class QualityIndexController extends BaseController {

	@Autowired
	private QualityIndexService qualityIndexService;

	/**
	 * 添加质检-指标跳转
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model, @PathVariable("id") Long id) {
		QualityIndex qualityIndex = new QualityIndex();
		qualityIndex.setPid(id);
		model.addAttribute("qualityIndex", qualityIndex);
		model.addAttribute("actionIndex", "create");
		return "stock/qualityIndexForm";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid QualityIndex qualityIndex, Model mode) {
		qualityIndexService.save(qualityIndex);
		return setReturnData("success", "保存成功", qualityIndex.getId());
	}

	/**
	 * 修改质检-指标跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("qualityIndex", qualityIndexService.get(id));
		model.addAttribute("actionIndex", "update");
		return "stock/qualityIndexForm";
	}

	/**
	 * 修改质检-指标
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody QualityIndex qualityIndex, Model model) {
		qualityIndexService.update(qualityIndex);
		return setReturnData("success", "保存成功", qualityIndex.getId());
	}

	/**
	 * 删除质检-指标
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		qualityIndexService.delete(id);
		return "success";
	}

	@ModelAttribute
	public void getQualityIndex(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("qualityIndex", qualityIndexService.get(id));
		}
	}

}
