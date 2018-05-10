package com.cbmie.lh.sale.entity;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
/**销售发票*/
@Entity
@Table(name = "LH_SALE_INVOICE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleInvoice extends BaseEntity {
	/**编号*/
	private String invoiceNo;
	/**客户名称*/
	private String customerName;
	private String customerNameView;
	@Transient
	public String getCustomerNameView() {
		customerNameView = DictUtils.getCorpName(customerName);
		return customerNameView;
	}
	public void setCustomerNameView(String customerNameView) {
		this.customerNameView = customerNameView;
	}
	/**是否为预开票   0 否 1是*/
	private String preBilling="0";
	/**手填*/
	private String goodsName;
	/**备注*/
	private String remark;
	/**销售合同号*/
	private String saleContractNo;
	/**状态*/
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	
	/**
	 * 业务经办人
	 */
	private String businessManager;
	private String businessManagerView;
	
	
	private String manageMode;

	@Column(length=50)
	public String getManageMode() {
		return manageMode;
	}
	
	public void setManageMode(String manageMode) {
		this.manageMode = manageMode;
	}

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
	private List<SaleInvoiceSub> saleInvoiceSubs = new ArrayList<SaleInvoiceSub>();
	@JsonIgnore
	private List<SaleInvoiceOutRel> outRelList = new ArrayList<SaleInvoiceOutRel>();
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPreBilling() {
		return preBilling;
	}
	public void setPreBilling(String preBilling) {
		this.preBilling = preBilling;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "saleInvoiceId")
	public List<SaleInvoiceSub> getSaleInvoiceSubs() {
		return saleInvoiceSubs;
	}
	public void setSaleInvoiceSubs(List<SaleInvoiceSub> saleInvoiceSubs) {
		this.saleInvoiceSubs = saleInvoiceSubs;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSaleContractNo() {
		return saleContractNo;
	}
	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "invoiceId")
	public List<SaleInvoiceOutRel> getOutRelList() {
		return outRelList;
	}
	public void setOutRelList(List<SaleInvoiceOutRel> outRelList) {
		this.outRelList = outRelList;
	}
}
