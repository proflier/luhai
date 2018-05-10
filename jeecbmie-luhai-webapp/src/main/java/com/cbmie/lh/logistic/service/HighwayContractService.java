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
import com.cbmie.lh.logistic.dao.HighwayContractDao;
import com.cbmie.lh.logistic.entity.HighwayContract;
import com.cbmie.lh.logistic.entity.RailwayContract;
import com.cbmie.system.utils.DictUtils;

@Service
public class HighwayContractService extends BaseService<HighwayContract, Long> {

	@Autowired
	private HighwayContractDao contractDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private BaseInfoUtilService bus;
	
	@Override
	public HibernateDao<HighwayContract, Long> getEntityDao() {
		return contractDao;
	}

	public boolean checkCodeUique(HighwayContract contract){
		return contractDao.checkCodeUique(contract);
	}
	
	public Map<String, Object> exportPDF(String contractNo) {
		HighwayContract highwayContract = contractDao.getByInnerNo(contractNo);
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		if (highwayContract != null) {
			mainContentSB.append("贸易类型：" + DictUtils.getDictSingleName(highwayContract.getTradeCategory()) + "<br/>");
			mainContentSB.append("主要内容：" + highwayContract.getContent() + "<br/>");
			mainContentSB.append("重大非常规披露：" + highwayContract.getTipContent() + "<br/>");
			if (highwayContract.getProcessInstanceId() != null) {
				traceList = activitiService.getTraceInfo(highwayContract.getProcessInstanceId());
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		//合同编号
		data.put("contractNo", highwayContract.getContractNo());
		//申请部门
		data.put("createrDept", highwayContract.getCreaterDept());
		//申请人
		data.put("createrName", highwayContract.getCreaterName());
		//业务经办人
		data.put("businessManager", DictUtils.getUserNameByLoginName(businessManager));
		//申请日期
		data.put("createDate", highwayContract.getCreateDate());
		//合同类型
		data.put("contractCategory", "汽运合同");
		//合同金额
		data.put("amount", highwayContract.getMoney());
		//币种
		data.put("currency", DictUtils.getDictSingleName(highwayContract.getMoneyCurrencyCode()));
		//合同期限
		data.put("contracTermt", highwayContract.getStartDate());
		data.put("contracTermtEnd", highwayContract.getEndDate());
		//单位全称
		WoodAffiBaseInfo affi = bus.getAffiBaseInfoObjByCode(highwayContract.getTraderName());
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
		export.put("title", StringUtils.replaceEach(highwayContract.getContractNo(), new String[]{" ", ","}, new String[]{"-", ""}));
		export.put("data", data);
		return export;
	}

	public List<HighwayContract> getHighwayContractBak(long id) {
		HighwayContract highwayContract = contractDao.get(id);
		return contractDao.getHighwayContractBak(highwayContract.getSourceId(),highwayContract.getId());
	}
}
