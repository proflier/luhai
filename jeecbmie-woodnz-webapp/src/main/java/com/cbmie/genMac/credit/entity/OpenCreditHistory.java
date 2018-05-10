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
 * 开证历史
 * @author czq
 */
@Entity
@Table(name = "open_credit_hi")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OpenCreditHistory extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 6827399147840915419L;
	
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
	 * 远期天数
	 */
	private Integer promptDays;
	
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
	 * 改证id
	 */
	private Long changeId;

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

	@Column(name = "PROMPT_DAYS")
	public Integer getPromptDays() {
		return promptDays;
	}

	public void setPromptDays(Integer promptDays) {
		this.promptDays = promptDays;
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

	@Column(name = "CHANGE_ID")
	public Long getChangeId() {
		return changeId;
	}

	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}
}
