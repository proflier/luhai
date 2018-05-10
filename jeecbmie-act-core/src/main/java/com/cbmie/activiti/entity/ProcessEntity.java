package com.cbmie.activiti.entity;

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
@Entity
@Table(name="act_re_procentity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ProcessEntity {
	private Long id;
	private String modeId;
	private String procDefKey;
	private String clazzFullname;
	private String entityView;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="MODE_ID")
	public String getModeId() {
		return modeId;
	}
	public void setModeId(String modeId) {
		this.modeId = modeId;
	}
	@Column(name="PROC_DEF_KEY")
	public String getProcDefKey() {
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	@Column(name="CLAZZ_FULL_NAME")
	public String getClazzFullname() {
		return clazzFullname;
	}
	public void setClazzFullname(String clazzFullname) {
		this.clazzFullname = clazzFullname;
	}
	@Column(name="ENTITY_VIEW")
	public String getEntityView() {
		return entityView;
	}
	public void setEntityView(String entityView) {
		this.entityView = entityView;
	}
}
