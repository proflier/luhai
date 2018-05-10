package com.cbmie.lh.administration.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.Reflections;
import com.cbmie.lh.administration.dao.ContractManagementDao;
import com.cbmie.lh.administration.entity.ContractManagement;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.logistic.dao.OrderShipContractDao;
import com.cbmie.lh.logistic.dao.ShipTraceDao;
import com.cbmie.lh.logistic.entity.ExtraExpense;
import com.cbmie.lh.logistic.entity.InsuranceContract;
import com.cbmie.lh.logistic.entity.OperateExpense;
import com.cbmie.lh.logistic.entity.OrderShipContract;
import com.cbmie.lh.logistic.entity.OrderShipContractSub;
import com.cbmie.lh.logistic.entity.PortContract;
import com.cbmie.lh.logistic.service.InsuranceContractService;
import com.cbmie.lh.logistic.service.PortContractService;
import com.cbmie.lh.purchase.dao.PurchaseContractDao;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleContractGoods;
import com.cbmie.lh.sale.service.SaleContractService;
import com.cbmie.lh.utils.MyTagUtil;
import com.cbmie.system.entity.User;
import com.cbmie.system.utils.DictUtils;

/**
 * 合同管理
 * @author Administrator
 *
 */
@Service
public class ContractManagementService extends BaseService<ContractManagement, Long> {

	@Autowired
	private ContractManagementDao contractManagementDao;
	
	@Autowired
	private BaseInfoUtilService bus;
	
	@Autowired
	private PurchaseContractDao purchaseContractDao;
	
	@Autowired
	private OrderShipContractDao orderShipContractDao;
	
	@Autowired
	private InsuranceContractService insuranceContractService;
	
	@Autowired
	private PortContractService portContractService;
	
	@Autowired
	private SaleContractService saleContractService;
	
	@Autowired
	private ShipTraceDao shipTraceDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Override
	public HibernateDao<ContractManagement, Long> getEntityDao() {
		return contractManagementDao;
	}

	public List<Map<String,Object>> getContract(String contractCategory){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<Object[]> list = contractManagementDao.getContract( contractCategory);
		for(Object obj : list) {
			Object[] obj_array = (Object[])obj;
			Map<String, Object> row_ = new HashMap<String, Object>();
			//归档编号
			row_.put("innerContractNo", obj_array[0].toString());
			//合同编号
			row_.put("contractNo", obj_array[1].toString());
			//签约方
			row_.put("signAffi", obj_array[2].toString());
			//签约日期
			row_.put("singnDate", obj_array[3].toString());
			//数量
			row_.put("quantity", obj_array[4].toString());
			//单价
			row_.put("unitPrice", obj_array[5].toString());
			//金额
			row_.put("amount", obj_array[6].toString());
			//类型
			row_.put("category", obj_array[7].toString());
			result.add(row_);
		}
		return result;
	}
	
