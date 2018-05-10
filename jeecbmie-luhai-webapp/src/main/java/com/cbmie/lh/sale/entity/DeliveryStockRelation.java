package com.cbmie.lh.sale.entity;

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

import com.cbmie.lh.stock.entity.InStockGoods;

/**
 * 库存与放货中间关系表
 * @author sunjialiang
 */
@Entity
@Table(name = "LH_DELIVERY_STOCK_RELATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class DeliveryStockRelation  implements java.io.Serializable {

	private static final long serialVersionUID = -5122311729964407048L;

	private Long id;
	/**
	 * 入库id
	 */
	private InStockGoods inStockGoods;
	/**
	 * 出库id
	 */
	private SaleDeliveryGoods salesDeliveryGoods;

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
	@JoinColumn(name = "IN_STOCK_GOODS_ID", nullable = false)
	public InStockGoods getInStockGoods() {
		return inStockGoods;
	}
	public void setInStockGoods(InStockGoods inStockGoods) {
		this.inStockGoods = inStockGoods;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_DELIVERY_GOODS_ID", nullable = false)
	public SaleDeliveryGoods getSalesDeliveryGoods() {
		return salesDeliveryGoods;
	}
	public void setSalesDeliveryGoods(SaleDeliveryGoods salesDeliveryGoods) {
		this.salesDeliveryGoods = salesDeliveryGoods;
	}
	
}
