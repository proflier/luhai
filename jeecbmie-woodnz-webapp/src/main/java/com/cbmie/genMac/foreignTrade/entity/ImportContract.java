package com.cbmie.genMac.foreignTrade.entity;

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
 * 进口合同
 */
@Entity
@Table(name = "IMPROT_CONTRACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ImportContract extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 合同号
	 */
	private String contractNO;

	/**
	 * 合同状态
	 */
	private String state ="草稿";

	/**
	 * 业务员
	 */
	private String relativePerson;

	/**
	 * 签署日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date signatureDate;

	/**
	 * 供应商
	 */
	private String supplier;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 原币金额
	 */
	private String originalCurrency;

	/**
	 * 价格条款
	 */
	private String pricingTerm;

	/**
	 * 运输方式
	 */
	private String transportMode;

	/**
	 * 国际运费USD
	 */
	private String intFare;

	/**
	 * 国际保费USD
	 */
	private String intSA;

	/**
	 * 保险系数
	 */
	private String safetyFactor;

	/**
	 * 保险费率
	 */
	private String insuranceRate;

	/**
	 * 装运港
	 */
	private String transportPort;

	/**
	 * 装运港描述
	 */
	private String transportPortInfo;

	/**
	 * 目的港
	 */
	private String destinationPort;

	/**
	 * 目的港描述
	 */
	private String destinationPortInfo;

	/**
	 * 装运期限
	 */
	private Date shipmentTime;

	/**
	 * 溢短装率
	 */
	private String moreOrLessRate;

	/**
	 * 签约地
	 */
	private String signPosition;
	
	/**
	 * 最晚开立信用证日期
	 */
	private Date openCreditDate;
	
	private List<Commodity> commodityList = new ArrayList<Commodity>();
	
	/**
	 * 代理协议编号
	 */
	private Long agencyAgreementId;
	
	@Column(name = "AGENCY_AGREEMENT_ID",nullable = false)
	public Long getAgencyAgreementId() {
		return agencyAgreementId;
	}

	public void setAgencyAgreementId(Long agencyAgreementId) {
		this.agencyAgreementId = agencyAgreementId;
	}

	@Column(name = "CONTRACT_NO", nullable = false)
	public String getContractNO() {
		return contractNO;
	}

	public void setContractNO(String contractNO) {
		this.contractNO = contractNO;
	}

	@Column(name = "STATE", nullable = false)
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name = "RELATIVE_PERSION", nullable = false)
	public String getRelativePerson() {
		return relativePerson;
	}


	public void setRelativePerson(String relativePerson) {
		this.relativePerson = relativePerson;
	}

	@Column(name = "SIGNATURE_DATE", nullable = false)
	public Date getSignatureDate() {
		return signatureDate;
	}

	public void setSignatureDate(Date signatureDate) {
		this.signatureDate = signatureDate;
	}

	@Column(name = "SUPPLIER", nullable = false)
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name = "CURRENCY", nullable = false)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "ORIGINAL_CURRENCY", nullable = false)
	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	@Column(name = "PRICING_TERM", nullable = false)
	public String getPricingTerm() {
		return pricingTerm;
	}

	public void setPricingTerm(String pricingTerm) {
		this.pricingTerm = pricingTerm;
	}

	@Column(name = "TRANSPORT_MODE", nullable = false)
	public String getTransportMode() {
		return transportMode;
	}

	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}

	@Column(name = "INT_FARE", nullable = true)
	public String getIntFare() {
		return intFare;
	}

	public void setIntFare(String intFare) {
		this.intFare = intFare;
	}

	@Column(name = "INT_SA", nullable = true)
	public String getIntSA() {
		return intSA;
	}

	public void setIntSA(String intSA) {
		this.intSA = intSA;
	}

	@Column(name = "SAFETY_FACTOR", nullable = false)
	public String getSafetyFactor() {
		return safetyFactor;
	}

	public void setSafetyFactor(String safetyFactor) {
		this.safetyFactor = safetyFactor;
	}

	@Column(name = "INSURANCE_RATE", nullable = false)
	public String getInsuranceRate() {
		return insuranceRate;
	}

	public void setInsuranceRate(String insuranceRate) {
		this.insuranceRate = insuranceRate;
	}

	@Column(name = "TRANSPORT_PORT", nullable = false)
	public String getTransportPort() {
		return transportPort;
	}

	public void setTransportPort(String transportPort) {
		this.transportPort = transportPort;
	}

	@Column(name = "TRANSPORT_PORT_INFO", nullable = true)
	public String getTransportPortInfo() {
		return transportPortInfo;
	}

	public void setTransportPortInfo(String transportPortInfo) {
		this.transportPortInfo = transportPortInfo;
	}

	@Column(name = "DESTINATION_PORT", nullable = false)
	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	@Column(name = "DESTINATION_PORT_INFO", nullable = true)
	public String getDestinationPortInfo() {
		return destinationPortInfo;
	}

	public void setDestinationPortInfo(String destinationPortInfo) {
		this.destinationPortInfo = destinationPortInfo;
	}

	@Column(name = "SHIPMENT_TIME")
	public Date getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(Date shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	@Column(name = "MORE_OR_LESS_RATE", nullable = false)
	public String getMoreOrLessRate() {
		return moreOrLessRate;
	}

	public void setMoreOrLessRate(String moreOrLessRate) {
		this.moreOrLessRate = moreOrLessRate;
	}

	@Column(name = "SIGN_POSITION", nullable = false)
	public String getSignPosition() {
		return signPosition;
	}

	public void setSignPosition(String signPosition) {
		this.signPosition = signPosition;
	}
	
	@Column(name = "OPEN_CREDIT_DATE", nullable = false)
	public Date getOpenCreditDate() {
		return openCreditDate;
	}

	public void setOpenCreditDate(Date openCreditDate) {
		this.openCreditDate = openCreditDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<Commodity> getCommodityList() {
		return commodityList;
	}

	public void setCommodityList(List<Commodity> commodityList) {
		this.commodityList = commodityList;
	}

}