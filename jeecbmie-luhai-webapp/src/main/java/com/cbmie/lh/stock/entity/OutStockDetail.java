package com.cbmie.lh.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 出库明细信息
 */
@Entity
@Table(name = "LH_OUT_STOCK_DETAIL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OutStockDetail extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	private String numbering;

	/**
	 * 码头
	 */
	private String shipment;

	/**
	 * 发货日期
	 */
	private String shipTime;

	/**
	 * 开单日期
	 */
	private String billTime;

	/**
	 * 销售合同号
	 */
	private String saleContractNo;

	/**
	 * 发货通知书编号
	 */
	private String deleveryNo;
	
	/**
	 * 主航次
	 */
	private String mainVoyage;


	/**
	 * 开单航次
	 */
	private String billVoyage;

	/**
	 * 调拨航次
	 */
	private String transferVoyage;

	/**
	 * 物料名称
	 */
	private String goodsName;
	
	/**
	 * 客户简称
	 */
	private String customerShort;
	
	/**
	 * 销售方式
	 */
	private String saleCategory;
	
	/**
	 * 运输方式
	 */
	private String transType;
	

	/**
	 * 承运商
	 */
	private String shipCompany;

	/**
	 * 磅单号
	 */
	private String poundsNo;

	/**
	 * 车牌号/火车卡号/船名
	 */
	private String license;

	/**
	 * 卡数/车数
	 */
	private String carNumber;

	/**
	 * 火车标重
	 */
	private String trainWeight;

	/**
	 * 火车轨道衡
	 */
	private String railwayTrackScale;

	/**
	 * 发货数量
	 */
	private String deliveryQuantity;

	/**
	 * "码头作业方式"
	 */
	private String shipmentMode;

	/**
	 * "是否拖头"
	 */
	private String tractor;

	/**
	 * 承运时间
	 */
	private String transportTime;

	/**
	 * 收货日期
	 */
	private String receiptDate;

	/**
	 * 收货数量
	 */
	private String receiptQunatity;

	/**
	 * 磅差
	 */
	private String poundsDifference;

	/**
	 * 扣运费
	 */
	private String transportFee;
	
	/**
	 * 配煤计划号
	 */
	private String planNo;
	
	/**
	 * 检验批号
	 */
	private String checkNo;

	/**
	 * 备注
	 */
	private String comment;

	private String outStockId;

	/**
	 * 装车楼电子数
	 */
	private String electQuantity;

	/**
	 * 计费重量
	 */
	private String chargeableWeight;

	/**
	 * 码头作业场地
	 */
	private String shipmentSpace;

	@Column
	public String getSaleContractNo() {
		return saleContractNo;
	}

	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	@Column
	public String getElectQuantity() {
		return electQuantity;
	}

	public void setElectQuantity(String electQuantity) {
		this.electQuantity = electQuantity;
	}

	@Column
	public String getChargeableWeight() {
		return chargeableWeight;
	}

	public void setChargeableWeight(String chargeableWeight) {
		this.chargeableWeight = chargeableWeight;
	}

	@Column
	public String getShipmentSpace() {
		return shipmentSpace;
	}

	public void setShipmentSpace(String shipmentSpace) {
		this.shipmentSpace = shipmentSpace;
	}

	@Column
	public String getOutStockId() {
		return outStockId;
	}

	public void setOutStockId(String outStockId) {
		this.outStockId = outStockId;
	}

	@Column
	public String getNumbering() {
		return numbering;
	}

	public void setNumbering(String numbering) {
		this.numbering = numbering;
	}

	@Column
	public String getShipTime() {
		return shipTime;
	}

	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}

	@Column
	public String getBillTime() {
		return billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	@Column
	public String getBillVoyage() {
		return billVoyage;
	}

	public void setBillVoyage(String billVoyage) {
		this.billVoyage = billVoyage;
	}

	@Column
	public String getTransferVoyage() {
		return transferVoyage;
	}

	public void setTransferVoyage(String transferVoyage) {
		this.transferVoyage = transferVoyage;
	}

	@Column
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column
	public String getShipCompany() {
		return shipCompany;
	}

	public void setShipCompany(String shipCompany) {
		this.shipCompany = shipCompany;
	}

	@Column
	public String getPoundsNo() {
		return poundsNo;
	}

	public void setPoundsNo(String poundsNo) {
		this.poundsNo = poundsNo;
	}

	@Column
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Column
	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	@Column
	public String getTrainWeight() {
		return trainWeight;
	}

	public void setTrainWeight(String trainWeight) {
		this.trainWeight = trainWeight;
	}

	@Column
	public String getRailwayTrackScale() {
		return railwayTrackScale;
	}

	public void setRailwayTrackScale(String railwayTrackScale) {
		this.railwayTrackScale = railwayTrackScale;
	}

	@Column
	public String getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(String deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	@Column
	public String getShipmentMode() {
		return shipmentMode;
	}

	public void setShipmentMode(String shipmentMode) {
		this.shipmentMode = shipmentMode;
	}

	@Column
	public String getTractor() {
		return tractor;
	}

	public void setTractor(String tractor) {
		this.tractor = tractor;
	}

	@Column
	public String getTransportTime() {
		return transportTime;
	}

	public void setTransportTime(String transportTime) {
		this.transportTime = transportTime;
	}

	@Column
	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	@Column
	public String getReceiptQunatity() {
		return receiptQunatity;
	}

	public void setReceiptQunatity(String receiptQunatity) {
		this.receiptQunatity = receiptQunatity;
	}

	@Column
	public String getPoundsDifference() {
		return poundsDifference;
	}

	public void setPoundsDifference(String poundsDifference) {
		this.poundsDifference = poundsDifference;
	}

	@Column
	public String getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(String transportFee) {
		this.transportFee = transportFee;
	}

	@Column
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column
	public String getShipment() {
		return shipment;
	}

	public void setShipment(String shipment) {
		this.shipment = shipment;
	}

	@Column
	public String getDeleveryNo() {
		return deleveryNo;
	}

	public void setDeleveryNo(String deleveryNo) {
		this.deleveryNo = deleveryNo;
	}

	@Column
	public String getMainVoyage() {
		return mainVoyage;
	}

	public void setMainVoyage(String mainVoyage) {
		this.mainVoyage = mainVoyage;
	}
	
	@Column
	public String getCustomerShort() {
		return customerShort;
	}

	public void setCustomerShort(String customerShort) {
		this.customerShort = customerShort;
	}

	@Column
	public String getSaleCategory() {
		return saleCategory;
	}

	public void setSaleCategory(String saleCategory) {
		this.saleCategory = saleCategory;
	}
	
	@Column
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	@Column
	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	@Column
	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	
	
}