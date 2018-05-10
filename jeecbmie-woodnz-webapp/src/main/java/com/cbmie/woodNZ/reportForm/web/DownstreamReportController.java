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

import com.cbmie.common.web.BaseController;
import com.cbmie.system.utils.ConvertBeanAndMap;
import com.cbmie.woodNZ.reportForm.entity.DownstreamReport;
import com.cbmie.woodNZ.reportForm.service.DownstreamReportService;

/**
 * 下游交单 log controller
 */
@Controller
@RequestMapping("reportForm/downstreamBill")
public class DownstreamReportController extends BaseController{

	@Autowired
	private DownstreamReportService downstreamReportService;
	
	/**
	 * 默认页面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "reportForm/downReportList";
	}
	
	/**
	 * 获取日志json
	 */
	@RequestMapping("json")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		data= downstreamReportService.search(showParams(request));
		return data;
	}
	

	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="exportExcel")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> mapReal = new HashMap<String, Object>();
		Map<String, Object> map = showParams(request);
		if(map.containsKey("cghth")){
			mapReal.put("cghth", map.get("cghth"));
		}
		if(map.containsKey("cpContractNo")){
			mapReal.put("cpContractNo", map.get("cpContractNo"));
		}
		if(map.containsKey("billNo")){
			mapReal.put("billNo", map.get("billNo"));
		}
		if(map.containsKey("invoiceNo")){
			mapReal.put("invoiceNo", map.get("invoiceNo"));
		}
		if(map.containsKey("expressNo")){
			mapReal.put("expressNo", map.get("expressNo"));
		}
		mapReal.put("pageSize", -1);
		mapReal.put("pageIndex",0 );
		data= downstreamReportService.search(mapReal);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		dataList = (List<Map<String, Object>>) data.get("rows");
		List<DownstreamReport> downstreamReports = new ArrayList<DownstreamReport>();
		for(Map<String, Object> mapData :dataList){
			DownstreamReport downstreamReport = (DownstreamReport) ConvertBeanAndMap.convertMap(DownstreamReport.class, mapData);
			downstreamReports.add(downstreamReport);
		}
		downstreamReportService.excelLog(response, downstreamReports);
	}
	
	/**
	 * 导出excel(ALL)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="exportExcelAll")
	@ResponseBody
	public void exportExcelAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageSize", -1);
		map.put("pageIndex",0 );
		data= downstreamReportService.search(map);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		dataList = (List<Map<String, Object>>) data.get("rows");
		List<DownstreamReport> downstreamReports = new ArrayList<DownstreamReport>();
		for(Map<String, Object> mapData :dataList){
			DownstreamReport downstreamReport = (DownstreamReport) ConvertBeanAndMap.convertMap(DownstreamReport.class, mapData);
			downstreamReports.add(downstreamReport);
		}
		downstreamReportService.excelLog(response, downstreamReports);
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
