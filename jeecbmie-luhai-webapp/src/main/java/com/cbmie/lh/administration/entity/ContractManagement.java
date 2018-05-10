package com.cbmie.lh.administration.entity;

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
 * 合同管理
 */
@Entity
@Table(name = "LH_CONTRACT_MANAGEMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ContractManagement extends BaseEntity {

	/**
	 * 合同类别
	 */
	private String contractCategory;

	/**
	 * 归档编号
	 */
	private String innerContractNo;

	/**
	 * 合同编号
	 */
	private String contractNo;

	/**
	 * 签约方
	 */
	private String signAffi;

	/**
	 * 合同名称
	 */
	private String contractName;

	/**
	 * 签约日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date singnDate;

	/**
	 * 数量
	 */
	private Double quantity;

	/**
	 * 单价
	 */
	private double unitPrice;

	/**
	 * 金额
	 */
	private double amount;

	/**
	 * 原件数量
	 */
	private double originalQuantity;

	/**
	 * 复印件数量
	 */
	private double copyQuantity;

	/**
	 * 期限
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date contracTermt;

	/**
	 * 期限
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date contracTermtEnd;

	/**
	 * 交货地点
	 */
	private String port;

	/**
	 * 备注
	 */
	private String comment;
	
	/**
	 * 盖章日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date sealDate;
	
	/**
	 * 收原件日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date receiveOrgDate;
	

	@Column
	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	@Column
	public String getInnerContractNo() {
		return innerContractNo;
	}

	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}

	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getSignAffi() {
		return signAffi;
	}

	public void setSignAffi(String signAffi) {
		this.signAffi = signAffi;
	}

	@Column
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Column
	public Date getSingnDate() {
		return singnDate;
	}

	public void setSingnDate(Date singnDate) {
		this.singnDate = singnDate;
	}

	@Column
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Column
	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column
	public double getOriginalQuantity() {
		return originalQuantity;
	}

	public void setOriginalQuantity(double originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	@Column
	public double getCopyQuantity() {
		return copyQuantity;
	}

	public void setCopyQuantity(double copyQuantity) {
		this.copyQuantity = copyQuantity;
	}

	@Column
	public Date getContracTermt() {
		return contracTermt;
	}

	public void setContracTermt(Date contracTermt) {
		this.contracTermt = contracTermt;
	}

	@Column
	public Date getContracTermtEnd() {
		return contracTermtEnd;
	}

	public void setContracTermtEnd(Date contracTermtEnd) {
		this.contracTermtEnd = contracTermtEnd;
	}

	@Column
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Column
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column
	public Date getSealDate() {
		return sealDate;
	}

	public void setSealDate(Date sealDate) {
		this.sealDate = sealDate;
	}

	@Column
	public Date getReceiveOrgDate() {
		return receiveOrgDate;
	}

	

	public void setReceiveOrgDate(Date receiveOrgDate) {
		this.receiveOrgDate = receiveOrgDate;
	}
	
	

}
