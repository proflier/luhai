package com.cbmie.woodNZ.baseInfo.web;

import java.util.ArrayList;
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
import com.cbmie.genMac.baseinfo.entity.WarehouseGoods;
import com.cbmie.woodNZ.stock.service.InStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.baseInfo.entity.WoodCk;
import com.cbmie.woodNZ.baseInfo.service.CkService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 仓库controller
 */
@Controller
@RequestMapping("baseInfo/ck")
public class CkController extends BaseController{
	
	
	@Autowired
	private CkService ckService;
	
	@Autowired
	private InStockService inStockService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/ckList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> warehouseList(HttpServletRequest request) {
		Page<WoodCk> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = ckService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加仓库跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		WoodCk info = new WoodCk();
		User currentUser = UserUtil.getCurrentUser();
		info.setBooker(currentUser.getName());
		info.setRegistDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("woodCk", info);
		model.addAttribute("action", "create");
		return "baseinfo/ckForm";
	}

	/**
	 * 添加仓库
	 * 
	 * @param warehouse
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WoodCk woodCk, Model model) throws JsonProcessingException {
		woodCk.setRecordDate(new Date());
		ckService.save(woodCk);
		return setReturnData("success","保存成功",woodCk.getId());
	}

	/**
	 * 修改仓库跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodCk", ckService.get(id));
		model.addAttribute("warehouseList", getWarehouseGoods(id));
		model.addAttribute("action", "update");
		return "baseinfo/ckForm";
	}
	
	/**
	 * 修改仓库
	 * 
	 * @param warehouse
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WoodCk WoodCk,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		WoodCk.setUpdater(currentUser.getLoginName());
		WoodCk.setUpdater(currentUser.getName());
		WoodCk.setUpdateDate(new Date());
		ckService.update(WoodCk);
		return setReturnData("success","保存成功",WoodCk.getId());
	}

	/**
	 * 删除仓库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		ckService.delete(id);
		return "success";
	}
	
	/**
	 * 仓库明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodCk", ckService.get(id));
		//model.addAttribute("warehouseList", getWarehouseGoods(id));
		//model.addAttribute("action", "detail");
		return "baseinfo/ckDetail";
	}
	
	
	/**
	 * 显示仓库商品列表
	 * @param id
	 * @return
	 */
	public  List<WarehouseGoods> getWarehouseGoods(Long id) {
		List<WarehouseGoods> returnList = new ArrayList<WarehouseGoods>();
		return returnList;
	}	
	
	
	@ModelAttribute
	public void getWarehouse(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("woodCk", ckService.get(id));
		}
	}
}
