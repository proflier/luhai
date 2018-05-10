package com.cbmie.woodNZ.baseInfo.entity;

import java.util.Date;
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
 * 仓库
 */
	@Entity
	@Table(name = "woodck")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@DynamicUpdate
	@DynamicInsert
	public class WoodCk implements java.io.Serializable{
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
		/** 流程是否结束，结束为'over'* */
		private String sysmemo3;
		/** 业务字段* */
		/** 单位编号* */
		private String no;
		
		/** 财务编号* */
		private String cwbm;
		
		/** 单位名称* */
		private String companyName;
		/** 单位简称* */
		private String shortName;
		/** 所属仓库* */
		private String belongCompany;
		
		/** 所属仓库编码* */
		private String belongCompanyNo;
		
		/** 所属仓库财务编码* */
		private String belongCompanyCwbm;
		
		
		/** 仓库类别* */
		private String cklb;
		/** 仓储费结算方案* */
		private String balanceType;
		/** 单位类型* */
		private String type;
		/** 地址* */
		private String addr;
		/** 电话* */
		private String tel;
		/** 传真* */
		private String fax;
		/** 企业性质* */
		private String property;
		/** 法人* */
		private String corporate;
		/** 总公司* */
		private String parentCompany;
		/** 备注* */
		private String remark;
		private String nationality;//国籍
		
		/** 联系人* */
		private String lxr;
		
		/** 编辑保存后不可修改* */
		private String flag;
		/** 映射user表 */
		private String userId; 
		/** 地区 **/
		private String area;
		
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getAdminDivisionInfoId() {
			return adminDivisionInfoId;
		}

		public void setAdminDivisionInfoId(String adminDivisionInfoId) {
			this.adminDivisionInfoId = adminDivisionInfoId;
		}

		private String ckArea;//仓库所在地区
		
		private String adminDivisionInfoId;//行政区域	
		//private WoodckExt	woodckExt;

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

		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getShortName() {
			return shortName;
		}

		public void setShortName(String shortName) {
			this.shortName = shortName;
		}

		public String getBelongCompany() {
			return belongCompany;
		}

		public void setBelongCompany(String belongCompany) {
			this.belongCompany = belongCompany;
		}

		public String getCklb() {
			return cklb;
		}

		public void setCklb(String cklb) {
			this.cklb = cklb;
		}

		public String getBalanceType() {
			return balanceType;
		}

		public void setBalanceType(String balanceType) {
			this.balanceType = balanceType;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getFax() {
			return fax;
		}

		public void setFax(String fax) {
			this.fax = fax;
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		public String getCorporate() {
			return corporate;
		}

		public void setCorporate(String corporate) {
			this.corporate = corporate;
		}

		public String getParentCompany() {
			return parentCompany;
		}

		public void setParentCompany(String parentCompany) {
			this.parentCompany = parentCompany;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getCwbm() {
			return cwbm;
		}

		public void setCwbm(String cwbm) {
			this.cwbm = cwbm;
		}

		public String getBelongCompanyNo() {
			return belongCompanyNo;
		}

		public void setBelongCompanyNo(String belongCompanyNo) {
			this.belongCompanyNo = belongCompanyNo;
		}

		public String getBelongCompanyCwbm() {
			return belongCompanyCwbm;
		}

		public void setBelongCompanyCwbm(String belongCompanyCwbm) {
			this.belongCompanyCwbm = belongCompanyCwbm;
		}
		
		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getLxr() {
			return lxr;
		}

		public void setLxr(String lxr) {
			this.lxr = lxr;
		}

		public String getNationality() {
			return nationality;
		}

		public void setNationality(String nationality) {
			this.nationality = nationality;
		}
		
		@Column(length = 20)
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}

		public String getCkArea() {
			return ckArea;
		}

		public void setCkArea(String ckArea) {
			this.ckArea = ckArea;
		}

		
	}