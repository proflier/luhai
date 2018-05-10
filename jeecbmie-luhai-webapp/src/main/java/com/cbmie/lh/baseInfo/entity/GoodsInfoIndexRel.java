package com.cbmie.lh.baseInfo.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Entity
@Table(name="LH_GOODSINFOINDEXREL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class GoodsInfoIndexRel {
	
	private Long id;
	private Long infoId;
	private GoodsIndex goodsIndex;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "index_id", nullable = false)
	public GoodsIndex getGoodsIndex() {
		return goodsIndex;
	}
	public void setGoodsIndex(GoodsIndex goodsIndex) {
		this.goodsIndex = goodsIndex;
	}

}
