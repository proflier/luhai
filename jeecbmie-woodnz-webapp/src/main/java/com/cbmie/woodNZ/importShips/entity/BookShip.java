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
 * 定船合同审批
 * 
 * @author linxiaopeng 2016年7月5日
 */
@Entity
@Table(name = "WOOD_BOOK_SHIPS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class BookShip extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 合同编号
	 */
	private String contractNo;

	/**
	 * 船名
	 */
	private String shipName;

	/**
	 * 船龄
	 */
	private String shipYear;

	/**
	 * 船次
	 */
	private String shipNo;

	/**
	 * 船运公司
	 */
	private String shipCompany;

	/**
	 * 载重
	 */
	private String loading;

	/**
	 * 订船费用
	 */
	private String bookShipFee;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 装港
	 */
	private String portOfShipment;

	/**
	 * 预计装港日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date expectShipTime;

	/**
	 * 目的港
	 */
	private String destinationPort;

	/**
	 * 预计卸港日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date expectUnloadTime;

	/**
	 * 受载期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date shipingTime;

	/**
	 * 签订时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date signedTime;

	/**
	 * 是否提示派检
	 */
	private String pointInspection;

	/**
	 * 运费率
	 */
	private String transportFeeRate;

	/**
	 * 经济人
	 */
	private String broker;

	/**
	 * 滞期费率
	 */
	private String demurrageRates;

	/**
	 * 速遣费率
	 */
	private String despatchRates;

	/**
	 * 装船期限
	 */
	private String loadDeadline;

	/**
	 * 卸船期限
	 */
	private String unloadDeadline;

	/**
	 * 吃水
	 */
	private String draft;

	/**
	 * 流水号
	 */
	private String serialNumber;

	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 状态
	 */
	private String state = "草稿";

	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column
	public String getShipYear() {
		return shipYear;
	}

	public void setShipYear(String shipYear) {
		this.shipYear = shipYear;
	}

	@Column
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	@Column
	public String getShipCompany() {
		return shipCompany;
	}

	public void setShipCompany(String shipCompany) {
		this.shipCompany = shipCompany;
	}

	@Column
	public String getLoading() {
		return loading;
	}

	public void setLoading(String loading) {
		this.loading = loading;
	}

	@Column
	public String getBookShipFee() {
		return bookShipFee;
	}

	public void setBookShipFee(String bookShipFee) {
		this.bookShipFee = bookShipFee;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public String getPortOfShipment() {
		return portOfShipment;
	}

	public void setPortOfShipment(String portOfShipment) {
		this.portOfShipment = portOfShipment;
	}

	@Column
	public Date getExpectShipTime() {
		return expectShipTime;
	}

	public void setExpectShipTime(Date expectShipTime) {
		this.expectShipTime = expectShipTime;
	}

	@Column
	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	@Column
	public Date getExpectUnloadTime() {
		return expectUnloadTime;
	}

	public void setExpectUnloadTime(Date expectUnloadTime) {
		this.expectUnloadTime = expectUnloadTime;
	}

	@Column
	public Date getShipingTime() {
		return shipingTime;
	}

	public void setShipingTime(Date shipingTime) {
		this.shipingTime = shipingTime;
	}

	@Column
	public Date getSignedTime() {
		return signedTime;
	}

	public void setSignedTime(Date signedTime) {
		this.signedTime = signedTime;
	}

	@Column
	public String getPointInspection() {
		return pointInspection;
	}

	public void setPointInspection(String pointInspection) {
		this.pointInspection = pointInspection;
	}

	@Column
	public String getTransportFeeRate() {
		return transportFeeRate;
	}

	public void setTransportFeeRate(String transportFeeRate) {
		this.transportFeeRate = transportFeeRate;
	}

	@Column
	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	@Column
	public String getDemurrageRates() {
		return demurrageRates;
	}

	public void setDemurrageRates(String demurrageRates) {
		this.demurrageRates = demurrageRates;
	}

	@Column
	public String getDespatchRates() {
		return despatchRates;
	}

	public void setDespatchRates(String despatchRates) {
		this.despatchRates = despatchRates;
	}

	@Column
	public String getLoadDeadline() {
		return loadDeadline;
	}

	public void setLoadDeadline(String loadDeadline) {
		this.loadDeadline = loadDeadline;
	}

	@Column
	public String getUnloadDeadline() {
		return unloadDeadline;
	}

	public void setUnloadDeadline(String unloadDeadline) {
		this.unloadDeadline = unloadDeadline;
	}

	@Column
	public String getDraft() {
		return draft;
	}

	public void setDraft(String draft) {
		this.draft = draft;
	}

	@Column
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
