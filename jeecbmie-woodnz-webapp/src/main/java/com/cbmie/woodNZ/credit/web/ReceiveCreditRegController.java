package com.cbmie.woodNZ.credit.web;

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
import com.cbmie.woodNZ.credit.entity.ReceiveCreditReg;
import com.cbmie.woodNZ.credit.service.ReceiveCreditRegService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 信用证收证登记
 * @author linxiaopeng
 * 2016年6月28日
 */
@Controller
@RequestMapping("credit/receiveCreditReg")
public class ReceiveCreditRegController extends BaseController {
	
	
	@Autowired
	private ReceiveCreditRegService receiveCreditRegService;
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "credit/receiveCreditRegList";
	}
	
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<ReceiveCreditReg> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = receiveCreditRegService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("receiveCreditReg", new ReceiveCreditReg());
		model.addAttribute("action", "create");
		return "credit/receiveCreditRegForm";
	}

	/**
	 * 添加
	 * 
	 * @param receiveCreditReg
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ReceiveCreditReg receiveCreditReg, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		receiveCreditReg.setCreaterNo(currentUser.getLoginName());
		receiveCreditReg.setCreaterName(currentUser.getName());
		receiveCreditReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		receiveCreditReg.setCreateDate(new Date());
		receiveCreditRegService.save(receiveCreditReg);
		return setReturnData("success","保存成功",receiveCreditReg.getId());
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
		model.addAttribute("receiveCreditReg", receiveCreditRegService.get(id));
		model.addAttribute("action", "update");
		return "credit/receiveCreditRegForm";
	}

	/**
	 * 修改
	 * 
	 * @param receiveCreditReg
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ReceiveCreditReg receiveCreditReg, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		receiveCreditReg.setUpdaterNo(currentUser.getLoginName());
		receiveCreditReg.setUpdaterName(currentUser.getName());
		receiveCreditReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		receiveCreditReg.setUpdateDate(new Date());
		receiveCreditRegService.update(receiveCreditReg);
		return setReturnData("success","保存成功",receiveCreditReg.getId());
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
		receiveCreditRegService.delete(id);
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
		model.addAttribute("receiveCreditReg", receiveCreditRegService.get(id));
		return "credit/receiveCreditRegDetail";
	}
	
	
	/**
	 * 下游到单配置跳转
	 */
	@RequestMapping(value = "toSaleContract", method = RequestMethod.GET)
	public String toAgencyConfig() {
		return "credit/loadSaleContract";
	}
	
	@ModelAttribute
	public void getReceiveCreditReg(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("receiveCreditReg", receiveCreditRegService.get(id));
		}
	}
	
	
}
