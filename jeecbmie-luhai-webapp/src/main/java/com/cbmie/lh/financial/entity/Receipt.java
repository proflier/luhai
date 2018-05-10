package com.cbmie.lh.financial.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 预收票
 */
@Entity
@Table(name = "LH_RECEIPT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Receipt extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 预收票编号
	 */
	private String receiptNo;

	/**
	 * 批次船名
	 */
	private String shipName;

	/**
	 * 供应商
	 */
	private String supply;
	private String supplyView;
	@Transient
	public String getSupplyView() {
		supplyView = DictUtils.getCorpName(supply);
		return supplyView;
	}

	public void setSupplyView(String supplyView) {
		this.supplyView = supplyView;
	}

	/**
	 * 所属月份
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date mouth;
	
	@Column(nullable = true)
	public Date getMouth() {
		return mouth;
	}

	public void setMouth(Date mouth) {
		this.mouth = mouth;
	}

	/**
	 * 开票期间
	 */
	private String receiptPeriod;

	/**
	 * 发票号
	 */
	private String invoiceNo;

	/**
	 * 信用证号
	 */
	private String creditNo;

	/**
	 * 煤种
	 */
	private String coalType;

	/**
	 * 目的港
	 */
	private String destPort;

	/**
	 * 入库数量
	 */
	private double inStockQuantity;

	/**
	 * 结算数量（上游到单）
	 */
	private double settleQuantity;

	/**
	 * 结算单价（上游到单）
	 */
	private double settleUnitPrice;

	/**
	 * 结算港口
	 */
	private String settlePort;

	/**
	 * 合同热值
	 */
	private double contractCalorific;

	/**
	 * 结算热值
	 */
	private double settleCalorific;

	/**
	 * 结算方式
	 */
	private String settleMode;

	/**
	 * 金额（上游到单）USD
	 */
	private double amountUSD;

	/**
	 * 开证日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date creditDate;

	/**
	 * 到单日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date billDate;

	/**
	 * 汇率（约定开票汇率）
	 */
	private double exchangeRate;

	/**
	 * 汇率调整金额(本船次实际差额)
	 */
	private double exchangeRateChange;

	/**
	 * 金额RMB 金额USD*汇率
	 */
	private double amountRMB;

	/**
	 * 进口关税 (报关中取)
	 */
	private double importTariff;

	/**
	 * 进口增值税(报关中取)
	 */
	private double importVAT;

	/**
	 * 保险费(国际采购部)
	 */
	private double insurance;

	/**
	 * 海运费（亏仓）
	 */
	private double shippingFee;

	/**
	 * 商检费
	 */
	private double inspectionFee;

	/**
	 * 商检进项金额
	 */
	private double inspectionEntryFee;

	/**
	 * 汇差合计
	 */
	private double differenceRate;

	/**
	 * 开证手续费
	 */
	private double creditApplyFee;

	/**
	 * 押汇利息
	 */
	private double mortgageInterest;

	/**
	 * 代理费
	 */
	private double agencyFee;

	/**
	 * 开票金额
	 */
	private double creditAmount;

	/**
	 * 暂付金额
	 */
	private double prepaidAmount;

	/**
	 * 应付金额
	 */
	private double payableAmount;

	@Column
	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column(nullable = true)
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column(nullable = true)
	public String getSupply() {
		return supply;
	}

	public void setSupply(String supply) {
		this.supply = supply;
	}

	@Column(nullable = true)
	public String getReceiptPeriod() {
		return receiptPeriod;
	}

	public void setReceiptPeriod(String receiptPeriod) {
		this.receiptPeriod = receiptPeriod;
	}

	@Column(nullable = true)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(nullable = true)
	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	@Column(nullable = true)
	public String getCoalType() {
		return coalType;
	}

	public void setCoalType(String coalType) {
		this.coalType = coalType;
	}

	@Column(nullable = true)
	public String getDestPort() {
		return destPort;
	}

	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	@Column(nullable = true)
	public double getInStockQuantity() {
		return inStockQuantity;
	}

	public void setInStockQuantity(double inStockQuantity) {
		this.inStockQuantity = inStockQuantity;
	}

	@Column(nullable = true)
	public double getSettleQuantity() {
		return settleQuantity;
	}

	public void setSettleQuantity(double settleQuantity) {
		this.settleQuantity = settleQuantity;
	}

	@Column(nullable = true)
	public double getSettleUnitPrice() {
		return settleUnitPrice;
	}

	public void setSettleUnitPrice(double settleUnitPrice) {
		this.settleUnitPrice = settleUnitPrice;
	}

	@Column(nullable = true)
	public String getSettlePort() {
		return settlePort;
	}

	public void setSettlePort(String settlePort) {
		this.settlePort = settlePort;
	}

	@Column(nullable = true)
	public double getContractCalorific() {
		return contractCalorific;
	}

	public void setContractCalorific(double contractCalorific) {
		this.contractCalorific = contractCalorific;
	}

	@Column(nullable = true)
	public double getSettleCalorific() {
		return settleCalorific;
	}

	public void setSettleCalorific(double settleCalorific) {
		this.settleCalorific = settleCalorific;
	}

	@Column(nullable = true)
	public String getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(String settleMode) {
		this.settleMode = settleMode;
	}

	@Column(nullable = true)
	public double getAmountUSD() {
		return amountUSD;
	}

	public void setAmountUSD(double amountUSD) {
		this.amountUSD = amountUSD;
	}

	@Column(nullable = true)
	public Date getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
	}

	@Column(nullable = true)
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	@Column(nullable = true)
	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column(nullable = true)
	public double getExchangeRateChange() {
		return exchangeRateChange;
	}

	public void setExchangeRateChange(double exchangeRateChange) {
		this.exchangeRateChange = exchangeRateChange;
	}

	@Column(nullable = true)
	public double getAmountRMB() {
		return amountRMB;
	}

	public void setAmountRMB(double amountRMB) {
		this.amountRMB = amountRMB;
	}

	@Column(nullable = true)
	public double getImportTariff() {
		return importTariff;
	}

	public void setImportTariff(double importTariff) {
		this.importTariff = importTariff;
	}

	@Column(nullable = true)
	public double getImportVAT() {
		return importVAT;
	}

	public void setImportVAT(double importVAT) {
		this.importVAT = importVAT;
	}

	@Column(nullable = true)
	public double getInsurance() {
		return insurance;
	}

	public void setInsurance(double insurance) {
		this.insurance = insurance;
	}

	@Column(nullable = true)
	public double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}

	@Column(nullable = true)
	public double getInspectionFee() {
		return inspectionFee;
	}

	public void setInspectionFee(double inspectionFee) {
		this.inspectionFee = inspectionFee;
	}

	@Column(nullable = true)
	public double getInspectionEntryFee() {
		return inspectionEntryFee;
	}

	public void setInspectionEntryFee(double inspectionEntryFee) {
		this.inspectionEntryFee = inspectionEntryFee;
	}

	@Column(nullable = true)
	public double getDifferenceRate() {
		return differenceRate;
	}

	public void setDifferenceRate(double differenceRate) {
		this.differenceRate = differenceRate;
	}

	@Column(nullable = true)
	public double getCreditApplyFee() {
		return creditApplyFee;
	}

	public void setCreditApplyFee(double creditApplyFee) {
		this.creditApplyFee = creditApplyFee;
	}

	@Column(nullable = true)
	public double getMortgageInterest() {
		return mortgageInterest;
	}

	public void setMortgageInterest(double mortgageInterest) {
		this.mortgageInterest = mortgageInterest;
	}

	@Column(nullable = true)
	public double getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(double agencyFee) {
		this.agencyFee = agencyFee;
	}

	@Column(nullable = true)
	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	@Column(nullable = true)
	public double getPrepaidAmount() {
		return prepaidAmount;
	}

	public void setPrepaidAmount(double prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}

	@Column(nullable = true)
	public double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}

}