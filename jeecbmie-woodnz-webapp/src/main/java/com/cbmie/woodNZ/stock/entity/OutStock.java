package com.cbmie.woodNZ.stock.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 出库主表
 * 
 * @author liminghao
 */
@Entity
@Table(name = "wood_out_stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OutStock extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 出库单号 **/
	private String outboundNo;
	/** 开单日期 **/
	private Date billDate;
	/** 提货单位 **/
	private String goodsUnit;
	/** 收款金额 **/
	private Double payAmount;
	/** 收款方式 **/
	private String payType;
	/** 收款日期 **/
	private Date payDate;
	/** 仓库 **/
	private String warehouse;
	/** 放货单号 **/
	private String sendGoodsNo;
	/** 数量 （原实发立方数）**/
	private Double realSendNum;
	/** 数量单位* */
	private String unit;
	/** 币种* */
	private String currency;
	/**
	 * 确认状态
	 */
	private String confirm;
	
	/** 对应的放货单 **/
	private Long woodSendGoodsid; 
	/** 系统编号（流水号） **/
	private String sysNo;
	/** 是否专案 **/
	private String ifProject="";//值为""，是，否；
	/** 备注 **/ 
	private String remark;
	 
	/** 放货主表ID **/ 
	private Long deliveryId;
	
	@Column(name = "delivery_id")
	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	
	/** 下游交单ID **/ 
	private Long downBillsId;
	
	@Column(name = "down_bills_id")
	public Long getDownBillsId() {
		return downBillsId;
	}

	public void setDownBillsId(Long downBillsId) {
		this.downBillsId = downBillsId;
	}
 
	/**
	 * 出库单子表
	 */
	@JsonIgnore
	private List<OutStockSub> subList = new ArrayList<OutStockSub>();
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<OutStockSub> getSubList() {
		return subList;
	}

	public void setSubList(List<OutStockSub> subList) {
		this.subList = subList;
	}

	@Column
	public String getOutboundNo() {
		return outboundNo;
	}

	public void setOutboundNo(String outboundNo) {
		this.outboundNo = outboundNo;
	}
	@Column
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	@Column
	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	@Column
	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	@Column
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	@Column
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	@Column
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	@Column
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	@Column
	public Long getWoodSendGoodsid() {
		return woodSendGoodsid;
	}

	public void setWoodSendGoodsid(Long woodSendGoodsid) {
		this.woodSendGoodsid = woodSendGoodsid;
	}
	@Column
	public Double getRealSendNum() {
		return realSendNum;
	}

	public void setRealSendNum(Double realSendNum) {
		this.realSendNum = realSendNum;
	}
	@Column
	public String getSysNo() {
		return sysNo;
	}

	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}
	@Column
	public String getSendGoodsNo() {
		return sendGoodsNo;
	}

	public void setSendGoodsNo(String sendGoodsNo) {
		this.sendGoodsNo = sendGoodsNo;
	}
	@Column
	public String getIfProject() {
		return ifProject;
	}

	public void setIfProject(String ifProject) {
		this.ifProject = ifProject;
	}
	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}