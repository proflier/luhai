package com.cbmie.baseinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 关联单位__客户评审
 */
@Entity
@Table(name = "WOOD_AFFI_CUSTOMER_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodAffiCustomerInfo extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 授信业务额度
	 */
	private String creditLine;

	/**
	 * 评审有效开始日期
	 */
	private String checkStartTime;

	/**
	 * 评审有效结束日期
	 */
	private String checkEndTime;

	/**
	 * 客户简介及生产经营情况等
	 */
	private String customerAndConditions;
	
	/**
	 * 关联id
	 */
	private String parentId;
	
	@Column(name = "PARENT_ID", nullable = false)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "CREDIT_LINE", nullable = false)
	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	@Column(name = "CHECK_START_TIME", nullable = false)
	public String getCheckStartTime() {
		return checkStartTime;
	}

	public void setCheckStartTime(String checkStartTime) {
		this.checkStartTime = checkStartTime;
	}

	@Column(name = "CHECK_END_TIME", nullable = false)
	public String getCheckEndTime() {
		return checkEndTime;
	}

	public void setCheckEndTime(String checkEndTime) {
		this.checkEndTime = checkEndTime;
	}

	@Column(name = "CUSTOMER_AND_CONDITIONS", nullable = false)
	public String getCustomerAndConditions() {
		return customerAndConditions;
	}

	public void setCustomerAndConditions(String customerAndConditions) {
		this.customerAndConditions = customerAndConditions;
	}

}