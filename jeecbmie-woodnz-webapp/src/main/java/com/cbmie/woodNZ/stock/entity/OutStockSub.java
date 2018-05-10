package com.cbmie.woodNZ.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 出库子表
 * 
 * @author liminghao
 */
@Entity
@Table(name = "wood_out_stock_sub")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OutStockSub extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 商品编号* */
	private String goodsNo;
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
	/** 可供件数* */
	private Double numAvailable;
	/** 可供片数* */
	private Double pieceAvailable;
	/** 可供数量* */
	private Double  amountAvailable;
	/** 在库件数* */
	private Double numFact;
	/** 在库片数* */
	private Double pieceFact;
	/** 实发件数* */
	private Double realsendPNum;
	/** 在库数量* */
	private Double amountFact;
	/** 件数（申请的）* */
	private Double num;
	/** 片数（申请的）* */
	private Double piece;
	/** 立方数（申请的）* */
	private Double amount;
	/** 数量单位* */
	private String unit; 
	 /** 成本单价 **/
	private Double costPrice;
	/** 单价**/
	private Double unitPrice;
	/** 销售价* */
	private Double money;
	/** 货物所在地点* */
	private String addr;//庞在溪出库追加专用字段
	/** 集装箱号* */
	private String containerNo;	
	/** 实发立方数* */
	private Double realsendVNum;
	/** 仓库 **/
	private String warehouse;
	/** 销售合同号* */
	private String saleContractNo;
	/** 綜合合同号* */
	private String cpContractNo;
	/** 数据源：下游交单；放货* */
	private String dataSource;	
	/** 入库主表ID* */
	private String inStockId;
	@Column
	public String getInStockId() {
		return inStockId;
	}
	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}
	@Column
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	@Column
	public String getCpContractNo() {
		return cpContractNo;
	}
	public void setCpContractNo(String cpContractNo) {
		this.cpContractNo = cpContractNo;
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
	
	/** 实际放货商品id，一对一关系* */
	private String RealSalesDeliveryGoodsId;
	
	@Column
	public String getRealSalesDeliveryGoodsId() {
		return RealSalesDeliveryGoodsId;
	}
	public void setRealSalesDeliveryGoodsId(String realSalesDeliveryGoodsId) {
		RealSalesDeliveryGoodsId = realSalesDeliveryGoodsId;
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
	public Double getNumAvailable() {
		return numAvailable;
	}
	public void setNumAvailable(Double numAvailable) {
		this.numAvailable = numAvailable;
	}
	@Column
	public Double getPieceAvailable() {
		return pieceAvailable;
	}
	public void setPieceAvailable(Double pieceAvailable) {
		this.pieceAvailable = pieceAvailable;
	}
	@Column
	public Double getAmountAvailable() {
		return amountAvailable;
	}
	public void setAmountAvailable(Double amountAvailable) {
		this.amountAvailable = amountAvailable;
	}
	@Column
	public Double getNumFact() {
		return numFact;
	}
	public void setNumFact(Double numFact) {
		this.numFact = numFact;
	}
	@Column
	public Double getPieceFact() {
		return pieceFact;
	}
	public void setPieceFact(Double pieceFact) {
		this.pieceFact = pieceFact;
	}
	@Column
	public Double getRealsendPNum() {
		return realsendPNum;
	}
	public void setRealsendPNum(Double realsendPNum) {
		this.realsendPNum = realsendPNum;
	}
	@Column
	public Double getAmountFact() {
		return amountFact;
	}
	public void setAmountFact(Double amountFact) {
		this.amountFact = amountFact;
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
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Column
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	@Column
	public Double getRealsendVNum() {
		return realsendVNum;
	}
	public void setRealsendVNum(Double realsendVNum) {
		this.realsendVNum = realsendVNum;
	}
	@Column
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getSaleContractNo() {
		return saleContractNo;
	}
	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
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
