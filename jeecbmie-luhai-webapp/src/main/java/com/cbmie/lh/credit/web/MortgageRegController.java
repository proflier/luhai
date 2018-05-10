package com.cbmie.lh.credit.web;

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
import com.cbmie.lh.credit.entity.MortgageReg;
import com.cbmie.lh.credit.service.MortgageRegService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 押汇登记controller
 */
@Controller
@RequestMapping("credit/mortgageReg")
public class MortgageRegController extends BaseController {

	@Autowired
	private MortgageRegService mortgageRegService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "credit/mortgageRegList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<MortgageReg> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		mortgageRegService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = mortgageRegService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		MortgageReg mortgageReg = new MortgageReg();
		mortgageReg.setCreateDate(new Date());
		mortgageReg.setUpdateDate(new Date());
		model.addAttribute("mortgageReg", mortgageReg);
		model.addAttribute("action", "create");
		return "credit/mortgageRegForm";
	}

	/**
	 * 添加
	 * 
	 * @param mortgageReg
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid MortgageReg mortgageReg, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		mortgageReg.setCreaterNo(currentUser.getLoginName());
		mortgageReg.setCreaterName(currentUser.getName());
		mortgageReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		mortgageReg.setCreateDate(new Date());
		mortgageRegService.save(mortgageReg);
		return setReturnData("success", "保存成功", mortgageReg.getId());
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
		model.addAttribute("mortgageReg", mortgageRegService.get(id));
		model.addAttribute("action", "update");
		return "credit/mortgageRegForm";
	}

	/**
	 * 修改
	 * 
	 * @param mortgageReg
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody MortgageReg mortgageReg, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		mortgageReg.setUpdaterNo(currentUser.getLoginName());
		mortgageReg.setUpdaterName(currentUser.getName());
		mortgageReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		mortgageReg.setUpdateDate(new Date());
		mortgageRegService.update(mortgageReg);
		return setReturnData("success", "保存成功", mortgageReg.getId());
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
		mortgageRegService.delete(id);
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
		model.addAttribute("mortgageReg", mortgageRegService.get(id));
		return "credit/mortgageRegDetail";
	}

	@ModelAttribute
	public void getMortgageReg(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("mortgageReg", mortgageRegService.get(id));
		}
	}

	/**
	 * 到单配置跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "toBillId", method = RequestMethod.GET)
	public String toBillId() {
		return "credit/loadBillId";
	}
}
