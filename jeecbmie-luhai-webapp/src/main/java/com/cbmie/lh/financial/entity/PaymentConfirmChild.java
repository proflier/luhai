package com.cbmie.lh.financial.entity;

import java.io.Serializable;

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

@Entity
@Table(name = "LH_PAYMENT_CONFIRM_CHILD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PaymentConfirmChild extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1658341525904970950L;

	/**
	 * 付款申请id
	 */
	private long paymentConfirmId;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 分摊金额
	 */
	private double shareAmount;

	/**
	 * 付款项目
	 */
	private String paymentType;

	/**
	 * 付款关联
	 */
	private String paymentRelation;
	
	/**
	 * 费用类别
	 */
	private String feeCategory;
	
	private String feeCategoryView;
	
	@Transient
	public String getFeeCategoryView() {
		feeCategoryView = DictUtils.getDictSingleName(feeCategory);
		return feeCategoryView;
	}

	public void setFeeCategoryView(String feeCategoryView) {
		this.feeCategoryView = feeCategoryView;
	}

	@Column
	public String getFeeCategory() {
		return feeCategory;
	}

	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}

	@Column
	public String getPaymentRelation() {
		return paymentRelation;
	}

	public void setPaymentRelation(String paymentRelation) {
		this.paymentRelation = paymentRelation;
	}

	@Column
	public long getPaymentConfirmId() {
		return paymentConfirmId;
	}

	public void setPaymentConfirmId(long paymentConfirmId) {
		this.paymentConfirmId = paymentConfirmId;
	}

	@Column
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column
	public double getShareAmount() {
		return shareAmount;
	}

	public void setShareAmount(double shareAmount) {
		this.shareAmount = shareAmount;
	}

	@Column
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}
