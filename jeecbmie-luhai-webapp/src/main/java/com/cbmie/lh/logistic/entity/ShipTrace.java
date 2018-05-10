package com.cbmie.lh.logistic.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
@Entity
@Table(name = "LH_SHIP_TRACE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ShipTrace extends BaseEntity {
	
	/**订船合同流水号 暂时去掉*/
	private String orderShipContractNo;
	/**
	 * 内部合同号
	 */
	private String innerContractNo;
	/**船编号 来自订船合同*/
	private String shipNo;
	/**船名称*/
	private String shipName;
	/**船名称英文*/
	private String shipNameE;
	/**抵装港时间*/
	private Date loadPortDate;
	/**装港港口*/
	private String loadPort;
	private String loadPortView;
	@Transient
	public String getLoadPortView() {
		loadPortView = DictUtils.getPortName(loadPort);
		return loadPortView;
	}
	public void setLoadPortView(String loadPortView) {
		this.loadPortView = loadPortView;
	}
	/**开装时间*/
	private Date loadBeginDate;
	/**装完时间*/
	private Date loadEndDate;
	/**装货量*/
	private Double loadQuantity;
	/**抵卸港时间*/
	private Date unloadPortDate;
	/**卸港港口*/
	private String unloadPort;
	/**开卸时间*/
	private Date unloadBeginDate;
	/**卸完时间*/
	private Date unloadEndDate;
	/**卸货量*/
	private Double unloadQuantity;
	/**贸易类别*/
	private String tradeCategory;
	/**外贸船编号*/
	private String wmShipNo;
	/**备注*/
	private String remarks;
	
	private String noAndName;
	
	/**
	 * 供货单位
	 */
	private String deliveryUnit;
	
	/**
	 * 供货单位
	 */
	private String deliveryUnitView;

	@Transient
	public String getDeliveryUnitView() {
		deliveryUnitView = DictUtils.getCorpName(deliveryUnit);
		return deliveryUnitView;
	}

	public void setDeliveryUnitView(String deliveryUnitView) {
		this.deliveryUnitView = deliveryUnitView;
	}
	
	private String transportCategory;
	
	private String transportCategoryView;
	
	@Transient
	public String getTransportCategoryView() {
		transportCategoryView = DictUtils.getDictSingleName(transportCategory);
		return transportCategoryView;
	}
	public void setTransportCategoryView(String transportCategoryView) {
		this.transportCategoryView = transportCategoryView;
	}
	
	/**
	 * 装载量 
	 */
	private String loading;
	
	public String getLoading() {
		return loading;
	}
	public void setLoading(String loading) {
		this.loading = loading;
	}
	public Date getLoadPortDate() {
		return loadPortDate;
	}
	public void setLoadPortDate(Date loadPortDate) {
		this.loadPortDate = loadPortDate;
	}
	public String getLoadPort() {
		return loadPort;
	}
	public void setLoadPort(String loadPort) {
		this.loadPort = loadPort;
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
	public Double getLoadQuantity() {
		return loadQuantity;
	}
	public void setLoadQuantity(Double loadQuantity) {
		this.loadQuantity = loadQuantity;
	}
	public Date getUnloadPortDate() {
		return unloadPortDate;
	}
	public void setUnloadPortDate(Date unloadPortDate) {
		this.unloadPortDate = unloadPortDate;
	}
	public String getUnloadPort() {
		return unloadPort;
	}
	public void setUnloadPort(String unloadPort) {
		this.unloadPort = unloadPort;
	}
	public Date getUnloadBeginDate() {
		return unloadBeginDate;
	}
	public void setUnloadBeginDate(Date unloadBeginDate) {
		this.unloadBeginDate = unloadBeginDate;
	}
	public Date getUnloadEndDate() {
		return unloadEndDate;
	}
	public void setUnloadEndDate(Date unloadEndDate) {
		this.unloadEndDate = unloadEndDate;
	}
	public Double getUnloadQuantity() {
		return unloadQuantity;
	}
	public void setUnloadQuantity(Double unloadQuantity) {
		this.unloadQuantity = unloadQuantity;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getOrderShipContractNo() {
		return orderShipContractNo;
	}
	public void setOrderShipContractNo(String orderShipContractNo) {
		this.orderShipContractNo = orderShipContractNo;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public String getShipNameE() {
		return shipNameE;
	}
	public void setShipNameE(String shipNameE) {
		this.shipNameE = shipNameE;
	}
	public String getInnerContractNo() {
		return innerContractNo;
	}
	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}
	public String getTradeCategory() {
		return tradeCategory;
	}
	public void setTradeCategory(String tradeCategory) {
		this.tradeCategory = tradeCategory;
	}
	public String getWmShipNo() {
		return wmShipNo;
	}
	public void setWmShipNo(String wmShipNo) {
		this.wmShipNo = wmShipNo;
	}
	
	@Transient
	public String getNoAndName() {
		this.noAndName = getShipNo() + getShipName();
		return noAndName;
	}
	public void setNoAndName(String noAndName) {
		this.noAndName = noAndName;
	}
	public String getDeliveryUnit() {
		return deliveryUnit;
	}
	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}
	public String getTransportCategory() {
		return transportCategory;
	}
	public void setTransportCategory(String transportCategory) {
		this.transportCategory = transportCategory;
	}
	
	
}
