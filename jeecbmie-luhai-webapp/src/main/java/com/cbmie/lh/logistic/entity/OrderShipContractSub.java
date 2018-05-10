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
@Table(name = "LH_ORDERSHIP_CONTRACTSUB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OrderShipContractSub extends BaseEntity {

	/** 船名 暂停使用 **/
	private String ship;
	/** 船编号 **/
	private String shipNo;
	/**装货开始时间*/
	private Date loadBeginDate;
	/** 装货结束时间 */
	private Date loadEndDate;
	/** 数量溢短装* */
	private Double numMoreOrLess;
	/** 运费单价* */
	private Double tradePriceRate;
	/** 装港 */
	private String loadPort;
	/** 卸港 */
	private String unloadPort;
	/** 装率 */
	private Double loadRate;
	/** 卸率 */
	private Double unloadRate;
	/** 滯期率 */
	private Double demurrageRate;
	/** 速遣费率 */
	private Double dispatchPriceRate;
	/** 吊机标志 0 无 1有 */
	private String craneFlag;
	/** 执行货种 */
	private String product;
	private Long orderShipContractId;

	public String getShip() {
		return ship;
	}

	public void setShip(String ship) {
		this.ship = ship;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public Date getLoadBeginDate() {
		return loadBeginDate;
	}

	public void setLoadBeginDate(Date loadBeginDate) {
		this.loadBeginDate = loadBeginDate;
	}

	public Date getLoadEndDate() {
		return loadEndDate;
	}

	public void setLoadEndDate(Date loadEndDate) {
		this.loadEndDate = loadEndDate;
	}

	public Double getNumMoreOrLess() {
		return numMoreOrLess;
	}

	public void setNumMoreOrLess(Double numMoreOrLess) {
		this.numMoreOrLess = numMoreOrLess;
	}

	public Double getTradePriceRate() {
		return tradePriceRate;
	}

	public void setTradePriceRate(Double tradePriceRate) {
		this.tradePriceRate = tradePriceRate;
	}

	public String getLoadPort() {
		return loadPort;
	}

	public void setLoadPort(String loadPort) {
		this.loadPort = loadPort;
	}

	public String getUnloadPort() {
		return unloadPort;
	}

	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}

	public Double getLoadRate() {
		return loadRate;
	}

	public void setLoadRate(Double loadRate) {
		this.loadRate = loadRate;
	}

	public Double getUnloadRate() {
		return unloadRate;
	}

	public void setUnloadRate(Double unloadRate) {
		this.unloadRate = unloadRate;
	}

	public Double getDemurrageRate() {
		return demurrageRate;
	}

	public void setDemurrageRate(Double demurrageRate) {
		this.demurrageRate = demurrageRate;
	}

	public Double getDispatchPriceRate() {
		return dispatchPriceRate;
	}

	public void setDispatchPriceRate(Double dispatchPriceRate) {
		this.dispatchPriceRate = dispatchPriceRate;
	}

	public String getCraneFlag() {
		return craneFlag;
	}

	public void setCraneFlag(String craneFlag) {
		this.craneFlag = craneFlag;
	}

	public Long getOrderShipContractId() {
		return orderShipContractId;
	}

	public void setOrderShipContractId(Long orderShipContractId) {
		this.orderShipContractId = orderShipContractId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}
