package com.cbmie.system.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.cbmie.common.utils.EhCacheUtils;
import com.cbmie.common.utils.SpringContextHolder;
import com.cbmie.system.entity.Organization;
import com.cbmie.system.entity.User;
import com.cbmie.system.service.OrganizationService;
import com.cbmie.system.service.UserService;

public class OrgUserUtils implements Serializable {

	private static final long serialVersionUID = 2389384278110557582L;
	public static final String ORGUSERBEAN = "org_user_bean";
	private static OrganizationService orgService = SpringContextHolder.getBean(OrganizationService.class);
	private static UserService userService = SpringContextHolder.getBean(UserService.class);
	
	/**
	 * 获取组织用户，部门必须有人员，才会显示
	 * */
	public static List<OrgUserBean> getOrgUserList(){
		User user = UserUtil.getCurrentUser();
		if(user!=null){
			Organization org = getCompany(user.getOrganization());
			if(org!=null){
				Object obj = EhCacheUtils.get(ORGUSERBEAN);
				if(obj==null){
					putOrgUserToCache();
				}else{
					Map<String,List<OrgUserBean>> map  = (Map<String, List<OrgUserBean>>)obj;
					if(map.get(org.getId().toString())==null){
						putOrgUserToCache();
					}
				}
				List<OrgUserBean> result =((Map<String,List<OrgUserBean>>) EhCacheUtils.get(ORGUSERBEAN)).get(org.getId().toString());
				return result;
			}
		}
		return new ArrayList<OrgUserBean>();
		
	}
	
	public static void putOrgUserToCache(){
		User user = UserUtil.getCurrentUser();
		if(user!=null){
			Organization org = getCompany(user.getOrganization());
			if(org!=null){
				List<OrgUserBean> orgList = new ArrayList<OrgUserBean>();
				List<OrgUserBean> userList = new ArrayList<OrgUserBean>();
				getSonOrg(org,orgList);
				getUsersByOrg(userList,orgList);
				for(OrgUserBean bean : orgList){
					if("0".equals(bean.getIsLeaf())){
						userList.add(bean);
					}
				}
				if(EhCacheUtils.get(ORGUSERBEAN)==null){
					Map<String,List<OrgUserBean>> map = new HashMap<String,List<OrgUserBean>>();
					map.put(org.getId().toString(), userList);
					EhCacheUtils.put(ORGUSERBEAN, map);
				}else{
					Map<String,List<OrgUserBean>> map = (Map<String, List<OrgUserBean>>) EhCacheUtils.get(ORGUSERBEAN);
					map.put(org.getId().toString(), userList);
					EhCacheUtils.put(ORGUSERBEAN, map);
				}
			}
		}
	}
	
	public static void putAllOrgUserToCache(){
		User user = userService.getUserByLoginName("admin");
		if(user!=null){
			Organization org = getCompany(user.getOrganization());
			if(org!=null){
				List<OrgUserBean> orgList = new ArrayList<OrgUserBean>();
				List<OrgUserBean> userList = new ArrayList<OrgUserBean>();
				getSonOrg(org,orgList);
				getUsersByOrg(userList,orgList);
				for(OrgUserBean bean : orgList){
					if("0".equals(bean.getIsLeaf())){
						userList.add(bean);
					}
				}
				if(EhCacheUtils.get(ORGUSERBEAN)==null){
					Map<String,List<OrgUserBean>> map = new HashMap<String,List<OrgUserBean>>();
					map.put(org.getId().toString(), userList);
					EhCacheUtils.put(ORGUSERBEAN, map);
				}else{
					Map<String,List<OrgUserBean>> map = (Map<String, List<OrgUserBean>>) EhCacheUtils.get(ORGUSERBEAN);
					map.put(org.getId().toString(), userList);
					EhCacheUtils.put(ORGUSERBEAN, map);
				}
			}
		}
	}
	
	public static Organization getCompany(Organization org){
		if(org==null) return null;
		if(GlobalParam.ORG_TYPE_COMPANY.equals(org.getOrgType())){
			return org;
		}else{
			if(org.getPid()!=null){
				Query query = orgService.getEntityDao().createQuery("from Organization a where a.id=:id");
				List<Organization> list = query.setParameter("id", org.getPid()).list();
				if(list!=null && list.size()>0){
					org = list.get(0);
				}else{
					org=null;
				}
				return getCompany(org);
			}else{
				return null;
			}
		}
	}
	
	public static void getSonOrg(Organization pOrg,List<OrgUserBean> orgList){
		if(pOrg!=null){
			OrgUserBean bean = new OrgUserBean();
			bean.setId(pOrg.getId()+"");
			if(pOrg.getPid()==null){
				bean.setState("open");
				bean.setPid(null);
			}else{
				bean.setPid(pOrg.getPid()+"");
			}
			bean.setName(pOrg.getOrgName());
			bean.setType(pOrg.getOrgType());
			
			orgList.add(bean);
			
			Query query = orgService.getEntityDao().createQuery("from Organization a where a.pid=:id");
			List<Organization> son_list = query.setParameter("id", pOrg.getId()).list();
			if(son_list != null && son_list.size()>0){
				bean.setIsLeaf("0");
				for(Organization tOrg : son_list){
					getSonOrg(tOrg, orgList);
				}
			}
		}
	}
	/**
	 * 获取部门人员
	 * */
	public static void getUsersByOrg(List<OrgUserBean> users,List<OrgUserBean> orgs){
		for(int i=0,j=orgs.size();i<j;i++){
			Query query = userService.getEntityDao().createQuery("from User a where a.organization.id=:id  and a.loginStatus=1 ");
			List<User> user_list = query.setParameter("id", Integer.parseInt(orgs.get(i).getId())).list();
			if(user_list!=null && user_list.size()>0){
				orgs.get(i).setIsLeaf("0");
				for(User user:user_list){
					OrgUserBean bean = new OrgUserBean();
					bean.setId(user.getLoginName());
					bean.setPid(orgs.get(i).getId());
					bean.setName(user.getName());
					bean.setState("open");
					bean.setLoginName(user.getLoginName());
					bean.setType("2");
					users.add(bean);
				}
			}
		}
	}

	public static List<OrgUserBean> getCompanyUserAll(){
		Object obj = EhCacheUtils.get(ORGUSERBEAN);
		if(obj==null){
			putAllOrgUserToCache();
		}else{
			Map<String,List<OrgUserBean>> map  = (Map<String, List<OrgUserBean>>)obj;
			if(map.get("17")==null){
				putAllOrgUserToCache();
			}
		}
		List<OrgUserBean> result =((Map<String,List<OrgUserBean>>) EhCacheUtils.get(ORGUSERBEAN)).get("17");
		return result;
	}
}
