package com.cbmie.lh.logistic.web;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.cbmie.lh.baseInfo.entity.GoodsIndex;
import com.cbmie.lh.baseInfo.service.GoodsIndexService;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.LhBillsGoods;
import com.cbmie.lh.logistic.entity.LhBillsGoodsIndex;
import com.cbmie.lh.logistic.service.LhBillsGoodsIndexService;
import com.cbmie.lh.logistic.service.LhBillsGoodsService;
import com.cbmie.lh.logistic.service.LhBillsService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 到单信息controller
 */
@Controller
@RequestMapping("logistic/billsGoods")
public class LhBillsGoodsController extends BaseController{
	@Autowired
	private LhBillsService billsService;
	@Autowired
	private LhBillsGoodsService goodsService;
	@Autowired
	private GoodsIndexService indexService;
	@Autowired
	private LhBillsGoodsIndexService billsGoodsIndexService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/billsGoodsList";
	}
	
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<LhBillsGoods> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = goodsService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 获取采购合同-子表json
	 */
	@RequestMapping(value = "getRelationGoodsByPid/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request,@PathVariable("pid") String id) {
		Page<LhBillsGoods> page = getPage(request);
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		PropertyFilter filter = new PropertyFilter("EQS_parentId", id);
		filterList.add(filter);
		page = goodsService.search(page, filterList);
		List<LhBillsGoods> ss= page.getResult();
		Double goodsQuantity_t = 0.0;
		Double amount_t = 0.0;
		for(LhBillsGoods billsGoods:ss){
			converGoods(billsGoods);
			if(StringUtils.isNotBlank(billsGoods.getGoodsQuantity())){
				goodsQuantity_t += Double.parseDouble(billsGoods.getGoodsQuantity());
			}
			if(StringUtils.isNotBlank(billsGoods.getAmount())){
				amount_t += Double.parseDouble(billsGoods.getAmount());
			}
		}
		Map<String, Object> result =  getEasyUIData(page);
			LhBillsGoods footer_t = new LhBillsGoods();
			footer_t.setAmount(amount_t.toString());
			footer_t.setGoodsQuantity(goodsQuantity_t.toString());
			footer_t.setPurchasePrice("合计");
			List<LhBillsGoods> footer = new ArrayList<LhBillsGoods>();
			footer.add(footer_t);
			result.put("footer", footer);
		return result;
	}
	public void converGoods(LhBillsGoods goods){
		List<LhBillsGoodsIndex> goodsIndexs =goods.getGoodsIndexList();
		StringBuffer ss = new StringBuffer();
		
		ss.append("<table width='98%' class='tableClass'><tr><th>指标</th><th>约定值</th><th>拒收值</th><th>扣罚条款</th></tr>");
		for(LhBillsGoodsIndex goodIndex:goodsIndexs){
			GoodsIndex temp = indexService.get(Long.valueOf(goodIndex.getIndexName()));
			ss.append( "<tr><td>"+temp.getIndexName()+"</td><td>"+goodIndex.getConventions()+"</td><td>"+goodIndex.getRejectionValue()+"</td><td>"+goodIndex.getTerms()+"</td></tr>");
		}
		ss.append("</table>");
		goods.setIndicatorInformation(ss.toString());
	}
	
	/**
	 * 添加到单跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create/{parentId}", method = RequestMethod.GET)
	public String createForm(@PathVariable("parentId") String parentId,Model model) throws ParseException {
		try {
			LhBillsGoods lhBillsGoods = new LhBillsGoods();
			lhBillsGoods.setParentId(parentId);
			model.addAttribute("lhBillsGoods", lhBillsGoods);
			model.addAttribute("actionGoods", "create");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logistic/billsGoodsForm";
	}
	
	/**
	 * 添加到单
	 * 
	 * @param port
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid LhBillsGoods lhBillsGoods, Model model,@RequestParam("goodsIndexJson") String goodsIndexJson) throws JsonProcessingException {
		try {
			lhBillsGoods.setCreateDate(new Date());
			goodsService.save(lhBillsGoods);
			billsGoodsIndexService.save(lhBillsGoods, goodsIndexJson);
			LhBills bills = fillBillTotal(Long.parseLong(lhBillsGoods.getParentId()));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("returnFlag", "success");
			map.put("returnId", lhBillsGoods.getId());
			map.put("returnMsg", "保存成功");
			map.put("numbers", bills.getNumbers());
			map.put("invoiceAmount", bills.getInvoiceAmount());
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(map);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail","保存失败",lhBillsGoods.getId());
		}
	}
	/**
	 * 修改到单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("lhBillsGoods", goodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "logistic/billsGoodsForm";
	}
	
	@ModelAttribute
	public void getLhBillsGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("lhBillsGoods", goodsService.get(id));
		}
	}

	/**
	 * 修改到单
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody LhBillsGoods lhBillsGoods,Model model,@RequestParam("goodsIndexJson") String goodsIndexJson) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		lhBillsGoods.setUpdaterNo(currentUser.getLoginName());
		lhBillsGoods.setUpdaterName(currentUser.getName());
		lhBillsGoods.setUpdateDate(new Date());
		goodsService.update(lhBillsGoods);
			billsGoodsIndexService.save(lhBillsGoods, goodsIndexJson);
			LhBills bills = fillBillTotal(Long.parseLong(lhBillsGoods.getParentId()));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("returnFlag", "success");
			map.put("returnId", lhBillsGoods.getId());
			map.put("returnMsg", "保存成功");
			map.put("numbers", bills.getNumbers());
			map.put("invoiceAmount", bills.getInvoiceAmount());
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(map);
	}
	/**
	 * 删除到单
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		LhBillsGoods goods = goodsService.get(id);
		goodsService.delete(id);
		LhBills bills = fillBillTotal(Long.parseLong(goods.getParentId()));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", "success");
		map.put("returnId", id);
		map.put("returnMsg", "保存成功");
		map.put("numbers", bills.getNumbers());
		map.put("invoiceAmount", bills.getInvoiceAmount());
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
//		billsGoodsIndexService.deleteEntityByParentId(id.toString());
	}
	public LhBills fillBillTotal(Long billId){
		LhBills bill = billsService.get(billId);
		List<LhBillsGoods> list = bill.getGoodsList();
		Double goodsQuantity_t = 0.0;
		Double amount_t = 0.0;
		for(LhBillsGoods billsGoods:list){
			if(StringUtils.isNotBlank(billsGoods.getGoodsQuantity())){
				goodsQuantity_t += Double.parseDouble(billsGoods.getGoodsQuantity());
			}
			if(StringUtils.isNotBlank(billsGoods.getAmount())){
				amount_t += Double.parseDouble(billsGoods.getAmount());
			}
		}
		bill.setNumbers(goodsQuantity_t);
		bill.setInvoiceAmount(amount_t);
		billsService.update(bill);
		return bill;
	}
}
