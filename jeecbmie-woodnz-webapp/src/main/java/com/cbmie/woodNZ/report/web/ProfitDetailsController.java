package com.cbmie.woodNZ.report.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.woodNZ.report.service.ProfitDetailsService;

@Controller
@RequestMapping("report/profitDetails")
public class ProfitDetailsController {
	
	/** 日志实例 */
	private static final Logger logger = Logger.getLogger(ProfitDetailsController.class);
	
	@Autowired
	protected ProfitDetailsService profitDetailsService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(value = "html", method = RequestMethod.GET)
	public String html() {
		return "report/profitDetailsReport";
	}
	
	@RequestMapping(value = "data", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request) {
		return profitDetailsService.search(showParams(request));
//		return buildTestData(request);
	}
	
	private Map<String, Object> buildTestData(HttpServletRequest request) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
		showParams(request);
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		int page = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		String orderby = request.getParameter("orderby");
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int rows = Integer.parseInt(request.getParameter("rows"));
		
		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < pageSize; i ++) {
			int j = (page - 1) * pageSize + (i + 1);
			
			Map<String, Object> row_ = new HashMap<String, Object>();
			row_.put("id", j);
			row_.put("contract", "ZHHTH" + j);
			row_.put("goodType", "商品类别" + j);
			row_.put("goodName", "商品名称" + j);
			row_.put("tradeType", "贸易类型" + j);
			row_.put("tradeWay", "贸易方式" + j);
			row_.put("unit", "实物量单位" + j);
			row_.put("salesQuantity", "销售数量" + j);
			row_.put("income", "主营业务收入" + j);
			row_.put("price", "进价" + j);
			row_.put("transpot", "运杂费" + j);
			row_.put("profit", "商品利润" + j);
			data_rows.add(row_);
		}
		List<Map<String, Object>> data_footer = new ArrayList<Map<String, Object>>();
		Map<String, Object> data_footer_row1 = new HashMap<String, Object>();
		data_footer_row1.put("id", "平均值：");
		data_footer_row1.put("salesQuantity", 100);
		data_footer_row1.put("income", 200);
		data_footer_row1.put("price", 300);
		data_footer_row1.put("transpot", 400);
		data_footer_row1.put("profit", 500);
		Map<String, Object> data_footer_row2 = new HashMap<String, Object>();
		data_footer_row2.put("id", "合计：");
		data_footer_row2.put("salesQuantity", 100);
		data_footer_row2.put("income", 200);
		data_footer_row2.put("price", 300);
		data_footer_row2.put("transpot", 400);
		data_footer_row2.put("profit", 500);
		data_footer.add(data_footer_row1);
		data_footer.add(data_footer_row2);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", data_rows);
		data.put("total", 1000000);
		data.put("footer", data_footer);
		return data;
	}

	private Map<String, Object> showParams(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}

		Set<Map.Entry<String, Object>> set = map.entrySet();
		System.out.println("------------------------------");
		for (Map.Entry entry : set) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		System.out.println("------------------------------");
		return map;
	}
}
