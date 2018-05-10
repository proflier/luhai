package com.cbmie.lh.logistic.web;

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
import com.cbmie.lh.logistic.entity.CustomsDeclarationGoods;
import com.cbmie.lh.logistic.service.CustomsDeclarationGoodsService;

@Controller
@RequestMapping("logistic/customsDeclarationGoods")
public class CustomsDeclarationGoodsController extends BaseController {

	@Autowired
	private CustomsDeclarationGoodsService customsDeclarationGoodsService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/customsDeclarationGoodsList";
	}
	
	/**
	 * 分页查询
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<CustomsDeclarationGoods> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = customsDeclarationGoodsService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 新增跳转
	 */
	@RequestMapping(value = "create/{pid}", method = RequestMethod.GET)
	public String createForm(@PathVariable("pid") Long pid, Model model) {
		CustomsDeclarationGoods customsDeclarationGoods = new CustomsDeclarationGoods();
		customsDeclarationGoods.setPid(pid);
		model.addAttribute("customsDeclarationGoods", customsDeclarationGoods);
		model.addAttribute("action", "create");
		return "logistic/customsDeclarationGoodsForm";
	}
	
	/**
	 * 添加提交
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid CustomsDeclarationGoods customsDeclarationGoods, Model model) {
		customsDeclarationGoodsService.save(customsDeclarationGoods);
		return setReturnData("success", "保存成功", customsDeclarationGoods.getId());
	}
	
	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		CustomsDeclarationGoods customsDeclarationGoods = customsDeclarationGoodsService.get(id);
		model.addAttribute("customsDeclarationGoods", customsDeclarationGoods);
		model.addAttribute("action", "update");
		return "logistic/customsDeclarationGoodsForm";
	}
	
	/**
	 * 修改提交
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CustomsDeclarationGoods customsDeclarationGoods, Model model) {
		customsDeclarationGoodsService.update(customsDeclarationGoods);
		return setReturnData("success", "保存成功", customsDeclarationGoods.getId());
	}
	
	
	@ModelAttribute
	public void getContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("customsDeclarationGoods", customsDeclarationGoodsService.get(id));
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		customsDeclarationGoodsService.delete(id);
		return setReturnData("success", "删除成功", id);
	}
	
	/**
	 * 查看明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		CustomsDeclarationGoods customsDeclarationGoods = customsDeclarationGoodsService.get(id);
		model.addAttribute("customsDeclarationGoods", customsDeclarationGoods);
		model.addAttribute("action", "view");
		return "logistic/customsDeclarationGoodsDetail";
	}
	
	/**
	 * 根据父id获取
	 */
	@RequestMapping(value = "getByPid/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomsDeclarationGoods> getByPid (@PathVariable("pid") Long pid) {
		return customsDeclarationGoodsService.getByPid(pid);
	}
	
}