	public void exportPdf(Map<String, Object> data, ContractManagement cm) {
		List<String> fieldNames = Reflections.getFiledNames(cm);
		String contractCategory = cm.getContractCategory();
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		if (contractCategory.equals("10920001") || contractCategory.equals("10920002")) {//采购合同
			PurchaseContract pc = purchaseContractDao.findNoRuleByContractNo(cm.getContractNo());
			if (pc != null) {
				List<PurchaseContractGoods> pcgList = pc.getPurchaseContractGoodsList();
				for (PurchaseContractGoods pcg : pcgList) {
					mainContentSB.append("品名：" + pcg.getGoodsName() + "<br/>");
					mainContentSB.append("数量：" + pcg.getGoodsQuantity() + "<br/>");
					mainContentSB.append("采购单价：" + pcg.getPurchasePrice() + "<br/>");
				}
				mainContentSB.append("结算条款：" + pc.getSettlementTerms() + "<br/>");
				if(pc.getComment()!=null){
					mainContentSB.append("备注：" + pc.getComment());
				}else{
					mainContentSB.append("备注：");
				}
				businessManager = pc.getBusinessManager();
				if (cm.getProcessInstanceId() != null) {
					traceList = activitiService.getTraceInfo(pc.getProcessInstanceId());
				}
			}
		} else if (contractCategory.equals("10920003") || contractCategory.equals("10920004")) {//订船合同
			OrderShipContract osc = orderShipContractDao.getOrderShipContractByNo(cm.getContractNo());
			if (osc != null) {
				List<OrderShipContractSub> oscsList = osc.getShipSubs();
				for (OrderShipContractSub oscs : oscsList) {
					mainContentSB.append("船编号：" + oscs.getShipNo() + "<br/>");
					mainContentSB.append("船名：" + shipTraceDao.getShipByNo(oscs.getShipNo()).getShipName() + "<br/>");
					mainContentSB.append("运费单价：" + oscs.getTradePriceRate() + "<br/>");
				}
				mainContentSB.append("货量：" + osc.getContractQuantity() + "<br/>");
				mainContentSB.append("重大非常规披露：" + osc.getTipContent());
				businessManager = osc.getBusinessManager();
				if (osc.getProcessInstanceId() != null) {
					traceList = activitiService.getTraceInfo(osc.getProcessInstanceId());
				}
			}
		} else if (contractCategory.equals("10920006") || contractCategory.equals("10920007")) {//保险合同
			InsuranceContract ic = insuranceContractService.findByContractNo(cm.getContractNo());
			if (ic != null) {
				mainContentSB.append("船名：【" + ic.getShipNo() + "】" + shipTraceDao.getShipByNo(ic.getShipNo()).getShipName() + "<br/>");
				if(ic.getComment()!=null){
					mainContentSB.append("备注：" + ic.getComment());
				}else{
					mainContentSB.append("备注：");
				}
				if (ic.getProcessInstanceId() != null) {
					traceList = activitiService.getTraceInfo(ic.getProcessInstanceId());
				}
			}
		} else if (contractCategory.equals("10920010")) {//码头合同
			PortContract pc = portContractService.findByContractNo(cm.getContractNo());
			if (pc != null) {
				mainContentSB.append("免费堆期天数：" + pc.getFreeHeapDays() + "<br/>");
				mainContentSB.append("免堆数(万吨)：" + pc.getFreeHeapCounts() + "<br/>");
				mainContentSB.append("<table width='100%' class='listTabClass'>");
				mainContentSB.append("<tr>");
				mainContentSB.append("<td colspan='8' align='center' style='font-size: 16px; padding: 5px; font-weight: bold; letter-spacing: 4px;'>作业费</td>");
				mainContentSB.append("</tr>");
				mainContentSB.append("<tr>");
				mainContentSB.append("<th>序号</th>");
				mainContentSB.append("<th>港口</th>");
				mainContentSB.append("<th>贸易类别</th>");
				mainContentSB.append("<th>作业方式</th>");
				mainContentSB.append("<th>计价范围开始(万吨)</th>");
				mainContentSB.append("<th>计价范围结束</th>");
				mainContentSB.append("<th>单价(元/吨)</th>");
				mainContentSB.append("<th>备注</th>");
				mainContentSB.append("</tr>");
				List<OperateExpense> oeList = pc.getOperateExpenseSubs();
				for (int i = 0; i < oeList.size(); i++) {
					mainContentSB.append("<tr>");
					mainContentSB.append("<th>" + (i + 1) + "</th>");
					mainContentSB.append("<th>" + (DictUtils.getCorpName(oeList.get(i).getPortNo())) + "</th>");
					mainContentSB.append("<th>" + (DictUtils.getDictSingleName(oeList.get(i).getTradeCategory())) + "</th>");
					mainContentSB.append("<th>" + (DictUtils.getDictSingleName(oeList.get(i).getOperateType())) + "</th>");
					mainContentSB.append("<th>" + (oeList.get(i).getExpenseStart()) + "</th>");
					mainContentSB.append("<th>" + (oeList.get(i).getExpenseEnd()) + "</th>");
					mainContentSB.append("<th>" + (oeList.get(i).getUnitPrice()) + "</th>");
					mainContentSB.append("<th>" + (oeList.get(i).getSummary()) + "</th>");
					mainContentSB.append("</tr>");
				}
				mainContentSB.append("</table>");
				mainContentSB.append("<table width='100%' class='listTabClass'>");
				mainContentSB.append("<tr>");
				mainContentSB.append("<td colspan='6' align='center' style='font-size: 16px; padding: 5px; font-weight: bold; letter-spacing: 4px;'>杂费</td>");
				mainContentSB.append("</tr>");
				mainContentSB.append("<tr>");
				mainContentSB.append("<th>序号</th>");
				mainContentSB.append("<th>费用类别</th>");
				mainContentSB.append("<th>计量单位</th>");
				mainContentSB.append("<th>计价范围开始(天)</th>");
				mainContentSB.append("<th>计价范围结束</th>");
				mainContentSB.append("<th>单价</th>");
				mainContentSB.append("</tr>");
				List<ExtraExpense> eeList = pc.getExtraExpenseSubs();
				for (int i = 0; i < eeList.size(); i++) {
					mainContentSB.append("<tr>");
					mainContentSB.append("<th>" + (i + 1) + "</th>");
					mainContentSB.append("<th>" + (DictUtils.getDictSingleName(eeList.get(i).getExpenseType())) + "</th>");
					mainContentSB.append("<th>" + (DictUtils.getDictSingleName(eeList.get(i).getCountUnit())) + "</th>");
					mainContentSB.append("<th>" + (eeList.get(i).getExpenseStart()) + "</th>");
					mainContentSB.append("<th>" + (eeList.get(i).getExpenseEnd()) + "</th>");
					mainContentSB.append("<th>" + (eeList.get(i).getUnitPrice()) + "</th>");
					mainContentSB.append("</tr>");
				}
				mainContentSB.append("</table>");
				if(pc.getRemarks()!=null){
					mainContentSB.append("备注：" + pc.getRemarks());
				}else{
					mainContentSB.append("备注：");
				}
				if (pc.getProcessInstanceId() != null) {
					traceList = activitiService.getTraceInfo(pc.getProcessInstanceId());
				}
			}
		} else if (contractCategory.equals("10920005")) {//销售合同
			SaleContract sc = saleContractService.getSaleContractByNo(cm.getContractNo());
			if (sc != null) {
				mainContentSB.append("销售方式：" + DictUtils.getDictLabelByCode(sc.getSaleMode(), "SALESMODE", "") + "<br/>");
				mainContentSB.append("业务类型：" + DictUtils.getDictLabelByCode(sc.getManageMode(), "BUSINESSTYPE", "") + "<br/>");
				mainContentSB.append("货物明细：" + "<br/>");
				for (SaleContractGoods scg : sc.getSaleContractSubList()) {
					mainContentSB.append("&nbsp;&nbsp;品名：" + MyTagUtil.getGoodsInformationByCode(scg.getGoodsName()).getInfoName());
					mainContentSB.append("&nbsp;&nbsp;单价：" + scg.getPrice());
					mainContentSB.append("&nbsp;&nbsp;数量：" + scg.getGoodsQuantity());
					mainContentSB.append("&nbsp;&nbsp;船编号：" + scg.getShipNo());
					mainContentSB.append("<br/>");
				}
				mainContentSB.append("数量结算依据：" + sc.getNumSettlementBasis() + "<br/>");
				mainContentSB.append("质量结算依据：" + sc.getQualitySettlementBasis() + "<br/>");
				mainContentSB.append("风险提示：" + sc.getRiskTip());
				businessManager = sc.getBusinessManager();
				if (sc.getProcessInstanceId() != null) {
					traceList = activitiService.getTraceInfo(sc.getProcessInstanceId());
				}
			}
		}
		if (businessManager.length() > 0) {
			User user = MyTagUtil.getUserByLoginName(businessManager);
			if (user != null) {
				businessManager = user.getName();
			}
		}
		data.put("mainContent", mainContentSB.toString());
		data.put("businessManager", businessManager);
		data.put("traceList", traceList);
		
		for (String fieldName : fieldNames) {
			Object value = Reflections.getFieldValue(cm, fieldName);
			if (fieldName.equals("contractCategory")) {
				value = DictUtils.getDictLabelByCode(String.valueOf(value), "contractCategoryAll", "");
			} else if (fieldName.equals("signAffi")) {//签约方
				WoodAffiBaseInfo affi = bus.getAffiBaseInfoObjByCode(String.valueOf(value));
				value = affi.getCustomerName();
				data.put("phone", affi.getPhoneContact());
				data.put("address", affi.getAddress());
			}
			data.put(fieldName, value);
		}
	}
}
