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
 * 商品类别
 * */
@Entity
@Table(name="LH_GOODSTYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class GoodsType extends BaseEntity {
	
	/**名称*/
	private String typeName;
	
	/**
	 * 编码
	 * */
	private String typeCode;
	
	/**
	 * 对应财务编码(暂时去掉)
	 * */
	private String financeCode;
	
	/**
	 * 备注
	 */
	private String comments;
	
	/**
	 * 是否启用 0 不启用 1启用
	 * */
	private String typeStatus = "1";

	@Column(length=50)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
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
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(length=50)
	public String getFinanceCode() {
		return financeCode;
	}

	public void setFinanceCode(String financeCode) {
		this.financeCode = financeCode;
	}

	@Column(length=1000)
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	@Column(length=10)
	public String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}
}
