package com.cbmie.lh.sale.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.cbmie.lh.baseInfo.service.GoodsIndexService;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.lh.sale.entity.SaleContractGoodsIndex;
import com.cbmie.lh.sale.service.SaleContractGoodsIndexService;
import com.cbmie.lh.sale.service.SaleContractGoodsService;
import com.cbmie.lh.sale.service.SaleContractService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 销售合同-进口controller
 */
@Controller
@RequestMapping("sale/saleContractGoods")
public class SaleContractGoodsController extends BaseController {

	@Autowired
	private SaleContractService contractService;
	@Autowired
	private SaleContractGoodsService saleContractGoodsService;

	@Autowired
	private SaleContractGoodsIndexService saleContractGoodsIndexService;
	
	@Autowired
	private GoodsIndexService indexService;
	

	/**
	 * 获取销售合同-子表json
	 */
	@RequestMapping(value = "forContractId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request,@PathVariable("id") String id) {
		Page<SaleContractGoods> page = getPage(request);
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		PropertyFilter filter = new PropertyFilter("EQL_saleContractId", id);
		filterList.add(filter);
		page = saleContractGoodsService.search(page, filterList);
		List<SaleContractGoods> ss= page.getResult();
		List<SaleContractGoods> returnList = new ArrayList<SaleContractGoods>();
		for(SaleContractGoods saleContractGoods:ss){
			returnList.add(converGoods(saleContractGoods));
		}
		page.setResult(returnList);
		return getEasyUIData(page);
	}
	
	/**
	 * 获取jsonNoPermission
	 */
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityListNoPermission(HttpServletRequest request) {
		Page<SaleContractGoods> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = saleContractGoodsService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加销售合同-子表跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model,@PathVariable("id") Long id) {
		SaleContractGoods saleContractGoods = new SaleContractGoods();
		saleContractGoods.setSaleContractId(id);
		model.addAttribute("saleContractGoods", saleContractGoods);
		model.addAttribute("actionGoods", "create");
		return "sale/saleGoodsForm";
	}

	/**
	 * 添加销售合同-子表
	 * @param saleContractGoods
	 * @param model
	 * @param goodsIndexJson
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleContractGoods saleContractGoods, Model model,
			@RequestParam("goodsIndexJson") String goodsIndexJson) throws JsonProcessingException  {
		User currentUser = UserUtil.getCurrentUser();
		saleContractGoods.setCreaterNo(currentUser.getLoginName());
		saleContractGoods.setCreaterName(currentUser.getName());
		saleContractGoods.setCreateDate(new Date());
		saleContractGoods.setSummary("销售合同-商品信息[" + saleContractGoods.getGoodsName() + "]");
		saleContractGoods.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleContractGoodsService.save(saleContractGoods);
		if(StringUtils.isNotBlank(goodsIndexJson)){
			saleContractGoodsIndexService.save(saleContractGoods, goodsIndexJson);
		}
		saleContractGoodsService.update(saleContractGoods);
		SaleContract contract = contractService.get(saleContractGoods.getSaleContractId());
		contractService.countSaleContract(contract);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", "success");
		map.put("returnId", saleContractGoods.getId());
		map.put("returnMsg", "保存成功");
		map.put("contractQuantity", contract.getContractQuantity());
		map.put("contractAmount", contract.getContractAmount());
		return setReturnData(map);
	}

	/**
	 * 修改销售合同-子表跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleContractGoods", saleContractGoodsService.get(id));
		model.addAttribute("actionGoods", "update");
		return "sale/saleGoodsForm";
	}

	/**
	 * 修改销售合同-子表
	 * @param saleContractGoods
	 * @param model
	 * @param goodsIndexJson
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleContractGoods saleContractGoods, Model model,
			@RequestParam("goodsIndexJson") String goodsIndexJson) throws JsonProcessingException {
		System.out.println("~~~~~~~~~~~~~~ID:" + saleContractGoods.getId() + ", Amount:" + saleContractGoods.getAmount());
		User currentUser = UserUtil.getCurrentUser();
		saleContractGoods.setUpdaterNo(currentUser.getLoginName());
		saleContractGoods.setUpdaterName(currentUser.getName());
		saleContractGoods.setUpdateDate(new Date());
		saleContractGoods.setSummary("销售合同-商品信息[" + saleContractGoods.getGoodsName() + "]");
		if(StringUtils.isNotBlank(goodsIndexJson)){
			saleContractGoodsIndexService.save(saleContractGoods, goodsIndexJson);
		}
		saleContractGoodsService.update(saleContractGoods);
		SaleContract contract = contractService.get(saleContractGoods.getSaleContractId());
		contractService.countSaleContract(contract);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", "success");
		map.put("returnId", saleContractGoods.getId());
		map.put("returnMsg", "保存成功");
		map.put("contractQuantity", contract.getContractQuantity());
		map.put("contractAmount", contract.getContractAmount());
		return setReturnData(map);
	}

	/**
	 * 删除销售合同-子表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		Long saleContractId = saleContractGoodsService.get(id).getSaleContractId();
		saleContractGoodsService.delete(id);
		SaleContract contract = contractService.get(saleContractId);
		contractService.countSaleContract(contract);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnFlag", "success");
		map.put("returnId", contract.getId());
		map.put("returnMsg", "删除成功");
		map.put("contractQuantity", contract.getContractQuantity());
		map.put("contractAmount", contract.getContractAmount());
		return setReturnData(map);
	}

	public String  setReturnData(Map<String, Object> map) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
	}
	/**
	 * 查看销售合同-子表明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleContractGoods", saleContractGoodsService.get(id));
		return "sale/saleGoodsDetail";
	}
	
	public SaleContractGoods converGoods(SaleContractGoods saleContractGoods){
		List<SaleContractGoodsIndex> goodsIndexList = saleContractGoods.getGoodsIndexList();
		StringBuffer ss = new StringBuffer();
		
		ss.append("<table width='98%' class='tableClass'><tr><th>指标</th><th>约定值</th><th>拒收值</th><th>扣罚条款</th></tr>");
		for(SaleContractGoodsIndex goodsIndex:goodsIndexList){
			ss.append( "<tr><td>"+indexService.get(Long.valueOf(goodsIndex.getIndexName())).getIndexName()+"</td><td>"+goodsIndex.getConventions()+"</td><td>"+goodsIndex.getRejectionValue()+"</td><td>"+goodsIndex.getTerms()+"</td></tr>");
		}
		ss.append("</table>");
		saleContractGoods.setIndicatorInformation(String.valueOf(ss));
		return saleContractGoods;
	}

	@ModelAttribute
	public void getPurchaseContractGoods(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleContractGoods", saleContractGoodsService.get(id));
		}
	}
	@RequestMapping(value = "getGoodsCasContract", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSaleContractFGoodsFetchContract(HttpServletRequest request){
		Map<String,Object> queryParams = new HashMap<String,Object>();
		Page<SaleContractGoods> page = getPage(request);
		String saleContractNo = request.getParameter("contractNo");
		if(StringUtils.isNotBlank(saleContractNo)){
			queryParams.put("saleContractNo", saleContractNo);
		}
		saleContractGoodsService.getSaleContractFGoodsFetchContract(page, queryParams);
		return getEasyUIData(page);
	}

}
