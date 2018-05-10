package com.cbmie.lh.logistic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.baseinfo.entity.WoodAffiBaseInfo;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.common.utils.StringUtils;
import com.cbmie.lh.baseInfo.service.BaseInfoUtilService;
import com.cbmie.lh.logistic.dao.InsuranceContractDao;
import com.cbmie.lh.logistic.entity.InsuranceContract;
import com.cbmie.system.utils.DictUtils;


/**
 * 保险合同service
 */
@Service
@Transactional
public class InsuranceContractService extends BaseService<InsuranceContract, Long> {

	@Autowired 
	private InsuranceContractDao insuranceContractDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private BaseInfoUtilService bus;

	@Override
	public HibernateDao<InsuranceContract, Long> getEntityDao() {
		return insuranceContractDao;
	} 

	public boolean checkInnerContractNoUnique(InsuranceContract insuranceContract) {
		return insuranceContractDao.checkInnerContractNoUnique(insuranceContract);
	}
	
	public InsuranceContract findByContractNo(String contractNo) {
		return insuranceContractDao.findByContractNo(contractNo);
	}

	public List<InsuranceContract> getPortContractBak(Long id) {
		InsuranceContract insuranceContract = insuranceContractDao.get(id);
		return insuranceContractDao.getPortContractBak(insuranceContract.getSourceId(),insuranceContract.getId());
	}
	
	public Map<String, Object> exportPDF(String contractNo) {
		InsuranceContract insuranceContract = insuranceContractDao.getByInnerNo(contractNo);
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		if (insuranceContract != null) {
			mainContentSB.append("船名：" + DictUtils.getShipName(insuranceContract.getShipNo()) + "<br/>");
			mainContentSB.append("备注：" + insuranceContract.getComment());
			if (insuranceContract.getProcessInstanceId() != null) {
				traceList = activitiService.getTraceInfo(insuranceContract.getProcessInstanceId());
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		//合同编号
		data.put("contractNo", insuranceContract.getContractNo());
		//申请部门
		data.put("createrDept", insuranceContract.getCreaterDept());
		//申请人
		data.put("createrName", insuranceContract.getCreaterName());
		//业务经办人
		data.put("businessManager", DictUtils.getUserNameByLoginName(businessManager));
		//申请日期
		data.put("createDate", insuranceContract.getCreateDate());
		//合同类型
		data.put("contractCategory", "保险合同");
		//合同金额
		data.put("amount", insuranceContract.getAmount());
		//币种
		data.put("currency", "人民币");
		//合同期限
//		data.put("contracTermt", insuranceContract.getDeliveryStartDate());
//		data.put("contracTermtEnd", insuranceContract.getDeliveryEndDate());
		//单位全称
		WoodAffiBaseInfo affi = bus.getAffiBaseInfoObjByCode(insuranceContract.getInsuranceCompany());
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
		export.put("title", StringUtils.replaceEach(insuranceContract.getContractNo(), new String[]{" ", ","}, new String[]{"-", ""}));
		export.put("data", data);
		return export;
	}
}
  