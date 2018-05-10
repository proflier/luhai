package com.cbmie.activiti.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
@Entity
@Table(name="act_sys_procpass")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessPass implements Serializable {

	private static final long serialVersionUID = -8484154675472611623L;
	
	private Long id;
	private String procKey;
	private String procInstanceId;
	private String taskId;
	private String nextTaskId;
	private String businessKey;
	private Date createDate;
	private Set<ProcessPassDetail> details = new HashSet<ProcessPassDetail>();
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="PROC_INSTANCE_ID")
	public String getProcInstanceId() {
		return procInstanceId;
	}
	public void setProcInstanceId(String procInstanceId) {
		this.procInstanceId = procInstanceId;
	}
	@Column(name="TASK_ID")
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="procPass")
	@Cascade(value={CascadeType.SAVE_UPDATE,CascadeType.DELETE})
	public Set<ProcessPassDetail> getDetails() {
		return details;
	}
	public void setDetails(Set<ProcessPassDetail> details) {
		this.details = details;
	}
	@Column(name="BUSINESS_KEY")
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	@Column(name="PROC_KEY")
	public String getProcKey() {
		return procKey;
	}
	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}
	@Column(name="NEXT_TASK_ID")
	public String getNextTaskId() {
		return nextTaskId;
	}
	public void setNextTaskId(String nextTaskId) {
		this.nextTaskId = nextTaskId;
	}
	
}
