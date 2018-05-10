package com.cbmie.woodNZ.reportForm.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 上游到单log
 */
public class BillsReport {

	/** 提单号 **/
	private String billNo;
	/** 综合合同号 **/
	private String cpContractNo;
	/**
	 * 品名
	 */
	private String cateforyName;
	/**
	 * 上游合同号
	 */
	private String contractNo;
	/** 预计到港日期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date expectPortDate;
	/** 供货商 **/
	private String supplier;
	/** 目的港 **/
	private String portName;
	/** 集装箱数 **/
	private double containerNumber;
	/** 发票号 **/
	private String invoiceNo;
	/** 下游发票号 **/
	private String downInvoiceNo;
	/** 货物描述 **/
	private String goodsDesc;
	/** 发票金额 **/
	private double invoiceAmount;
	/** 交单日期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date giveBillsDate;
	public Date getGiveBillsDate() {
		return giveBillsDate;
	}

	public void setGiveBillsDate(Date giveBillsDate) {
		this.giveBillsDate = giveBillsDate;
	}

	/** 装船日期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date shipDate;
	/** 商品描述 **/
	private String spmc;
	/**
	 * 数量
	 */
	private String sl;
	/**
	 * 快递号
	 */
	private String expressNo;
	/**
	 * 下游客户
	 */
	private String downStreamClient;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCpContractNo() {
		return cpContractNo;
	}

	public void setCpContractNo(String cpContractNo) {
		this.cpContractNo = cpContractNo;
	}

	public String getCateforyName() {
		return cateforyName;
	}

	public void setCateforyName(String cateforyName) {
		this.cateforyName = cateforyName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getExpectPortDate() {
		return expectPortDate;
	}

	public void setExpectPortDate(Date expectPortDate) {
		this.expectPortDate = expectPortDate;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public double getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(double containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getDownInvoiceNo() {
		return downInvoiceNo;
	}

	public void setDownInvoiceNo(String downInvoiceNo) {
		this.downInvoiceNo = downInvoiceNo;
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public String getSpmc() {
		return spmc;
	}

	public void setSpmc(String spmc) {
		this.spmc = spmc;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getDownStreamClient() {
		return downStreamClient;
	}

	public void setDownStreamClient(String downStreamClient) {
		this.downStreamClient = downStreamClient;
	}
	
	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

}
