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
 * 流水
 */
@Entity
@Table(name = "LH_SERIAL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Serial extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	private String serialNumber;

	/**
	 * 日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date serialDate;

	/**
	 * 银行
	 */
	private String bank;

	/**
	 * 银行账号
	 */
	private String bankNO;

	/**
	 * 水单抬头
	 */
	private String serialTitle;
	
	private String serialTitleView;

	@Transient
	public String getSerialTitleView() {
		serialTitleView = DictUtils.getCorpName(serialTitle);
		return serialTitleView;
	}

	public void setSerialTitleView(String serialTitleView) {
		this.serialTitleView = serialTitleView;
	}

	/**
	 * 资金类别
	 */
	private String fundCategory;
	
	private String fundCategoryView;

	@Transient
	public String getFundCategoryView() {
		fundCategoryView = DictUtils.getDictSingleName(fundCategory);
		return fundCategoryView;
	}

	public void setFundCategoryView(String fundCategoryView) {
		this.fundCategoryView = fundCategoryView;
	}

	/**
	 * 银承到期日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date bankDeadline;

	/**
	 * 金额
	 */
	private Double money;

	/**
	 * 资金来源
	 */
	private String fundSource;

	/**
	 * 合同号
	 */
	private String contractNo;

	/**
	 * 认领人备注
	 */
	private String comments;

	/**
	 * 水单类型
	 */
	private String serialCategory;

	/**
	 * 拆分状态
	 */
	private String splitStatus = "default";
	/**
	 * 发票号
	 */
	private String invoiceNo;

	/**
	 * 认领人
	 */
	private String claimPerson;

	/**
	 * 认领时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date claimDate;

	public String getClaimPerson() {
		return claimPerson;
	}

	public void setClaimPerson(String claimPerson) {
		this.claimPerson = claimPerson;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	@Column
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "SPLIT_STATUS", nullable = true)
	public String getSplitStatus() {
		return splitStatus;
	}

	public void setSplitStatus(String splitStatus) {
		this.splitStatus = splitStatus;
	}

	@Column(name = "SERIAL_CATEGORY", nullable = true)
	public String getSerialCategory() {
		return serialCategory;
	}

	public void setSerialCategory(String serialCategory) {
		this.serialCategory = serialCategory;
	}

	@Column(name = "SERIAL_NUMBER", nullable = false)
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "SERIAL_DATE", nullable = false)
	public Date getSerialDate() {
		return serialDate;
	}

	public void setSerialDate(Date serialDate) {
		this.serialDate = serialDate;
	}

	@Column(name = "BANK", nullable = false)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "BANK_NO", nullable = false)
	public String getBankNO() {
		return bankNO;
	}

	public void setBankNO(String bankNO) {
		this.bankNO = bankNO;
	}

	@Column(name = "SERIAL_TITLE", nullable = false)
	public String getSerialTitle() {
		return serialTitle;
	}

	public void setSerialTitle(String serialTitle) {
		this.serialTitle = serialTitle;
	}

	@Column(name = "FUND_CATEGORY", nullable = false)
	public String getFundCategory() {
		return fundCategory;
	}

	public void setFundCategory(String fundCategory) {
		this.fundCategory = fundCategory;
	}

	@Column(name = "BANK_DEADLINE", nullable = false)
	public Date getBankDeadline() {
		return bankDeadline;
	}

	public void setBankDeadline(Date bankDeadline) {
		this.bankDeadline = bankDeadline;
	}

	@Column(name = "MONEY", nullable = false)
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "FUND_SOURCE", nullable = false)
	public String getFundSource() {
		return fundSource;
	}

	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	@Column(name = "CONTRACT_NO", nullable = true)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "COMMENTS", nullable = true)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}