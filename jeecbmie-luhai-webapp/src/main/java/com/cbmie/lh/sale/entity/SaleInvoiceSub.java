package com.cbmie.lh.sale.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name = "LH_SALE_INVOICE_SUB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleInvoiceSub extends BaseEntity {
	/**销售合同号 暂时废弃*/
	private String saleContractNo;
	/**开票日期*/
	private Date billDate;
	/**开票数量*/
	private Double billQuantity;
	/**开票单价*/
	private Double billPrice;
	/**开票金额*/
	private Double billMoney;
	
	private Long saleInvoiceId;
	
	public String getSaleContractNo() {
		return saleContractNo;
	}
	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Double getBillQuantity() {
		return billQuantity;
	}
	public void setBillQuantity(Double billQuantity) {
		this.billQuantity = billQuantity;
	}
	public Double getBillPrice() {
		return billPrice;
	}
	public void setBillPrice(Double billPrice) {
		this.billPrice = billPrice;
	}
	public Double getBillMoney() {
		return billMoney;
	}
	public void setBillMoney(Double billMoney) {
		this.billMoney = billMoney;
	}
	public Long getSaleInvoiceId() {
		return saleInvoiceId;
	}
	public void setSaleInvoiceId(Long saleInvoiceId) {
		this.saleInvoiceId = saleInvoiceId;
	}
	
}
