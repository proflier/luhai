package com.cbmie.woodNZ.reportForm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.woodNZ.logistics.service.BillsService;
import com.cbmie.woodNZ.reportForm.entity.BillsReport;
import com.cbmie.woodNZ.reportForm.service.BillsReportService;

/**
 * 上游到单 bill controller
 */
@Controller
@RequestMapping("reportForm/bill")
public class BillsReportController extends BaseController{

	@Autowired
	private BillsReportService billReportService;
	
	@Autowired
	private BillsService billsService;
	/** 
	 * 默认页面
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(){
		return "reportForm/billReportList";
	}
	
	/**
	 * 获取日志json
	 */
	@RequestMapping("json")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		Page<BillsReport> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		List<BillsReport> billReportList = billReportService.getBillReportList(filters);
		List<BillsReport> newList = new ArrayList<BillsReport>();
		int firstCount = page.getPageSize()*(page.getPageNo()-1);
		int endCount = firstCount+page.getPageSize();
		for(int i=firstCount;i<billReportList.size();i++){
			if(i >= endCount){
				break;
			}
			newList.add(billReportList.get(i));
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("rows", newList);
		map.put("total", billReportList.size());
		return map;
	}
	

	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param pageNo  第几页
	 * @throws Exception
	 */ 
	@RequestMapping(value="exportExcel/{pageNo}")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("pageNo") int pageNo){
		Page<BillsReport> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		List<BillsReport> billReportList = billReportService.getBillReportList(filters);
		List<BillsReport> newList = new ArrayList<BillsReport>();
		int firstCount = page.getPageSize()*(pageNo-1);
		int endCount = firstCount+page.getPageSize();
		for(int i=firstCount;i<billReportList.size();i++){
			if(i >= endCount){
				break;
			}
			newList.add(billReportList.get(i));
		}
        billReportService.excelLog(response, newList);
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @param pageNo  第几页
	 * @throws Exception
	 */ 
	@RequestMapping(value="exportExcelAll")
	@ResponseBody
	public void exportExcelAll(HttpServletRequest request, HttpServletResponse response){
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		List<BillsReport> billReportList = billReportService.getBillReportList(filters);
        billReportService.excelLog(response, billReportList);
	}
}
