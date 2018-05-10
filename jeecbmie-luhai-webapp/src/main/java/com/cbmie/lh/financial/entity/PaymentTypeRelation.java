package com.cbmie.lh.financial.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

@Entity
@Table(name = "LH_PAYMENT_TYPE_RELATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PaymentTypeRelation extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1658341525904970950L;

	/**
	 * 付款项目
	 */
	private String payType;

	/**
	 * 付款关联名称
	 */
	private String paymentRelation;

	/**
	 * 付款关联
	 */
	private String actionResource;
	
	/**
	 * 调转action url
	 */
	private String url;
	
	/**
	 * 能否下拉 1:可以  0不可以
	 */
	private String hasArrow;
	
	@Column
	public String getHasArrow() {
		return hasArrow;
	}

	public void setHasArrow(String hasArrow) {
		this.hasArrow = hasArrow;
	}

	@Column
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column
	public String getPaymentRelation() {
		return paymentRelation;
	}

	public void setPaymentRelation(String paymentRelation) {
		this.paymentRelation = paymentRelation;
	}

	@Column
	public String getActionResource() {
		return actionResource;
	}

	public void setActionResource(String actionResource) {
		this.actionResource = actionResource;
	}

}
