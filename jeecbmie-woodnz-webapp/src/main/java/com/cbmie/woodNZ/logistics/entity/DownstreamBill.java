package com.cbmie.woodNZ.logistics.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 下游交单
 */
@Entity
@Table(name = "WOOD_DOWNSTREAM_BILL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class DownstreamBill extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 提单号 **/
	private String billNo;
	/** 综合合同号 **/
	private String cpContractNo;
	/** 二次换单提单号 **/
	private String twoTimeBillNo;
	/** 我司单位 **/
	private String ourUnit;
	/** 我司单位编码（财务） **/
	private String unitCode;
	/** 装运类别 **/
	private String shipmentType;
	/**
	 * 副本到单日期
	 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date copyToDate;
	/** 预计到港日期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date expectPortDate;
	/** 交单期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date giveBillsDate;
	/** 拟定货代 **/
	private String forwarder;
	/** 供货商 **/
	private String supplier;
	/** 供应商编码 **/
	private String supplierNo;
	/** 供应商编码（财务） **/
	private String supplierNoF;
	/** 币种 **/
	private String currency;
	/** 目的港 **/
	private String portName;
	/** 集装箱数 **/
	private double containerNumber;
	/** 数量 **/
	private double numbers;
	/** 数量单位 **/
	private String numberUnits;
	/** 总件数 **/
	private double totalNumber;
	/** 提单总额 **/
	private double billTotalAmount;
	/** 船名 **/
	private String shipName;
	/** 航次 **/
	private String voyage;
	/** 总件数/跟数 **/
	private String totalP;
	/** 发票日期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date invoiceDate;
	/** 发票号 **/
	private String invoiceNo;
	/** 下游发票号 **/
	private String downInvoiceNo;
	
	@Column
	public String getDownInvoiceNo() {
		return downInvoiceNo;
	}

	public void setDownInvoiceNo(String downInvoiceNo) {
		this.downInvoiceNo = downInvoiceNo;
	}

	/** 发票金额 **/
	private double invoiceAmount;
	/** 合同到单次数 **/
	private String billNumber;
	/** 备注 **/
	private String remark;
	/** 免箱期天数 **/
	private int noBoxDay;
	/** 装船日期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date shipDate;
	/** 商品描述 **/
	private String goodsD;
	/** 原产地证 **/
	private boolean addrCer;
	// private String ycdzNote;
	/** 质检证 **/
	private boolean qualityCer;
	// private String zjzNote;
	/** 价格条款 **/
	private String priceTerms;
	/** 船公司 **/
	private String shipCompany;
	/** 排序号 **/
	private Integer orderNo;
	/** 地区 **/
	private String area;
	/** 货代包干费用单价 **/
	private double forwarderPrice;

	/**
	 * 快递号
	 */
	private String expressNo;

	/**
	 * 快递日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date expressDate;

	/**
	 * 下游客户
	 */
	private String downStreamClient;

	@Column
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	@Column
	public Date getExpressDate() {
		return expressDate;
	}

	public void setExpressDate(Date expressDate) {
		this.expressDate = expressDate;
	}

	@Column
	public String getDownStreamClient() {
		return downStreamClient;
	}

	public void setDownStreamClient(String downStreamClient) {
		this.downStreamClient = downStreamClient;
	}

	/**
	 * 关联销售合同ids
	 */
	private String relationSaleContarte;

	@Column
	public String getRelationSaleContarte() {
		return relationSaleContarte;
	}

	public void setRelationSaleContarte(String relationSaleContarte) {
		this.relationSaleContarte = relationSaleContarte;
	}
	
	/**
	 * 关联销售合同No
	 */
	private String saleContarteNo;
	
	@Column
	public String getSaleContarteNo() {
		return saleContarteNo;
	}

	public void setSaleContarteNo(String saleContarteNo) {
		this.saleContarteNo = saleContarteNo;
	}

	private String returnJson;

	@Transient
	public String getReturnJson() {
		return returnJson;
	}

	public void setReturnJson(String returnJson) {
		this.returnJson = returnJson;
	}
	
	/**
	 * 确认状态
	 */
	private String confirm;
	
	@Column
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	

	/**
	 * 下游交单子表-箱单
	 */
	@JsonIgnore
	private List<DownstreamContainer> downstreamContainerList = new ArrayList<DownstreamContainer>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<DownstreamContainer> getDownstreamContainerList() {
		return downstreamContainerList;
	}

	public void setDownstreamContainerList(List<DownstreamContainer> downstreamContainerList) {
		this.downstreamContainerList = downstreamContainerList;
	}

	/**
	 * 下游交单子表- 货物明细
	 */
	@JsonIgnore
	private List<DownstreamGoods> downstreamGoodsList = new ArrayList<DownstreamGoods>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<DownstreamGoods> getDownstreamGoodsList() {
		return downstreamGoodsList;
	}

	public void setDownstreamGoodsList(List<DownstreamGoods> downstreamGoodsList) {
		this.downstreamGoodsList = downstreamGoodsList;
	}

	/** 正本到单日期 **/
	private Date trueToDate;

	@Column
	public Date getTrueToDate() {
		return trueToDate;
	}

	public void setTrueToDate(Date trueToDate) {
		this.trueToDate = trueToDate;
	}

	@Column
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@Column
	public String getTwoTimeBillNo() {
		return twoTimeBillNo;
	}

	public void setTwoTimeBillNo(String twoTimeBillNo) {
		this.twoTimeBillNo = twoTimeBillNo;
	}

	@Column
	public String getOurUnit() {
		return ourUnit;
	}

	public void setOurUnit(String ourUnit) {
		this.ourUnit = ourUnit;
	}

	@Column
	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	@Column
	public String getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	@Column
	public Date getCopyToDate() {
		return copyToDate;
	}

	public void setCopyToDate(Date copyToDate) {
		this.copyToDate = copyToDate;
	}

	@Column
	public Date getExpectPortDate() {
		return expectPortDate;
	}

	public void setExpectPortDate(Date expectPortDate) {
		this.expectPortDate = expectPortDate;
	}

	@Column
	public String getForwarder() {
		return forwarder;
	}

	public void setForwarder(String forwarder) {
		this.forwarder = forwarder;
	}

	@Column
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column
	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	@Column
	public String getSupplierNoF() {
		return supplierNoF;
	}

	public void setSupplierNoF(String supplierNoF) {
		this.supplierNoF = supplierNoF;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	@Column
	public double getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(double containerNumber) {
		this.containerNumber = containerNumber;
	}

	@Column
	public double getNumbers() {
		return numbers;
	}

	public void setNumbers(double numbers) {
		this.numbers = numbers;
	}

	@Column
	public String getNumberUnits() {
		return numberUnits;
	}

	public void setNumberUnits(String numberUnits) {
		this.numberUnits = numberUnits;
	}

	@Column
	public double getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(double totalNumber) {
		this.totalNumber = totalNumber;
	}

	@Column
	public double getBillTotalAmount() {
		return billTotalAmount;
	}

	public void setBillTotalAmount(double billTotalAmount) {
		this.billTotalAmount = billTotalAmount;
	}

	@Column
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column
	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	@Column
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Column
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column
	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	@Column
	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public int getNoBoxDay() {
		return noBoxDay;
	}

	public void setNoBoxDay(int noBoxDay) {
		this.noBoxDay = noBoxDay;
	}

	@Column
	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	@Column
	public String getGoodsD() {
		return goodsD;
	}

	public void setGoodsD(String goodsD) {
		this.goodsD = goodsD;
	}

	@Column
	public boolean isAddrCer() {
		return addrCer;
	}

	public void setAddrCer(boolean addrCer) {
		this.addrCer = addrCer;
	}

	@Column
	public boolean isQualityCer() {
		return qualityCer;
	}

	public void setQualityCer(boolean qualityCer) {
		this.qualityCer = qualityCer;
	}

	@Column
	public String getPriceTerms() {
		return priceTerms;
	}

	public void setPriceTerms(String priceTerms) {
		this.priceTerms = priceTerms;
	}

	@Column
	public String getShipCompany() {
		return shipCompany;
	}

	public void setShipCompany(String shipCompany) {
		this.shipCompany = shipCompany;
	}

	@Column
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column
	public String getCpContractNo() {
		return cpContractNo;
	}

	public void setCpContractNo(String cpContractNo) {
		this.cpContractNo = cpContractNo;
	}

	@Column
	public double getForwarderPrice() {
		return forwarderPrice;
	}

	public void setForwarderPrice(double forwarderPrice) {
		this.forwarderPrice = forwarderPrice;
	}

	@Column
	public String getTotalP() {
		return totalP;
	}

	public void setTotalP(String totalP) {
		this.totalP = totalP;
	}

	@Column
	public Date getGiveBillsDate() {
		return giveBillsDate;
	}

	public void setGiveBillsDate(Date giveBillsDate) {
		this.giveBillsDate = giveBillsDate;
	}
}
