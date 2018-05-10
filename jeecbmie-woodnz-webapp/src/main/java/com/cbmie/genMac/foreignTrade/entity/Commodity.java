package com.cbmie.genMac.foreignTrade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cbmie.common.entity.BaseEntity;

/**
 * 进口商品
 */
@Entity
@Table(name = "commodity")
public class Commodity extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 商品大类
	 */
	private String goodsCategory;

	/**
	 * 规格型号
	 */
	private String specification;

	/**
	 * 原产地
	 */
	private String origin;

	/**
	 * 英文名
	 */
	private String ename;

	/**
	 * 中文名
	 */
	private String cname;

	/**
	 * 数量
	 */
	private Double amount;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 单价
	 */
	private Double price;

	/**
	 * 原币金额
	 */
	private Double original;

	/**
	 * 关税税额
	 */
	private String tax;

	/**
	 * 关税税率
	 */
	private String taxRate;

	/**
	 * 消费税额
	 */
	private String saleTax;

	/**
	 * 消费税率
	 */
	private String saleTaxRate;

	/**
	 * 增值税额
	 */
	private String vat;

	/**
	 * 增值税率
	 */
	private String vatRate;
	
	/**
	 * 父id
	 */
	private Long parentId;
	
	@Column(name = "PARENT_ID", nullable = false)
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "GOODS_CATEGORY", nullable = false)
	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	@Column(name = "SPECIFICATION")
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "ORIGIN", nullable = true)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "ENAME", nullable = false)
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name = "CNNAME", nullable = true)
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "AMOUNT", nullable = false)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "UNIT", nullable = false)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "PRICE", nullable = false)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	@Column(name = "ORIGINAL", nullable = false)
	public Double getOriginal() {
		return original;
	}

	public void setOriginal(Double original) {
		this.original = original;
	}

	@Column(name = "TAX", nullable = true)
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	@Column(name = "TAX_RATE", nullable = true)
	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name = "SALE_TAX", nullable = true)
	public String getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(String saleTax) {
		this.saleTax = saleTax;
	}

	@Column(name = "SALE_TAX_RATE", nullable = true)
	public String getSaleTaxRate() {
		return saleTaxRate;
	}

	public void setSaleTaxRate(String saleTaxRate) {
		this.saleTaxRate = saleTaxRate;
	}

	@Column(name = "VAT", nullable = true)
	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	@Column(name = "VAT_RATE", nullable = true)
	public String getVatRate() {
		return vatRate;
	}

	public void setVatRate(String vatRate) {
		this.vatRate = vatRate;
	}

}