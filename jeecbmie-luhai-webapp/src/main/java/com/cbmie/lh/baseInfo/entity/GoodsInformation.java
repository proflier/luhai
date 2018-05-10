package com.cbmie.lh.baseInfo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 商品信息
 * */
@Entity
@Table(name="LH_GOODSINFORMATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class GoodsInformation extends BaseEntity {
	
	/**
	 * 商品类别编码
	 * */
	private String goodsTypeCode;
	/**
	 * 商品名称
	 * */
	private String infoName;
	/**
	 * 商品英文名称
	 * */
	private String infoNameE;
	/**
	 * 商品编码
	 * */
	private String infoCode;
	/**
	 * 供应商编码
	 * */
	private String customerCode;
	
	/**
	 * 状态：1启用、0停用
	 */
	private Integer status;
	
	@Column
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonIgnore
	List<GoodsInfoIndexRel> infoIndexRels = new ArrayList<GoodsInfoIndexRel>();
	
	@Column(length=50)
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}
	@Column(length=50)
	public String getInfoName() {
		return infoName;
	}
	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	@Column(length=50)
	public String getInfoNameE() {
		return infoNameE;
	}
	public void setInfoNameE(String infoNameE) {
		this.infoNameE = infoNameE;
	}
	@Column(length=50)
	public String getInfoCode() {
		return infoCode;
	}
	public void setInfoCode(String infoCode) {
		this.infoCode = infoCode;
	}
	@Column(length=50)
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "infoId")
	public List<GoodsInfoIndexRel> getInfoIndexRels() {
		return infoIndexRels;
	}
	public void setInfoIndexRels(List<GoodsInfoIndexRel> infoIndexRels) {
		this.infoIndexRels = infoIndexRels;
	}
}
