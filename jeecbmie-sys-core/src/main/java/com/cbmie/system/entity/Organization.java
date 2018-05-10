package com.cbmie.system.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 机构entity
 */
@Entity
@Table(name = "organization")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class Organization implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orgName;
	private Integer pid;
	/**0公司 1部门*/
	private String orgType;
	private Integer orgSort;
	private Integer orgLevel;
	private String orgCode;
	private Integer areaId;
	private String text;
	private List<Organization> children = new ArrayList<Organization>();

	private String orgPrefix;
	
	private String orgBusiCode;
	
	
	
	// Constructors

	/** default constructor */
	public Organization() {
	}

	/** minimal constructor */
	public Organization(Integer id, String orgName) {
		this.id = id;
		this.orgName = orgName;
	}

	/** full constructor */
	public Organization(Integer id, String orgName, Integer pid,
			String orgType, Integer orgSort, Integer orgLevel) {
		this.id = id;
		this.orgName = orgName;
		this.pid = pid;
		this.orgType = orgType;
		this.orgSort = orgSort;
		this.orgLevel = orgLevel;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "org_name", nullable = false)
	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "pid")
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "org_type")
	public String getOrgType() {
		return this.orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	@Column(name = "org_sort")
	public Integer getOrgSort() {
		return this.orgSort;
	}

	public void setOrgSort(Integer orgSort) {
		this.orgSort = orgSort;
	}

	@Column(name = "org_level")
	public Integer getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}
	@Column(name = "org_code")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	@Column(name = "area_id")
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	@Transient
	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}
	@Transient
	public String getText() {
		text = orgName;
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "org_prefix")
	public String getOrgPrefix() {
		return orgPrefix;
	}

	public void setOrgPrefix(String orgPrefix) {
		this.orgPrefix = orgPrefix;
	}
	
	@Column(name = "org_busi_code")
	public String getOrgBusiCode() {
		return orgBusiCode;
	}

	public void setOrgBusiCode(String orgBusiCode) {
		this.orgBusiCode = orgBusiCode;
	}

}