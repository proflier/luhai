package com.cbmie.lh.birt.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cbmie.common.utils.PropertiesLoader;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.utils.SessionListener;

/**
 * BIRT controller
 */
@Controller
@RequestMapping("report/birt")
public class BirtController extends BaseController {

	/**
	 * 默认页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "{name}", method = RequestMethod.GET)
	public String show(@PathVariable("name") String name, RedirectAttributes model) throws Exception {
		PropertiesLoader properties = new PropertiesLoader("birt.properties");
		String serverURL = properties.getProperty("serverURL");
		model.addAttribute("__report", StringUtils.replacePlaceholder(properties.getProperty("__report"), name));
		model.addAttribute("__title", properties.getProperty("__title"));
		model.addAttribute("__showtitle", properties.getProperty("__showtitle"));
		model.addAttribute("__parameterpage", properties.getProperty("__parameterpage"));

		// 此处使用AES-128-ECB加密模式，key需要为16位。
		String key = String.valueOf((long) ((Math.random() * 9 + 1) * 1000000000000000l));

		// 需要加密的字串
		String token = properties.getProperty("token");

		// 加密
		token = AES.Encrypt(token, key);

		model.addAttribute("key", key);
		model.addAttribute("token", token);

		// sessionid
		model.addAttribute("sessionid", SecurityUtils.getSubject().getSession().getId());

		return "redirect:" + serverURL;
	}

	@RequestMapping(value = "validation/{sessionid}", method = RequestMethod.GET)
	@ResponseBody
	public boolean validation(@PathVariable("sessionid") String sessionid, HttpServletRequest request) {
		Map<String, HttpSession> sessionMap = SessionListener.sessionMap;
		return sessionMap.get(sessionid) != null;
	}

}
