package com.cbmie.lh.permission.entity;

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
 *	业务相关用户权限
 */
@Entity
@Table(name = "LH_BUSINESS_PERSSION")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class BusinessPerssion implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Long id;
	
	/**
	 * 相关人员登陆账号ID
	 */
	private Integer loginId;
	
	/**
	 * 相关业务模块唯一code
	 */
	private String businessCode;
	
	/**
	 * 相关业务模块唯一id
	 */
	private String businessId;
	
	/**
	 * 相关业务模块标识
	 */
	private String businessFlag;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="LOGIN_ID")
	public Integer getLoginId() {
		return loginId;
	}

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	@Column(name="BUSINESS_CODE")
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	@Column(name="BUSINESS_ID")
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	@Column(name="BUSINESS_FLAG")
	public String getBusinessFlag() {
		return businessFlag;
	}

	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}
	
	

	
}