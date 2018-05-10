package com.cbmie.woodNZ.salesDelivery.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods;
import com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery;
import com.cbmie.woodNZ.salesDelivery.service.DeliveryStockRelationService;
import com.cbmie.woodNZ.salesDelivery.service.RealSalesDeliveryGoodsService;
import com.cbmie.woodNZ.salesDelivery.service.SalesDeliveryGoodsService;
import com.cbmie.woodNZ.salesDelivery.service.SalesDeliveryService;
import com.cbmie.woodNZ.stock.entity.StockStatistic;
import com.cbmie.woodNZ.stock.service.InStockGoodsService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 销售放货申请controller
 */
@Controller
@RequestMapping("salesDelivery/salesDelivery")
public class SalesDeliveryController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SalesDeliveryService salesDeliveryService;
	
	@Autowired
	private SalesDeliveryGoodsService salesDeliveryGoodsService;
	
	@Autowired
	private RealSalesDeliveryGoodsService realSalesDeliveryGoodsService;
	
	@Autowired
	private DeliveryStockRelationService deliveryStockRelationService;
	
	@Autowired
	private ActivitiService activitiService;
	
	
	@Autowired
	private InStockGoodsService inStockGoodsService;
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "salesDelivery/salesDeliveryList";
	}
	
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<SalesDelivery> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = salesDeliveryService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("salesDelivery", new SalesDelivery());
		model.addAttribute("action", "create");
		return "salesDelivery/salesDeliveryform";
	}

	/**
	 * 添加
	 * 
	 * @param salesDelivery
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SalesDelivery salesDelivery, Model model,
			@RequestParam("salesDeliveryGoodsJson") String salesDeliveryGoodsJson,
			@RequestParam("realSalesDeliveryGoodsJson") String realSalesDeliveryGoodsJson) throws JsonProcessingException {
			User currentUser = UserUtil.getCurrentUser();
			salesDelivery.setCreaterNo(currentUser.getLoginName());
			salesDelivery.setCreaterName(currentUser.getName());
			salesDelivery.setCreaterDept(currentUser.getOrganization().getOrgName());
			salesDelivery.setCreateDate(new Date());
			salesDelivery.setSummary("放货申请[" + salesDelivery.getBillContractNo() + "]");
			salesDeliveryService.save(salesDelivery);
			if(StringUtils.isNotBlank(salesDeliveryGoodsJson)){
				salesDeliveryGoodsService.save(salesDelivery, salesDeliveryGoodsJson, currentUser);
			}
			if(StringUtils.isNotBlank(realSalesDeliveryGoodsJson)){
				realSalesDeliveryGoodsService.save(salesDelivery, realSalesDeliveryGoodsJson, currentUser);
				deliveryStockRelationService.saveRelation(salesDelivery);
			}
			return setReturnData("success","保存成功",salesDelivery.getId());
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
		model.addAttribute("salesDelivery", salesDeliveryService.get(id));
		model.addAttribute("action", "update");
		return "salesDelivery/salesDeliveryform";
	}

	/**
	 * 修改
	 * 
	 * @param salesDelivery
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SalesDelivery salesDelivery, Model model,
			@RequestParam("salesDeliveryGoodsJson") String salesDeliveryGoodsJson,
			@RequestParam("realSalesDeliveryGoodsJson") String realSalesDeliveryGoodsJson) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		salesDelivery.setUpdaterNo(currentUser.getLoginName());
		salesDelivery.setUpdaterName(currentUser.getName());
		salesDelivery.setCreaterDept(currentUser.getOrganization().getOrgName());
		salesDelivery.setUpdateDate(new Date());
		salesDelivery.setSummary("[放货申请" + salesDelivery.getBillContractNo() + "]");
		salesDeliveryService.update(salesDelivery);
		if(StringUtils.isNotBlank(salesDeliveryGoodsJson)){
			salesDeliveryGoodsService.save(salesDelivery, salesDeliveryGoodsJson, currentUser);
		}
		if(StringUtils.isNotBlank(realSalesDeliveryGoodsJson)){
			realSalesDeliveryGoodsService.save(salesDelivery, realSalesDeliveryGoodsJson, currentUser);
			//删除关联
			delRelation(salesDelivery.getId());
			//建立关联
			deliveryStockRelationService.saveRelation(salesDelivery);
		}
		return setReturnData("success","保存成功",salesDelivery.getId());
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
		//删除关联
		delRelation(id);
		salesDeliveryService.delete(id);
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
		model.addAttribute("salesDelivery", salesDeliveryService.get(id));
		return "salesDelivery/salesDeliveryDetail";
	}
	
	@ModelAttribute
	public void getSalesDelivery(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("salesDelivery", salesDeliveryService.get(id));
		}
	}
	
	/**
	 * 提交流程申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "apply/{id}")
	@ResponseBody
	public String apply(@PathVariable("id") Long id) {
		Map<String,Object> result = new HashMap<String,Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			User user = UserUtil.getCurrentUser();
			SalesDelivery salesDelivery = salesDeliveryService.get(id);
//			salesDelivery.setUserId((user.getLoginName()).toString());
			salesDelivery.setState("已提交");
			salesDeliveryService.save(salesDelivery);
			Map<String, Object> variables = new HashMap<String, Object>();
			result = activitiService.startWorkflow(salesDelivery, "wf_salesDelivery", variables, (user.getLoginName()).toString());
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 撤回流程申请
	 * @return
	 */
	@RequestMapping(value = "callBack/{id}/{processInstanceId}")
	@ResponseBody
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId, HttpSession session) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId)) {
				SalesDelivery salesDelivery = salesDeliveryService.get(id);
				salesDelivery.setProcessInstanceId(null);
				salesDelivery.setState("草稿");
				salesDeliveryService.save(salesDelivery);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	
	/**
	 * 采购合同号跳转
	 * @return
	 */
	@RequestMapping(value = "loadSaleContract", method = RequestMethod.GET)
	public String loadBillId() {
		return "salesDelivery/loadSaleContract";
	}
	
	
	/**
	 * 采购合同号跳转
	 * @return
	 */
	@RequestMapping(value = "loadStockStatistic", method = RequestMethod.GET)
	public String loadStockStatistic() {
		return "salesDelivery/loadStockStatistic";
	}
	
	/**
	 * 获取json
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value = "matchStock/{jsonParam:.+}", method = RequestMethod.GET)
	@ResponseBody
	public List<RealSalesDeliveryGoods> matchStock( @PathVariable("jsonParam") String jsonParam)  {
//		System.out.println(jsonParam);
		jsonParam = StringEscapeUtils.unescapeHtml4(jsonParam);
//		System.out.println(jsonParam);
		// 把json转成对象
		ObjectMapper objectMapper = new ObjectMapper();
		List<StockStatistic> stockStatisticList = new ArrayList<StockStatistic>();
		try {
			JsonNode jsonNode = objectMapper.readTree(jsonParam);
			for (JsonNode jn : jsonNode) {
				StockStatistic stockStatistic = objectMapper.readValue(jn.toString(), StockStatistic.class);
				stockStatisticList.add(stockStatistic);
			}
		} catch (Exception e) {
		}
		
		List<RealSalesDeliveryGoods> returnList = new ArrayList<RealSalesDeliveryGoods>();
		for (StockStatistic stockStatistic :stockStatisticList) {
			RealSalesDeliveryGoods realSalesDeliveryGoods = new RealSalesDeliveryGoods();
			realSalesDeliveryGoods.setGoodsNo(stockStatistic.getGoodsNo());
			realSalesDeliveryGoods.setGoodsName(stockStatistic.getGoodsName());
			realSalesDeliveryGoods.setAmount(stockStatistic.getAmount());
			realSalesDeliveryGoods.setWarehouse(String.valueOf(stockStatistic.getWarehouse()));
			realSalesDeliveryGoods.setUnit(stockStatistic.getUnit());
			realSalesDeliveryGoods.setCpContractNo(inStockGoodsService.get(stockStatistic.getId()).getCpContractNo());
			returnList.add(realSalesDeliveryGoods);
		}
		return returnList;
	}
	
	/**
	 * 删除关联
	 * @param id 放货id
	 */
	public void delRelation(Long id){
		List<RealSalesDeliveryGoods>  realSalesDeliveryGoodsList = new ArrayList<RealSalesDeliveryGoods>();
		realSalesDeliveryGoodsList = salesDeliveryService.get(id).getRealSalesDeliveryGoodsList();
		for(RealSalesDeliveryGoods realSalesDeliveryGoods:realSalesDeliveryGoodsList){
			deliveryStockRelationService.deleteByRealGoods(realSalesDeliveryGoods);
		}
	}
	
	
}
