package com.cbmie.lh.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

@Entity
@Table(name = "LH_PURCHASE_GOODSINDEX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PurchaseGoodsIndex extends BaseEntity {

	/**
	 * 指标名称
	 */
	private String indexName;

	/**
	 * 指标名称（英文）
	 */
	private String indexNameE;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 拒收值
	 */
	private String rejectionValue;

	@Column
	public String getRejectionValue() {
		return rejectionValue;
	}

	public void setRejectionValue(String rejectionValue) {
		this.rejectionValue = rejectionValue;
	}

	/**
	 * 约定值
	 */
	private String conventions;

	/**
	 * 扣罚条款
	 */
	private String terms;

	private String parentId;

	@Column
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	@Column
	public String getIndexNameE() {
		return indexNameE;
	}

	public void setIndexNameE(String indexNameE) {
		this.indexNameE = indexNameE;
	}

	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column
	public String getConventions() {
		return conventions;
	}

	public void setConventions(String conventions) {
		this.conventions = conventions;
	}

	@Column
	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	@Column
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
