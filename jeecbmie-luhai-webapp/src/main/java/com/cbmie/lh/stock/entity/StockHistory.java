package com.cbmie.lh.stock.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StockHistory {
	
	/**
	 * 入库编号
	 */
	private String inStockId;

	public String getInStockId() {
		return inStockId;
	}

	public void setInStockId(String inStockId) {
		this.inStockId = inStockId;
	}

	/**
	 * 入库日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date inStockDate;

	/**
	 * 船编号
	 */
	private String shipNo;

	/**
	 * 船名
	 */
	private String shipName;

	/**
	 * 内部合同号
	 */
	private String innerContractNo;

	/** 商品名称/编码 **/
	private String goodsName;

	/** 仓库名称/编码 **/
	private String warehouseName;

	/** 入库数量 **/
	private double quantity;

	/**
	 * 属性 0:初始库存 1:经过调配的库存 2:盘库 3：历史库存
	 */
	private String instockCategory = "3";

	/**
	 * 是否历史数据 1：是 0：否
	 */
	private String historyData = "1";

	/**
	 * 入库类型
	 */
	private String inStockType = "10670002";

	/** 数量单位 **/
	private String units = "10580003";

	public String getHistoryData() {
		return historyData;
	}

	public void setHistoryData(String historyData) {
		this.historyData = historyData;
	}

	public String getInstockCategory() {
		return instockCategory;
	}

	public void setInstockCategory(String instockCategory) {
		this.instockCategory = instockCategory;
	}

	private Long id;
	private String createrNo;// 创建人编号
	private String createrName;// 创建人姓名
	private String createrDept;// 创建人部门
	private Integer deptId;
	private Integer companyId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createDate;// 创建时间

	private String updaterNo;// 修改人编号
	private String updaterName;// 修改人姓名
	private String updaterDept;// 修改人部门
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date updateDate;// 修改时间

	private String summary; // 业务摘要
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 库存成本
	 */
	private double stockCost;

	public double getStockCost() {
		return stockCost;
	}

	public void setStockCost(double stockCost) {
		this.stockCost = stockCost;
	}

	public Date getInStockDate() {
		return inStockDate;
	}

	public void setInStockDate(Date inStockDate) {
		this.inStockDate = inStockDate;
	}

	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getInnerContractNo() {
		return innerContractNo;
	}

	public void setInnerContractNo(String innerContractNo) {
		this.innerContractNo = innerContractNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreaterNo() {
		return createrNo;
	}

	public void setCreaterNo(String createrNo) {
		this.createrNo = createrNo;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCreaterDept() {
		return createrDept;
	}

	public void setCreaterDept(String createrDept) {
		this.createrDept = createrDept;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdaterNo() {
		return updaterNo;
	}

	public void setUpdaterNo(String updaterNo) {
		this.updaterNo = updaterNo;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public String getUpdaterDept() {
		return updaterDept;
	}

	public void setUpdaterDept(String updaterDept) {
		this.updaterDept = updaterDept;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getInStockType() {
		return inStockType;
	}

	public void setInStockType(String inStockType) {
		this.inStockType = inStockType;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

}
