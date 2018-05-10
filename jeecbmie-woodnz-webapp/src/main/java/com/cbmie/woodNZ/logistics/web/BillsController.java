package com.cbmie.woodNZ.logistics.web;


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
import com.cbmie.system.web.DownMenuController;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMx;
import com.cbmie.woodNZ.cgcontract.service.WoodCghtJkService;
import com.cbmie.woodNZ.logistics.entity.WoodBills;
import com.cbmie.woodNZ.logistics.entity.WoodBillsGoods;
import com.cbmie.woodNZ.logistics.entity.WoodBillsSub;
import com.cbmie.woodNZ.logistics.service.BillsGoodsService;
import com.cbmie.woodNZ.logistics.service.BillsService;
import com.cbmie.woodNZ.logistics.service.BillsSubService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 到单信息controller
 */
@Controller
@Scope("session")
@RequestMapping("logistics/bills")
public class BillsController extends BaseController{
	

	@Autowired
	private BillsService billsService;
	
	@Autowired
	private BillsSubService billsSubService;
	
	@Autowired
	private WoodCghtJkService woodCghtJkService;
	
	@Autowired
	private BillsGoodsService billsGoodsService;
	
	@Autowired
	private DownMenuController downMenuController;
	
	
	private Long billsId=null;//到单主表id

