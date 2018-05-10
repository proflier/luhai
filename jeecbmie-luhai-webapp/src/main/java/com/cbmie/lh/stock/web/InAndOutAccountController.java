package com.cbmie.lh.stock.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.stock.service.InAndOutAccountService;

/**
 * 进销存台账controller
 */
@Controller
@RequestMapping("stock/inAndOutAccount")
public class InAndOutAccountController extends BaseController {

	@Autowired
	private InAndOutAccountService inAndOutAccountService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/inAndOutAccountList";
	}

	/**
	 * 获取入库json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Map<String, Object> data = new HashMap<String, Object>();
		data = inAndOutAccountService.getCurrency(showParams(request));
		return data;
	}


	@ModelAttribute
	public void getInAndOutAccount(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inAndOutAccount", inAndOutAccountService.get(id));
		}
	}
	
	/**
	 * 获取当前仓库结余数量
	 */
	@RequestMapping(value = "getSurplusQuantity/{warehouseName}", method = RequestMethod.GET)
	@ResponseBody
	public String getSurplusQuantity(HttpServletRequest request,@PathVariable("warehouseName") String warehouseName) {
		Double returnValue =  inAndOutAccountService.getSurplusQuantity(showParams(request,warehouseName));
		DecimalFormat df = new DecimalFormat("######0.000");   
		return df.format(returnValue);
	}
	
	/**
	 * 根据request获取过滤参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
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
		for (Map.Entry entry : set) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	private Map<String, Object> showParams(HttpServletRequest request,String warehouseName) {
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
		for (Map.Entry entry : set) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		if(!map.containsKey("warehouseName")){
			map.put("warehouseName", warehouseName);
		}
		return map;
	}

}
