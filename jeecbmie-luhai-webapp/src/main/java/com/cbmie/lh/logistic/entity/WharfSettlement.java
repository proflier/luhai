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
@Table(name = "LH_WHARF_SETTLEMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WharfSettlement extends BaseEntity {

	/**码头*/
	private String wharf;
	/**商检水尺*/
	private String gaugeQuantity;
	/**出入库日期*/
	private Date stockDate;
	/**结算日期*/
	private Date settleDate;
	/**结算月份*/
	private String settleMonth;
	/**预付*/
	private Double prePay=0.0;
	/**应付*/
	private Double shouldPay=0.0;
	/**已开票*/
	private Double alreadyBill=0.0;
	/**应开票*/
	private Double shouldBill=0.0;
	
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
	
	private List<WharfSettlementSub> subList = new ArrayList<WharfSettlementSub>();

	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	
	public String getWharf() {
		return wharf;
	}

	public void setWharf(String wharf) {
		this.wharf = wharf;
	}

	public String getGaugeQuantity() {
		return gaugeQuantity;
	}

	public void setGaugeQuantity(String gaugeQuantity) {
		this.gaugeQuantity = gaugeQuantity;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public Date getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}

	public Double getPrePay() {
		return prePay;
	}

	public void setPrePay(Double prePay) {
		this.prePay = prePay;
	}

	public Double getShouldPay() {
		return shouldPay;
	}

	public void setShouldPay(Double shouldPay) {
		this.shouldPay = shouldPay;
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
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="wharfSettlementId",orphanRemoval=true)
	public List<WharfSettlementSub> getSubList() {
		return subList;
	}

	public void setSubList(List<WharfSettlementSub> subList) {
		this.subList = subList;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSettleMonth() {
		return settleMonth;
	}

	public void setSettleMonth(String settleMonth) {
		this.settleMonth = settleMonth;
	}

}
