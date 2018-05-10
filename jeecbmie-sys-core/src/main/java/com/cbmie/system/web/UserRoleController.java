package com.cbmie.system.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbmie.common.persistence.Page;
import com.cbmie.common.web.BaseController;
import com.cbmie.system.entity.UserRole;
import com.cbmie.system.service.UserRoleService;
@Controller
@RequestMapping("system/userRole")
public class UserRoleController extends BaseController {
	
	@Autowired
	private UserRoleService userRoleService;
	
	@RequestMapping(value = "selectOrgs/{relationId}/{relationDepts}")
	public String selectOrgsForUser(@PathVariable("relationId") Integer relationId,@PathVariable("relationDepts") String relationDepts, Model model){
		model.addAttribute("relationId", relationId);
		model.addAttribute("relationDepts", relationDepts);
		return "system/roleUserOrgList";
	}
	
	@RequestMapping(value = "relations/{roleId}")
	@ResponseBody
	public Map<String, Object> getRelationInRole(HttpServletRequest request,@PathVariable("roleId") Integer roleId){
		Page<UserRole> page = getPage(request);
		userRoleService.getUserRoleByRoleId(page,roleId);
		return getEasyUIData(page);
	}
	@RequestMapping(value = "changeOrgs/{id}")
	@ResponseBody
	public String changeRelationDepts(@PathVariable("id") Integer id,@RequestBody List<Integer> newDeptList){
		String depts="";
		if(newDeptList!=null && newDeptList.size()>0){
			for(int i=0;i<newDeptList.size();i++){
				if(i==newDeptList.size()-1){
					depts+=newDeptList.get(i).toString();
				}else{
					depts+=newDeptList.get(i).toString()+",";
				}
			}
		}
		UserRole userRole = userRoleService.get(id);
		userRole.setRelationDepts(depts);
		userRoleService.update(userRole);
		return "success";
	}
	
}
