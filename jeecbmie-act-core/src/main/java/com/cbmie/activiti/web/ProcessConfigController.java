package com.cbmie.activiti.web;

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

import com.cbmie.activiti.entity.ProcessConfig;
import com.cbmie.activiti.service.ProcessConfigService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("activiti/config")
public class ProcessConfigController extends BaseController {

	@Autowired
	private ProcessConfigService processConfigService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "activiti/processConfigList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<ProcessConfig> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = processConfigService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		ProcessConfig processConfig = new ProcessConfig();
		model.addAttribute("processConfig", processConfig);
		model.addAttribute("action", "create");
		return "activiti/processConfigForm";
	}

	/**
	 * 添加
	 * 
	 * @param processConfig
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ProcessConfig processConfig, Model model) throws JsonProcessingException {
		processConfigService.save(processConfig);
		return setReturnData("success", "保存成功", processConfig.getId());
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
		model.addAttribute("processConfig", processConfigService.get(id));
		model.addAttribute("action", "update");
		return "activiti/processConfigForm";
	}

	/**
	 * 修改
	 * 
	 * @param processConfig
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ProcessConfig processConfig, Model model)
			throws JsonProcessingException {
		processConfigService.update(processConfig);
		return setReturnData("success", "保存成功", processConfig.getId());
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
		processConfigService.delete(id);
		return "success";
	}
	
	/**
	 * 生效
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "effect/{id}")
	@ResponseBody
	public String effect(@PathVariable("id") Long id) {
		ProcessConfig processConfig = processConfigService.get(id);
		processConfig.setState("1");
		processConfigService.update(processConfig);
		return "success";
	}
	
	/**
	 * 生效
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "cancelEeffect/{id}")
	@ResponseBody
	public String cancelEeffect(@PathVariable("id") Long id) {
		ProcessConfig processConfig = processConfigService.get(id);
		processConfig.setState("0");
		processConfigService.update(processConfig);
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
		model.addAttribute("processConfig", processConfigService.get(id));
		return "activiti/processConfigDetail";
	}

	@ModelAttribute
	public void getProcessConfig(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("processConfig", processConfigService.get(id));
		}
	}

}
