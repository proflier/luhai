package com.cbmie.lh.sale.entity;

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
 * 销售放货&出库申请商品信息
 * 
 * @author sunjialiang
 */
@Entity
@Table(name = "LH_SALE_DELIVERY_GOODS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleDeliveryGoods extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -531529171875368614L;

	/** 发货码头 **/
	private String port;
	
	private String portView;
	@Transient
	public String getPortView() {
		portView = DictUtils.getCorpName(port);
		return portView;
	}

	public void setPortView(String portView) {
		this.portView = portView;
	}

	/** 合同编号(暂时不用) **/
	private String contractNo;
	/** 品名 **/
	private String goodsName;
	
	/**
	 * 规格型号
	 */
	private String specifications;
	
	/**
	 * 运输方式
	 */
	private String transportation;
	
	private String goodsNameView;
	@Transient
	public String getGoodsNameView() {
		goodsNameView = DictUtils.getGoodsInfoName(goodsName);
		return goodsNameView;
	}

	public void setGoodsNameView(String goodsNameView) {
		this.goodsNameView = goodsNameView;
	}

	/** 航次 **/
	private String voy;
	private String voyView;
	@Transient
	public String getVoyView() {
		voyView = DictUtils.getShipName(voy);
		return voyView;
	}

	public void setVoyView(String voyView) {
		this.voyView = voyView;
	}

	/** 煤种 **/
	private String category;
	/** 数量 **/
	private Double quantity;
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

	/** 备注 **/
	private String remark;
	/** 关联放货id ***/
	private Long saleDeliveryId;
	/** 销售放货货物Id ***/
	private Long saleContractGoodsId;
	/** 关联出库id ***/
	private Long outStockId;
	
	/**
	 * 船名和编号
	 */
	private String shipNoAndName;
	
	/**
	 * 运输方式
	 */
	private String transType;
	private String transTypeView;
	@Transient
	public String getTransTypeView() {
		transTypeView = DictUtils.getDictSingleName(transType);
		return transTypeView;
	}

	public void setTransTypeView(String transTypeView) {
		this.transTypeView = transTypeView;
	}

	/**
	 * 开单航次
	 */
	private String billVoyage;
	private String billVoyageView;
	@Transient
	public String getBillVoyageView() {
		billVoyageView = DictUtils.getShipName(billVoyage);
		return billVoyageView;
	}

	public void setBillVoyageView(String billVoyageView) {
		this.billVoyageView = billVoyageView;
	}

	@Column
	public String getBillVoyage() {
		return billVoyage;
	}

	public void setBillVoyage(String billVoyage) {
		this.billVoyage = billVoyage;
	}

	@Column
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	@Column
	public String getShipNoAndName() {
		return shipNoAndName;
	}

	public void setShipNoAndName(String shipNoAndName) {
		this.shipNoAndName = shipNoAndName;
	}

	@Column(name = "OUT_STOCK_ID",nullable = true)
	public Long getOutStockId() {
		return outStockId;
	}

	public void setOutStockId(Long outStockId) {
		this.outStockId = outStockId;
	}

	@Column(name="SALE_DELIVERY_ID", nullable = true)
	public Long getSaleDeliveryId() {
		return saleDeliveryId;
	}

	public void setSaleDeliveryId(Long saleDeliveryId) {
		this.saleDeliveryId = saleDeliveryId;
	}

	@Column
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column
	public String getVoy() {
		return voy;
	}

	public void setVoy(String voy) {
		this.voy = voy;
	}

	@Column
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
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
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSaleContractGoodsId() {
		return saleContractGoodsId;
	}

	public void setSaleContractGoodsId(Long saleContractGoodsId) {
		this.saleContractGoodsId = saleContractGoodsId;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}
	
}
