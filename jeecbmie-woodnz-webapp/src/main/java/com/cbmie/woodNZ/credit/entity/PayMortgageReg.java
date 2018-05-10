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
@Table(name = "WOOD_PAY_MORTGAGE_REG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PayMortgageReg extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 到单号
	 */
	private String woodBillId;

	/**
	 * 还汇日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date payMortgageTime;

	/**
	 * 押汇利率 - 外币
	 */
	private String mortgageInterestO;

	/**
	 * 押汇利率 - 人民币
	 */
	private String mortgageInterestRMB;

	/**
	 * 汇率
	 */
	private String exchangeRate;

	/**
	 * 本息合计
	 */
	private String allMoney;

	@Column
	public String getWoodBillId() {
		return woodBillId;
	}

	public void setWoodBillId(String woodBillId) {
		this.woodBillId = woodBillId;
	}

	@Column
	public Date getPayMortgageTime() {
		return payMortgageTime;
	}

	public void setPayMortgageTime(Date payMortgageTime) {
		this.payMortgageTime = payMortgageTime;
	}

	@Column
	public String getMortgageInterestO() {
		return mortgageInterestO;
	}

	public void setMortgageInterestO(String mortgageInterestO) {
		this.mortgageInterestO = mortgageInterestO;
	}

	@Column
	public String getMortgageInterestRMB() {
		return mortgageInterestRMB;
	}

	public void setMortgageInterestRMB(String mortgageInterestRMB) {
		this.mortgageInterestRMB = mortgageInterestRMB;
	}

	@Column
	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Column
	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

}
