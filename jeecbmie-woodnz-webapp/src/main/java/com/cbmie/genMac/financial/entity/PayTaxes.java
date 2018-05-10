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
 * 交税
 */
@Entity
@Table(name = "pay_taxes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PayTaxes extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 提单号
	 */
	private String invoiceNo;
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 提单总额
	 */
	private Double invoiceMoney;
	
	/**
	 * 税号
	 */
	private String taxNo;
	
	/**
	 * 税率
	 */
	private Double taxRate;
	
	/**
	 *  税金
	 */
	private Double taxMoney;
	
	/**
	 * 划税日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rowTaxDate;
	
	/**
	 * 收款单位
	 */
	private String receivingUnit;
	
	/**
	 * 账号
	 */
	private String account;
	
	/**
	 * 汇入行名称
	 */
	private String bank;
	
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

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "INVOICE_MONEY")
	public Double getInvoiceMoney() {
		return invoiceMoney;
	}

	public void setInvoiceMoney(Double invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}

	@Column(name = "TAX_NO")
	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	@Column(name = "TAX_RATE")
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name = "TAX_MONEY")
	public Double getTaxMoney() {
		return taxMoney;
	}

	public void setTaxMoney(Double taxMoney) {
		this.taxMoney = taxMoney;
	}

	@Column(name = "ROW_TAX_DATE")
	public Date getRowTaxDate() {
		return rowTaxDate;
	}

	public void setRowTaxDate(Date rowTaxDate) {
		this.rowTaxDate = rowTaxDate;
	}

	@Column(name = "RECEIVING_UNIT")
	public String getReceivingUnit() {
		return receivingUnit;
	}

	public void setReceivingUnit(String receivingUnit) {
		this.receivingUnit = receivingUnit;
	}

	@Column
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}