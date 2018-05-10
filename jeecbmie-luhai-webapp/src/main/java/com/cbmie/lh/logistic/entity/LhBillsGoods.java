package com.cbmie.lh.logistic.entity;

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
 * 到单子表 - 货物明细
 */
@Entity
@Table(name = "LH_BILLS_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class LhBillsGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 采购合同号 **/
	private String purchaseContractNo;
	/** 综合合同号 **/
	private String innerContractNo;
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

	/** 类别 **/
	private String goodsCategory;
	/** 数量 **/
	private String goodsQuantity;
	/** 数量单位 **/
	private String goodsUnit;
	/** 采购单价 **/
	private String purchasePrice;
	/** 采购金额 **/
	private String amount;
	
	private String indicatorInformation;

	private String parentId;

	@JsonIgnore
	private List<LhBillsGoodsIndex> goodsIndexList = new ArrayList<LhBillsGoodsIndex>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<LhBillsGoodsIndex> getGoodsIndexList() {
		return goodsIndexList;
	}

	public void setGoodsIndexList(List<LhBillsGoodsIndex> goodsIndexList) {
		this.goodsIndexList = goodsIndexList;
	}
	
	@Column(name = "PARENT_ID", nullable = false)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPurchaseContractNo() {
		return purchaseContractNo;
	}

	public void setPurchaseContractNo(String purchaseContractNo) {
		this.purchaseContractNo = purchaseContractNo;
	}

	public String getInnerContractNo() {
		return innerContractNo;
	}

	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public String getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(String goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

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
}
