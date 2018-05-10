package com.cbmie.lh.baseInfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
/**
 * 商品指标
 * */
@Entity
@Table(name="LH_GOODSINDEX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class GoodsIndex extends BaseEntity {

	/**
	 * 指标名称
	 * */
	private String indexName;
	
	/**
	 * 指标编码
	 * */
	private String indexCode;
	/**
	 * 指标名称（英文）
	 * */
	private String indexNameE;
	/**
	 * 单位
	 * */
	private String unit;
	/**
	 * 显示顺序
	 * */
	private int showOrder;
	/**
	 * 备注
	 * */
	private String comments;
	
	/**
	 * 状态：1启用、0停用
	 */
	private Integer status;
	
	@Column
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(length=50)
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	@Column(length=50)
	public String getIndexCode() {
		return indexCode;
	}
	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	@Column(length=50)
	public String getIndexNameE() {
		return indexNameE;
	}
	public void setIndexNameE(String indexNameE) {
		this.indexNameE = indexNameE;
	}
	@Column(length=50)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getShowOrder() {
		return showOrder;
	}
	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}
	@Column(length=1000)
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
