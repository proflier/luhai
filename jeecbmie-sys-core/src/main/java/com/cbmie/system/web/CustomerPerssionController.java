package com.cbmie.system.web;

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

import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.baseinfo.service.AffiBaseInfoService;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.CustomerPerssion;
import com.cbmie.system.service.CustomerPerssionService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 客户相关用户权限controller
 */
@Controller
@RequestMapping("system/customerPerssion")
public class CustomerPerssionController extends BaseController{
	
	@Autowired
	private CustomerPerssionService customerPerssionService;
	
	@Autowired
	private AffiBaseInfoService affiBaseInfoService;
	
	@Autowired
	private HttpServletRequest request;
	/**
	 * 跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "userRealtion", method = RequestMethod.GET)
	public String userRealtion() {
		return "baseinfo/userRealtion";
	}
	
	/**
	 *  获取相关业务员名称
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "initSelect/{customerCode}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> initSelect(@PathVariable("customerCode") String customerCode) {
		return customerPerssionService.initSelect(customerCode);
	}
	
	/**
	 * 客户相关用户权限
	 * @param customerPerssion
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "saveUserRealtion/{customerCode}", method = RequestMethod.POST)
	@ResponseBody
	public String saveUserRealtion(HttpServletRequest request,@PathVariable("customerCode") String customerCode){
		String themeMemberIds = request.getParameter("themeMemberIds");
		String themeMemberKeyIds = request.getParameter("themeMemberKeyIds");
		CustomerPerssion customerPerssion = null;
		customerPerssion = customerPerssionService.findByCus(customerCode);
		if(customerPerssion!=null){
		}else{
			customerPerssion = new CustomerPerssion();
			customerPerssion.setCustomerCode(customerCode);
		}
		customerPerssion.setBusinessManager(themeMemberKeyIds);
		customerPerssion.setRanges(themeMemberIds);
		customerPerssionService.save(customerPerssion);
		customerPerssionService.getEntityDao().getSession().flush();
  		return "success";
	}
	
	/**
	 *  获取相关业务员名称
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "getBusinessManager/{customerCode}", method = RequestMethod.GET)
	@ResponseBody
	public String getBusinessManager(@PathVariable("customerCode") String customerCode) {
		return customerPerssionService.getBusinessManager(customerCode);
	}
	
	/**
	 *  获取相关人员名称
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "getRanges/{customerCode}", method = RequestMethod.GET)
	@ResponseBody
	public String getRanges(@PathVariable("customerCode") String customerCode) {
		return customerPerssionService.getRanges(customerCode);
	}
	
	/**
	 *  获取相关人员账号
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "getUserLoginsByCode/{customerCode}", method = RequestMethod.GET)
	@ResponseBody
	public String getUserLoginsByCode(@PathVariable("customerCode") String customerCode) {
		return customerPerssionService.getUserLoginsByCode(customerCode);
	}
	
	@RequestMapping(value = "saveRealtion/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String saveRealtion( @PathVariable("loginName") String loginName, @RequestParam(value="customerCodes",required=false)String[] customerCodes ){
		customerPerssionService.saveByUser(loginName,customerCodes);
		return "success";
	}
	
	@ModelAttribute
	public void getCustomerPerssion(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("customerPerssion", customerPerssionService.get(id));
		}
	}
}
