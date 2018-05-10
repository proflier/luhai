package com.cbmie.lh.stock.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 进销存台账
 */
@Entity
@Table(name = "LH_IN_AND_OUT_ACCOUNT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InAndOutAccount extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date date;

	/**
	 * 仓库
	 */
	private String warehouse;

	/**
	 * 单据类别
	 */
	private String category;

	/**
	 * 客户/码头
	 */
	private String client;

	/**
	 * 船次
	 */
	private String voyage;

	/**
	 * 物料名称
	 */
	private String goodsName;

	/**
	 * 入库吨数
	 */
	private String inStockQuantity;

	/**
	 * 出库吨数
	 */
	private String outStockQuantity;

	/**
	 * 结余吨数
	 */
	private String surplusQuantity;

	/**
	 * 备注
	 */
	private String comments;

	@Column
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Column
	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	@Column
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column
	public String getInStockQuantity() {
		return inStockQuantity;
	}

	public void setInStockQuantity(String inStockQuantity) {
		this.inStockQuantity = inStockQuantity;
	}

	@Column
	public String getOutStockQuantity() {
		return outStockQuantity;
	}

	public void setOutStockQuantity(String outStockQuantity) {
		this.outStockQuantity = outStockQuantity;
	}

	@Column
	public String getSurplusQuantity() {
		return surplusQuantity;
	}

	public void setSurplusQuantity(String surplusQuantity) {
		this.surplusQuantity = surplusQuantity;
	}

	@Column
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}