package com.cbmie.baseinfo.web;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
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

import com.cbmie.baseinfo.entity.WoodAffiBankInfo;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.baseinfo.service.AffiBankInfoService;
import com.cbmie.baseinfo.service.AffiBaseInfoService;
import com.cbmie.baseinfo.service.AffiContactInfoService;
import com.cbmie.baseinfo.service.AffiCustomerInfoService;
import com.cbmie.baseinfo.service.SequenceCodeService;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;
import com.cbmie.system.utils.OrgUserUtils;
import com.cbmie.system.utils.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 关联单位controller
 */
@Controller
@RequestMapping("baseinfo/affiliates")
public class AffiController extends BaseController{
	
	@Autowired
	private AffiBankInfoService affiBankInfoService;
	
	@Autowired
	private AffiBaseInfoService affiBaseInfoService;
	
	@Autowired
	private AffiCustomerInfoService affiCustomerInfoService;
	
	@Autowired
	private AffiContactInfoService affiContactInfoService;
	
	@Autowired
	private SequenceCodeService sequenceCodeService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "baseinfo/affiBaseInfoList";
	}
	
	/**
	 * 获取json 关联单位基本信息
	 */
	@RequestMapping(value="json",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> affiBaseInfoList(HttpServletRequest request) {
		Page<WoodAffiBaseInfo> page = getPage(request);
		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		List<WoodAffiBaseInfo> dataList = affiBaseInfoService.search(filters);
		
		List<WoodAffiBaseInfo> list = new ArrayList<WoodAffiBaseInfo>();
		String customerType= request.getParameter("customerType");
		if (StringUtils.isNotBlank(customerType)) {
			List<String> qaramTypeList = Arrays.asList(StringUtils.split(customerType, ","));
			for (WoodAffiBaseInfo wab : dataList) {
				List<String> dataTypeList = Arrays.asList(StringUtils.split(wab.getCustomerType(), ","));
				if (dataTypeList.containsAll(qaramTypeList)) {
					list.add(wab);
				}
			}
		} else {
			list.addAll(dataList);
		}
		createPageByResult(page, list);
		return getEasyUIData(page);
	}
	@RequestMapping(value="jsonNew",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> listEntity(HttpServletRequest request){
		Page<WoodAffiBaseInfo> page = getPage(request);
		//构造过滤条件
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(request.getParameter("filter_LIKES_customerCode_OR_customerName"))){
			params.put("customerCodeOrcustomerName", request.getParameter("filter_LIKES_customerCode_OR_customerName"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("filter_customerType"))){
			params.put("customerType", request.getParameter("filter_customerType"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("filter_Hperson"))){
			params.put("Hperson", request.getParameter("filter_Hperson"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("filter_Rperson"))){
			params.put("Rperson", request.getParameter("filter_Rperson"));
		}
		//获取当前可见客户
		Session session =SecurityUtils.getSubject().getSession();
		String customerCode = (String) session.getAttribute("customerCode");
		User user = (User) session.getAttribute("user");
		if(user.getId()!=1){
			//除admin外设置当前用户的可见单位
			params.put("userName", user.getLoginName());
		}
		affiBaseInfoService.findEntityList(page, params);
		return getEasyUIData(page);
	}
	
	@RequestMapping(value="getOurUnitAndCustomer",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getOurUnitAndCustomer(HttpServletRequest request){
		//构造过滤条件
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(request.getParameter("customerName"))){
			params.put("customerName", request.getParameter("customerName"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("customerCode"))){
			params.put("customerCode", request.getParameter("customerCode"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("customerType"))){
			params.put("customerType", request.getParameter("customerType"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("Hperson"))){
			params.put("Hperson", request.getParameter("Hperson"));
		}
//		affiBaseInfoService.initHistory();
		return affiBaseInfoService.getOurUnitAndCustomer(params);
	}
	
	
	/**
	 * 添加关联单位跳转
	 * 
	 * @param model
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		try {
			WoodAffiBaseInfo info = new WoodAffiBaseInfo();
			User currentUser = UserUtil.getCurrentUser();
			info.setCreaterNo(currentUser.getLoginName());
			info.setCreaterName(currentUser.getName());
			info.setCreaterDept(currentUser.getOrganization().getOrgName());
			model.addAttribute("affiBaseInfo", info);
			String customerCode = sequenceCodeService.getNextCode("003001").get("returnStr");
			info.setCustomerCode(customerCode);
			model.addAttribute("action", "create");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "baseinfo/affiBaseInfoForm";
	}

	/**
	 * 添加关联单位
	 * 
	 * @param WoodAffiBaseInfo
	 * @param model
	 * @throws JsonProcessingException 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid WoodAffiBaseInfo affiBaseInfo, Model model, @RequestParam("affiBankJson") String affiBankJson, @RequestParam("affiCustomerJson") String affiCustomerJson,
			 @RequestParam("affiContactJson") String affiContactJson) throws JsonProcessingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
//		boolean change1 = !(customerCode.equals(affiBaseInfo.getCustomerCode()));//false 为客户编码没改过
		String  currnetSequence = null;
		if (affiBaseInfoService.findByNo(affiBaseInfo) != null) {
			String customerCode = sequenceCodeService.getNextCode("003001").get("returnStr");
			affiBaseInfo.setCustomerCode(customerCode);
			currnetSequence = customerCode;
		}
		if (affiBaseInfoService.findByCustomerName(affiBaseInfo) != null) {
			return setReturnData("fail","客户名称重复",affiBaseInfo.getId());
		}
		affiBaseInfo.setCreateDate(new Date());
		if(affiBaseInfo.getId()==null){
			//调用获取下个sequence方法，将当前sequence保存
			sequenceCodeService.getNextSequence("003001");
		}
		affiBaseInfoService.save(affiBaseInfo);
		//数据变更，清除缓存
		EhCacheUtils.remove(DictUtils.CACHE_CORP_MAP);
		if(StringUtils.isNotBlank(affiBankJson)){
			affiBankInfoService.save(affiBaseInfo, affiBankJson);
		}
		if(StringUtils.isNotBlank(affiCustomerJson)){
			affiCustomerInfoService.save(affiBaseInfo, affiCustomerJson);
		}
		if(StringUtils.isNotBlank(affiContactJson)){
			affiContactInfoService.save(affiBaseInfo, affiContactJson); 
		}
		return setReturnData("success", currnetSequence!=null?"客户编码重复，已自动生成不重复编码并保存":"保存成功", affiBaseInfo.getId(),currnetSequence);
	}

	/**
	 * 修改关联单位跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("affiBaseInfo", affiBaseInfoService.get(id));
		model.addAttribute("action", "update");
		return "baseinfo/affiBaseInfoForm";
	}

	/**
	 * 修改关联单位
	 * 
	 * @param port
	 * @param model
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody WoodAffiBaseInfo affiBaseInfo,Model model, @RequestParam("affiBankJson") String affiBankJson, @RequestParam("affiCustomerJson") String affiCustomerJson,
			 @RequestParam("affiContactJson") String affiContactJson) throws JsonProcessingException {
//		if(change1 && !affiBaseInfoService.validateCustomerNo(affiBaseInfo)){//校验客户编码重复
//			String code = affiBaseInfo.getCustomerCode();
//			affiBaseInfo.setCustomerCode((customerCode));
//			return setReturnData("fail","保存失败, 客户编码: "+code+" 已经存在！",affiBaseInfo.getId());
//		}
//		if (affiBaseInfoService.findByNo(affiBaseInfo) != null) {
//			return setReturnData("fail","客户编码重复",affiBaseInfo.getId());
//		}
		if (affiBaseInfoService.findByCustomerName(affiBaseInfo) != null) {
			return setReturnData("fail","客户名称重复",affiBaseInfo.getId());
		}
		User currentUser = UserUtil.getCurrentUser();
		affiBaseInfo.setUpdaterNo(currentUser.getLoginName());
		affiBaseInfo.setUpdaterName(currentUser.getName());
		affiBaseInfo.setUpdateDate(new Date());
		affiBaseInfoService.update(affiBaseInfo);
		EhCacheUtils.remove(DictUtils.CACHE_CORP_MAP);
		try {
			if(StringUtils.isNotBlank(affiBankJson)){
				affiBankInfoService.save(affiBaseInfo, affiBankJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(affiCustomerJson)){
			affiCustomerInfoService.save(affiBaseInfo, affiCustomerJson);
		}
		if(StringUtils.isNotBlank(affiContactJson)){
			affiContactInfoService.save(affiBaseInfo, affiContactJson); 
		}
		return setReturnData("success","保存成功",affiBaseInfo.getId());
	}

	/**
	 * 删除关联单位
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		affiBaseInfoService.delete(id);
		EhCacheUtils.remove(DictUtils.CACHE_CORP_MAP);
		return "success";
	}
	
	/**
	 * 关联单位明细跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	public String getDetail(@PathVariable("id") Long id, Model model) {
		try {
			model.addAttribute("affiBaseInfo", affiBaseInfoService.get(id));
			model.addAttribute("affiBankInfo", affiBankInfoService.getByParentId(Long.toString(id)));
			model.addAttribute("affiCustomerInfo", affiCustomerInfoService.getListByParentId(Long.toString(id)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("action", "detail");
		return "baseinfo/affiliatesDetail";
	}
	
	/**
	 * 获取我司单位
	 */
	@RequestMapping(value = "getSelfCompany")
	@ResponseBody
	public List<WoodAffiBaseInfo> getSelfCompany() {
		return affiBaseInfoService.getByCustomerType("1");
	}
	
	/**
	 * 获取供应商 供应商对应字典编码为2
	 */
	@RequestMapping(value = "getSupplier")
	@ResponseBody
	public List<WoodAffiBaseInfo> getSupplier() {
		return affiBaseInfoService.getByCustomerType("2");
	}
	
	/**
	 * 获取国内、国外客户
	 */
	@RequestMapping(value = "getCustomer")
	@ResponseBody
	public List<WoodAffiBaseInfo> getCustomer() {
		List<WoodAffiBaseInfo> returnList = new ArrayList<WoodAffiBaseInfo>();
		returnList.addAll(affiBaseInfoService.getByCustomerType("1"));
		returnList.addAll(affiBaseInfoService.getByCustomerType("2"));
		return returnList;
	}
	
	/**
	 * 获取报关单位
	 */
	@RequestMapping(value = "getCusDecCompany")
	@ResponseBody
	public List<WoodAffiBaseInfo> getCusDecCompany() {
		return affiBaseInfoService.getByCustomerType("6");
	}

	/**
	 * 根据父id获取银行信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "selectBank/{pid}")
	@ResponseBody
	public  List<WoodAffiBankInfo> selectBank(@PathVariable("pid") String pid) {
		List<WoodAffiBankInfo> affiBankInfoList = affiBankInfoService.getByParentId(pid);
		return affiBankInfoList;
	}
	
	/**
	 *  全部银行信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "selectBank")
	@ResponseBody
	public  List<WoodAffiBankInfo> selectBank() {
		List<WoodAffiBankInfo> affiBankInfoList = affiBankInfoService.getAll();
		return affiBankInfoList;
	}
	
	/**
	 *  获取银行信息
	 * @param bankId
	 * @return
	 */
	@RequestMapping(value = "getAffiBankInfoById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public WoodAffiBankInfo getAffiBankInfoById(@PathVariable("id") Long id) {
		WoodAffiBankInfo affiBankInfo = affiBankInfoService.getNoLoad(id);
		return affiBankInfo;
	}
	
	
	/**
	 *  根据银行账号获取英航名称
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getAffiBankInfoByNO/{banNO}", method = RequestMethod.GET)
	@ResponseBody
	public String  getAffiBankInfoByNO(@PathVariable("banNO") String banNO) {
		WoodAffiBankInfo affiBankInfo = affiBankInfoService.getByNo(banNO);
		return affiBankInfo.getBankName();
	}
	
	
	/**
	 * 根据id编号获取类型名称
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "typeAjax/{id}")
	@ResponseBody
	public String typeAjax(@PathVariable("id") String id) {
		WoodAffiBaseInfo affiBaseInfo = affiBaseInfoService.get(Long.valueOf(id));
		return affiBaseInfo.getCustomerName();
	}
	
	
	/**
	 * 根据id编号获取类型名称
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getCustomerEnName/{id}")
	@ResponseBody
	public String getCustomerEnName(@PathVariable("id") String id) {
		WoodAffiBaseInfo affiBaseInfo = affiBaseInfoService.get(Long.valueOf(id));
		return affiBaseInfo.getCustomerEnName();
	}
	
	@ModelAttribute
	public void getAffi(@RequestParam(value = "id", defaultValue = "-1") Long id,Model model) {
		if (id != -1) {
			model.addAttribute("woodAffiBaseInfo", affiBaseInfoService.get(id));
		}
	}
	
	/**
	 * 去除重复
	 * @param request
	 * @return
	 */
	public List<WoodAffiBaseInfo>  removeDuplicate(List<WoodAffiBaseInfo> list) {
	      HashSet h = new HashSet(list);
	      list.clear();
	      list.addAll(h);
	      return list;
	}
	
}
