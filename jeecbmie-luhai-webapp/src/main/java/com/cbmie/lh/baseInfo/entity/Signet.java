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
 * 印章
 */
@Entity
@Table(name = "LH_SIGNET")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Signet extends BaseEntity {

	/**
	 * 印章编码
	 */
	private String signetCode;
	/**
	 * 印章类别名称
	 */
	private String typeName;
	/**
	 * 印章类别
	 */
	private String typeCode;
	/**
	 * 所属公司
	 */
	private Integer orgId;
	/**
	 * 印章保管人
	 */
	private Integer saveUserId;
	/**
	 * 印章保管人
	 */
	private String saveUserName;
	
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

	@Column(length = 50)
	public String getSignetCode() {
		return signetCode;
	}

	public void setSignetCode(String signetCode) {
		this.signetCode = signetCode;
	}

	@Column(length = 50)
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getSaveUserId() {
		return saveUserId;
	}

	public void setSaveUserId(Integer saveUserId) {
		this.saveUserId = saveUserId;
	}

	@Column
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column
	public String getSaveUserName() {
		return saveUserName;
	}

	public void setSaveUserName(String saveUserName) {
		this.saveUserName = saveUserName;
	}

}
