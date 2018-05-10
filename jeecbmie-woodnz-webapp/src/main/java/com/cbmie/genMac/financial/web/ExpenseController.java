package com.cbmie.genMac.financial.web;

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
import com.cbmie.common.web.BaseController;
import com.cbmie.genMac.financial.entity.Expense;
import com.cbmie.genMac.financial.service.ExpenseService;
import com.cbmie.genMac.financial.service.PaymentService;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.UserUtil;

/**
 * 费用controller
 */
@Controller
@RequestMapping("financial/expense")
public class ExpenseController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	protected ActivitiService activitiService;
	
	@Autowired
	private  HttpServletRequest request;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "financial/expenseList";
	}

	/**
	 * 获取json
	 */
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> expenseList(HttpServletRequest request) {
		Page<Expense> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = expenseService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("expense", new Expense());
		model.addAttribute("action", "create");
		return "financial/expenseForm";
	}

	/**
	 * 添加费用
	 * 
	 * @param importContract
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Expense expense, Model model,@RequestParam("paymentChildJson") String paymentChildJson) {
		if (expenseService.findByNo(expense) != null) {
			return "付款申请号！";
		}
		User currentUser = UserUtil.getCurrentUser();
		expense.setSummary("付款申请号[" + expense.getExpenseId() + "]");
		expense.setCreaterNo(currentUser.getLoginName());
		expense.setCreaterName(currentUser.getName());
		expense.setCreateDate(new Date());
		expense.setCreaterDept(currentUser.getOrganization().getOrgName());
		expenseService.save(expense);
		if(paymentChildJson.length()>2){
			paymentService.save(expense, paymentChildJson);
		}
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(expense.getId());
		}
		return "success";
	}

	/**
	 * 修改费用跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		Expense expense = new Expense();
		expense = expenseService.get(id);
		model.addAttribute("expense", expense);
		model.addAttribute("action", "update");
		return "financial/expenseForm";
	}

	/**
	 * 修改费用
	 * 
	 * @param expense
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody Expense expense, Model model,@RequestParam("paymentChildJson") String paymentChildJson) {
		if (expenseService.findByNo(expense) != null) {
			return "付款申请号！";
		}
		User currentUser = UserUtil.getCurrentUser();
		expense.setSummary("付款申请号[" + expense.getExpenseId() + "]");
		expense.setUpdaterNo(currentUser.getLoginName());
		expense.setUpdaterName(currentUser.getName());
		expense.setUpdateDate(new Date());
		expenseService.update(expense);
		if(paymentChildJson.length()>2){
			paymentService.save(expense, paymentChildJson);
		}
		if (Boolean.valueOf(request.getParameter("apply"))) {
			apply(expense.getId());
		}
		return "success";
	}

	/**
	 * 删除费用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		expenseService.delete(id);
		return "success";
	}

	/**
	 * 费用明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		Expense expense = new Expense();
		expense = expenseService.get(id);
		model.addAttribute("expense", expense);
		model.addAttribute("action", "detail");
		return "financial/expenseDetail";
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
			Expense expense = expenseService.get(id);
//			expense.setUserId((user.getLoginName()).toString());
			expense.setState("已提交");
			expenseService.save(expense);
			Map<String, Object> variables = new HashMap<String, Object>();
			activitiService.startWorkflow(expense, "wf_expense", variables, (user.getLoginName()).toString());

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
				Expense expense  = expenseService.get(id);
				expense.setProcessInstanceId(null);
				expense.setState("草稿");
				expenseService.save(expense);
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
	public void getExpense(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("expense", expenseService.get(id));
		}
	}

}
