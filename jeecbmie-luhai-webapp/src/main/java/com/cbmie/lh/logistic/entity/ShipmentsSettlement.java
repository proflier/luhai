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
@Table(name = "LH_SHIPMENTS_SETTLEMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ShipmentsSettlement extends BaseEntity {

	/**结算日期*/
	private Date settleDate;
	/**户名*/
	private String accountName;
	/**开户行*/
	private String bank;
	/**账号*/
	private String account;
	/**订船合同*/
	private String contractNo;
	/**订船合同*/
	private String contractNoOld;
	/**订船类型*/
	private String tradeCategory;
	
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
	
	private String state=ActivitiConstant.ACTIVITI_DRAFT;
	
	private List<ShipmentsSettlementSub> settleSubs = new ArrayList<ShipmentsSettlementSub>();
	
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="shipmentsSettleId",orphanRemoval=true)
	public List<ShipmentsSettlementSub> getSettleSubs() {
		return settleSubs;
	}
	public void setSettleSubs(List<ShipmentsSettlementSub> settleSubs) {
		this.settleSubs = settleSubs;
	}
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
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getContractNoOld() {
		return contractNoOld;
	}
	public void setContractNoOld(String contractNoOld) {
		this.contractNoOld = contractNoOld;
	}
}
