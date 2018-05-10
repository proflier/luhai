package com.cbmie.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 系统通知entity
 */
@Entity
@Table(name = "inform")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class Inform implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 类名
	 */
	private String className;
	
	/**
	 * 业务id
	 */
	private Long businessId;
	
	/**
	 * 通知人
	 */
	private String informPerson;
	
	/**
	 * 通知内容
	 */
	private String informContent;
	
	/**
	 * 剩余天数
	 */
	private Integer residueDays;
	
	/**
	 * 状态
	 */
	private String state;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 修改时间
	 */
	private Date updateDate;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CLASS_NAME")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "BUSINESS_ID")
	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	@Column(name = "INFORM_PERSON")
	public String getInformPerson() {
		return informPerson;
	}

	public void setInformPerson(String informPerson) {
		this.informPerson = informPerson;
	}

	@Column(name = "INFORM_CONTENT")
	public String getInformContent() {
		return informContent;
	}

	public void setInformContent(String informContent) {
		this.informContent = informContent;
	}

	@Column(name = "RESIDUE_DAYS")
	public Integer getResidueDays() {
		return residueDays;
	}

	public void setResidueDays(Integer residueDays) {
		this.residueDays = residueDays;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}