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

/**
 * 流程分支判断
 * @author admin
 *
 */
@Entity
@Table(name="act_process_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ProcessConfig {
	private Long id;
	/**
	 * 业务名称
	 */
	private String processName;
	/**
	 * 业务对应实体
	 */
	private String entityView;
	/**
	 * 关键字段
	 */
	private String keyWord;
	/**
	 * 关键值
	 */
	private String keyValue;
	/**
	 * 公式
	 */
	private String formula;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 备注
	 */
	private String comment;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="PROCESS_NAME")
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	@Column(name="COMMENT")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Column( name="STATE" ,columnDefinition = "varchar(255) default '0'")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column(name="ENTITY_VIEW")
	public String getEntityView() {
		return entityView;
	}
	public void setEntityView(String entityView) {
		this.entityView = entityView;
	}
	@Column(name="KEY_VALUE")
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	@Column(name="KEY_WORD")
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	@Column(name="FORMULA")
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	
	
	
	
}
