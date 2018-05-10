package com.cbmie.baseinfo.entity;

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
 * 港口
 */
	@Entity
	@Table(name = "woodgk")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@DynamicUpdate
	@DynamicInsert
	public class WoodGk implements java.io.Serializable{
		private static final long serialVersionUID = 1L;
	
		/** 基础字段 **/
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
		/** 流程是否结束，结束为'over' **/
		private String sysmemo3;
		/** 业务字段 **/
		/** 编码 **/
		private String bm;
		/** 港口名 **/
		private String gkm;
		/** 国家 **/
		private String gj;
		/** 地区 **/
		private String dq;
		/** 免堆期 **/
		private Double freeDays;

		@Id
		@GeneratedValue
		@Column(name = "ID", unique = true, nullable = false)
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

		public String getBm() {
			return bm;
		}

		public void setBm(String bm) {
			this.bm = bm;
		}

		public String getGkm() {
			return gkm;
		}

		public void setGkm(String gkm) {
			this.gkm = gkm;
		}

		public String getGj() {
			return gj;
		}

		public void setGj(String gj) {
			this.gj = gj;
		}

		public String getDq() {
			return dq;
		}

		public void setDq(String dq) {
			this.dq = dq;
		}

		public Double getFreeDays() {
			return freeDays;
		}

		public void setFreeDays(Double freeDays) {
			this.freeDays = freeDays;
		}
	}