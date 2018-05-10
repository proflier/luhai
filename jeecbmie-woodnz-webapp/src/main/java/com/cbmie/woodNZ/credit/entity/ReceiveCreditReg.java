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
 * 信用证收证登记
 * 
 * @author linxiaopeng 2016年6月28日
 */
@Entity
@Table(name = "WOOD_RECEIVE_CREDIT_REG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ReceiveCreditReg extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 到单号
	 */
	private String woodBillId;

	/**
	 * 下游信用证号
	 */
	private String creditNo;

	/**
	 * 综合合同号
	 */
	private String inteContractNo;

	/**
	 * 授信类型
	 */
	private String creditType;

	/**
	 * 开证日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date creditTime;

	/**
	 * 登记日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date regTime;

	/**
	 * 开证公司
	 */
	private String creditCompany;

	/**
	 * 有效期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date effictTime;

	/**
	 * 最晚装船期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date lastShipTime;

	/**
	 * 交单期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date billTime;

	/**
	 * 开证银行
	 */
	private String issuingBank;

	@Column
	public String getIssuingBank() {
		return issuingBank;
	}

	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}

	/**
	 * 收证银行
	 */
	private String receiveBank;

	@Column
	public String getReceiveBank() {
		return receiveBank;
	}

	public void setReceiveBank(String receiveBank) {
		this.receiveBank = receiveBank;
	}

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
	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	@Column
	public Date getCreditTime() {
		return creditTime;
	}

	public void setCreditTime(Date creditTime) {
		this.creditTime = creditTime;
	}

	@Column
	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column
	public String getCreditCompany() {
		return creditCompany;
	}

	public void setCreditCompany(String creditCompany) {
		this.creditCompany = creditCompany;
	}

	@Column
	public Date getEffictTime() {
		return effictTime;
	}

	public void setEffictTime(Date effictTime) {
		this.effictTime = effictTime;
	}

	@Column
	public Date getLastShipTime() {
		return lastShipTime;
	}

	public void setLastShipTime(Date lastShipTime) {
		this.lastShipTime = lastShipTime;
	}

	@Column
	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

}
