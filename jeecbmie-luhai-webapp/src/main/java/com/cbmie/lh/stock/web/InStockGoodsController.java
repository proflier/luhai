package com.cbmie.lh.stock.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.entity.InStockGoods;
import com.cbmie.lh.stock.service.InStockGoodsService;
import com.cbmie.lh.stock.service.InStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 入库明细controller
 */
@Controller
@RequestMapping("stock/inStockGoods")
public class InStockGoodsController extends BaseController {

	@Autowired
	private InStockGoodsService inStockGoodsService;
	
	@Autowired
	private InStockService inStockService;

	/**
	 * 获取当前库存剩余
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getAll(HttpServletRequest request) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
//		data = inStockGoodsService.getCurrency(showParams(request));
		data = inStockGoodsService.getCurrentStockGoods(showParams(request));
		return data;
	}
	
	/**
	 * 获取当前库存剩余
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAllByDeliveryNo/{deliveryNo}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getAllByDeliveryNo(@PathVariable("deliveryNo") String deliveryNo) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		data = inStockGoodsService.getAllByDeliveryNo(deliveryNo);
		return data;
	}
	
	/**
	 * 获取当前库存剩余
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAllBySaleNo/{saleNo}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getAllBySaleNo(@PathVariable("saleNo") String saleNo) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		data = inStockGoodsService.getAllBySaleNo(saleNo);
		return data;
	}

	/**
	 * 根据id获取入库明细json
	 */
	@RequestMapping(value = "getInstockGodds/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<InStockGoods> goodsList(@PathVariable("id") String id) {
		return inStockGoodsService.goodsList(id);
	}

	/**
	 * 添加入库明细跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model, @PathVariable("id") String id) {
		InStockGoods inStockGoods = new InStockGoods();
		inStockGoods.setParentId(id);
		model.addAttribute("inStockGoods", inStockGoods);
		model.addAttribute("actionGoods", "create");
		return "stock/inStockGoodsForm";
	}

	/**
	 * 添加入库明细
	 * 
	 * @param inStockGoods
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InStockGoods inStockGoods, Model mode) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		inStockGoods.setCreaterNo(currentUser.getLoginName());
		inStockGoods.setCreaterName(currentUser.getName());
		inStockGoods.setCreateDate(new Date());
		inStockGoods.setUpdateDate(new Date());
		inStockGoods.setSummary("入库-入库明细[" + inStockGoods.getGoodsName() + "]");
		inStockGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		inStockGoodsService.save(inStockGoods);
		return setReturnData("success", "保存成功", inStockGoods.getId());
	}

	/**
	 * 修改入库明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inStockGoods", inStockGoodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "stock/inStockGoodsForm";
	}

	/**
	 * 修改入库明细
	 * 
	 * @param inStockGoods
	 * @param model
	 * @param goodsIndexJson
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InStockGoods inStockGoods, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		inStockGoods.setUpdaterNo(currentUser.getLoginName());
		inStockGoods.setUpdaterName(currentUser.getName());
		inStockGoods.setUpdateDate(new Date());
		inStockGoods.setSummary("入库-入库明细[" + inStockGoods.getGoodsName() + "]");
		inStockGoodsService.update(inStockGoods);
		return setReturnData("success", "保存成功", inStockGoods.getId());
	}

	/**
	 * 删除入库明细
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		inStockGoodsService.delete(id);
		return "success";
	}

	@ModelAttribute
	public void getInStockGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inStockGoods", inStockGoodsService.get(id));
		}
	}
	
	/**
	 * 保存子表
	 * @param billNo
	 * @param parentId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "saveGoods/{billNo}/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public String saveGoods( @PathVariable("billNo")String billNo,@PathVariable("parentId")String parentId) throws Exception{
		if (inStockService.findByBillId(billNo,Long.valueOf(parentId)) != null) {
			return setReturnData("fail", "同一提单重复入库！", Long.valueOf(parentId));
		}
		InStock inStock = new InStock();
		inStock = inStockService.get(Long.valueOf(parentId));
		inStock.setBillNo(billNo);
		inStockGoodsService.saveGood(billNo,parentId);
		inStockService.update(inStock);
		return setReturnData("success", "保存成功！", inStock.getId());
	} 

	/**
	 * 根据request获取过滤参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, Object> showParams(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		Set<Map.Entry<String, Object>> set = map.entrySet();
		for (Map.Entry entry : set) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		return map;
	}

}
