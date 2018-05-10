package com.cbmie.lh.stock.entity;

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
import com.cbmie.lh.sale.entity.SaleDeliveryGoods;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 出库主表
 */
@Entity
@Table(name = "LH_OUT_STOCK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OutStock extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 放货单号
	 */
	private String deliveryNo;

	/**
	 * 出库单编号
	 */
	private String outStockNo;

	/**
	 * 提货单编号
	 */
	private String receiptNo;

	/**
	 * 收货单位
	 */
	private String receiptCode;

	/**
	 * 货款
	 */
	private double goodsMoney;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 实发数量
	 */
	private double realQuantity;

	/**
	 * 数量单位
	 */
	private String unitsMain;

	/**
	 * 出库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date outStockDate;

	/**
	 * 是否配煤
	 */
	private String coalMix;

	/**
	 * 确认人
	 */
	private String determineName;

	/**
	 * 确认时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date determineTime;

	/**
	 * 确认状态
	 */
	private String confirm;
	
	/**
	 * 码头日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date warehouseDate;
	
	/**
	 * 备注
	 */
	private String comment;
	
	@Column
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column
	public Date getWarehouseDate() {
		return warehouseDate;
	}

	public void setWarehouseDate(Date warehouseDate) {
		this.warehouseDate = warehouseDate;
	}

	@Column
	public String getDetermineName() {
		return determineName;
	}

	public void setDetermineName(String determineName) {
		this.determineName = determineName;
	}

	@Column
	public Date getDetermineTime() {
		return determineTime;
	}

	public void setDetermineTime(Date determineTime) {
		this.determineTime = determineTime;
	}

	@Column
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	@Column
	public String getCoalMix() {
		return coalMix;
	}

	public void setCoalMix(String coalMix) {
		this.coalMix = coalMix;
	}

	/**
	 * 客户签收日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date receiptDate;

	@JsonIgnore
	private List<SaleDeliveryGoods> salesDeliveryGoodsList = new ArrayList<SaleDeliveryGoods>();

	@JsonIgnore
	private List<OutStockDetail> outStockDetailList = new ArrayList<OutStockDetail>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "outStockId")
	public List<OutStockDetail> getOutStockDetailList() {
		return outStockDetailList;
	}

	public void setOutStockDetailList(List<OutStockDetail> outStockDetailList) {
		this.outStockDetailList = outStockDetailList;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "outStockId")
	public List<SaleDeliveryGoods> getSalesDeliveryGoodsList() {
		return salesDeliveryGoodsList;
	}

	public void setSalesDeliveryGoodsList(List<SaleDeliveryGoods> salesDeliveryGoodsList) {
		this.salesDeliveryGoodsList = salesDeliveryGoodsList;
	}

	@Column
	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	@Column
	public String getReceiptCode() {
		return receiptCode;
	}

	public void setReceiptCode(String receiptCode) {
		this.receiptCode = receiptCode;
	}

	@Column
	public String getOutStockNo() {
		return outStockNo;
	}

	public void setOutStockNo(String outStockNo) {
		this.outStockNo = outStockNo;
	}

	@Column
	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column
	public double getGoodsMoney() {
		return goodsMoney;
	}

	public void setGoodsMoney(double goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public double getRealQuantity() {
		return realQuantity;
	}

	public void setRealQuantity(double realQuantity) {
		this.realQuantity = realQuantity;
	}

	@Column
	public String getUnitsMain() {
		return unitsMain;
	}

	public void setUnitsMain(String unitsMain) {
		this.unitsMain = unitsMain;
	}

	@Column
	public Date getOutStockDate() {
		return outStockDate;
	}

	public void setOutStockDate(Date outStockDate) {
		this.outStockDate = outStockDate;
	}

	@Column
	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

}