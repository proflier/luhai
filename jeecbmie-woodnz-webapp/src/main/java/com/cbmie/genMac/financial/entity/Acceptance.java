package com.cbmie.genMac.financial.entity;

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
 * 承兑
 */
@Entity
@Table(name = "acceptance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Acceptance extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 提单号
	 */
	private String invoiceNo;
	
	/**
	 * 信用证号
	 */
	private String creditNo;
	
	/**
	 * 银行名称
	 */
	private String bank;
	
	/**
	 * 付款期限
	 */
	private String prompt;
	
	/**
	 * 押汇金额
	 */
	private Double documentaryBillsMoney;
	
	/**
	 * 押汇币种
	 */
	private String documentaryBillsCurrency;
	
	/**
	 * 汇率
	 */
	private Double rate;
	
	/**
	 * 押汇日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date documentaryBillsDate;
	
	/**
	 * 是否最后一次押汇
	 */
	private int documentaryBillsLast;
	
	/**
	 * 预计还押汇日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date documentaryBillsExpectBackTime;
	
	/**
	 * 实际还押汇日期
	 */
	private Date documentaryBillsRealityBackTime;
	
	/**
	 * 押汇利率
	 */
	private Double documentaryBillsRate;
	
	/**
	 * 人民币金额
	 */
	private Double rmb;
	
	/**
	 * 备注
	 */
	private String comment;
	
	/**
	 * 状态
	 */
	private String state = "草稿";

	@Column(name = "INVOICE_NO", nullable = false)
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "CREDIT_NO", nullable = false)
	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	@Column
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column
	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Column(name = "DOCUMENTARY_BILLS_MONEY", nullable = false)
	public Double getDocumentaryBillsMoney() {
		return documentaryBillsMoney;
	}

	public void setDocumentaryBillsMoney(Double documentaryBillsMoney) {
		this.documentaryBillsMoney = documentaryBillsMoney;
	}

	@Column(name = "DOCUMENTARY_BILLS_CURRENCY", nullable = false)
	public String getDocumentaryBillsCurrency() {
		return documentaryBillsCurrency;
	}

	public void setDocumentaryBillsCurrency(String documentaryBillsCurrency) {
		this.documentaryBillsCurrency = documentaryBillsCurrency;
	}

	@Column(nullable = false)
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "DOCUMENTARY_BILLS_DATE", nullable = false)
	public Date getDocumentaryBillsDate() {
		return documentaryBillsDate;
	}

	public void setDocumentaryBillsDate(Date documentaryBillsDate) {
		this.documentaryBillsDate = documentaryBillsDate;
	}

	@Column(name = "DOCUMENTARY_BILLS_LAST", nullable = false)
	public int getDocumentaryBillsLast() {
		return documentaryBillsLast;
	}

	public void setDocumentaryBillsLast(int documentaryBillsLast) {
		this.documentaryBillsLast = documentaryBillsLast;
	}

	@Column(name = "DOCUMENTARY_BILLS_EXPECT_BACK_TIME", nullable = false)
	public Date getDocumentaryBillsExpectBackTime() {
		return documentaryBillsExpectBackTime;
	}

	public void setDocumentaryBillsExpectBackTime(Date documentaryBillsExpectBackTime) {
		this.documentaryBillsExpectBackTime = documentaryBillsExpectBackTime;
	}

	@Column(name = "DOCUMENTARY_BILLS_REALITY_BACK_TIME")
	public Date getDocumentaryBillsRealityBackTime() {
		return documentaryBillsRealityBackTime;
	}

	public void setDocumentaryBillsRealityBackTime(Date documentaryBillsRealityBackTime) {
		this.documentaryBillsRealityBackTime = documentaryBillsRealityBackTime;
	}

	@Column(name = "DOCUMENTARY_BILLS_RATE", nullable = false)
	public Double getDocumentaryBillsRate() {
		return documentaryBillsRate;
	}

	public void setDocumentaryBillsRate(Double documentaryBillsRate) {
		this.documentaryBillsRate = documentaryBillsRate;
	}

	@Column(nullable = false)
	public Double getRmb() {
		return rmb;
	}

	public void setRmb(Double rmb) {
		this.rmb = rmb;
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