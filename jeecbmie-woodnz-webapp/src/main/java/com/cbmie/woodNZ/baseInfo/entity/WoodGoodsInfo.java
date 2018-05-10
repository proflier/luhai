package com.cbmie.woodNZ.baseInfo.entity;

import static javax.persistence.GenerationType.IDENTITY;
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
 * 商品信息
 */
@Entity
@Table(name = "woodgoodsinfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodGoodsInfo  implements java.io.Serializable {

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
	/** 级别* */
	private String classes;
	/** 级别名称* */
	private String className;
	/** 编码* */
	private String code;
	/** 名称* */
	private String name;
	/** 是否是濒危* */
	private String ifDanger;
	/** 备注* */
	private String remark;
	/** 登记时间* */
	private Date sqrq;
	/** 登记人* */
	private String sqr;
	/** 登记部门* */
	private String dept;
	private String description;//描述
	private String affiliated;//所属类别
	
	/** 换算数* */
	private double hss;
	
	/** 编辑保存后不可修改* */
	private String flag;
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
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

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIfDanger() {
		return ifDanger;
	}

	public void setIfDanger(String ifDanger) {
		this.ifDanger = ifDanger;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSqrq() {
		return sqrq;
	}

	public void setSqrq(Date sqrq) {
		this.sqrq = sqrq;
	}

	public String getSqr() {
		return sqr;
	}

	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAffiliated() {
		return affiliated;
	}

	public void setAffiliated(String affiliated) {
		this.affiliated = affiliated;
	}

	public double getHss() {
		return hss;
	}

	public void setHss(double hss) {
		this.hss = hss;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
	
}
