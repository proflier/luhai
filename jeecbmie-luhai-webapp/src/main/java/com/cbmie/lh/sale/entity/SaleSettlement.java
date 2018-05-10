package com.cbmie.lh.sale.entity;

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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 销售结算
 * */
@Entity
@Table(name = "LH_SALE_SETTLEMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleSettlement extends BaseEntity {
	/**
	 * 结算编号
	 */
	private String saleSettlementNo;
	/**
	 * 销售合同号
	 */
	private String saleContractNo;
	/**
	 * 结算日期
	 */
	private Date settlementDate;
	/**
	 * 收货单位
	 */
	private String receivingUnit;
	private String receivingUnitView;
	@Transient
	public String getReceivingUnitView() {
		receivingUnitView = DictUtils.getCorpName(receivingUnit);
		return receivingUnitView;
	}

	public void setReceivingUnitView(String receivingUnitView) {
		this.receivingUnitView = receivingUnitView;
	}

	/**发货开始日期*/
	private Date sendBeginDate;
	/**发货结束日期*/
	private Date sendEndDate;
	/**收货开始日期*/
	private Date receiveBeginDate;
	/**收货结束日期*/
	private Date receiveEndDate;
	/**奖惩金额*/
	private Double sanctionPrice;
	
	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	
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
	
	private String manageMode;

	@Column(length=50)
	public String getManageMode() {
		return manageMode;
	}

	public void setManageMode(String manageMode) {
		this.manageMode = manageMode;
	}
	
	@JsonIgnore
	private List<SaleSettlementGoods> saleSettlementSubList = new ArrayList<SaleSettlementGoods>();
	@JsonIgnore
	private List<SaleSettlementOutRel> outRelList = new ArrayList<SaleSettlementOutRel>();

	public String getSaleContractNo() {
		return saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	@Column(length=20)
	public String getReceivingUnit() {
		return receivingUnit;
	}

	public void setReceivingUnit(String receivingUnit) {
		this.receivingUnit = receivingUnit;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "saleSettlementId")
	public List<SaleSettlementGoods> getSaleSettlementSubList() {
		return saleSettlementSubList;
	}

	public void setSaleSettlementSubList(List<SaleSettlementGoods> saleSettlementSubList) {
		this.saleSettlementSubList = saleSettlementSubList;
	}

	public String getSaleSettlementNo() {
		return saleSettlementNo;
	}

	public void setSaleSettlementNo(String saleSettlementNo) {
		this.saleSettlementNo = saleSettlementNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getSendBeginDate() {
		return sendBeginDate;
	}

	public void setSendBeginDate(Date sendBeginDate) {
		this.sendBeginDate = sendBeginDate;
	}

	public Date getSendEndDate() {
		return sendEndDate;
	}

	public void setSendEndDate(Date sendEndDate) {
		this.sendEndDate = sendEndDate;
	}

	public Date getReceiveBeginDate() {
		return receiveBeginDate;
	}

	public void setReceiveBeginDate(Date receiveBeginDate) {
		this.receiveBeginDate = receiveBeginDate;
	}

	public Date getReceiveEndDate() {
		return receiveEndDate;
	}

	public void setReceiveEndDate(Date receiveEndDate) {
		this.receiveEndDate = receiveEndDate;
	}

	public Double getSanctionPrice() {
		return sanctionPrice;
	}

	public void setSanctionPrice(Double sanctionPrice) {
		this.sanctionPrice = sanctionPrice;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "settlementId")
	public List<SaleSettlementOutRel> getOutRelList() {
		return outRelList;
	}

	public void setOutRelList(List<SaleSettlementOutRel> outRelList) {
		this.outRelList = outRelList;
	}
}
