package com.cbmie.lh.logistic.entity;

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

/**
 * 报关单
 */
@Entity
@Table(name = "LH_CUSTOMS_DECLARATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class CustomsDeclaration extends BaseEntity {

	/**
	 * 船名
	 */
	private String shipNo;
	
	/**
	 * 海关编号
	 */
	private String customsCode;
	
	/**
	 * 收发货人
	 */
	private String consignee;
	
	/**
	 * 进口口岸
	 */
	private String importPort;
	
	/**
	 * 进口日期
	 */
	private Date importDate;
	
	/**
	 * 申报日期
	 */
	private Date declarationDate;
	
	/**
	 * 消费使用单位
	 */
	private String consumptionUnit;
	
	/**
	 * 运输方式
	 */
	private String transportation;
	
	/**
	 * 运输工具名称
	 */
	private String transportName;
	
	/**
	 * 提运单号
	 */
	private String deliveryNumber;
	
	/**
	 * 申报单位
	 */
	private String applicationUnit;
	
	/**
	 * 监管方式
	 */
	private String regulatoryWay;
	
	/**
	 * 征免性质
	 */
	private String natureExemption;
	
	/**
	 * 备案号
	 */
	private String record;
	
	/**
	 * 贸易国（地区）
	 */
	private String tradeNation;
	
	/**
	 * 启运国（地区）
	 */
	private String beginNation;
	
	/**
	 * 装货港
	 */
	private String loadingPort;
	
	/**
	 * 境内目的地
	 */
	private String domesticDestination;
	
	/**
	 * 许可证号
	 */
	private String license;
	
	/**
	 * 成交方式
	 */
	private String termsDelivery;
	
	/**
	 * 运费
	 */
	private String freightFee;
	
	/**
	 * 保费
	 */
	private String premium;
	
	/**
	 * 杂费
	 */
	private String pettyFee;
	
	/**
	 * 合同协议号
	 */
	private String contractNo;
	
	/**
	 * 件数
	 */
	private String number;
	
	/**
	 * 包装种类
	 */
	private String packageType;
	
	/**
	 * 毛重
	 */
	private String grossWeight;
	
	/**
	 * 净重
	 */
	private String suttle;
	
	/**
	 * 集装箱号
	 */
	private String containerNo;
	
	/**
	 * 随附单证
	 */
	private String accompanyingDocument;
	
	/**
	 * 标记唛码及备注
	 */
	private String remark;
	
	/**
	 * 代理公司
	 */
	private String agencyCompany;
	
	/**
	 * 海关放行日期
	 */
	private Date customsReleaseDate;
	
	/**
	 * 商检放行日期
	 */
	private Date inspectionReleaseDate;
	
	List<CustomsDeclarationGoods> goodsList = new ArrayList<CustomsDeclarationGoods>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pid")
	public List<CustomsDeclarationGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<CustomsDeclarationGoods> goodsList) {
		this.goodsList = goodsList;
	}

	@Column
	public String getShipNo() {
		return shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	@Column
	public String getCustomsCode() {
		return customsCode;
	}

	public void setCustomsCode(String customsCode) {
		this.customsCode = customsCode;
	}

	@Column
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column
	public String getImportPort() {
		return importPort;
	}

	public void setImportPort(String importPort) {
		this.importPort = importPort;
	}

	@Column
	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	@Column
	public Date getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}

	@Column
	public String getConsumptionUnit() {
		return consumptionUnit;
	}

	public void setConsumptionUnit(String consumptionUnit) {
		this.consumptionUnit = consumptionUnit;
	}

	@Column
	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	@Column
	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	@Column
	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	@Column
	public String getApplicationUnit() {
		return applicationUnit;
	}

	public void setApplicationUnit(String applicationUnit) {
		this.applicationUnit = applicationUnit;
	}

	@Column
	public String getRegulatoryWay() {
		return regulatoryWay;
	}

	public void setRegulatoryWay(String regulatoryWay) {
		this.regulatoryWay = regulatoryWay;
	}

	@Column
	public String getNatureExemption() {
		return natureExemption;
	}

	public void setNatureExemption(String natureExemption) {
		this.natureExemption = natureExemption;
	}

	@Column
	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Column
	public String getTradeNation() {
		return tradeNation;
	}

	public void setTradeNation(String tradeNation) {
		this.tradeNation = tradeNation;
	}

	@Column
	public String getBeginNation() {
		return beginNation;
	}

	public void setBeginNation(String beginNation) {
		this.beginNation = beginNation;
	}

	@Column
	public String getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}

	@Column
	public String getDomesticDestination() {
		return domesticDestination;
	}

	public void setDomesticDestination(String domesticDestination) {
		this.domesticDestination = domesticDestination;
	}

	@Column
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Column
	public String getTermsDelivery() {
		return termsDelivery;
	}

	public void setTermsDelivery(String termsDelivery) {
		this.termsDelivery = termsDelivery;
	}

	@Column
	public String getFreightFee() {
		return freightFee;
	}

	public void setFreightFee(String freightFee) {
		this.freightFee = freightFee;
	}

	@Column
	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	@Column
	public String getPettyFee() {
		return pettyFee;
	}

	public void setPettyFee(String pettyFee) {
		this.pettyFee = pettyFee;
	}

	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column
	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	@Column
	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	@Column
	public String getSuttle() {
		return suttle;
	}

	public void setSuttle(String suttle) {
		this.suttle = suttle;
	}

	@Column
	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	@Column
	public String getAccompanyingDocument() {
		return accompanyingDocument;
	}

	public void setAccompanyingDocument(String accompanyingDocument) {
		this.accompanyingDocument = accompanyingDocument;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column
	public String getAgencyCompany() {
		return agencyCompany;
	}

	public void setAgencyCompany(String agencyCompany) {
		this.agencyCompany = agencyCompany;
	}

	@Column
	public Date getCustomsReleaseDate() {
		return customsReleaseDate;
	}

	public void setCustomsReleaseDate(Date customsReleaseDate) {
		this.customsReleaseDate = customsReleaseDate;
	}

	@Column
	public Date getInspectionReleaseDate() {
		return inspectionReleaseDate;
	}

	public void setInspectionReleaseDate(Date inspectionReleaseDate) {
		this.inspectionReleaseDate = inspectionReleaseDate;
	}

}
