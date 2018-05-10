package com.cbmie.lh.permission.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *	业务相关用户权限
 */
public class CurrentSelectUsers  {

	/**讨论人员ID*/
	private String themeMemberIds;
	/**讨论人员name*/
	private String themeMembers;
	/**关键人员ID*/
	private String themeMemberKeyIds;
	/**关键人员name*/
	private String themeMemberKeys;
	public String getThemeMemberIds() {
		return themeMemberIds;
	}
	
	public void setThemeMemberIds(String themeMemberIds) {
		this.themeMemberIds = themeMemberIds;
	}
	public String getThemeMembers() {
		return themeMembers;
	}
	public void setThemeMembers(String themeMembers) {
		this.themeMembers = themeMembers;
	}
	public String getThemeMemberKeyIds() {
		return themeMemberKeyIds;
	}
	public void setThemeMemberKeyIds(String themeMemberKeyIds) {
		this.themeMemberKeyIds = themeMemberKeyIds;
	}
	public String getThemeMemberKeys() {
		return themeMemberKeys;
	}
	public void setThemeMemberKeys(String themeMemberKeys) {
		this.themeMemberKeys = themeMemberKeys;
	}
	
}