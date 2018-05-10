package com.cbmie.woodNZ.stock.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name="v_wood_stock_statistic")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class StockStatistic implements Serializable {

	private static final long serialVersionUID = -2046846247415342986L;
	private Long id;
	/**仓库id*/
	private Long warehouse;
	/**仓库name*/
	private String warehouseName;
	/**仓库总件数  临时字段*/
	private Double totalAmount;
	/**商品编号*/
	private String goodsNo;
	/**商品名称*/
	private String goodsName;
	/**商品类别*/
	private String goodsType;
	/**入库件数*//*
	private Double inPieceNum;
	*//**出库件数*//*
	private Double outPieceNum;*/
	/**商品件数*/
	private Double num;
	/**商品片数*/
	private Double piece;
	/**商品数量*/
	private Double amount;
	/**商品绑定件数*/
	private Double bindNum;
	/**商品单位*/
	private String unit;
	/**商品*/
	private Double bindAmount;
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="WAREHOUSE")
	public Long getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Long warehouse) {
		this.warehouse = warehouse;
	}
	@Column(name="WAREHOUSENAME")
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
	@Column(name="GOODSNO")
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	@Column(name="GOODSNAME")
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column(name="GOODSTYPE")
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	@Column(name="NUM")
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	@Column(name="PIECE")
	public Double getPiece() {
		return piece;
	}
	public void setPiece(Double piece) {
		this.piece = piece;
	}
	@Column(name="AMOUNT")
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column(name="UNIT")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(name="BINDNUM")
	public Double getBindNum() {
		return bindNum;
	}
	public void setBindNum(Double bindNum) {
		this.bindNum = bindNum;
	}
	@Column(name="BINDAMOUNT")
	public Double getBindAmount() {
		return bindAmount;
	}
	public void setBindAmount(Double bindAmount) {
		this.bindAmount = bindAmount;
	}
	@Transient
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
