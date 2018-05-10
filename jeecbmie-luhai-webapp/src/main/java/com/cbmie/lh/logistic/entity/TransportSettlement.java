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
@Entity
@Table(name = "LH_TRANSPORT_SETTLEMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class TransportSettlement extends BaseEntity {

	/**运输商*/
	private String transporter;
	/**办事处*/
	private String office;
	/**运输方式*/
	private String transportType;
	/**数量单位*/
	private String numUnit;
	/**金额单位*/
	private String moneyUnit;
	/**运输路线*/
	private String route;
	/**收货方*/
	private String receiver;
	/**结算开始日期*/
	private Date settleBeginDate;
	/**结算结束日期*/
	private Date settleEndDate;
	/**预付*/
	private Double prePay=0.0;
	/**上期结余*/
	private Double priorPay=0.0;
	/**已付*/
	private Double alreadyPay=0.0;
	/**应付*/
	private Double shouldPay=0.0;
	/**扣磅差费*/
	private Double differPay=0.0;
	/**其他费用*/
	private Double elsePay=0.0;
	/**实付*/
	private Double realPay=0.0;
	/**已开票*/
	private Double alreadyBill=0.0;
	/**应开票*/
	private Double shouldBill=0.0;
	
	private String state=ActivitiConstant.ACTIVITI_DRAFT;
	/**备注*/
	private String remarks;
	
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
	
	private List<TransportSettlementSub> settleSubs = new ArrayList<TransportSettlementSub>();

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public String getNumUnit() {
		return numUnit;
	}

	public void setNumUnit(String numUnit) {
		this.numUnit = numUnit;
	}

	public String getMoneyUnit() {
		return moneyUnit;
	}

	public void setMoneyUnit(String moneyUnit) {
		this.moneyUnit = moneyUnit;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Date getSettleBeginDate() {
		return settleBeginDate;
	}

	public void setSettleBeginDate(Date settleBeginDate) {
		this.settleBeginDate = settleBeginDate;
	}

	public Date getSettleEndDate() {
		return settleEndDate;
	}

	public void setSettleEndDate(Date settleEndDate) {
		this.settleEndDate = settleEndDate;
	}

	public Double getPrePay() {
		return prePay;
	}

	public void setPrePay(Double prePay) {
		this.prePay = prePay;
	}

	public Double getPriorPay() {
		return priorPay;
	}

	public void setPriorPay(Double priorPay) {
		this.priorPay = priorPay;
	}

	public Double getAlreadyPay() {
		return alreadyPay;
	}

	public void setAlreadyPay(Double alreadyPay) {
		this.alreadyPay = alreadyPay;
	}

	public Double getShouldPay() {
		return shouldPay;
	}

	public void setShouldPay(Double shouldPay) {
		this.shouldPay = shouldPay;
	}

	public Double getDifferPay() {
		return differPay;
	}

	public void setDifferPay(Double differPay) {
		this.differPay = differPay;
	}

	public Double getElsePay() {
		return elsePay;
	}

	public void setElsePay(Double elsePay) {
		this.elsePay = elsePay;
	}

	public Double getRealPay() {
		return realPay;
	}

	public void setRealPay(Double realPay) {
		this.realPay = realPay;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="transportSettlementId",orphanRemoval=true)
	public List<TransportSettlementSub> getSettleSubs() {
		return settleSubs;
	}

	public void setSettleSubs(List<TransportSettlementSub> settleSubs) {
		this.settleSubs = settleSubs;
	}

	public Double getAlreadyBill() {
		return alreadyBill;
	}

	public void setAlreadyBill(Double alreadyBill) {
		this.alreadyBill = alreadyBill;
	}

	public Double getShouldBill() {
		return shouldBill;
	}

	public void setShouldBill(Double shouldBill) {
		this.shouldBill = shouldBill;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(length=5000)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
