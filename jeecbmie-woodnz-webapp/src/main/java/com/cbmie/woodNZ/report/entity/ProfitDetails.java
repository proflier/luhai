package com.cbmie.woodNZ.report.entity;

import javax.persistence.Entity;

//@NamedNativeQueries({
//	@NamedNativeQuery(name = "profitDetails_123", query = "select * from wood_purchase_agreement")
//})
//@NamedNativeQueries({
//	@NamedNativeQuery(name = "profitDetails_123", query = ProfitDetails.sql, resultSetMapping = "profitDetails")
//	})
//@SqlResultSetMapping(name = "profitDetails", entities = @EntityResult(entityClass = com.cbmie.woodNZ.report.entity.ProfitDetails.class) )
public class ProfitDetails {
	public static final String sql = "select substring(a.goods_no, 1, 2) as goodType,substring(a.goods_no, 1, 2) as goodName,a.cp_contract_no as contract,"
			+ "b.htlb as tradeType,'' as tradeWay,a.number_units as unit,sum(c.s_amount) as salesQuantity,a.currency as currency,"
			+ "sum(c.s_money) as income,sum(a.total_price) as price,0 as transpot,sum(c.s_money) - sum(a.total_price) as profit "
			+ "from wood_in_stock_goods as a " + "join wood_cght_jk b on b.inter_contract_no = a.cp_contract_no "
			+ "join (select outsub.cp_contract_no,sum(amount) as s_amount,sum(outsub.money) as s_money from wood_out_stock_sub outsub group by outsub.cp_contract_no) c on c.cp_contract_no = a.cp_contract_no "
			+ "where 1=1 " + "and length(a.goods_no) = 13 " + "and substring(a.goods_no, 1, 2) <> 99 "
			+ "and a.cp_contract_no <> '' "
			+ "group by a.cp_contract_no,substring(a.goods_no, 1, 2),b.htlb,a.currency,a.number_units ";

	// 商品类别
	private String goodType;
	// 商品名称
	private String goodName;
	// 综合合同号
	private String contract;
	// 贸易类型
	private String tradeType;
	// 贸易方式
	private String tradeWay;
	// 数量单位
	private String unit;
	// 销售数量
	private double salesQuantity;
	// 币种
	private String currency;
	// 主营业务收入（销售价）
	private double income;
	// 进价
	private double price;
	// 运杂费
	private double transpot;
	// 商品利润
	private double profit;

	public String getGoodType() {
		return goodType;
	}

	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getSalesQuantity() {
		return salesQuantity;
	}

	public void setSalesQuantity(double salesQuantity) {
		this.salesQuantity = salesQuantity;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTranspot() {
		return transpot;
	}

	public void setTranspot(double transpot) {
		this.transpot = transpot;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

}
