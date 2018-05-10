package com.cbmie.lh.stock.web;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import com.cbmie.lh.sale.service.SaleDeliveryGoodsService;
import com.cbmie.lh.stock.entity.OutStock;
import com.cbmie.lh.stock.entity.OutStockDetail;
import com.cbmie.lh.stock.service.OutStockDetailService;
import com.cbmie.lh.stock.service.OutStockService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 出库controller
 */
@Controller
@RequestMapping("stock/outStock")
public class OutStockController extends BaseController {

	@Autowired
	private OutStockService outStockService;
	
	@Autowired
	private OutStockDetailService outStockDetailService;
	
	@Autowired
	private SaleDeliveryGoodsService saleDeliveryGoodsService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/outStockList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<OutStock> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
//		//获取当前可见客户
//		Session session =SecurityUtils.getSubject().getSession();
//		String customerCode = (String) session.getAttribute("customerCode");
//		//根据某一字段设置权限
//		outStockService.setCurrentPermission(filters, "INQ_receiptCode", customerCode);
		outStockService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = outStockService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加出库跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		OutStock outStock = new OutStock();
		outStock.setCreateDate(new Date());
		outStock.setUpdateDate(new Date());
		String outStockNo = sequenceCodeService.getNextCode("001020").get("returnStr");
		outStock.setOutStockNo(outStockNo);
		model.addAttribute("outStock", outStock);
		model.addAttribute("action", "create");
		return "stock/outStockForm";
	}

	/**
	 * 添加出库
	 * 
	 * @param outStock
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid OutStock outStock, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (outStockService.findByNo(outStock) != null) {
			String outStockNo = sequenceCodeService.getNextCode("001020").get("returnStr");
			outStock.setOutStockNo(outStockNo);
			currnetSequence = outStockNo;
		}
		User currentUser = UserUtil.getCurrentUser();
		//调用获取下个sequence方法，将当前sequence保存
		if(outStock.getCreaterNo()==null){
			sequenceCodeService.getNextSequence("001020");	
		}
		outStock.setCreaterNo(currentUser.getLoginName());
		outStock.setCreaterName(currentUser.getName());
		outStock.setCreateDate(new Date());
		outStock.setCreaterDept(currentUser.getOrganization().getOrgName());
		outStockService.save(outStock);
		return setReturnData("success", currnetSequence!=null?"出库号重复，已自动生成不重复编码并保存":"保存成功", outStock.getId(),currnetSequence);
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
		model.addAttribute("outStock", outStockService.get(id));
		model.addAttribute("action", "update");
		return "stock/outStockForm";
	}

	/**
	 * 修改出库
	 * 
	 * @param outStock
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OutStock outStock, Model model)
			throws JsonProcessingException {
		if (outStockService.findByNo(outStock) != null) {
			return setReturnData("fail", "出库号重复！", outStock.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		outStock.setUpdaterNo(currentUser.getLoginName());
		outStock.setUpdaterName(currentUser.getName());
		outStock.setUpdateDate(new Date());
		outStockService.update(outStock);
		return setReturnData("success", "保存成功", outStock.getId());
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
		outStockService.delete(id);
		return "success";
	}

	/**
	 * 查看出库明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("outStock", outStockService.get(id));
		model.addAttribute("action", "view");
		return "stock/outStockForm";
	}
	
	/**
	 * 默认页面
	 */
	@RequestMapping(value = "outStockDetailList",method = RequestMethod.GET)
	public String outStockDetailList() {
		return "stock/outStockDetailList";
	}
	
	@ResponseBody
	@RequestMapping(value = "readExcel", method = RequestMethod.POST)
	public String readExcel( @RequestParam("file") MultipartFile file)   {
			try {
				outStockDetailService.importExcel(file);
				return "success";
			} catch (IOException e) {
				e.printStackTrace();
				return "error";
			}
			
	}
	
	@RequestMapping(value = "outStockDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> outStockDetail(HttpServletRequest request) {
		Page<OutStockDetail> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
//		PropertyFilter filter = new PropertyFilter("EQS_outStockId", id);
//		filters.add(filter);
		page = outStockDetailService.search(page, filters);
		return getEasyUIData(page);
	}

	@ModelAttribute
	public void getOutStock(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("outStock", outStockService.get(id));
		}
	}

	/**
	 * 放货弹窗
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadDelivery", method = RequestMethod.GET)
	public String loadBillId() {
		return "stock/loadDelivery";
	}
	
	
	/**
	 * 保存子表
	 * @param billNo
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "saveGoods/{deliveryNo}/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public String saveGoods( @PathVariable("deliveryNo")String deliveryNo,@PathVariable("parentId")String parentId){
		try {
			saleDeliveryGoodsService.saveGood(deliveryNo,parentId);
			OutStock outStock = outStockService.get(Long.valueOf(parentId));
			outStock.setDeliveryNo(deliveryNo);
			outStockService.update(outStock);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return "success";
	} 
	
	 /**
		 * 确认出库
		 *
		 * @param id
		 * @return
		 * @throws JsonProcessingException 
		 */
		 @RequestMapping(value = "confirm/{id}")
		 @ResponseBody
		 public String confirm(@PathVariable("id") Long id) throws JsonProcessingException {
			 Map<String, Object> returnMap = outStockService.checkGoods(id);
			 if((boolean) returnMap.get("returnValue")){
				 OutStock outStock = outStockService.get(id);
				 outStock.setConfirm("1");
				 outStock.setDetermineTime(new Date());
				 User currentUser = UserUtil.getCurrentUser();
				 outStock.setDetermineName(currentUser.getName());
				 outStockService.save(outStock);
				 return setReturnData("success", "保存成功", outStock.getId());
			 }else{
				 return setReturnData("fail", (String) returnMap.get("msg"), id);
			 }
		 }
		
		 /**
		 * 取消确认出库
		 *
		 * @param id
		 * @return
		 */
		 @RequestMapping(value = "cancleConfirm/{id}")
		 @ResponseBody
		 public String cancleConfirm(@PathVariable("id") Long id) {
			 OutStock outStock = outStockService.get(id);
			 outStock.setConfirm("0");
			 outStock.setDetermineTime(null);
			 outStock.setDetermineName(null);
			 outStockService.save(outStock);
			 return "success";
		 }
		 
		 
		 /**
		  * 放货跟踪跳转
		  * @return
		  */
		 @RequestMapping(value = "stockTrack",method = RequestMethod.GET)
			public String stockTrack() {
				return "stock/stockTrackList";
			}

		 /**
		 * 获取放货跟踪json
		 */
		@RequestMapping(value = "stockTrackJson", method = RequestMethod.GET)
		@ResponseBody
		public List<Map<String, Object>> stockTrack(HttpServletRequest request) {
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			data = outStockService.stockTrack(showParams(request));
			return data;
		}
		
		/**
		 * 根据request获取过滤参数
		 * 
		 * @param request
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		private Map<String, Object> showParams(HttpServletRequest request) {
			Map<String, Object> map = new HashMap<String, Object>();
			Enumeration paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();

				String[] paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						map.put(paramName, paramValue);
					}
				}
			}
			Set<Map.Entry<String, Object>> set = map.entrySet();
			for (Map.Entry entry : set) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			return map;
		}
		
		@RequestMapping(value = "saveOnChangeGoods/{id}/{mainQuantity:.*}")
		@ResponseBody
		public void saveOnChangeGoods(@PathVariable("id") Long id, @PathVariable("mainQuantity")Double mainQuantity){
			OutStock outStock = outStockService.get(id);
			outStock.setRealQuantity(mainQuantity);
			outStockService.update(outStock);
		}
}
