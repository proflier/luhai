package com.cbmie.woodNZ.salesContract.entity;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 采销同批子表(中间表：关联采购合同主表id和销售合同主表id)
 * 
 * @author liminghao
 *
 */
@Entity
@Table(name = "wood_purchase_sale_same_sub")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class PurchaseSaleSameSub implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 关联采销同批主表id
	 */
	private Long parentId;
	
	/**
	 * 采购合同主表id
	 */
	private WoodCghtJk woodCghtJk;
	
	/**
	 * 销售合同主表id
	 */
	private WoodSaleContract woodSaleContract;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "PARENT_ID", nullable = false)
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "wood_cght_jk_id", nullable = false)
	public WoodCghtJk getWoodCghtJk() {
		return woodCghtJk;
	}
	public void setWoodCghtJk(WoodCghtJk woodCghtJk) {
		this.woodCghtJk = woodCghtJk;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "wood_sale_contract_id", nullable = false)
	public WoodSaleContract getWoodSaleContract() {
		return woodSaleContract;
	}
	public void setWoodSaleContract(WoodSaleContract woodSaleContract) {
		this.woodSaleContract = woodSaleContract;
	}
}
