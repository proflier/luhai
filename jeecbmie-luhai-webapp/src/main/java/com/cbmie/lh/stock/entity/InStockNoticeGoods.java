package com.cbmie.lh.stock.entity;

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
@Table(name = "LH_IN_STOCK_NOTICE_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InStockNoticeGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

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
	 * 运输方式
	 */
	private String transportCategory;
	
	private String transportCategoryView;
	
	@Transient
	public String getTransportCategoryView() {
		transportCategoryView = DictUtils.getDictSingleName(transportCategory);
		return transportCategoryView;
	}

	public void setTransportCategoryView(String transportCategoryView) {
		this.transportCategoryView = transportCategoryView;
	}

	/**
	 * 运输编号
	 */
	private String shipNo;

	private String inStockNoticeId;

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

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public double getQuantityCIQ() {
		return quantityCIQ;
	}

	public void setQuantityCIQ(double quantityCIQ) {
		this.quantityCIQ = quantityCIQ;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getTransportCategory() {
		return transportCategory;
	}

	public void setTransportCategory(String transportCategory) {
		this.transportCategory = transportCategory;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getInStockNoticeId() {
		return inStockNoticeId;
	}

	public void setInStockNoticeId(String inStockNoticeId) {
		this.inStockNoticeId = inStockNoticeId;
	}

}
