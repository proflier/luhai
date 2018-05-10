package com.cbmie.lh.stock.web;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import org.springframework.web.multipart.MultipartFile;

import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.service.InStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 入库controller
 */
@Controller
@RequestMapping("stock/inStock")
public class InStockController extends BaseController {

	@Autowired
	private InStockService inStockService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/inStockList";
	}

	/**
	 * 获取入库json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<InStock> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		inStockService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = inStockService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加入库跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		InStock inStock = new InStock();
		inStock.setCreateDate(new Date());
		inStock.setUpdateDate(new Date());
		String inStockId = sequenceCodeService.getNextCode("001019").get("returnStr");
		inStock.setInStockId(inStockId);
		model.addAttribute("inStock", inStock);
		model.addAttribute("action", "create");
		return "stock/inStockForm";
	}

	/**
	 * 添加库存历史数据跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "addOld", method = RequestMethod.GET)
	public String addOld(Model model) {
		InStock inStock = new InStock();
		inStock.setHistoryData("1");
		inStockService.save(inStock);
		model.addAttribute("inStock", inStock);
		model.addAttribute("action", "create");
		return "stock/addOldStockForm";
	}
	
	/**
	 * 添加入库
	 * 
	 * @param inStock
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InStock inStock, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (inStockService.findByNo(inStock) != null) {
			String inStockId = sequenceCodeService.getNextCode("001019").get("returnStr");
			inStock.setInStockId(inStockId);
			currnetSequence = inStockId;
		}
		if (inStockService.findByBillId(inStock) != null) {
			return setReturnData("fail", "同一提单重复入库！", inStock.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		if(inStock.getCreaterNo()==null){
			//调用获取下个sequence方法，将当前sequence保存
			sequenceCodeService.getNextSequence("001019");
		}
		inStock.setCreaterNo(currentUser.getLoginName());
		inStock.setCreaterName(currentUser.getName());
		inStock.setCreateDate(new Date());
		inStock.setCreaterDept(currentUser.getOrganization().getOrgName());
		inStockService.save(inStock);
		return setReturnData("success", currnetSequence!=null?"入库号重复，已自动生成不重复编码并保存":"保存成功", inStock.getId(),currnetSequence);
	}

	/**
	 * 修改入库跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inStock", inStockService.get(id));
		model.addAttribute("action", "update");
		return "stock/inStockForm";
	}

	/**
	 * 修改入库
	 * 
	 * @param inStock
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InStock inStock, Model model)
			throws JsonProcessingException {
//		if (inStockService.findByNo(inStock) != null) {
//			return setReturnData("fail", "入库号重复！", inStock.getId());
//		}
		if (inStockService.findByBillId(inStock) != null) {
			return setReturnData("fail", "同一提单重复入库！", inStock.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		inStock.setUpdaterNo(currentUser.getLoginName());
		inStock.setUpdaterName(currentUser.getName());
		inStock.setUpdateDate(new Date());
		inStockService.update(inStock);
		return setReturnData("success", "保存成功", inStock.getId());
	}

	/**
	 * 删除入库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		inStockService.delete(id);
		return "success";
	}

	/**
	 * 查看入库明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("inStock", inStockService.get(id));
		return "stock/inStockDetail";
	}

	 /**
	 * 确认入库
	 *
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	 @RequestMapping(value = "confirm/{id}")
	 @ResponseBody
	 public String confirm(@PathVariable("id") Long id) throws JsonProcessingException {
	 if(inStockService.checkGoods(id)){
		 InStock instock = inStockService.get(id);
		 instock.setConfirm("1");
		 instock.setDetermineTime(new Date());
		 User currentUser = UserUtil.getCurrentUser();
		 instock.setDetermineName(currentUser.getName());
		 inStockService.save(instock);
		 return setReturnData("success", "保存成功", instock.getId());
	 }else{
		 return setReturnData("fail", "存在未选择仓库商品！请检查！", id);
	 }
	 }
	
	 /**
	 * 取消确认入库
	 *
	 * @param id
	 * @return
	 */
	 @RequestMapping(value = "cancleConfirm/{id}")
	 @ResponseBody
	 public String cancleConfirm(@PathVariable("id") Long id) {
	 InStock instock = inStockService.get(id);
	 instock.setConfirm("0");
	 instock.setDetermineTime(null);
	 instock.setDetermineName(null);
	 inStockService.save(instock);
	 return "success";
	 }
	 
	 /**
	 * 复核入库
	 *
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	 @RequestMapping(value = "review/{id}")
	 @ResponseBody
	 public String review(@PathVariable("id") Long id) throws JsonProcessingException {
		 InStock instock = inStockService.get(id);
		 instock.setReview("1");
		 inStockService.save(instock);
		 return setReturnData("success", "操作成功", instock.getId());
	 }
	
	 /**
	 * 取消复核入库
	 * @param id
	 * @return
	 */
	 @RequestMapping(value = "cancleReview/{id}")
	 @ResponseBody
	 public String cancleReview(@PathVariable("id") Long id) {
	 InStock instock = inStockService.get(id);
	 instock.setReview("0");
	 inStockService.save(instock);
	 return "success";
	 }

	@ModelAttribute
	public void getInStock(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inStock", inStockService.get(id));
		}
	}

	/**
	 * 提单号配置跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadBillId", method = RequestMethod.GET)
	public String loadBillId() {
		return "stock/loadBillId";
	}
	
	/**
	 * 获取尚未入库的到单
	 * @return
	 */
	@RequestMapping(value = "selectBillsNoRepeict/{billNo:}", method = RequestMethod.GET)
	@ResponseBody
	public List<LhBills> selectBillsNoRepeict(@PathVariable("billNo") String billNo) {
		return inStockService.selectBillsNoRepeict(billNo);
	}
	
	/**
	 * 质检跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadInspectionNo", method = RequestMethod.GET)
	public String loadInspectionNo() {
		return "stock/loadInspectionNo";
	}

	/**
	 * 历史库存导入跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "toReadExcel", method = RequestMethod.GET)
	public String toReadExcel() {
		return "stock/historyRead";
	}
	
	@ResponseBody
	@RequestMapping(value = "readExcel", method = RequestMethod.POST)
	public String readExcel( @RequestParam("file") MultipartFile file)   {
			try {
				String msg = "";
				try {
					msg = inStockService.importExcel(file);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				if("success".equals(msg)){
					return setReturnData("success", "历史数据上传成功", null);
				}else{
					return setReturnData("fail", msg, null);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			}
			
	}
	
	@RequestMapping(value = "saveOnChangeGoods/{id}/{mainQuantity:.*}")
	@ResponseBody
	public void saveOnChangeGoods(@PathVariable("id") Long id, @PathVariable("mainQuantity")Double mainQuantity){
		InStock outStock = inStockService.get(id);
		outStock.setNumbers(mainQuantity);
		inStockService.update(outStock);
	}
	
}
