package com.cbmie.woodNZ.salesContract.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cbmie.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 销售主表
 * 
 * @author liminghao
 */
@Entity
@Table(name = "wood_sale_contract")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class WoodSaleContract extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/** 卖受人* */
	private String seller;
	/** 买受人* */
	private String purchaser;
	/** 卖受人编码* */
	private String sellerNo;
	/** 买受人编码* */
	private String purchaserNo;
	/** 签约日期* */
	private Date signDate;
	/** 签约地点* */
	private String signAddr;
	/** 合同编号* */
	private String contractNo;
	/** 协议号* */
	private String dealNo;
	/** 业务部门* */
	private String dept;
	/** 合同类型* */
	private String contractType;
	/** 合同制作人* */
	private String draftsman;
	/** 合同执行人* */
	private String executer;
	/** 贸易性质* */
	private String tradeProperty;
	/** 收款止期* */
	private Date gatheringDate;
	
	private Double bail;//保证金
	
	private Double totalMoney;//合同总额
	
	private String connecter;//买方联系人
	
	private String phone;//买方联系人电话
	
	private String addr; //送货地址
	/** 币别* */
	private String currency;
	/** 汇率* */
	private Double exchangeRate;
	/** 提货方式* */
	private String takeWay;
	/** 提货地点* */
	private String takeAddr;
	/** 提货起期 * */
	//private Date takeStartDate;
	/** 提货止期* */
	private Date takeDate;
	/** 付款方式* */
	private String payWay;
	/** 溢短装* */
	private Double error;
	/** 合同失效期* */
	private Date disableDate;
	/** 登记日期* */
	private Date registerDate;
	/** 备注* */
	private String remark;
	/** 购销合同 销售合同**/
	private String salesType;
	/** 数量单位* */
	private String numUnit;
	
	private Double  dlf;//代理费
	
	private String cqjj;//提货超期单价上调条款

	private Double  total;//销售总立方数
	
	private Double  totalJs;//销售总件数
	
	private Double  totalPs;//销售总片数
	
	private Double unitPrice;//销售单价
	
	private String status;//状态：草稿，生效，作废
	
	private String dapimming;//大品名
	
	private String ifZa;//是否专案
	
	private String jsStatus;//是否结算
	
	private String state_fh = "否";//放货完成状态
	
	private String state_ck ="否";//出库完成状态
	/** 是否选中，用于页面多选 **/
	private Boolean isselect= Boolean.FALSE;
	/** 地区 **/
	private String area;
	/** 客户名称* */
	private String clientName;
	
	private Long clientId;
	
	/**
	 * 状态
	 */
	private String state = "草稿";
	
	private String state_ht = "运行中";//销售合同状态：运行中，关闭
	
	private String state_cx = "0";//采销状态：0未选择，1已选择
	
	public String getState_cx() {
		return state_cx;
	}

	public void setState_cx(String state_cx) {
		this.state_cx = state_cx;
	}

	@Column
	public String getState_ht() {
		return state_ht;
	}

	public void setState_ht(String state_ht) {
		this.state_ht = state_ht;
	}

	@Column
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private String returnJson;
	
	@Transient 
	public String getReturnJson() {
		return returnJson;
	}
	public void setReturnJson(String returnJson) {
		this.returnJson = returnJson;
	}
	
	/**
	 * 销售合同子表信息
	 */
	@JsonIgnore
	private List<WoodSaleContractSub> saleContractSubList = new ArrayList<WoodSaleContractSub>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentId")
	public List<WoodSaleContractSub> getSaleContractSubList() {
		return saleContractSubList;
	}

	public void setSaleContractSubList(List<WoodSaleContractSub> saleContractSubList) {
		this.saleContractSubList = saleContractSubList;
	}

	@Column
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}
	@Column
	public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}
	@Column
	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}
	@Column
	public String getPurchaserNo() {
		return purchaserNo;
	}

	public void setPurchaserNo(String purchaserNo) {
		this.purchaserNo = purchaserNo;
	}
	@Column
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	@Column
	public String getSignAddr() {
		return signAddr;
	}

	public void setSignAddr(String signAddr) {
		this.signAddr = signAddr;
	}
	@Column
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	@Column
	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}
	@Column
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	@Column
	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	@Column
	public String getDraftsman() {
		return draftsman;
	}

	public void setDraftsman(String draftsman) {
		this.draftsman = draftsman;
	}
	@Column
	public String getExecuter() {
		return executer;
	}

	public void setExecuter(String executer) {
		this.executer = executer;
	}
	@Column
	public String getTradeProperty() {
		return tradeProperty;
	}

	public void setTradeProperty(String tradeProperty) {
		this.tradeProperty = tradeProperty;
	}
	@Column
	public Date getGatheringDate() {
		return gatheringDate;
	}

	public void setGatheringDate(Date gatheringDate) {
		this.gatheringDate = gatheringDate;
	}
	@Column
	public Double getBail() {
		return bail;
	}

	public void setBail(Double bail) {
		this.bail = bail;
	}
	@Column
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	@Column
	public String getConnecter() {
		return connecter;
	}

	public void setConnecter(String connecter) {
		this.connecter = connecter;
	}
	@Column
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Column
	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	@Column
	public String getTakeWay() {
		return takeWay;
	}

	public void setTakeWay(String takeWay) {
		this.takeWay = takeWay;
	}
	@Column
	public String getTakeAddr() {
		return takeAddr;
	}

	public void setTakeAddr(String takeAddr) {
		this.takeAddr = takeAddr;
	}
	@Column
	public Date getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}
	@Column
	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	@Column
	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}
	@Column
	public Date getDisableDate() {
		return disableDate;
	}

	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}
	@Column
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column
	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	@Column
	public Double getDlf() {
		return dlf;
	}

	public void setDlf(Double dlf) {
		this.dlf = dlf;
	}
	@Column
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	@Column
	public Double getTotalJs() {
		return totalJs;
	}

	public void setTotalJs(Double totalJs) {
		this.totalJs = totalJs;
	}
	@Column
	public Double getTotalPs() {
		return totalPs;
	}

	public void setTotalPs(Double totalPs) {
		this.totalPs = totalPs;
	}
	@Column
	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	@Column
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column
	public String getDapimming() {
		return dapimming;
	}

	public void setDapimming(String dapimming) {
		this.dapimming = dapimming;
	}
	@Column
	public String getIfZa() {
		return ifZa;
	}

	public void setIfZa(String ifZa) {
		this.ifZa = ifZa;
	}
	@Column
	public String getJsStatus() {
		return jsStatus;
	}

	public void setJsStatus(String jsStatus) {
		this.jsStatus = jsStatus;
	}
	@Column
	public String getState_fh() {
		return state_fh;
	}

	public void setState_fh(String state_fh) {
		this.state_fh = state_fh;
	}
	@Column
	public String getState_ck() {
		return state_ck;
	}

	public void setState_ck(String state_ck) {
		this.state_ck = state_ck;
	}
	@Column
	public Boolean getIsselect() {
		return isselect;
	}

	public void setIsselect(Boolean isselect) {
		this.isselect = isselect;
	}
	@Column
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	@Column
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	@Column
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	@Column
	public String getCqjj() {
		return cqjj;
	}

	public void setCqjj(String cqjj) {
		this.cqjj = cqjj;
	}
	public String getNumUnit() {
		return numUnit;
	}

	public void setNumUnit(String numUnit) {
		this.numUnit = numUnit;
	}
	
}
