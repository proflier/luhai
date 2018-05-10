package com.cbmie.activiti.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name="act_sys_DELEGATEPERSON")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DelegatePerson extends BaseEntity{

	/**流程定义key (用于区分模块)*/
	private String procDefKey;
	/**委托人*/
	private String consigner;
	/**受委托人*/
	private String mandatary;
	/**开始时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date beginDate;
	/**结束时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	/**终止委托时间*/
	private Date terminatingDate;
	/**激活标志  1激活 0关闭*/
	private String activateFlag="1";
	
	public String getConsigner() {
		return consigner;
	}
	public void setConsigner(String consigner) {
		this.consigner = consigner;
	}
	public String getMandatary() {
		return mandatary;
	}
	public void setMandatary(String mandatary) {
		this.mandatary = mandatary;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTerminatingDate() {
		return terminatingDate;
	}
	public void setTerminatingDate(Date terminatingDate) {
		this.terminatingDate = terminatingDate;
	}
	@Column(length=5)
	public String getActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(String activateFlag) {
		this.activateFlag = activateFlag;
	}
	
	@Column(name = "proc_def_key" ,length=999)
	public String getProcDefKey() {
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
}
