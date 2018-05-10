package com.cbmie.lh.logistic.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name = "LH_BILLS_GOODSINDEX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class LhBillsGoodsIndex extends BaseEntity {
	/**
	 * 指标名称
	 */
	private String indexName;
	private String indexNameE;
	private String unit;

	/**
	 * 拒收值
	 */
	private String rejectionValue;
	/**
	 * 约定值
	 */
	private String conventions;

	/**
	 * 扣罚条款
	 */
	private String terms;

	private String parentId;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getRejectionValue() {
		return rejectionValue;
	}

	public void setRejectionValue(String rejectionValue) {
		this.rejectionValue = rejectionValue;
	}

	public String getConventions() {
		return conventions;
	}

	public void setConventions(String conventions) {
		this.conventions = conventions;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getIndexNameE() {
		return indexNameE;
	}

	public void setIndexNameE(String indexNameE) {
		this.indexNameE = indexNameE;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
