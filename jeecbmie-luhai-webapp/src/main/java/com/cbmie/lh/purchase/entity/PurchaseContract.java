package com.cbmie.lh.purchase.entity;

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

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 采购合同-进口
 */
@Entity
@Table(name = "LH_PURCHASE_CONTRACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PurchaseContract extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 内部合同号
	 */
	private String innerContractNo;

	/**
	 * 采购合同号
	 */
	private String purchaseContractNo;

	/**
	 * 协议号
	 */
	private String agreementNo;

	/**
	 * 供货单位(卖方)
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
	 * 进口国别
	 */
	private String importCountry;

	/**
	 * 价格条款
	 */
	private String priceTerm;

	/**
	 * 应预付额
	 */
	private String prepaidMoney;

	/**
	 * 合同数量
	 */
	private String contractQuantity;

	/**
	 * 数量单位
	 */
	private String quantityUnit;

	/**
	 * 保证金
	 */
	private String margin;

	/**
	 * 合同金额
	 */
	private String contractAmount;

	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 付款方式（结算方式）
	 */
	private String payMode;

	/**
	 * 授信类型
	 */
	private String creditType;
	
	/**发运地点（装运港）*/
	private String sendAddr;
	
	/**交货地点（卸货港）*/
	private String deliveryAddr;
	
	/**
	 * 预计发货（装港）时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date expectSendDate;
	
	/**
	 * 预计到货（卸港）时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date expectDeliveryDate;
	
	/**交货方式*/
	private String deliveryMode;
	/**交货方式详情*/
	private String deliveryModeDetail;
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
	
	
	/**
	 * 信用证开具期限（结算方式为信用证时才显示）
	 */
	private String creditDeadline;

	/**
	 * 订货单位
	 */
	private String orderUnit;

	/**
	 * 收货单位(买方)
	 */
	private String receivingUnit;

	/**
	 * 业务经办人
	 */
	private String businessManager;
	private String businessManagerView;
	
	/**
	 * 贸易方式
	 * 1、库存
	 * 2、以销订购
	 */
	private String tradeWay;
	
	/**
	 * 帐套单位
	 */
	private String setUnit;
	
	
	@Transient
	public String getBusinessManagerView() {
		businessManagerView = DictUtils.getUserNameByLoginName(businessManager);
		return businessManagerView;
	}

	public void setBusinessManagerView(String businessManagerView) {
		this.businessManagerView = businessManagerView;
	}
	
	/***
	 * 运输方式
	 */
	private String transportCategory;
	
	
	
	/**
	 * 合同类型
	 */
	private String contractCategory;
	private String contractCategoryView;
	
	@Transient
	public String getContractCategoryView() {
		contractCategoryView = DictUtils.getDictSingleName(contractCategory);
		return contractCategoryView;
	}

	public void setContractCategoryView(String contractCategoryView) {
		this.contractCategoryView = contractCategoryView;
	}

	/**
	 * 印章类型
	 */
	private String sealCategory;

	/**
	 * 印章管理员
	 */
	private String sealManager;

	/**
	 * 经营方式
	 */
	private String runMode;

	/**
	 * 签约地点
	 */
	private String signingPlace;

	/**
	 * 签约日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date signingDate;

	/**
	 * 金额溢短装
	 */
	private String moreOrLessAmount;

	/**
	 * 数量溢短装
	 */
	private String moreOrLessQuantity;

	/**
	 * 合同金额大写
	 */
	private String blockMoney;

	/**
	 * 受载期起始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date transportStartDate;

	/**
	 * 受载期终止时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date transportTermDate;

	/**
	 * 卸率
	 */
	private String unloadingRate;

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

	/**
	 * 结算条款
	 */
	private String settlementTerms;

	/**
	 * 仲裁地
	 */
	private String arbitrationPlace;

	/** 校验机构 **/
	private String checkOrg;

	/** 校验标准 **/
	private String checkStandard;
	
	/**
	 * 费用承担人
	 */
	private String costBearer;
	
	/**
	 * 备注
	 */
	private String comment;
	
	/**
	 * 合同有效期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date contractValidityPeriod;
	
	/**
	 * 货权转移条款
	 */
	private String stockTerms;
	
	/**
	 * 质量验收标准
	 */
	private String qualityAcceptance;
	
	/**
	 * 数量验收标准
	 */
	private String quantityAcceptance;
	
	/**
	 * 检测费用
	 */
	private String testFee;
	
	/**
	 * 港建费
	 */
	private String portFee;
	
	/**
	 * 码头费
	 */
	private String dockFee;

	/**
	 * 运费
	 */
	private String freightFee;
	
	/**
	 * 堆存费
	 */
	private String storageFee;
	
	/**
	 * 滞期/速遣费
	 */
	private String dispatchFee;
	
	/**
	 * 其他费用
	 */
	private String otherFee;
	
	/**
	 * 结算条款
	 */
	private String settlementClause;
	
	/**
	 * 付款条款
	 */
	private String paymentClause;
	
	/**
	 * 运输信息编号
	 */
	private String shipNo;
	
	@Column
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	@Column
	public String getArbitrationPlace() {
		return arbitrationPlace;
	}

	public void setArbitrationPlace(String arbitrationPlace) {
		this.arbitrationPlace = arbitrationPlace;
	}

	@Column
	public String getSettlementTerms() {
		return settlementTerms;
	}

	public void setSettlementTerms(String settlementTerms) {
		this.settlementTerms = settlementTerms;
	}

	@Column
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	/**
	 * 合同关闭打开  0:关闭  1：运行中
	 */
	private String closeOrOpen = "1";

	@Column
	public String getCloseOrOpen() {
		return closeOrOpen;
	}

	public void setCloseOrOpen(String closeOrOpen) {
		this.closeOrOpen = closeOrOpen;
	}

	@Column(columnDefinition = "varchar(255) default '1'")
	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}

	@Column
	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	
	@Column
	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Column
	public String getDeliveryModeDetail() {
		return deliveryModeDetail;
	}

	public void setDeliveryModeDetail(String deliveryModeDetail) {
		this.deliveryModeDetail = deliveryModeDetail;
	}
	
	
	@Column
	public String getSendAddr() {
		return sendAddr;
	}

	public void setSendAddr(String sendAddr) {
		this.sendAddr = sendAddr;
	}

	@Column
	public String getDeliveryAddr() {
		return deliveryAddr;
	}

	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}

	@Column
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private List<PurchaseContractGoods> purchaseContractGoodsList = new ArrayList<PurchaseContractGoods>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "purchaseContractId")
	public List<PurchaseContractGoods> getPurchaseContractGoodsList() {
		return purchaseContractGoodsList;
	}

	public void setPurchaseContractGoodsList(List<PurchaseContractGoods> purchaseContractGoodsList) {
		this.purchaseContractGoodsList = purchaseContractGoodsList;
	}

	@Column
	public String getInnerContractNo() {
		return innerContractNo;
	}

	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}

	@Column
	public String getPurchaseContractNo() {
		return purchaseContractNo;
	}

	public void setPurchaseContractNo(String purchaseContractNo) {
		this.purchaseContractNo = purchaseContractNo;
	}

	@Column
	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	@Column
	public String getDeliveryUnit() {
		return deliveryUnit;
	}

	public void setDeliveryUnit(String deliveryUnit) {
		this.deliveryUnit = deliveryUnit;
	}

	@Column
	public String getImportCountry() {
		return importCountry;
	}

	public void setImportCountry(String importCountry) {
		this.importCountry = importCountry;
	}

	@Column
	public String getPriceTerm() {
		return priceTerm;
	}

	public void setPriceTerm(String priceTerm) {
		this.priceTerm = priceTerm;
	}

	@Column
	public String getPrepaidMoney() {
		return prepaidMoney;
	}

	public void setPrepaidMoney(String prepaidMoney) {
		this.prepaidMoney = prepaidMoney;
	}

	@Column
	public String getContractQuantity() {
		return contractQuantity;
	}

	public void setContractQuantity(String contractQuantity) {
		this.contractQuantity = contractQuantity;
	}

	@Column
	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	@Column
	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	@Column
	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	@Column
	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	@Column
	public Date getDeliveryStartDate() {
		return deliveryStartDate;
	}

	public void setDeliveryStartDate(Date deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}

	@Column
	public Date getDeliveryTerm() {
		return deliveryTerm;
	}

	public void setDeliveryTerm(Date deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}

	@Column
	public String getOrderUnit() {
		return orderUnit;
	}

	public void setOrderUnit(String orderUnit) {
		this.orderUnit = orderUnit;
	}

	@Column
	public String getReceivingUnit() {
		return receivingUnit;
	}

	public void setReceivingUnit(String receivingUnit) {
		this.receivingUnit = receivingUnit;
	}

	@Column
	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
	}

	@Column
	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	@Column
	public String getSealCategory() {
		return sealCategory;
	}

	public void setSealCategory(String sealCategory) {
		this.sealCategory = sealCategory;
	}

	@Column
	public String getSealManager() {
		return sealManager;
	}

	public void setSealManager(String sealManager) {
		this.sealManager = sealManager;
	}

	@Column
	public String getRunMode() {
		return runMode;
	}

	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	@Column
	public String getSigningPlace() {
		return signingPlace;
	}

	public void setSigningPlace(String signingPlace) {
		this.signingPlace = signingPlace;
	}

	@Column
	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	@Column
	public String getMoreOrLessAmount() {
		return moreOrLessAmount;
	}

	public void setMoreOrLessAmount(String moreOrLessAmount) {
		this.moreOrLessAmount = moreOrLessAmount;
	}

	@Column
	public String getMoreOrLessQuantity() {
		return moreOrLessQuantity;
	}

	public void setMoreOrLessQuantity(String moreOrLessQuantity) {
		this.moreOrLessQuantity = moreOrLessQuantity;
	}

	@Column
	public String getBlockMoney() {
		return blockMoney;
	}

	public void setBlockMoney(String blockMoney) {
		this.blockMoney = blockMoney;
	}

	@Column
	public Date getTransportStartDate() {
		return transportStartDate;
	}

	public void setTransportStartDate(Date transportStartDate) {
		this.transportStartDate = transportStartDate;
	}

	@Column
	public Date getTransportTermDate() {
		return transportTermDate;
	}

	public void setTransportTermDate(Date transportTermDate) {
		this.transportTermDate = transportTermDate;
	}

	@Column
	public String getUnloadingRate() {
		return unloadingRate;
	}

	public void setUnloadingRate(String unloadingRate) {
		this.unloadingRate = unloadingRate;
	}

	@Column
	public String getCheckOrg() {
		return checkOrg;
	}

	public void setCheckOrg(String checkOrg) {
		this.checkOrg = checkOrg;
	}

	@Column
	public String getCheckStandard() {
		return checkStandard;
	}

	public void setCheckStandard(String checkStandard) {
		this.checkStandard = checkStandard;
	}

	@Column
	public String getSetUnit() {
		return setUnit;
	}

	public void setSetUnit(String setUnit) {
		this.setUnit = setUnit;
	}
	
	@Column(length=500)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column
	public Date getExpectSendDate() {
		return expectSendDate;
	}

	public void setExpectSendDate(Date expectSendDate) {
		this.expectSendDate = expectSendDate;
	}

	@Column
	public Date getExpectDeliveryDate() {
		return expectDeliveryDate;
	}

	public void setExpectDeliveryDate(Date expectDeliveryDate) {
		this.expectDeliveryDate = expectDeliveryDate;
	}

	@Column
	public String getCreditDeadline() {
		return creditDeadline;
	}

	public void setCreditDeadline(String creditDeadline) {
		this.creditDeadline = creditDeadline;
	}

	@Column
	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	@Column
	public String getTransportCategory() {
		return transportCategory;
	}

	public void setTransportCategory(String transportCategory) {
		this.transportCategory = transportCategory;
	}

	@Column
	public String getCostBearer() {
		return costBearer;
	}

	public void setCostBearer(String costBearer) {
		this.costBearer = costBearer;
	}

	@Column
	public Date getContractValidityPeriod() {
		return contractValidityPeriod;
	}

	public void setContractValidityPeriod(Date contractValidityPeriod) {
		this.contractValidityPeriod = contractValidityPeriod;
	}

	@Column
	public String getStockTerms() {
		return stockTerms;
	}

	public void setStockTerms(String stockTerms) {
		this.stockTerms = stockTerms;
	}

	@Column
	public String getQualityAcceptance() {
		return qualityAcceptance;
	}

	public void setQualityAcceptance(String qualityAcceptance) {
		this.qualityAcceptance = qualityAcceptance;
	}

	@Column
	public String getQuantityAcceptance() {
		return quantityAcceptance;
	}

	public void setQuantityAcceptance(String quantityAcceptance) {
		this.quantityAcceptance = quantityAcceptance;
	}

	@Column
	public String getTestFee() {
		return testFee;
	}

	public void setTestFee(String testFee) {
		this.testFee = testFee;
	}

	@Column
	public String getPortFee() {
		return portFee;
	}

	public void setPortFee(String portFee) {
		this.portFee = portFee;
	}

	@Column
	public String getDockFee() {
		return dockFee;
	}

	public void setDockFee(String dockFee) {
		this.dockFee = dockFee;
	}

	@Column
	public String getFreightFee() {
		return freightFee;
	}

	public void setFreightFee(String freightFee) {
		this.freightFee = freightFee;
	}

	@Column
	public String getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(String storageFee) {
		this.storageFee = storageFee;
	}

	@Column
	public String getDispatchFee() {
		return dispatchFee;
	}

	public void setDispatchFee(String dispatchFee) {
		this.dispatchFee = dispatchFee;
	}

	@Column
	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

	@Column
	public String getSettlementClause() {
		return settlementClause;
	}

	public void setSettlementClause(String settlementClause) {
		this.settlementClause = settlementClause;
	}

	@Column
	public String getPaymentClause() {
		return paymentClause;
	}

	public void setPaymentClause(String paymentClause) {
		this.paymentClause = paymentClause;
	}

	
}