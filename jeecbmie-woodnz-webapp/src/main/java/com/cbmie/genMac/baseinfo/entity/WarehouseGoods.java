package com.cbmie.genMac.baseinfo.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 仓库货物
 */
public class WarehouseGoods  {


	/**
	 * 入库编号
	 */
	private String inStockId;

	/**
	 * 货权单位
	 */
	private String goodsAffiliates;

	/**
	 * 联系人
	 */
	private String contacts;

	/**
	 * 电话
	 */
	private String phoneNO;

	/**
	 * 合同号
	 */
	private String contractNO;

	/**
	 * 对应提单号
	 */
	private String invoiceNo;

	/**
	 * 入库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date inStockDate;

	/**
	 * 商品大类
	 */
	private String goodsCategory;

	/**
	 * 数量
	 */
	private Double amount;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 货物概要
	 */
	private String note;

	private String wareHouseGoodsId;

	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	public String getGoodsAffiliates() {
		return goodsAffiliates;
	}

	public void setGoodsAffiliates(String goodsAffiliates) {
		this.goodsAffiliates = goodsAffiliates;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhoneNO() {
		return phoneNO;
	}

	public void setPhoneNO(String phoneNO) {
		this.phoneNO = phoneNO;
	}

	public String getContractNO() {
		return contractNO;
	}

	public void setContractNO(String contractNO) {
		this.contractNO = contractNO;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInStockDate() {
		return inStockDate;
	}

	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
	}

	public String getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getWareHouseGoodsId() {
		return wareHouseGoodsId;
	}

	public void setWareHouseGoodsId(String wareHouseGoodsId) {
		this.wareHouseGoodsId = wareHouseGoodsId;
	}

	

	
}