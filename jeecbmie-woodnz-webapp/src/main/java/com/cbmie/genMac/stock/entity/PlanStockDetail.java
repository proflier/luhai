package com.cbmie.genMac.stock.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 盘库明细
 */
@Entity
@Table(name = "plan_stock_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PlanStockDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	/**
	 * 商品名称/规格型号
	 */
	private String goodsNameSpecification;
	
	/**
	 * 提单号
	 */
	private String invoiceNo;
	
	/**
	 * 入库编号
	 */
	private String inStockId;
	
	/**
	 * 入库数量
	 */
	private Double inStockAmount;
	
	/**
	 * 入库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date inStockDate;
	
	/**
	 * 发货单号
	 */
	private String sendGoodsNo;
	
	/**
	 * 发货数量
	 */
	private Double sendGoodsAmount;
	
	/**
	 * 签发日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
	private Date sendDate;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private Long parentId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "GOODS_NAME_SPECIFICATION")
	public String getGoodsNameSpecification() {
		return goodsNameSpecification;
	}

	public void setGoodsNameSpecification(String goodsNameSpecification) {
		this.goodsNameSpecification = goodsNameSpecification;
	}

	@Column(name = "INVOICE_NO")
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "IN_STOCK_ID")
	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	@Column(name = "IN_STOCK_AMOUNT")
	public Double getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(Double inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	@Column(name = "IN_STOCK_DATE")
	public Date getInStockDate() {
		return inStockDate;
	}

	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
	}

	@Column(name = "SEND_GOODS_NO")
	public String getSendGoodsNo() {
		return sendGoodsNo;
	}

	public void setSendGoodsNo(String sendGoodsNo) {
		this.sendGoodsNo = sendGoodsNo;
	}

	@Column(name = "SEND_GOODS_AMOUNT")
	public Double getSendGoodsAmount() {
		return sendGoodsAmount;
	}

	public void setSendGoodsAmount(Double sendGoodsAmount) {
		this.sendGoodsAmount = sendGoodsAmount;
	}

	@Column(name = "SEND_DATE")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "parent_id")
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}