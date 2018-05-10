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
 * 付款登记
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "WOOD_PAY_REG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PayReg extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 对应进口合同号
	 */
	private String contractNo;

	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 对应付款申请号
	 */
	private String payApplyNo;

	@Column
	public String getPayApplyNo() {
		return payApplyNo;
	}

	public void setPayApplyNo(String payApplyNo) {
		this.payApplyNo = payApplyNo;
	}

	/**
	 * 信用证号
	 */
	private String creditNo;

	/**
	 * 综合合同号
	 */
	private String inteContractNo;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 我司单位
	 */
	private String ourUnit;

	/**
	 * 收款方式
	 */
	private String paymenMethod;

	/**
	 * 授信类型
	 */
	private String creditType;

	/**
	 * 开证金额
	 */
	private String creditMoney;

	/**
	 * 汇率
	 */
	private String exchangeRate;

	@Column
	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 开证币种
	 */
	private String creditCurrency;

	/**
	 * 开证时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date regTime;
	/**
	 * 通知行
	 */
	private String noticeBank;
	/**
	 * 最晚装船期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date latestShipTime;
	/**
	 * 信用证有效期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date regValidity;

	private String state = "已登记";

	/**** 开证费登记字段 ****/

	/**
	 * 付费时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date payTime;

	/**
	 * 开证费率
	 */
	private String payApplyRate;

	/**
	 * 外币金额
	 */
	private String moneyOut;

	/**
	 * 人民币金额
	 */
	private String moneyRMB;

	/**
	 * 信用证类型
	 */
	private String lcCategory;

	/**
	 * 开证费币种
	 */
	private String feeCurrency;

	@Column
	public String getFeeCurrency() {
		return feeCurrency;
	}

	@Column
	public void setFeeCurrency(String feeCurrency) {
		this.feeCurrency = feeCurrency;
	}

	@Column
	public String getLcCategory() {
		return lcCategory;
	}

	@Column
	public void setLcCategory(String lcCategory) {
		this.lcCategory = lcCategory;
	}

	@Column
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Column
	public String getPayApplyRate() {
		return payApplyRate;
	}

	public void setPayApplyRate(String payApplyRate) {
		this.payApplyRate = payApplyRate;
	}

	@Column
	public String getMoneyOut() {
		return moneyOut;
	}

	public void setMoneyOut(String moneyOut) {
		this.moneyOut = moneyOut;
	}

	@Column
	public String getMoneyRMB() {
		return moneyRMB;
	}

	public void setMoneyRMB(String moneyRMB) {
		this.moneyRMB = moneyRMB;
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
	public String getOurUnit() {
		return ourUnit;
	}

	public void setOurUnit(String ourUnit) {
		this.ourUnit = ourUnit;
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
	public String getCreditMoney() {
		return creditMoney;
	}

	public void setCreditMoney(String creditMoney) {
		this.creditMoney = creditMoney;
	}

	@Column
	public String getCreditCurrency() {
		return creditCurrency;
	}

	public void setCreditCurrency(String creditCurrency) {
		this.creditCurrency = creditCurrency;
	}

	@Column
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column
	public String getNoticeBank() {
		return noticeBank;
	}

	public void setNoticeBank(String noticeBank) {
		this.noticeBank = noticeBank;
	}

	@Column
	public Date getLatestShipTime() {
		return latestShipTime;
	}

	public void setLatestShipTime(Date latestShipTime) {
		this.latestShipTime = latestShipTime;
	}

	@Column
	public Date getRegValidity() {
		return regValidity;
	}

	public void setRegValidity(Date regValidity) {
		this.regValidity = regValidity;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
