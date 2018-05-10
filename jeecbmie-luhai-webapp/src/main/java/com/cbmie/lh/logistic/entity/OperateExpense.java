package com.cbmie.lh.logistic.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.system.utils.DictUtils;
/**
 * 作业费
 */
@Entity
@Table(name = "LH_LOGISTIC_OPERATEEXPENSE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class OperateExpense extends BaseEntity {
	/**港口*/
	private String portNo;
	private String portNoView;
	/**贸易方式*/
	private String tradeCategory;
	/**贸易方式转名称*/
	private String tradeCategoryView;
	/**作业方式*/
	private String operateType;
	private String operateTypeView;
	/**计价范围开始*/
	private Double expenseStart;
	/**计价范围结束*/
	private Double expenseEnd;
	/**单价*/
	private Double unitPrice;
	/**父节点Id*/
	private Long portContractId;

	@Transient
	public String getPortNoView() {
		portNoView = DictUtils.getCorpName(portNo);
		return portNoView;
	}
	public void setPortNoView(String portNoView) {
		this.portNoView = portNoView;
	}
	@Transient
	public String getTradeCategoryView() {
		tradeCategoryView = DictUtils.getDictSingleName(tradeCategory);
		return tradeCategoryView;
	}
	public void setTradeCategoryView(String tradeCategoryView) {
		this.tradeCategoryView = tradeCategoryView;
	}
	@Transient
	public String getOperateTypeView() {
		operateTypeView = DictUtils.getDictSingleName(operateType);
		return operateTypeView;
	}
	public void setOperateTypeView(String operateTypeView) {
		this.operateTypeView = operateTypeView;
	}
	public String getPortNo() {
		return portNo;
	}
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}
	public String getTradeCategory() {
		return tradeCategory;
	}
	public void setTradeCategory(String tradeCategory) {
		this.tradeCategory = tradeCategory;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Long getPortContractId() {
		return portContractId;
	}
	public void setPortContractId(Long portContractId) {
		this.portContractId = portContractId;
	}
	public Double getExpenseStart() {
		return expenseStart;
	}
	public void setExpenseStart(Double expenseStart) {
		this.expenseStart = expenseStart;
	}
	public Double getExpenseEnd() {
		return expenseEnd;
	}
	public void setExpenseEnd(Double expenseEnd) {
		this.expenseEnd = expenseEnd;
	}
}
