package com.cbmie.lh.credit.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.credit.service.PayApplyService;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.purchase.service.PurchaseContractService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.GlobalParam;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 开征申请controller
 */
@Controller
@RequestMapping("credit/payApply")
public class PayApplyController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PayApplyService payApplyService;

	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private PurchaseContractService purchaseContractService;
	
	@Autowired
	private BaseInfoUtilService bius;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@Autowired
	private BaseInfoUtilService baseInfoUtilService;

	@Autowired
	private AccessoryService accessoryService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "credit/payApplyList";
	}
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "jsonNoPermission", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getListNoPermission(HttpServletRequest request) {
		Page<PayApply> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = payApplyService.searchNoPermission(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<PayApply> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		payApplyService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = payApplyService.search(page, filters);
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
		PayApply payApply = new PayApply();
		String payApplyNo = sequenceCodeService.getNextCode("002001").get("returnStr");
		payApply.setPayApplyNo(payApplyNo);
		payApply.setCreateDate(new Date());
		payApply.setUpdateDate(new Date());
		model.addAttribute("payApply", payApply);
		model.addAttribute("action", "create");
		return "credit/payApplyForm";
	}

	/**
	 * 添加
	 * 
	 * @param payApply
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PayApply payApply, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		String  currnetSequence = null;
		if (payApplyService.findByNo(payApply) != null) {
			String payApplyNo = sequenceCodeService.getNextCode("002001").get("returnStr");
			payApply.setPayApplyNo(payApplyNo);
			currnetSequence = payApplyNo;
		}
		User currentUser = UserUtil.getCurrentUser();
		payApply.setCreaterNo(currentUser.getLoginName());
		payApply.setCreaterName(currentUser.getName());
		payApply.setCreaterDept(currentUser.getOrganization().getOrgName());
		payApply.setCreateDate(new Date());
		String summary = "[开证申请][" + baseInfoUtilService.getAffiBaseInfoByCode(payApply.getSupplier())+"][开证金额"+payApply.getApplyMoney()+DictUtils.getDictLabelByCode(payApply.getCurrency(), "currency", "")+"]";
		payApply.setSummary(summary);
		//调用获取下个sequence方法，将当前sequence保存
		if(payApply.getId()==null){
			sequenceCodeService.getNextSequence("002001");
		}
		payApplyService.save(payApply);
		return setReturnData("success", currnetSequence!=null?"开证申请号重复，已自动生成不重复编码并保存":"保存成功", payApply.getId(),currnetSequence);
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
		model.addAttribute("payApply", payApplyService.get(id));
		model.addAttribute("action", "update");
		return "credit/payApplyForm";
	}

	/**
	 * 修改
	 * 
	 * @param payApply
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PayApply payApply, Model model)
			throws JsonProcessingException {
		if (payApplyService.findByNo(payApply) != null) {
			return setReturnData("fail", "开证申请号重复！", payApply.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		payApply.setUpdaterNo(currentUser.getLoginName());
		payApply.setUpdaterName(currentUser.getName());
		payApply.setCreaterDept(currentUser.getOrganization().getOrgName());
		payApply.setUpdateDate(new Date());
		String summary = "[开证申请][" + baseInfoUtilService.getAffiBaseInfoByCode(payApply.getSupplier())+"][开证金额"+payApply.getApplyMoney()+DictUtils.getDictLabelByCode(payApply.getCurrency(), "currency", "")+"]";
		payApply.setSummary(summary);
		payApplyService.update(payApply);
		return setReturnData("success", "保存成功", payApply.getId());
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		PayApply payApply = payApplyService.get(id);
		if(payApply.getPid()!=null){
			PayApply payApplyParent = payApplyService.get(payApply.getPid());
			payApplyParent.setChangeState("1");
			payApplyService.update(payApplyParent);
		}
		payApplyService.delete(id);
		return "success";
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
		model.addAttribute("payApply", payApplyService.get(id));
		return "credit/payApplyDetail";
	}

	@ModelAttribute
	public void getPayApply(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("payApply", payApplyService.get(id));
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
		Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			User user = UserUtil.getCurrentUser();
			PayApply payApply = payApplyService.get(id);
			// payApply.setUserId((user.getLoginName()).toString());
			payApply.setState(ActivitiConstant.ACTIVITI_COMMIT);
			String comtractNo = payApply.getContractNo();
			PurchaseContract purchaseContract = purchaseContractService.findByContractNo(comtractNo);
			String summary = "[开证申请][" + baseInfoUtilService.getAffiBaseInfoByCode(payApply.getSupplier())+"][开证金额"+payApply.getApplyMoney()+DictUtils.getDictLabelByCode(payApply.getCurrency(), "currency", "")+"]";
			payApply.setSummary(summary);
			payApplyService.save(payApply);
			Map<String, Object> variables = new HashMap<String, Object>();
			// 采购合同 内贸对应编码：10620001  外贸对应编码：10620002 以此为依据 判读是国际信用证还是国内信用证
			variables.put("creditType", purchaseContract.getContractCategory());
			variables.put("deptId", payApply.getDeptId());
			//获取流程标识
			String processKey = activitiService.getCurrentProcessKey(PayApply.class,payApply);
			if(processKey!=null){
				result = activitiService.startWorkflow(payApply, processKey, variables,
						(user.getLoginName()).toString());
			}else{
				return "流程未部署、不存在或存在多个，请联系管理员！";
			}
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 撤回流程申请
	 * 
	 * @return
	 */
	@RequestMapping(value = "callBack/{id}/{processInstanceId}")
	@ResponseBody
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId,
			HttpSession session) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId)) {
				PayApply payApply = payApplyService.get(id);
				payApply.setProcessInstanceId(null);
				payApply.setState(ActivitiConstant.ACTIVITI_DRAFT);
				payApplyService.save(payApply);
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
	 * 开证申请变更跳转
	 * @param id
	 * @param model
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "change/{id}", method = RequestMethod.GET)
	public String changeForm(@PathVariable("id") Long id, Model model) throws IllegalAccessException, InvocationTargetException {
		PayApply payApplySource = payApplyService.getNoLoad(id);
		PayApply payApply = copyProperties(payApplySource);
		model.addAttribute("payApply", payApply);
		model.addAttribute("action", "update");
		return "credit/payApplyForm";
	}

	private PayApply copyProperties(PayApply payApplySource) throws IllegalAccessException, InvocationTargetException {
		PayApply payApplyDest = new PayApply();
		BeanUtils.copyProperties(payApplyDest, payApplySource);
		payApplyDest.setId(null);
		payApplyDest.setSourceId(payApplySource.getSourceId()==null?payApplySource.getId():payApplySource.getSourceId());
		payApplyDest.setPid(payApplySource.getId());
		payApplyDest.setChangeState("2");
		payApplyDest.setProcessInstanceId(null);
		payApplyDest.setState(ActivitiConstant.ACTIVITI_DRAFT);
		payApplyDest.setChangeReason(null);
		//系统信息
		User currentUser = UserUtil.getCurrentUser();
		payApplyDest.setCreaterNo(currentUser.getLoginName());
		payApplyDest.setCreaterName(currentUser.getName());
		payApplyDest.setCreaterDept(currentUser.getOrganization().getOrgName());
		payApplyDest.setCreateDate(new Date());
		payApplyDest.setUpdateDate(new Date());
		payApplySource.setChangeState("0");
		payApplyService.update(payApplySource);
		payApplyService.save(payApplyDest);
		//更新附件信息
		List<Accessory> accessoryList = accessoryService.getListByPidAndEntityForWirte(payApplySource.getId().toString(),"com_cbmie_lh_credit_entity_PayApply");
		accessoryService.copyAttach(accessoryList,payApplyDest.getId()+"",null);
		return payApplyDest; 
	}

	/**
	 * 采购合同号配置跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "toContract", method = RequestMethod.GET)
	public String toAgencyConfig() {
		return "credit/payApplyContract";
	}

}
