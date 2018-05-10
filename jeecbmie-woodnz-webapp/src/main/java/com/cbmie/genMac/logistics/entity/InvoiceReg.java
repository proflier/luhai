package com.cbmie.genMac.logistics.entity;

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
 * 到单登记
 * @author czq
 */
@Entity
@Table(name = "invoice_reg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InvoiceReg extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6341576388663483114L;
	
	/**
	 * 提单号
	 */
	private String invoiceNo;
	
	/**
	 * 正本份数
	 */
	private Integer originalNum;
	
	/**
	 * 业务员
	 */
	private String salesman;
	
	/**
	 * 进口合同号
	 */
	private String contractNo;
	
	/**
	 * 进口供应商
	 */
	private String supplier;
	
	/**
	 * 贸易国别
	 */
	private String tradeCountry;
	
	/**
	 * 贸易方式
	 */
	private String tradeWay;
	
	/**
	 * 是否冲销预付款
	 */
	private Integer revAdvPayment;
	
	/**
	 * 库存方式
	 */
	private String inventoryWay;
	
	/**
	 * 价格条款
	 */
	private String pricingTerm;
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 对人民币汇率
	 */
	private Double rmbRate;
	
	/**
	 * 原币金额
	 */
	private String originalCurrency;
	
	/**
	 * 人民币金额
	 */
	private String rmb;
	
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
	 * 目的港
	 */
	private String destinationPort;
	
	/**
	 * 开证行
	 */
	private String bank;
	
	/**
	 * 报关公司
	 */
	private String cusDecCompany;
	
	/**
	 * 提单日期
	 */
	private Date submitDate;
	
	/**
	 * 到单日期
	 */
	private Date arriveDate;
	
	/**
	 * 承兑日期
	 */
	private Date acceptDate;
	
	/**
	 * 报关单号
	 */
	private String customsDeclarationNo;
	
	/**
	 * 报关单位
	 */
	private String customsDeclarationUnit;
	
	/**
	 * 放行日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date letDate;
	
	/**
	 * 到单商品
	 */
	private List<InvoiceGoods> invoiceGoods = new ArrayList<InvoiceGoods>();

	@Column(name = "INVOICE_NO", nullable = false, unique = true)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "ORIGINAL_NUM")
	public Integer getOriginalNum() {
		return originalNum;
	}

	public void setOriginalNum(Integer originalNum) {
		this.originalNum = originalNum;
	}

	@Column
	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column(name = "TRADE_COUNTRY")
	public String getTradeCountry() {
		return tradeCountry;
	}

	public void setTradeCountry(String tradeCountry) {
		this.tradeCountry = tradeCountry;
	}

	@Column(name = "PRICING_TERM")
	public String getPricingTerm() {
		return pricingTerm;
	}

	public void setPricingTerm(String pricingTerm) {
		this.pricingTerm = pricingTerm;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "RMB_RATE")
	public Double getRmbRate() {
		return rmbRate;
	}

	public void setRmbRate(Double rmbRate) {
		this.rmbRate = rmbRate;
	}

	@Column(name = "ORIGINAL_CURRENCY")
	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}
	
	@Column
	public String getRmb() {
		return rmb;
	}

	public void setRmb(String rmb) {
		this.rmb = rmb;
	}

	@Column(name = "INT_FARE")
	public String getIntFare() {
		return intFare;
	}

	public void setIntFare(String intFare) {
		this.intFare = intFare;
	}

	@Column(name = "INT_SA")
	public String getIntSA() {
		return intSA;
	}

	public void setIntSA(String intSA) {
		this.intSA = intSA;
	}

	@Column(name = "SAFETY_FACTOR")
	public String getSafetyFactor() {
		return safetyFactor;
	}

	public void setSafetyFactor(String safetyFactor) {
		this.safetyFactor = safetyFactor;
	}

	@Column(name = "INSURANCE_RATE")
	public String getInsuranceRate() {
		return insuranceRate;
	}

	public void setInsuranceRate(String insuranceRate) {
		this.insuranceRate = insuranceRate;
	}

	@Column(name = "TRANSPORT_PORT")
	public String getTransportPort() {
		return transportPort;
	}

	public void setTransportPort(String transportPort) {
		this.transportPort = transportPort;
	}

	@Column(name = "DESTINATION_PORT")
	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	@Column
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "CUS_DEC_COMPANY")
	public String getCusDecCompany() {
		return cusDecCompany;
	}

	public void setCusDecCompany(String cusDecCompany) {
		this.cusDecCompany = cusDecCompany;
	}

	@Column(name = "TRADE_WAY")
	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	@Column(name = "REV_ADV_PAYMENT")
	public Integer getRevAdvPayment() {
		return revAdvPayment;
	}

	public void setRevAdvPayment(Integer revAdvPayment) {
		this.revAdvPayment = revAdvPayment;
	}

	@Column(name = "INVENTORY_WAY")
	public String getInventoryWay() {
		return inventoryWay;
	}

	public void setInventoryWay(String inventoryWay) {
		this.inventoryWay = inventoryWay;
	}
	
	@Column(name = "SUBMIT_DATE")
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Column(name = "ARRIVE_DATE")
	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	@Column(name = "ACCEPT_DATE")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	@Column(name = "CUSTOMS_DECLARATION_NO")
	public String getCustomsDeclarationNo() {
		return customsDeclarationNo;
	}

	public void setCustomsDeclarationNo(String customsDeclarationNo) {
		this.customsDeclarationNo = customsDeclarationNo;
	}

	@Column(name = "CUSTOMS_DECLARATION_UNIT")
	public String getCustomsDeclarationUnit() {
		return customsDeclarationUnit;
	}

	public void setCustomsDeclarationUnit(String customsDeclarationUnit) {
		this.customsDeclarationUnit = customsDeclarationUnit;
	}

	@Column(name = "LET_DATE")
	public Date getLetDate() {
		return letDate;
	}

	public void setLetDate(Date letDate) {
		this.letDate = letDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<InvoiceGoods> getInvoiceGoods() {
		return invoiceGoods;
	}
	
	public void setInvoiceGoods(List<InvoiceGoods> invoiceGoods) {
		this.invoiceGoods = invoiceGoods;
	}
	
}
