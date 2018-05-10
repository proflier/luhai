package com.cbmie.lh.administration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 印章管理
 */
@Entity
@Table(name = "LH_SEAL_APPROVAL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SealApproval extends BaseEntity {

	/**
	 * 印章公司编码
	 */
	private String contractCode;
	
	/**
	 * 印章公司
	 */
	private String contractCategory;

	/**
	 * 印章编码
	 */
	private String signetCode;
	
	/**
	 * 印章类别名称
	 */
	private String typeName;
	
	/**
	 * 用印方式编码
	 */
	private String sealCode;
	
	/**
	 * 用印方式
	 */
	private String sealModel;

	/**
	 * 申请人单位
	 */
	private String applicantUnit;
	
	/**
	 * 申请人信息
	 */
	private String applicantInformation;

	/**
	 * 用印日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date singnDate;

	/**
	 * 文件名及主要内容
	 */
	private String fileNameMainContents;

	/**
	 * 份数
	 */
	private int copies;

	/**
	 * 事由及提交单位
	 */
	private String causeSubmission;
	
	/**
	 * 归还日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private String backDate;
	

	/**
	 * 归还状态
	 */
	private String lendState;
	
	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column
	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	@Column(length=50)
	public String getSealModel() {
		return sealModel;
	}

	public void setSealModel(String sealModel) {
		this.sealModel = sealModel;
	}

	@Column(length=50)
	public String getApplicantUnit() {
		return applicantUnit;
	}

	public void setApplicantUnit(String applicantUnit) {
		this.applicantUnit = applicantUnit;
	}

	@Column(length=50)
	public String getApplicantInformation() {
		return applicantInformation;
	}

	public void setApplicantInformation(String applicantInformation) {
		this.applicantInformation = applicantInformation;
	}

	@Column
	public Date getSingnDate() {
		return singnDate;
	}

	public void setSingnDate(Date singnDate) {
		this.singnDate = singnDate;
	}

	@Column(length=50)
	public String getSignetCode() {
		return signetCode;
	}

	public void setSignetCode(String signetCode) {
		this.signetCode = signetCode;
	}

	@Column(length=50)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(length=3500)
	public String getFileNameMainContents() {
		return fileNameMainContents;
	}

	public void setFileNameMainContents(String fileNameMainContents) {
		this.fileNameMainContents = fileNameMainContents;
	}

	@Column(length=50)
	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	@Column
	public String getCauseSubmission() {
		return causeSubmission;
	}

	public void setCauseSubmission(String causeSubmission) {
		this.causeSubmission = causeSubmission;
	}

	@Column(length=50)
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	@Column(length=50)
	public String getSealCode() {
		return sealCode;
	}

	public void setSealCode(String sealCode) {
		this.sealCode = sealCode;
	}

	@Column
	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	@Column
	public String getLendState() {
		return lendState;
	}

	public void setLendState(String lendState) {
		this.lendState = lendState;
	}

	
	
}
