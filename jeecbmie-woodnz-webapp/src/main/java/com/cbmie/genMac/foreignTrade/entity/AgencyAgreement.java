package com.cbmie.genMac.foreignTrade.entity;

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

/**
 * 代理协议
 * @author czq
 */
@Entity
@Table(name = "agency_agreement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class AgencyAgreement extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -595260427222943439L;

	/**
	 * 代理协议号
	 */
	private String agencyAgreementNo;
	
	/**
	 * 协议类型
	 */
	private String agreementType;
	
	/**
	 * 状态
	 */
	private String state = "草稿";
	
	/**
	 * 业务员
	 */
	private String salesman;
	
	/**
	 * 签署日期
	 */
	private Date signingDate;
	
	/**
	 * 生效日期
	 */
	private Date effectDate; 
	
	/**
	 * 是否是保税区业务
	 */
	private int bondedArea;
	
	/**
	 * 我司单位
	 */
	private String ourUnit;
	
	/**
	 * 签约地
	 */
	private String signedPlace;
	
	/**
	 * 委托方
	 */
	private String client;
	
	/**
	 * 所属地区
	 */
	private String district;
	
	/**
	 * 代理费方式
	 */
	private String agencyFeeWay;
	
	/**
	 * 手续费基数
	 */
	private Double commissionBase;
	
	/**
	 * 每美元多少人民币
	 */
	private Double everyDollarToRMB;
	
	/**
	 * 代理费率%
	 */
	private Double agencyFee;
	
	/**
	 * 代理费收入
	 */
	private Double agencyFeeIncome;
	
	/**
	 * 国内保证金比例%
	 */
	private Double domesticMarginRatio;
	
	/**
	 * 其他保证金说明
	 */
	private String otherMarginExplain;
	
	/**
	 * 发函人
	 */
	private Integer sentLetterPerson;
	
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 对人民币汇率
	 */
	private Double rmbRate;
	
	/**
	 * 对美元汇率
	 */
	private Double dollarRate;
	
	/**
	 * 协议商品
	 */
	private List<AgreementGoods> agreementGoods = new ArrayList<AgreementGoods>();
	
	@Column(name = "AGENCY_AGREEMENT_NO", nullable = false, unique = true)
	public String getAgencyAgreementNo() {
		return agencyAgreementNo;
	}

	public void setAgencyAgreementNo(String agencyAgreementNo) {
		this.agencyAgreementNo = agencyAgreementNo;
	}

	@Column(name = "AGREEMENT_TYPE", nullable = false)
	public String getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	@Column(name = "STATE", nullable = false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "SALESMAN", nullable = false)
	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	@Column(name = "SIGNING_DATE", nullable = false)
	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	@Column(name = "EFFECT_DATE")
	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}

	@Column(name = "BONDED_AREA", nullable = false)
	public int getBondedArea() {
		return bondedArea;
	}

	public void setBondedArea(int bondedArea) {
		this.bondedArea = bondedArea;
	}

	@Column(name = "OUR_UNIT", nullable = false)
	public String getOurUnit() {
		return ourUnit;
	}

	public void setOurUnit(String ourUnit) {
		this.ourUnit = ourUnit;
	}

	@Column(name = "SIGNED_PLACE")
	public String getSignedPlace() {
		return signedPlace;
	}

	public void setSignedPlace(String signedPlace) {
		this.signedPlace = signedPlace;
	}

	@Column(name = "CLIENT", nullable = false)
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Column(name = "DISTRICT", nullable = false)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "AGENCY_FEE_WAY", nullable = false)
	public String getAgencyFeeWay() {
		return agencyFeeWay;
	}

	public void setAgencyFeeWay(String agencyFeeWay) {
		this.agencyFeeWay = agencyFeeWay;
	}

	@Column(name = "COMMISSION_BASE")
	public Double getCommissionBase() {
		return commissionBase;
	}

	public void setCommissionBase(Double commissionBase) {
		this.commissionBase = commissionBase;
	}

	@Column(name = "EVERY_DOLLAR_TO_RMB")
	public Double getEveryDollarToRMB() {
		return everyDollarToRMB;
	}

	public void setEveryDollarToRMB(Double everyDollarToRMB) {
		this.everyDollarToRMB = everyDollarToRMB;
	}

	@Column(name = "AGENCY_FEE")
	public Double getAgencyFee() {
		return agencyFee;
	}

	public void setAgencyFee(Double agencyFee) {
		this.agencyFee = agencyFee;
	}

	@Column(name = "AGENCY_FEE_INCOME")
	public Double getAgencyFeeIncome() {
		return agencyFeeIncome;
	}

	public void setAgencyFeeIncome(Double agencyFeeIncome) {
		this.agencyFeeIncome = agencyFeeIncome;
	}

	@Column(name = "DOMESTIC_MARGIN_RATIO", nullable = false)
	public Double getDomesticMarginRatio() {
		return domesticMarginRatio;
	}

	public void setDomesticMarginRatio(Double domesticMarginRatio) {
		this.domesticMarginRatio = domesticMarginRatio;
	}

	@Column(name = "OTHER_MARGIN_EXPLAIN")
	public String getOtherMarginExplain() {
		return otherMarginExplain;
	}

	public void setOtherMarginExplain(String otherMarginExplain) {
		this.otherMarginExplain = otherMarginExplain;
	}

	@Column(name = "SENT_LETTER_PERSON", nullable = false)
	public Integer getSentLetterPerson() {
		return sentLetterPerson;
	}

	public void setSentLetterPerson(Integer sentLetterPerson) {
		this.sentLetterPerson = sentLetterPerson;
	}
	
	@Column(name = "CURRENCY", nullable = false)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "RMBRATE", nullable = false)
	public Double getRmbRate() {
		return rmbRate;
	}

	public void setRmbRate(Double rmbRate) {
		this.rmbRate = rmbRate;
	}

	@Column(name = "DOLLARRATE", nullable = false)
	public Double getDollarRate() {
		return dollarRate;
	}

	public void setDollarRate(Double dollarRate) {
		this.dollarRate = dollarRate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<AgreementGoods> getAgreementGoods() {
		return agreementGoods;
	}

	public void setAgreementGoods(List<AgreementGoods> agreementGoods) {
		this.agreementGoods = agreementGoods;
	}
	
}
