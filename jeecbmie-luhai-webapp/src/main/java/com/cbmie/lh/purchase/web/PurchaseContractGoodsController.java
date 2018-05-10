package com.cbmie.lh.purchase.web;

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
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;
import com.cbmie.lh.purchase.service.PurchaseContractGoodsService;
import com.cbmie.lh.purchase.service.PurchaseGoodsIndexService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 采购合同-进口controller
 */
@Controller
@RequestMapping("purchase/purchaseContractGoods")
public class PurchaseContractGoodsController extends BaseController {

	@Autowired
	private PurchaseContractGoodsService purchaseContractGoodsService;

	@Autowired
	private PurchaseGoodsIndexService purchaseGoodsIndexService;

	/**
	 * 获取采购合同-子表json
	 */
	@RequestMapping(value = "getContractGodds/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseContractGoods> goodsList(@PathVariable("id") long id) {
		return purchaseContractGoodsService.getContractGodds(id);
	}
	
	/**
	 * 获取jsonNoPermission
	 */
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityListNoPermission(HttpServletRequest request) {
		Page<PurchaseContractGoods> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = purchaseContractGoodsService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加采购合同-子表跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model, @PathVariable("id") long id) {
		PurchaseContractGoods purchaseContractGoods = new PurchaseContractGoods();
		purchaseContractGoods.setPurchaseContractId(id);
		model.addAttribute("purchaseContractGoods", purchaseContractGoods);
		model.addAttribute("actionGoods", "create");
		return "purchase/contractGoodsForm";
	}

	/**
	 * 添加采购合同-子表
	 * 
	 * @param purchaseContractGoods
	 * @param model
	 * @param goodsIndexJson
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PurchaseContractGoods purchaseContractGoods, Model model,
			@RequestParam("goodsIndexJson") String goodsIndexJson) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		purchaseContractGoods.setCreaterNo(currentUser.getLoginName());
		purchaseContractGoods.setCreaterName(currentUser.getName());
		purchaseContractGoods.setCreateDate(new Date());
		purchaseContractGoods.setSummary("采购合同-采购信息[" + purchaseContractGoods.getGoodsName() + "]");
		purchaseContractGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		purchaseContractGoodsService.save(purchaseContractGoods);
		if (StringUtils.isNotBlank(goodsIndexJson)) {
			purchaseGoodsIndexService.save(purchaseContractGoods, goodsIndexJson);
		}
		return setReturnData("success", "保存成功", purchaseContractGoods.getId());
	}

	/**
	 * 修改采购合同-子表跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("purchaseContractGoods", purchaseContractGoodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "purchase/contractGoodsForm";
	}

	/**
	 * 修改采购合同-子表
	 * 
	 * @param purchaseContractGoods
	 * @param model
	 * @param goodsIndexJson
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PurchaseContractGoods purchaseContractGoods, Model model,
			@RequestParam("goodsIndexJson") String goodsIndexJson) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		purchaseContractGoods.setUpdaterNo(currentUser.getLoginName());
		purchaseContractGoods.setUpdaterName(currentUser.getName());
		purchaseContractGoods.setUpdateDate(new Date());
		purchaseContractGoods.setSummary("采购合同-采购信息[" + purchaseContractGoods.getGoodsName() + "]");
		purchaseContractGoodsService.update(purchaseContractGoods);
		if (StringUtils.isNotBlank(goodsIndexJson)) {
			purchaseGoodsIndexService.save(purchaseContractGoods, goodsIndexJson);
		}
		return setReturnData("success", "保存成功", purchaseContractGoods.getId());
	}

	/**
	 * 删除采购合同-子表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		purchaseContractGoodsService.delete(id);
		return "success";
	}

	/**
	 * 查看采购合同-子表明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("purchaseContractGoods", purchaseContractGoodsService.get(id));
		return "purchase/contractGoodsDetail";
	}

	@ModelAttribute
	public void getPurchaseContractGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("purchaseContractGoods", purchaseContractGoodsService.get(id));
		}
	}

}
