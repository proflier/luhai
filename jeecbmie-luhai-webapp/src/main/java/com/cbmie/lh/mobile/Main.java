package com.cbmie.lh.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.logistic.service.ShipTraceService;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.LogService;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("mobile/main")
public class Main extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShipTraceService shipTraceService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 主页
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		//船舶动态
		request.setAttribute("sort", "desc,desc");
		request.setAttribute("order", "updateDate,createDate");
		Page<ShipTrace> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		model.addAttribute("shipTraceList", shipTraceService.searchNoPermission(page, filters).getResult());
		//活跃
		model.addAttribute("activeList", logService.countLog(0));
		return "mobile/main/index";
	}
	
	/**
	 * 通讯录列表
	 */
	@RequestMapping(value = "addr", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {
		Map<String, List<User>> map = new TreeMap<String, List<User>>();
		List<User> userList = userService.getUsersByStatus(1);//启用的用户
		for (User user : userList) {
			String convert = String.valueOf(StringUtils.getPinyinHeadChar(user.getName()).toUpperCase().charAt(0));
			List<User> list = map.get(convert);
			if (list == null) {
				list = new ArrayList<User>();
				map.put(convert, list);
			}
			list.add(user);
		}
		model.addAttribute("count", userList.size());
		model.addAttribute("map", map);
		return "mobile/main/addr";
	}
	
	@RequestMapping(value = "addrDetail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.get(id));
		return "mobile/main/addrDetail";
	}
	
	/**
	 * 我的
	 */
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public String user(Model model) {
		model.addAttribute("user", UserUtil.getCurrentUser());
		return "mobile/main/user";
	}
	
	/**
	 * 活跃
	 */
	@RequestMapping(value = "active/{days}", method = RequestMethod.GET)
	public String active(@PathVariable("days") int days, Model model) {
		model.addAttribute("activeList", logService.countLog(days));
		return "mobile/main/active";
	}
	
}
