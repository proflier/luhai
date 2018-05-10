package com.cbmie.genMac.financial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 费用
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Payment extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 单据号
	 */
	private String documentNO;

	/**
	 * 付款子类型
	 */
	private String paymentChildType;

	/**
	 * 费用认定方式
	 */
	private String expenseIdentifyWay;


	/**
	 * 进口合同
	 */
	private String importContract;

	/**
	 * 费用性质
	 */
	private String expenseNature;


	/**
	 * 结算客户
	 */
	private String billingCustomer;


	/**
	 * 原币金额
	 */
	private String originalCurrency;

	/**
	 * 部门人员
	 */
	private String departmentStaff;
	
	/**
	 * 代垫客户
	 */
	private String prepayCustomer;
	
	/**
	 * 父id
	 */
	private Long parentId;
	
	@Column(name = "PARENT_ID", nullable = false)
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "DOCUMENT_NO", nullable = true)
	public String getDocumentNO() {
		return documentNO;
	}

	public void setDocumentNO(String documentNO) {
		this.documentNO = documentNO;
	}

	@Column(name = "PAYMENT_CHILD_TYPE", nullable = false)
	public String getPaymentChildType() {
		return paymentChildType;
	}

	public void setPaymentChildType(String paymentChildType) {
		this.paymentChildType = paymentChildType;
	}

	@Column(name = "EXPENSE_IDENTIFY_WAY", nullable = true)
	public String getExpenseIdentifyWay() {
		return expenseIdentifyWay;
	}

	public void setExpenseIdentifyWay(String expenseIdentifyWay) {
		this.expenseIdentifyWay = expenseIdentifyWay;
	}

	@Column(name = "IMPORT_CONTRACT", nullable = false)
	public String getImportContract() {
		return importContract;
	}

	public void setImportContract(String importContract) {
		this.importContract = importContract;
	}

	@Column(name = "EXPENSE_NATURE", nullable = false)
	public String getExpenseNature() {
		return expenseNature;
	}

	public void setExpenseNature(String expenseNature) {
		this.expenseNature = expenseNature;
	}


	@Column(name = "BILLING_CUSTOMER", nullable = false)
	public String getBillingCustomer() {
		return billingCustomer;
	}

	public void setBillingCustomer(String billingCustomer) {
		this.billingCustomer = billingCustomer;
	}

	@Column(name = "ORIGINAL_CURRENCY", nullable = false)
	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	@Column(name = "DEPARTMENT_STAFF", nullable = true)
	public String getDepartmentStaff() {
		return departmentStaff;
	}

	public void setDepartmentStaff(String departmentStaff) {
		this.departmentStaff = departmentStaff;
	}

	@Column(name = "PREPAY_CUSTOMER", nullable = false)
	public String getPrepayCustomer() {
		return prepayCustomer;
	}

	public void setPrepayCustomer(String prepayCustomer) {
		this.prepayCustomer = prepayCustomer;
	}
	
	
	
	
	
	

}