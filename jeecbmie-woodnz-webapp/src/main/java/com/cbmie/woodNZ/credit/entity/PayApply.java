package com.cbmie.woodNZ.credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 付款申请
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "WOOD_PAY_APPLY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PayApply extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 对应采购合同
	 */
	private String contractNo;
	
	/**
	 * 开征申请号
	 */
	private String payApplyNo;

	/**
	 * 综合合同号
	 */
	private String inteContractNo;

	/**
	 * 开证金额
	 */
	private String applyMoney;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 溢短装
	 */
	private String moreOrLess;

	/**
	 * 汇率
	 */
	private String exchangeRate;

	/**
	 * 保证金
	 */
	private String handelRecognizance;

	/**
	 * 已到账保证金
	 */
	private String arrivalRecognizance;

	/**
	 * 保证金币种
	 */
	private String recognizanceCurrency;

	/**
	 * 期望银行
	 */
	private String bankExpects;

	/**
	 * 收款方式
	 */
	private String paymenMethod;

	/**
	 * 授信类型
	 */
	private String creditType;

	/**
	 * 使用时间
	 */
	private String usageTime;

	/**
	 * 我司单位
	 */
	private String ourUnit;

	/**
	 * 供应商
	 */
	private String supplier;

	/**
	 * 进口国别
	 */
	private String importingCountry;

	/**
	 * 国内客户
	 */
	private String internalClient;

	/**
	 * 经营方式
	 */
	private String runMode;

	/**
	 * 预计付款时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date forecastPayTime;

	/**
	 * 利润情况
	 */
	private String profits;

	/**
	 * 授信额度使用情况
	 */
	private String creditState;

	/**
	 * 客户开证情况
	 */
	private String openCreditState;

	/**
	 * 保证金情况说明
	 */
	private String recognizanceDirections;

	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 状态
	 */
	private String state = "草稿";



	@Column
	public String getInteContractNo() {
		return inteContractNo;
	}

	public void setInteContractNo(String inteContractNo) {
		this.inteContractNo = inteContractNo;
	}


	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getPayApplyNo() {
		return payApplyNo;
	}

	public void setPayApplyNo(String payApplyNo) {
		this.payApplyNo = payApplyNo;
	}
	
	@Column
	public String getApplyMoney() {
		return applyMoney;
	}

	public void setApplyMoney(String applyMoney) {
		this.applyMoney = applyMoney;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public String getMoreOrLess() {
		return moreOrLess;
	}

	public void setMoreOrLess(String moreOrLess) {
		this.moreOrLess = moreOrLess;
	}

	@Column
	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column
	public String getHandelRecognizance() {
		return handelRecognizance;
	}

	public void setHandelRecognizance(String handelRecognizance) {
		this.handelRecognizance = handelRecognizance;
	}

	@Column
	public String getArrivalRecognizance() {
		return arrivalRecognizance;
	}

	public void setArrivalRecognizance(String arrivalRecognizance) {
		this.arrivalRecognizance = arrivalRecognizance;
	}

	@Column
	public String getRecognizanceCurrency() {
		return recognizanceCurrency;
	}

	public void setRecognizanceCurrency(String recognizanceCurrency) {
		this.recognizanceCurrency = recognizanceCurrency;
	}

	@Column
	public String getBankExpects() {
		return bankExpects;
	}

	public void setBankExpects(String bankExpects) {
		this.bankExpects = bankExpects;
	}

	@Column
	public String getPaymenMethod() {
		return paymenMethod;
	}

	public void setPaymenMethod(String paymenMethod) {
		this.paymenMethod = paymenMethod;
	}

	@Column
	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	@Column
	public String getUsageTime() {
		return usageTime;
	}

	public void setUsageTime(String usageTime) {
		this.usageTime = usageTime;
	}

	@Column
	public String getOurUnit() {
		return ourUnit;
	}

	public void setOurUnit(String ourUnit) {
		this.ourUnit = ourUnit;
	}

	@Column
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column
	public String getImportingCountry() {
		return importingCountry;
	}

	public void setImportingCountry(String importingCountry) {
		this.importingCountry = importingCountry;
	}

	@Column
	public String getInternalClient() {
		return internalClient;
	}

	public void setInternalClient(String internalClient) {
		this.internalClient = internalClient;
	}

	@Column
	public String getRunMode() {
		return runMode;
	}

	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	@Column
	public Date getForecastPayTime() {
		return forecastPayTime;
	}

	public void setForecastPayTime(Date forecastPayTime) {
		this.forecastPayTime = forecastPayTime;
	}

	@Column
	public String getProfits() {
		return profits;
	}

	public void setProfits(String profits) {
		this.profits = profits;
	}

	@Column
	public String getCreditState() {
		return creditState;
	}

	public void setCreditState(String creditState) {
		this.creditState = creditState;
	}

	@Column
	public String getOpenCreditState() {
		return openCreditState;
	}

	public void setOpenCreditState(String openCreditState) {
		this.openCreditState = openCreditState;
	}

	@Column
	public String getRecognizanceDirections() {
		return recognizanceDirections;
	}

	public void setRecognizanceDirections(String recognizanceDirections) {
		this.recognizanceDirections = recognizanceDirections;
	}

	@Column
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
