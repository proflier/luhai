package com.cbmie.lh.sale.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 销售报盘_商品_指标
 */
@Entity
@Table(name = "LH_SALE_OFFER_INDEX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleOfferIndex {
	
	private Long id;
	
	/**
	 * pid
	 */
	private Long pid;

	/**
	 * 指标名称(英)
	 */
	private String indexNameEn;

	/**
	 * 指标名称(中)
	 */
	private String indexName;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 检验值
	 */
	private double inspectionValue;
	
	/**
	 * 价格调整说明
	 */
	private String description;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column
	public String getIndexNameEn() {
		return indexNameEn;
	}

	public void setIndexNameEn(String indexNameEn) {
		this.indexNameEn = indexNameEn;
	}

	@Column
	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column
	public double getInspectionValue() {
		return inspectionValue;
	}

	public void setInspectionValue(double inspectionValue) {
		this.inspectionValue = inspectionValue;
	}

	@Column
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
