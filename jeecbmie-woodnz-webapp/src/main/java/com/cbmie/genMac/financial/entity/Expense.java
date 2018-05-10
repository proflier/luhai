package com.cbmie.genMac.financial.entity;

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
 * 费用
 */
@Entity
@Table(name = "expense")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Expense extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 付款申请号
	 */
	private String expenseId;

	/**
	 * 业务员
	 */
	private String expenseClerk;

	/**
	 * 状态
	 */
	private String state = "草稿";

	/**
	 * 申请支付日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date applyPayDate;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 支付方式
	 */
	private String payModel;

	/**
	 * 收款方信息
	 */
	private String payeeInformation;

	/**
	 * 英文信息
	 */
	private String payeeEnName;

	/**
	 * 对方银行
	 */
	private String payeeBank;

	/**
	 * 对方账号
	 */
	private String payeeBankNo;

	/**
	 * 原币金额
	 */
	private String originalCurrency;

	/**
	 * 财务实付日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date payDate;

	/**
	 * 实付原币
	 */
	private String payCurrency;

	/**
	 * 未付金额
	 */
	private String oweCurrency;

	private List<Payment> paymentList = new ArrayList<Payment>();

	@Column(name = "EXPENSE_ID", nullable = false)
	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	@Column(name = "EXPENSE_CLERK", nullable = false)
	public String getExpenseClerk() {
		return expenseClerk;
	}

	public void setExpenseClerk(String expenseClerk) {
		this.expenseClerk = expenseClerk;
	}

	@Column(name = "STATE", nullable = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "APPLY_PAY_DATE", nullable = false)
	public Date getApplyPayDate() {
		return applyPayDate;
	}

	public void setApplyPayDate(Date applyPayDate) {
		this.applyPayDate = applyPayDate;
	}

	@Column(name = "CURRENCY", nullable = false)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "PAY_MODEL", nullable = false)
	public String getPayModel() {
		return payModel;
	}

	public void setPayModel(String payModel) {
		this.payModel = payModel;
	}

	@Column(name = "PAYEE_INFORMATION", nullable = false)
	public String getPayeeInformation() {
		return payeeInformation;
	}

	public void setPayeeInformation(String payeeInformation) {
		this.payeeInformation = payeeInformation;
	}

	@Column(name = "PAYEE_EN_NAME", nullable = false)
	public String getPayeeEnName() {
		return payeeEnName;
	}

	public void setPayeeEnName(String payeeEnName) {
		this.payeeEnName = payeeEnName;
	}

	@Column(name = "PAYEE_BANK", nullable = false)
	public String getPayeeBank() {
		return payeeBank;
	}

	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}

	@Column(name = "PAYEE_BANK_NO", nullable = false)
	public String getPayeeBankNo() {
		return payeeBankNo;
	}

	public void setPayeeBankNo(String payeeBankNo) {
		this.payeeBankNo = payeeBankNo;
	}

	@Column(name = "ORIGINAL_CURRENCY", nullable = true)
	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	@Column(name = "PAY_DATE", nullable = true)
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "PAY_CURRENCY", nullable = true)
	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}

	@Column(name = "OWE_CURRENCY", nullable = false)
	public String getOweCurrency() {
		return oweCurrency;
	}

	public void setOweCurrency(String oweCurrency) {
		this.oweCurrency = oweCurrency;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<Payment> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
	}
}