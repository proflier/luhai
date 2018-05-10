package com.cbmie.lh.logistic.service;

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
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.logistic.dao.PortContractDao;
import com.cbmie.lh.logistic.entity.ExtraExpense;
import com.cbmie.lh.logistic.entity.OperateExpense;
import com.cbmie.lh.logistic.entity.PortContract;
import com.cbmie.system.utils.DictUtils;

@Service
public class PortContractService extends BaseService<PortContract, Long> {

	@Autowired
	private PortContractDao contractDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private BaseInfoUtilService bus;
	
	@Override
	public HibernateDao<PortContract, Long> getEntityDao() {
		return contractDao;
	}

	public boolean checkCodeUique(PortContract contract){
		return contractDao.checkCodeUique(contract);
	}
	
	public PortContract findByContractNo(String contractNo) {
		return contractDao.findByContractNo(contractNo);
	}

	public List<PortContract> getPortContractBak(long id) {
		PortContract portContract = contractDao.get(id);
		return contractDao.getPortContractBak(portContract.getSourceId(),portContract.getId());
	}
	
	public Map<String, Object> exportPDF(String contractNo) {
		PortContract portContract = contractDao.findByContractNo(contractNo);
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		if (portContract != null) {
			mainContentSB.append("免费堆期天数：" + portContract.getFreeHeapDays() + "<br/>");
			mainContentSB.append("免堆数(万吨)：" + portContract.getFreeHeapCounts() + "<br/>");
			mainContentSB.append("<table width='100%' class='listTabClass'>");
			mainContentSB.append("<tr>");
			mainContentSB.append("<td colspan='7' align='center' style='font-size: 16px; padding: 5px; font-weight: bold; letter-spacing: 4px;'>作业费</td>");
			mainContentSB.append("</tr>");
			mainContentSB.append("<tr>");
			mainContentSB.append("<th>序号</th>");
			mainContentSB.append("<th>港口</th>");
			mainContentSB.append("<th>贸易类别</th>");
			mainContentSB.append("<th>作业方式</th>");
			mainContentSB.append("<th>计价范围开始(万吨)</th>");
			mainContentSB.append("<th>计价范围结束</th>");
			mainContentSB.append("<th>单价(元/吨)</th>");
//			mainContentSB.append("<th>备注</th>");
			mainContentSB.append("</tr>");
			List<OperateExpense> oeList = portContract.getOperateExpenseSubs();
			for (int i = 0; i < oeList.size(); i++) {
				mainContentSB.append("<tr>");
				mainContentSB.append("<th>" + (i + 1) + "</th>");
				mainContentSB.append("<th>" + (DictUtils.getCorpName(oeList.get(i).getPortNo())) + "</th>");
				mainContentSB.append("<th>" + (DictUtils.getDictSingleName(oeList.get(i).getTradeCategory())) + "</th>");
				mainContentSB.append("<th>" + (DictUtils.getDictSingleName(oeList.get(i).getOperateType())) + "</th>");
				mainContentSB.append("<th>" + (oeList.get(i).getExpenseStart()) + "</th>");
				mainContentSB.append("<th>" + (oeList.get(i).getExpenseEnd() == null ? "" : oeList.get(i).getExpenseEnd()) + "</th>");
				mainContentSB.append("<th>" + (oeList.get(i).getUnitPrice()) + "</th>");
//				mainContentSB.append("<th>" + (oeList.get(i).getSummary()) + "</th>");
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
			List<ExtraExpense> eeList = portContract.getExtraExpenseSubs();
			for (int i = 0; i < eeList.size(); i++) {
				mainContentSB.append("<tr>");
				mainContentSB.append("<th>" + (i + 1) + "</th>");
				mainContentSB.append("<th>" + (DictUtils.getDictSingleName(eeList.get(i).getExpenseType())) + "</th>");
				mainContentSB.append("<th>" + (DictUtils.getDictSingleName(eeList.get(i).getCountUnit())) + "</th>");
				mainContentSB.append("<th>" + (eeList.get(i).getExpenseStart() == null ? "" : eeList.get(i).getExpenseStart()) + "</th>");
				mainContentSB.append("<th>" + (eeList.get(i).getExpenseEnd() == null ? "" : eeList.get(i).getExpenseEnd()) + "</th>");
				mainContentSB.append("<th>" + (eeList.get(i).getUnitPrice()) + "</th>");
				mainContentSB.append("</tr>");
			}
			mainContentSB.append("</table>");
			mainContentSB.append("备注：" + portContract.getRemarks());
			if (portContract.getProcessInstanceId() != null) {
				traceList = activitiService.getTraceInfo(portContract.getProcessInstanceId());
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		//合同编号
		data.put("contractNo", portContract.getContractNo());
		//申请部门
		data.put("createrDept", portContract.getCreaterDept());
		//申请人
		data.put("createrName", portContract.getCreaterName());
		//业务经办人
		data.put("businessManager", DictUtils.getUserNameByLoginName(businessManager));
		//申请日期
		data.put("createDate", portContract.getCreateDate());
		//合同类型
		data.put("contractCategory", "码头合同");
		//合同金额
//		data.put("amount", portContract.getMoney());
		//币种
//		data.put("currency", DictUtils.getDictSingleName(portContract.getMoneyCurrencyCode()));
		//合同期限
		data.put("contracTermt", portContract.getStartDay());
		data.put("contracTermtEnd", portContract.getEndDay());
		//单位全称
		WoodAffiBaseInfo affi = bus.getAffiBaseInfoObjByCode(portContract.getSignAffiliate());
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
		export.put("title", StringUtils.replaceEach(portContract.getContractNo(), new String[]{" ", ","}, new String[]{"-", ""}));
		export.put("data", data);
		return export;
	}
}
