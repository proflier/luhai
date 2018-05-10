package com.cbmie.lh.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;

/**
 * 库存子表
 */
@Entity
@Table(name = "LH_IN_STOCK_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InStockGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 到单编号
	 */
	private String billNo;
	
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * 内部合同号
	 */
	private String innerContractNo;

	/** 商品名称/编码 **/
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

	/** 仓库名称/编码 **/
	private String warehouseName;
	private String warehouseNameView;
	@Transient
	public String getWarehouseNameView() {
		warehouseNameView = DictUtils.getCorpName(warehouseName);
		return warehouseNameView;
	}

	public void setWarehouseNameView(String warehouseNameView) {
		this.warehouseNameView = warehouseNameView;
	}

	/**
	 * CIQ数量
	 */
	private double quantityCIQ;

	/** 入库数量 **/
	private double quantity;

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

	/**
	 * 库存成本
	 */
	private double stockCost;

	@Column
	public double getStockCost() {
		return stockCost;
	}

	public void setStockCost(double stockCost) {
		this.stockCost = stockCost;
	}

	/**
	 * 属性 0:初始库存 1:经过调配的库存 2:盘库 3：历史库存(上期结存)
	 */
	private String instockCategory = "0";

	private String parentId;

	@Column
	public String getInnerContractNo() {
		return innerContractNo;
	}

	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}

	@Column
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Column
	public double getQuantityCIQ() {
		return quantityCIQ;
	}

	public void setQuantityCIQ(double quantityCIQ) {
		this.quantityCIQ = quantityCIQ;
	}

	@Column
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Column
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@Column
	public String getInstockCategory() {
		return instockCategory;
	}

	public void setInstockCategory(String instockCategory) {
		this.instockCategory = instockCategory;
	}

	@Column
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
