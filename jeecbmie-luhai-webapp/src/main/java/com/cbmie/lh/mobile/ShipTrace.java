package com.cbmie.lh.mobile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.logistic.service.ShipTraceService;

@Controller
@RequestMapping("mobile/shipTrace")
public class ShipTrace extends BaseController {
	
	@Autowired
	private ShipTraceService shipTraceService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) throws Exception {
		return "mobile/shipTrace/list";
	}
	
	@RequestMapping(value = "initList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<com.cbmie.lh.logistic.entity.ShipTrace> initList(HttpServletRequest request) {
		Page<com.cbmie.lh.logistic.entity.ShipTrace> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = shipTraceService.searchNoPermission(page, filters);
		return page.getResult();
	}
	
	/**
	 * 查看
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("shipTrace", shipTraceService.get(id));
		return "mobile/shipTrace/todo";
	}

}
