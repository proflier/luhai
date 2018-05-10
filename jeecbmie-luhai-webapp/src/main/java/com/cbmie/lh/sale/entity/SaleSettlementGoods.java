package com.cbmie.lh.sale.entity;

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
@Entity
@Table(name = "LH_SALE_SETTLEMENT_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleSettlementGoods extends BaseEntity {
	/**
	 * 船次
	 */
	private String ship;
	/** 类别 **/
	private String goodsCategory;
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
	/** 发货日期 （暂停使用） **/
	private Date sendDate;
	/** 收货日期 （暂停使用） **/
	private Date receiveDate;
	/** 发货数量 **/
	private Double sendQuantity;
	/** 收货数量 **/
	private Double receiveQuantity;
	/** 结算数量 **/
	private Double settlementQuantity;
	/** 结算单价 **/
	private Double settlementPrice;
	/** 扣减金额 **/
	private Double deductionPrice;
	/** 已收金额 **/
	private Double receivedPrice;
	/** 应收金额 **/
	private Double receivablePrice;
	/** 基准单价 **/
	private Double basePrice;
	/** 结算金额 **/
	private Double settlementTotalPrice;
	/** 备注 **/
	private String remark;
	
	private Long saleSettlementId;
	
	private String saleContractNo;
	
	@Column(length=50)
	public String getShip() {
		return ship;
	}
	public void setShip(String ship) {
		this.ship = ship;
	}
	@Column(length=20)
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public Double getSendQuantity() {
		return sendQuantity;
	}
	public void setSendQuantity(Double sendQuantity) {
		this.sendQuantity = sendQuantity;
	}
	public Double getReceiveQuantity() {
		return receiveQuantity;
	}
	public void setReceiveQuantity(Double receiveQuantity) {
		this.receiveQuantity = receiveQuantity;
	}
	public Double getSettlementQuantity() {
		return settlementQuantity;
	}
	public void setSettlementQuantity(Double settlementQuantity) {
		this.settlementQuantity = settlementQuantity;
	}
	public Double getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(Double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public Double getDeductionPrice() {
		return deductionPrice;
	}
	public void setDeductionPrice(Double deductionPrice) {
		this.deductionPrice = deductionPrice;
	}
	public Double getReceivedPrice() {
		return receivedPrice;
	}
	public void setReceivedPrice(Double receivedPrice) {
		this.receivedPrice = receivedPrice;
	}
	public Double getReceivablePrice() {
		return receivablePrice;
	}
	public void setReceivablePrice(Double receivablePrice) {
		this.receivablePrice = receivablePrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "SALESETTLEMENT_ID", nullable = false)
	public Long getSaleSettlementId() {
		return saleSettlementId;
	}
	public void setSaleSettlementId(Long saleSettlementId) {
		this.saleSettlementId = saleSettlementId;
	}
	public String getGoodsCategory() {
		return goodsCategory;
	}
	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	@Transient
	public String getSaleContractNo() {
		return saleContractNo;
	}
	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}
	public Double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	public Double getSettlementTotalPrice() {
		return settlementTotalPrice;
	}
	public void setSettlementTotalPrice(Double settlementTotalPrice) {
		this.settlementTotalPrice = settlementTotalPrice;
	}
}
