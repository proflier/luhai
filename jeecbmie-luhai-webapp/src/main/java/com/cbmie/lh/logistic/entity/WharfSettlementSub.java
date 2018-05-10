package com.cbmie.lh.logistic.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name = "LH_WHARF_SETTLEMENTSUB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WharfSettlementSub extends BaseEntity {

	/**结算船舶*/
	private String shipNo;
	/**结算船舶名*/
	private String shipName;
	/**计费吨数*/
	private Double settleQuantity=0.0;
	/**结算单价*/
	private Double unitPrice=0.0;
	/**金额*/
	private Double totalPrice=0.0;
	/**备注*/
	private String remarks;
	/**摘要*/
	private String roundup;
	/**港口费用类别*/
	private String portExpenseType;
	
	private Long wharfSettlementId;

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

	public String getRoundup() {
		return roundup;
	}

	public void setRoundup(String roundup) {
		this.roundup = roundup;
	}

	public Long getWharfSettlementId() {
		return wharfSettlementId;
	}

	public void setWharfSettlementId(Long wharfSettlementId) {
		this.wharfSettlementId = wharfSettlementId;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getPortExpenseType() {
		return portExpenseType;
	}

	public void setPortExpenseType(String portExpenseType) {
		this.portExpenseType = portExpenseType;
	}
}
