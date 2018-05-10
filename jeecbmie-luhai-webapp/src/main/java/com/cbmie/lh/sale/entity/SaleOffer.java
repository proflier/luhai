package com.cbmie.lh.sale.entity;

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
 * 销售报盘
 */
@Entity
@Table(name = "LH_SALE_OFFER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleOffer extends BaseEntity {
	
	/**
	 * 报盘单号
	 */
	private String offerNo;
	
	/**
	 * 客户名称
	 */
	private String customer;
	
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
	private String faxContact;
	
	/**
	 * 邮箱
	 */
	private String emailContact;
	
	/**
	 * 付款方式（结算方式）
	 */
	private String payMode;
	
	/**
	 * 交货开始日期
	 */
	private Date deliveryStartDate;
	
	/**
	 * 交货结束日期
	 */
	private Date deliveryEndDate;
	
	/**
	 * 交货方式
	 */
	private String deliveryMode;
	
	/**
	 * 交货地点
	 */
	private String deliveryAddr;
	
	/**
	 * 业务经办人
	 */
	private String businessManager;
	
	/**
	 * 联系电话
	 */
	private String phoneBusiness;
	
	/**
	 * 传真
	 */
	private String faxBusiness;
	
	/**
	 * 邮箱
	 */
	private String emailBusiness;
	
	/**
	 * 报盘日期
	 */
	private Date offerDate;
	
	/**
	 * 报盘有效期
	 */
	private Date validityDate;
	
	/**
	 * 其他说明
	 */
	private String otherExplain;
	
	/**
	 * 制单日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date billDate;
	
	List<SaleOfferGoods> goodsList = new ArrayList<SaleOfferGoods>();

	@Column
	public String getOfferNo() {
		return offerNo;
	}

	public void setOfferNo(String offerNo) {
		this.offerNo = offerNo;
	}

	@Column
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Column
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column
	public String getPhoneContact() {
		return phoneContact;
	}

	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}

	@Column
	public String getFaxContact() {
		return faxContact;
	}

	public void setFaxContact(String faxContact) {
		this.faxContact = faxContact;
	}

	@Column
	public String getEmailContact() {
		return emailContact;
	}

	public void setEmailContact(String emailContact) {
		this.emailContact = emailContact;
	}

	@Column
	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	@Column
	public Date getDeliveryStartDate() {
		return deliveryStartDate;
	}

	public void setDeliveryStartDate(Date deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}

	@Column
	public Date getDeliveryEndDate() {
		return deliveryEndDate;
	}

	public void setDeliveryEndDate(Date deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}

	@Column
	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Column
	public String getDeliveryAddr() {
		return deliveryAddr;
	}

	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}

	@Column
	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	@Column
	public String getPhoneBusiness() {
		return phoneBusiness;
	}

	public void setPhoneBusiness(String phoneBusiness) {
		this.phoneBusiness = phoneBusiness;
	}

	@Column
	public String getFaxBusiness() {
		return faxBusiness;
	}

	public void setFaxBusiness(String faxBusiness) {
		this.faxBusiness = faxBusiness;
	}

	@Column
	public String getEmailBusiness() {
		return emailBusiness;
	}

	public void setEmailBusiness(String emailBusiness) {
		this.emailBusiness = emailBusiness;
	}

	@Column
	public Date getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}

	@Column
	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	@Column(length=500)
	public String getOtherExplain() {
		return otherExplain;
	}

	public void setOtherExplain(String otherExplain) {
		this.otherExplain = otherExplain;
	}

	@Column
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<SaleOfferGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<SaleOfferGoods> goodsList) {
		this.goodsList = goodsList;
	}
	
}
