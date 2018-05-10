package com.cbmie.lh.stock.web;

import java.util.Date;
import java.util.List;

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

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.stock.entity.InStockNotice;
import com.cbmie.lh.stock.entity.InStockNoticeGoods;
import com.cbmie.lh.stock.service.InStockNoticeGoodsService;
import com.cbmie.lh.stock.service.InStockNoticeService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 入库明细controller
 */
@Controller
@RequestMapping("stock/inStockNoticeGoods")
public class InStockNoticeGoodsController extends BaseController {

	@Autowired
	private InStockNoticeGoodsService inStockGoodsNoticeService;
	
	@Autowired
	private InStockNoticeService inStockNoticeService;


	/**
	 * 根据id获取入库明细json
	 */
	@RequestMapping(value = "getInstockNoticeGoods/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<InStockNoticeGoods> goodsList(@PathVariable("id") String id) {
		return inStockGoodsNoticeService.goodsList(id);
	}

	/**
	 * 添加入库明细跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model, @PathVariable("id") String id) {
		InStockNoticeGoods inStockNoticeGoods = new InStockNoticeGoods();
		inStockNoticeGoods.setInStockNoticeId(id);
		InStockNotice inStockNotice = inStockNoticeService.get(Long.valueOf(id));
		inStockNoticeGoods.setInnerContractNo(inStockNotice.getInnerContractNo());
		model.addAttribute("inStockNoticeGoods", inStockNoticeGoods);
		model.addAttribute("actionGoods", "create");
		return "stock/inStockNoticeGoodsForm";
	}

	/**
	 * 添加入库明细
	 * 
	 * @param inStockNoticeGoods
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InStockNoticeGoods inStockNoticeGoods, Model mode) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		inStockNoticeGoods.setCreaterNo(currentUser.getLoginName());
		inStockNoticeGoods.setCreaterName(currentUser.getName());
		inStockNoticeGoods.setCreateDate(new Date());
		inStockNoticeGoods.setUpdateDate(new Date());
		inStockNoticeGoods.setSummary("入库-入库明细[" + inStockNoticeGoods.getGoodsName() + "]");
		inStockNoticeGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		inStockGoodsNoticeService.save(inStockNoticeGoods);
		return setReturnData("success", "保存成功", inStockNoticeGoods.getId());
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
		model.addAttribute("inStockNoticeGoods", inStockGoodsNoticeService.get(id));
		model.addAttribute("actionGoods", "update");
		return "stock/inStockNoticeGoodsForm";
	}

	/**
	 * 修改入库明细
	 * 
	 * @param inStockNoticeGoods
	 * @param model
	 * @param goodsIndexJson
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InStockNoticeGoods inStockNoticeGoods, Model model)
			throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		inStockNoticeGoods.setUpdaterNo(currentUser.getLoginName());
		inStockNoticeGoods.setUpdaterName(currentUser.getName());
		inStockNoticeGoods.setUpdateDate(new Date());
		inStockNoticeGoods.setSummary("入库-入库明细[" + inStockNoticeGoods.getGoodsName() + "]");
		inStockGoodsNoticeService.update(inStockNoticeGoods);
		return setReturnData("success", "保存成功", inStockNoticeGoods.getId());
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
		inStockGoodsNoticeService.delete(id);
		return "success";
	}

	@ModelAttribute
	public void getInStockNoticeGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inStockNoticeGoods", inStockGoodsNoticeService.get(id));
		}
	}
	
	/**
	 * 保存子表
	 * @param innerContractNo
	 * @param inStockNoticeId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "saveGoods/{innerContractNo}/{inStockNoticeId}", method = RequestMethod.POST)
	@ResponseBody
	public String saveGoods( @PathVariable("innerContractNo")String innerContractNo,@PathVariable("inStockNoticeId")String inStockNoticeId) throws Exception{
		inStockGoodsNoticeService.saveGoods(innerContractNo,inStockNoticeId);
		return setReturnData("success", "保存成功！", Long.valueOf(inStockNoticeId));
	} 


}
