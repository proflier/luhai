package com.cbmie.baseinfo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

/**
 * 关联单位__基本信息
 */
@Entity
@Table(name = "WOOD_AFFI_BASE_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodAffiBaseInfo extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 客户英文名
	 */
	private String customerEnName;

	/**
	 * 客户类型
	 */
	private String customerType;

	/**
	 * 法定代表人姓名
	 */
	private String legalName;

	/**
	 * 身份号码
	 */
	private String idCardNO;

	/**
	 * 业务范围
	 */
	private String businessScope;

	/**
	 * 国别地区
	 */
	private String country;

	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 邮编
	 */
	private String zipCode;

	/**
	 * 网址
	 */
	private String internetSite;

	/**
	 * 联系人
	 */
	private String contactPerson;

	/**
	 * 联系电话
	 */
	private String phoneContact;

	/**
	 * 传真
	 */
	private String fax;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 国内税务编号
	 */
	private String internalTaxNO;

	/**
	 * 状态：1启用、0停用
	 */
	private Integer status;
	
	/**
	 * 备注
	 */
	private String comments;
	
	/**
	 * 单位简称
	 */
	private String unitAbbr;
	
	/**
	 * 银行信息
	 */
	@JsonIgnore
	private List<WoodAffiBankInfo> affiBankInfo = new ArrayList<WoodAffiBankInfo>();
	
	/**
	 * 客户信息
	 */
	@JsonIgnore
	private List<WoodAffiCustomerInfo> affiCustomerInfo = new ArrayList<WoodAffiCustomerInfo>();
	
	/**
	 * 联系人信息
	 */
	@JsonIgnore
	private List<WoodAffiContactInfo> affiContactInfo = new ArrayList<WoodAffiContactInfo>();

	@Column(name = "COMMENTS", nullable = true)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "CUSTOMER_CODE", nullable = true)
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@Column(name = "CUSTOMER_NAME", nullable = true)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "CUSTOMER_EN_NAME")
	public String getCustomerEnName() {
		return customerEnName;
	}

	public void setCustomerEnName(String customerEnName) {
		this.customerEnName = customerEnName;
	}

	@Column(name = "CUSTOMER_TYPE", nullable = true)
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Column(name = "LEGAL_NAME")
	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	@Column(name = "ID_CARD_NO", nullable = true)
	public String getIdCardNO() {
		return idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
	}

	@Column(name = "BUSINESS_SCOPE", nullable = true)
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@Column(name = "COUNTRY", nullable = true)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "ADDRESS", nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "INTERNET_SITE", nullable = true)
	public String getInternetSite() {
		return internetSite;
	}

	public void setInternetSite(String internetSite) {
		this.internetSite = internetSite;
	}

	@Column(name = "CONTACT_PERSON", nullable = true)
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name = "PHONE_CONTACT", nullable = true)
	public String getPhoneContact() {
		return phoneContact;
	}

	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}

	@Column(name = "INTERNAL_TAX_NO", nullable = true)
	public String getInternalTaxNO() {
		return internalTaxNO;
	}

	public void setInternalTaxNO(String internalTaxNO) {
		this.internalTaxNO = internalTaxNO;
	}

	@Column(name = "FAX", nullable = true)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ZIP_CODE", nullable = true)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(name = "STATUS", nullable = true)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "UNIT_ABBR")
	public String getUnitAbbr() {
		return unitAbbr;
	}

	public void setUnitAbbr(String unitAbbr) {
		this.unitAbbr = unitAbbr;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<WoodAffiBankInfo> getAffiBankInfo() {
		return affiBankInfo;
	}

	public void setAffiBankInfo(List<WoodAffiBankInfo> affiBankInfo) {
		this.affiBankInfo = affiBankInfo;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<WoodAffiCustomerInfo> getAffiCustomerInfo() {
		return affiCustomerInfo;
	}

	public void setAffiCustomerInfo(List<WoodAffiCustomerInfo> affiCustomerInfo) {
		this.affiCustomerInfo = affiCustomerInfo;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<WoodAffiContactInfo> getAffiContactInfo() {
		return affiContactInfo;
	}

	public void setAffiContactInfo(List<WoodAffiContactInfo> affiContactInfo) {
		this.affiContactInfo = affiContactInfo;
	}
}