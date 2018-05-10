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
 * 码头合同
 */
@Entity
@Table(name = "LH_LOGISTIC_PORTCONTRACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PortContract extends BaseEntity {

	/**合同号*/
	private String contractNo;
	/**免堆单天数*/
	private Double freeHeapDays;
	/**达量免堆数*/
	private Double freeHeapCounts;
	/**开始日期*/
	private Date startDay;
	/**结束日期*/
	private Date endDay;
	/**签订单位*/
	private String signAffiliate;
	private String signAffiliateView;
	/** 对方联系方式 **/
	private String traderContact;
	/**是否法人签署 */
	private String isCorporationSign;
	/**代理人*/
	private String agent;
	/**审查类别(数据字典)*/
	private String checkTypeCode;
	private String checkTypeCodeView;
	/**是否法务审查*/
	private String isLegalReview;
	/**备注*/
	private String remarks;
	
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	
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
	
	/**
	 * 帐套单位
	 */
	private String setUnit;
	
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
	
	@Column
	public String getSetUnit() {
		return setUnit;
	}

	public void setSetUnit(String setUnit) {
		this.setUnit = setUnit;
	}

	
	private List<OperateExpense> operateExpenseSubs = new ArrayList<OperateExpense>();
	
	private List<ExtraExpense> extraExpenseSubs = new ArrayList<ExtraExpense>();
	
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Double getFreeHeapDays() {
		return freeHeapDays;
	}
	public void setFreeHeapDays(Double freeHeapDays) {
		this.freeHeapDays = freeHeapDays;
	}
	public Double getFreeHeapCounts() {
		return freeHeapCounts;
	}
	public void setFreeHeapCounts(Double freeHeapCounts) {
		this.freeHeapCounts = freeHeapCounts;
	}
	public Date getStartDay() {
		return startDay;
	}
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}
	public Date getEndDay() {
		return endDay;
	}
	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}
	public String getSignAffiliate() {
		return signAffiliate;
	}
	public void setSignAffiliate(String signAffiliate) {
		this.signAffiliate = signAffiliate;
	}
	public String getTraderContact() {
		return traderContact;
	}
	public void setTraderContact(String traderContact) {
		this.traderContact = traderContact;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "portContractId")
	public List<OperateExpense> getOperateExpenseSubs() {
		return operateExpenseSubs;
	}
	public void setOperateExpenseSubs(List<OperateExpense> operateExpenseSubs) {
		this.operateExpenseSubs = operateExpenseSubs;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "portContractId")
	public List<ExtraExpense> getExtraExpenseSubs() {
		return extraExpenseSubs;
	}
	public void setExtraExpenseSubs(List<ExtraExpense> extraExpenseSubs) {
		this.extraExpenseSubs = extraExpenseSubs;
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
	@Transient
	public String getSignAffiliateView() {
		signAffiliateView = DictUtils.getCorpName(signAffiliate);
		return signAffiliateView;
	}
	public void setSignAffiliateView(String signAffiliateView) {
		this.signAffiliateView = signAffiliateView;
	}
	@Transient
	public String getCheckTypeCodeView() {
		checkTypeCodeView = DictUtils.getDictSingleName(checkTypeCode);
		return checkTypeCodeView;
	}
	public void setCheckTypeCodeView(String checkTypeCodeView) {
		this.checkTypeCodeView = checkTypeCodeView;
	}
	
	
}
