package com.cbmie.woodNZ.reportForm.entity;

/**
 * 存货明细
 */
public class StockDetail {

	// 商品名称
	private String goodname;
	// 贸易类型
	private String tradetype;
	// 贸易方式
	private String tradeway;
	// 仓库
	private String warehouse;

	public String getGoodname() {
		return goodname;
	}

	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getTradeway() {
		return tradeway;
	}

	public void setTradeway(String tradeway) {
		this.tradeway = tradeway;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRealstock() {
		return realstock;
	}

	public void setRealstock(String realstock) {
		this.realstock = realstock;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getInstockfee() {
		return instockfee;
	}

	public void setInstockfee(String instockfee) {
		this.instockfee = instockfee;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTranspot() {
		return transpot;
	}

	public void setTranspot(String transpot) {
		this.transpot = transpot;
	}

	public String getMouth() {
		return mouth;
	}

	public void setMouth(String mouth) {
		this.mouth = mouth;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getSecondSeason() {
		return secondSeason;
	}

	public void setSecondSeason(String secondSeason) {
		this.secondSeason = secondSeason;
	}

	public String getSixMouth() {
		return sixMouth;
	}

	public void setSixMouth(String sixMouth) {
		this.sixMouth = sixMouth;
	}

	// 数量单位
	private String unit;
	// 业务实存数量
	private String realstock;
	// 币种
	private String currency;
	// 库存合计
	private String instockfee;
	// 进价
	private String price;
	// 运杂费
	private String transpot;
	private String mouth;
	private String season;
	private String secondSeason;
	private String sixMouth;

}
