package com.cbmie.lh.logistic.entity;

import javax.persistence.Column;
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
 * 杂费
 */
@Entity
@Table(name = "LH_LOGISTIC_EXTRAEXPENSE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class ExtraExpense extends BaseEntity {

	/**费用类别*/
	private String expenseType;
	private String expenseTypeView;
	/**计量单位*/
	private String countUnit;
	private String countUnitView;
	/**计价范围开始*/
	private Double expenseStart;
	/**计价范围结束*/
	private Double expenseEnd;
	/**单价*/
	private Double unitPrice;
	/**备注*/
	private String remarks;
	/**父节点Id*/
	private Long portContractId;
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public String getCountUnit() {
		return countUnit;
	}
	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
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
	@Column
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Transient
	public String getExpenseTypeView() {
		expenseTypeView = DictUtils.getDictSingleName(expenseType);
		return expenseTypeView;
	}
	public void setExpenseTypeView(String expenseTypeView) {
		this.expenseTypeView = expenseTypeView;
	}
	@Transient
	public String getCountUnitView() {
		countUnitView = DictUtils.getDictSingleName(countUnit);
		return countUnitView;
	}
	public void setCountUnitView(String countUnitView) {
		this.countUnitView = countUnitView;
	}
	
	
	
}
