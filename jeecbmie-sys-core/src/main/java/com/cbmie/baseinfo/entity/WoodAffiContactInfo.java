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
 * 关联单位__银行信息
 */
@Entity
@Table(name = "WOOD_AFFI_CONTACT_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodAffiContactInfo extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 姓名* */
	private String name;
	/** 是否默认* */
	private String ifDefault;
	/** 地址* */
	private String addr;
	/** 职位* */
	private String position;
	/** 电话* */
	private String tel;
	/** 手机* */
	private String mobil;
	/** 传真* */
	private String fax;
	/** EMAIL* */
	private String email;
	/** 主营品种* */
	private String breed;
	
	/**
	 * 关联id
	 */
	private String parentId;
	
	@Column(name = "PARENT_ID", nullable = false)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIfDefault() {
		return ifDefault;
	}

	public void setIfDefault(String ifDefault) {
		this.ifDefault = ifDefault;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}



}