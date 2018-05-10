package com.cbmie.lh.stock.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 仓储调拨
 */
@Entity
@Table(name = "LH_STOCK_ALLOCATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class StockAllocation extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 船编号
	 */
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


	/**
	 * 船名和编号
	 */
	private String shipNoAndName;
	@Column
	public String getShipNoAndName() {
		return shipNoAndName;
	}

	public void setShipNoAndName(String shipNoAndName) {
		this.shipNoAndName = shipNoAndName;
	}


	/**
	 * 运输方式
	 */
	private String transportType;

	/**
	 * 调入日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date inTime;

	/**
	 * 调出日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date outTime;

	/**
	 * 承运船次
	 */
	private String innerShipNo;
	private String innerShipNoView;
	@Transient
	public String getInnerShipNoView() {
		innerShipNoView = DictUtils.getShipName(innerShipNo);
		return innerShipNoView;
	}

	public void setInnerShipNoView(String innerShipNoView) {
		this.innerShipNoView = innerShipNoView;
	}

	@Column
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	@Column
	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	@Column
	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	@Column
	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	@Column
	public String getInnerShipNo() {
		return innerShipNo;
	}

	public void setInnerShipNo(String innerShipNo) {
		this.innerShipNo = innerShipNo;
	}

	/**
	 * 调拨编码
	 */
	private String allocationNo;

	/**
	 * 源仓库编码
	 */
	private String sourceWarehouseCode;
	private String sourceWarehouseCodeView;
	@Transient
	public String getSourceWarehouseCodeView() {
		sourceWarehouseCodeView = DictUtils.getCorpName(sourceWarehouseCode);
		return sourceWarehouseCodeView;
	}

	public void setSourceWarehouseCodeView(String sourceWarehouseCodeView) {
		this.sourceWarehouseCodeView = sourceWarehouseCodeView;
	}


	/**
	 * 源仓储明细ID
	 */
	private Long sourceStockGoodsId;

	/**
	 * 源仓库商品数量
	 */
	private double sourceGoodsQuantity;

	/**
	 * 目标仓库编码
	 */
	private String destWarehouseCode;
	private String destWarehouseCodeView;
	@Transient
	public String getDestWarehouseCodeView() {
		destWarehouseCodeView = DictUtils.getCorpName(destWarehouseCode);
		return destWarehouseCodeView;
	}

	public void setDestWarehouseCodeView(String destWarehouseCodeView) {
		this.destWarehouseCodeView = destWarehouseCodeView;
	}


	/**
	 * 储明增加ID
	 */
	private Long stockAddId;

	/**
	 * 储明减少ID
	 */
	private Long stockMinusId;

	/**
	 * 商品编码/名称
	 */
	private String goodsName;
	private String goodsNameView;
	@Transient
	public String getGoodsNameView() {
		goodsNameView = DictUtils.getGoodsInfoName(goodsName);
		return goodsNameView;
	}

	public void setGoodsNameView(String goodsNameView) {
		this.goodsNameView = goodsNameView;
	}


	/**
	 * 调拨数量
	 */
	private double allocationQuantity;

	/**
	 * 调拨人
	 */
	private String allocationPerson;

	/**
	 * 接收人
	 */
	private String receivePerson;

	/**
	 * 调入数量
	 */
	private double receiveQuantity;

	@Column
	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	@Column
	public double getReceiveQuantity() {
		return receiveQuantity;
	}

	public void setReceiveQuantity(double receiveQuantity) {
		this.receiveQuantity = receiveQuantity;
	}

	@Column
	public String getAllocationPerson() {
		return allocationPerson;
	}

	public void setAllocationPerson(String allocationPerson) {
		this.allocationPerson = allocationPerson;
	}

	/**
	 * 调拨日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date allocationDate;

	@Column
	public String getAllocationNo() {
		return allocationNo;
	}

	public void setAllocationNo(String allocationNo) {
		this.allocationNo = allocationNo;
	}

	@Column
	public String getSourceWarehouseCode() {
		return sourceWarehouseCode;
	}

	public void setSourceWarehouseCode(String sourceWarehouseCode) {
		this.sourceWarehouseCode = sourceWarehouseCode;
	}

	@Column
	public Long getSourceStockGoodsId() {
		return sourceStockGoodsId;
	}

	public void setSourceStockGoodsId(Long sourceStockGoodsId) {
		this.sourceStockGoodsId = sourceStockGoodsId;
	}

	@Column
	public double getSourceGoodsQuantity() {
		return sourceGoodsQuantity;
	}

	public void setSourceGoodsQuantity(double sourceGoodsQuantity) {
		this.sourceGoodsQuantity = sourceGoodsQuantity;
	}

	@Column
	public String getDestWarehouseCode() {
		return destWarehouseCode;
	}

	public void setDestWarehouseCode(String destWarehouseCode) {
		this.destWarehouseCode = destWarehouseCode;
	}

	@Column
	public Long getStockAddId() {
		return stockAddId;
	}

	public void setStockAddId(Long stockAddId) {
		this.stockAddId = stockAddId;
	}

	@Column
	public Long getStockMinusId() {
		return stockMinusId;
	}

	public void setStockMinusId(Long stockMinusId) {
		this.stockMinusId = stockMinusId;
	}

	@Column
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column
	public double getAllocationQuantity() {
		return allocationQuantity;
	}

	public void setAllocationQuantity(double allocationQuantity) {
		this.allocationQuantity = allocationQuantity;
	}

	@Column
	public Date getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}

}