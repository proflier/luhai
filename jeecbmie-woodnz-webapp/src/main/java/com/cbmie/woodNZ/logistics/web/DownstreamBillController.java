package com.cbmie.woodNZ.logistics.web;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.logistics.entity.DownstreamBill;
import com.cbmie.woodNZ.logistics.entity.DownstreamGoods;
import com.cbmie.woodNZ.logistics.entity.WoodBills;
import com.cbmie.woodNZ.logistics.entity.WoodBillsGoods;
import com.cbmie.woodNZ.logistics.entity.WoodBillsSub;
import com.cbmie.woodNZ.logistics.service.BillsGoodsService;
import com.cbmie.woodNZ.logistics.service.BillsService;
import com.cbmie.woodNZ.logistics.service.BillsSubService;
import com.cbmie.woodNZ.logistics.service.DownstreamBillService;
import com.cbmie.woodNZ.logistics.service.DownstreamGoodsService;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSameSub;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContractSub;
import com.cbmie.woodNZ.salesContract.service.PurchaseSaleSameSubService;
import com.cbmie.woodNZ.salesContract.service.SaleContractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 注意：所有获取下游到单信息列表的必须通过downstreamBillList (json)方法
 * 下游到单controller
 */
@Controller
@RequestMapping("logistics/downstreamBill")
public class DownstreamBillController extends BaseController {


	@Autowired
	private DownstreamBillService downstreamBillService;
	
	@Autowired
	private DownstreamGoodsService downstreamGoodsService;
	
	
	@Autowired
	private SaleContractService saleContractService;
	
	@Autowired
	private PurchaseSaleSameSubService purchaseSaleSameSubService;
	
	@Autowired
	private BillsService billsService;
	
	@Autowired
	private BillsSubService billsSubService;
	
