package com.cbmie.lh.purchase.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.entity.BaseEntity;

/**
 * 采购协议
 * 
 */
@Entity
@Table(name = "LH_PURCHASE_AGREEMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PurchaseAgreement extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 协议号 **/
	private String agreementNo;
	/** 协议性质 **/
	private String agreementNature;
	/** 协议客户 **/
	private String customer;
	/** 协议第三方 **/
	private String thirdParty;
	/** 相关单位 **/
	private String company;
	/** 签订时间 **/
	private Date signDate;
	/** 我司单位 **/
	private String ourUnit;
	/** 签订地点 **/
	private String addr;
	/** 备注 **/
	private String remark;
	/** 协议期限开始 **/
	private Date startDate;
	/** 协议期限结束 **/
	private Date endDate;

	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column
	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	@Column
	public String getAgreementNature() {
		return agreementNature;
	}

	public void setAgreementNature(String agreementNature) {
		this.agreementNature = agreementNature;
	}

	@Column
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Column
	public String getThirdParty() {
		return thirdParty;
	}

	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	@Column
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@Column
	public String getOurUnit() {
		return ourUnit;
	}

	public void setOurUnit(String ourUnit) {
		this.ourUnit = ourUnit;
	}

	@Column
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
