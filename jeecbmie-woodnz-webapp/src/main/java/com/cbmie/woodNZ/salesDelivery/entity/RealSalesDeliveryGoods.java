package com.cbmie.woodNZ.salesDelivery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 销售放货实际商品信息
 * @author linxiaopeng
 * 2016年8月5日
 */
@Entity
@Table(name = "WOOD_REAL_SALES_DELIVERY_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class RealSalesDeliveryGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -531529171875368614L;

	/** 商品编号* */
	private String goodsNo;
	/** 综合合同号 **/
	private String cpContractNo;
	public String getCpContractNo() {
		return cpContractNo;
	}

	public void setCpContractNo(String cpContractNo) {
		this.cpContractNo = cpContractNo;
	}

	/** 商品名称* */
	private String goodsName;
	/** 品牌* */
	private String brand;
	/** 类别* */
	private String type;
	/** 材种* */
	private String species;
	/** 等级* */
	private String classes;
	/** 规格* */
	private String spec;
	/** 长度* */
	private String length;
	/** 包装* */
	private String packages;
	/** 件数* */
	private Double num;
	/** 片数* */
	private Double piece;
	/** 数量* */
	private Double amount;
	/** 数量单位* */
	private String unit;
	/** 单价* */
	private Double unitPrice;
	/** 金额* */
	private Double money;
	/** 单片体积* */
	private Double singleVolume;
	/** 入库数量* */
	private Double inStockNum;
	/** 可供数量* */
	private Double amountAvailable;
	/** 可供片数* */
	private Double pieceAvailable;
	/** 可供件数* */
	private Double numAvailable;
	/** 币种* */
	private String currency;
	/** 增值税率* */
	private Double taxRate;
	/** 集装箱号* */
	private String boxNo;
	/** 仓库 **/
	private String warehouse;
	/** 来源* */
	private String source;
	/** 提单号* */
	private String billsNo;
	
	/***立方数***/
	private String cubeNumber;
	/***可选立方数***/
	private String shouleCubeNumber;
	
	/** 含水率 **/
	private Double waterRate;
	
	@Column
	public Double getWaterRate() {
		return waterRate;
	}

	public void setWaterRate(Double waterRate) {
		this.waterRate = waterRate;
	}
	
	@Column
	public String getCubeNumber() {
		return cubeNumber;
	}
	public void setCubeNumber(String cubeNumber) {
		this.cubeNumber = cubeNumber;
	}
	
	@Column
	public String getShouleCubeNumber() {
		return shouleCubeNumber;
	}
	public void setShouleCubeNumber(String shouleCubeNumber) {
		this.shouleCubeNumber = shouleCubeNumber;
	}

	/**
	 * 关联id
	 */
	private Long parentId;
	
	@Column(name = "PARENT_ID", nullable = false)
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	@Column
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}
	@Column
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	@Column
	public Double getPiece() {
		return piece;
	}

	public void setPiece(Double piece) {
		this.piece = piece;
	}
	@Column
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column
	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	@Column
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	@Column
	public Double getSingleVolume() {
		return singleVolume;
	}

	public void setSingleVolume(Double singleVolume) {
		this.singleVolume = singleVolume;
	}
	@Column
	public Double getInStockNum() {
		return inStockNum;
	}

	public void setInStockNum(Double inStockNum) {
		this.inStockNum = inStockNum;
	}
	@Column
	public Double getAmountAvailable() {
		return amountAvailable;
	}

	public void setAmountAvailable(Double amountAvailable) {
		this.amountAvailable = amountAvailable;
	}
	@Column
	public Double getPieceAvailable() {
		return pieceAvailable;
	}

	public void setPieceAvailable(Double pieceAvailable) {
		this.pieceAvailable = pieceAvailable;
	}
	@Column
	public Double getNumAvailable() {
		return numAvailable;
	}

	public void setNumAvailable(Double numAvailable) {
		this.numAvailable = numAvailable;
	}
	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Column
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	@Column
	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}
	@Column
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	@Column
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	@Column
	public String getBillsNo() {
		return billsNo;
	}

	public void setBillsNo(String billsNo) {
		this.billsNo = billsNo;
	}
}
