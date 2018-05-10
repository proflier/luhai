package com.cbmie.lh.sale.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.financial.entity.InputInvoice;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.sale.dao.SaleContractDao;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleSettlement;
import com.cbmie.system.utils.DictUtils;
@Service
public class SaleContractService extends BaseService<SaleContract, Long> {

	@Autowired
	private SaleContractDao dao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private BaseInfoUtilService bus;
	
	@Override
	public HibernateDao<SaleContract, Long> getEntityDao() {
		return dao;
	}

	public boolean checkContractNoUnique(SaleContract SaleContract) {
		return dao.checkContractNoUnique(SaleContract);
	}
	
	public SaleContract getSaleContractByNo(String contractNo){
		return dao.getSaleContractByNo(contractNo);
	}

	public List<SaleContract> getAllEffectSaleContract() {
		return dao.getAllEffectSaleContract();
	}
	
	public List<SaleContract> getSaleShip() {
		return dao.findBy("manageMode", "10820001");
	}
	
	public void countSaleContract(SaleContract contract){
		List<SaleContractGoods> goods = contract.getSaleContractSubList();
		double amount=0.0;
		double quantity = 0.0;
		for(SaleContractGoods good : goods){
			double am = StringUtils.isBlank(good.getAmount())?0.0:Double.parseDouble(good.getAmount());
			amount+=am;
			quantity+=good.getGoodsQuantity();
		}
		contract.setContractAmount(amount);
		contract.setContractQuantity(quantity);
		this.update(contract);
	}
	public void getEffectSaleContractSubs(Page<SaleContract> page,Map<String,Object> param){
		dao.getEffectSaleContractSubs(page, param);
	}
	
	public List<SaleContract> getSaleContractHistory(Long sourceId,Long curId){
		return dao.getSaleContractHistory(sourceId,curId);
	}

	public String getContractNoCustomer(String contractNo) {
		return dao.getContractNoCustomer(contractNo);
	}
	
	public Map<String, Object> exportPDF(Long id) {
		SaleContract saleContract = get(id);
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		if (saleContract != null) {
			mainContentSB.append("销售方式：" + DictUtils.getDictSingleName(saleContract.getSaleMode()) + "<br/>");
			mainContentSB.append("业务类型：" + DictUtils.getDictSingleName(saleContract.getManageMode()) + "<br/>");
			mainContentSB.append("货物明细：" + "<br/>");
			for (SaleContractGoods scg : saleContract.getSaleContractSubList()) {
				mainContentSB.append("&nbsp;&nbsp;品名：" + DictUtils.getGoodsInfoName(scg.getGoodsName()));
				mainContentSB.append("&nbsp;&nbsp;单价：" + scg.getPrice());
				mainContentSB.append("&nbsp;&nbsp;数量：" + scg.getGoodsQuantity());
				mainContentSB.append("&nbsp;&nbsp;船名：" + DictUtils.getShipName(scg.getShipNo()));
				mainContentSB.append("<br/>");
			}
			mainContentSB.append("数量结算依据：" + saleContract.getNumSettlementBasis() + "<br/>");
			mainContentSB.append("质量结算依据：" + saleContract.getQualitySettlementBasis() + "<br/>");
			mainContentSB.append("风险提示：" + saleContract.getRiskTip());
			businessManager = saleContract.getBusinessManager();
			if (saleContract.getProcessInstanceId() != null) {
				traceList = activitiService.getTraceInfo(saleContract.getProcessInstanceId());
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		//合同编号
		data.put("contractNo", saleContract.getContractNo());
		//申请部门
		data.put("createrDept", saleContract.getCreaterDept());
		//申请人
		data.put("createrName", saleContract.getCreaterName());
		//业务经办人
		data.put("businessManager", DictUtils.getUserNameByLoginName(businessManager));
		//申请日期
		data.put("createDate", saleContract.getCreateDate());
		//合同类型
		data.put("contractCategory", "销售合同");
		//合同金额
		data.put("amount", saleContract.getContractAmount());
		//币种
		data.put("currency", "人民币");
		//合同期限
		data.put("contracTermt", saleContract.getDeliveryStartDate());
		data.put("contracTermtEnd", saleContract.getDeliveryEndDate());
		//单位全称
		WoodAffiBaseInfo affi = bus.getAffiBaseInfoObjByCode(saleContract.getPurchaser());
		data.put("signAffi", affi.getCustomerName());
		//联系方式
		data.put("phone", affi.getPhoneContact());
		//地址
		data.put("address", affi.getAddress());
		//主要内容
		data.put("mainContent", mainContentSB.toString());
		//合同审查
		data.put("traceList", traceList);
		
		Map<String, Object> export = new HashMap<String, Object>();
		export.put("title", StringUtils.replaceEach(saleContract.getContractNo(), new String[]{" ", ","}, new String[]{"-", ""}));
		export.put("data", data);
		return export;
	}

	public List<LhBills> getBills(String contractNo) {
		return dao.getBills(contractNo);
	}
	
	public List<ShipTrace> getShipTracts(String contractNo) {
		return dao.getShipTracts(contractNo);
	}
	
	public List<Map<String, Object>> getSaleDelarys(String contractNo) {
		return dao.getSaleDelarys(contractNo);
	}

	public List<PurchaseContract> getPurchaseContracts(String contractNo) {
		return dao.getPurchaseContracts(contractNo);
	}

	public List<SaleSettlement> getSaleSettlements(String contractNo) {
		return dao.getSaleSettlements(contractNo);
	}

	public List<PayApply> getPayApplys(String contractNo) {
		return dao.getPayApplys(contractNo);
	}

	public List<SaleInvoice> getSaleInvoices(String contractNo) {
		return dao.getSaleInvoices(contractNo);
	}

	public List<PaymentConfirm> getPaymentConfirms(String contractNo) {
		return dao.getPaymentConfirms(contractNo);
	}

	public List<InputInvoice> getInputInvoices(String contractNo) {
		return dao.getInputInvoices(contractNo);
	}

	public String getShipNameById(Long id) {
		return dao.getShipNameById(id);
	}
	
}
