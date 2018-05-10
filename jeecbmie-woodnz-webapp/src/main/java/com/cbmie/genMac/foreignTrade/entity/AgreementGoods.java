package com.cbmie.genMac.foreignTrade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 协议商品
 * @author czq
 */
@Entity
@Table(name = "agreement_goods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AgreementGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -433334685190014498L;

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
	 * 英文品名
	 */
	private String ename;
	
	/**
	 * 中文品名
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
	 * 人民币金额
	 */
	private Double rmb;
	
	/**
	 * 代理协议id
	 */
	private Long pid;

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

	@Column(name = "ORIGIN")
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "ENAME")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Column(name = "CNAME", nullable = false)
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

	@Column(name = "RMB", nullable = false)
	public Double getRmb() {
		return rmb;
	}

	public void setRmb(Double rmb) {
		this.rmb = rmb;
	} 
	
	@Column(name = "PID")
	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

}