	private List<WoodBillsGoods> goodsCacheList = new ArrayList<WoodBillsGoods>();//临时货物明细数据，存储保存到单主表前的货物数据
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		billsId = null;//到单主表id
		return "logistics/billsList";
	}
	
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) throws JsonProcessingException {
		Page<WoodBills> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = billsService.search(page, filters);
		List<WoodBillsSub> woodBillsSubList = new ArrayList<WoodBillsSub>();
		List<WoodBillsGoods> goodsList = new ArrayList<WoodBillsGoods>();
		List<WoodBills> woodBillsList = billsService.search(filters);
		if(woodBillsList.size()>0){
			woodBillsSubList = billsSubService.getByParentId(woodBillsList.get(0).getId());
			if(woodBillsSubList.size()>0){
				ObjectMapper objectMapper = new ObjectMapper();
				String testJson = objectMapper.writeValueAsString(woodBillsSubList);
				page.getResult().get(0).setReturnJson(testJson);
			}
			goodsList = billsGoodsService.getByParentId(woodBillsList.get(0).getId());
			if(goodsList.size()>0){
				ObjectMapper objectMapper = new ObjectMapper();
				String goodJson = objectMapper.writeValueAsString(goodsList);
				page.getResult().get(0).setReturnGoodsJson(goodJson);
			}
		}
		return getEasyUIData(page);
	} 
	

	/**
	 * 添加到单跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		try {
			WoodBills info = new WoodBills();
			User currentUser = UserUtil.getCurrentUser();
			info.setCreaterNo(currentUser.getLoginName());
			info.setCreaterName(currentUser.getName());
			info.setCreaterDept(currentUser.getOrganization().getOrgName());
			billsId = null;
			goodsCacheList.clear();
			model.addAttribute("bills", info);
			model.addAttribute("action", "create");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logistics/billsForm";
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
	public String create(@Valid WoodBills info, Model model,
			@RequestParam("billGoodsJson") String billGoodsJson) throws JsonProcessingException {
		boolean result = billsService.validateBillNo(info.getBillNo(),info.getId());//提单号重复校验
		if(result==true){
			return setReturnData("fail","保存失败，提单号"+info.getBillNo()+"重复！",null);
		}
		info.setCreateDate(new Date());
		billsService.save(info);
		if(StringUtils.isNotBlank(billGoodsJson)){
			billsGoodsService.save(info, billGoodsJson);
		}
		billsId = info.getId();//当前的到单id
//		this.billsId.set(billsId);  //当前的到单id
		return setReturnData("success","保存成功",info.getId());
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
		billsId = id;
		model.addAttribute("bills", billsService.get(id));
		model.addAttribute("action", "update");
		return "logistics/billsForm";
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
	public String update(@Valid @ModelAttribute @RequestBody WoodBills info,Model model,
			@RequestParam("billGoodsJson") String billGoodsJson) throws JsonProcessingException {
		boolean result = billsService.validateBillNo(info.getBillNo(),info.getId());//提单号重复校验
		if(result==true){
			return setReturnData("fail","保存失败，提单号"+info.getBillNo()+"重复！",null);
		}
		User currentUser = UserUtil.getCurrentUser();
		info.setUpdaterName(currentUser.getName());
		info.setUpdateDate(new Date());
		billsService.update(info);
		if(StringUtils.isNotBlank(billGoodsJson)){
			billsGoodsService.save(info, billGoodsJson);
		}
		return setReturnData("success","保存成功",info.getId());
	}

	/**
	 * 删除到单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		billsService.delete(id);
		return "success";
	}
	
	/**
	 * 到单明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("bills", billsService.get(id));
		model.addAttribute("action", "detail");
		return "logistics/billsDetail";
	}
	
	
	/**
	 * 箱单页面跳转 
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "createBox", method = RequestMethod.GET)
	public String createBox(Model model) throws ParseException {
		try {
			WoodBillsSub billsSub = new WoodBillsSub();
			User currentUser = UserUtil.getCurrentUser();
			billsSub.setCreaterNo(currentUser.getLoginName());
			billsSub.setCreaterName(currentUser.getName());
			billsSub.setCreaterDept(currentUser.getOrganization().getOrgName());
			if(billsId != null){
				billsSub.setParentId(billsId.toString());
//				Long id = this.billsId.get();
				WoodBills bills = billsService.get(billsId);
				if(bills != null){
					billsSub.setNumberUnits(bills.getNumberUnits()==null?"":bills.getNumberUnits());//数量单位默认值与到单主表中的保持一致
					billsSub.setCurrency(bills.getCurrency()==null?"":bills.getCurrency());//币种默认值与到单主表中的保持一致
					List<WoodBillsGoods> goodsList = getCpContractNo();
					if(goodsList != null)
						billsSub.setCpContractNo(goodsList.get(0).getCpContractNo()!=null?(goodsList.get(0).getCpContractNo()):"");//综合合同号默认值
				}
				
			}
			model.addAttribute("billsSub", billsSub);
			model.addAttribute("actionBox", "createBox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logistics/billsBoxForm";
	}
	
	/**
	 * 添加箱单
	 * 
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@ResponseBody
	@RequestMapping(value = "createBox",method = RequestMethod.POST)
	public String createBox(@Valid WoodBillsSub box,Model model) throws JsonProcessingException {
		try {
			User currentUser = UserUtil.getCurrentUser();
			box.setCreaterNo(currentUser.getLoginName());
			box.setCreaterName(currentUser.getName());
			box.setCreaterDept(currentUser.getOrganization().getOrgName());
			box.setCreateDate(new Date());
			if(billsId != null){
				box.setParentId(billsId.toString());
			}
			billsSubService.save(box);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setReturnData("success","保存成功",box.getId());
	}
	
	/**
	 * 获取箱单列表json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="getBoxList",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBoxList(){
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String,Object> total = new HashMap<String, Object>();
		total.put("boxNo", "合计 : ");
		total.put("num", "");
		total.put("pieceNum", "");
		total.put("pNum", "");
		total.put("totalPrice", "");
		List<Map> totalList = new ArrayList<Map>();
		totalList.add(total);
		if(billsId==null){
			result.put("rows",new ArrayList<WoodBillsSub>());
			result.put("footer", totalList);
		}else{
			WoodBills woodBills = (WoodBills)billsService.get(billsId);
			List<WoodBillsSub> subList = woodBills.getSubList();
			result.put("rows", subList);
			result.put("footer", totalList);
		}
		return result;
	}
	
	
	/**
	 * 修改箱单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateBox/{id}", method = RequestMethod.GET)
	public String updateBox(@PathVariable("id") Long id, Model model) {
		try {
			model.addAttribute("billsSub", billsSubService.get(id));
			model.addAttribute("actionBox", "updateBox");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "logistics/billsBoxForm";
	}

	/**
	 * 修改箱单
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "updateBox", method = RequestMethod.POST)
	@ResponseBody
	public String updateBox(@Valid @ModelAttribute @RequestBody WoodBillsSub info,Model model) throws JsonProcessingException {
		if(info.getId()!=null){//id不为空，执行更新方法
			User currentUser = UserUtil.getCurrentUser();
			info.setUpdaterName(currentUser.getName());
			info.setUpdateDate(new Date());
			billsSubService.update(info);
		}else if(info.getId()==null){//id为空，即为箱单复制方法，执行新增方法
			User currentUser = UserUtil.getCurrentUser();
			info.setCreaterNo(currentUser.getLoginName());
			info.setCreaterName(currentUser.getName());
			info.setCreaterDept(currentUser.getOrganization().getOrgName());
			info.setCreateDate(new Date());
			info.setParentId(billsId == null?"":billsId.toString());
			billsSubService.save(info);
		}
		return setReturnData("success","保存成功",info.getId());
	}
	
	/**
	 * 删除箱单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteBox/{id}")
	@ResponseBody
	public String deleteBox(@PathVariable("id") Long id) {
		billsSubService.delete(id);
		return "success";
	}
	
	/**
	 * 复制新增箱单
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "addCopyBox/{id}")
	@ResponseBody
	public String addCopyBox(@PathVariable("id") Long id) throws JsonProcessingException {
		try {
			WoodBillsSub boxOld =  billsSubService.get(id);
			WoodBillsSub box =  new WoodBillsSub();
			User currentUser = UserUtil.getCurrentUser();
			box.setCreaterNo(currentUser.getLoginName());
			box.setCreaterName(currentUser.getName());
			box.setCreaterDept(currentUser.getOrganization().getOrgName());
			box.setCreateDate(new Date());
			
			box.setCpContractNo(boxOld.getCpContractNo()==null?"":boxOld.getCpContractNo());
			box.setBoxNo(boxOld.getBoxNo()==null?"":boxOld.getBoxNo());
			box.setGoodsNo(boxOld.getGoodsNo()==null?"":boxOld.getGoodsNo());
			box.setGoodsName(boxOld.getGoodsName()==null?"":boxOld.getGoodsName());
			box.setGoodsDes(boxOld.getGoodsDes()==null?"":boxOld.getGoodsDes());
			box.setpNum(boxOld.getpNum());
			box.setCurrency(boxOld.getCurrency()==null?"":boxOld.getCurrency());
			box.setPurchasePrice(boxOld.getPurchasePrice());
			box.setPieceNum(boxOld.getPieceNum());
			box.setNum(boxOld.getNum());
			box.setNumberUnits(boxOld.getNumberUnits());
			box.setTotalPrice(boxOld.getTotalPrice());
			box.setParentId(boxOld.getParentId());
			box.setWaterRate(boxOld.getWaterRate()==null?0:boxOld.getWaterRate());//含水率
			billsSubService.save(box);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 采购合同页面跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toPurchaseList", method = RequestMethod.GET)
	public String toPurchaseList() {
		goodsCacheList.clear();//清空临时货物明细列表
		return "logistics/billsPurchaseListForm";
	}

	
	/**
	 * 获取采购合同数据列表
	 */
	@RequestMapping(value="getPurchaseList",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPurchaseList(HttpServletRequest request) {
		Page<WoodCghtJk> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = woodCghtJkService.search(page, filters);
		return getEasyUIData(page);
	} 

	/**
	 * 选择采购合同后执行此方法
	 * 
	 * @param purchaseArray
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "setGoods", method = RequestMethod.POST)
	@ResponseBody
	public String setGoods(Model model,@RequestParam("purchaseArray") List<String> purchaseArray) throws JsonProcessingException {
		//先清除货物明细数据
		if(billsId != null){
			WoodBills bills = billsService.get(billsId);
			List<WoodBillsGoods> billsGoodsList = bills.getGoodsList();
			List<WoodBillsGoods> dataList = new ArrayList<WoodBillsGoods>();
			for(WoodBillsGoods g:billsGoodsList){
				dataList.add(g);
			}
			for(WoodBillsGoods goods:dataList){
				billsGoodsList.remove(goods);
				goods.setParentId(null);
				billsGoodsService.delete(goods);
			}
		}else if(billsId == null ){
			goodsCacheList.clear();
		}
		
		for(String id:purchaseArray){
			WoodCghtJk contract = woodCghtJkService.get(Long.valueOf(id));
			List<WoodCghtJkMx> mxList = contract.getWoodCghtJkMxList();
			if(billsId != null){
				setGoodsList(mxList,contract.getInterContractNo());//从采购合同子表获取数据
			}else if(billsId == null){
				setGoodsCacheList(mxList,contract.getInterContractNo());//从采购合同子表获取数据存储在临时货物明细list中
			}
		}
		//相同信用证号下的采购合同的供货单位和收方单位数据一样，获取第一个采购合同的数据即可，采购币种也从中带出
		WoodCghtJk contract = woodCghtJkService.get(Long.valueOf(purchaseArray.get(0)));
		Map<String,Object> map = new HashMap<String,Object>();
		String ghdw = contract.getGhdw()==null?"":contract.getGhdw();//供货单位
		String sfdw = contract.getSfdw()==null?"":contract.getSfdw();//收方单位
		String interContractNo = contract.getInterContractNo()==null?"":contract.getInterContractNo();//综合合同号
		String currency = contract.getCgbz()==null?"":contract.getCgbz();//币种
		map.put("ghdw", ghdw);
		map.put("sfdw", sfdw);
		map.put("currency",  currency);
		map.put("interContractNo", interContractNo);
		map.put("state",  "success");
		ObjectMapper mapper = new ObjectMapper();
		String stringJson = mapper.writeValueAsString(map);
		return stringJson;
	}
	
	//参数为采购合同商品明细和采购合同中的综合合同号
	public void setGoodsList(List<WoodCghtJkMx> mxList, String cpContractNo){
		for(WoodCghtJkMx mx:mxList){
			WoodBillsGoods billsGoods = new WoodBillsGoods();
			if(billsId != null){
				billsGoods.setParentId(billsId.toString());
			}
			String cgjeString;
			String cgdjString;
			try {
				Long parentId = Long.valueOf(mx.getParentId());//采购合同主表ID
				WoodCghtJk purchaseContract = woodCghtJkService.get(parentId);
				billsGoods.setCghth(purchaseContract.getHth());//从采购合同子表获取数据
				billsGoods.setCpContractNo(cpContractNo);//综合合同号赋值
				billsGoods.setSpbm(mx.getSpbm()==null?"":mx.getSpbm());
				billsGoods.setSpmc(mx.getSpmc()==null?"":mx.getSpmc());
				billsGoods.setSldw(mx.getSldw()==null?"":mx.getSldw());
				billsGoods.setJs(mx.getJs().equals("")?"0":mx.getJs());
				billsGoods.setCgbz(mx.getCgbz()==null?"":mx.getCgbz());
				billsGoods.setSl(mx.getLfs().equals("")?"0":mx.getLfs());
				billsGoods.setWaterRate(mx.getWaterRate()==null?0:mx.getWaterRate());//含水率
				
				Double cgje = Double.valueOf(mx.getCgje().equals("")?"0":mx.getCgje());//保留2位小数
				cgjeString = String.format("%.2f", cgje);
				Double cgdj = Double.valueOf(mx.getCgdj().equals("")?"0":mx.getCgdj());
				cgdjString = String.format("%.2f", cgdj);
				
				billsGoods.setCgdj(mx.getCgdj().equals("")?"0":cgdjString);
				billsGoods.setCgje(mx.getCgje().equals("")?"0":cgjeString);
				billsGoodsService.save(billsGoods);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}
	
	//参数为采购合同商品明细和采购合同中的综合合同号
	public void setGoodsCacheList(List<WoodCghtJkMx> mxList, String cpContractNo){
		try {
			for(WoodCghtJkMx mx:mxList){
				WoodBillsGoods billsGoods = new WoodBillsGoods();
				Long parentId = Long.valueOf(mx.getParentId());//采购合同主表ID
				WoodCghtJk purchaseContract = woodCghtJkService.get(parentId);
				billsGoods.setCghth(purchaseContract.getHth());//从采购合同子表获取数据
				billsGoods.setCpContractNo(cpContractNo);//综合合同号赋值
				billsGoods.setSpbm(mx.getSpbm()==null?"":mx.getSpbm());
				billsGoods.setSpmc(mx.getSpmc()==null?"":mx.getSpmc());
				billsGoods.setSldw(mx.getSldw()==null?"":mx.getSldw());
				billsGoods.setJs(mx.getJs().equals("")?"0":mx.getJs());
				billsGoods.setCgbz(mx.getCgbz()==null?"":mx.getCgbz());
				billsGoods.setSl(mx.getLfs().equals("")?"0":mx.getLfs());
				billsGoods.setWaterRate(mx.getWaterRate()==null?0:mx.getWaterRate());//含水率
				
				Double cgje = Double.valueOf(mx.getCgje().equals("")?"0":mx.getCgje());//保留2位小数
				String cgjeString = String.format("%.2f", cgje);
				Double cgdj = Double.valueOf(mx.getCgdj().equals("")?"0":mx.getCgdj());
				String cgdjString = String.format("%.2f", cgdj);
				
				billsGoods.setCgdj(mx.getCgdj().equals("")?"0":cgdjString);
				billsGoods.setCgje(mx.getCgje().equals("")?"0":cgjeString);
				goodsCacheList.add(billsGoods);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取货物明细列表
	 */
	@RequestMapping(value="getGoodsList",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodBillsGoods> getGoodsList(HttpServletRequest request) {
		try {
			if(billsId!=null){
//				Long id=this.billsId.get();
				WoodBills woodBills = (WoodBills)billsService.get(billsId);
				List<WoodBillsGoods> goodsList = woodBills.getGoodsList();
				return goodsList;
			}else if(billsId==null){
				return goodsCacheList;//如果主表未保存，则返回临时货物列表
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<WoodBillsGoods>();
	} 
	
	@ModelAttribute
	public void getBills(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("woodBills", billsService.get(id));
		}
	}
	
	@ModelAttribute
	public void getBillsSub(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("woodBillsSub", billsSubService.get(id));
		}
	}
	
	/**
	 * 删除货物明细
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteGoods/{id}")
	@ResponseBody
	public String deleteGoods(@PathVariable("id") Long id) {
		billsGoodsService.delete(id);
		return "success";
	}
	
	/**
	 * 综合合同号下拉选
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getCpContractNo")
	@ResponseBody
	public List<WoodBillsGoods> getCpContractNo() {
		if(billsId!=null){
			WoodBills woodBills = (WoodBills)billsService.get(billsId);
			List<WoodBillsGoods> goodsList = woodBills.getGoodsList();
			List<WoodBillsGoods> newList = removeDuplicate(goodsList);
			return newList;
		}
		return null;
	}
	
	//list去除重复项
	public List<WoodBillsGoods>  removeDuplicate(List<WoodBillsGoods> list) {
	   for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
	     for ( int j = list.size() - 1 ; j > i; j -- ) {
	    	   if(list.get(j).getCpContractNo()==null || list.get(i).getCpContractNo() == null){continue;}
		       if (list.get(j).getCpContractNo().equals(list.get(i).getCpContractNo())) {
		         list.remove(j);
		       }
	      }
	    }
	    return list;
	}
	
	
	/**
	 * 到单确认
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "confirm/{id}")
	@ResponseBody
	public String confirm(@PathVariable("id") Long id) {
		WoodBills woodBills = billsService.get(id);
		woodBills.setConfirm("1");
		billsService.save(woodBills);
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
		WoodBills woodBills = billsService.get(id);
		woodBills.setConfirm("0");
		billsService.save(woodBills);
		return "success";
	}
	
}
