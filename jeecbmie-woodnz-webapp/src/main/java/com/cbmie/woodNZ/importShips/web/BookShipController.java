package com.cbmie.woodNZ.importShips.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;
import com.cbmie.woodNZ.importShips.entity.BookShip;
import com.cbmie.woodNZ.importShips.service.BookShipService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 定船合同审批controller
 * @author linxiaopeng
 * 2016年7月5日
 */
@Controller
@RequestMapping("importShips/bookShip")
public class BookShipController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BookShipService bookShipService;
	
	
	@Autowired
	private ActivitiService activitiService;
	
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "importShips/bookShipList";
	}
	
	
	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request) {
		Page<BookShip> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = bookShipService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("bookShip", new BookShip());
		model.addAttribute("action", "create");
		return "importShips/bookShipform";
	}

	/**
	 * 添加
	 * 
	 * @param bookShip
	 * @param model
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid BookShip bookShip, Model model) throws JsonProcessingException {
		if (bookShipService.findByNo(bookShip) != null) {
			return setReturnData("fail","合同编号重复！",bookShip.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		bookShip.setCreaterNo(currentUser.getLoginName());
		bookShip.setCreaterName(currentUser.getName());
		bookShip.setCreaterDept(currentUser.getOrganization().getOrgName());
		bookShip.setCreateDate(new Date());
		bookShip.setSummary("订船合同申请[" + bookShip.getContractNo() + "]");
		bookShipService.save(bookShip);
		return setReturnData("success","保存成功",bookShip.getId());
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
		model.addAttribute("bookShip", bookShipService.get(id));
		model.addAttribute("action", "update");
		return "importShips/bookShipform";
	}

	/**
	 * 修改
	 * 
	 * @param bookShip
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BookShip bookShip, Model model) throws JsonProcessingException {
		if (bookShipService.findByNo(bookShip) != null) {
			return setReturnData("fail","合同编号重复",bookShip.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		bookShip.setUpdaterNo(currentUser.getLoginName());
		bookShip.setUpdaterName(currentUser.getName());
		bookShip.setCreaterDept(currentUser.getOrganization().getOrgName());
		bookShip.setUpdateDate(new Date());
		bookShip.setSummary("[订船合同申请" + bookShip.getContractNo() + "]");
		bookShipService.update(bookShip);
		return setReturnData("success","保存成功",bookShip.getId());
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
		bookShipService.delete(id);
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
		model.addAttribute("bookShip", bookShipService.get(id));
		return "importShips/bookShipDetail";
	}
	
	@ModelAttribute
	public void getBookShip(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("bookShip", bookShipService.get(id));
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
		Map<String,Object> result = new HashMap<String,Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			User user = UserUtil.getCurrentUser();
			BookShip bookShip = bookShipService.get(id);
//			bookShip.setUserId((user.getLoginName()).toString());
			bookShip.setState("已提交");
			bookShipService.save(bookShip);
			Map<String, Object> variables = new HashMap<String, Object>();
			result = activitiService.startWorkflow(bookShip, "wf_bookShip", variables, (user.getLoginName()).toString());
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
				BookShip bookShip = bookShipService.get(id);
				bookShip.setProcessInstanceId(null);
				bookShip.setState("草稿");
				bookShipService.save(bookShip);
				return "success";
			} else {
				return "已被签收，不能撤回！";
			}
		} catch (Exception e) {
			logger.error("撤回申请失败：", e);
			return "撤回申请失败！";
		}
	}
	
	
	
	
}
