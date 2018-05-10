package com.cbmie.lh.mobile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("mobile/echarts")
public class ECharts {
	
	/**
	 * 测试
	 */
	@RequestMapping(value = "webview", method = RequestMethod.GET)
	public String webview(Model model) throws Exception {
		return "mobile/echarts/webview";
	}
	
	/**
	 * 测试
	 */
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test(Model model) throws Exception {
		return "mobile/echarts/test";
	}
	
}