	@Autowired
	private BillsGoodsService billsGoodsService;
	
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistics/downBillsList";
	}

	/**
	 * 获取下游到单json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> downstreamBillList(HttpServletRequest request) {
		Page<DownstreamBill> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = downstreamBillService.search(page, filters);
		Iterator<DownstreamBill> it = page.getResult().iterator();
		while (it.hasNext()) {
			DownstreamBill downstreamBill = (DownstreamBill) it.next();
			if(StringUtils.isNotBlank(downstreamBill.getBillNo())){
			}else{
				downstreamBillService.delete(downstreamBill);
				it.remove();
			}
		}
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		DownstreamBill downstreamBill = new DownstreamBill();
		downstreamBillService.save(downstreamBill);
		model.addAttribute("downstreamBill",downstreamBill);
		model.addAttribute("action", "create");
		return "logistics/downBillsForm";
	}

	/**
	 * 添加下游到单
	 * 
	 * @param downstreamBill
	 * @param model
	 * @throws JsonProcessingException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid DownstreamBill downstreamBill, Model model,
			@RequestParam("downstreamGoodsJson") String downstreamGoodsJson) throws JsonProcessingException, IllegalAccessException, InvocationTargetException {
		if (downstreamBillService.findByNo(downstreamBill) != null) {
			return setReturnData("fail","下游到单号重复",downstreamBill.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		downstreamBill.setCreaterNo(currentUser.getLoginName());
		downstreamBill.setCreaterName(currentUser.getName());
		downstreamBill.setCreateDate(new Date());
		downstreamBill.setCreaterDept(currentUser.getOrganization().getOrgName());
		downstreamBillService.save(downstreamBill);
		if(StringUtils.isNotBlank(downstreamGoodsJson)){
			downstreamGoodsService.save(downstreamBill, downstreamGoodsJson);
		}
		downstreamBill = setCpContractNo(downstreamBill);
		downstreamBillService.update(downstreamBill);
		return setReturnData("success","保存成功",downstreamBill.getId());
	}


	/**
	 * 修改下游到单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		DownstreamBill downstreamBill = new DownstreamBill();
		downstreamBill = downstreamBillService.get(id);
		downstreamBill.setDownstreamGoodsList(downstreamGoodsService.getByParentId(id));
		model.addAttribute("downstreamBill", downstreamBill);
		model.addAttribute("action", "update");
		return "logistics/downBillsForm";
	}

	/**
	 * 修改下游到单
	 * 
	 * @param downstreamBill
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody DownstreamBill downstreamBill, Model model,@RequestParam("downstreamGoodsJson") String downstreamGoodsJson) throws JsonProcessingException, IllegalAccessException, InvocationTargetException {
		if (downstreamBillService.findByNo(downstreamBill) != null) {
			return setReturnData("fail","下游到单号重复",downstreamBill.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		downstreamBill.setUpdaterNo(currentUser.getLoginName());
		downstreamBill.setUpdaterName(currentUser.getName());
		downstreamBill.setUpdateDate(new Date());
		if(StringUtils.isNotBlank(downstreamGoodsJson)){
			downstreamGoodsService.save(downstreamBill, downstreamGoodsJson);
		}
		downstreamBill = setCpContractNo(downstreamBill);
		downstreamBillService.update(downstreamBill);
		return setReturnData("success","保存成功",downstreamBill.getId());
	}
	
	/**
	 * 删除下游到单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		downstreamBillService.delete(id);
		return "success";
	}
	
	/**
	 * 确认下游到单
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "confirm/{id}")
	@ResponseBody
	public String confirm(@PathVariable("id") Long id) throws JsonProcessingException {
		DownstreamBill downstreamBill = downstreamBillService.get(id);
		downstreamBill.setConfirm("1");
		downstreamBillService.save(downstreamBill);
		return setReturnData("success","确认成功",downstreamBill.getId());
	}
	
	/**
	 * 取消确认下游到单
	 * 
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "cancleConfirm/{id}")
	@ResponseBody
	public String cancleConfirm(@PathVariable("id") Long id) throws JsonProcessingException {
		String result = downstreamBillService.getChose(id);
		if(result.equals("1")){//如果此交单出过库，则不允许取消确认
			return setReturnData("fail","交单已经出库，不能取消确认!",null);
		}
		DownstreamBill downstreamBill = downstreamBillService.get(id);
		downstreamBill.setConfirm("0");
		downstreamBillService.save(downstreamBill);
		return setReturnData("success","成功取消确认",downstreamBill.getId());
	}
	
	
	/**
	 * 查看下游到单明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		DownstreamBill downstreamBill = new DownstreamBill();
		downstreamBill = downstreamBillService.get(id);
		downstreamBill.setDownstreamGoodsList(downstreamGoodsService.getByParentId(id));
		model.addAttribute("downstreamBill", downstreamBill);
		return "logistics/downBillsDetail";
	}
	
	@ModelAttribute
	public void getDownstreamBill(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("downstreamBill", downstreamBillService.get(id));
		}
	}
	
	/**
	 * 销售合同选择跳转
	 * @return
	 */
	@RequestMapping(value = "toSaleList", method = RequestMethod.GET)
	public String loadBillId() {
		return "logistics/loadSaleContract";
	}
	
	/**
	 * 根据选择的销售合同显示对应的 上游交单
	 * @param displayValue 销售合同号
	 * @return
	 */
	@RequestMapping(value = "searchBills/{displayValue}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> searchBills(@PathVariable("displayValue") String displayValue) {
		Map<String,Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(displayValue)){
			List<WoodBills>  returnList = downstreamBillService.searchBills(displayValue);
			result.put("rows", returnList);
			result.put("totalCount", returnList.size());
			return result;
		}else{
			return null;
		}
	}
	
	
	
	/**
	 * 获取有采销关系的销售合同列表
	 * 获取saleJson
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="saleJson",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodSaleContract> outList(HttpServletRequest request) throws JsonProcessingException {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		List<WoodSaleContract> woodSaleContractList = saleContractService.search(filters);
//		page = saleContractService.search(page, filters);
		/**
		 * 过滤销售合同，如果不存在与销售合同对应的到单则移除
		 */
		if(woodSaleContractList.size()>0){
			Iterator<WoodSaleContract> it = woodSaleContractList.iterator();
			while (it.hasNext()) {
				WoodSaleContract woodSaleContract = (WoodSaleContract) it.next();
				List<PurchaseSaleSameSub> purchaseSaleSameSubList =  purchaseSaleSameSubService.getBySaleId(woodSaleContract.getId());
				/**
				 * 过滤采销同批
				 */
				if(purchaseSaleSameSubList.size()<=0){
					it.remove();
				}else{
					/**
					 * 过滤不存在对应的到单的销售合同
					 */
					for(int i=0;i<purchaseSaleSameSubList.size();i++){
						PurchaseSaleSameSub purchaseSaleSameSub = purchaseSaleSameSubList.get(i);
						List<WoodBills>  returnList = downstreamBillService.searchBills(purchaseSaleSameSub.getWoodSaleContract().getId().toString());
						if(returnList.size()<=0){
							it.remove();
						}
					}
					
				}
			}
		}
		return woodSaleContractList;
	}
	
	/**
	 * 根据选择的下上游交单信息  加载 下游交单信息
	 * @param request
	 * @param saleId
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value="billJson/{saleId}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request,@PathVariable("saleId") Long saleId) throws JsonProcessingException {
		Page<WoodBills> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = billsService.search(page, filters);
		List<WoodBillsSub> woodBillsSubList = new ArrayList<WoodBillsSub>();
		List<WoodBillsGoods> goodsList = new ArrayList<WoodBillsGoods>();
		List<WoodBills> woodBillsList = billsService.search(filters);
		WoodSaleContract woodSaleContract = saleContractService.get(saleId);
		List<WoodSaleContractSub> saleContractSubList = woodSaleContract.getSaleContractSubList();
		
		if(woodBillsList.size()>0){
			woodBillsSubList = billsSubService.getByParentId(woodBillsList.get(0).getId());
			if(woodBillsSubList.size()>0){
				ObjectMapper objectMapper = new ObjectMapper();
				String testJson = objectMapper.writeValueAsString(woodBillsSubList);
				page.getResult().get(0).setReturnJson(testJson);
			}
			goodsList = billsGoodsService.getByParentId(woodBillsList.get(0).getId());
			if(goodsList.size()>0){
				for(WoodBillsGoods woodBillsGoods : goodsList){
					for(WoodSaleContractSub woodSaleContractSub :saleContractSubList){
						if(woodBillsGoods.getSpbm().equals(woodSaleContractSub.getGoodsNo())){
							woodBillsGoods.setCgdj(String.valueOf(woodSaleContractSub.getUnitPrice()));
							Double cgje = formatDouble(woodBillsGoods.getCgdj())*formatDouble(woodBillsGoods.getSl());
							woodBillsGoods.setCgje(String.valueOf(formatDouble(cgje)));
						}
					}
					woodBillsGoods.setSaleContractNo(woodSaleContract.getContractNo());
				}
				ObjectMapper objectMapper = new ObjectMapper();
				String goodJson = objectMapper.writeValueAsString(goodsList);
				page.getResult().get(0).setReturnGoodsJson(goodJson);
			}
		}
		return getEasyUIData(page);
	}
	
	
	public Double formatDouble(double source){
		BigDecimal   b   =   new   BigDecimal(source);  
		double   souce   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		return souce;
	}
	
	public Double formatDouble(String source){
		BigDecimal   b   =   new   BigDecimal(source);  
		double   souce   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		return souce;
	}
	
	
	/**
	 * 设置综合合同号
	 * @param downstreamBill old
	 * @return downstreamBill new
	 */
	private DownstreamBill setCpContractNo(DownstreamBill downstreamBill) {
		List<DownstreamGoods> downstreamGoodsList = downstreamBill.getDownstreamGoodsList();
		String[] contractNo = {};
		for(DownstreamGoods downstreamGoods :downstreamGoodsList){
			contractNo=Arrays.copyOf(contractNo, contractNo.length+1);
			contractNo[contractNo.length-1]= downstreamGoods.getCpContractNo();
		}
		downstreamBill.setCpContractNo(Arrays.toString(contractNo).replace("[", "").replace("]", ""));
		return downstreamBill;
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "superSearch", method = RequestMethod.GET)
	public String superSearch() {
		return "logistics/superSearch";
	}
	
	
}
