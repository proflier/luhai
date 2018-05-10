package com.cbmie.baseinfo.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Date;
import java.util.HashMap;
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

import com.cbmie.baseinfo.entity.SequenceCode;
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 编码生成controller
 */
@Controller
@RequestMapping("system/sequenceCode")
public class SequenceCodeController extends BaseController {

	@Autowired
	private SequenceCodeService sequenceCodeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sequenceCode/sequenceCodeList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<SequenceCode> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = sequenceCodeService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("sequenceCode", new SequenceCode());
		model.addAttribute("action", "create");
		return "sequenceCode/sequenceCodeForm";
	}

	/**
	 * 添加
	 * 
	 * @param sequenceCode
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SequenceCode sequenceCode, Model model) throws JsonProcessingException {
		if (sequenceCodeService.findBySequenceCode(sequenceCode)!= null) {
			return setReturnData("fail", "模块英文名重复", sequenceCode.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		sequenceCode.setCreaterNo(currentUser.getLoginName());
		sequenceCode.setCreaterName(currentUser.getName());
		sequenceCode.setCreaterDept(currentUser.getOrganization().getOrgName());
		sequenceCode.setCreateDate(new Date());
		sequenceCodeService.save(sequenceCode);
		return setReturnData("success", "保存成功", sequenceCode.getId());
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
		model.addAttribute("sequenceCode", sequenceCodeService.get(id));
		model.addAttribute("action", "update");
		return "sequenceCode/sequenceCodeForm";
	}

	/**
	 * 修改
	 * 
	 * @param sequenceCode
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SequenceCode sequenceCode, Model model)
			throws JsonProcessingException {
		if (sequenceCodeService.findBySequenceCode(sequenceCode)!= null) {
			return setReturnData("fail", "模块英文名重复", sequenceCode.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		sequenceCode.setUpdaterNo(currentUser.getLoginName());
		sequenceCode.setUpdaterName(currentUser.getName());
		sequenceCode.setCreaterDept(currentUser.getOrganization().getOrgName());
		sequenceCode.setUpdateDate(new Date());
		sequenceCodeService.update(sequenceCode);
		return setReturnData("success", "保存成功", sequenceCode.getId());
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
		sequenceCodeService.delete(id);
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
		model.addAttribute("sequenceCode", sequenceCodeService.get(id));
		model.addAttribute("action", "view");
		return "sequenceCode/sequenceCodeForm";
	}
	
	
	/**
	 * 获取下个编码数据库不变化
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "nextCodeNoSave/{param}", method = RequestMethod.GET)
	@ResponseBody
	public String nextCodeNoSave(@PathVariable("param") String param) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		return sequenceCodeService.getNextCode(param).get("returnStr");
	}
	
	
	/**
	 * 获取下个编码数据库变化
	 */
	@RequestMapping(value = "nextCodeSave/{param}", method = RequestMethod.GET)
	@ResponseBody
	public String nextCodeSave(@PathVariable("param") String param) {
		return sequenceCodeService.getNextSequence(param).toString();
		 
	}
	
	/**
	 * 判断当前是否最新code，是回退一个
	 * @param param 公式对应参数
	 * @param paramCode  当前生成编码
	 * @return success成功回退   fail未回退
	 */
	@RequestMapping(value = "isNewCode/{param}/{currentValue}", method = RequestMethod.GET)
	@ResponseBody
	public String nextCodeSave(@PathVariable("param") String param,@PathVariable("currentValue") String paramCode) {
		SequenceCode sequenceCode = new SequenceCode();
		sequenceCode = sequenceCodeService.findBySequenceCode(param);
		String currentCode = sequenceCodeService.getCurrentCode(param);
		
		Map<String , String > returnMap = new HashMap<String, String>();
		returnMap = sequenceCodeService.transFormula(sequenceCode.getFormula(), param);
		//获取流水长度
		Integer flow = Integer.valueOf(returnMap.get("FLOW"));
		String flowFormate = "";
		for(int i=0;i<=flow-1;i++){
			flowFormate = flowFormate + "0";
		}
		Format f1 = new DecimalFormat(flowFormate);
		String flowStr = f1.format(sequenceCode.getValue());
		//获取开始位置
		Integer startIndex= currentCode.indexOf(flowStr);
		Integer paramFlow = Integer.valueOf(paramCode.substring(startIndex, startIndex+flow));
		//判断传入code是否为最新code
		if(sequenceCode.getValue().equals(paramFlow)){
			sequenceCodeService.updateSequence(param, sequenceCode.getValue()-1);
			return "success";
		}else{
			return "fail";
		}
		
	}
	
	
	@ModelAttribute
	public void getSequenceCode(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("sequenceCode", sequenceCodeService.get(id));
		}
	}

}
