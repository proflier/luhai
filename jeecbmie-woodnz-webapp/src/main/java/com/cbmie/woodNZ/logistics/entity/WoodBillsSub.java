package com.cbmie.woodNZ.logistics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 到单子表 - 箱单信息
 */
@Entity
@Table(name = "wood_bills_sub")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodBillsSub extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 集装箱号 **/
	private String boxNo;

	/** 综合合同号 **/
	private String cpContractNo;
	
	/** 商品编码 **/
	private String goodsNo;

	/** 商品名称 **/
	private String goodsName;

	/** 商品描述 **/
	private String goodsDes;

	/** 类别 **/
	private String type;

	/** 品牌 &厂牌 **/
	private String brand;

	/** 材种 **/
	private String species;

	/** 等级 **/
	private String classes;

	/** 采购价 **/
	private double purchasePrice;

	/** 规格 **/
	private String spec;

	/** 长度 **/
	private String length;

	/** 数量 **/
	private double num;

	/** 数量单位 **/
	private String numberUnits;

	/** 件数 **/
	private double pieceNum;

	/**
	 * 实入件数
	 */
	private String realNumber;

	/** 片数 **/
	private double pNum;

	/**
	 * 实入片数
	 */
	private String realPiece;

	/**
	 * 立方数
	 */
	private String cubeNumber;

	/**
	 * 商品定损
	 */
	private String goodsLoss;

	/**
	 * 锁定销售单价
	 */
	private String lockSellingPrice;

	/**
	 * 锁定销售合同号
	 */
	private String contractNo;

	/** 币种 **/
	private String currency;

	/** 总价 **/
	private double totalPrice;

	/** 备注 **/
	private String remark;

	/** 是否濒危物种 **/
	private String ifDanger;

	/** 单片体积 **/
	private double volume;

	/**
	 * 关联id
	 */
	private String parentId;

	@Column
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	@Column(name = "PARENT_ID", nullable = false)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column
	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	@Column
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	@Column
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column
	public String getGoodsDes() {
		return goodsDes;
	}

	public void setGoodsDes(String goodsDes) {
		this.goodsDes = goodsDes;
	}

	@Column
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Column
	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	@Column
	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	@Column
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Column
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@Column
	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	@Column
	public String getNumberUnits() {
		return numberUnits;
	}

	public void setNumberUnits(String numberUnits) {
		this.numberUnits = numberUnits;
	}

	@Column
	public double getPieceNum() {
		return pieceNum;
	}

	public void setPieceNum(double pieceNum) {
		this.pieceNum = pieceNum;
	}

	@Column
	public double getpNum() {
		return pNum;
	}

	public void setpNum(double pNum) {
		this.pNum = pNum;
	}

	@Column
	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public String getIfDanger() {
		return ifDanger;
	}

	public void setIfDanger(String ifDanger) {
		this.ifDanger = ifDanger;
	}

	@Column
	public String getRealNumber() {
		return realNumber;
	}

	public void setRealNumber(String realNumber) {
		this.realNumber = realNumber;
	}

	@Column
	public String getRealPiece() {
		return realPiece;
	}

	public void setRealPiece(String realPiece) {
		this.realPiece = realPiece;
	}

	@Column
	public String getCubeNumber() {
		return cubeNumber;
	}

	public void setCubeNumber(String cubeNumber) {
		this.cubeNumber = cubeNumber;
	}

	@Column
	public String getGoodsLoss() {
		return goodsLoss;
	}

	public void setGoodsLoss(String goodsLoss) {
		this.goodsLoss = goodsLoss;
	}

	@Column
	public String getLockSellingPrice() {
		return lockSellingPrice;
	}

	public void setLockSellingPrice(String lockSellingPrice) {
		this.lockSellingPrice = lockSellingPrice;
	}

	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column
	public String getCpContractNo() {
		return cpContractNo;
	}

	public void setCpContractNo(String cpContractNo) {
		this.cpContractNo = cpContractNo;
	}
	
	/** 含水率 **/
	private Double waterRate;
	
	@Column
	public Double getWaterRate() {
		return waterRate;
	}

	public void setWaterRate(Double waterRate) {
		this.waterRate = waterRate;
	}
}
