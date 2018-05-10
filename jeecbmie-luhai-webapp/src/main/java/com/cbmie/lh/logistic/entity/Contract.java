package com.cbmie.lh.logistic.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.entity.BaseEntity;
/**
 * 合同主表
 * 
 */
@Entity
@Table(name = "LH_LOGISTIC_CONTRACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Contract extends BaseEntity {

	/** 流水号 **/
	private String serialnumber;
	/** 内部合同号 **/
	private String innerContractNo;
	/** 申请部门Id **/
	private String applyDeptId;
	/** 申请人员Id **/
	private String applyUserId;
	/** 经办人Id **/
	private String handlingUserId;
	/** 申请日期**/
	private Date applyDate; 
	/** 合同号 **/
	private String contractNo;
	/** 合同类别编码(数据字典) **/
	private String contractTypeCode;
	/** 合同金额 **/
	private String money;
	/** 保额币种(数据字典) **/
	private String moneyCurrencyCode;
	/** 合同开始日期 **/
	private Date startDate;
	/** 合同结束日期 **/
	private Date endDate;
	/** 公章编码 **/
	private String signetCode;
	/** 对方单位全称 **/
	private String traderName;
	/** 对方联系方式 **/
	private String traderContact;
	/** 主要内容 **/
	private String content;
	/** 贸易类型 **/
	private String tradeCategory;
	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	/**
	 * 是否法人签署 1 是 0否
	 * */
	private String isCorporationSign;
	/**代理人*/
	private String agent;
	/**审查类别(数据字典)*/
	private String checkTypeCode;
	/**是否法务审查 1是 0 否*/
	private String isLegalReview;
	/**重大非常规披露*/
	private String tipContent;
	@Column(length=50)
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	@Column(length=50)
	public String getInnerContractNo() {
		return innerContractNo;
	}
	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}
	@Column(length=50)
	public String getApplyDeptId() {
		return applyDeptId;
	}
	public void setApplyDeptId(String applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	@Column(length=50)
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	@Column(length=50)
	public String getHandlingUserId() {
		return handlingUserId;
	}
	public void setHandlingUserId(String handlingUserId) {
		this.handlingUserId = handlingUserId;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	@Column(length=50)
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column(length=50)
	public String getContractTypeCode() {
		return contractTypeCode;
	}
	public void setContractTypeCode(String contractTypeCode) {
		this.contractTypeCode = contractTypeCode;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@Column(length=50)
	public String getMoneyCurrencyCode() {
		return moneyCurrencyCode;
	}
	public void setMoneyCurrencyCode(String moneyCurrencyCode) {
		this.moneyCurrencyCode = moneyCurrencyCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(length=50)
	public String getSignetCode() {
		return signetCode;
	}
	public void setSignetCode(String signetCode) {
		this.signetCode = signetCode;
	}
	@Column(length=100)
	public String getTraderName() {
		return traderName;
	}
	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}
	@Column(length=100)
	public String getTraderContact() {
		return traderContact;
	}
	public void setTraderContact(String traderContact) {
		this.traderContact = traderContact;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(length=10)
	public String getIsCorporationSign() {
		return isCorporationSign;
	}
	public void setIsCorporationSign(String isCorporationSign) {
		this.isCorporationSign = isCorporationSign;
	}
	@Column(length=50)
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	@Column(length=30)
	public String getCheckTypeCode() {
		return checkTypeCode;
	}
	public void setCheckTypeCode(String checkTypeCode) {
		this.checkTypeCode = checkTypeCode;
	}
	@Column(length=10)
	public String getIsLegalReview() {
		return isLegalReview;
	}
	public void setIsLegalReview(String isLegalReview) {
		this.isLegalReview = isLegalReview;
	}
	public String getTipContent() {
		return tipContent;
	}
	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}
	@Column(length=10)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTradeCategory() {
		return tradeCategory;
	}
	public void setTradeCategory(String tradeCategory) {
		this.tradeCategory = tradeCategory;
	}
}
