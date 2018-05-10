package com.cbmie.genMac.stock.web;

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
import com.cbmie.genMac.stock.entity.PlanStock;
import com.cbmie.genMac.stock.entity.PlanStockDetail;
import com.cbmie.genMac.stock.service.PlanStockDetailService;
import com.cbmie.genMac.stock.service.PlanStockService;

/**
 * 盘库controller
 */
@Controller
@RequestMapping("stock/planStock")
public class PlanStockController extends BaseController {


	@Autowired
	private PlanStockService planStockService;
	
	@Autowired
	private PlanStockDetailService planStockDetailService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/planStockList";
	}

	/**
	 * 获取盘库json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> planStockList(HttpServletRequest request) {
		Page<PlanStock> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = planStockService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 盘库明细跳转
	 */
	@RequestMapping(value = "detail/{parentId}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("parentId") Long parentId, Model model) {
		return "stock/planStockDetail";
	}
	
	/**
	 * 获取盘库明细json
	 */
	@RequestMapping(value = "detail/json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> planStockDetailList(HttpServletRequest request) {
		Page<PlanStockDetail> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = planStockDetailService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 盘库
	 */
	@RequestMapping(value = "planStock", method = RequestMethod.GET)
	@ResponseBody
	public String planStock() {
		if (planStockService.planStock()) {
			return "success";
		} else {
			return "盘库失败！";
		}
	}
	
	/**
	 * 提交备注
	 */
	@RequestMapping(value = "saveRemark", method = RequestMethod.POST)
	@ResponseBody
	public String saveRemark(@Valid @ModelAttribute @RequestBody PlanStockDetail planStockDetail) {
		planStockDetailService.update(planStockDetail);
		return "success";
	}
	
	@ModelAttribute
	public void getPlanStockDetail(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("planStockDetail", planStockDetailService.get(id));
		}
	}
	
}
