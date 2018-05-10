package com.cbmie.genMac.foreignTrade.web;

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
import com.cbmie.genMac.foreignTrade.entity.Commodity;
import com.cbmie.genMac.foreignTrade.service.CommodityService;

/**
 * 进口商品controller
 */
@Controller
@RequestMapping("foreignTrade/commodity")
public class CommodityController extends BaseController{
	
	@Autowired
	private CommodityService commodityService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "importContract/commodityList";
	}
	
	/**
	 * 获取商品json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> commodityList(HttpServletRequest request) {
		Page<Commodity> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = commodityService.search(page, filters);
//		page = commodityService.search(page);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加商品跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("commodity", new Commodity());
		model.addAttribute("action", "create");
		return "importContract/commodityForm";
	}

	/**
	 * 添加商品
	 * 
	 * @param commodity
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Commodity commodity, Model model) {
		commodityService.save(commodity);
		return "success";
	}

	/**
	 * 修改商品跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("commodity", commodityService.get(id));
		model.addAttribute("action", "update");
		return "importContract/commodityForm";
	}

	/**
	 * 修改商品
	 * 
	 * @param commodity
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Commodity commodity,Model model) {
		commodityService.update(commodity);
		return "success";
	}

	/**
	 * 删除商品
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		commodityService.delete(id);
		return "success";
	}
	
	@ModelAttribute
	public void getCommodity(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("commodity", commodityService.get(id));
		}
	}
}
