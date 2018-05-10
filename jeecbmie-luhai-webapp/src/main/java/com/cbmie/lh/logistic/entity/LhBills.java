package com.cbmie.lh.logistic.entity;

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
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 到单主表
 */
@Entity
@Table(name = "LH_BILLS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class LhBills extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 提单号 **/
	private String billNo;
	private String purchaseContractIds;
	/** 二次换单提单号(暂时去掉) **/
	private String twoTimeBillNo;
	/** 我司单位编码 **/
	private String ourUnit;
	/** 装运类别 **/
	private String shipmentType;
	/** 免堆期 **/
	private String freeDays;
	/**
	 * 副本到单日期
	 **/
	private Date copyToDate;
	/** 预计到港日期 **/
	private Date expectPortDate;
	/** 交单期 **/
	private Date giveBillsDate;
	/** 拟定货代 **/
	private String forwarder;
	/** 发货单位 **/
	private String sendUnit;
	/**
	 * 供货单位
	 */
	private String deliveryUnit;
	
	private String deliveryUnitView;
	
	@Transient
	public String getDeliveryUnitView() {
		deliveryUnitView = DictUtils.getCorpName(deliveryUnit);
		return deliveryUnitView;
	}

	public void setDeliveryUnitView(String deliveryUnitView) {
		this.deliveryUnitView = deliveryUnitView;
	}

	/**
	 * 订货单位
	 */
	private String orderUnit;

	/**
	 * 收货单位
	 */
	private String receivingUnit;
	/** 币种 **/
	private String currency;
	/** 目的港 **/
	private String portName;
	/** 数量 **/
	private double numbers;
	/** 数量单位 **/
	private String numberUnits;
	/** 提单总额 **/
	private double billTotalAmount;
	/** 船名 **/
	private String shipNo;
	
	private String shipNoView;
	@Transient
	public String getShipNoView() {
		shipNoView = DictUtils.getShipName(shipNo);
		return shipNoView;
	}

	public void setShipNoView(String shipNoView) {
		this.shipNoView = shipNoView;
	}

	/** 船名 **/
	private String shipName;
	/** 航次 (暂时去掉) **/
	private String voyage;
	/** 发票日期  (暂时去掉)**/
	private Date invoiceDate;
	/** 发票号 **/
	private String invoiceNo;
	/** 发票金额 **/
	private double invoiceAmount;
	/** 备注 **/
	private String remark;
	/** 装船日期 **/
	private Date shipDate;
	/** 价格条款 **/
	private String priceTerms;
	/** 船公司 **/
	private String shipCompany;

	private String returnJson;

	private String returnGoodsJson;
	/** 正本到单日期 **/
	private Date trueToDate;
	/**
	 * 确认状态  1 确认 
	 */
	private String confirm;
	/**
	 * 复核状态 1复核 0 未复核
	 */
	private String review="0";
	/** 校验机构 **/
	private String checkOrg;
	/** 校验标准 **/
	private String checkStandard;

	@Transient
	public String getReturnGoodsJson() {
		return returnGoodsJson;
	}

	public void setReturnGoodsJson(String returnGoodsJson) {
		this.returnGoodsJson = returnGoodsJson;
	}

	@Transient
	public String getReturnJson() {
		return returnJson;
	}

	public void setReturnJson(String returnJson) {
		this.returnJson = returnJson;
	}

	/**
	 * 提单子表- 货物明细
	 */
	@JsonIgnore
	private List<LhBillsGoods> goodsList = new ArrayList<LhBillsGoods>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<LhBillsGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<LhBillsGoods> goodsList) {
		this.goodsList = goodsList;
	}


	@Column
	public Date getTrueToDate() {
		return trueToDate;
	}

	public void setTrueToDate(Date trueToDate) {
		this.trueToDate = trueToDate;
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
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
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
	public Date getGiveBillsDate() {
		return giveBillsDate;
	}

	public void setGiveBillsDate(Date giveBillsDate) {
		this.giveBillsDate = giveBillsDate;
	}
	
	
	@Column
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getOurUnit() {
		return ourUnit;
	}

	public void setOurUnit(String ourUnit) {
		this.ourUnit = ourUnit;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public String getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}

	public String getReceivingUnit() {
		return receivingUnit;
	}

	public void setReceivingUnit(String receivingUnit) {
		this.receivingUnit = receivingUnit;
	}

	public String getPurchaseContractIds() {
		return purchaseContractIds;
	}

	public void setPurchaseContractIds(String purchaseContractIds) {
		this.purchaseContractIds = purchaseContractIds;
	}

	public String getSendUnit() {
		return sendUnit;
	}

	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}

	public String getFreeDays() {
		return freeDays;
	}

	public void setFreeDays(String freeDays) {
		this.freeDays = freeDays;
	}

	public String getCheckOrg() {
		return checkOrg;
	}

	public void setCheckOrg(String checkOrg) {
		this.checkOrg = checkOrg;
	}

	public String getCheckStandard() {
		return checkStandard;
	}

	public void setCheckStandard(String checkStandard) {
		this.checkStandard = checkStandard;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
}
