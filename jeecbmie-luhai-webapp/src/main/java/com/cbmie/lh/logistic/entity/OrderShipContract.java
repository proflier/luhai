package com.cbmie.lh.logistic.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
/**
 * 订船合同
 * 
 */
@Entity
@Table(name = "LH_ORDERSHIP_CONTRACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OrderShipContract extends BaseEntity {

	/** 流水号 暂时停用**/
	private String serialnumber;
	/** 内部合同号 **/
	private String innerContractNo;
	/** 申请部门Id 暂时停用**/
	private String applyDeptId;
	/** 申请人员Id  暂时停用**/
	private String applyUserId;
	/** 经办人Id 暂时停用 **/
	private String handlingUserId;
	/** 业务经办人 **/
	private String businessManager;
	/** 申请日期**/
	private Date applyDate; 
	/** 合同号 **/
	private String contractNo;
	/** 合同类别编码(数据字典) **/
	private String contractTypeCode;
	/** 合同货量 */
	private Double contractQuantity;
	/** 合同金额 **/
	private String money;
	/** 保额币种(数据字典) **/
	private String moneyCurrencyCode;
	private String moneyCurrencyCodeView;
	
	/**
	 * 帐套单位
	 */
	private String setUnit;
	
	@Column
	public String getSetUnit() {
		return setUnit;
	}

	public void setSetUnit(String setUnit) {
		this.setUnit = setUnit;
	}
	@Transient
	public String getMoneyCurrencyCodeView() {
		moneyCurrencyCodeView = DictUtils.getDictSingleName(moneyCurrencyCode);
		return moneyCurrencyCodeView;
	}
	public void setMoneyCurrencyCodeView(String moneyCurrencyCodeView) {
		this.moneyCurrencyCodeView = moneyCurrencyCodeView;
	}
	
	/** 合同开始日期 **/
	private Date startDate;
	/** 合同结束日期 **/
	private Date endDate;
	/** 公章编码 **/
	private String signetCode;
	/** 印章管理员 **/
	private String sealManager;
	/** 对方单位全称 **/
	private String traderName;
	private String traderNameView;
	@Transient
	public String getTraderNameView() {
		traderNameView = DictUtils.getCorpName(traderName);
		return traderNameView;
	}
	public void setTraderNameView(String traderNameView) {
		this.traderNameView = traderNameView;
	}

	/** 对方联系方式 **/
	private String traderContact;
	/** 类型 */
	private String orderShipType;
	private String orderShipTypeView;
	@Transient
	public String getOrderShipTypeView() {
		orderShipTypeView = DictUtils.getDictSingleName(orderShipType);
		return orderShipTypeView;
	}
	public void setOrderShipTypeView(String orderShipTypeView) {
		this.orderShipTypeView = orderShipTypeView;
	}

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
	
	/**
	 * 变更状态 0:无效 1：有效  2:变更中
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
	
	@Column(columnDefinition="varchar(255) default '1'")
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

	List<OrderShipContractSub> shipSubs = new ArrayList<OrderShipContractSub>(); 
	
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getInnerContractNo() {
		return innerContractNo;
	}
	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}
	public String getApplyDeptId() {
		return applyDeptId;
	}
	public void setApplyDeptId(String applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
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
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
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
	public String getSignetCode() {
		return signetCode;
	}
	public void setSignetCode(String signetCode) {
		this.signetCode = signetCode;
	}
	public String getTraderName() {
		return traderName;
	}
	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}
	public String getTraderContact() {
		return traderContact;
	}
	public void setTraderContact(String traderContact) {
		this.traderContact = traderContact;
	}
	public Double getContractQuantity() {
		return contractQuantity;
	}
	public void setContractQuantity(Double contractQuantity) {
		this.contractQuantity = contractQuantity;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIsCorporationSign() {
		return isCorporationSign;
	}
	public void setIsCorporationSign(String isCorporationSign) {
		this.isCorporationSign = isCorporationSign;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getCheckTypeCode() {
		return checkTypeCode;
	}
	public void setCheckTypeCode(String checkTypeCode) {
		this.checkTypeCode = checkTypeCode;
	}
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
	public String getOrderShipType() {
		return orderShipType;
	}
	public void setOrderShipType(String orderShipType) {
		this.orderShipType = orderShipType;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="orderShipContractId",orphanRemoval=true)
	public List<OrderShipContractSub> getShipSubs() {
		return shipSubs;
	}
	public void setShipSubs(List<OrderShipContractSub> shipSubs) {
		this.shipSubs = shipSubs;
	}
	public String getBusinessManager() {
		return businessManager;
	}
	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}
	public String getSealManager() {
		return sealManager;
	}
	public void setSealManager(String sealManager) {
		this.sealManager = sealManager;
	}
}
