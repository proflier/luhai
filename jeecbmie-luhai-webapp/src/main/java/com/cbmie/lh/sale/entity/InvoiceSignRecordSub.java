package com.cbmie.lh.sale.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

@Entity
@Table(name = "LH_INVOICE_SIGNRECORDSUB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InvoiceSignRecordSub extends BaseEntity{
	/**数量*/
	private Double quantity;
	/**单价*/
	private Double unitPrice;
	/**价税合计*/
	private Double totalPrice;
	/**发票号码*/
	private String invoiceNo;
	/**发票份数*/
	private Integer invoiceCopies;
	/**备注*/
	private String remarks;
	
	private Long invoiceSignRecordId;

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Integer getInvoiceCopies() {
		return invoiceCopies;
	}

	public void setInvoiceCopies(Integer invoiceCopies) {
		this.invoiceCopies = invoiceCopies;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getInvoiceSignRecordId() {
		return invoiceSignRecordId;
	}

	public void setInvoiceSignRecordId(Long invoiceSignRecordId) {
		this.invoiceSignRecordId = invoiceSignRecordId;
	}
}
