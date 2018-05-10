package com.cbmie.woodNZ.salesDelivery.entity;

import java.util.ArrayList;
import java.util.Date;
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
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 销售放货申请
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "WOOD_SALES_DELIVERY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SalesDelivery extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;


	/**
	 * 客户单位
	 */
	private String customerUnit;
	
	/**
	 * 联系人
	 */
	private String contacts;
	
	/**
	 * 联系电话
	 */
	private String contactsNumber;
	
	/**
	 * 送货地址
	 */
	private String deliveryAddress;
	
	/**
	 * 开单日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date billingDate;
	
	/**
	 * 销售合同号
	 */
	private String billContractNo;
	
	/**
	 * 合同总额
	 */
	private String contractTotal;
	
	/**
	 * 合同累计收款金额
	 */
	private String contractCumulation;
	
	/**
	 * 合同已经放货金额
	 */
	private String contractHaveLending;
	
	/**
	 * 客户累计收款金额
	 */
	private String customerCumulation;
	
	/**
	 * 客户已经放货金额
	 */
	private String customerHaveDelivery;
	
	/**
	 * 客户累计余额
	 */
	private String customerToltalOverage;
	
	/**
	 * 保证金
	 */
	private String securityDeposit;
	
	/***
	 * 付款方式
	 */
	private String paymentMethod;
	
	@Column
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * 本次明细汇总金额
	 */
	private String summaryAmount;
	
	/**
	 * 本次明细汇总数量
	 */
	private String summaryNumber;

	/**
	 * 是否专案
	 */
	private String isProject;
	
	/**
	 * 合同执行人
	 */
	private String contractExecutor;
	
	/**
	 * 合同是否放货完成
	 */
	private String contractDelivery;
	
	/**
	 * 是否代拆
	 */
	private String isOpen;
	
	/**
	 *  优先出货提单号 
	 */
	private String priorityShippingNo;
	
	/**
	 * 状态
	 */
	private String state = "草稿";
	
	/**
	 * 备注
	 */
	private String comment;
	
	private List<SalesDeliveryGoods> salesDeliveryGoodsList = new ArrayList<SalesDeliveryGoods>();
	
	private List<RealSalesDeliveryGoods> realSalesDeliveryGoodsList = new ArrayList<RealSalesDeliveryGoods>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<RealSalesDeliveryGoods> getRealSalesDeliveryGoodsList() {
		return realSalesDeliveryGoodsList;
	}

	public void setRealSalesDeliveryGoodsList(List<RealSalesDeliveryGoods> realSalesDeliveryGoodsList) {
		this.realSalesDeliveryGoodsList = realSalesDeliveryGoodsList;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<SalesDeliveryGoods> getSalesDeliveryGoodsList() {
		return salesDeliveryGoodsList;
	}

	public void setSalesDeliveryGoodsList(List<SalesDeliveryGoods> salesDeliveryGoodsList) {
		this.salesDeliveryGoodsList = salesDeliveryGoodsList;
	}

	@Column
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column
	public String getCustomerUnit() {
		return customerUnit;
	}

	public void setCustomerUnit(String customerUnit) {
		this.customerUnit = customerUnit;
	}

	@Column
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column
	public String getContactsNumber() {
		return contactsNumber;
	}

	public void setContactsNumber(String contactsNumber) {
		this.contactsNumber = contactsNumber;
	}

	@Column
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@Column
	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	@Column
	public String getBillContractNo() {
		return billContractNo;
	}

	public void setBillContractNo(String billContractNo) {
		this.billContractNo = billContractNo;
	}

	@Column
	public String getContractTotal() {
		return contractTotal;
	}

	public void setContractTotal(String contractTotal) {
		this.contractTotal = contractTotal;
	}

	@Column
	public String getContractCumulation() {
		return contractCumulation;
	}

	public void setContractCumulation(String contractCumulation) {
		this.contractCumulation = contractCumulation;
	}

	@Column
	public String getContractHaveLending() {
		return contractHaveLending;
	}

	public void setContractHaveLending(String contractHaveLending) {
		this.contractHaveLending = contractHaveLending;
	}

	@Column
	public String getCustomerCumulation() {
		return customerCumulation;
	}

	public void setCustomerCumulation(String customerCumulation) {
		this.customerCumulation = customerCumulation;
	}

	@Column
	public String getCustomerHaveDelivery() {
		return customerHaveDelivery;
	}

	public void setCustomerHaveDelivery(String customerHaveDelivery) {
		this.customerHaveDelivery = customerHaveDelivery;
	}

	@Column
	public String getCustomerToltalOverage() {
		return customerToltalOverage;
	}

	public void setCustomerToltalOverage(String customerToltalOverage) {
		this.customerToltalOverage = customerToltalOverage;
	}

	@Column
	public String getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(String securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	@Column
	public String getSummaryAmount() {
		return summaryAmount;
	}

	public void setSummaryAmount(String summaryAmount) {
		this.summaryAmount = summaryAmount;
	}

	@Column
	public String getSummaryNumber() {
		return summaryNumber;
	}

	public void setSummaryNumber(String summaryNumber) {
		this.summaryNumber = summaryNumber;
	}

	@Column
	public String getIsProject() {
		return isProject;
	}

	public void setIsProject(String isProject) {
		this.isProject = isProject;
	}

	@Column
	public String getContractExecutor() {
		return contractExecutor;
	}

	public void setContractExecutor(String contractExecutor) {
		this.contractExecutor = contractExecutor;
	}

	@Column
	public String getContractDelivery() {
		return contractDelivery;
	}

	public void setContractDelivery(String contractDelivery) {
		this.contractDelivery = contractDelivery;
	}

	@Column
	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	@Column
	public String getPriorityShippingNo() {
		return priorityShippingNo;
	}

	public void setPriorityShippingNo(String priorityShippingNo) {
		this.priorityShippingNo = priorityShippingNo;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
