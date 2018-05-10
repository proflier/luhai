package com.cbmie.system.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrgUserBean implements Serializable {

	private static final long serialVersionUID = 2958606513121039808L;
	private String id;
	private String pid;
	//专职user
	private String loginName;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	private String name;
	private String type;
	/**0不是，1是*/
	private String isLeaf="1";
	private String state="closed";
	private boolean checked = false;
	
	private List<OrgUserBean> OrgUserBeanList = new ArrayList<OrgUserBean>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<OrgUserBean> getOrgUserBeanList() {
		return OrgUserBeanList;
	}
	public void setOrgUserBeanList(List<OrgUserBean> orgUserBeanList) {
		OrgUserBeanList = orgUserBeanList;
	}
	
}
