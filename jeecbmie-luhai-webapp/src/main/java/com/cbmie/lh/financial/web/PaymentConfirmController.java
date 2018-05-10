package com.cbmie.lh.financial.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.JavaToPdfHtml;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.financial.entity.PaymentConfirmChild;
import com.cbmie.lh.financial.service.PaymentConfirmService;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 付款申请
 * @author admin
 *
 */
@Controller
@RequestMapping("financial/paymentConfirm")
public class PaymentConfirmController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PaymentConfirmService confirmService;
	// @Autowired
	// private HttpServletRequest request;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private SequenceCodeService sequenceCodeService;
	@Autowired
	private BaseInfoUtilService baseInfoUtilService;
	
	@Autowired
	private UserService userService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/paymentConfirmList";
	}

	/**
	 * 获取付款确认单json
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goodsList(HttpServletRequest request) {
		Page<PaymentConfirm> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		confirmService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = confirmService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加
	 * 
	 * @param model
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		PaymentConfirm paymentConfirm = new PaymentConfirm();
		User currentUser = UserUtil.getCurrentUser();
		paymentConfirm.setCreaterNo(currentUser.getLoginName());
		paymentConfirm.setCreaterName(currentUser.getName());
		paymentConfirm.setCreaterDept(currentUser.getOrganization().getOrgName());
		paymentConfirm.setCreateDate(new Date());
		paymentConfirm.setUpdateDate(new Date());
		String confirmNo = sequenceCodeService.getNextCode("001013").get("returnStr");
		paymentConfirm.setConfirmNo(confirmNo);
		model.addAttribute("paymentConfirm", paymentConfirm);
		model.addAttribute("action", "create");
		return "financial/paymentConfirmForm";
	}

	/**
	 * 添加
	 * 
	 * @param woodCghtJk
	 * @param model
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid PaymentConfirm paymentConfirm, Model model) throws JsonProcessingException {
		try {
			String  currnetSequence = null;
			if (confirmService.findByConfirmNo(paymentConfirm) != null) {
				String confirmNo = sequenceCodeService.getNextCode("001013").get("returnStr");
				paymentConfirm.setConfirmNo(confirmNo);
				currnetSequence = confirmNo;
			}
			User currentUser = UserUtil.getCurrentUser();
			paymentConfirm.setCreaterNo(currentUser.getLoginName());
			paymentConfirm.setCreaterName(currentUser.getName());
			paymentConfirm.setCreateDate(new Date());
			String receiveUnitName =  baseInfoUtilService.getAffiBaseInfoByCode(paymentConfirm.getReceiveUnitName());//收款单位
			String paymentContent = DictUtils.getDictLabelByCode(paymentConfirm.getPayContent(), "paymentContent", "");//付款内容
			String currency = DictUtils.getDictLabelByCode(paymentConfirm.getCurrency(), "currency", "");//币种
			paymentConfirm.setSummary("[付款申请][付："+receiveUnitName+"]["+paymentContent+paymentConfirm.getPayMoneyXiao().longValue()+currency+"]");
			paymentConfirm.setCreaterDept(currentUser.getOrganization().getOrgName());
			//调用获取下个sequence方法，将当前sequence保存
			if(paymentConfirm.getId()==null){
				sequenceCodeService.getNextSequence("001013");
			}
			confirmService.save(paymentConfirm);
			confirmService.saveBusinessPermission(paymentConfirm);
			return setReturnData("success", currnetSequence!=null?"付款单号重复，已自动生成不重复编码并保存":"保存成功", paymentConfirm.getId(),currnetSequence);
		} catch (Exception e) {
			e.printStackTrace();
			return setReturnData("fail", "保存失败", paymentConfirm.getId());
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
		User user = UserUtil.getCurrentUser();
		PaymentConfirm paymentConfirm = confirmService.get(id);
		if(!(paymentConfirm.getPaymentConfirmChildList().size()>0)){
			return "分摊金额为空，不能提交申请！";
		}
		if(!(paymentConfirm.getPayMoneyXiao()!=null&&paymentConfirm.getPayMoneyXiao()>0)){
			return "付款金额为0不能提交申请！";
		}
		BigDecimal totalMoney=BigDecimal.valueOf(0.0);
		for(PaymentConfirmChild paymentConfirmChild : paymentConfirm.getPaymentConfirmChildList()){
			BigDecimal cur = BigDecimal.valueOf(paymentConfirmChild.getShareAmount());
			totalMoney = totalMoney.add(cur);
		}
		if(totalMoney.doubleValue()!=paymentConfirm.getPayMoneyXiao()){
			return "付款金额为与分摊金额不符！";
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("payMoneyXiao", paymentConfirm.getPayMoneyXiao() == null ? 0 : paymentConfirm.getPayMoneyXiao());
		variables.put("goodsClasses", paymentConfirm.getGoodsClasses());
		variables.put("payMoneyXiao", paymentConfirm.getPayMoneyXiao());
		
		if(StringUtils.isNotBlank(paymentConfirm.getBusinessManager())){
			User businessManager = userService.getUserByLoginName(paymentConfirm.getBusinessManager());
			variables.put("businessDeptId", businessManager.getOrganization().getId());
		}else{
			return "业务经办人不能为空！";
		}
		variables.put("contractCategory", paymentConfirm.getContractCategory());
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(PaymentConfirm.class,paymentConfirm);
		if(processKey!=null){
			result = activitiService.startWorkflow(paymentConfirm, processKey, variables,
					(user.getLoginName()).toString());
		}else{
			return "流程未部署、不存在或存在多个，请联系管理员！";
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 修改
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("paymentConfirm", confirmService.get(id));
		model.addAttribute("action", "update");
		return "financial/paymentConfirmForm";
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateById/{id}/{payContent}", method = RequestMethod.GET)
	@ResponseBody
	public String updateById(@PathVariable("id") Long id,@PathVariable("payContent") String payContent, Model model) {
		PaymentConfirm paymentConfirm = confirmService.get(id);
		paymentConfirm.setPayContent(payContent);
		confirmService.update(paymentConfirm);
		confirmService.saveBusinessPermission(paymentConfirm);
		return "success";
	}
	
	/**
	 * 付款类型 修改 流程中
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateReDo/{id}", method = RequestMethod.GET)
	public String updateForm2(@PathVariable("id") Long id, Model model) {
		model.addAttribute("paymentConfirm", confirmService.get(id));
		model.addAttribute("action", "update");
		return "financial/paymentConfirmFormReDo";
	}

	/**
	 * 修改
	 * 
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody PaymentConfirm paymentConfirm, Model model)
			throws JsonProcessingException {
		if (confirmService.findByConfirmNo(paymentConfirm) != null) {
			return setReturnData("fail", "付款单号重复", paymentConfirm.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		paymentConfirm.setUpdaterNo(currentUser.getLoginName());
		paymentConfirm.setUpdaterName(currentUser.getName());
		paymentConfirm.setUpdateDate(new Date());
		String receiveUnitName =  baseInfoUtilService.getAffiBaseInfoByCode(paymentConfirm.getReceiveUnitName());//收款单位
		String paymentContent = DictUtils.getDictLabelByCode(paymentConfirm.getPayContent(), "paymentContent", "");//付款内容
		String currency = DictUtils.getDictLabelByCode(paymentConfirm.getCurrency(), "currency", "");//币种
		paymentConfirm.setSummary("[付款申请][付："+receiveUnitName+"]["+paymentContent+paymentConfirm.getPayMoneyXiao().longValue()+currency+"]");
		confirmService.update(paymentConfirm);
		confirmService.saveBusinessPermission(paymentConfirm);
		return setReturnData("success", "保存成功", paymentConfirm.getId());
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws Exception {
		confirmService.delete(id);
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
		model.addAttribute("paymentConfirm", confirmService.get(id));
		return "financial/paymentConfirmDetail";
		
	}
	
	/**
	 * 打印
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "print/{id}", method = RequestMethod.GET)
	public String print(@PathVariable("id") Long id, Model model) {
		model.addAttribute("paymentConfirm", confirmService.get(id));
		return "financial/paymentConfirmPrint";
		
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPdf/{id}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		PaymentConfirm pc = confirmService.getNoLoad(id);
		Map<String, Object> data = new HashMap<String, Object>();
		confirmService.exportPdf(data, pc);
		if (pc.getProcessInstanceId() != null) {
			data.put("traceList", activitiService.getTraceInfo(pc.getProcessInstanceId()));
		}
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "paymentConfirm.html", "MSYH.TTF", "style.css");
		jtph.entrance(data, response, StringUtils.replaceEach(pc.getContractNo(), new String[]{" ", ","}, new String[]{"-", ""}) + ".pdf");
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
				PaymentConfirm paymentConfirm = confirmService.get(id);
				paymentConfirm.setProcessInstanceId(null);
				paymentConfirm.setState(ActivitiConstant.ACTIVITI_DRAFT);
				confirmService.save(paymentConfirm);
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
	 * 采购合同号配置跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "toContract", method = RequestMethod.GET)
	public String toAgencyConfig() {
		return "financial/paymentConfirmContract";
	}
	
	@RequestMapping(value = "getListNoYH", method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBaseInfo> getListNoYH() {
		return confirmService.getListNoYH();
	}

	@ModelAttribute
	public void getPaymentConfirm(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("paymentConfirm", confirmService.get(id));
		}
	}

}
