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

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 销售合同子表
 * 
 *
 */
@Entity
@Table(name = "LH_SALE_CONTRACT_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleContractGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** 综合合同号 (暂时不用)**/
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
	private Double goodsQuantity;
	/** 数量单位 **/
	private String units;
	private String unitsView;
	@Transient
	public String getUnitsView() {
		unitsView = DictUtils.getDictSingleName(units);
		return unitsView;
	}

	public void setUnitsView(String unitsView) {
		this.unitsView = unitsView;
	}

	/** 单价 **/
	private String price;
	/** 金额 **/
	private String amount;
	
	private String indicatorInformation;
	/** 运输工具编号**/
	private String shipNo;
	private String shipNoView;
	@Transient
	public String getShipNoView() {
		shipNoView = DictUtils.getShipName(shipNo);
		return shipNoView;
	}

	public void setShipNoView(String shipNoView) {
		this.shipNoView = shipNoView;
	}

	private Long saleContractId;
	
	@JsonIgnore
	private List<SaleContractGoodsIndex> goodsIndexList = new ArrayList<SaleContractGoodsIndex>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "saleContractGoodsId")
	public List<SaleContractGoodsIndex> getGoodsIndexList() {
		return goodsIndexList;
	}

	public void setGoodsIndexList(List<SaleContractGoodsIndex> goodsIndexList) {
		this.goodsIndexList = goodsIndexList;
	}
	
	@Column(name = "SALECONTRACT_ID", nullable = false)
	public Long getSaleContractId() {
		return saleContractId;
	}
	public void setSaleContractId(Long saleContractId) {
		this.saleContractId = saleContractId;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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

	public Double getGoodsQuantity() {
		return goodsQuantity;
	}

	public void setGoodsQuantity(Double goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
}
