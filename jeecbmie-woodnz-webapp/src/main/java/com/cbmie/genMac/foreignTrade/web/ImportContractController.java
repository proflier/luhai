package com.cbmie.genMac.foreignTrade.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.activiti.engine.ActivitiException;
import org.apache.commons.beanutils.BeanUtils;
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
import com.cbmie.genMac.foreignTrade.entity.AgreementGoods;
import com.cbmie.genMac.foreignTrade.entity.Commodity;
import com.cbmie.genMac.foreignTrade.entity.ImportContract;
import com.cbmie.genMac.foreignTrade.service.AgencyAgreementService;
import com.cbmie.genMac.foreignTrade.service.CommodityService;
import com.cbmie.genMac.foreignTrade.service.ImportContractService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 进口合同_基本信息controller
 */
@Controller
@RequestMapping("foreignTrade/importContract")
public class ImportContractController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ImportContractService importContractService;

	@Autowired
	private CommodityService commodityService;

	@Autowired
	private AgencyAgreementService agencyAgreementService;
	
	@Autowired
	protected ActivitiService activitiService;

	@Autowired
	private  HttpServletRequest request;
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "importContract/importContractList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> importContractList(HttpServletRequest request) {
		Page<ImportContract> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = importContractService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 根据id获取合同号
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "contractNOAjax/{id}")
	@ResponseBody
	public String contractNOAjax(@PathVariable("id") String id) {
		if(StringUtils.isNotEmpty(id)&&!id.equals("null")){
			ImportContract importContract = importContractService.get(Long.valueOf(id));
			return importContract.getContractNO();
		}else{
			return "";
		}
		
	}
	
	/**
	 * 根据id获取客户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "supplierAjax/{id}")
	@ResponseBody
	public String supplierAjax(@PathVariable("id") String id) {
		if(StringUtils.isNotEmpty(id)&&!id.equals("null")){
			ImportContract importContract = importContractService.get(Long.valueOf(id));
			return importContract.getSupplier();
		}else{
			return "";
		}
		
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("importContract", new ImportContract());
		model.addAttribute("action", "create");
		return "importContract/importContractForm";
	}

	/**
	 * 添加进口合同
	 * 
	 * @param importContract
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid ImportContract importContract, Model model,
			@RequestParam("commodityChildJson") String commodityChildJson) {
		try {
			if (importContractService.findByNo(importContract) != null) {
				return "进口合同号重复！";
			}
			User currentUser = UserUtil.getCurrentUser();
			importContract.setSummary("进口合同[" + importContract.getContractNO() + "]");
			importContract.setCreaterNo(currentUser.getLoginName());
			importContract.setCreaterName(currentUser.getName());
			importContract.setCreateDate(new Date());
			importContract.setCreaterDept(currentUser.getOrganization().getOrgName());
			importContractService.save(importContract);
			if(commodityChildJson.length()>2){
				commodityService.save(importContract, commodityChildJson);
			}
			if (Boolean.valueOf(request.getParameter("apply"))) {
				apply(importContract.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 修改进口合同跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		ImportContract importContract = new ImportContract();
		importContract = importContractService.get(id);
		model.addAttribute("importContract", importContract);
		model.addAttribute("agencyAgreement", agencyAgreementService.get(importContract.getAgencyAgreementId()));
		model.addAttribute("action", "update");
		return "importContract/importContractForm";
	}

	/**
	 * 修改进口合同
	 * 
	 * @param importContract
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ImportContract importContract, Model model,
			@RequestParam("commodityChildJson") String commodityChildJson) {
			if (importContractService.findByNo(importContract) != null) {
				return "进口合同号重复！";
			}
			User currentUser = UserUtil.getCurrentUser();
			importContract.setSummary("进口合同[" + importContract.getContractNO() + "]");
			importContract.setUpdaterNo(currentUser.getLoginName());
			importContract.setUpdaterName(currentUser.getName());
			importContract.setUpdateDate(new Date());
			importContractService.update(importContract);
			if(commodityChildJson.length()>2){
				commodityService.save(importContract, commodityChildJson);
			}
			if (Boolean.valueOf(request.getParameter("apply"))) {
				apply(importContract.getId());
			}
		return "success";
	}

	/**
	 * 删除进口合同
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		importContractService.delete(id);
		return "success";
	}

	/**
	 * 进口合同明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		ImportContract importContract = new ImportContract();
		importContract = importContractService.get(id);
		model.addAttribute("importContract", importContract);
		model.addAttribute("agencyAgreement", agencyAgreementService.get(importContract.getAgencyAgreementId()));
		model.addAttribute("action", "detail");
		return "importContract/importContracteDetail";
	}

	/**
	 * 进口合同协议配置跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAgencyConfig", method = RequestMethod.GET)
	public String toAgencyConfig() {
		// model.addAttribute("importContract", importContractService.get(id));
		// model.addAttribute("action", "update");
		return "importContract/agencyConfig";
	}

	/**
	 * 进口合同协议配置
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "agencyConfig/{id}", method = RequestMethod.POST)
	public String agencyConfig(@PathVariable("id") Long id, Model model) throws JsonProcessingException, IllegalAccessException, InvocationTargetException {
		// importContractService.setAgencyConfig(id,importContractId);
		model.addAttribute("agencyAgreement", agencyAgreementService.get(id));
		ObjectMapper objectMapper = new ObjectMapper();
		List<AgreementGoods> agreementGoodsList = new ArrayList<AgreementGoods>();
		List<Commodity> commodityList = new ArrayList<Commodity>();
		Commodity commodity = null;
		agreementGoodsList = agencyAgreementService.get(id).getAgreementGoods();
		for(AgreementGoods agreementGoods:agreementGoodsList){
			commodity = new Commodity();
			BeanUtils.copyProperties(commodity, agreementGoods);
			commodity.setId(null);
			commodityList.add(commodity);
		}
		if(commodityList.size()>0){
			String gcJson = objectMapper.writeValueAsString(commodityList);
			model.addAttribute("gcJson", gcJson);
		}else{
			model.addAttribute("gcJson", null);
		}
		
		return "importContract/agencyConfigInfo";
	}
	
	/**
	 * 找出满足到单登记的进口合同
	 */
	@RequestMapping(value = "getInvoiceRegImportContract", method = RequestMethod.GET)
	@ResponseBody
	public List<ImportContract> getInvoiceRegImportContract(){
		return importContractService.findInvoiceReg();
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
			ImportContract importContract = importContractService.get(id);
//			importContract.setUserId((user.getLoginName()).toString());
			importContract.setState("已提交");
			importContractService.save(importContract);
			Map<String, Object> variables = new HashMap<String, Object>();
			//variables.put("contractNO", importContract.getContractNO());// 传递开始参数
			activitiService.startWorkflow(importContract, "wf_contract", variables, (user.getLoginName()).toString());

			return "success";
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
				ImportContract importContract = importContractService.get(id);
				importContract.setProcessInstanceId(null);
				importContract.setState("草稿");
				importContractService.save(importContract);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}

	@ModelAttribute
	public void getImportContract(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("importContract", importContractService.get(id));
		}
	}

}
