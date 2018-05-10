package com.cbmie.lh.logistic.web;

import java.text.ParseException;
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
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.JavaToPdfHtml;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.logistic.entity.WharfSettlement;
import com.cbmie.lh.logistic.service.WharfSettlementService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Controller
@RequestMapping(value="logistic/wharf")
public class WharfSettlementController extends BaseController {

	@Autowired
	private WharfSettlementService wharfService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private BaseInfoUtilService bius;
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "logistic/wharfList";
	}
	
	/**
	 * @throws Exception 
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> entityList(HttpServletRequest request) {
		Page<WharfSettlement> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		wharfService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = wharfService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * @param model
	 * @throws ParseException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) throws ParseException {
		WharfSettlement wharfSettlement = new WharfSettlement();
		User currentUser = UserUtil.getCurrentUser();
		wharfSettlement.setCreaterNo(currentUser.getLoginName());
		wharfSettlement.setCreaterName(currentUser.getName());
		wharfSettlement.setCreaterDept(currentUser.getOrganization().getOrgName());
		wharfSettlement.setCreateDate(new Date());
		model.addAttribute("wharfSettlement", wharfSettlement);
		model.addAttribute("action", "create");
		return "logistic/wharfForm";
	}
	
	/**
	 * 添加
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WharfSettlement wharfSettlement) throws JsonProcessingException {
			wharfSettlement.setSummary("[码头结算][" + bius.getAffiBaseInfoByCode(wharfSettlement.getWharf()) + "]" +  (wharfSettlement.getShouldPay() == null ? "" : ("[金额" + wharfSettlement.getShouldPay().longValue() + "元]")));
			wharfService.save(wharfSettlement);
			return setReturnData("success","保存成功",wharfSettlement.getId());
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
		model.addAttribute("wharfSettlement", wharfService.get(id));
		model.addAttribute("action", "update");
		return "logistic/wharfForm";
	}
	
	/**
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WharfSettlement wharfSettlement) throws JsonProcessingException {
		User currentUser = UserUtil.getCurrentUser();
		wharfSettlement.setUpdaterNo(currentUser.getLoginName());
		wharfSettlement.setUpdaterName(currentUser.getName());
		wharfSettlement.setUpdateDate(new Date());
		wharfSettlement.setSummary("[码头结算][" + bius.getAffiBaseInfoByCode(wharfSettlement.getWharf()) + "]" +  (wharfSettlement.getShouldPay() == null ? "" : ("[金额" + wharfSettlement.getShouldPay().longValue() + "元]")));
		wharfService.update(wharfSettlement);
		return setReturnData("success","保存成功",wharfSettlement.getId());
	}
	
	@ModelAttribute
	public void getWharfSettlement(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("wharfSettlement", wharfService.get(id));
		}
	}
	
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model) {
		model.addAttribute("wharfSettlement", wharfService.get(id));
		model.addAttribute("action", "view");
		return "logistic/wharfDetail";
	}
	
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) throws JsonProcessingException {
		wharfService.delete(id);
		return setReturnData("success","删除成功",id);
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
		Map<String,Object> result = new HashMap<String,Object>();
		User user = UserUtil.getCurrentUser();
		WharfSettlement wharfSettlement = wharfService.get(id);
		wharfSettlement.setSummary("[码头结算][" + bius.getAffiBaseInfoByCode(wharfSettlement.getWharf()) + "]" +  (wharfSettlement.getShouldPay() == null ? "" : ("[金额" + wharfSettlement.getShouldPay().longValue() + "元]")));
		wharfService.update(wharfSettlement);
		Map<String, Object> variables = new HashMap<String, Object>();
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(WharfSettlement.class,wharfSettlement);
		if(processKey!=null){
			result = activitiService.startWorkflow(wharfSettlement, processKey, variables,
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
	 * 撤回流程申请
	 * @return
	 */
	@RequestMapping(value = "callBack/{id}/{processInstanceId}")
	@ResponseBody
	public String callBack(@PathVariable("id") Long id, @PathVariable("processInstanceId") String processInstanceId, HttpSession session) {
		try {
			if (activitiService.deleteProcessInstance(processInstanceId)) {
				WharfSettlement wharfSettlement = wharfService.get(id);
				wharfSettlement.setProcessInstanceId(null);
				wharfSettlement.setState(ActivitiConstant.ACTIVITI_DRAFT);
				wharfService.update(wharfSettlement);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	@RequestMapping(value = "toPortContractSelect")
	public String toPortContractSelect(){
		return "logistic/portContractSelect";
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPDF/{id}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "wharfSettlement.html", "MSYH.TTF", "style.css");
		Map<String, Object> exportMap = wharfService.exportPDF(id);
		jtph.entrance(exportMap, response);
	}
}
