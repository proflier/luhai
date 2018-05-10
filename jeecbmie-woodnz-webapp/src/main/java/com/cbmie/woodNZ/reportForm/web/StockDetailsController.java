package com.cbmie.woodNZ.reportForm.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.system.utils.ConvertBeanAndMap;
import com.cbmie.woodNZ.reportForm.entity.StockDetail;
import com.cbmie.woodNZ.reportForm.service.StockDetailsService;

@Controller
@RequestMapping("reportForm/stockDetail")
public class StockDetailsController {
	
	
	@Autowired
	protected StockDetailsService stockDetailsService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String html() {
		return "reportForm/stockDetailsReport";
	}
	
	@RequestMapping(value = "data", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		data= stockDetailsService.search(showParams(request));
		return data;
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> mapReal = new HashMap<String, Object>();
		Map<String, Object> map = showParams(request);
		if(map.containsKey("receiveWarehouse")){
			mapReal.put("receiveWarehouse", map.get("receiveWarehouse"));
		}
		mapReal.put("pageSize", -1);
		mapReal.put("pageIndex",0 );
		data= stockDetailsService.search(mapReal);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		dataList = (List<Map<String, Object>>) data.get("rows");
		List<StockDetail> stockDetailList = new ArrayList<StockDetail>();
		for(Map<String, Object> mapData :dataList){
			StockDetail stockDetail = (StockDetail) ConvertBeanAndMap.convertMap(StockDetail.class, mapData);
			stockDetailList.add(stockDetail);
		}
		stockDetailsService.excelLog(response, stockDetailList);
	}
	
	/**
	 * 导出excelAll
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exportExcelAll")
	@ResponseBody
	public void exportExcelAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageSize", -1);
		map.put("pageIndex",0 );
		data= stockDetailsService.search(map);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		dataList = (List<Map<String, Object>>) data.get("rows");
		List<StockDetail> stockDetailList = new ArrayList<StockDetail>();
		for(Map<String, Object> mapData :dataList){
			StockDetail stockDetail = (StockDetail) ConvertBeanAndMap.convertMap(StockDetail.class, mapData);
			stockDetailList.add(stockDetail);
		}
		stockDetailsService.excelLog(response, stockDetailList);
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
