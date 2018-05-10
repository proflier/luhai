package com.cbmie.genMac.logistics.entity;

import java.util.ArrayList;
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
 * 货代
 * @author czq
 */
@Entity
@Table(name = "freight")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Freight extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 货权单位
	 */
	private String goodsRightUnit;
	
	/**
	 * 提单号
	 */
	private String invoiceNo;
	
	/**
	 * 合同号
	 */
	private String contractNo;
	
	/**
	 * 物流公司名称
	 */
	private String logisticsUnitName;
	
	/**
	 * 物流公司地址
	 */
	private String logisticsUnitAddr;
	
	/**
	 * 物流公司邮编
	 */
	private String logisticsUnitZipcode;
	
	/**
	 * 物流公司联系人
	 */
	private String logisticsUnitContacts;
	
	/**
	 * 物流公司联系人电话
	 */
	private String logisticsUnitContactsPhone;
	
	/**
	 * 物流公司联系人传真
	 */
	private String logisticsUnitContactsFax;
	
	/**
	 * 送货要求
	 */
	private String requirement;
	
	/**
	 * 堆存条件
	 */
	private String conditions;
	
	/**
	 * 货代商品
	 */
	private List<FreightGoods> freightGoods = new ArrayList<FreightGoods>();

	@Column(name = "GOODS_RIGHT_UNIT")
	public String getGoodsRightUnit() {
		return goodsRightUnit;
	}

	public void setGoodsRightUnit(String goodsRightUnit) {
		this.goodsRightUnit = goodsRightUnit;
	}

	@Column(name = "INVOICE_NO")
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "LOGISTICS_UNIT_NAME")
	public String getLogisticsUnitName() {
		return logisticsUnitName;
	}

	public void setLogisticsUnitName(String logisticsUnitName) {
		this.logisticsUnitName = logisticsUnitName;
	}

	@Column(name = "LOGISTICS_UNIT_ADDR")
	public String getLogisticsUnitAddr() {
		return logisticsUnitAddr;
	}

	public void setLogisticsUnitAddr(String logisticsUnitAddr) {
		this.logisticsUnitAddr = logisticsUnitAddr;
	}

	@Column(name = "LOGISTICS_UNIT_ZIPCODE")
	public String getLogisticsUnitZipcode() {
		return logisticsUnitZipcode;
	}

	public void setLogisticsUnitZipcode(String logisticsUnitZipcode) {
		this.logisticsUnitZipcode = logisticsUnitZipcode;
	}

	@Column(name = "LOGISTICS_UNIT_CONTACTS")
	public String getLogisticsUnitContacts() {
		return logisticsUnitContacts;
	}

	public void setLogisticsUnitContacts(String logisticsUnitContacts) {
		this.logisticsUnitContacts = logisticsUnitContacts;
	}

	@Column(name = "LOGISTICS_UNIT_CONTACTS_PHONE")
	public String getLogisticsUnitContactsPhone() {
		return logisticsUnitContactsPhone;
	}

	public void setLogisticsUnitContactsPhone(String logisticsUnitContactsPhone) {
		this.logisticsUnitContactsPhone = logisticsUnitContactsPhone;
	}

	@Column(name = "LOGISTICS_UNIT_CONTACTS_FAX")
	public String getLogisticsUnitContactsFax() {
		return logisticsUnitContactsFax;
	}

	public void setLogisticsUnitContactsFax(String logisticsUnitContactsFax) {
		this.logisticsUnitContactsFax = logisticsUnitContactsFax;
	}

	@Column
	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	@Column
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<FreightGoods> getFreightGoods() {
		return freightGoods;
	}

	public void setFreightGoods(List<FreightGoods> freightGoods) {
		this.freightGoods = freightGoods;
	}
	
}
