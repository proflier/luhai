package com.cbmie.lh.sale.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**结算出库中间表*/
@Entity
@Table(name = "LH_SALE_INVOICEOUTREL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class SaleInvoiceOutRel implements Serializable {
	private static final long serialVersionUID = -4859351896367965463L;

	private Long id;
	private Long invoiceId;
	private Long outstockDetailId;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOutstockDetailId() {
		return outstockDetailId;
	}
	public void setOutstockDetailId(Long outstockDetailId) {
		this.outstockDetailId = outstockDetailId;
	}
	public Long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
}
