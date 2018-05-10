package com.cbmie.woodNZ.stock.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 入库
 * 
 * @author linxiaopeng 2016年6月28日
 */
@Entity
@Table(name = "wood_in_stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InStock extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 入库单号
	 */
	private String inStockId;

	/**
	 * 制单日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date createStockDate;

	/**
	 * 入库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date inStockDate;

	/**
	 * 收货仓库
	 */
	private String receiveWarehouse;

	/**
	 * 公司抬头
	 */
	private String companyName;

	/**
	 * 交货部门
	 */
	private String payDept;

	/**
	 * 船名
	 */
	private String shipName;

	/**
	 * 航次
	 */
	private String voyage;

	/**
	 * 锁定销售单价
	 */
	private String lockSellingPrice;

	/**
	 * 锁定销售合同
	 */
	private String lockContract;

	/**
	 * 是否代拆
	 */
	private String isOpen;

	/**
	 * 入库类型
	 */
	private String inStockType;

	/**
	 * 提单号
	 */
	private String billId;

	/**
	 * 仓库验收人
	 */
	private String warehouseAccept;
	
	/**
	 * 确认人
	 */
	private String determineName;
	
	/**
	 * 确认时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date determineTime;
	
	/**
	 * 确认状态
	 */
	private String confirm;
	
	/**
	 * 币种
	 */
	private String currency;
	
	
	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	@Column
	public String getDetermineName() {
		return determineName;
	}

	public void setDetermineName(String determineName) {
		this.determineName = determineName;
	}

	@Column
	public Date getDetermineTime() {
		return determineTime;
	}

	public void setDetermineTime(Date determineTime) {
		this.determineTime = determineTime;
	}

	/**
	 * 到单商品
	 */
	private List<InStockGoods> inStockGoods = new ArrayList<InStockGoods>();

	@Column
	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	@Column
	public Date getCreateStockDate() {
		return createStockDate;
	}

	public void setCreateStockDate(Date createStockDate) {
		this.createStockDate = createStockDate;
	}

	@Column
	public Date getInStockDate() {
		return inStockDate;
	}

	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
	}

	@Column
	public String getReceiveWarehouse() {
		return receiveWarehouse;
	}

	public void setReceiveWarehouse(String receiveWarehouse) {
		this.receiveWarehouse = receiveWarehouse;
	}

	@Column
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column
	public String getPayDept() {
		return payDept;
	}

	public void setPayDept(String payDept) {
		this.payDept = payDept;
	}

	@Column
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column
	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	@Column
	public String getLockSellingPrice() {
		return lockSellingPrice;
	}

	public void setLockSellingPrice(String lockSellingPrice) {
		this.lockSellingPrice = lockSellingPrice;
	}

	@Column
	public String getLockContract() {
		return lockContract;
	}

	public void setLockContract(String lockContract) {
		this.lockContract = lockContract;
	}

	@Column
	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	@Column
	public String getInStockType() {
		return inStockType;
	}

	public void setInStockType(String inStockType) {
		this.inStockType = inStockType;
	}

	@Column
	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	@Column
	public String getWarehouseAccept() {
		return warehouseAccept;
	}

	public void setWarehouseAccept(String warehouseAccept) {
		this.warehouseAccept = warehouseAccept;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<InStockGoods> getInStockGoods() {
		return inStockGoods;
	}

	public void setInStockGoods(List<InStockGoods> inStockGoods) {
		this.inStockGoods = inStockGoods;
	}

}