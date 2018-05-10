package com.cbmie.lh.logistic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.logistic.dao.WharfSettlementDao;
import com.cbmie.lh.logistic.entity.WharfSettlement;
import com.cbmie.lh.logistic.entity.WharfSettlementSub;
import com.cbmie.system.utils.DictUtils;
@Service
public class WharfSettlementService extends BaseService<WharfSettlement, Long> {

	@Autowired
	private WharfSettlementDao wharfDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Override
	public HibernateDao<WharfSettlement, Long> getEntityDao() {
		return wharfDao;
	}
	public Map<String, Object> exportPDF(Long id) {
		WharfSettlement wharfSettlement = wharfDao.find(id);
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		if (wharfSettlement != null) {
			mainContentSB.append("<table width='100%' class='listTabClass'>");
			mainContentSB.append("<tr>");
			mainContentSB.append("<td colspan='7' align='center' style='font-size: 16px; padding: 5px; font-weight: bold; letter-spacing: 4px;'>本次开票信息</td>");
			mainContentSB.append("</tr>");
			mainContentSB.append("<tr>");
			mainContentSB.append("<th>序号</th>");
			mainContentSB.append("<th>船编号</th>");
			mainContentSB.append("<th>费用类别</th>");
			mainContentSB.append("<th>计费吨数</th>");
			mainContentSB.append("<th>结算单价(元/吨)</th>");
			mainContentSB.append("<th>金额(元)</th>");
			mainContentSB.append("<th>摘要</th>");
			mainContentSB.append("<th>备注</th>");
			mainContentSB.append("</tr>");
			List<WharfSettlementSub> subList = wharfSettlement.getSubList();
			for (int i = 0; i < subList.size(); i++) {
				mainContentSB.append("<tr>");
				mainContentSB.append("<th>" + (i + 1) + "</th>");
				mainContentSB.append("<th>" + (DictUtils.getShipName(subList.get(i).getShipNo())) + "</th>");
				mainContentSB.append("<th>" + (DictUtils.getDictSingleName(subList.get(i).getPortExpenseType())) + "</th>");
				mainContentSB.append("<th>" + (subList.get(i).getSettleQuantity()) + "</th>");
				mainContentSB.append("<th>" + (subList.get(i).getUnitPrice()) + "</th>");
				mainContentSB.append("<th>" + (subList.get(i).getTotalPrice()) + "</th>");
				mainContentSB.append("<th>" + (subList.get(i).getRoundup()) + "</th>");
				mainContentSB.append("<th>" + (subList.get(i).getRemarks()) + "</th>");
				mainContentSB.append("</tr>");
			}
			if (wharfSettlement.getProcessInstanceId() != null) {
				traceList = activitiService.getTraceInfo(wharfSettlement.getProcessInstanceId());
			}
			mainContentSB.append("</table>");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		//合同类型
		data.put("wharf", wharfSettlement.getWharf());
		data.put("prePay", wharfSettlement.getPrePay());
		data.put("gaugeQuantity", wharfSettlement.getGaugeQuantity());
		data.put("stockDate", wharfSettlement.getStockDate());
		data.put("settleDate", wharfSettlement.getSettleDate());
		data.put("settleMonth", wharfSettlement.getSettleMonth());
		data.put("shouldPay", wharfSettlement.getShouldPay());
		data.put("shouldBill", wharfSettlement.getShouldBill());
		data.put("alreadyBill", wharfSettlement.getAlreadyBill());
		data.put("createrDept", wharfSettlement.getCreaterDept());
		data.put("createrName", wharfSettlement.getCreaterName());
		data.put("businessManager", DictUtils.getUserNameByLoginName(businessManager));
		data.put("createDate", wharfSettlement.getCreateDate());
		data.put("contractCategory", "码头结算");
		data.put("mainContent", mainContentSB.toString());
		data.put("traceList", traceList);
		
		Map<String, Object> export = new HashMap<String, Object>();
		export.put("title", StringUtils.replaceEach(wharfSettlement.getWharf(), new String[]{" ", ","}, new String[]{"-", ""}));
		export.put("data", data);
		return export;
	}

}
