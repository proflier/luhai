package com.cbmie.baseinfo.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.cbmie.baseinfo.entity.WoodGk;
import com.cbmie.baseinfo.service.GkService;
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 港口controller
 */
@Controller
@RequestMapping("baseInfo/woodGk")
public class GkController extends BaseController{
	
	
	@Autowired
	private GkService woodGkService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/gkList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> portList(HttpServletRequest request) {
		Page<WoodGk> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = woodGkService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加港口跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		WoodGk info = new WoodGk();
		User currentUser = UserUtil.getCurrentUser();
		info.setBooker(currentUser.getLoginName());
		info.setRegistDept(currentUser.getOrganization().getOrgName());
		String bm = sequenceCodeService.getNextCode("001006").get("returnStr");
		info.setBm(bm);
		model.addAttribute("woodGk", info);
		model.addAttribute("action", "create");
		return "baseinfo/gkForm";
	}

	/**
	 * 添加港口
	 * 
	 * @param port
	 * @param model
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WoodGk woodGk, Model model) throws ParseException, JsonProcessingException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date time=sdf.parse(sdf.format(new Date()));
		woodGk.setRecordDate(time);
		woodGkService.save(woodGk);
		//调用获取下个sequence方法，将当前sequence保存
		sequenceCodeService.getNextSequence("001006");
		return setReturnData("success","保存成功",woodGk.getId());
	}

	/**
	 * 修改港口跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodGk", woodGkService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/gkForm";
	}

	/**
	 * 修改港口
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WoodGk woodGk,Model model) throws JsonProcessingException {
		woodGk.setUpdateDate(new Date());
		woodGkService.update(woodGk);
		return setReturnData("success","保存成功",woodGk.getId());
	}

	/**
	 * 删除港口
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		woodGkService.delete(id);
		return "success";
	}
	
	/**
	 * 港口明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodGk", woodGkService.get(id));
		model.addAttribute("action", "detail");
		return "baseinfo/gkDetail";
	}
	
	/**
	 * 获取所有港口
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getPortList")
	@ResponseBody
	public List<WoodGk> getDictByCode() {
		List<WoodGk> portList = woodGkService.getAll();
		return portList;
	}
	
	
	
	@ModelAttribute
	public void getPort(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("port", woodGkService.get(id));
		}
	}
	
}
