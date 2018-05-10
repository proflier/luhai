package com.cbmie.system.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

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

import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.CountryArea;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.CountryAreaService;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 国别区域controller
 */
@Controller
@RequestMapping("system/countryArea")
public class CountryAreaController extends BaseController{

	@Autowired
	private CountryAreaService countryAreaService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/countryAreaList";
	}
	
	/**
	 * 获取国别区域json
	 */
//	@RequiresPermissions("sys:countryArea:view")
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public List<CountryArea> countryAreaList(HttpServletRequest request) {
		List<CountryArea> countryAreaList = countryAreaService.getAll();
		return countryAreaList;
	}
	
	/**
	 * 添加国别区域跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		CountryArea countryArea = new CountryArea();
		String code = sequenceCodeService.getNextCode("003003").get("returnStr");
		countryArea.setCode(code);
		model.addAttribute("countryArea", countryArea);
		model.addAttribute("action", "create");
		return "system/countryAreaForm";
	}

	/**
	 * 添加国别区域
	 * 
	 * @param countryArea
	 * @param model
	 * @throws JsonProcessingException 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid CountryArea countryArea, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (countryAreaService.findByNo(countryArea) != null) {
			String code = sequenceCodeService.getNextCode("003003").get("returnStr");
			countryArea.setCode(code);
			currnetSequence = code;
		}
		if (countryAreaService.findByName(countryArea) != null) {
			return setReturnData("fail","名称重复",countryArea.getId()!=null?Long.valueOf(countryArea.getId()):null);
		}
		User currentUser = UserUtil.getCurrentUser();
		countryArea.setRegistrant(currentUser.getName());
		countryArea.setRegistrantDept(currentUser.getOrganization().getOrgName());
		countryArea.setRegistrantDate(new Date());
		if(countryArea.getId()==null){
			//调用获取下个sequence方法，将当前sequence保存
			sequenceCodeService.getNextSequence("003003");
		}
		countryAreaService.save(countryArea);
		return setReturnData("success", currnetSequence!=null?"编码重复，已自动生成不重复编码并保存":"保存成功", Long.valueOf(countryArea.getId()),currnetSequence);
	}

	/**
	 * 修改国别区域跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("countryArea", countryAreaService.get(id));
		model.addAttribute("action", "update");
		return "system/countryAreaForm";
	}

	/**
	 * 修改国别区域
	 * 
	 * @param countryArea
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CountryArea countryArea, Model model) throws JsonProcessingException {
		if (countryAreaService.findByName(countryArea) != null) {
			return setReturnData("fail","名称重复",countryArea.getId()!=null?Long.valueOf(countryArea.getId()):null);
		}
		countryAreaService.update(countryArea);
		return setReturnData("success","保存成功",Long.valueOf(countryArea.getId()));
	}

	/**
	 * 删除国别区域
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		countryAreaService.delete(id);
		return "success";
	}
	
	/**
	 * 查看国别区域跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Integer id, Model model) {
		CountryArea countryArea = countryAreaService.get(id);
		model.addAttribute("countryArea", countryArea);
		if (countryArea.getPid() != null) {
			CountryArea pCountryArea = countryAreaService.get(countryArea.getPid());
			model.addAttribute("pName", pCountryArea.getName());
		}
		return "system/countryAreaDetail";
	}
	
	/**
	 * 获取国别区域名称
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getName/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable("id") Integer id, Model model) {
		CountryArea countryArea = countryAreaService.get(id);
		return countryArea.getName();
	}
	
	@ModelAttribute
	public void getCountry(@RequestParam(value = "id", defaultValue = "-1") Integer id,Model model) {
		if (id != -1) {
			model.addAttribute("countryArea", countryAreaService.get(id));
		}
	}
	
}
