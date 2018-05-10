package com.cbmie.lh.stock.web;

import java.beans.IntrospectionException;
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

import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.stock.entity.StockAllocation;
import com.cbmie.lh.stock.service.StockAllocationService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 库存调拨controller
 */
@Controller
@RequestMapping("stock/stockAllocation")
public class StockAllocationController extends BaseController {

	@Autowired
	private StockAllocationService stockAllocationService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/stockAllocationList";
	}

	/**
	 * 获取库存调拨json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> stockAllocationList(HttpServletRequest request) {
		Page<StockAllocation> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		stockAllocationService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = stockAllocationService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加库存调拨跳转
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		StockAllocation stockAllocation = new StockAllocation();
		stockAllocation.setAllocationDate(new Date());
		stockAllocation.setCreateDate(new Date());
		stockAllocation.setUpdateDate(new Date());
		String allocationNo = sequenceCodeService.getNextCode("001021").get("returnStr");
		stockAllocation.setAllocationNo(allocationNo);
		model.addAttribute("stockAllocation",stockAllocation );
		model.addAttribute("action", "create");
		return "stock/stockAllocationForm";
	}

	/**
	 * 添加库存调拨
	 * 
	 * @param stockAllocation
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid StockAllocation stockAllocation, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (stockAllocationService.findByNo(stockAllocation) != null) {
			String allocationNo = sequenceCodeService.getNextCode("001021").get("returnStr");
			stockAllocation.setAllocationNo(allocationNo);
			currnetSequence = allocationNo;
		}
		User currentUser = UserUtil.getCurrentUser();
		stockAllocation.setCreaterNo(currentUser.getLoginName());
		stockAllocation.setCreaterName(currentUser.getName());
		stockAllocation.setCreateDate(new Date());
		stockAllocation.setUpdateDate(new Date());
		stockAllocation.setCreaterDept(currentUser.getOrganization().getOrgName());
		stockAllocation.setUserId(currentUser.getId().toString());
		stockAllocation.setDeptId(currentUser.getOrganization().getId());
		stockAllocation.setCompanyId(stockAllocationService.getCompany(currentUser.getOrganization()).getId());
		//调用获取下个sequence方法，将当前sequence保存
		if(stockAllocation.getId()==null){
			sequenceCodeService.getNextSequence("001021");
		}
		stockAllocationService.toSave(stockAllocation);
		return setReturnData("success", currnetSequence!=null?"库存调拨号重复，已自动生成不重复编码并保存":"保存成功", stockAllocation.getId(),currnetSequence);
	}

	/**
	 * 修改库存调拨跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("stockAllocation", stockAllocationService.get(id));
		model.addAttribute("action", "update");
		return "stock/stockAllocationForm";
	}

	/**
	 * 修改库存调拨
	 * 
	 * @param stockAllocation
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody StockAllocation stockAllocation, Model model)
			throws JsonProcessingException {
		if (stockAllocationService.findByNo(stockAllocation) != null) {
			return setReturnData("fail", "库存调拨号重复！", stockAllocation.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		stockAllocation.setUpdaterNo(currentUser.getLoginName());
		stockAllocation.setUpdaterName(currentUser.getName());
		stockAllocation.setUpdateDate(new Date());
		stockAllocationService.toSave(stockAllocation);
		return setReturnData("success", "保存成功", stockAllocation.getId());
	}
	
	/**
	 * 库存调拨-调入跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "inAlloaction/{id}", method = RequestMethod.GET)
	public String inAlloaction(@PathVariable("id") Long id, Model model) {
		model.addAttribute("stockAllocation", stockAllocationService.get(id));
		model.addAttribute("action", "inAlloaction");
		return "stock/stockInAllocation";
	}
	
	/**
	 * 库存调拨-调入
	 * 
	 * @param stockAllocation
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "inAlloaction", method = RequestMethod.POST)
	@ResponseBody
	public String inAlloaction(@Valid @ModelAttribute @RequestBody StockAllocation stockAllocation, Model model)
			throws JsonProcessingException {
		if (stockAllocationService.findByNo(stockAllocation) != null) {
			return setReturnData("fail", "库存调拨号重复！", stockAllocation.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		stockAllocation.setUpdaterNo(currentUser.getLoginName());
		stockAllocation.setUpdaterName(currentUser.getName());
		stockAllocation.setUpdateDate(new Date());
		stockAllocationService.inAllocation(stockAllocation);
		return setReturnData("success", "保存成功", stockAllocation.getId());
	}

	/**
	 * 删除库存调拨
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		stockAllocationService.delAllocation(stockAllocationService.get(id));
		return "success";
	}

	/**
	 * 查看库存调拨明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("stockAllocation", stockAllocationService.get(id));
		return "stock/stockAllocationDetail";
	}

	@ModelAttribute
	public void getStockAllocation(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("stockAllocation", stockAllocationService.get(id));
		}
	}

	/**
	 * 选择库存明细跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadStockGodds", method = RequestMethod.GET)
	public String loadBillId() {
		return "stock/loadStockGodds";
	}
	
	/**
	 * 获取内贸船编号
	 */
	@RequestMapping(value = "getInnnerShipTrace", method = RequestMethod.GET)
	@ResponseBody
	public List<ShipTrace> getInnnerShipTrace() {
		return stockAllocationService.getInnnerShipTrace();
	}

}
