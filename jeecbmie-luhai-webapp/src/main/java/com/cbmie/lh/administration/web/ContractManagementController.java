package com.cbmie.lh.administration.web;

import java.util.ArrayList;
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
import com.cbmie.lh.administration.entity.ContractManagement;
import com.cbmie.lh.administration.service.ContractManagementService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 合同管理
 */
@Controller
@RequestMapping("administration/contractManagement")
public class ContractManagementController extends BaseController {

	@Autowired
	private ContractManagementService contractManagementService;
	

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "administration/contractManagementList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<ContractManagement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = contractManagementService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("contractManagement", new ContractManagement());
		model.addAttribute("action", "create");
		return "administration/contractManagementForm";
	}

	/**
	 * 添加合同管理
	 * 
	 * @param contractManagement
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ContractManagement contractManagement, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		contractManagement.setCreaterNo(currentUser.getLoginName());
		contractManagement.setCreaterName(currentUser.getName());
		contractManagement.setCreateDate(new Date());
		contractManagement.setCreaterDept(currentUser.getOrganization().getOrgName());
		contractManagementService.save(contractManagement);
		return setReturnData("success", "保存成功", contractManagement.getId());
	}

	/**
	 * 修改合同管理跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("contractManagement", contractManagementService.get(id));
		model.addAttribute("action", "update");
		return "administration/contractManagementForm";
	}

	/**
	 * 修改合同管理
	 * 
	 * @param contractManagement
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ContractManagement contractManagement, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		contractManagement.setUpdaterNo(currentUser.getLoginName());
		contractManagement.setUpdaterName(currentUser.getName());
		contractManagement.setUpdateDate(new Date());
		contractManagementService.update(contractManagement);
		return setReturnData("success", "保存成功", contractManagement.getId());
	}

	/**
	 * 删除合同管理
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		contractManagementService.delete(id);
		return "success";
	}

	/**
	 * 查看合同管理明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("contractManagement", contractManagementService.get(id));
		model.addAttribute("action", "view");
		return "administration/contractManagementForm";
	}
	
	

	@ModelAttribute
	public void getContractManagement(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("contractManagement", contractManagementService.get(id));
		}
	}
	
	/**
	 * 对应合同类别 所有合同
	 * @param contractCategory
	 * @return
	 */
	@RequestMapping(value = "loadContract/{contractCategory}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> loadContract(@PathVariable("contractCategory") String contractCategory) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		data = contractManagementService.getContract(contractCategory);
		return data;
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPdf/{id}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		ContractManagement cm = contractManagementService.getNoLoad(id);
		Map<String, Object> data = new HashMap<String, Object>();
		contractManagementService.exportPdf(data, cm);
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "contractManagement.html", "MSYH.TTF", "style.css");
		jtph.entrance(data, response, StringUtils.replaceEach(cm.getContractNo(), new String[]{" ", ","}, new String[]{"-", ""}) + ".pdf");
	}

}
