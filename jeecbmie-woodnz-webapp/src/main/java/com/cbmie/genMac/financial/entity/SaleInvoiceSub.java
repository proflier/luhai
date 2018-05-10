package com.cbmie.genMac.financial.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name = "wood_sale_invoice_sub")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleInvoiceSub extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 5654526894201694981L;
	
	/**驳船号*/
	private String shipNo;
	/**公吨*/
	private Double metricTon;
	/**含水率*/
	private Double waterSale;
	/**干吨*/
	private Double dryTon;
	/**美金单价*/
	private Double money;
	/**总价*/
	private Double totalMoney;
	/**
	 * 关联id
	 */
	private Long parentId;
	
	@Column(name="SHIPNO")
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	@Column(name="METRICTON")
	public Double getMetricTon() {
		return metricTon;
	}
	public void setMetricTon(Double metricTon) {
		this.metricTon = metricTon;
	}
	@Column(name="WATERSALE")
	public Double getWaterSale() {
		return waterSale;
	}
	public void setWaterSale(Double waterSale) {
		this.waterSale = waterSale;
	}
	@Column(name="DRYTON")
	public Double getDryTon() {
		return dryTon;
	}
	public void setDryTon(Double dryTon) {
		this.dryTon = dryTon;
	}
	@Column(name="MONEY")
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Column(name="TOTALMONEY")
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	@Column(name="PARENTID")
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
