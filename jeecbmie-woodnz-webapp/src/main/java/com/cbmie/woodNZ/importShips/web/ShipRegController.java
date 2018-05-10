package com.cbmie.woodNZ.importShips.web;

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
import com.cbmie.woodNZ.importShips.entity.ShipReg;
import com.cbmie.woodNZ.importShips.service.ShipRegService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 船舶信息登记
 * @author linxiaopeng
 * 2016年7月6日
 */
@Controller
@RequestMapping("importShips/shipReg")
public class ShipRegController extends BaseController {
	
	
	@Autowired
	private ShipRegService shipRegService;
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "importShips/shipRegList";
	}
	
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<ShipReg> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = shipRegService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		ShipReg shipReg = new ShipReg();
		shipReg.setLastUpdateTime(new Date());
		model.addAttribute("shipReg", shipReg);
		model.addAttribute("action", "create");
		return "importShips/shipRegForm";
	}

	/**
	 * 添加
	 * 
	 * @param shipReg
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ShipReg shipReg, Model model) throws JsonProcessingException {
		if (shipRegService.findByNo(shipReg) != null) {
			return 	setReturnData("fail","船编号重复！",shipReg.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		shipReg.setCreateDate(new Date());
		shipReg.setCreaterName(currentUser.getName());
		shipReg.setCreaterNo(currentUser.getLoginName());
		shipReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		shipRegService.save(shipReg);
		
		return setReturnData("success","保存成功",shipReg.getId());
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
		model.addAttribute("shipReg", shipRegService.get(id));
		model.addAttribute("action", "update");
		return "importShips/shipRegForm";
	}

	/**
	 * 修改
	 * 
	 * @param shipReg
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ShipReg shipReg, Model model) throws JsonProcessingException {
		if (shipRegService.findByNo(shipReg) != null) {
			return setReturnData("fail","船编号重复！",shipReg.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		shipReg.setUpdaterNo(currentUser.getLoginName());
		shipReg.setUpdaterName(currentUser.getName());
		shipReg.setCreaterDept(currentUser.getOrganization().getOrgName());
		shipReg.setUpdateDate(new Date());
		shipReg.setLastUpdateTime(new Date());
		shipRegService.update(shipReg);
		return setReturnData("success","保存成功",shipReg.getId());
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
		shipRegService.delete(id);
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
		model.addAttribute("shipReg", shipRegService.get(id));
		return "importShips/shipRegDetail";
	}
	
	@ModelAttribute
	public void getShipReg(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("shipReg", shipRegService.get(id));
		}
	}
	
	
}
