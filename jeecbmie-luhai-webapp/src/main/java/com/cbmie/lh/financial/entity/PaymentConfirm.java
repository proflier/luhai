package com.cbmie.lh.financial.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 付款申请
 * @author admin
 *
 */
@Entity
@Table(name = "LH_PAYMENT_CONFIRM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PaymentConfirm extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1658341525904970950L;

	/** 确认单号 */
	private String confirmNo;
	/** 确认日期 */
	private Date confirmDate;
	/** 收款单位名称 */
	private String receiveUnitName;
	private String receiveUnitNameView;
	
	@Transient
	public String getReceiveUnitNameView() {
		receiveUnitNameView = DictUtils.getCorpName(receiveUnitName);
		return receiveUnitNameView;
	}

	public void setReceiveUnitNameView(String receiveUnitNameView) {
		this.receiveUnitNameView = receiveUnitNameView;
	}

	/** 汇入地点 */
	private String remitArea;
	/** 汇入银行 */
	private String remitBank;
	/** 汇入账号 */
	private String remitAccount;
	/** 付款方式 */
	private String payType;
	/** 付款金额(大写) */
	private String payMoneyDa;
	/** 付款金额(小写) */
	private Double payMoneyXiao;
	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	private String goodsClasses;

	/** 合同编号 */
	private String contractNo;
	/** 商品编号 */
	private String goodsNo;
	/** 品名 */
	private String goodsName;
	/** 数量 */
	private Double goodsNum;
	/** 付款内容 */
	private String payContent;
	private String payContentView;
	
	private String contractCategory;
	
	@Column
	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}
	
	@Transient
	public String getPayContentView() {
		payContentView = DictUtils.getDictSingleName(payContent);
		return payContentView;
	}

	public void setPayContentView(String payContentView) {
		this.payContentView = payContentView;
	}

	/** 备注 */
	private String remarks;
	/**
	 * 币种
	 */
	private String currency;
	private String currencyView;
	
	@Transient
	public String getCurrencyView() {
		currencyView = DictUtils.getDictSingleName(currency);
		return currencyView;
	}

	public void setCurrencyView(String currencyView) {
		this.currencyView = currencyView;
	}

	/**
	 * 付款单位
	 */
	private String payUnit;
	
	private String payUnitView;
	
	@Transient
	public String getPayUnitView() {
		payUnitView = DictUtils.getCorpName(payUnit);
		return payUnitView;
	}
	
	public void setPayUnitView(String payUnitView) {
		this.payUnitView = payUnitView;
	}
	
	
	@Column
	public String getPayUnit() {
		return payUnit;
	}

	public void setPayUnit(String payUnit) {
		this.payUnit = payUnit;
	}

	/**
	 * 综合合同号
	 */
	private String interContractNo;
	
	/**
	 * 变更状态 0:无效 1：有效 2:变更中
	 */
	private String changeState = "1";
	/**
	 * 源id
	 */
	private Long sourceId;

	/**
	 * 变更id
	 */
	private Long pid;

	/**
	 * 变更原因
	 */
	private String changeReason;
	
	/**
	 * 业务经办人
	 */
	private String businessManager;
	private String businessManagerView;

	@Column
	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	@Transient
	public String getBusinessManagerView() {
		businessManagerView = DictUtils.getUserNameByLoginName(businessManager);
		return businessManagerView;
	}

	public void setBusinessManagerView(String businessManagerView) {
		this.businessManagerView = businessManagerView;
	}
	
	@JsonIgnore
	private List<PaymentConfirmChild> paymentConfirmChildList = new ArrayList<PaymentConfirmChild>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "paymentConfirmId")
	public List<PaymentConfirmChild> getPaymentConfirmChildList() {
		return paymentConfirmChildList;
	}

	public void setPaymentConfirmChildList(List<PaymentConfirmChild> paymentConfirmChildList) {
		this.paymentConfirmChildList = paymentConfirmChildList;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "CONFIRMNO")
	public String getConfirmNo() {
		return confirmNo;
	}

	public void setConfirmNo(String confirmNo) {
		this.confirmNo = confirmNo;
	}

	@Column(name = "CONFIRMDATE")
	@Temporal(TemporalType.DATE)
	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	@Column(name = "RECEIVEUNITNAME")
	public String getReceiveUnitName() {
		return receiveUnitName;
	}

	public void setReceiveUnitName(String receiveUnitName) {
		this.receiveUnitName = receiveUnitName;
	}

	@Column(name = "REMITAREA")
	public String getRemitArea() {
		return remitArea;
	}

	public void setRemitArea(String remitArea) {
		this.remitArea = remitArea;
	}

	@Column(name = "REMITBANK")
	public String getRemitBank() {
		return remitBank;
	}

	public void setRemitBank(String remitBank) {
		this.remitBank = remitBank;
	}

	@Column(name = "REMITACCOUNT")
	public String getRemitAccount() {
		return remitAccount;
	}

	public void setRemitAccount(String remitAccount) {
		this.remitAccount = remitAccount;
	}

	@Column(name = "PAYMONEYDA")
	public String getPayMoneyDa() {
		return payMoneyDa;
	}

	public void setPayMoneyDa(String payMoneyDa) {
		this.payMoneyDa = payMoneyDa;
	}

	@Column(name = "PAYMONEYXIAO")
	public Double getPayMoneyXiao() {
		return payMoneyXiao;
	}

	public void setPayMoneyXiao(Double payMoneyXiao) {
		this.payMoneyXiao = payMoneyXiao;
	}

	@Column(name = "CONTRACTNO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "GOODSNO")
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	@Column(name = "GOODSNAME")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "GOODSNUM")
	public Double getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Double goodsNum) {
		this.goodsNum = goodsNum;
	}

	@Column(name = "PAYCONTENT")
	public String getPayContent() {
		return payContent;
	}

	public void setPayContent(String payContent) {
		this.payContent = payContent;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "PAYTYPE")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "GOODSCLASSES")
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

	@Column(columnDefinition = "varchar(255) default '1'")
	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	

}
