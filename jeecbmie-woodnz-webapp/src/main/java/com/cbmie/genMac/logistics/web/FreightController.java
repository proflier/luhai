package com.cbmie.genMac.logistics.web;

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
import com.cbmie.genMac.logistics.entity.Freight;
import com.cbmie.genMac.logistics.service.FreightGoodsService;
import com.cbmie.genMac.logistics.service.FreightService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 货代controller
 */
@Controller
@RequestMapping("logistics/freight")
public class FreightController extends BaseController{
	
	@Autowired
	private FreightService freightService;
	
	@Autowired
	private FreightGoodsService freightGoodsService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistics/freightList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> freightList(HttpServletRequest request) {
		Page<Freight> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = freightService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("freight", new Freight());
		model.addAttribute("action", "create");
		return "logistics/freightForm";
	}

	/**
	 * 添加
	 * 
	 * @param freight
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Freight freight, Model model, @RequestParam("freightGoodsJson") String freightGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		freight.setCreaterNo(currentUser.getLoginName());
		freight.setCreaterName(currentUser.getName());
		freight.setCreaterDept(currentUser.getOrganization().getOrgName());
		freight.setCreateDate(new Date());
		freightService.save(freight);
		freightGoodsService.save(freight, freightGoodsJson, currentUser);
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
		model.addAttribute("freight", freightService.get(id));
		model.addAttribute("action", "update");
		return "logistics/freightForm";
	}

	/**
	 * 修改
	 * 
	 * @param freight
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Freight freight, Model model, @RequestParam("freightGoodsJson") String freightGoodsJson) {
		User currentUser = UserUtil.getCurrentUser();
		freight.setUpdaterNo(currentUser.getLoginName());
		freight.setUpdaterName(currentUser.getName());
		freight.setCreaterDept(currentUser.getOrganization().getOrgName());
		freight.setUpdateDate(new Date());
		freightGoodsService.save(freight, freightGoodsJson, currentUser);
		freightService.update(freight);
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
		freightService.delete(id);
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
		model.addAttribute("freight", freightService.get(id));
		return "logistics/freightInfo";
	}
	
	@ModelAttribute
	public void getFreight(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("freight", freightService.get(id));
		}
	}
	
}
