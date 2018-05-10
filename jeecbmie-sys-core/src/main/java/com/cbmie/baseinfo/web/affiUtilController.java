package com.cbmie.baseinfo.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.baseinfo.entity.WoodAffiBankInfo;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.baseinfo.service.AffiBankInfoService;
import com.cbmie.baseinfo.service.AffiBaseInfoService;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.common.web.BaseController;
@Controller
@RequestMapping(value="baseinfo/affiUtil")
public class affiUtilController extends BaseController {

	@Autowired
	private AffiBankInfoService affiBankInfoService;
	
	@Autowired
	private AffiBaseInfoService affiBaseInfoService;
	
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
				String matchValue = (String) filter.getMatchValue();
				String[] matchValues = matchValue.split("or");
				for(int i=0; i<matchValues.length;i++){
					List<WoodAffiBaseInfo> list = affiBaseInfoService.getByCustomerType(matchValues[i]);
					list = validateOurUnit(matchValues[i],list);//如果选择公司类别编码1，禁止带出编码为10，11，12的公司信息
					returnList.addAll(list);
				}
			}
			
		}else{
			returnList.addAll(affiBaseInfoService.getAll());
		}
		return removeDuplicate(returnList);
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
}
