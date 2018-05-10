package com.cbmie.lh.logistic.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 报关单商品子表
 */
@Entity
@Table(name = "LH_CUSTOMS_DECLARATION_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class CustomsDeclarationGoods {
	
	private Long id;
	
	/**
	 * 父id
	 */
	private Long pid;

	/**
	 * 项号
	 */
	private String itemNo;
	
	/**
	 * 商品编码
	 */
	private String goodsCode;
	
	/**
	 * 商品名称
	 */
	private String goodsName;
	
	/**
	 * 规格型号
	 */
	private String specifications;
	
	/**
	 * 数量
	 */
	private Double amount;
	
	/**
	 * 单位
	 */
	private String unit;
	
	/**
	 * 原产国（地区）
	 */
	private String originCountry;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 总价
	 */
	private Double total;
	
	/**
	 * 币制
	 */
	private String monetary;
	
	/**
	 * 征免
	 */
	private String shallBe;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getMonetary() {
		return monetary;
	}

	public void setMonetary(String monetary) {
		this.monetary = monetary;
	}

	public String getShallBe() {
		return shallBe;
	}

	public void setShallBe(String shallBe) {
		this.shallBe = shallBe;
	}
	
}
