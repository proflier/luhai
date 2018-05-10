package com.cbmie.lh.purchase.entity;

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

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 采购合同-子表信息
 */
@Entity
@Table(name = "LH_PURCHASE_CONTRACT_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PurchaseContractGoods extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 种类
	 */
	private String goodsCategory;

	/**
	 * 品名
	 */
	private String goodsName;
	private String goodsNameView;
	@Transient
	public String getGoodsNameView() {
		goodsNameView = DictUtils.getGoodsInfoName(goodsName);
		return goodsNameView;
	}

	public void setGoodsNameView(String goodsNameView) {
		this.goodsNameView = goodsNameView;
	}

	/**
	 * 数量
	 */
	private double goodsQuantity;
	
	/** 数量单位 **/
	private String units;
	
	private String unitsView;

	/**
	 * 采购单价
	 */
	private String purchasePrice;

	/**
	 * 金额
	 */
	private String amount;

	/**
	 * 指标信息
	 */
	private String indicatorInformation;

	private long purchaseContractId;
	
	private Long saleContractGoodsId;

	@Column(nullable = true)
	public Long getSaleContractGoodsId() {
		return saleContractGoodsId;
	}

	public void setSaleContractGoodsId(Long saleContractGoodsId) {
		this.saleContractGoodsId = saleContractGoodsId;
	}

	@JsonIgnore
	private List<PurchaseGoodsIndex> purchaseGoodsIndexList = new ArrayList<PurchaseGoodsIndex>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<PurchaseGoodsIndex> getPurchaseGoodsIndexList() {
		return purchaseGoodsIndexList;
	}

	public void setPurchaseGoodsIndexList(List<PurchaseGoodsIndex> purchaseGoodsIndexList) {
		this.purchaseGoodsIndexList = purchaseGoodsIndexList;
	}

	@Column
	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	@Column
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column
	public double getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(double goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	@Column
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@Transient
	public String getUnitsView() {
		unitsView = DictUtils.getDictSingleName(units);
		return unitsView;
	}

	public void setUnitsView(String unitsView) {
		this.unitsView = unitsView;
	}
	
	@Column
	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Column
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	
	@Transient
	public String getIndicatorInformation() {
		return indicatorInformation;
	}

	public void setIndicatorInformation(String indicatorInformation) {
		this.indicatorInformation = indicatorInformation;
	}

	@Column
	public long getPurchaseContractId() {
		return purchaseContractId;
	}

	public void setPurchaseContractId(long purchaseContractId) {
		this.purchaseContractId = purchaseContractId;
	}

}