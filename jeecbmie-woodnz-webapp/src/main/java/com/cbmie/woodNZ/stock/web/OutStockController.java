package com.cbmie.woodNZ.stock.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.baseInfo.service.CkService;
import com.cbmie.woodNZ.logistics.entity.DownstreamBill;
import com.cbmie.woodNZ.logistics.entity.DownstreamContainer;
import com.cbmie.woodNZ.logistics.service.DownstreamBillService;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.cbmie.woodNZ.salesContract.service.SaleContractService;
import com.cbmie.woodNZ.salesDelivery.entity.DeliveryStockRelation;
import com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods;
import com.cbmie.woodNZ.salesDelivery.entity.SalesDelivery;
import com.cbmie.woodNZ.salesDelivery.service.SalesDeliveryGoodsService;
import com.cbmie.woodNZ.salesDelivery.service.SalesDeliveryService;
import com.cbmie.woodNZ.stock.entity.InStock;
import com.cbmie.woodNZ.stock.entity.InStockGoods;
import com.cbmie.woodNZ.stock.entity.InStockOutStockRelation;
import com.cbmie.woodNZ.stock.entity.OutStock;
import com.cbmie.woodNZ.stock.entity.OutStockCache;
import com.cbmie.woodNZ.stock.entity.OutStockSub;
import com.cbmie.woodNZ.stock.service.InStockOutStockRelationService;
import com.cbmie.woodNZ.stock.service.InStockService;
import com.cbmie.woodNZ.stock.service.OutStockCacheService;
import com.cbmie.woodNZ.stock.service.OutStockService;
import com.cbmie.woodNZ.stock.service.OutStockSubService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 出库controller
 */
@Controller
@Scope("session")
@RequestMapping("stock/outStock")
public class OutStockController extends BaseController{
	
	
	@Autowired
	private OutStockService outStockService;
	
	@Autowired
	private OutStockSubService outStockSubService;
	
	
	@Autowired
	private SalesDeliveryService salesDeliveryService;
	
	@Autowired
	private SalesDeliveryGoodsService salesDeliveryGoodsService;
	
	@Autowired
	private InStockOutStockRelationService inStockOutStockRelationService;
	
	@Autowired
	private OutStockCacheService outStockCacheService;
	
	@Autowired
	private SaleContractService saleContractService;

	@Autowired
	private DownstreamBillService downstreamBillService;
	
	@Autowired
	private InStockService inStockService;
	
	@Autowired
	private CkService ckService;
	
	
	private Long mainId=null;//出库主表id
	
