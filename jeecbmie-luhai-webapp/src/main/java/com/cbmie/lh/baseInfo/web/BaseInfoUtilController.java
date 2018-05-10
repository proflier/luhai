package com.cbmie.lh.baseInfo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.baseinfo.entity.WoodAffiBankInfo;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.baseinfo.entity.WoodGk;
import com.cbmie.baseinfo.service.AffiBankInfoService;
import com.cbmie.baseinfo.service.AffiBaseInfoService;
import com.cbmie.baseinfo.service.GkService;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.system.entity.CountryArea;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.CountryAreaService;
import com.cbmie.system.service.OrganizationService;
import com.cbmie.system.service.UserService;
import com.cbmie.system.utils.UserUtil;
@Controller
@RequestMapping(value="/baseInfo/baseUtil")
public class BaseInfoUtilController {
	
	
	@Autowired
	private AffiBaseInfoService affiService;
	@Autowired
	private CountryAreaService countryAreaService;
	@Autowired
	private OrganizationService orgservice;
	
	@Autowired
	private AffiBankInfoService affiBankInfoService;
	
	@Autowired
	private AffiBaseInfoService affiBaseInfoService;
	
	@Autowired
	private GkService gkService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BaseInfoUtilService bius;
	
	
	/**
	 * 关联单位之供应商选项
	 * */
	@RequestMapping(value="affiBaseInfoItems",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBaseInfo> getSelectItemOfAffiBaseInfo(){
		String sql = " select * from WOOD_AFFI_BASE_INFO a where a.customer_type like '%10230002%' and status=1  ";
		List<WoodAffiBaseInfo> result = affiService.getEntityDao().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class).list();
		return result;
	}
	
	@RequestMapping(value="getAffiByType/{type}",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBaseInfo> getSelectItemOfAffiBaseInfo(@PathVariable String type){
		String sql = " select * from WOOD_AFFI_BASE_INFO a where a.customer_type like '%"+type+"%' and status=1 ";
		List<WoodAffiBaseInfo> result = affiService.getEntityDao().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class).list();
		return result;
	}
	
