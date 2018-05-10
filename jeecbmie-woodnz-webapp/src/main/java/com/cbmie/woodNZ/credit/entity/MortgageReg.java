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
 * 押汇登记
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "WOOD_MORTGAGE_REG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class MortgageReg extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 到单号
	 */
	private String woodBillId;

	/**
	 * 信用证号
	 */
	private String creditNo;

	/**
	 * 财务编号
	 */
	private String inteContractNo;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 银行编号
	 */
	private String bankNo;

	/**
	 * 授信类型
	 */
	private String creditType;

	/**
	 * 押汇金额
	 */
	private String mortgageMoney;

	/**
	 * 押汇币种
	 */
	private String mortgageCurrency;

	/**
	 * 汇率
	 */
	private String exchangeRate;

	/**
	 * 押汇日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date mortgageTime;

	/**
	 * 是否最后一次押汇
	 */
	private String lastMortgage;

	/**
	 * 预计还押汇日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date payMortgageTime;
	
	/**
	 * 实际还押汇日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date payMortgageTimeReal;

	@Column
	public Date getPayMortgageTimeReal() {
		return payMortgageTimeReal;
	}

	public void setPayMortgageTimeReal(Date payMortgageTimeReal) {
		this.payMortgageTimeReal = payMortgageTimeReal;
	}

	/**
	 * 押汇利率
	 */
	private String mortgageRate;

	/**
	 * 人民币金额
	 */
	private String rmbMoney;

	@Column
	public String getWoodBillId() {
		return woodBillId;
	}

	public void setWoodBillId(String woodBillId) {
		this.woodBillId = woodBillId;
	}

	@Column
	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	@Column
	public String getInteContractNo() {
		return inteContractNo;
	}

	public void setInteContractNo(String inteContractNo) {
		this.inteContractNo = inteContractNo;
	}

	@Column
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	@Column
	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	@Column
	public String getMortgageMoney() {
		return mortgageMoney;
	}

	public void setMortgageMoney(String mortgageMoney) {
		this.mortgageMoney = mortgageMoney;
	}

	@Column
	public String getMortgageCurrency() {
		return mortgageCurrency;
	}

	public void setMortgageCurrency(String mortgageCurrency) {
		this.mortgageCurrency = mortgageCurrency;
	}

	@Column
	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column
	public Date getMortgageTime() {
		return mortgageTime;
	}

	public void setMortgageTime(Date mortgageTime) {
		this.mortgageTime = mortgageTime;
	}

	@Column
	public String getLastMortgage() {
		return lastMortgage;
	}

	public void setLastMortgage(String lastMortgage) {
		this.lastMortgage = lastMortgage;
	}

	@Column
	public Date getPayMortgageTime() {
		return payMortgageTime;
	}

	public void setPayMortgageTime(Date payMortgageTime) {
		this.payMortgageTime = payMortgageTime;
	}

	@Column
	public String getMortgageRate() {
		return mortgageRate;
	}

	public void setMortgageRate(String mortgageRate) {
		this.mortgageRate = mortgageRate;
	}

	@Column
	public String getRmbMoney() {
		return rmbMoney;
	}

	public void setRmbMoney(String rmbMoney) {
		this.rmbMoney = rmbMoney;
	}

}
