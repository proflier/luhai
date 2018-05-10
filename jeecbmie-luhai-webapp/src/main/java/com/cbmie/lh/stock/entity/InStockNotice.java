package com.cbmie.lh.stock.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 入库通知
 */
@Entity
@Table(name = "LH_IN_STOCK_NOTICE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InStockNotice extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 通知单号
	 */
	private String noticeNo;
	
	/**
	 * 内部合同号
	 */
	private String innerContractNo;

	/**
	 * 采购合同号
	 */
	private String purchaseContractNo;

	/**
	 * 供货单位
	 */
	private String deliveryUnit;
	
	/**
	 * 供货单位
	 */
	private String deliveryUnitView;

	@Transient
	public String getDeliveryUnitView() {
		deliveryUnitView = DictUtils.getCorpName(deliveryUnit);
		return deliveryUnitView;
	}

	public void setDeliveryUnitView(String deliveryUnitView) {
		this.deliveryUnitView = deliveryUnitView;
	}

	/**
	 * 帐套单位
	 */
	private String setUnit;
	
	private String setUnitView;
	
	@Transient
	public String getSetUnitView() {
		setUnitView = DictUtils.getCorpName(setUnit);
		return setUnitView;
	}

	public void setSetUnitView(String setUnitView) {
		this.setUnitView = setUnitView;
	}

	/**
	 * 交货起期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date deliveryStartDate;

	/**
	 * 交货止期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date deliveryTerm;

	/** 交货方式 */
	private String deliveryMode;
	
	private String deliveryModeView;
	
	@Transient
	public String getDeliveryModeView() {
		deliveryModeView = DictUtils.getDictSingleName(deliveryMode);
		return deliveryModeView;
	}

	public void setDeliveryModeView(String deliveryModeView) {
		this.deliveryModeView = deliveryModeView;
	}

	/**
	 * 数量溢短装
	 */
	private String moreOrLessQuantity;

	/**
	 * 业务经办人
	 */
	private String businessManager;
	private String businessManagerView;

	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 状态     0：废除 1：生效 2：草稿 3:已提交
	 */
	private String state = "2";
	
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
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonIgnore
	private List<InStockNoticeGoods> inStockNoticeGoodsList = new ArrayList<InStockNoticeGoods>();

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getInnerContractNo() {
		return innerContractNo;
	}

	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}

	public String getPurchaseContractNo() {
		return purchaseContractNo;
	}

	public void setPurchaseContractNo(String purchaseContractNo) {
		this.purchaseContractNo = purchaseContractNo;
	}

	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	public String getSetUnit() {
		return setUnit;
	}

	public void setSetUnit(String setUnit) {
		this.setUnit = setUnit;
	}

	public Date getDeliveryStartDate() {
		return deliveryStartDate;
	}

	public void setDeliveryStartDate(Date deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}

	public Date getDeliveryTerm() {
		return deliveryTerm;
	}

	public void setDeliveryTerm(Date deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getMoreOrLessQuantity() {
		return moreOrLessQuantity;
	}

	public void setMoreOrLessQuantity(String moreOrLessQuantity) {
		this.moreOrLessQuantity = moreOrLessQuantity;
	}

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "inStockNoticeId")
	public List<InStockNoticeGoods> getInStockNoticeGoodsList() {
		return inStockNoticeGoodsList;
	}

	public void setInStockNoticeGoodsList(List<InStockNoticeGoods> inStockNoticeGoodsList) {
		this.inStockNoticeGoodsList = inStockNoticeGoodsList;
	}

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