	private List<OutStockSub> goodsCacheList = new ArrayList<OutStockSub>();//临时放货数据，存储保存出库主表前的放货数据

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		mainId=null;
		return "stock/outStockList";
	}
	 
	/**
	 * 获取json
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> outList(HttpServletRequest request) {
		Page<OutStock> page = getPage(request);
		try {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = outStockService.search(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getEasyUIData(page);
	}
	
	/**
	 * 添加出库跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		mainId=null;
		goodsCacheList.clear();
		OutStock outStock = new OutStock();
		User currentUser = UserUtil.getCurrentUser();
		outStock.setCreaterNo(currentUser.getLoginName());
		outStock.setCreaterName(currentUser.getName());
		outStock.setCreaterDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("outStock", outStock);
		model.addAttribute("action", "create");
		return "stock/outStockForm";
	}

	/**
	 * 添加出库
	 * 
	 * @param port
	 * @param model
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid OutStock outStock, @RequestParam("outStackSubJson") String subJson,Model model) throws ParseException, JsonProcessingException {
		outStock.setCreateDate(new Date());
		outStockService.save(outStock);
		if(StringUtils.isNotBlank(subJson)){
			outStockSubService.save(outStock, subJson);
		}
		mainId = outStock.getId();//当前的出库主表id
		return setReturnData("success","保存成功",outStock.getId());
	}

	/**
	 * 修改出库跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		mainId=id;
		goodsCacheList.clear();
		model.addAttribute("outStock", outStockService.get(id));
		model.addAttribute("action", "update");
		return "stock/outStockForm";
	}

	/**
	 * 修改出库
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OutStock outStock,
			@RequestParam("outStackSubJson") String subJson,Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		outStock.setUpdaterName(currentUser.getLoginName());
		outStock.setUpdateDate(new Date());
		outStock.setUpdaterNo(currentUser.getLoginName());
		outStock.setUpdaterName(currentUser.getName());
		outStockService.update(outStock);
		if(StringUtils.isNotBlank(subJson)){
			outStockSubService.save(outStock, subJson);
		}
		return setReturnData("success","保存成功",outStock.getId());
	}

	/**
	 * 删除出库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		deleteRelation(id);//删除出库中间表数据
		outStockService.delete(id);
		return "success";
	}
	
	/**
	 * 出库明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("outStock", outStockService.get(id));
		model.addAttribute("action", "detail");
		return "stock/outStockDetail";
	}
	
	/**
	 * 获取所有出库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getPortList")
	@ResponseBody
	public List<OutStock> getDictByCode() {
		List<OutStock> portList = outStockService.getAll();
		return portList;
	}
	
	
	
	@ModelAttribute
	public void getOutStock(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("outStock", outStockService.get(id));
		}
	}
	
	
	/**
	 * 放货列表页面跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toSendGoods", method = RequestMethod.GET)
	public String toSendGoods() {
		return "stock/outStockSendGoods";
	}

	
	/**
	 *  转换为合同号
	 */
	public String getNoById(String str){ 
		if(str == null || str.equals("")){
			return "";
		}
		String result="";
		String[] strArray = null;   
		strArray =  str.split(","); //拆分字符为"," ,然后把结果交给数组strArray 
		if(strArray ==null || strArray.length ==0){
			return "";
		}
		for(String saleId:strArray){
			WoodSaleContract entity = saleContractService.get(Long.valueOf(saleId));
			result += entity.getContractNo();
			result += ",";
		}
		return result.substring(0,result.length()-1);//删除最后一个逗号
	}
	
	
	/**
	 *  判断字符串是否为数字
	 */
	public static boolean isNumeric(String str){ 
		for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){ 
			   return false; 
		   } 
		} 
		return true; 
	} 

	/**
	 * 选择采放货或者下游交单数据后执行此方法
	 * 
	 * @param id 放货主表id或者下游交单主表id
	 * @param state 数据源状态：1代表放货数据源，2代表下游交单数据源
	 * @return
	 */
	@RequestMapping(value = "setGoodsInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setGoodsInfo(Model model,@RequestParam("id") String id,
			@RequestParam("state") String state) {
		//先清除出库明细数据
		if(mainId != null){
			deleteRelation(mainId);//删除出库单下的中间表数据
			OutStock outStock = outStockService.get(mainId);
			List<OutStockSub> OutStockSubList = outStock.getSubList();
			List<OutStockSub> dataList = new ArrayList<OutStockSub>();
			for(OutStockSub s:OutStockSubList){
				dataList.add(s);
			}
			for(OutStockSub sub:dataList){
				OutStockSubList.remove(sub);
				sub.setParentId(null);
				outStockSubService.delete(sub);
			}
		}else if(mainId == null){
			deleteCacheRelation(goodsCacheList);
			goodsCacheList.clear();
		}
		
		//读取数据************
		if(state.equals("1")){//从销售放货实际商品信息表取数据
			SalesDelivery sendGoods = new SalesDelivery();
			sendGoods = salesDeliveryService.get(Long.valueOf(id));
			List<RealSalesDeliveryGoods> goodsList = sendGoods.getRealSalesDeliveryGoodsList();
			if(mainId != null)
				setGoodsList(goodsList);
			else if(mainId == null)
				setGoodsCacheList(goodsList);//存储在临时list中
			Map<String, Object> msg = new HashMap<String, Object>();
			msg.put("result", "success");
			msg.put("dataSource", "delivery");
			msg.put("currency", goodsList.get(0).getCurrency());//币种从放货子表中带出，取第一个元素的币种值
			msg.put("deliveryId", sendGoods.getId());//放货主表id
			return msg;
		}else if(state.equals("2")){//从下游交单的箱单信息中取数据
			DownstreamBill downBills = new DownstreamBill();
			downBills = downstreamBillService.get(Long.valueOf(id));
			List<DownstreamContainer> boxList = downBills.getDownstreamContainerList();
			String no = getNoById(downBills.getRelationSaleContarte());
			if(mainId != null){
				setBoxsList(boxList,no); 
			}
			else if(mainId == null){
				setBoxCacheList(boxList,no);//存储在临时list中
			}
			Map<String, Object> msg = new HashMap<String, Object>();
			msg.put("result", "success");
			msg.put("dataSource", "downBills");
			msg.put("currency", boxList.get(0).getCurrency());//币种从下游交单的箱单信息中带出，取第一个元素的币种值
			msg.put("downBillsId", downBills.getId());//下游交单主表id
			return msg;
		}
		return null;
	}
	
	private void deleteRelation(Long mainId) {
		List<OutStockSub> subList = outStockSubService.getOutStockByMainId(mainId);
		for(OutStockSub outStockSub:subList){
			inStockOutStockRelationService.deleteByOutStockSubId(outStockSub.getId());//删除出库单下的中间表数据
		}
	}
	
	private void deleteCacheRelation(List<OutStockSub> subList) {
		for(OutStockSub outStockSub:subList){
			inStockOutStockRelationService.deleteByOutStockSubId(outStockSub.getId());//删除出库单下的中间表数据
		}
	}
	
	private void updateRelation(OutStockSub outStockSub) {
		//*************向出库关联表插入数据   start***************//
		List<DeliveryStockRelation> deliveryStockRelationList = 
				outStockService.getDeliverStockBydeliveryId(Long.valueOf(outStockSub.getRealSalesDeliveryGoodsId()));//通过放货子表id返回入库中间表集合；
		for(DeliveryStockRelation deliveryStockRelation:deliveryStockRelationList){
			InStockOutStockRelation relation = new InStockOutStockRelation();
			relation.setInStockGoods(deliveryStockRelation.getInStockGoods());//入库商品表id
			relation.setOutStockSub(outStockSub);//出库商品表id
			inStockOutStockRelationService.save(relation);
		}
		//*************向出库关联表插入数据   end***************//
	}
	
	
	public void setGoodsList(List<RealSalesDeliveryGoods>  goodsList){
		for(RealSalesDeliveryGoods mx:goodsList){
			OutStockSub outStockSub = new OutStockSub();
			if(mainId != null){
				outStockSub.setParentId(mainId);
			}
			SalesDelivery salesDelivery = salesDeliveryService.get(mx.getParentId());//获取销售合同主表
			outStockSub.setSaleContractNo(salesDelivery.getBillContractNo());//销售合同号
			outStockSub.setCpContractNo(mx.getCpContractNo());//综合合同号
			outStockSub.setGoodsNo(mx.getGoodsNo());//商品编码
			outStockSub.setGoodsName(mx.getGoodsName());//商品名称
			outStockSub.setRealsendPNum(mx.getNum()==null?0:mx.getNum());//实发件数
			outStockSub.setRealsendVNum(mx.getAmount()==null?'0':mx.getAmount());//实发立方数
			outStockSub.setPieceAvailable(mx.getPiece()==null?0:mx.getPiece());//片数
			outStockSub.setNumAvailable(mx.getNum()==null?0:mx.getNum());//件数
			outStockSub.setAmount(mx.getAmount()==null?'0':mx.getAmount());//立方数
			outStockSub.setUnitPrice(Double.valueOf(mx.getUnitPrice()==null?0:mx.getUnitPrice()));//单价
			outStockSub.setMoney(Double.valueOf(mx.getMoney()==null?0:mx.getMoney()));//金额
			outStockSub.setWaterRate(mx.getWaterRate()==null?0:mx.getWaterRate());//含水率
			outStockSub.setWarehouse(mx.getWarehouse());//仓库
			outStockSub.setRealSalesDeliveryGoodsId(mx.getId().toString());//实际放货商品id
			outStockSub.setDataSource("放货");
			User currentUser = UserUtil.getCurrentUser();
			outStockSub.setCreaterNo(currentUser.getLoginName());
			outStockSub.setCreaterName(currentUser.getName());
			outStockSub.setCreaterDept(currentUser.getOrganization().getOrgName());
			outStockSub.setCreateDate(new Date());
			outStockSubService.save(outStockSub);
			updateRelation(outStockSub);
		}
	}
	
	public void setGoodsCacheList(List<RealSalesDeliveryGoods>  goodsList){
		for(RealSalesDeliveryGoods mx:goodsList){
			OutStockSub outStockSub = new OutStockSub();
			SalesDelivery salesDelivery = salesDeliveryService.get(mx.getParentId());//获取销售合同主表
			outStockSub.setSaleContractNo(salesDelivery.getBillContractNo());//销售合同号
			outStockSub.setCpContractNo(mx.getCpContractNo());//综合合同号
			outStockSub.setGoodsNo(mx.getGoodsNo());//商品编码
			outStockSub.setGoodsName(mx.getGoodsName());//商品名称
			outStockSub.setRealsendPNum(mx.getNum()==null?0:mx.getNum());//实发件数
			outStockSub.setRealsendVNum(mx.getAmount()==null?'0':mx.getAmount());//实发立方数
			outStockSub.setPieceAvailable(mx.getPiece()==null?0:mx.getPiece());//片数
			outStockSub.setNumAvailable(mx.getNum()==null?0:mx.getNum());//件数
			outStockSub.setAmount(mx.getAmount()==null?'0':mx.getAmount());//立方数
			outStockSub.setUnitPrice(Double.valueOf(mx.getUnitPrice()==null?0:mx.getUnitPrice()));//单价
			outStockSub.setMoney(Double.valueOf(mx.getMoney()==null?0:mx.getMoney()));//金额
			outStockSub.setWaterRate(mx.getWaterRate()==null?0:mx.getWaterRate());//含水率
			outStockSub.setWarehouse(mx.getWarehouse());//仓库
			outStockSub.setRealSalesDeliveryGoodsId(mx.getId().toString());//实际放货商品id
			outStockSub.setDataSource("放货");
			User currentUser = UserUtil.getCurrentUser();
			outStockSub.setCreaterNo(currentUser.getLoginName());
			outStockSub.setCreaterName(currentUser.getName());
			outStockSub.setCreaterDept(currentUser.getOrganization().getOrgName());
			outStockSub.setCreateDate(new Date());
			goodsCacheList.add(outStockSub);
		}
	}
	
	/**
	 * 获取出库明细列表
	 */
	@RequestMapping(value="getOutStockSubList",method = RequestMethod.GET)
	@ResponseBody
	public List<OutStockSub> getGoodsList(HttpServletRequest request) {
		try {
			if(mainId!=null){
				OutStock outStock = (OutStock)outStockService.get(mainId);
				List<OutStockSub> goodsList = outStock.getSubList();
				return goodsList;
			}else if(mainId == null){
				return goodsCacheList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<OutStockSub>();
	} 
	
	public void setBoxsList(List<DownstreamContainer>  boxList, String saleContractNo){
		for(DownstreamContainer box:boxList){
			OutStockSub outStockSub = new OutStockSub();
			if(mainId != null){
				outStockSub.setParentId(mainId);
			}
			outStockSub.setSaleContractNo(saleContractNo);//销售合同号
			outStockSub.setCpContractNo(box.getCpContractNo());//綜合合同号
			outStockSub.setGoodsNo(box.getGoodsNo());//商品编码
			outStockSub.setGoodsName(box.getGoodsName());//商品名称
			outStockSub.setRealsendPNum(box.getPieceNum()==0?0:box.getPieceNum());//件数
			outStockSub.setRealsendVNum(box.getNum()==0?0:box.getNum());//实发立方数
			outStockSub.setPieceAvailable(box.getpNum()==0?0:box.getpNum());//片数
			outStockSub.setNumAvailable(box.getNum()==0?0:box.getNum());//件数
			outStockSub.setAmount(box.getNum()==0?0:box.getNum());//立方数
			outStockSub.setUnitPrice(Double.valueOf(box.getPurchasePrice()==0?0:box.getPurchasePrice()));//单价
			outStockSub.setMoney(Double.valueOf(box.getTotalPrice()==0?0:box.getTotalPrice()));//金额
			outStockSub.setWaterRate(box.getWaterRate()==null?0:box.getWaterRate());//含水率
			if(box.getWarehouse()!=null){
				outStockSub.setWarehouse(box.getWarehouse());//入库主表id
				Long inStockId = Long.valueOf(box.getWarehouse());//入库主表id
				InStock inStock = inStockService.get(inStockId);
				if(inStock !=null){
					outStockSub.setWarehouse(inStock.getReceiveWarehouse());//仓库ID
				}
			}
			outStockSub.setDataSource("下游交单");
			User currentUser = UserUtil.getCurrentUser();
			outStockSub.setCreaterNo(currentUser.getLoginName());
			outStockSub.setCreaterName(currentUser.getName());
			outStockSub.setCreaterDept(currentUser.getOrganization().getOrgName());
			outStockSub.setCreateDate(new Date());
			outStockSubService.save(outStockSub);
			updateRelation4Down(outStockSub);
		}
	}
	
	private void updateRelation4Down(OutStockSub outStockSub) {
		//*************向出库关联表插入数据   start***************//
		InStock inStock = 
				inStockService.get(Long.valueOf(outStockSub.getInStockId()));//通过入库主表id返回入库子表集合；
		List<InStockGoods> subList = inStock.getInStockGoods();
		for(InStockGoods inStockGoods:subList){
			if(inStockGoods.getGoodsNo().equals(outStockSub.getGoodsNo())){//找到对应入库子表
				InStockOutStockRelation relation = new InStockOutStockRelation();
				relation.setInStockGoods(inStockGoods);//入库商品表id
				relation.setOutStockSub(outStockSub);//出库商品表id
				inStockOutStockRelationService.save(relation);
			}
		}
		//*************向出库关联表插入数据   end***************//
	}
	
	public void setBoxCacheList(List<DownstreamContainer>  boxList, String saleContractNo){
		for(DownstreamContainer box:boxList){
			OutStockSub outStockSub = new OutStockSub();
			outStockSub.setSaleContractNo(saleContractNo);//销售合同号
			outStockSub.setGoodsNo(box.getGoodsNo());//商品编码
			outStockSub.setGoodsName(box.getGoodsName());//商品名称
			outStockSub.setRealsendPNum(box.getPieceNum()==0?0:box.getPieceNum());//件数
			outStockSub.setRealsendVNum(box.getNum()==0?0:box.getNum());//实发立方数
			outStockSub.setPieceAvailable(box.getpNum()==0?0:box.getpNum());//片数
			outStockSub.setNumAvailable(box.getNum()==0?0:box.getNum());//件数
			outStockSub.setAmount(box.getNum()==0?0:box.getNum());//立方数
			outStockSub.setUnitPrice(Double.valueOf(box.getPurchasePrice()==0?0:box.getPurchasePrice()));//单价
			outStockSub.setMoney(Double.valueOf(box.getTotalPrice()==0?0:box.getTotalPrice()));//金额
			outStockSub.setWaterRate(box.getWaterRate()==null?0:box.getWaterRate());//含水率
			if(box.getWarehouse()!=null){
				outStockSub.setWarehouse(box.getWarehouse());//入库主表id
				Long inStockId = Long.valueOf(box.getWarehouse());//入库主表id
				InStock inStock = inStockService.get(inStockId);
				if(inStock !=null){
					outStockSub.setWarehouse(inStock.getReceiveWarehouse());//仓库ID
				}
			}
			outStockSub.setDataSource("下游交单");
			User currentUser = UserUtil.getCurrentUser();
			outStockSub.setCreateDate(new Date());
			outStockSub.setCreaterNo(currentUser.getLoginName());
			outStockSub.setCreaterName(currentUser.getName());
			outStockSub.setCpContractNo(box.getCpContractNo());//綜合合同号
			try {
				outStockSub.setCreaterDept(currentUser.getOrganization().getOrgName());
				goodsCacheList.add(outStockSub);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 出库确认
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "confirm/{id}")
	@ResponseBody
	public String confirm(@PathVariable("id") Long id) {
		OutStock outStock = outStockService.get(id);
		outStock.setConfirm("1");
		outStockService.save(outStock);
		return "success";
	}
	
	/**
	 * 取消确认
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "cancleConfirm/{id}")
	@ResponseBody
	public String cancleConfirm(@PathVariable("id") Long id) {
		OutStock outStock = outStockService.get(id);
		outStock.setConfirm("0"); 
		outStockService.save(outStock);
		return "success";
	}
	
	/**
	 * 通过入库主表id获取仓库名称
	 * 
	 * @param warehouse 入库主表id
	 * @return
	 */
	@RequestMapping(value = "getWarehouseName/{warehouse}")
	@ResponseBody
	public String getWarehouseName(@PathVariable("warehouse") Long warehouse) {
		InStock inStock = inStockService.get(warehouse);
		if(inStock !=null){
			String warehouseId = inStock.getReceiveWarehouse();//仓库ID
			if(StringUtils.isNotBlank(warehouseId)){
				 return ckService.get(Long.valueOf(warehouseId)).getCompanyName();
			}else{
				return "";
			}
		}
		return "fail";
	}
	
	/**
	 * 获取放货和下游交单数据列表
	 */
	@RequestMapping(value="getSendGoodsList",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getSendGoodsList(HttpServletRequest request) {
		Page<OutStockCache> page;
		try {
			String source = request.getParameter("dataSource");
			page = getPage(request);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			if(filters!=null && !filters.isEmpty() &&
					source!=null && source.equals("下游交单")){//下游交单数据源
				String saleContractNo = (String) filters.get(0).getMatchValue();
				Long saleContractId = saleContractService.getIdByContractNo(saleContractNo);
				if(saleContractId==null){
					return getEasyUIData(page);//没找到销售合同对应的下游交单数据
				}
				saleContractNo = saleContractId.toString();
				List<OutStockCache> cacheList = saleContractService.getCacheByContractNo(saleContractNo);
				if(cacheList !=null && !cacheList.isEmpty()){
					page.setResult(cacheList);
					page.setTotalCount(cacheList.size());
				}
			}else{//放货数据源
				page = outStockCacheService.searchByPermission(page, filters,new HashMap<>());
			}
			List<OutStockCache> cacheList = page.getResult();
			for(int i=0;i< cacheList.size();i++){
				if(isNumeric(cacheList.get(i).getState())){//state是数字，说明为下游交单数据源,根据销售合同id找到销售合同号
					String contractNo = getNoById(cacheList.get(i).getSaleContractNo());
					cacheList.get(i).setSaleContractNo(contractNo);
				}
			}
			return getEasyUIData(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
