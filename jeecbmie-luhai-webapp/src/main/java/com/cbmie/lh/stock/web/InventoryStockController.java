package com.cbmie.lh.stock.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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

import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.stock.entity.InventoryStock;
import com.cbmie.lh.stock.service.InventoryStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 盘库controller
 */
@Controller
@RequestMapping("stock/inventoryStock")
public class InventoryStockController extends BaseController {

	@Autowired
	private InventoryStockService inventoryStockService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/inventoryStockList";
	}

	/**
	 * 获取盘库json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<InventoryStock> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		inventoryStockService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = inventoryStockService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加盘库跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		InventoryStock inventoryStock = new InventoryStock();
		inventoryStock.setInventoryDate(new Date());
		inventoryStock.setCreateDate(new Date());
		inventoryStock.setUpdateDate(new Date());
		String inventoryNo = sequenceCodeService.getNextCode("001022").get("returnStr");
		inventoryStock.setInventoryNo(inventoryNo);
		model.addAttribute("inventoryStock", inventoryStock);
		model.addAttribute("action", "create");
		return "stock/inventoryStockForm";
	}

	/**
	 * 添加盘库
	 * 
	 * @param inventoryStock
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InventoryStock inventoryStock, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (inventoryStockService.findByNo(inventoryStock) != null) {
			String inventoryNo = sequenceCodeService.getNextCode("001022").get("returnStr");
			inventoryStock.setInventoryNo(inventoryNo);
			currnetSequence = inventoryNo;
		}
		User currentUser = UserUtil.getCurrentUser();
		inventoryStock.setCreaterNo(currentUser.getLoginName());
		inventoryStock.setCreaterName(currentUser.getName());
		inventoryStock.setCreateDate(new Date());
		inventoryStock.setUpdateDate(new Date());
		inventoryStock.setCreaterDept(currentUser.getOrganization().getOrgName());
		inventoryStock.setUserId(currentUser.getId().toString());
		inventoryStock.setDeptId(currentUser.getOrganization().getId());
		inventoryStock.setCompanyId(inventoryStockService.getCompany(currentUser.getOrganization()).getId());
		//调用获取下个sequence方法，将当前sequence保存
		if(inventoryStock.getId()==null){
			sequenceCodeService.getNextSequence("001022");
		}
		inventoryStockService.toSave(inventoryStock);
		return setReturnData("success", currnetSequence!=null?"盘库号重复，已自动生成不重复编码并保存":"保存成功", inventoryStock.getId(),currnetSequence);
	}

	/**
	 * 修改盘库跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inventoryStock", inventoryStockService.get(id));
		model.addAttribute("action", "update");
		return "stock/inventoryStockForm";
	}

	/**
	 * 修改盘库
	 * 
	 * @param inventoryStock
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InventoryStock inventoryStock, Model model)
			throws JsonProcessingException {
		if (inventoryStockService.findByNo(inventoryStock) != null) {
			return setReturnData("fail", "盘库号重复！", inventoryStock.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		inventoryStock.setUpdaterNo(currentUser.getLoginName());
		inventoryStock.setUpdaterName(currentUser.getName());
		inventoryStock.setUpdateDate(new Date());
		inventoryStockService.toSave(inventoryStock);
		return setReturnData("success", "保存成功", inventoryStock.getId());
	}

	/**
	 * 删除盘库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		inventoryStockService.deleteInventory(inventoryStockService.get(id));
		return "success";
	}

	/**
	 * 查看盘库明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inventoryStock", inventoryStockService.get(id));
		return "stock/inventoryStockDetail";
	}

	@ModelAttribute
	public void getInventoryStock(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inventoryStock", inventoryStockService.get(id));
		}
	}

	/**
	 * 选择库存明细跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadStockGodds", method = RequestMethod.GET)
	public String loadBillId() {
		return "stock/loadStockGodds";
	}

}
