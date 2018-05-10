package com.cbmie.woodNZ.stock.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.woodNZ.stock.entity.StockStatistic;
import com.cbmie.woodNZ.stock.service.StockStatisticService;
@Controller
@RequestMapping("stock/stockStatistic")
public class StockStatisticController extends BaseController {
	@Autowired
	private StockStatisticService statisticService;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/stockStatistic";
	}
	
	/**
	 * 获取入库json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<StockStatistic> page = getPage(request);
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = statisticService.search(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getEasyUIData(page);
	}
}
