package com.cbmie.lh.utils;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;

/**
 * 图表controller
 */
@Controller
@RequestMapping("catchHtmlController")
public class CatchHtmlController extends BaseController {

	/**
	 * 环渤海动力煤指数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getBSPI", method = RequestMethod.GET)
	@ResponseBody
	public String getBSPI() throws IOException{
		String returnValue = CatchHtmlUtil.bspi();
		return returnValue;
	}
	
	/**
	 * 动力煤 走势图
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getSteamCoalTrend", method = RequestMethod.GET)
	@ResponseBody
	public String steamCoalTrend() throws IOException{
		String returnValue = CatchHtmlUtil.steamCoalTrend();
		return returnValue;
	}
	
	/**
	 * 无烟 走势图
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getAnthraciteTrend", method = RequestMethod.GET)
	@ResponseBody
	public String anthraciteTrend() throws IOException{
		String returnValue = CatchHtmlUtil.anthraciteTrend();
		return returnValue;
	}
	
	/**
	 * 最新价格
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getNewPrice", method = RequestMethod.GET)
	@ResponseBody
	public String newPrice() throws IOException{
		String returnValue = CatchHtmlUtil.newPrice();
		return returnValue;
	}
}
