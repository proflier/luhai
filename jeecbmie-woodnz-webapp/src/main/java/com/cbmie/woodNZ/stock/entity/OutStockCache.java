package com.cbmie.woodNZ.stock.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 出库 - 选择放货弹框列表
 * 
 * @author liminghao
 */
@Entity
@Table(name = "v_wood_out_stock_cache")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class OutStockCache  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键*/
	private Long id;
	/** 客户单位*/
	private String customerUnit;
	/** 关联id*/
	private Long outId;
	/** 金额*/
	private String money;
	/** 状态（区分下游交单和放货，下游交单为数字，放货为汉子：生效，已提交，草稿）*/
	private String state;
	/** 销售合同号*/
	private String saleContractNo;
	/** 提单号*/
	private String billNo;
	
	@Column
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	@Column
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Column
	public String getSaleContractNo() {
		return saleContractNo;
	}
	public void setSaleContractNo(String saleContractNo) {
		this.saleContractNo = saleContractNo;
	}
	@Column
	public String getCustomerUnit() {
		return customerUnit;
	}
	public void setCustomerUnit(String customerUnit) {
		this.customerUnit = customerUnit;
	}
	@Column
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	@Id
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	@Column
	public Long getOutId() {
		return outId;
	}
	public void setOutId(Long outId) {
		this.outId = outId;
	}
	
	private String userId;
	
	@Column(name = "USERID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	private Integer deptId;
	private Integer companyId;
	
	@Column(name = "DEPTID")
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	@Column(name = "COMPANYID")
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}