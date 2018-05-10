package com.cbmie.woodNZ.reportAccount.web;

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

import com.cbmie.woodNZ.reportAccount.service.PayableAccountService; 

@Controller
@RequestMapping("account/payableAccount")
public class PayableAccountController {
	
	/** 日志实例 */
	private static final Logger logger = Logger.getLogger(PayableAccountController.class);
	
	@Autowired
	protected PayableAccountService payableAccountService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(value = "html", method = RequestMethod.GET)
	public String html() {
		return "account/payableAccount";
	}
	
	@RequestMapping(value = "data", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request) {
		return payableAccountService.search(showParams(request));
//		return buildTestData(request);
	}
	
//	private Map<String, Object> buildTestData(HttpServletRequest request) {
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
//		showParams(request);
//		String sort = request.getParameter("sort");
//		String order = request.getParameter("order");
//		int page = Integer.parseInt(request.getParameter("page"));
//		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
//		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
//		String orderby = request.getParameter("orderby");
//		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
//		int rows = Integer.parseInt(request.getParameter("rows"));
//		
//		List<Map<String, Object>> data_rows = new ArrayList<Map<String, Object>>();
//		for(int i = 0; i < pageSize; i ++) {
//			int j = (page - 1) * pageSize + (i + 1);
//			
//			Map<String, Object> row_ = new HashMap<String, Object>();
//			row_.put("id", j);
//			row_.put("customername", "客户名称" + j);
//			row_.put("days", "账龄" + j);
//			row_.put("currency", "币种" + j);
//			row_.put("rmbmoney", "人民币余额" + j);
//			row_.put("usmoney", "美金余额" + j);
//			row_.put("nzmoney", "纽币余额" + j);
//			row_.put("submoney", "余额" + j);
//			row_.put("saleContractNo", "销售合同号" + j);
//			row_.put("cpContractNo", "综合合同号" + j);
//		 
//			data_rows.add(row_);
//		}
//		 
//		
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("rows", data_rows);
//		 
//		return data;
//	}

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
