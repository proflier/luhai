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
 * 销售主表
 * 
 */
@Entity
@Table(name = "LH_SALE_CONTRACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleContract extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 费用承担人
	 */
	private String feeUnderwriter;
	
	/**
	 * 货权转移条款
	 */
	@Column
	private String transferClause;
	
	/**
	 * 数量验收标准
	 */
	@Column(length=50)
	private String quantityAcceptance;
	
	/**
	 * 质量验收标准
	 */
	@Column(length=50)
	private String qualityAcceptance;
	
	/**
	 * 第三方检测机构
	 */
	@Column(length=50)
	private String thirdPartyTest;
	
	/**
	 * 检测费用
	 */
	@Column(length=50)
	private String testFee;
	
	/**
	 * 港建费
	 */
	@Column(length=50)
	private String portFee;
	
	/**
	 * 码头费
	 */
	@Column(length=50)
	private String dockFee;

	/**
	 * 运费
	 */
	@Column(length=50)
	private String freightFee;
	
	/**
	 * 堆存费
	 */
	@Column(length=50)
	private String storageFee;
	
	/**
	 * 滞期/速遣费
	 */
	@Column(length=50)
	private String dispatchFee;
	
	/**
	 * 其他费用
	 */
	@Column(length=50)
	private String otherFee;
	
	/**
	 * 结算条款
	 */
	private String settlementClause;
	
	/**
	 * 付款条款
	 */
	private String paymentClause;
	
	public String getFeeUnderwriter() {
		return feeUnderwriter;
	}

	public void setFeeUnderwriter(String feeUnderwriter) {
		this.feeUnderwriter = feeUnderwriter;
	}

	public String getSettlementClause() {
		return settlementClause;
	}

	public void setSettlementClause(String settlementClause) {
		this.settlementClause = settlementClause;
	}

	public String getPaymentClause() {
		return paymentClause;
	}

	public void setPaymentClause(String paymentClause) {
		this.paymentClause = paymentClause;
	}

	public String getPortFee() {
		return portFee;
	}

	public void setPortFee(String portFee) {
		this.portFee = portFee;
	}

	public String getDockFee() {
		return dockFee;
	}

	public void setDockFee(String dockFee) {
		this.dockFee = dockFee;
	}

	public String getFreightFee() {
		return freightFee;
	}

	public void setFreightFee(String freightFee) {
		this.freightFee = freightFee;
	}

	public String getStorageFee() {
		return storageFee;
	}

	public void setStorageFee(String storageFee) {
		this.storageFee = storageFee;
	}

	public String getDispatchFee() {
		return dispatchFee;
	}

	public void setDispatchFee(String dispatchFee) {
		this.dispatchFee = dispatchFee;
	}

	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

	public String getTestFee() {
		return testFee;
	}

	public void setTestFee(String testFee) {
		this.testFee = testFee;
	}

	public String getThirdPartyTest() {
		return thirdPartyTest;
	}

	public void setThirdPartyTest(String thirdPartyTest) {
		this.thirdPartyTest = thirdPartyTest;
	}

	public String getTransferClause() {
		return transferClause;
	}

	public void setTransferClause(String transferClause) {
		this.transferClause = transferClause;
	}

	public String getQuantityAcceptance() {
		return quantityAcceptance;
	}

	public void setQuantityAcceptance(String quantityAcceptance) {
		this.quantityAcceptance = quantityAcceptance;
	}

	public String getQualityAcceptance() {
		return qualityAcceptance;
	}

	public void setQualityAcceptance(String qualityAcceptance) {
		this.qualityAcceptance = qualityAcceptance;
	}

	/** 合同编号* */
	private String contractNo;
	/** 卖受人* */
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

	/** 买受人* */
	private String purchaser;
	/** 买方信用评级 **/
	@Column(length=50)
	private String purchaserCredit;
	private String purchaserView;
	@Transient
	public String getPurchaserView() {
		purchaserView = DictUtils.getCorpName(purchaser);
		return purchaserView;
	}

	public void setPurchaserView(String purchaserView) {
		this.purchaserView = purchaserView;
	}

	/**印章类型*/
	private String signetCode;
	/**销售类型*/
	private String saleMode;
	/**经营方式*/
	private String manageMode;
	/** 签约地点* */
	private String signAddr;
	/** 签约日期* */
	private Date signDate;
	/** 合同有效开始日期* */
	private Date startDate;
	/** 合同有效结束日期* */
	private Date endDate;
	/** 合同类型* */
	private String contractMode;
	/** 数量溢短装* */
	private Double numMoreOrLess;
	/** 金额溢短装* */
	private Double moneyMoreOrLess;
	/** 币别* */
	private String currency;
	/** 保证金* */
	private Double bail;
	/**交货开始日期*/
	private Date deliveryStartDate;
	/**交货结束日期*/
	private Date deliveryEndDate;
	/**交货方式*/
	private String deliveryMode;
	/**交货地点*/
	private String deliveryAddr;
	/**船名(暂时不用)*/
	private String ship;
	/** 收款止期* */
	private Date gatheringDate;
	/**数量结算依据*/
	private String numSettlementBasis;
	/**质量结算依据*/
	private String qualitySettlementBasis;
	/**交款方式*/
	private String settlementMode;
	private String settlementModeView;
	@Transient
	public String getSettlementModeView() {
		settlementModeView = DictUtils.getDictSingleName(settlementMode);
		return settlementModeView;
	}

	public void setSettlementModeView(String settlementModeView) {
		this.settlementModeView = settlementModeView;
	}

	/**销售负责人*/
	private Integer salePerson;
	/** 合同类别* */
	private String contractType;
	/** 合同金额 **/
	private Double contractAmount;
	/**
	 * 合同金额大写
	 */
	private String blockMoney;
	/**
	 * 业务经办人
	 */
	private String businessManager;

	/**
	 * 合同数量
	 */
	private Double contractQuantity;
	/**帐期*/
	private Integer accountStage;
	/**
	 * 状态
	 */
	private String state = ActivitiConstant.ACTIVITI_DRAFT;
	/** 风险提示 **/
	private String riskTip;
	/** 客户合同号 */
	private String customerContractNo;
	/**0 未关闭 1 关闭*/
	private String closedFlag="0";
	
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
	
	@Column
	public String getSetUnit() {
		return setUnit;
	}

	public void setSetUnit(String setUnit) {
		this.setUnit = setUnit;
	}

	/**
	 * 销售合同子表信息
	 */
	private List<SaleContractGoods> saleContractSubList = new ArrayList<SaleContractGoods>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "saleContractId")
	public List<SaleContractGoods> getSaleContractSubList() {
		return saleContractSubList;
	}

	public void setSaleContractSubList(List<SaleContractGoods> saleContractSubList) {
		this.saleContractSubList = saleContractSubList;
	}

	@Column(length=50)
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column(length=50)
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	@Column(length=50)
	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}
	@Column(length=50)
	public String getSignetCode() {
		return signetCode;
	}

	public void setSignetCode(String signetCode) {
		this.signetCode = signetCode;
	}
	@Column(length=50)
	public String getSaleMode() {
		return saleMode;
	}

	public void setSaleMode(String saleMode) {
		this.saleMode = saleMode;
	}
	@Column(length=50)
	public String getManageMode() {
		return manageMode;
	}

	public void setManageMode(String manageMode) {
		this.manageMode = manageMode;
	}
	@Column(length=150)
	public String getSignAddr() {
		return signAddr;
	}

	public void setSignAddr(String signAddr) {
		this.signAddr = signAddr;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(length=50)
	public String getContractMode() {
		return contractMode;
	}

	public void setContractMode(String contractMode) {
		this.contractMode = contractMode;
	}

	public Double getNumMoreOrLess() {
		return numMoreOrLess;
	}

	public void setNumMoreOrLess(Double numMoreOrLess) {
		this.numMoreOrLess = numMoreOrLess;
	}

	public Double getMoneyMoreOrLess() {
		return moneyMoreOrLess;
	}

	public void setMoneyMoreOrLess(Double moneyMoreOrLess) {
		this.moneyMoreOrLess = moneyMoreOrLess;
	}
	@Column(length=50)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getBail() {
		return bail;
	}

	public void setBail(Double bail) {
		this.bail = bail;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getDeliveryStartDate() {
		return deliveryStartDate;
	}

	public void setDeliveryStartDate(Date deliveryStartDate) {
		this.deliveryStartDate = deliveryStartDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getDeliveryEndDate() {
		return deliveryEndDate;
	}

	public void setDeliveryEndDate(Date deliveryEndDate) {
		this.deliveryEndDate = deliveryEndDate;
	}
	@Column(length=50)
	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getGatheringDate() {
		return gatheringDate;
	}

	public void setGatheringDate(Date gatheringDate) {
		this.gatheringDate = gatheringDate;
	}
	@Column(length=50)
	public String getSettlementMode() {
		return settlementMode;
	}

	public void setSettlementMode(String settlementMode) {
		this.settlementMode = settlementMode;
	}

	public Integer getSalePerson() {
		return salePerson;
	}

	public void setSalePerson(Integer salePerson) {
		this.salePerson = salePerson;
	}
	@Column(length=50)
	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	@Column(length=50)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	@Column(length=50)
	public String getBlockMoney() {
		return blockMoney;
	}

	public void setBlockMoney(String blockMoney) {
		this.blockMoney = blockMoney;
	}

	public Double getContractQuantity() {
		return contractQuantity;
	}

	public void setContractQuantity(Double contractQuantity) {
		this.contractQuantity = contractQuantity;
	}

	public String getDeliveryAddr() {
		return deliveryAddr;
	}

	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}

	public String getShip() {
		return ship;
	}

	public void setShip(String ship) {
		this.ship = ship;
	}

	public String getRiskTip() {
		return riskTip;
	}

	public void setRiskTip(String riskTip) {
		this.riskTip = riskTip;
	}

	public String getCustomerContractNo() {
		return customerContractNo;
	}

	public void setCustomerContractNo(String customerContractNo) {
		this.customerContractNo = customerContractNo;
	}

	public String getClosedFlag() {
		return closedFlag;
	}

	public void setClosedFlag(String closedFlag) {
		this.closedFlag = closedFlag;
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
	@Column(columnDefinition = "varchar(255) default '1'")
	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}
	@Column(length=30)
	public String getBusinessManager() {
		return businessManager;
	}

	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
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
	@Column(length=500)
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Integer getAccountStage() {
		return accountStage;
	}

	public void setAccountStage(Integer accountStage) {
		this.accountStage = accountStage;
	}

	public String getPurchaserCredit() {
		return purchaserCredit;
	}

	public void setPurchaserCredit(String purchaserCredit) {
		this.purchaserCredit = purchaserCredit;
	}
	
}
