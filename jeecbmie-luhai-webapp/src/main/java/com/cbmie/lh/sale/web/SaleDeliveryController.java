package com.cbmie.lh.sale.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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

import com.cbmie.accessory.entity.Accessory;
import com.cbmie.accessory.service.AccessoryService;
import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;
import com.cbmie.lh.sale.service.SaleContractGoodsService;
import com.cbmie.lh.sale.service.SaleDeliveryGoodsService;
import com.cbmie.lh.sale.service.SaleDeliveryService;
import com.cbmie.system.entity.DictChild;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 销售放货申请controller
 */
@Controller
@RequestMapping("sale/saleDelivery")
public class SaleDeliveryController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SaleDeliveryService saleDeliveryService;
	
	@Autowired
	private SaleDeliveryGoodsService saleDeliveryGoodsService;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private SaleContractGoodsService saleGoodsService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@Autowired
	private AccessoryService accessoryService;
	
	@Autowired
	private BaseInfoUtilService bius;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "sale/saleDeliveryList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<SaleDelivery> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		saleDeliveryService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = saleDeliveryService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		SaleDelivery saleDelivery = new SaleDelivery();
		saleDelivery.setBillDate(new Date());
		User currentUser = UserUtil.getCurrentUser();
		saleDelivery.setCreaterNo(currentUser.getLoginName());
		saleDelivery.setCreaterName(currentUser.getName());
		saleDelivery.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleDelivery.setCreateDate(new Date());
		saleDelivery.setUpdateDate(new Date());
		String no = sequenceCodeService.getNextCode("001003").get("returnStr");
		saleDelivery.setDeliveryReleaseNo(no);
		model.addAttribute("saleDelivery", saleDelivery);
		model.addAttribute("action", "create");
		return "sale/saleDeliveryForm";
	}

	/**
	 * 添加
	 * 
	 * @param saleDelivery
	 * @param model
	 * @throws JsonProcessingException 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid SaleDelivery saleDelivery, Model model,Long[] saleGoodsIds) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
			String  currnetSequence = null;
			if (saleDeliveryService.findByNo(saleDelivery) != null) {
				String deliveryReleaseNo = sequenceCodeService.getNextCode("001003").get("returnStr");
				saleDelivery.setDeliveryReleaseNo(deliveryReleaseNo);
				currnetSequence = deliveryReleaseNo;
			}
			String summary = "[发货通知审批]["+ bius.getAffiBaseInfoByCode(saleDelivery.getSeller()) +"]";
			List<SaleDeliveryGoods> subs = saleDelivery.getSalesDeliveryGoodsList();
			if(subs!=null && subs.size()>0){
				BigDecimal total = new BigDecimal(0.0);
				for(SaleDeliveryGoods sub : subs){
					if(sub.getQuantity()!=null)
					total = total.add(new BigDecimal(sub.getQuantity()));
				}
				summary = summary+"[数量"+total.doubleValue()+DictUtils.getDictLabelsByCodes(subs.get(0).getUnits(), "sldw", "")+"]";
			}
			saleDelivery.setSummary(summary);
			if(saleDelivery.getId()==null){
				sequenceCodeService.getNextSequence("001003");
			}
			saleDeliveryService.save(saleDelivery);
			if(saleGoodsIds!=null && saleGoodsIds.length>0){
				deleteSub(saleDelivery);
				fillSubBySaleGoodsIds(saleDelivery,saleGoodsIds);
			}
			return setReturnData("success", currnetSequence!=null?"出库编号，已自动生成不重复编码并保存":"保存成功", saleDelivery.getId(),currnetSequence);
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
		model.addAttribute("saleDelivery", saleDeliveryService.get(id));
		model.addAttribute("action", "update");
		return "sale/saleDeliveryForm";
	}

	/**
	 * 修改
	 * 
	 * @param saleDelivery
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody SaleDelivery saleDelivery, Model model, Long[] saleGoodsIds) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		saleDelivery.setUpdaterNo(currentUser.getLoginName());
		saleDelivery.setUpdaterName(currentUser.getName());
		saleDelivery.setUpdateDate(new Date());
		String summary = "[发货通知审批]["+ bius.getAffiBaseInfoByCode(saleDelivery.getSeller()) +"]";
		List<SaleDeliveryGoods> subs = saleDelivery.getSalesDeliveryGoodsList();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(SaleDeliveryGoods sub : subs){
				if(sub.getQuantity()!=null)
				total = total.add(new BigDecimal(sub.getQuantity()));
			}
			summary = summary+"[数量"+total.doubleValue()+DictUtils.getDictLabelsByCodes(subs.get(0).getUnits(), "sldw", "")+"]";
		}
		saleDelivery.setSummary(summary);
		saleDeliveryService.update(saleDelivery);
		if(saleGoodsIds!=null && saleGoodsIds.length>0){
			deleteSub(saleDelivery);
			fillSubBySaleGoodsIds(saleDelivery,saleGoodsIds);
		}
		return setReturnData("success","保存成功",saleDelivery.getId());
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		SaleDelivery saleDelivery = saleDeliveryService.get(id);
		if(saleDelivery.getPid()!=null){
			SaleDelivery parent = saleDeliveryService.get(saleDelivery.getPid());
			parent.setChangeState("1");
			saleDeliveryService.update(parent);
		}
		saleDeliveryService.delete(id);
		return setReturnData("success","删除成功",id);
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
		model.addAttribute("saleDelivery", saleDeliveryService.get(id));
		model.addAttribute("action", "view");
		return "sale/saleDeliveryDetail";
	}
	
	@ModelAttribute
	public void getSaleDelivery(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("saleDelivery", saleDeliveryService.get(id));
		}
	}

	public void deleteSub(SaleDelivery saleDelivery){
		saleDeliveryGoodsService.deleteEntityBySaleDeliveryId(saleDelivery.getId());
		saleDelivery.getSalesDeliveryGoodsList().clear();
	}
	public void fillSubBySaleGoodsIds(SaleDelivery saleDelivery,Long[] saleGoodsIds){
		for(Long goodsId : saleGoodsIds){
			SaleContractGoods saleGoods = saleGoodsService.get(goodsId);
			SaleDeliveryGoods t = new SaleDeliveryGoods();
			t.setContractNo(saleDelivery.getSaleContractNo());
			t.setGoodsName(saleGoods.getGoodsName());
			t.setQuantity(saleGoods.getGoodsQuantity());
			t.setSaleDeliveryId(saleDelivery.getId());
			t.setUnits(saleGoods.getUnits());
			saleDeliveryGoodsService.save(t);
			saleDelivery.getSalesDeliveryGoodsList().add(t);
		}
	}
	/*public void fillSubBySaleContractNo(SaleDelivery saleDelivery){
		SaleContract saleContract = saleContractService.getSaleContractByNo(saleDelivery.getSaleContractNo());
		List<SaleContractGoods> list = saleContract.getSaleContractSubList();
		for(SaleContractGoods good : list){
			SaleDeliveryGoods t = new SaleDeliveryGoods();
			t.setContractNo(saleDelivery.getSaleContractNo());
			t.setGoodsName(good.getGoodsName());
			t.setQuantity(good.getGoodsQuantity());
			t.setSaleDeliveryId(saleDelivery.getId());
			saleDeliveryGoodsService.save(t);
			saleDelivery.getSalesDeliveryGoodsList().add(t);
		}
	}*/
	
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
		User user = UserUtil.getCurrentUser();
		SaleDelivery saleDelivery = saleDeliveryService.get(id);
		String summary = "[发货通知审批]["+ bius.getAffiBaseInfoByCode(saleDelivery.getSeller()) +"]";
		List<SaleDeliveryGoods> subs = saleDelivery.getSalesDeliveryGoodsList();
		if(subs!=null && subs.size()>0){
			BigDecimal total = new BigDecimal(0.0);
			for(SaleDeliveryGoods sub : subs){
				if(sub.getQuantity()!=null)
				total = total.add(new BigDecimal(sub.getQuantity()));
			}
			summary = summary+"[数量"+total.doubleValue()+DictUtils.getDictLabelsByCodes(subs.get(0).getUnits(), "sldw", "")+"]";
		}
		saleDelivery.setSummary(summary);
		saleDeliveryService.update(saleDelivery);
		//此处可以增加业务验证
		boolean flag = true; //业务是否发起流程（默认发起）
		List<SaleDeliveryGoods> goods_subs = saleDelivery.getSalesDeliveryGoodsList();
		if(goods_subs.size()<1){
			result.put("msg", "放货信息列表不能为空");
			flag = false;
		}else{
			for(SaleDeliveryGoods goods_sub : goods_subs){
				if(StringUtils.isBlank(goods_sub.getPort())){
					result.put("msg", "放货信息仓库不能为空");
					flag = false;
					break;
				}
				if(saleDeliveryService.getAllQuantity(goods_sub)>saleDeliveryService.getMaxQuantity(goods_sub)){
					result.put("msg", "货物超出最大数量");
					flag = false;
					break;
				}
			}
			
		}
		if(flag){
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("manageMode", saleDelivery.getManageMode());
			//获取流程标识
			String processKey = activitiService.getCurrentProcessKey(SaleDelivery.class,saleDelivery);
			if(processKey!=null){
				result = activitiService.startWorkflow(saleDelivery, processKey, variables,
						(user.getLoginName()).toString());
			}else{
				return "流程未部署、不存在或存在多个，请联系管理员！";
			}
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
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
				SaleDelivery saleDelivery = saleDeliveryService.get(id);
				saleDelivery.setProcessInstanceId(null);
				saleDelivery.setState(ActivitiConstant.ACTIVITI_DRAFT);
				saleDeliveryService.save(saleDelivery);
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
	 * 变更发货通知
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "change/{id}", method = RequestMethod.GET)
	public String changeForm(@PathVariable("id") Long id, Model model) throws IllegalAccessException, InvocationTargetException {
		SaleDelivery saleDelivery_source = saleDeliveryService.getNoLoad(id);
		SaleDelivery saleDelivery = copyProperties(saleDelivery_source);
		model.addAttribute("saleDelivery", saleDelivery);
		model.addAttribute("action", "update");
		return "sale/saleDeliveryForm";
	}
	
	public SaleDelivery copyProperties(SaleDelivery saleDelivery_source) throws IllegalAccessException, InvocationTargetException{
		SaleDelivery saleDelivery_dest = new SaleDelivery();  //主表
		List<SaleDeliveryGoods> goodList_dest = new ArrayList<SaleDeliveryGoods>(); //子表
		BeanUtils.copyProperties(saleDelivery_dest, saleDelivery_source);
		saleDelivery_dest.setId(null);
		saleDelivery_dest.setSourceId(saleDelivery_source.getSourceId()==null?saleDelivery_source.getId():saleDelivery_source.getSourceId());
		saleDelivery_dest.setPid(saleDelivery_source.getId());
		saleDelivery_dest.setSalesDeliveryGoodsList(null);
		saleDelivery_dest.setChangeState("2");
		saleDelivery_dest.setProcessInstanceId(null);
		saleDelivery_dest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		saleDelivery_dest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		saleDelivery_dest.setCreaterNo(currentUser.getLoginName());
		saleDelivery_dest.setCreaterName(currentUser.getName());
		saleDelivery_dest.setCreaterDept(currentUser.getOrganization().getOrgName());
		saleDelivery_dest.setCreateDate(new Date());
		saleDelivery_dest.setUpdateDate(new Date());
		saleDelivery_source.setChangeState("0");
		saleDeliveryService.update(saleDelivery_source);
		saleDeliveryService.save(saleDelivery_dest);
		List<SaleDeliveryGoods> goods_sources = saleDelivery_source.getSalesDeliveryGoodsList();
		for(SaleDeliveryGoods good_source : goods_sources){
			SaleDeliveryGoods goods_dest = new SaleDeliveryGoods();
			BeanUtils.copyProperties(goods_dest, good_source);
			goods_dest.setId(null);
			goods_dest.setSaleDeliveryId(saleDelivery_dest.getId());
			saleDeliveryGoodsService.save(goods_dest);
			goodList_dest.add(goods_dest);
		}
		saleDelivery_dest.setSalesDeliveryGoodsList(goodList_dest );
		//更新附件信息
		 List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(saleDelivery_source.getId().toString(),"com_cbmie_lh_sale_entity_SaleDelivery");
		 accessoryService.copyAttach(accessoryList,saleDelivery_dest.getId()+"",null);
		return saleDelivery_dest; 
	}
	
	
	/**
	 * 获取备份List
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getSaleDeliveryBaks/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<SaleDelivery> getSaleDeliveryBaks(@PathVariable("id") long id){
		return saleDeliveryService.getSaleDeliveryBaks(id);
	}
	
	/**
	 * 查看采购合同-进口明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showChange/{id}", method = RequestMethod.GET)
	public String showChange(@PathVariable("id") Long id, Model model) {
		SaleDelivery saleDelivery = saleDeliveryService.get(id);
		model.addAttribute("saleDeliveryChange", saleDelivery);
		//相关业务员回显
		model.addAttribute("action", "view");
		return "sale/saleDeliveryDetailChange";
	}
	
	/**
	 * 采购合同号跳转
	 * @return
	 */
	@RequestMapping(value = "loadSaleContract", method = RequestMethod.GET)
	public String loadBillId() {
		return "sale/loadSaleContract";
	}
	
	
	/**
	 * 仓储跳转
	 * @return
	 */
	@RequestMapping(value = "loadStockStatistic", method = RequestMethod.GET)
	public String loadStockStatistic() {
		return "sale/loadStockStatistic";
	}
	
	@RequestMapping(value = "toSaleDeliverySaleContractList", method = RequestMethod.GET)
	public String toSaleContractList(){
		return "sale/selectSaleContractGoods";
	}
	
	@RequestMapping(value = "toPrintPage/{saleDeliveryId}", method = RequestMethod.GET)
	public String toPrintPage(@PathVariable("saleDeliveryId") Long saleDeliveryId, Model model){
		model.addAttribute("saleDelivery", saleDeliveryService.get(saleDeliveryId));
		List<DictChild> saleModes = DictUtils.getDictList("SALESMODE");
		model.addAttribute("saleModes", saleModes);
		return "sale/saleDeliveryPrint";
	}
	/**
	 * 取消复核
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "closeOrOpen/{id}")
	@ResponseBody
	public String closeOrOpen(@PathVariable("id") Long id){
		SaleDelivery saleDelivery = saleDeliveryService.get(id);
		String flag = saleDelivery.getCloseOrOpen();
		flag = "0".equals(flag)?"1":"0";
		saleDelivery.setCloseOrOpen(flag);
		saleDeliveryService.update(saleDelivery);
		return "success";
	}
}
