package com.cbmie.lh.baseInfo.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.baseinfo.entity.WoodAffiBankInfo;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.baseinfo.entity.WoodGk;
import com.cbmie.baseinfo.service.AffiBankInfoService;
import com.cbmie.baseinfo.service.AffiBaseInfoService;
import com.cbmie.baseinfo.service.GkService;
import com.cbmie.common.persistence.PropertyFilter;
import com.cbmie.system.entity.CountryArea;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.CountryAreaService;
import com.cbmie.system.service.OrganizationService;
import com.cbmie.system.service.UserService;

@Service
@Transactional
public class BaseInfoUtilService {
	
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
	
	/**
	 * 供应商展示
	 * 返回值包含  单位名称
	 */
	public String getAffiBaseInfoByCode(String code){
		String result = "";
		try{
			String hql = "from WoodAffiBaseInfo a where a.customerCode=:code";
			List<WoodAffiBaseInfo> list = affiService.getEntityDao().createQuery(hql).setParameter("code", code).list();
			if(list!=null && list.size()>0){
				WoodAffiBaseInfo info = list.get(0);
				result = info.getCustomerName();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 供应商展示
	 * 返回值对象
	 */
	public WoodAffiBaseInfo getAffiBaseInfoObjByCode(String code){
		WoodAffiBaseInfo info = null;
		try{
			String hql = "from WoodAffiBaseInfo a where a.customerCode=:code";
			List<WoodAffiBaseInfo> list = affiService.getEntityDao().createQuery(hql).setParameter("code", code).list();
			if(list!=null && list.size()>0){
				info = list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 根据id获取国别区域
	 * @param id
	 * @return
	 */
	public CountryArea getCountryArea(Integer id){
		return countryAreaService.get(id);
	}
	
	/**
	 * 根据id获取关联单位_银行
	 * @param id
	 * @return
	 */
	public WoodAffiBankInfo getAffiBankInfoById(Long id) {
		return affiBankInfoService.get(id);
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return userService.getUser(loginName);
	}
	
	
	/**
	 * 根据id获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserById(Integer id) {
		return userService.get(id);
	}
	
	public List<WoodGk> gkList(HttpServletRequest request) {
		List<WoodGk> returnList = new ArrayList<WoodGk>();
		if (request != null) {
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			for(PropertyFilter filter : filters){
				returnList.addAll(gkService.getByGJtype((String)filter.getMatchValue()));
			}
		} else {
			returnList.addAll(gkService.getAll());
		}
		return returnList;
	}
	
	public String getGKMByCode(String code) {
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
	 * 根据Id获取组织名称
	 * */
	public String getOrgNameById(Integer orgId){
		Organization org = orgservice.get(orgId);
		if(org != null){
			return org.getOrgName();
		}
		return null;
	}
}
