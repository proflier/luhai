package com.cbmie.lh.financial.entity;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "LH_INPUT_INVOICESUB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InputInvoiceSub extends BaseEntity {

	/** 采购订单号 */
	private String purchaseOrderNumber;
	/** 相关销售订单 */
	private String relatedSalesOrder;
	/** 发票号 */
	private String invoiceNo;
	/** 开票日期 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date billingDate;
	/** 品名 */
	private String productName;
	private String productNameView;
	@Transient
	public String getProductNameView() {
		productNameView = DictUtils.getGoodsInfoName(productName);
		return productNameView;
	}

	public void setProductNameView(String productNameView) {
		this.productNameView = productNameView;
	}
	/** 计量单位 */
	private String unitMeasurement;
	private String unitMeasurementView;
	@Transient
	public String getUnitMeasurementView() {
		unitMeasurementView = DictUtils.getDictSingleName(unitMeasurement);
		return unitMeasurementView;
	}

	public void setUnitMeasurementView(String unitMeasurementView) {
		this.unitMeasurementView = unitMeasurementView;
	}
	/** 计量单位编码 */
	private String unitMeasureCode;
	

	/** 数量 */
	private int mount;
	/** 开票单价 */
	private Double prices;
	/** 开票金额 */
	private Double allPrices;
	/** 税额 */
	private Double taxMoney;
	/** 退税率 */
	private Double rebateRate;
	
	
	private Long inputInvoiceSubId;
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getRelatedSalesOrder() {
		return relatedSalesOrder;
	}

	public void setRelatedSalesOrder(String relatedSalesOrder) {
		this.relatedSalesOrder = relatedSalesOrder;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUnitMeasurement() {
		return unitMeasurement;
	}

	public void setUnitMeasurement(String unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	public int getMount() {
		return mount;
	}

	public void setMount(int mount) {
		this.mount = mount;
	}

	public Double getPrices() {
		return prices;
	}

	public void setPrices(Double prices) {
		this.prices = prices;
	}

	public Double getAllPrices() {
		return allPrices;
	}

	public void setAllPrices(Double allPrices) {
		this.allPrices = allPrices;
	}

	public Double getTaxMoney() {
		return taxMoney;
	}

	public void setTaxMoney(Double taxMoney) {
		this.taxMoney = taxMoney;
	}

	public Double getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(Double rebateRate) {
		this.rebateRate = rebateRate;
	}

	

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}
	
	public Long getInputInvoiceSubId() {
		return inputInvoiceSubId;
	}

	public void setInputInvoiceSubId(Long inputInvoiceSubId) {
		this.inputInvoiceSubId = inputInvoiceSubId;
	}

	public String getUnitMeasureCode() {
		return unitMeasureCode;
	}

	public void setUnitMeasureCode(String unitMeasureCode) {
		this.unitMeasureCode = unitMeasureCode;
	}
	
}
