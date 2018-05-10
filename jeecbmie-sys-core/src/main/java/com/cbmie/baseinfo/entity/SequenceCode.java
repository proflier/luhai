package com.cbmie.baseinfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 编码生成
 */
@Entity
@Table(name = "t_sequence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SequenceCode extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 名称
	 */
	private String sequenceName;

	/**
	 * 值
	 */
	private Integer value;
	
	/**
	 * 公式
	 */
	private String formula;
	
	/**
	 * 备注
	 */
	private String comment;

	@Column
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column
	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	@Column(columnDefinition = "INT default 0",nullable=false)
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Column
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	@Column
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
	

}