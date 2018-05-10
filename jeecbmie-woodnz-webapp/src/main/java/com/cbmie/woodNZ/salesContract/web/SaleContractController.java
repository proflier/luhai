package com.cbmie.woodNZ.salesContract.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.ActivitiException;
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
import com.cbmie.woodNZ.baseInfo.entity.WoodGoodsCode;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContractSub;
import com.cbmie.woodNZ.salesContract.service.SaleContractService;
import com.cbmie.woodNZ.salesContract.service.SaleContractSubService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 销售controller
 */
@Controller
@RequestMapping("sale/saleContract")
public class SaleContractController extends BaseController{
	
	
	@Autowired
	private SaleContractService saleContractService;
	
	@Autowired
	private SaleContractSubService saleContractSubService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "saleContract/saleContractList";
	}
	 
	/**
	 * 获取json
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> outList(HttpServletRequest request) throws JsonProcessingException {
		Page<WoodSaleContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = saleContractService.search(page, filters);
		List<WoodSaleContractSub> saleContractSubList = new ArrayList<WoodSaleContractSub>();
		List<WoodSaleContract> woodSaleContractList = saleContractService.search(filters);
		if(woodSaleContractList.size()>0){
			saleContractSubList = woodSaleContractList.get(0).getSaleContractSubList();
			if(saleContractSubList.size()>0){
				ObjectMapper objectMapper = new ObjectMapper();
				String testJson = objectMapper.writeValueAsString(saleContractSubList);
				page.getResult().get(0).setReturnJson(testJson); 
			}
		}
		return getEasyUIData(page);
	}
	
	/**
	 * 添加销售跳转
	 * 
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		WoodSaleContract info = new WoodSaleContract();
		User currentUser = UserUtil.getCurrentUser();
		info.setCreaterNo(currentUser.getLoginName());
		info.setCreaterName(currentUser.getName());
		info.setCreaterDept(currentUser.getOrganization().getOrgName());
		model.addAttribute("woodSaleContract", info);
		model.addAttribute("action", "create");
		return "saleContract/saleContractForm";
	}

	/**
	 * 添加销售
	 * 
	 * @param port
	 * @param model
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WoodSaleContract saleContract, Model model,
			@RequestParam("contractSubJson") String contractSubJson) throws ParseException, JsonProcessingException {
		boolean result = saleContractService.validateContractNo(saleContract.getContractNo(),saleContract.getId());//合同号重复校验
		if(result==true){
			return setReturnData("fail","保存失败，合同号"+saleContract.getContractNo()+"重复！",null);
		}
		saleContract.setCreateDate(new Date());
		saleContract.setSummary("销售合同-[" + saleContract.getContractNo() + "]");
		saleContractService.save(saleContract);
		if(StringUtils.isNotBlank(contractSubJson)){
			saleContractSubService.save(saleContract, contractSubJson);
		}
//		if (Boolean.valueOf(request.getParameter("apply"))) {
//			apply(saleContract.getId());
//		}
		return setReturnData("success","保存成功",saleContract.getId());
	}

	/**
	 * 修改销售跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("woodSaleContract", saleContractService.get(id));
		model.addAttribute("action", "update");
		return "saleContract/saleContractForm";
	}

	/**
	 * 修改销售
	 * 
	 * @param woodSaleContract
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WoodSaleContract woodSaleContract,Model model,
		@RequestParam("contractSubJson") String contractSubJson) throws JsonProcessingException {
		boolean result = saleContractService.validateContractNo(woodSaleContract.getContractNo(),woodSaleContract.getId());//合同号重复校验
		if(result==true){
			return setReturnData("fail","保存失败，合同号"+woodSaleContract.getContractNo()+"重复！",null);
		}
		User currentUser = UserUtil.getCurrentUser();
		woodSaleContract.setUpdaterNo(currentUser.getLoginName());
		woodSaleContract.setUpdaterName(currentUser.getName());
		woodSaleContract.setUpdateDate(new Date());
		woodSaleContract.setSummary("销售合同-[" + woodSaleContract.getContractNo() + "]");
		saleContractService.update(woodSaleContract);
		if(StringUtils.isNotBlank(contractSubJson)){
			saleContractSubService.save(woodSaleContract, contractSubJson);
		}
//		if (Boolean.valueOf(request.getParameter("apply"))) {
//			apply(saleContract.getId());
//		}
		return setReturnData("success","保存成功",woodSaleContract.getId());
	}

	/**
	 * 删除销售
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		saleContractService.delete(id);
		return "success";
	}
	
	/**
	 * 销售明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleContract", saleContractService.get(id));
		model.addAttribute("action", "detail");
		return "saleContract/saleContractDetail";
	}
	
	
	@ModelAttribute
	public void setWoodSaleContract(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("woodSaleContract", saleContractService.get(id));
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
		try {
			User user = UserUtil.getCurrentUser();
			WoodSaleContract woodSaleContract = saleContractService.get(id);
//			woodSaleContract.setUserId((user.getLoginName()).toString());
			woodSaleContract.setState("已提交");
			saleContractService.save(woodSaleContract);
			Map<String, Object> variables = new HashMap<String, Object>();
			Map<String,Object> result = new HashMap<String,Object>();
			result = activitiService.startWorkflow(woodSaleContract, "wf_woodSaleContract", variables, (user.getLoginName()).toString());
//			return "success";
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(result);
		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				logger.warn("没有部署流程!", e);
				return "no deployment";
			} else {
				logger.error("启动流程失败：", e);
				return "start fail";
			}
		} catch (Exception e) {
			logger.error("启动流程失败：", e);
			return "start fail";
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
				WoodSaleContract woodSaleContract = saleContractService.get(id);
				woodSaleContract.setProcessInstanceId(null);
				woodSaleContract.setState("草稿");
				saleContractService.save(woodSaleContract);
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
	 * 修改销售合同放货状态跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateState/{id}", method = RequestMethod.GET)
	public String updateStateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("saleContract", saleContractService.get(id));
		model.addAttribute("action", "updateState");
		return "saleContract/saleContractStateUpdate";
	}
	
	/**
	 * 修改销售合同放货状态
	 * 
	 * @param port
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateState", method = RequestMethod.POST)
	@ResponseBody
	public String updateState(@RequestParam(value = "id", defaultValue = "-1") Long id,
						@RequestParam(value = "state_ht", defaultValue = "-1") String state,Model model) {
		try {
			WoodSaleContract contract= saleContractService.get(id);
			contract.setState_ht(state);
			saleContractService.update(contract);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 查看商品编码跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ckbm", method = RequestMethod.GET)
	public String searchGoodsBM(Model model) {
		model.addAttribute("code", new WoodGoodsCode());
		return "saleContract/searchGoodsBmForm";
	}
	
}


