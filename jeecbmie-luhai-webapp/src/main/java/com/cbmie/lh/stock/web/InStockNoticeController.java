package com.cbmie.lh.stock.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
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

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.JavaToPdfHtml;
import com.cbmie.common.web.BaseController;
import com.cbmie.lh.stock.entity.InStockNotice;
import com.cbmie.lh.stock.service.InStockNoticeService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 入库通知controller
 */
@Controller
@RequestMapping("stock/inStockNotice")
public class InStockNoticeController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private InStockNoticeService inStockNoticeService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	@Autowired
	private ActivitiService activitiService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "stock/inStockNoticeList";
	}

	/**
	 * 获取入库json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> inStockList(HttpServletRequest request) {
		Page<InStockNotice> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		inStockNoticeService.setCurrentPermission(filters, "LIKES_relLoginNames_OR_createrNo");
		page = inStockNoticeService.search(page, filters);
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
		InStockNotice inStockNotice = new InStockNotice();
		inStockNotice.setCreateDate(new Date());
		inStockNotice.setUpdateDate(new Date());
		String noticeNo = sequenceCodeService.getNextCode("001019").get("returnStr");
		inStockNotice.setNoticeNo(noticeNo);
		model.addAttribute("inStockNotice", inStockNotice);
		model.addAttribute("action", "create");
		return "stock/inStockNoticeForm";
	}

	
	/**
	 * 添加入库
	 * 
	 * @param inStockNotice
	 * @param model
	 * @throws JsonProcessingException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid InStockNotice inStockNotice, Model model) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		if (inStockNoticeService.findByInner(inStockNotice.getInnerContractNo()) != null) {
			return setReturnData("fail", "同一提单重复入库！", null);
		}
		String  currnetSequence = null;
		if (inStockNoticeService.findByNo(inStockNotice) != null) {
			String noticeNo = sequenceCodeService.getNextCode("001019").get("returnStr");
			inStockNotice.setNoticeNo(noticeNo);
			currnetSequence = noticeNo;
		}
		User currentUser = UserUtil.getCurrentUser();
		if(inStockNotice.getCreaterNo()==null){
			//调用获取下个sequence方法，将当前sequence保存
			sequenceCodeService.getNextSequence("001019");
		}
		inStockNotice.setCreaterNo(currentUser.getLoginName());
		inStockNotice.setCreaterName(currentUser.getName());
		inStockNotice.setCreateDate(new Date());
		inStockNotice.setCreaterDept(currentUser.getOrganization().getOrgName());
		inStockNoticeService.save(inStockNotice);
		return setReturnData("success", currnetSequence!=null?"入库通知号重复，已自动生成不重复编码并保存":"保存成功", inStockNotice.getId(),currnetSequence);
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
		model.addAttribute("inStockNotice", inStockNoticeService.get(id));
		model.addAttribute("action", "update");
		return "stock/inStockNoticeForm";
	}

	/**
	 * 修改入库
	 * 
	 * @param inStockNotice
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody InStockNotice inStockNotice, Model model)
			throws JsonProcessingException {
//		if (inStockService.findByNo(inStock) != null) {
//			return setReturnData("fail", "入库号重复！", inStock.getId());
//		}
//		if (inStockNoticeService.findByBillId(inStockNotice) != null) {
//			return setReturnData("fail", "同一提单重复入库！", inStockNotice.getId());
//		}
		User currentUser = UserUtil.getCurrentUser();
		inStockNotice.setUpdaterNo(currentUser.getLoginName());
		inStockNotice.setUpdaterName(currentUser.getName());
		inStockNotice.setUpdateDate(new Date());
		inStockNoticeService.update(inStockNotice);
		return setReturnData("success", "保存成功", inStockNotice.getId());
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
		inStockNoticeService.delete(id);
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
		model.addAttribute("inStockNotice", inStockNoticeService.get(id));
		return "stock/inStockNoticeDetail";
	}


	@ModelAttribute
	public void getInStock(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("inStock", inStockNoticeService.get(id));
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
		User user = UserUtil.getCurrentUser();
		InStockNotice inStockNotice = inStockNoticeService.get(id);
		if(!(inStockNotice.getInStockNoticeGoodsList().size()>0)){
			return "采购详情为空，不能提交申请！";
		}
		inStockNotice.setState("3");
		String summary = "[入库通知][供货单位"+inStockNotice.getDeliveryUnitView()+"][入库单："+inStockNotice.getNoticeNo()+"]";
		inStockNotice.setSummary(summary);
		inStockNoticeService.save(inStockNotice);
		Map<String, Object> variables = new HashMap<String, Object>();
//		variables.put("businessManager", inStockNotice.getBusinessManager());
		//获取流程标识
		String processKey = activitiService.getCurrentProcessKey(InStockNotice.class,inStockNotice);
		if(processKey!=null){
			result = activitiService.startWorkflow(inStockNotice, processKey, variables,
					(user.getLoginName()).toString());
		}else{
			return "流程未部署、不存在或存在多个，请联系管理员！";
		}
		try {
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
				InStockNotice inStockNotice = inStockNoticeService.get(id);
				inStockNotice.setProcessInstanceId(null);
				if(inStockNotice.getPid()!=null){
					//设置状态  变更中
					inStockNotice.setChangeState("2");
				}
				//设置状态  草稿
				inStockNotice.setState("2");
				inStockNoticeService.save(inStockNotice);
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
	 * 采购合同配置跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadPurchase", method = RequestMethod.GET)
	public String loadBillId() {
		return "stock/loadPurchase";
	}
	
	/**
	 * pdf导出
	 */
	@RequestMapping(value = "exportPDF/{innerContractNo}", method = RequestMethod.GET)
	public void exportPdf(@PathVariable("innerContractNo") String innerContractNo, HttpServletRequest request, HttpServletResponse response) {
		JavaToPdfHtml jtph = new JavaToPdfHtml(request, "inStockNotice.html", "MSYH.TTF", "style.css");
		Map<String, Object> exportMap = inStockNoticeService.exportPDF(innerContractNo);
		jtph.entrance(exportMap, response);
	}
	
}
