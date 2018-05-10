package com.cbmie.activiti.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name="act_sys_procpassdetail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessPassDetail implements Serializable {

	private static final long serialVersionUID = 8187846132171476958L;
	
	private Long id;
	private ProcessPass procPass;
	private String assignee;
	private String readFlag="0";
	private Date readDate;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PASS_ID",referencedColumnName="id")
	public ProcessPass getProcPass() {
		return procPass;
	}
	public void setProcPass(ProcessPass procPass) {
		this.procPass = procPass;
	}
	@Column(name="ASSIGNEE")
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	@Column(name="READ_FLAG")
	public String getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	@Column(name="READ_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getReadDate() {
		return readDate;
	}
	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

}
