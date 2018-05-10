package com.cbmie.woodNZ.offerManagement.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.offerManagement.entity.ProflitLossAccounting;
import com.cbmie.woodNZ.offerManagement.service.ProflitLossAccountingService;


/**
 *  盈亏核算
 * @author linxiaopeng
 * 2016年7月8日
 */
@Controller
@RequestMapping("offerManagement/proflitLossAccounting")
public class ProflitLossAccountingController extends BaseController {
	
	
	@Autowired
	private ProflitLossAccountingService proflitLossAccountingService;
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "offerManagement/proflitLossList";
	}
	
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> openCreditList(HttpServletRequest request) {
		Page<ProflitLossAccounting> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = proflitLossAccountingService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("proflitLossAccounting", new ProflitLossAccounting());
		model.addAttribute("action", "create");
		return "offerManagement/proflitLossForm";
	}

	/**
	 * 添加
	 * 
	 * @param proflitLossAccounting
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ProflitLossAccounting proflitLossAccounting, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		proflitLossAccounting.setCreaterNo(currentUser.getLoginName());
		proflitLossAccounting.setCreaterName(currentUser.getName());
		proflitLossAccounting.setCreaterDept(currentUser.getOrganization().getOrgName());
		proflitLossAccounting.setCreateDate(new Date());
		proflitLossAccountingService.save(proflitLossAccounting);
		return "success";
	}

	/**
	 * 修改跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("proflitLossAccounting", proflitLossAccountingService.get(id));
		model.addAttribute("action", "update");
		return "offerManagement/proflitLossForm";
	}

	/**
	 * 修改
	 * 
	 * @param proflitLossAccounting
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ProflitLossAccounting proflitLossAccounting, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		proflitLossAccounting.setUpdaterNo(currentUser.getLoginName());
		proflitLossAccounting.setUpdaterName(currentUser.getName());
		proflitLossAccounting.setUpdaterDept(currentUser.getOrganization().getOrgName());
		proflitLossAccounting.setUpdateDate(new Date());
		proflitLossAccountingService.update(proflitLossAccounting);
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		proflitLossAccountingService.delete(id);
		return "success";
	}
	
	/**
	 * 查看明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("proflitLossAccounting", proflitLossAccountingService.get(id));
		return "offerManagement/proflitLossDetail";
	}
	
	@ModelAttribute
	public void getProflitLossAccounting(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("proflitLossAccounting", proflitLossAccountingService.get(id));
		}
	}
	
	
}
