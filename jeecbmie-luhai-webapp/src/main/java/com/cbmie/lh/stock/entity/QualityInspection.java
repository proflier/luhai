package com.cbmie.lh.stock.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.NumberFormat;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 质检
 */
@Entity
@Table(name = "LH_QUALITY_INSPECTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class QualityInspection extends BaseEntity {

	/**
	 * 质检编码
	 */
	private String inspectionNo;
	
	/**
	 * 证书编号
	 */
	private String certificateNo;

	/**
	 * 合同号
	 */
	private String contractNo;

	/**
	 * 提单号/放货编号
	 */
	private String billOrReleaseNo;
	
	/**
	 * 发票号码
	 */
	private String invoiceNo;
	
	/**
	 * 检验单位
	 */
	private String inspectionUnit;

	/**
	 * 送检日期
	 */
	private Date sendInspectionDate;
	
	/**
	 * 出检日期
	 */
	private Date outInspectionDate;
	
	/**
	 * 采验方式
	 */
	private String miningTestWay;
	
	/**
	 * 检验方式
	 */
	private String inspectionWay;
	
	/**
	 * 化验员
	 */
	private String labTechnicians;
	
	/**
	 * 数量合计
	 */
	private Double numTotal;
	
	/**
	 * 业务经办人
	 */
	private String businessManager;
	
	/**
	 * 帐套单位
	 */
	private String setUnit;
	
	/**
	 * 制单日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date billDate;
	
	List<QualityGoods> goodsList = new ArrayList<QualityGoods>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<QualityGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<QualityGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public String getInspectionNo() {
		return inspectionNo;
	}

	public void setInspectionNo(String inspectionNo) {
		this.inspectionNo = inspectionNo;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBillOrReleaseNo() {
		return billOrReleaseNo;
	}

	public void setBillOrReleaseNo(String billOrReleaseNo) {
		this.billOrReleaseNo = billOrReleaseNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInspectionUnit() {
		return inspectionUnit;
	}

	public void setInspectionUnit(String inspectionUnit) {
		this.inspectionUnit = inspectionUnit;
	}

	public Date getSendInspectionDate() {
		return sendInspectionDate;
	}

	public void setSendInspectionDate(Date sendInspectionDate) {
		this.sendInspectionDate = sendInspectionDate;
	}

	public Date getOutInspectionDate() {
		return outInspectionDate;
	}

	public void setOutInspectionDate(Date outInspectionDate) {
		this.outInspectionDate = outInspectionDate;
	}

	public String getMiningTestWay() {
		return miningTestWay;
	}

	public void setMiningTestWay(String miningTestWay) {
		this.miningTestWay = miningTestWay;
	}

	public String getInspectionWay() {
		return inspectionWay;
	}

	public void setInspectionWay(String inspectionWay) {
		this.inspectionWay = inspectionWay;
	}

	public String getLabTechnicians() {
		return labTechnicians;
	}

	public void setLabTechnicians(String labTechnicians) {
		this.labTechnicians = labTechnicians;
	}

	public Double getNumTotal() {
		return numTotal;
	}

	public void setNumTotal(Double numTotal) {
		this.numTotal = numTotal;
	}

	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	public String getSetUnit() {
		return setUnit;
	}

	public void setSetUnit(String setUnit) {
		this.setUnit = setUnit;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

}