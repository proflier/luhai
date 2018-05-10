package com.cbmie.woodNZ.stock.entity;

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

import com.cbmie.common.entity.BaseEntity;
import com.cbmie.woodNZ.salesDelivery.entity.RealSalesDeliveryGoods;

/**
 * 库存中间表，关联入库子表和出库子表(入库子表实体名：InStockGoods， 出库子表实体名：OutStockSub)
 * @author liminghao
 * 2016年8月11日
 */
@Entity
@Table(name = "WOOD_INSTOCK_OUTSTOCK_RELATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InStockOutStockRelation implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	private Long id;
	
	/**
	 * 入库子表
	 */
	private InStockGoods inStockGoods;
	
	/**
	 * 出库子表
	 */
	private OutStockSub outStockSub;
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IN_STOCK_ID", nullable = false)
	public InStockGoods getInStockGoods() {
		return inStockGoods;
	}

	public void setInStockGoods(InStockGoods inStockGoods) {
		this.inStockGoods = inStockGoods;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OUT_STOCK_ID", nullable = false)
	public OutStockSub getOutStockSub() {
		return outStockSub;
	}

	public void setOutStockSub(OutStockSub outStockSub) {
		this.outStockSub = outStockSub;
	}

}
