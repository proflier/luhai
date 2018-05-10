package com.cbmie.lh.sale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
@Entity
@Table(name = "LH_SALE_GOODSINDEX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleContractGoodsIndex extends BaseEntity{
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

	private Long saleContractGoodsId;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
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

	@Column(name = "SALECONTRACTGOODS_ID", nullable = false)
	public Long getSaleContractGoodsId() {
		return saleContractGoodsId;
	}

	public void setSaleContractGoodsId(Long saleContractGoodsId) {
		this.saleContractGoodsId = saleContractGoodsId;
	}
}
