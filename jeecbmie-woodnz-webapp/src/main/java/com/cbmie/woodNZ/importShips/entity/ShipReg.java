package com.cbmie.woodNZ.importShips.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 船舶信息登记
 * 
 * @author linxiaopeng 2016年7月6日
 */
@Entity
@Table(name = "WOOD_SHIP_REG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ShipReg extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 船编号
	 */
	private String shipNo;

	/**
	 * 船名
	 */
	private String shipName;

	/**
	 * 航次
	 */
	private String voyage;

	/**
	 * 装船时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date shipLoadTime;

	/**
	 * 开船时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date sailingTime;
	
	/**
	 * 最后更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date lastUpdateTime;
	
	

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 运输单位
	 */
	private String transportPart;
	
	@Column
	public String getTransportPart() {
		return transportPart;
	}

	public void setTransportPart(String transportPart) {
		this.transportPart = transportPart;
	}

	/**
	 * 采购合同号
	 */
	private String importContractNo;

	@Column
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
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
	public Date getShipLoadTime() {
		return shipLoadTime;
	}

	public void setShipLoadTime(Date shipLoadTime) {
		this.shipLoadTime = shipLoadTime;
	}

	@Column
	public Date getSailingTime() {
		return sailingTime;
	}

	public void setSailingTime(Date sailingTime) {
		this.sailingTime = sailingTime;
	}

	@Column
	public String getImportContractNo() {
		return importContractNo;
	}

	public void setImportContractNo(String importContractNo) {
		this.importContractNo = importContractNo;
	}

}
