package com.cbmie.lh.logistic.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name = "LH_SHIPMENTS_SETTLEMENTSUB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ShipmentsSettlementSub extends BaseEntity {

	/**船名*/
	private String ship;
	/**船编号*/
	private String shipNo;
	/**航线*/
	private String route;
	/**卸货日期*/
	private Date unloadDate;
	/**结算吨数*/
	private Double settleQuantity;
	/** 币种(数据字典) **/
	private String moneyCurrencyCode;
	/**结算单价*/
	private Double unitPrice;
	/**小计金额*/
	private Double totalPrice;
	/**备注*/
	private String remarks;
	
	private Long shipmentsSettleId;
	
	public String getShip() {
		return ship;
	}
	public void setShip(String ship) {
		this.ship = ship;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public Date getUnloadDate() {
		return unloadDate;
	}
	public void setUnloadDate(Date unloadDate) {
		this.unloadDate = unloadDate;
	}
	public Double getSettleQuantity() {
		return settleQuantity;
	}
	public void setSettleQuantity(Double settleQuantity) {
		this.settleQuantity = settleQuantity;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getShipmentsSettleId() {
		return shipmentsSettleId;
	}
	public void setShipmentsSettleId(Long shipmentsSettleId) {
		this.shipmentsSettleId = shipmentsSettleId;
	}
	public String getMoneyCurrencyCode() {
		return moneyCurrencyCode;
	}
	public void setMoneyCurrencyCode(String moneyCurrencyCode) {
		this.moneyCurrencyCode = moneyCurrencyCode;
	}
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
}