	@RequestMapping(value="getAffiUnitAbbr",method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAffiUnitAbbr(){
		String sql = " select unit_abbr from WOOD_AFFI_BASE_INFO a where a.customer_type like '%10230001%' and status=1 ";
		List<String> result = affiService.getEntityDao().createSQLQuery(sql).list();
		return result;
	}
	
	/**
	 * 供应商展示
	 * 返回值包含  单位名称、英文名称、国家名称、英文名称
	 * */
	@RequestMapping(value="affiBaseInfoShow",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> getWoodAffiBaseInfoByCode(@RequestParam String code){
		Map<String,String> result = new HashMap<String,String>();
		try{
			String hql = "from WoodAffiBaseInfo a where a.customerCode=:code";
			List<WoodAffiBaseInfo> list = affiService.getEntityDao().createQuery(hql).setParameter("code", code).list();
			if(list!=null && list.size()>0){
				WoodAffiBaseInfo info = list.get(0);
				result.put("customerName", info.getCustomerName());
				result.put("customerNameE", info.getCustomerEnName());
				String country = info.getCountry();
				if(StringUtils.isNotBlank(country)){
					CountryArea ca = countryAreaService.get(Integer.parseInt(country));
					result.put("countryName", ca.getName());
					result.put("countryNameE", ca.getNameE());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 供应商展示
	 * 返回值包含  单位名称
	 * */
	@RequestMapping(value="affiBaseInfoNameByCode/{code}",method = RequestMethod.GET)
	@ResponseBody
	public String getAffiBaseInfoByCode(@PathVariable String code){
		return bius.getAffiBaseInfoByCode(code);
	}
	
	/**
	 * 公司下拉菜单
	 * */
	@RequestMapping(value="companyItems",method = RequestMethod.GET)
	@ResponseBody
	public List<Organization> getCompany(){
		String sql = "from Organization a where a.orgType='0' order by a.id asc";
		List<Organization> list = orgservice.getEntityDao().createQuery(sql).list();
		return list;
	}
	/**
	 * 根据Id获取组织名称
	 * */
	@RequestMapping(value="orgNameShow",method = RequestMethod.GET)
	@ResponseBody
	public String getOrgNameById(@RequestParam Integer orgId){
		Organization org = orgservice.get(orgId);
		if(org != null){
			return org.getOrgName();
		}
		return null;
	}
	
	/**********************单位信息下拉相关 start*********************************/
	
	/**
	 * 符合条件单位信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="jsonBaseInfo",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBaseInfo> affiBaseInfoList(HttpServletRequest request) {
		List<WoodAffiBaseInfo> returnList = new ArrayList<WoodAffiBaseInfo>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			for(PropertyFilter filter : filters){
				if("customerType".equals(filter.getPropertyName())){
					String matchValue = (String) filter.getMatchValue();
					String[] matchValues = matchValue.split("or");
					List<WoodAffiBaseInfo> list = affiBaseInfoService.getByCustomerTypes(matchValues);
					returnList.addAll(list);
					break;
				}
			}
		}else{
			returnList.addAll(affiBaseInfoService.getAll());
		}
		return returnList;
	}
	
	/**
	 * 符合条件单位信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="jsonBaseInfo4UserPermission",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBaseInfo> affiBaseInfoList4UserPermission(HttpServletRequest request) {
		List<WoodAffiBaseInfo> returnList = new ArrayList<WoodAffiBaseInfo>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		User currentUser = UserUtil.getCurrentUser();
		if(filters.size()>0){
			if(currentUser.getId()==1){
				for(PropertyFilter filter : filters){
					if("customerType".equals(filter.getPropertyName())){
						String matchValue = (String) filter.getMatchValue();
						String[] matchValues = matchValue.split("or");
						List<WoodAffiBaseInfo> list = affiBaseInfoService.getByCustomerTypes(matchValues);
						returnList.addAll(list);
						break;
					}
				}
			}else {
				for(PropertyFilter filter : filters){
					if("customerType".equals(filter.getPropertyName())){
						String matchValue = (String) filter.getMatchValue();
						if(matchValue.equals("2")||matchValue.equals("3")){
							Session session =SecurityUtils.getSubject().getSession();
							String customerCode =  (String) session.getAttribute("customerCode");
							String[] customerCodeArr = customerCode.split(",");
							List<WoodAffiBaseInfo> list = affiBaseInfoService.getByCustomerTypes4UserPermission(matchValue,customerCodeArr);
							returnList.addAll(list);
							break;
						}else{
							String[] matchValues = matchValue.split("or");
							List<WoodAffiBaseInfo> list = affiBaseInfoService.getByCustomerTypes(matchValues);
							returnList.addAll(list);
							break;
						}
					}
				}
			}
		}else{
			returnList.addAll(affiBaseInfoService.getAll());
		}
		return returnList;
	}
	
	/**
	 * 符合条件单位信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getAffiOurUnitOrCustomer",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBaseInfo> getAffiOurUnitOrCustomer() {
		return affiBaseInfoService.getAffiOurUnitOrCustomer();
	}
	
	
	@RequestMapping(value="regexpBaseInfo/{regexp}",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBaseInfo> getAffiBaseInfo(@PathVariable String regexp){
		String sql = "select * from WOOD_AFFI_BASE_INFO a where a.customer_Type REGEXP '"+regexp+"' and status = '1' order by a.id asc ";
		return orgservice.getEntityDao().createSQLQuery(sql).addEntity(WoodAffiBaseInfo.class).list();
	}
	/**
	 * 去除重复单位信息列表
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 */
	public List<WoodAffiBaseInfo>  removeDuplicate(List<WoodAffiBaseInfo> list) {
	      HashSet h = new HashSet(list);
	      list.clear();
	      list.addAll(h);
	      return list;
	}
	
	/**
	 * 如果选择公司类别编码1，禁止带出编码为10，11，12的公司信息
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 */
	public List<WoodAffiBaseInfo> validateOurUnit(String  customerType,List<WoodAffiBaseInfo> returnList) {
		if(customerType.equals("1")){//如果选择公司类别编码1，禁止带出编码为10，11，12的公司信息
			List<WoodAffiBaseInfo> newResult = new ArrayList<WoodAffiBaseInfo>();
			for(WoodAffiBaseInfo info:returnList){
				if(!info.getCustomerType().equals("10") && !info.getCustomerType().equals("11") && !info.getCustomerType().equals("12"))
					newResult.add(info);
			}
			returnList = newResult;
		}
		return returnList;
	}
	
	/**
	 * 显示id对应的关联单位名称
	 * @param dict 
	 * @return
	 */
	@RequestMapping(value = "getBaseInfoName/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getAffiBaseInfoById(@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return affiBaseInfoService.get(Long.valueOf(dict)).getCustomerName();
		} else {
			return "";
		}
	}

	
	/**
	 * 符合条件单位银行信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="jsonBankInfo",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodAffiBankInfo> affiBankInfoList(HttpServletRequest request) {
		List<WoodAffiBankInfo> returnList = new ArrayList<WoodAffiBankInfo>();
		List<WoodAffiBaseInfo> BaseInfoList = new ArrayList<WoodAffiBaseInfo>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			for(PropertyFilter filter : filters){
				BaseInfoList.addAll(affiBaseInfoService.getByCustomerType((String)filter.getMatchValue()));
			}
			for(WoodAffiBaseInfo woodAffiBaseInfo : BaseInfoList){
				returnList.addAll(woodAffiBaseInfo.getAffiBankInfo());
			}
		}else{
			returnList.addAll(affiBankInfoService.getAll());
		}
		return returnList;
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
	 * 根据父id获取银行信息
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "selectBankByCode/{pCode}")
	@ResponseBody
	public  List<WoodAffiBankInfo> selectBankByCode(@PathVariable("pCode") String pCode) {
		List<WoodAffiBankInfo> affiBankInfoList = affiBankInfoService.getByParentCode(pCode);
		return affiBankInfoList;
	}
	
	/**
	 * 显示id对应的关联单位银行名称
	 * @param dict 
	 * @return
	 */
	@RequestMapping(value = "getBankInfoName/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getAffiBankInfoById(@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return affiBankInfoService.get(Long.valueOf(dict)).getBankName();
		}else{
			return "";
		}
		
	}
	
	/**********************单位信息下拉相关 end*********************************/
	
	/**********************港口信息下拉相关 start*********************************/
	/**
	 * 符合条件港口信息下拉
	 * 根据页面 filter 控制要显示的单位信息
	 * @param request（目前支持根据国家筛选）
	 * @return
	 */
	@RequestMapping(value="jsonGK",method = RequestMethod.GET)
	@ResponseBody
	public List<WoodGk> gkList(HttpServletRequest request) {
		List<WoodGk> returnList = new ArrayList<WoodGk>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		if(filters.size()>0){
			for(PropertyFilter filter : filters){
				returnList.addAll(gkService.getByGJtype((String)filter.getMatchValue()));
			}
		}else{
			returnList.addAll(gkService.getAll());
		}
		return returnList;
	}
	
	/**
	 * 显示id对应的港口名称
	 * @param dict 
	 * @return
	 */
	@RequestMapping(value = "getPortName/{dict}", method = RequestMethod.GET)
	@ResponseBody
	public String  getGKById(@PathVariable("dict") String dict) {
		if(StringUtils.isNotBlank(dict)){
			return gkService.get(Long.valueOf(dict)).getGkm();
		}else{
			return "";
		}
		
	}
	
	/**
	 * 供应商展示
	 * 返回值包含  单位名称
	 * */
	@RequestMapping(value="getPortNameByCode/{code}",method = RequestMethod.GET)
	@ResponseBody
	public String getGKMByCode(@PathVariable String code){
		String result = "";
		try{
			String hql = "from WoodGk a where a.bm=:code";
			List<WoodGk> list = gkService.getEntityDao().createQuery(hql).setParameter("code", code).list();
			if(list!=null && list.size()>0){
				WoodGk info = list.get(0);
				result = info.getGkm();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取国别区域json
	 */
	@RequestMapping(value="countryAreaItems",method = RequestMethod.GET)
	@ResponseBody
	public List<CountryArea> countryAreaList(HttpServletRequest request) {
		List<CountryArea> countryAreaList = countryAreaService.getEffectArea();
		return countryAreaList;
	}
	

	/**
	 * 根据id获取国别区域名称
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "countryAreaNameShow/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable("id") Integer id, Model model) {
		CountryArea countryArea = bius.getCountryArea(id);
		return countryArea.getName();
	}
	
	/**
	 * 获取用户
	 */
	@RequestMapping(value="userItems",method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUserList() {
		List<User> userList = userService.getEffectUsers();
		return userList;
	}
	/**
	 */
	@RequestMapping(value="userItemsByOrgId",method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUserListByOrgId(@RequestParam(required=false,value="orgId") Integer orgId) {
		List<User> userList = null;
		if(orgId==null){
			userList = userService.getEffectUsers();
		}else{
			String sql = "select * from user a where a.ORG_ID=:orgId and a.login_status=1 ";
			userList = userService.getEntityDao().createSQLQuery(sql).addEntity(User.class).setParameter("orgId", orgId).list();
		}
		return userList;
	}
	/**
	 * 根据userId获取用户名
	 */
	@RequestMapping(value="userNameShow",method = RequestMethod.GET)
	@ResponseBody
	public String getUserNameById(@RequestParam Integer userId){
		return bius.getUserById(userId).getName();
	}
	
}
