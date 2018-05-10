package com.cbmie.lh.logistic.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name = "LH_TRANSPORT_SETTLEMENTSUB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class TransportSettlementSub extends BaseEntity {

	/**出库吨数*/
	private Double outStockQuantity=0.0;
	/**到货吨数*/
	private Double arrivalQuantity=0.0;
	/**结算吨数*/
	private Double settleQuantity=0.0;
	/**运费单价(含税)*/
	private Double unitPrice=0.0;
	/**应付*/
	private Double shouldPay=0.0;
	/**扣磅差费*/
	private Double differPay=0.0;
	/**其他费用*/
	private Double elsePay=0.0;
	/**实付*/
	private Double realPay=0.0;
	/**备注*/
	private String remarks;
	/**摘要*/
	private String roundup;
	
	private Long transportSettlementId;
	
	public Double getOutStockQuantity() {
		return outStockQuantity;
	}
	public void setOutStockQuantity(Double outStockQuantity) {
		this.outStockQuantity = outStockQuantity;
	}
	public Double getArrivalQuantity() {
		return arrivalQuantity;
	}
	public void setArrivalQuantity(Double arrivalQuantity) {
		this.arrivalQuantity = arrivalQuantity;
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
	public Double getShouldPay() {
		return shouldPay;
	}
	public void setShouldPay(Double shouldPay) {
		this.shouldPay = shouldPay;
	}
	public Double getDifferPay() {
		return differPay;
	}
	public void setDifferPay(Double differPay) {
		this.differPay = differPay;
	}
	public Double getElsePay() {
		return elsePay;
	}
	public void setElsePay(Double elsePay) {
		this.elsePay = elsePay;
	}
	public Double getRealPay() {
		return realPay;
	}
	public void setRealPay(Double realPay) {
		this.realPay = realPay;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRoundup() {
		return roundup;
	}
	public void setRoundup(String roundup) {
		this.roundup = roundup;
	}
	public Long getTransportSettlementId() {
		return transportSettlementId;
	}
	public void setTransportSettlementId(Long transportSettlementId) {
		this.transportSettlementId = transportSettlementId;
	}
}
