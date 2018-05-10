package com.cbmie.genMac.financial.entity;

import java.io.Serializable;
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
@Entity
@Table(name = "wood_sale_invoice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleInvoice extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1680681837593817363L;
	
	/**抬头*/
	private String title;
	/**发票日期*/
	private Date invoiceDate;
	/**发票号*/
	private String invoideNo;
	/**下游名称地址*/
	private String downAddress;
	/**合同号*/
	private String contractNo;
	/**装运港*/
	private String srcPort;
	/**目的港*/
	private String destPort;
	/**交易模式*/
	private String transType;
	/**装货时间*/
	private Date goodsDate;
	/**提单号*/
	private String billNo;
	
	List<SaleInvoiceSub> subs = new ArrayList<SaleInvoiceSub>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="INVOICEDATE")
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	@Column(name="INVOIDENO")
	public String getInvoideNo() {
		return invoideNo;
	}
	public void setInvoideNo(String invoideNo) {
		this.invoideNo = invoideNo;
	}
	@Column(name="DOWNADDRESS")
	public String getDownAddress() {
		return downAddress;
	}
	public void setDownAddress(String downAddress) {
		this.downAddress = downAddress;
	}
	@Column(name="CONTRACTNO")
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column(name="SRCPORT")
	public String getSrcPort() {
		return srcPort;
	}
	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}
	@Column(name="DESTPORT")
	public String getDestPort() {
		return destPort;
	}
	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}
	@Column(name="TRANSTYPE")
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Column(name="BILLNO")
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<SaleInvoiceSub> getSubs() {
		return subs;
	}
	public void setSubs(List<SaleInvoiceSub> subs) {
		this.subs = subs;
	}
	@Column(name="GOODSDATE")
	public Date getGoodsDate() {
		return goodsDate;
	}
	public void setGoodsDate(Date goodsDate) {
		this.goodsDate = goodsDate;
	}
	

}
