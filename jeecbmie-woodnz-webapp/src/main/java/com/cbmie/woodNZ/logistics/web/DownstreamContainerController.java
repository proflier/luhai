package com.cbmie.woodNZ.logistics.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
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
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.logistics.entity.DownstreamContainer;
import com.cbmie.woodNZ.logistics.entity.WoodBillsSub;
import com.cbmie.woodNZ.logistics.service.BillsService;
import com.cbmie.woodNZ.logistics.service.BillsSubService;
import com.cbmie.woodNZ.logistics.service.DownstreamContainerService;
import com.cbmie.woodNZ.stock.service.InStockService;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * 箱单controller
 */
@Controller
@RequestMapping("logistics/downstreamContainer")
public class DownstreamContainerController extends BaseController {


	@Autowired
	private DownstreamContainerService downstreamContainerService;	
	
	@Autowired
	private BillsSubService billsSubService;
	
	@Autowired
	private BillsService billsService;
	
	@Autowired
	private InStockService inStockService;
	
	

//	/**
//	 * 获取json
//	 */
//	@RequestMapping(value = "getContainerList/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public List<DownstreamContainer> getContainerList(Long id) {
//		return downstreamContainerService.getByParentId(id);
//	}
	
	/** 
	 * 获取json
	 */
	@RequestMapping(value = "getContainerList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getContainerList(HttpServletRequest request,@PathVariable("id") String id) {
		Page<DownstreamContainer> page = getPage(request);
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		PropertyFilter filter = new PropertyFilter("EQS_parentId", id);
		filterList.add(filter);
		page  =  downstreamContainerService.search(page, filterList);
		return getEasyUIData(page);
	}

	/**
	 * 添加箱单跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create/{id}", method = RequestMethod.GET)
	public String createForm(Model model,@PathVariable("id") String id) {
		DownstreamContainer downstreamContainer = new DownstreamContainer();
		downstreamContainer.setParentId(id);
		model.addAttribute("downstreamContainer",downstreamContainer);
		model.addAttribute("actionContainer", "create");
		return "logistics/downstreamContainerForm";
	}

	/**
	 * 添加箱单
	 * 
	 * @param downstreamContainer
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid DownstreamContainer downstreamContainer, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		downstreamContainer.setCreaterNo(currentUser.getLoginName());
		downstreamContainer.setCreaterName(currentUser.getName());
		downstreamContainer.setCreateDate(new Date());
		downstreamContainer.setCreaterDept(currentUser.getOrganization().getOrgName());
		downstreamContainerService.save(downstreamContainer);
		return setReturnData("success","保存成功",downstreamContainer.getId());
	}

	/**
	 * 修改箱单跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("downstreamContainer", downstreamContainerService.get(id));
		model.addAttribute("actionContainer", "update");
		return "logistics/downstreamContainerForm";
	}

	/**
	 * 修改箱单
	 * 
	 * @param downstreamContainer
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody DownstreamContainer downstreamContainer, Model model) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		downstreamContainer.setUpdaterNo(currentUser.getLoginName());
		downstreamContainer.setUpdaterName(currentUser.getName());
		downstreamContainer.setUpdateDate(new Date());
		downstreamContainerService.update(downstreamContainer);
		return setReturnData("success","保存成功",downstreamContainer.getId());
	}
	
	/**
	 * 删除箱单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		downstreamContainerService.delete(id);
		return "success";
	}
	
	/**
	 * 设置箱单子表
	 * @param sourceId 到单主表id
	 * @param parentId 下游交单箱单对应主表id
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "setContainerList/{sourceId}/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public String setContainerList(@PathVariable("sourceId") Long sourceId,@PathVariable("parentId") String parentId) throws IllegalAccessException, InvocationTargetException {
		List<DownstreamContainer> downstreamContainerList = new ArrayList<DownstreamContainer>();
		List<WoodBillsSub> woodBillsSubList = new ArrayList<WoodBillsSub>();
		woodBillsSubList = billsSubService.getByParentId(sourceId);
		String billNo = billsService.get(sourceId).getBillNo();
		String warehouse = String.valueOf(inStockService.findByBillId(billNo).getId());//获取箱单对应的入库主表id
		//设置箱单 对应入库主表id
		for(WoodBillsSub woodBillsSub:woodBillsSubList){
			DownstreamContainer downstreamContainer = new DownstreamContainer();
			BeanUtils.copyProperties(downstreamContainer, woodBillsSub);
			downstreamContainer.setWarehouse(warehouse);
			downstreamContainerList.add(downstreamContainer);
		}
		downstreamContainerService.deleteByParentId(parentId);
		downstreamContainerService.saveList(downstreamContainerList,parentId);
		return "success";
	}
	
	/**
	 * 根据综合合同号、商品编码  由商品表统一设置箱单表的单价 
	 * @param parentId
	 * @param cpContractNo
	 * @param spbm
	 * @param cgdj
	 * @return
	 */
	@RequestMapping(value = "setCGDJ/{parentId}/{cpContractNo}/{spbm}/{cgdj}", method = RequestMethod.GET)
	@ResponseBody
	public String setCGDJ(@PathVariable("parentId") String parentId,
							@PathVariable("cpContractNo") String cpContractNo,
							@PathVariable("spbm") String spbm,
							@PathVariable("cgdj") String cgdj) {
		List<DownstreamContainer> downstreamContainerList = new ArrayList<DownstreamContainer>();
		downstreamContainerList = downstreamContainerService.getByParentId(parentId);
		for(DownstreamContainer downstreamContainer:downstreamContainerList){
			downstreamContainerService.setCGDJ(downstreamContainer,cpContractNo,spbm,cgdj);
		}
		return "success";
	}
	
	
	@ModelAttribute
	public void getDownstreamContainer(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("downstreamContainer", downstreamContainerService.get(id));
		}
	}
	
	
}
