package com.cbmie.lh.permission.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 业务权限组实体
 * @author admin
 *
 */
@Entity
@Table(name = "LH_PERMISSION_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PermissionGroup extends BaseEntity {

	private String groupName;
	@JsonIgnore
	private List<PermissionMember> members = new ArrayList<PermissionMember>();
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="permissionGroupId",orphanRemoval=true)
	public List<PermissionMember> getMembers() {
		return members;
	}
	public void setMembers(List<PermissionMember> members) {
		this.members = members;
	}
	
}
