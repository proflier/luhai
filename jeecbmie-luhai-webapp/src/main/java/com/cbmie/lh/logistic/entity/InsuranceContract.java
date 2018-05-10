package com.cbmie.lh.logistic.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;

/**
 * 保险合同主表
 * 
 */
@Entity
@Table(name = "LH_INSURANCE_CONTRACT")
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
	private String insuranceCompanyView;
	@Transient
	public String getInsuranceCompanyView() {
		insuranceCompanyView = DictUtils.getCorpName(insuranceCompany);
		return insuranceCompanyView;
	}

	public void setInsuranceCompanyView(String insuranceCompanyView) {
		this.insuranceCompanyView = insuranceCompanyView;
	}

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
	/** 船名 暂时停用**/
	private String shipName;
	/** 船编号 **/
	private String shipNo;
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
	/** 内部合同号 **/
	private String innerContractNo;
	/** 贸易类型 **/
	private String tradeCategory;
	/**
	 * 备注
	 */
	private String comment;
	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	
	/**
	 * 变更状态 0:无效 1：有效  2:变更中
	 */
	private String changeState = "1";
	/**
	 * 源id
	 */
	private Long sourceId;

	/**
	 * 变更id
	 */
	private Long pid;
	
	/**
	 * 变更原因
	 */
	private String changeReason;
	

	/**
	 * 帐套单位
	 */
	private String setUnit;
	
	/**
	 * 业务经办人
	 */
	private String businessManager;
	private String businessManagerView;

	@Column
	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	@Transient
	public String getBusinessManagerView() {
		businessManagerView = DictUtils.getUserNameByLoginName(businessManager);
		return businessManagerView;
	}

	public void setBusinessManagerView(String businessManagerView) {
		this.businessManagerView = businessManagerView;
	}
	
	@Column
	public String getSetUnit() {
		return setUnit;
	}

	public void setSetUnit(String setUnit) {
		this.setUnit = setUnit;
	}
	
	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getInnerContractNo() {
		return innerContractNo;
	}

	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getTradeCategory() {
		return tradeCategory;
	}

	public void setTradeCategory(String tradeCategory) {
		this.tradeCategory = tradeCategory;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(columnDefinition = "varchar(255) default '1'")
	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	
	
}
