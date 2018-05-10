package com.cbmie.lh.permission.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.web.BaseController;
import com.cbmie.lh.permission.entity.BusinessPerssion;
import com.cbmie.lh.permission.service.BusinessPerssionService;
import com.cbmie.system.entity.CustomerPerssion;
import com.cbmie.system.service.CustomerPerssionService;
import com.cbmie.system.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 客户相关用户权限controller
 */
@Controller
@RequestMapping("permission/businessPerssion")
public class BusinessPerssionController extends BaseController{
	
	@Autowired
	private BusinessPerssionService businessPerssionService;
	
	@Autowired
	private CustomerPerssionService customerPerssionService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private HttpServletRequest request;
	
	
	/**
	 * 跳转选择用户
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "businessSelectUsers", method = RequestMethod.GET)
	public String businessSelectUsers() {
		return "permission/businessSelectUsers";
	}
	
	/**
	 * 保存
	 * @param businessPerssion
	 * @param model
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "saveUserRealtion/{businessId}/{businessCode}/{businessFlag}", method = RequestMethod.POST)
	@ResponseBody
	public String update(HttpServletRequest request,@PathVariable("businessId") String businessId,@PathVariable("businessCode") String businessCode,@PathVariable("businessFlag") String businessFlag)
			throws JsonProcessingException {
		String themeMemberIds = request.getParameter("themeMemberIds");
		String themeMemberKeyIds = request.getParameter("themeMemberKeyIds");
		List<BusinessPerssion> businessPerssions = businessPerssionService.findByBusinessCode(businessId,businessCode);
		if(businessPerssions.size()>0){
			for(BusinessPerssion businessPerssion:businessPerssions){
				businessPerssionService.delete(businessPerssion);
			}
		}
		String[] userIds = themeMemberIds.split(",");
		if(userIds.length>0){
			for(String userId: userIds){
				BusinessPerssion businessPerssion = new BusinessPerssion();
				businessPerssion.setBusinessCode(businessCode);
				businessPerssion.setLoginId(Integer.valueOf(userId));
				businessPerssion.setBusinessId(businessId);
				businessPerssion.setBusinessFlag(businessFlag);
				businessPerssionService.save(businessPerssion);
			}
			businessPerssionService.getEntityDao().getSession().flush();
		}
		
  		return "success";
	}
	
	/**
	 *  获取相关人员名称
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "getUserNamesString/{relLoginNames}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getUserNamesString(@PathVariable("relLoginNames") String relLoginNames) {
		return businessPerssionService.getUserNamesString(relLoginNames);
	}
	
	/**
	 *  更新采购相关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4Purchase/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updateBusinessPermission(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4Purchase(id,themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新销售相关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4Sale/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4Sale(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4Sale(id,themeMemberIds);
		 return "success";
	}
	
	
	/**
	 *  更新汽运关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4HighwayContract/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4HighwayContract(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4HighwayContract(id, themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新入库相关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4InStock/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4InStock(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4InStock(id, themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新保险合同相关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4InsuranceContract/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4InsuranceContract(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4InsuranceContract(id, themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新订船合同相关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4OrderShipContract/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4OrderShipContract(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4OrderShipContract(id, themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新汽运铁运结算相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4HightOrRail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4HightOrRail(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4HightOrRail(id, themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新出库相关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4OutStock/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4OutStock(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4OutStock(id, themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新码头合同相关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4PortContract/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4PortContract(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4PortContract(id, themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新销售相关业务模块相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4RailwayContract/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4RailwayContract(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4RailwayContract(id, themeMemberIds);
		 return "success";
	}
	
	/**
	 *  更新船舶动态相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePermission4ShipTrace/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4ShipTrace(@PathVariable("id") long id) {
		 String themeMemberIds = request.getParameter("themeMemberIds");
		 businessPerssionService.updatePermission4ShipTrace(id, themeMemberIds);
		 return "success";
	}
	
	
	/**
	 *  更新船舶动态相关人员信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "updatePaymentByCode/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String updatePermission4ShipTrace(@PathVariable("id") Long id) {
		CustomerPerssion customerPerssion = customerPerssionService.get(id);
		String users = customerPerssionService.getUserLoginsByCode(customerPerssion.getCustomerCode());
		businessPerssionService.updatePaymentConfirm(customerPerssion.getCustomerCode(), users);
		 return "success";
	}
	
	
	/**
	 * 更新用户相关采购
	 * @param loginName
	 * @param ids 采购ids
	 * @return
	 */
	@RequestMapping(value = "saveRealtion4Purchase/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String saveRealtion4Purchase( @PathVariable("loginName") String loginName, @RequestParam(value="ids",required=false)String[] ids ){
		businessPerssionService.saveRealtion4Purchase(loginName,ids);
		return "success";
	}
	
	/**
	 * 更新用户相关销售
	 * @param loginName
	 * @param ids 销售ids
	 * @return
	 */
	@RequestMapping(value = "saveRealtion4Sale/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String saveRealtion4Sale( @PathVariable("loginName") String loginName, @RequestParam(value="ids",required=false)String[] ids ){
		businessPerssionService.saveRealtion4Sale(loginName,ids);
		return "success";
	}
	
	/**
	 * 更新用户相关订船
	 * @param loginName
	 * @param ids 订船合同ids
	 * @return
	 */
	@RequestMapping(value = "saveRealtion4OrderShip/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String saveRealtion4OrderShip( @PathVariable("loginName") String loginName, @RequestParam(value="ids",required=false)String[] ids ){
		businessPerssionService.saveRealtion4OrderShip(loginName,ids);
		return "success";
	}
	
	/**
	 * 更新用户相关保险
	 * @param loginName
	 * @param ids 保险合同ids
	 * @return
	 */
	@RequestMapping(value = "saveRealtion4Insurance/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String saveRealtion4Insurance( @PathVariable("loginName") String loginName, @RequestParam(value="ids",required=false)String[] ids ){
		businessPerssionService.saveRealtion4Insurance(loginName,ids);
		return "success";
	}
	
	/**
	 * 更新用户相关汽运
	 * @param loginName
	 * @param ids 汽运合同ids
	 * @return
	 */
	@RequestMapping(value = "saveRealtion4Highway/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String saveRealtion4Highway( @PathVariable("loginName") String loginName, @RequestParam(value="ids",required=false)String[] ids ){
		businessPerssionService.saveRealtion4Highway(loginName,ids);
		return "success";
	}
	
	/**
	 * 更新用户相关铁运
	 * @param loginName
	 * @param ids 铁运合同ids
	 * @return
	 */
	@RequestMapping(value = "saveRealtion4Railway/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String saveRealtion4Railway( @PathVariable("loginName") String loginName, @RequestParam(value="ids",required=false)String[] ids ){
		businessPerssionService.saveRealtion4Railway(loginName,ids);
		return "success";
	}
	
	/**
	 * 更新用户相关码头
	 * @param loginName
	 * @param ids 汽运合同ids
	 * @return
	 */
	@RequestMapping(value = "saveRealtion4Port/{loginName}", method = RequestMethod.GET)
	@ResponseBody
	public String saveRealtion4Port( @PathVariable("loginName") String loginName, @RequestParam(value="ids",required=false)String[] ids ){
		businessPerssionService.saveRealtion4Port(loginName,ids);
		return "success";
	}
	
	
	
	
	@ModelAttribute
	public void getCustomerPerssion(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("businessPerssion", businessPerssionService.get(id));
		}
	}
}
