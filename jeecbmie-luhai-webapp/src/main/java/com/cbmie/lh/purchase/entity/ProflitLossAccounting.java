package com.cbmie.lh.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 盈亏核算
 * 
 * @author linxiaopeng 2016年7月8日
 */
@Entity
@Table(name = "LH_PROFLIT_LOSS_ACCOUNTING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ProflitLossAccounting extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 合同号
	 */
	private String contractNo;

	/**
	 * 材质
	 */
	private String material;

	/**
	 * 体积
	 */
	private String volume;

	/**
	 * 数量单位
	 */
	private String units;

	/**
	 * 采购美金单价
	 */
	private String priceProcurementDollars;

	/**
	 * 金额
	 */
	private String value;

	/**
	 * 海运费
	 */
	private String shippingFee;

	/**
	 * 装货费
	 */
	private String loadingCharges;

	/**
	 * 通知费+修改费+快递费
	 */
	private String extraFee;

	/**
	 * 人工费
	 */
	private String personFee;

	/**
	 * 港口费
	 */
	private String portFee;

	@Column
	public String getPersonFee() {
		return personFee;
	}

	@Column
	public void setPersonFee(String personFee) {
		this.personFee = personFee;
	}

	@Column
	public String getPortFee() {
		return portFee;
	}

	@Column
	public void setPortFee(String portFee) {
		this.portFee = portFee;
	}

	/**
	 * 资金成本
	 */
	private String capitalCost;

	/**
	 * 贴现
	 */
	private String discount;

	/**
	 * 议付费
	 */
	private String yeePay;

	/**
	 * 承兑手续费
	 */
	private String acceptanceFee;

	/**
	 * 保险费
	 */
	private String insurance;

	/**
	 * 销售美金单价
	 */
	private String unitSalesDollars;

	/**
	 * 利润
	 */
	private String profit;

	/**
	 * 利润合计
	 */
	private String totalProfit;

	/**
	 * 目的港
	 */
	private String destinationPort;

	/**
	 * 供应商
	 */
	private String supplier;

	@Column
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Column
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	@Column
	public String getPriceProcurementDollars() {
		return priceProcurementDollars;
	}

	public void setPriceProcurementDollars(String priceProcurementDollars) {
		this.priceProcurementDollars = priceProcurementDollars;
	}

	@Column
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column
	public String getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(String shippingFee) {
		this.shippingFee = shippingFee;
	}

	@Column
	public String getLoadingCharges() {
		return loadingCharges;
	}

	public void setLoadingCharges(String loadingCharges) {
		this.loadingCharges = loadingCharges;
	}

	@Column
	public String getExtraFee() {
		return extraFee;
	}

	public void setExtraFee(String extraFee) {
		this.extraFee = extraFee;
	}

	@Column
	public String getCapitalCost() {
		return capitalCost;
	}

	public void setCapitalCost(String capitalCost) {
		this.capitalCost = capitalCost;
	}

	@Column
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	@Column
	public String getYeePay() {
		return yeePay;
	}

	public void setYeePay(String yeePay) {
		this.yeePay = yeePay;
	}

	@Column
	public String getAcceptanceFee() {
		return acceptanceFee;
	}

	public void setAcceptanceFee(String acceptanceFee) {
		this.acceptanceFee = acceptanceFee;
	}

	@Column
	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	@Column
	public String getUnitSalesDollars() {
		return unitSalesDollars;
	}

	public void setUnitSalesDollars(String unitSalesDollars) {
		this.unitSalesDollars = unitSalesDollars;
	}

	@Column
	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	@Column
	public String getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}

	@Column
	public String getDestinationPort() {
		return destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	@Column
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

}
