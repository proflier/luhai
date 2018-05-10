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
/**运价函**/
@Entity
@Table(name="LH_LOGISTIC_FREIGHTLETTER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class FreightLetter extends BaseEntity {

	/**主合同编号*/
	private String contractNo;
	/**承运函编号**/
	private String letterNo;
	/**承运时间**/
	private Date transitTime;
	/**线路开始*/
	private String lineFrom;
	/**线路结束*/
	private String lineTo;
	/**结算单价*/
	private Double unitPrice;
	/** 数量单位 **/
	private String numberUnit;
	/** 备注 **/
	private String remark;
	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	
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
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getLetterNo() {
		return letterNo;
	}
	public void setLetterNo(String letterNo) {
		this.letterNo = letterNo;
	}
	public Date getTransitTime() {
		return transitTime;
	}
	public void setTransitTime(Date transitTime) {
		this.transitTime = transitTime;
	}
	public String getLineFrom() {
		return lineFrom;
	}
	public void setLineFrom(String lineFrom) {
		this.lineFrom = lineFrom;
	}
	public String getLineTo() {
		return lineTo;
	}
	public void setLineTo(String lineTo) {
		this.lineTo = lineTo;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getNumberUnit() {
		return numberUnit;
	}
	public void setNumberUnit(String numberUnit) {
		this.numberUnit = numberUnit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
