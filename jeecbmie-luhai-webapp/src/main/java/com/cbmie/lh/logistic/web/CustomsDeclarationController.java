package com.cbmie.lh.logistic.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.cbmie.common.utils.JavaToPdfHtml;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.logistic.entity.CustomsDeclaration;
import com.cbmie.lh.logistic.service.CustomsDeclarationService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

@Controller
@RequestMapping("logistic/customsDeclaration")
public class CustomsDeclarationController extends BaseController {

	@Autowired
	private CustomsDeclarationService customsDeclarationService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/customsDeclarationList";
	}
	
	/**
	 * 分页查询
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<CustomsDeclaration> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = customsDeclarationService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 新增跳转
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		CustomsDeclaration customsDeclaration = new CustomsDeclaration();
		User currentUser = UserUtil.getCurrentUser();
		customsDeclaration.setCreaterNo(currentUser.getLoginName());
		customsDeclaration.setCreaterName(currentUser.getName());
		customsDeclaration.setCreaterDept(currentUser.getOrganization().getOrgName());
		customsDeclaration.setCreateDate(new Date());
		customsDeclaration.setUpdateDate(new Date());
		model.addAttribute("customsDeclaration", customsDeclaration);
		model.addAttribute("action", "create");
		return "logistic/customsDeclarationForm";
	}
	
	/**
	 * 添加提交
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid CustomsDeclaration customsDeclaration, Model model) {
		customsDeclarationService.save(customsDeclaration);
		return setReturnData("success", "保存成功", customsDeclaration.getId());
	}
	
	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		CustomsDeclaration customsDeclaration = customsDeclarationService.get(id);
		model.addAttribute("customsDeclaration", customsDeclaration);
		model.addAttribute("action", "update");
		return "logistic/customsDeclarationForm";
	}
	
	/**
	 * 修改提交
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CustomsDeclaration customsDeclaration, Model model) {
		User currentUser = UserUtil.getCurrentUser();
		customsDeclaration.setUpdaterNo(currentUser.getLoginName());
		customsDeclaration.setUpdaterName(currentUser.getName());
		customsDeclaration.setUpdateDate(new Date());
		customsDeclarationService.update(customsDeclaration);
		return setReturnData("success", "保存成功", customsDeclaration.getId());
	}
	
	
	@ModelAttribute
	public void getContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("customsDeclaration", customsDeclarationService.get(id));
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		customsDeclarationService.delete(id);
		return setReturnData("success", "删除成功", id);
	}
	
	/**
	 * 查看明细跳转
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		CustomsDeclaration customsDeclaration = customsDeclarationService.get(id);
		model.addAttribute("customsDeclaration", customsDeclaration);
		model.addAttribute("action", "view");
		return "logistic/customsDeclarationDetail";
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPdf/{id}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		CustomsDeclaration customsDeclaration = customsDeclarationService.getNoLoad(id);
		Map<String, Object> data = new HashMap<String, Object>();
		customsDeclarationService.exportPdf(data, customsDeclaration);
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "customsDeclaration.html", "MSYH.TTF", "style.css");
		jtph.entrance(data, response, StringUtils.replaceEach(customsDeclaration.getContractNo(), new String[]{" ", ","}, new String[]{"-", ""}) + ".pdf");
	}
	
	/**
	 * 商检申报、商检放行、海关放行日期
	 */
	@RequestMapping(value = "updateFieldDate/{name}/{id}")
	@ResponseBody
	public String updateFieldDate(@PathVariable("name") String name, @PathVariable("id") Long id) {
		CustomsDeclaration customsDeclaration = customsDeclarationService.get(id);
		Date curDate = new Date();
		String msg = "保存";
		if (name.equals("declaration")) {
			customsDeclaration.setDeclarationDate(curDate);
			msg = "商检申报";
		} else if (name.equals("inspectionRelease")) {
			customsDeclaration.setInspectionReleaseDate(curDate);
			msg = "商检放行";
		} else if (name.equals("customsRelease")) {
			customsDeclaration.setCustomsReleaseDate(curDate);
			msg = "海关放行";
		}
		customsDeclarationService.save(customsDeclaration);
		return setReturnData("success", msg + "成功", id);
	}
	
}
