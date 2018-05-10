package com.cbmie.genMac.logistics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 到单商品
 * @author czq
 */
@Entity
@Table(name = "invoice_goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InvoiceGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -531529171875368614L;

	/**
	 * 商品大类
	 */
	private String goodsCategory;

	/**
	 * 规格型号
	 */
	private String specification;
	
	/**
	 * 车架号
	 */
	private String frameNo;

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
	
	@Column(name = "GOODS_CATEGORY", nullable = false)
	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	
	@Column
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "FRAME_NO")
	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	@Column
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column
	public Double getOriginal() {
		return original;
	}

	public void setOriginal(Double original) {
		this.original = original;
	}

	@Column
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	@Column(name = "TAX_RATE")
	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name = "SALE_TAX")
	public String getSaleTax() {
		return saleTax;
	}

	public void setSaleTax(String saleTax) {
		this.saleTax = saleTax;
	}

	@Column(name = "SALE_TAX_RATE")
	public String getSaleTaxRate() {
		return saleTaxRate;
	}

	public void setSaleTaxRate(String saleTaxRate) {
		this.saleTaxRate = saleTaxRate;
	}

	@Column(name = "VAT")
	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	@Column(name = "VAT_RATE")
	public String getVatRate() {
		return vatRate;
	}

	public void setVatRate(String vatRate) {
		this.vatRate = vatRate;
	}

	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
