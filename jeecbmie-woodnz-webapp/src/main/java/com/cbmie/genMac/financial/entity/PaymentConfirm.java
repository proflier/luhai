package com.cbmie.genMac.financial.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name = "wood_payment_confirm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PaymentConfirm extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1658341525904970950L;
	
	/**确认单号*/
	private String confirmNo;
	/**确认日期*/
	private Date confirmDate;
	/**收款单位名称*/
	private String receiveUnitName;
	/**汇入地点*/
	private String remitArea;
	/**汇入银行*/
	private String remitBank;
	/**汇入账号*/
	private String remitAccount;
	/**付款方式*/
	private Long payType;
	/**付款金额(大写)*/
	private String payMoneyDa;
	/**付款金额(小写)*/
	private Double payMoneyXiao;
	/**
	 * 状态
	 */
	private String state = "草稿";
	private String goodsClasses;

	/**合同编号*/
	private String contractNo;
	/**商品编号*/
	private String goodsNo;
	/**品名*/
	private String goodsName;
	/**数量*/
	private Double goodsNum;
	/**付款内容*/
	private Long payContent;
	/**备注*/
	private String remarks;
	
	/**
	 * 综合合同号
	 */
	private String interContractNo;
	@Column(name="CONFIRMNO")
	public String getConfirmNo() {
		return confirmNo;
	}
	public void setConfirmNo(String confirmNo) {
		this.confirmNo = confirmNo;
	}
	@Column(name="CONFIRMDATE")
	@Temporal(TemporalType.DATE)
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	@Column(name="RECEIVEUNITNAME")
	public String getReceiveUnitName() {
		return receiveUnitName;
	}
	public void setReceiveUnitName(String receiveUnitName) {
		this.receiveUnitName = receiveUnitName;
	}
	@Column(name="REMITAREA")
	public String getRemitArea() {
		return remitArea;
	}
	public void setRemitArea(String remitArea) {
		this.remitArea = remitArea;
	}
	@Column(name="REMITBANK")
	public String getRemitBank() {
		return remitBank;
	}
	public void setRemitBank(String remitBank) {
		this.remitBank = remitBank;
	}
	@Column(name="REMITACCOUNT")
	public String getRemitAccount() {
		return remitAccount;
	}
	public void setRemitAccount(String remitAccount) {
		this.remitAccount = remitAccount;
	}
	@Column(name="PAYMONEYDA")
	public String getPayMoneyDa() {
		return payMoneyDa;
	}
	public void setPayMoneyDa(String payMoneyDa) {
		this.payMoneyDa = payMoneyDa;
	}
	@Column(name="PAYMONEYXIAO")
	public Double getPayMoneyXiao() {
		return payMoneyXiao;
	}
	public void setPayMoneyXiao(Double payMoneyXiao) {
		this.payMoneyXiao = payMoneyXiao;
	}
	@Column(name="CONTRACTNO")
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column(name="GOODSNO")
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	@Column(name="GOODSNAME")
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column(name="GOODSNUM")
	public Double getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Double goodsNum) {
		this.goodsNum = goodsNum;
	}
	@Column(name="PAYCONTENT")
	public Long getPayContent() {
		return payContent;
	}
	public void setPayContent(Long payContent) {
		this.payContent = payContent;
	}
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="STATE")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column(name="PAYTYPE")
	public Long getPayType() {
		return payType;
	}
	public void setPayType(Long payType) {
		this.payType = payType;
	}
	@Column(name="GOODSCLASSES")
	public String getGoodsClasses() {
		return goodsClasses;
	}
	public void setGoodsClasses(String goodsClasses) {
		this.goodsClasses = goodsClasses;
	}
	public String getInterContractNo() {
		return interContractNo;
	}
	public void setInterContractNo(String interContractNo) {
		this.interContractNo = interContractNo;
	}
	
}
