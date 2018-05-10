package com.cbmie.lh.feedback.entity;

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

@Entity
@Table(name = "LH_DISCUSS_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class DiscussGroup extends BaseEntity {

	private String groupName;
	@JsonIgnore
	private List<DiscussMember> members = new ArrayList<DiscussMember>();
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="discussGroupId",orphanRemoval=true)
	public List<DiscussMember> getMembers() {
		return members;
	}
	public void setMembers(List<DiscussMember> members) {
		this.members = members;
	}
	
}
