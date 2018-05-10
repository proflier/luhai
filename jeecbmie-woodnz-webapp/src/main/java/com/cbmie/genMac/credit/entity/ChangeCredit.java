package com.cbmie.genMac.credit.entity;

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
 * 改证申请
 * @author czq
 */
@Entity
@Table(name = "change_credit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ChangeCredit extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 535172019785429017L;

	/**
	 * 采购合同号
	 */
	private String contractNo;
	
	/**
	 * 申请方
	 */
	private String applicant;
	
	/**
	 * 受益方
	 */
	private String beneficiary;
	
	/**
	 * 开证行
	 */
	private String bank;
	
	/**
	 * 信用证有效期
	 */
	private Date creditValidity;
	
	/**
	 * 信用证类型
	 */
	private String creditType;
	
	/**
	 * 期望开证日
	 */
	private Date expectDate;
	
	/**
	 * 付款期限
	 */
	private String prompt;
	
	/**
	 * 开证总金额
	 */
	private Double creditTotalMoney;
	
	/**
	 * 开证总金额大写
	 */
	private String totalChineseNo;
	
	/**
	 * 开证日
	 */
	private Date creditDate;
	
	/**
	 * 信用证号
	 */
	private String creditNo; 
	
	/**
	 * 开证金额
	 */
	private Double creditMoney;
	
	/**
	 * 大写
	 */
	private String chineseNo;
	
	/**
	 * 状态
	 */
	private String state = "草稿";
	
	/**
	 * 改证id
	 */
	private Long changeId;
	
	/**
	 * 历史id(生效后存)
	 */
	private Long historyId;

	@Column(name = "CONTRACT_NO", nullable = false)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	@Column
	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	@Column
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "CREDIT_VALIDITY", nullable = false)
	public Date getCreditValidity() {
		return creditValidity;
	}

	public void setCreditValidity(Date creditValidity) {
		this.creditValidity = creditValidity;
	}

	@Column(name = "CREDIT_TYPE", nullable = false)
	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	@Column(name = "EXPECT_DATE", nullable = false)
	public Date getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}

	@Column
	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Column(name = "CREDIT_TOTAL_MONEY", nullable = false)
	public Double getCreditTotalMoney() {
		return creditTotalMoney;
	}

	public void setCreditTotalMoney(Double creditTotalMoney) {
		this.creditTotalMoney = creditTotalMoney;
	}

	@Column(name = "TOTAL_CHINESE_NO")
	public String getTotalChineseNo() {
		return totalChineseNo;
	}

	public void setTotalChineseNo(String totalChineseNo) {
		this.totalChineseNo = totalChineseNo;
	}

	@Column(name = "CREDIT_DATE")
	public Date getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
	}

	@Column(name = "CREDIT_NO")
	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	@Column(name = "CREDIT_MONEY")
	public Double getCreditMoney() {
		return creditMoney;
	}

	public void setCreditMoney(Double creditMoney) {
		this.creditMoney = creditMoney;
	}

	@Column(name = "CHINESE_NO")
	public String getChineseNo() {
		return chineseNo;
	}

	public void setChineseNo(String chineseNo) {
		this.chineseNo = chineseNo;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "CHANGE_ID")
	public Long getChangeId() {
		return changeId;
	}

	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}

	@Column(name = "HISTORY_ID")
	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

}
