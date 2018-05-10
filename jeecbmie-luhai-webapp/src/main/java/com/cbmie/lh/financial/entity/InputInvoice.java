package com.cbmie.lh.financial.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "LH_INPUT_INVOICE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InputInvoice extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2036517446323368658L;
	
	/** 发票分类 */
	private String invoiceClassModel;
	/** 发票分类编码 */
	private String invoiceClassCode;
	/** 发票类型 */
	private String invoiceTypeModel;
	/** 发票类型编码 */
	private String invoiceTypeCode;
	/** 贸易方式 */
	private String tradeMode;
	/** 贸易方式 编码*/
	private String tradeCode;
	/** 客户名 */
	private String userName;
	private String userNameView;
	@Transient
	public String getUserNameView() {
		userNameView = DictUtils.getCorpName(userName);
		return userNameView;
	}
	public void setUserNameView(String userNameView) {
		this.userNameView = userNameView;
	}
	/** 账户 */
	private String account;
	/** 开户行 */
	private String accountBank;
	/** 开票单位 */
	private String issuingOffice;
	/** 开票单位编码 */
	private String issuingCode;
	/** 结算中心 */
	private String centerSettlement;
	/** 税票张数 */
	private int numStamps;
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
	
	private List<InputInvoiceSub> settleSubs = new ArrayList<InputInvoiceSub>();
	
	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInvoiceClassModel() {
		return invoiceClassModel;
	}
	public void setInvoiceClassModel(String invoiceClassModel) {
		this.invoiceClassModel = invoiceClassModel;
	}
	public String getInvoiceClassCode() {
		return invoiceClassCode;
	}
	public void setInvoiceClassCode(String invoiceClassCode) {
		this.invoiceClassCode = invoiceClassCode;
	}
	public String getInvoiceTypeModel() {
		return invoiceTypeModel;
	}
	public void setInvoiceTypeModel(String invoiceTypeModel) {
		this.invoiceTypeModel = invoiceTypeModel;
	}
	public String getInvoiceTypeCode() {
		return invoiceTypeCode;
	}
	public void setInvoiceTypeCode(String invoiceTypeCode) {
		this.invoiceTypeCode = invoiceTypeCode;
	}
	public String getTradeMode() {
		return tradeMode;
	}
	public void setTradeMode(String tradeMode) {
		this.tradeMode = tradeMode;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIssuingOffice() {
		return issuingOffice;
	}
	public void setIssuingOffice(String issuingOffice) {
		this.issuingOffice = issuingOffice;
	}
	public String getIssuingCode() {
		return issuingCode;
	}
	public void setIssuingCode(String issuingCode) {
		this.issuingCode = issuingCode;
	}
	public String getCenterSettlement() {
		return centerSettlement;
	}
	public void setCenterSettlement(String centerSettlement) {
		this.centerSettlement = centerSettlement;
	}
	public int getNumStamps() {
		return numStamps;
	}
	public void setNumStamps(int numStamps) {
		this.numStamps = numStamps;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="inputInvoiceSubId",orphanRemoval=true)
	public List<InputInvoiceSub> getSettleSubs() {                     
		return settleSubs;
	}
	public void setSettleSubs(List<InputInvoiceSub> settleSubs) {
		this.settleSubs = settleSubs;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountBank() {
		return accountBank;
	}
	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}
	
	
}
