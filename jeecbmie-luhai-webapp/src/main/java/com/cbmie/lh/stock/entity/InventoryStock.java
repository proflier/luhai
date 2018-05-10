package com.cbmie.lh.stock.entity;

import java.util.Date;

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
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 库存盘点
 */
@Entity
@Table(name = "LH_INVENTORY_STOCK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InventoryStock extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 船编号
	 */
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

	/**
	 * 船名和编号
	 */
	private String shipNoAndName;
	
	@Column
	public String getShipNoAndName() {
		return shipNoAndName;
	}

	public void setShipNoAndName(String shipNoAndName) {
		this.shipNoAndName = shipNoAndName;
	}

	@Column
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	/**
	 * 盘点编码
	 */
	private String inventoryNo;

	/**
	 * 仓储明细ID
	 */
	private Long stockGoodsId;

	/**
	 * 入库新增商品id
	 */
	private Long newStockGoodsId;

	/**
	 * 内部合同号
	 */
	private String innerContractNo;

	/**
	 * 仓库编码
	 */
	private String warehouseCode;
	private String warehouseCodeView;

	@Transient
	public String getWarehouseCodeView() {
		warehouseCodeView = DictUtils.getCorpName(warehouseCode);
		return warehouseCodeView;
	}

	public void setWarehouseCodeView(String warehouseCodeView) {
		this.warehouseCodeView = warehouseCodeView;
	}

	/**
	 * 商品编码/名称
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
	 * 原始数量
	 */
	private double sourceQuantity;

	@Column
	public double getSourceQuantity() {
		return sourceQuantity;
	}

	public void setSourceQuantity(double sourceQuantity) {
		this.sourceQuantity = sourceQuantity;
	}

	/**
	 * 盘点数量
	 */
	private double inventoryQuantity;

	/** 数量单位 **/
	private String units;

	/**
	 * 盘库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date inventoryDate;

	/**
	 * 负责人
	 */
	private String inventoryPerson;

	@Column
	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	@Column
	public Long getStockGoodsId() {
		return stockGoodsId;
	}

	public void setStockGoodsId(Long stockGoodsId) {
		this.stockGoodsId = stockGoodsId;
	}

	@Column
	public Long getNewStockGoodsId() {
		return newStockGoodsId;
	}

	public void setNewStockGoodsId(Long newStockGoodsId) {
		this.newStockGoodsId = newStockGoodsId;
	}

	@Column
	public String getInnerContractNo() {
		return innerContractNo;
	}

	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}

	@Column
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	@Column
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column
	public double getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(double inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	@Column
	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@Column
	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	@Column
	public String getInventoryPerson() {
		return inventoryPerson;
	}

	public void setInventoryPerson(String inventoryPerson) {
		this.inventoryPerson = inventoryPerson;
	}

}