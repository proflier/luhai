package com.cbmie.activiti.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

/**
 * 流程办理记录（统计手机端和PC端任务的办理比例）
 * @author czq
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "act_handle_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class HandleRecord implements java.io.Serializable {
	
	private static final long serialVersionUID = -242319717295077169L;

	private Long id;
	
	/**
	 * 流程key
	 */
	private String processKey;
	
	/**
	 * 任务名
	 */
	private String taskName;
	
	/**
	 * 操作系统
	 */
	private String os;
	
	/**
	 * 办理人
	 */
	private String transactor;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createDate;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getProcessKey() {
		return processKey;
	}
	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}
	
	@Column
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@Column
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	
	@Column
	public String getTransactor() {
		return transactor;
	}
	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}
	
	@Column
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
