package com.cbmie.woodNZ.baseInfo.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 商品信息
 */
@Entity
@Table(name = "woodbusinesscompany")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodBusinessCompanySub  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 基础字段* */
	private Long id;
	private String booker;
	private Date recordDate;
	private String registDept;
	private String updater;
	private Date updateDate;
	private long processinstanceid;
	private String registCompy;
	private String title;
	private String sysmemo1;
	private String sysmemo2;
	private String sysmemo3;
	/** 业务字段* */
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
	/** 备注* */
	private String remark;
	/** 关联公司主表id* */
	private String woodBusinessCompanyId;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBooker() {
		return booker;
	}

	public void setBooker(String booker) {
		this.booker = booker;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getRegistDept() {
		return registDept;
	}

	public void setRegistDept(String registDept) {
		this.registDept = registDept;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getProcessinstanceid() {
		return processinstanceid;
	}

	public void setProcessinstanceid(long processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public String getRegistCompy() {
		return registCompy;
	}

	public void setRegistCompy(String registCompy) {
		this.registCompy = registCompy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSysmemo1() {
		return sysmemo1;
	}

	public void setSysmemo1(String sysmemo1) {
		this.sysmemo1 = sysmemo1;
	}

	public String getSysmemo2() {
		return sysmemo2;
	}

	public void setSysmemo2(String sysmemo2) {
		this.sysmemo2 = sysmemo2;
	}

	public String getSysmemo3() {
		return sysmemo3;
	}

	public void setSysmemo3(String sysmemo3) {
		this.sysmemo3 = sysmemo3;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWoodBusinessCompanyId() {
		return woodBusinessCompanyId;
	}

	public void setWoodBusinessCompanyId(String woodBusinessCompanyId) {
		this.woodBusinessCompanyId = woodBusinessCompanyId;
	}

	

	
}
