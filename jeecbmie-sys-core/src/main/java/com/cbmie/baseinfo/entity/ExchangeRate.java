package com.cbmie.baseinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 汇率
 */
@Entity
@Table(name = "exchange_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ExchangeRate extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String showTime; 
	 
	private String year;
		
		
	private String month;
		
	private double rateUS;//对美元汇率
		
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getRateUS() {
		return rateUS;
	}

	public void setRateUS(double rateUS) {
		this.rateUS = rateUS;
	}

	public double getRateUN() {
		return rateUN;
	}

	public void setRateUN(double rateUN) {
		this.rateUN = rateUN;
	}

	public double getRateNZ() {
		return rateNZ;
	}

	public void setRateNZ(double rateNZ) {
		this.rateNZ = rateNZ;
	}

	private double rateUN;//对欧元汇率
		
	private double rateNZ;//对新西兰元汇率
	 
	 @Column(name = "SHOW_TIME") 
	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 对本币汇率
	 */
	private double exchangeRateSelf;

	/**
	 * 美元汇率
	 */
	private double exchangeRateUS;

	/**
	 * 对本币汇率买入价
	 */
	private double buyingRateSelf;

	/**
	 * 对美元汇率买入价
	 */
	private double buyingRateUS;

	/**
	 * 对本币汇率卖出价
	 */
	private double sellingRateSelf;

	/**
	 * 对美元汇率卖出价
	 */
	private double sellingRateUS;


	@Column(name = "CURRENCY")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "EXCHANGE_RATE_SELF")
	public double getExchangeRateSelf() {
		return exchangeRateSelf;
	}

	public void setExchangeRateSelf(double exchangeRateSelf) {
		this.exchangeRateSelf = exchangeRateSelf;
	}

	@Column(name = "EXCHANGE_RATE_US")
	public double getExchangeRateUS() {
		return exchangeRateUS;
	}

	public void setExchangeRateUS(double exchangeRateUS) {
		this.exchangeRateUS = exchangeRateUS;
	}

	@Column(name = "BUYING_RATE_SELF")
	public double getBuyingRateSelf() {
		return buyingRateSelf;
	}

	public void setBuyingRateSelf(double buyingRateSelf) {
		this.buyingRateSelf = buyingRateSelf;
	}

	@Column(name = "BUTING_RATE_US")
	public double getBuyingRateUS() {
		return buyingRateUS;
	}

	public void setBuyingRateUS(double buyingRateUS) {
		this.buyingRateUS = buyingRateUS;
	}

	@Column(name = "SELLING_RATE_SELF")
	public double getSellingRateSelf() {
		return sellingRateSelf;
	}

	public void setSellingRateSelf(double sellingRateSelf) {
		this.sellingRateSelf = sellingRateSelf;
	}

	@Column(name = "SELLING_RATE_US")
	public double getSellingRateUS() {
		return sellingRateUS;
	}

	public void setSellingRateUS(double sellingRateUS) {
		this.sellingRateUS = sellingRateUS;
	}

}