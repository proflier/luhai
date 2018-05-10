package com.cbmie.lh.sale.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.activiti.base.ActivitiConstant;
import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 销售放货申请
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "LH_SALE_DELIVERY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleDelivery extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	/**
	 * 销售合同号
	 */
	private String saleContractNo;
	/**
	 * 销售合同号
	 */
	private String saleContractNoOld;
	/**
	 * 制单日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date billDate;
	/**
	 * 发货通知号
	 */
	private String deliveryReleaseNo;
	/**
	 * 客户编号
	 */
	private String seller;
	private String sellerView;
	@Transient
	public String getSellerView() {
		sellerView = DictUtils.getCorpName(seller);
		return sellerView;
	}
	public void setSellerView(String sellerView) {
		this.sellerView = sellerView;
	}
	/**
	 * 客户名称
	 */
	/**
	 * 销售方式
	 */
	private String saleMode;
	
	/**业务类型*/
	private String manageMode;
	@Column(length=50)
	public String getManageMode() {
		return manageMode;
	}

	public void setManageMode(String manageMode) {
		this.manageMode = manageMode;
	}
	
	private String saleModeView;
	@Transient
	public String getSaleModeView() {
		saleModeView = DictUtils.getDictSingleName(saleMode);
		return saleModeView;
	}
	public void setSaleModeView(String saleModeView) {
		this.saleModeView = saleModeView;
	}
	/**
	 * 业务员
	 */
	private String businessManager;
	private String businessManagerView;
	@Transient
	public String getBusinessManagerView() {
		businessManagerView = DictUtils.getUserNameByLoginName(businessManager);
		return businessManagerView;
	}
	public void setBusinessManagerView(String businessManagerView) {
		this.businessManagerView = businessManagerView;
	}
	
	/**
	 * 帐套单位
	 */
	private String setUnit;
	/**
	 * 数量合计
	 */
	private Double numTotal;
	/**
	 * 注意事项
	 */
	private String remark;
	/**交接方式*/
	private String deliveryMode;
	/**运输方式*/
	private String transType;
	/**交货开始日期*/
	private Date deliveryStartDate;
	/**交货结束日期*/
	private Date deliveryEndDate;
	/**数量验收*/
	private String numSettlementBasis;
	/**质量验收*/
	private String qualitySettlementBasis;
	/** 费用承担 **/
	private String riskTip;
	/** 数量溢短装* */
	private Double numMoreOrLess;
	/** 放货状态 1 打开 0 关闭* */
	private String closeOrOpen="1";
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
	private List<SaleDeliveryGoods> salesDeliveryGoodsList = new ArrayList<SaleDeliveryGoods>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "saleDeliveryId")
	public List<SaleDeliveryGoods> getSalesDeliveryGoodsList() {
		return salesDeliveryGoodsList;
	}
	public void setSalesDeliveryGoodsList(List<SaleDeliveryGoods> salesDeliveryGoodsList) {
		this.salesDeliveryGoodsList = salesDeliveryGoodsList;
	}

	@Column
	public String getSaleContractNo() {
		return saleContractNo;
	}
	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}

	@Column
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	@Column
	public String getDeliveryReleaseNo() {
		return deliveryReleaseNo;
	}
	public void setDeliveryReleaseNo(String deliveryReleaseNo) {
		this.deliveryReleaseNo = deliveryReleaseNo;
	}

	@Column
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}

	@Column
	public String getSaleMode() {
		return saleMode;
	}
	public void setSaleMode(String saleMode) {
		this.saleMode = saleMode;
	}
	
	@Column
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public Date getDeliveryStartDate() {
		return deliveryStartDate;
	}
	public void setDeliveryStartDate(Date deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}
	public Date getDeliveryEndDate() {
		return deliveryEndDate;
	}
	public void setDeliveryEndDate(Date deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}
	public String getNumSettlementBasis() {
		return numSettlementBasis;
	}
	public void setNumSettlementBasis(String numSettlementBasis) {
		this.numSettlementBasis = numSettlementBasis;
	}
	public String getQualitySettlementBasis() {
		return qualitySettlementBasis;
	}
	public void setQualitySettlementBasis(String qualitySettlementBasis) {
		this.qualitySettlementBasis = qualitySettlementBasis;
	}
	public String getRiskTip() {
		return riskTip;
	}
	public void setRiskTip(String riskTip) {
		this.riskTip = riskTip;
	}
	public String getSaleContractNoOld() {
		return saleContractNoOld;
	}
	public void setSaleContractNoOld(String saleContractNoOld) {
		this.saleContractNoOld = saleContractNoOld;
	}
	public Double getNumMoreOrLess() {
		return numMoreOrLess;
	}
	public void setNumMoreOrLess(Double numMoreOrLess) {
		this.numMoreOrLess = numMoreOrLess;
	}
	public String getCloseOrOpen() {
		return closeOrOpen;
	}
	public void setCloseOrOpen(String closeOrOpen) {
		this.closeOrOpen = closeOrOpen;
	}
	@Column(length=50)
	public String getBusinessManager() {
		return businessManager;
	}
	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getSetUnit() {
		return setUnit;
	}
	public void setSetUnit(String setUnit) {
		this.setUnit = setUnit;
	}
	public Double getNumTotal() {
		return numTotal;
	}
	public void setNumTotal(Double numTotal) {
		this.numTotal = numTotal;
	}
	
}
