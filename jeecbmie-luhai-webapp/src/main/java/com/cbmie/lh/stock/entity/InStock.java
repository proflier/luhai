package com.cbmie.lh.stock.entity;

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
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 入库
 */
@Entity
@Table(name = "LH_IN_STOCK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class InStock extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 上游提单号
	 */
	private String billNo;

	/** 数量 **/
	private double numbers;

	/**
	 * 入库编号
	 */
	private String inStockId;

	/**
	 * 质检编码
	 */
	private String inspectionNo;

	/**
	 * 入库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date inStockDate;

	/**
	 * 船名
	 */
	private String shipName;
	
	/**
	 * 船编号
	 */
	private String shipNo;

	/**
	 * 入库类型
	 */
	private String inStockType;

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
	 * 制单日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date createStockDate;

	/**
	 * 确认状态
	 */
	private String confirm;
	
	/**
	 * 复核状态
	 */
	private String review;
	
	/**
	 * 是否历史数据  	1：是   0：否
	 */
	private String historyData="0";
	
	@Column
	public String getHistoryData() {
		return historyData;
	}

	public void setHistoryData(String historyData) {
		this.historyData = historyData;
	}

	@Column
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Column
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	/**
	 * 到单商品
	 */
	@JsonIgnore
	private List<InStockGoods> inStockGoodsList = new ArrayList<InStockGoods>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<InStockGoods> getInStockGoodsList() {
		return inStockGoodsList;
	}

	public void setInStockGoodsList(List<InStockGoods> inStockGoodsList) {
		this.inStockGoodsList = inStockGoodsList;
	}

	@Column
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	@Column
	public double getNumbers() {
		return numbers;
	}

	public void setNumbers(double numbers) {
		this.numbers = numbers;
	}

	@Column
	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	@Column
	public Date getInStockDate() {
		return inStockDate;
	}

	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
	}

	@Column
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	@Column
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	@Column
	public String getInStockType() {
		return inStockType;
	}

	public void setInStockType(String inStockType) {
		this.inStockType = inStockType;
	}

	@Column
	public String getWarehouseAccept() {
		return warehouseAccept;
	}

	public void setWarehouseAccept(String warehouseAccept) {
		this.warehouseAccept = warehouseAccept;
	}

	@Column
	public String getInspectionNo() {
		return inspectionNo;
	}

	public void setInspectionNo(String inspectionNo) {
		this.inspectionNo = inspectionNo;
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

	@Column
	public Date getCreateStockDate() {
		return createStockDate;
	}

	public void setCreateStockDate(Date createStockDate) {
		this.createStockDate = createStockDate;
	}

}