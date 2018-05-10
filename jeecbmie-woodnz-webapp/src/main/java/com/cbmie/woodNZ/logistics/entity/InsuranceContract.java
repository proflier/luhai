package com.cbmie.woodNZ.logistics.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 保险合同主表
 * 
 * @author liminghao
 */
@Entity
@Table(name = "wood_insurance_contract")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InsuranceContract extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 合同号 **/
	private String contractNo;
	/** 是否已保 **/
	private String isInsurance;
	/** 保险公司 **/
	private String insuranceCompany;
	/** 保额 **/
	private Double money;
	/** 保额币种 **/
	private String moneyCurrency;
	/** 保费 **/
	private Double amount;
	/** 保费币种 **/
	private String amountCurrency;
	/**费率**/
	private Double exchangeRate;
	/** 船名 **/
	private String shipName;
	/** 船次 **/
	private String shipTime;
	/** 投保日期 **/
	private Date insuranceDate;
	/** 险种 **/
	private String type;
	/** 备注 **/
	private String remark;
	/** 采购合同号 **/
	private String purchaseContractNo;
	/** 被保险人 **/
	private String insurancePerson;
	/** 包装及数量 **/
	private String packAndNum;
	/**保险货物名称**/
	private String goodsName;
	/**装载运输工具**/
	private String transportTool;
	/** 赔款偿付地点 **/
	private String addr;
	/** 综合合同号 **/
	private String cpContractNo;
	/**
	 * 状态
	 */
	private String state = "草稿";
	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column
	public String getCpContractNo() {
		return cpContractNo;
	}
	public void setCpContractNo(String cpContractNo) {
		this.cpContractNo = cpContractNo;
	}
	@Column
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column
	public String getIsInsurance() {
		return isInsurance;
	}
	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}
	@Column
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
	@Column
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Column
	public String getMoneyCurrency() {
		return moneyCurrency;
	}
	public void setMoneyCurrency(String moneyCurrency) {
		this.moneyCurrency = moneyCurrency;
	}
	@Column
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column
	public String getAmountCurrency() {
		return amountCurrency;
	}
	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}
	@Column
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	@Column
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	@Column
	public String getShipTime() {
		return shipTime;
	}
	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}
	@Column
	public Date getInsuranceDate() {
		return insuranceDate;
	}
	public void setInsuranceDate(Date insuranceDate) {
		this.insuranceDate = insuranceDate;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column
	public String getPurchaseContractNo() {
		return purchaseContractNo;
	}
	public void setPurchaseContractNo(String purchaseContractNo) {
		this.purchaseContractNo = purchaseContractNo;
	}
	@Column
	public String getInsurancePerson() {
		return insurancePerson;
	}
	public void setInsurancePerson(String insurancePerson) {
		this.insurancePerson = insurancePerson;
	}
	@Column
	public String getPackAndNum() {
		return packAndNum;
	}
	public void setPackAndNum(String packAndNum) {
		this.packAndNum = packAndNum;
	}
	@Column
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column
	public String getTransportTool() {
		return transportTool;
	}
	public void setTransportTool(String transportTool) {
		this.transportTool = transportTool;
	}
	@Column
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
}
