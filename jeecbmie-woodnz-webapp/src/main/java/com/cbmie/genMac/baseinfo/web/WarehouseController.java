package com.cbmie.genMac.baseinfo.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.baseinfo.entity.Warehouse;
import com.cbmie.genMac.baseinfo.entity.WarehouseGoods;
import com.cbmie.genMac.baseinfo.service.WarehouseService;
import com.cbmie.woodNZ.stock.entity.InStock;
import com.cbmie.woodNZ.stock.entity.InStockGoods;
import com.cbmie.woodNZ.stock.service.InStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 仓库controller
 */
@Controller
@RequestMapping("baseinfo/warehouse")
public class WarehouseController extends BaseController{
	
	
	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
	private InStockService inStockService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/warehouseList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> warehouseList(HttpServletRequest request) {
		Page<Warehouse> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = warehouseService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加仓库跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("warehouse", new Warehouse());
		model.addAttribute("action", "create");
		return "baseinfo/warehouseForm";
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
	public String create(@Valid Warehouse warehouse, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		warehouse.setCreaterNo(currentUser.getLoginName());
		warehouse.setCreaterName(currentUser.getName());
		warehouse.setCreateDate(new Date());
		warehouse.setCreaterDept(currentUser.getOrganization().getOrgName());
		warehouseService.save(warehouse);
		return setReturnData("success","保存成功",warehouse.getId());
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
		model.addAttribute("warehouse", warehouseService.get(id));
		model.addAttribute("warehouseList", getWarehouseGoods(id));
		model.addAttribute("action", "update");
		return "baseinfo/warehouseForm";
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "listDetail/{id}/{filters}")
	public String updateResetForm(Model model,@PathVariable("id") Long id, @PathVariable("filters") String filters) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		Map<String, Object> filterParamMap = new HashMap<String, Object>();
		filterParamMap.put("LIKES_inStockId_OR_invoiceNo", filters);
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = (String) entry.getValue();
			//如果value值为空,则忽略此filter.
			if (StringUtils.isNotBlank(value)) {
				PropertyFilter filter = new PropertyFilter(filterName, value);
				filterList.add(filter);
			}
		}
		List<WarehouseGoods> warehouseGoodsList = new ArrayList<WarehouseGoods>();
		List<WarehouseGoods> warehouseGoodsFinal = new ArrayList<WarehouseGoods>();
		warehouseGoodsList = getWarehouseGoods(id);
		List<InStock> inStockList = inStockService.search(filterList);
//		for(WarehouseGoods warehouseGoods : warehouseGoodsList){
//			for(InStock inStock : inStockList){
//				if(warehouseGoods.getWareHouseGoodsId().equals(inStock.getInStockId()+"_"+inStock.getInvoiceNo())){
//					warehouseGoodsFinal.add(warehouseGoods);
//				}
//			}
//		}
		model.addAttribute("warehouse", warehouseService.get(id));
		model.addAttribute("warehouseList", warehouseGoodsFinal);
		model.addAttribute("action", "detail");
		return "baseinfo/warehouseDetail";
	}

	/**
	 * 修改仓库
	 * 
	 * @param warehouse
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Warehouse warehouse,Model model) {
		User currentUser = UserUtil.getCurrentUser();
		warehouse.setUpdaterNo(currentUser.getLoginName());
		warehouse.setUpdaterName(currentUser.getName());
		warehouse.setUpdateDate(new Date());
		warehouseService.update(warehouse);
		return "success";
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
		warehouseService.delete(id);
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
		model.addAttribute("warehouse", warehouseService.get(id));
		model.addAttribute("warehouseList", getWarehouseGoods(id));
		model.addAttribute("action", "detail");
		return "baseinfo/warehouseDetail";
	}
	
	/**
	 * 根据仓库id获取仓库名称
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getWarehouseName/{id}")
	@ResponseBody
	public String getWarehouseName(@PathVariable("id") Long id) {
		return warehouseService.get(id).getWarehouseName();
	}
	
	/**
	 * 显示仓库商品列表
	 * @param id
	 * @return
	 */
	public  List<WarehouseGoods> getWarehouseGoods(Long id) {
		List<WarehouseGoods> returnList = new ArrayList<WarehouseGoods>();
//		try {
//			WarehouseGoods warehouseGoods = null;
//			WarehouseGoods warehouseGoodsAdd = null;
//			List<InStock> inStockList = inStockService.getAll();
//			for(InStock inStock : inStockList){
//				if(inStock.getStorageUnit().equals(String.valueOf(id))){
//					warehouseGoods = new WarehouseGoods();
//					BeanUtils.copyProperties(warehouseGoods, inStock);
//					for(InStockGoods inStockGoods:inStock.getInStockGoods()){
//						warehouseGoodsAdd = new WarehouseGoods();
//						BeanUtils.copyProperties(warehouseGoodsAdd, warehouseGoods);
//						BeanUtils.copyProperties(warehouseGoodsAdd, inStockGoods);
//						warehouseGoodsAdd.setNote(inStockGoods.getGoodsCategory()+"  "+inStockGoods.getSpecification()+"  "+inStockGoods.getCname());
//						warehouseGoodsAdd.setWareHouseGoodsId(inStock.getInStockId()+"_"+inStock.getInvoiceNo());
//						returnList.add(warehouseGoodsAdd);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return returnList;
	}	
	@ModelAttribute
	public void getWarehouse(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("warehouse", warehouseService.get(id));
		}
	}
	
}
