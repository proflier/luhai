package com.cbmie.woodNZ.cgcontract.entity;

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
 * 采购合同-进口
 * 
 * @author Linxiaopeng
 *
 */
@Entity
@Table(name = "WoodCghtJkBak")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodCghtJkBak extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** 合同号 **/
	private String hth;
	/**
	 * 综合合同号
	 */
	private String interContractNo;

	@Column
	public String getInterContractNo() {
		return interContractNo;
	}

	public void setInterContractNo(String interContractNo) {
		this.interContractNo = interContractNo;
	}
	/** 协议号 **/
	private String xyh;
	/** 供货单位 **/
	private String ghdw;
	/** 供货单位编码 **/
	private String ghdwNo;
	/** 币种 **/
	private String cgbz;
	/** 应预付额 **/
	private String yyfe;
	/** 订货总量 **/
	private String dhzl;
	/** 数量单位 **/
	private String sldw;
	/** 定金 **/
	private String dj;
	/** 货款 **/
	private String hk;
	/** 生产单位 **/
	private String scdw;
	/** 汇率 **/
	private String hl;
	/** 溢短 **/
	private String yd;
	/** 订货日期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date dhrq;
	/** 交货起期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date jhqq;
	/** 交货止期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date jhzq;
	/** 收款方式 **/
	private String skfs;
	/** 授信类型 **/
	private String sxlx;
	/** 价格条款 **/
	private String jgtk;
	/** 货到地区 **/
	private String hddq;
	/** 订货单位 **/
	private String dhdw;
	/** 订货单位编号 **/
	private String dhdwNo;
	/** 收方单位 **/
	private String sfdw;
	/** 收方单位编号 **/
	private String sfdwNo;
	/** 业务员 **/
	private String ywy;
	/** 登记日期 **/
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	private Date djrq;
	/** 部门 **/
	private String bm;
	/** 合同类别 **/
	private String htlb;
	/**
	 * 状态
	 */
	private String state = "草稿";
	/** 备注 **/
	private String bz;

	private String zt;

	private String isjs;// 是否结算

	/** 选择状态,不作为数据库表字段 **/
	private String select;

	/** 与销售合同建立多对多的关系 **/

	/** 已到单总额 **/
	private String yddze;

	/** 进口国别 **/
	private String jkgb;

	/** 地区 **/
	private String area;
	
	/**
	 * 合同关闭打开
	 */
	private String closeOrOpen = "运行中";
	
	@Column(name = "CLOSE_OR_OPEN")
	public String getCloseOrOpen() {
		return closeOrOpen;
	}

	public void setCloseOrOpen(String closeOrOpen) {
		this.closeOrOpen = closeOrOpen;
	}

	/**
	 * 采购合同子表信息
	 */
	@JsonIgnore
	private List<WoodCghtJkMxBak> woodCghtJkMxBakList = new ArrayList<WoodCghtJkMxBak>();
	
	/**
	 * 关联id 变更前id
	 */
	private String changeRelativeId ;
	
	@Column(name = "CHANGE_RELATIVE_ID")
	public String getChangeRelativeId() {
		return changeRelativeId;
	}

	public void setChangeRelativeId(String changeRelativeId) {
		this.changeRelativeId = changeRelativeId;
	}

	private String changeReason;
	
	@Column(name = "CHANGE_REASON")
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	@Column(name = "HTH")
	public String getHth() {
		return hth;
	}

	public void setHth(String hth) {
		this.hth = hth;
	}

	@Column(name = "XYH")
	public String getXyh() {
		return xyh;
	}

	public void setXyh(String xyh) {
		this.xyh = xyh;
	}

	@Column(name = "GHDW")
	public String getGhdw() {
		return ghdw;
	}

	public void setGhdw(String ghdw) {
		this.ghdw = ghdw;
	}

	@Column(name = "GHDW_NO")
	public String getGhdwNo() {
		return ghdwNo;
	}

	public void setGhdwNo(String ghdwNo) {
		this.ghdwNo = ghdwNo;
	}

	@Column(name = "CGBZ")
	public String getCgbz() {
		return cgbz;
	}

	public void setCgbz(String cgbz) {
		this.cgbz = cgbz;
	}

	@Column(name = "YYFE")
	public String getYyfe() {
		return yyfe;
	}

	public void setYyfe(String yyfe) {
		this.yyfe = yyfe;
	}

	@Column(name = "DHZL")
	public String getDhzl() {
		return dhzl;
	}

	public void setDhzl(String dhzl) {
		this.dhzl = dhzl;
	}

	@Column(name = "SLDW")
	public String getSldw() {
		return sldw;
	}

	public void setSldw(String sldw) {
		this.sldw = sldw;
	}

	@Column(name = "DJ")
	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	@Column(name = "HK")
	public String getHk() {
		return hk;
	}

	public void setHk(String hk) {
		this.hk = hk;
	}

	@Column(name = "SCDW")
	public String getScdw() {
		return scdw;
	}

	public void setScdw(String scdw) {
		this.scdw = scdw;
	}

	@Column(name = "HL")
	public String getHl() {
		return hl;
	}

	public void setHl(String hl) {
		this.hl = hl;
	}

	@Column(name = "YD")
	public String getYd() {
		return yd;
	}

	public void setYd(String yd) {
		this.yd = yd;
	}

	@Column(name = "DHRQ")
	public Date getDhrq() {
		return dhrq;
	}

	public void setDhrq(Date dhrq) {
		this.dhrq = dhrq;
	}

	@Column(name = "JHQQ")
	public Date getJhqq() {
		return jhqq;
	}

	public void setJhqq(Date jhqq) {
		this.jhqq = jhqq;
	}

	@Column(name = "JHZQ")
	public Date getJhzq() {
		return jhzq;
	}

	public void setJhzq(Date jhzq) {
		this.jhzq = jhzq;
	}

	@Column(name = "SKFS")
	public String getSkfs() {
		return skfs;
	}

	public void setSkfs(String skfs) {
		this.skfs = skfs;
	}

	@Column(name = "SXLX")
	public String getSxlx() {
		return sxlx;
	}

	public void setSxlx(String sxlx) {
		this.sxlx = sxlx;
	}

	@Column(name = "JGTK")
	public String getJgtk() {
		return jgtk;
	}

	public void setJgtk(String jgtk) {
		this.jgtk = jgtk;
	}

	@Column(name = "HDDQ")
	public String getHddq() {
		return hddq;
	}

	public void setHddq(String hddq) {
		this.hddq = hddq;
	}

	@Column(name = "DHDW")
	public String getDhdw() {
		return dhdw;
	}

	public void setDhdw(String dhdw) {
		this.dhdw = dhdw;
	}

	@Column(name = "DHDW_NO")
	public String getDhdwNo() {
		return dhdwNo;
	}

	public void setDhdwNo(String dhdwNo) {
		this.dhdwNo = dhdwNo;
	}

	@Column(name = "SFDW")
	public String getSfdw() {
		return sfdw;
	}

	public void setSfdw(String sfdw) {
		this.sfdw = sfdw;
	}

	@Column(name = "SFDW_NO")
	public String getSfdwNo() {
		return sfdwNo;
	}

	public void setSfdwNo(String sfdwNo) {
		this.sfdwNo = sfdwNo;
	}

	@Column(name = "YWY")
	public String getYwy() {
		return ywy;
	}

	public void setYwy(String ywy) {
		this.ywy = ywy;
	}

	@Column(name = "DJRQ")
	public Date getDjrq() {
		return djrq;
	}

	public void setDjrq(Date djrq) {
		this.djrq = djrq;
	}

	@Column(name = "BM")
	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	@Column(name = "HTLB")
	public String getHtlb() {
		return htlb;
	}

	public void setHtlb(String htlb) {
		this.htlb = htlb;
	}

	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "BZ")
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name = "ZT")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	@Column(name = "IS_JS")
	public String getIsjs() {
		return isjs;
	}

	public void setIsjs(String isjs) {
		this.isjs = isjs;
	}

	@Column(name = "IS_SELECT")
	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	@Column(name = "YDDZE")
	public String getYddze() {
		return yddze;
	}

	public void setYddze(String yddze) {
		this.yddze = yddze;
	}

	@Column(name = "JKGB")
	public String getJkgb() {
		return jkgb;
	}

	public void setJkgb(String jkgb) {
		this.jkgb = jkgb;
	}

	@Column(name = "AREA")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<WoodCghtJkMxBak> getWoodCghtJkMxBakList() {
		return woodCghtJkMxBakList;
	}

	public void setWoodCghtJkMxBakList(List<WoodCghtJkMxBak> woodCghtJkMxBakList) {
		this.woodCghtJkMxBakList = woodCghtJkMxBakList;
	}

}