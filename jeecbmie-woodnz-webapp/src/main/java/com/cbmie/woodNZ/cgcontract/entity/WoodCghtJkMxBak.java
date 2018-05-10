package com.cbmie.woodNZ.cgcontract.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;

/**
 * 采购合同-进口 子表信息
 * 
 * @author linxiaopeng
 *
 */

@Entity
@Table(name = "WoodCghtJkMxBak")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodCghtJkMxBak extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 子项号 **/
	private String zxh;
	/** 商品编码 **/
	private String spbm;
	/** 商品名称 **/
	private String spmc;
	/** 商品描述 **/
	private String spms;
	/** 品牌 **/
	private String pp;
	/** 类别 **/
	private String lb;
	/** 材种 **/
	private String cz;
	/** 等级 **/
	private String dj;
	/** 规格 **/
	private String gg;
	/** 长度 **/
	private String cd;
	/** 件数 **/
	private String js;
	/** 片数 **/
	private String ps;
	/** 单片体积 **/
	private String dptj;
	/** 立方数 **/
	private String lfs;
	/** 数量单位 **/
	private String sldw;
	/** 采购单价 **/
	private String cgdj;
	/** 币种 **/
	private String cgbz;
	/** 采购金额 **/
	private String cgje;
	/** 预计货到港口 **/
	private String yjhdgg;
	/** 描述 **/
	private String ms;

	/** 是否濒危物种 **/
	private String sfbwwz;

	// 是否原合同
	private boolean isOld;
	// 变更明细id
	private Long bgMxId;
	// 新增变更原因和变更描述
	private String bgReason;
	private String bgDesc;

	private String parentId;
	
	/** 含水率 **/
	private Double waterRate;
	
	@Column
	public Double getWaterRate() {
		return waterRate;
	}

	public void setWaterRate(Double waterRate) {
		this.waterRate = waterRate;
	}

	@Column(name = "PARENT_ID", nullable = false)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "ZXH")
	public String getZxh() {
		return zxh;
	}

	public void setZxh(String zxh) {
		this.zxh = zxh;
	}

	@Column(name = "SPBM")
	public String getSpbm() {
		return spbm;
	}

	public void setSpbm(String spbm) {
		this.spbm = spbm;
	}

	@Column(name = "SPMC")
	public String getSpmc() {
		return spmc;
	}

	public void setSpmc(String spmc) {
		this.spmc = spmc;
	}

	@Column(name = "SPMS")
	public String getSpms() {
		return spms;
	}

	public void setSpms(String spms) {
		this.spms = spms;
	}

	@Column(name = "PP")
	public String getPp() {
		return pp;
	}

	public void setPp(String pp) {
		this.pp = pp;
	}

	@Column(name = "LB")
	public String getLb() {
		return lb;
	}

	public void setLb(String lb) {
		this.lb = lb;
	}

	@Column(name = "CZ")
	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	@Column(name = "DJ")
	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	@Column(name = "GG")
	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	@Column(name = "CD")
	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	@Column(name = "JS")
	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	@Column(name = "PS")
	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	@Column(name = "DPTJ")
	public String getDptj() {
		return dptj;
	}

	public void setDptj(String dptj) {
		this.dptj = dptj;
	}

	@Column(name = "LFS")
	public String getLfs() {
		return lfs;
	}

	public void setLfs(String lfs) {
		this.lfs = lfs;
	}

	@Column(name = "SLDW")
	public String getSldw() {
		return sldw;
	}

	public void setSldw(String sldw) {
		this.sldw = sldw;
	}

	@Column(name = "CGDJ")
	public String getCgdj() {
		return cgdj;
	}

	public void setCgdj(String cgdj) {
		this.cgdj = cgdj;
	}

	@Column(name = "CGBZ")
	public String getCgbz() {
		return cgbz;
	}

	public void setCgbz(String cgbz) {
		this.cgbz = cgbz;
	}

	@Column(name = "CGJE")
	public String getCgje() {
		return cgje;
	}

	public void setCgje(String cgje) {
		this.cgje = cgje;
	}

	@Column(name = "YJHDGG")
	public String getYjhdgg() {
		return yjhdgg;
	}

	public void setYjhdgg(String yjhdgg) {
		this.yjhdgg = yjhdgg;
	}

	@Column(name = "MS")
	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	@Column(name = "SFBWWZ")
	public String getSfbwwz() {
		return sfbwwz;
	}

	public void setSfbwwz(String sfbwwz) {
		this.sfbwwz = sfbwwz;
	}

	@Column(name = "IS_OLD")
	public boolean isOld() {
		return isOld;
	}

	public void setOld(boolean isOld) {
		this.isOld = isOld;
	}

	@Column(name = "BGMXID")
	public Long getBgMxId() {
		return bgMxId;
	}

	public void setBgMxId(Long bgMxId) {
		this.bgMxId = bgMxId;
	}

	@Column(name = "BG_REASON")
	public String getBgReason() {
		return bgReason;
	}

	public void setBgReason(String bgReason) {
		this.bgReason = bgReason;
	}

	@Column(name = "BG_DESC")
	public String getBgDesc() {
		return bgDesc;
	}

	public void setBgDesc(String bgDesc) {
		this.bgDesc = bgDesc;
	}

}