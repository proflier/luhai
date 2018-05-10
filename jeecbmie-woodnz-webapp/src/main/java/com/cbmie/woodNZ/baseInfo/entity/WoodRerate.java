package com.cbmie.woodNZ.baseInfo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.cbmie.common.entity.BaseEntity;

/**
 * 汇率
 */
@Entity
@Table(name = "woodrerate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodRerate extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	

	private String year;
	
	
	private String month;
	
	private double rateUS;//对美元汇率
	
	private double rateUN;//对欧元汇率
	
	private double rateNZ;//对新西兰元汇率
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public double getRateUS() {
		return rateUS;
	}

	public void setRateUS(double rateUS) {
		this.rateUS = rateUS;
	}

	public double getRateUN() {
		return rateUN;
	}

	public void setRateUN(double rateUN) {
		this.rateUN = rateUN;
	}

	public double getRateNZ() {
		return rateNZ;
	}

	public void setRateNZ(double rateNZ) {
		this.rateNZ = rateNZ;
	}
	
}