package com.cbmie.woodNZ.salesContract.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.cbmie.common.entity.BaseEntity;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 采销同批主表
 * 
 * @author liminghao
 */
@Entity
@Table(name = "wood_purchase_sale_same")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PurchaseSaleSame extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 收文单位* */
	private String receiveCompany ;
	/** 发文单位* */
	private String postCompany ;
	/** 日期* */
	private Date applyDate ;
	/** 经办人* */
	private String transactor ;
	/** 部门主管* */
	private String deptLeader ;
	/** 主旨* */
	private String keynote ;
	/** 正文* */
	private String content ;
	/** 状态* */
	private String state = "草稿";
	/** 采购合同主表list(临时变量，不存储数据库)* */
	@Transient
	public List<WoodCghtJk> purchaseContractList ;
	/** 销售合同主表list(临时变量，不存储数据库)* */
	@Transient
	public List<WoodSaleContract> saleContractList ;
	/**
	 * 采销同批子表
	 */
	@JsonIgnore
	private List<PurchaseSaleSameSub> purchaseSaleSameSubList = new ArrayList<PurchaseSaleSameSub>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<PurchaseSaleSameSub> getSaleContractSubList() {
		return purchaseSaleSameSubList;
	}

	public void setSaleContractSubList(List<PurchaseSaleSameSub> saleContractSubList) {
		this.purchaseSaleSameSubList = saleContractSubList;
	}
	
	@Column
	public String getReceiveCompany() {
		return receiveCompany;
	}
	public void setReceiveCompany(String receiveCompany) {
		this.receiveCompany = receiveCompany;
	}
	@Column
	public String getPostCompany() {
		return postCompany;
	}
	public void setPostCompany(String postCompany) {
		this.postCompany = postCompany;
	}
	@Column
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	@Column
	public String getTransactor() {
		return transactor;
	}
	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}
	@Column
	public String getDeptLeader() {
		return deptLeader;
	}
	public void setDeptLeader(String deptLeader) {
		this.deptLeader = deptLeader;
	}
	@Column
	public String getKeynote() {
		return keynote;
	}
	public void setKeynote(String keynote) {
		this.keynote = keynote;
	}
	@Column
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
