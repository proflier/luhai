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
import com.cbmie.lh.logistic.dao.OrderShipContractDao;
import com.cbmie.lh.logistic.entity.OrderShipContract;
import com.cbmie.lh.logistic.entity.OrderShipContractSub;
import com.cbmie.system.utils.DictUtils;
@Service
public class OrderShipContractService extends BaseService<OrderShipContract, Long> {
	@Autowired
	private OrderShipContractDao contractDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Autowired
	private BaseInfoUtilService bus;
	
	@Override
	public HibernateDao<OrderShipContract, Long> getEntityDao() {
		return contractDao;
	}

	public boolean checkCodeUique(OrderShipContract contract){
		return contractDao.checkCodeUique(contract);
	}
	
	public boolean checkInnerCodeUique(OrderShipContract contract){
		return contractDao.checkInnerCodeUique(contract);
	}
	
	public OrderShipContract getOrderShipContractByNo(String contractNo){
		return contractDao.getOrderShipContractByNo(contractNo);
	}
	
	public OrderShipContract getOrderShipContractByInnerNo(String innerNo){
		return contractDao.getOrderShipContractByInnerNo(innerNo);
	}
	
	public Map<String, Object> exportPDF(String contractNo) {
		OrderShipContract osc = getOrderShipContractByInnerNo(contractNo);
		//主要内容
		StringBuffer mainContentSB = new StringBuffer();
		//业务经办人
		String businessManager = "";
		//合同审查
		List<Map<String, Object>> traceList = new ArrayList<Map<String, Object>>();
		if (osc != null) {
			List<OrderShipContractSub> oscsList = osc.getShipSubs();
			for (int i = 0; i < oscsList.size(); i ++) {
				OrderShipContractSub oscs = oscsList.get(i);
//				mainContentSB.append("船编号：" + oscs.getShipNo() + "<br/>");
				mainContentSB.append("船名：" + DictUtils.getShipName(oscs.getShipNo()) + "<br/>");
				mainContentSB.append("运费单价：" + oscs.getTradePriceRate() + "<br/>");
				if((i + 1) < oscsList.size()) {
					mainContentSB.append("----------------------------------------------<br/>");
				}
			}
			if(oscsList.size() != 0) {
				mainContentSB.append("===========================<br/>");
			}
			mainContentSB.append("货量：" + osc.getContractQuantity() + "<br/>");
			mainContentSB.append("重大非常规披露：" + osc.getTipContent());
			businessManager = osc.getBusinessManager();
			if (osc.getProcessInstanceId() != null) {
				traceList = activitiService.getTraceInfo(osc.getProcessInstanceId());
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("osc", osc);
		//内部合同号
		data.put("innerContractNo", contractNo);
		//合同编号
		data.put("contractNo", osc.getContractNo());
		//申请部门
		data.put("createrDept", osc.getCreaterDept());
		//申请人
		data.put("createrName", osc.getCreaterName());
		//业务经办人
		data.put("businessManager", DictUtils.getUserNameByLoginName(businessManager));
		//申请日期
		data.put("createDate", osc.getCreateDate());
		//合同类型
		data.put("contractCategory", "国际采购".equals(DictUtils.getDictSingleName(osc.getOrderShipType())) ? "国际租船合同" : "国内租船合同");
		//合同金额
		data.put("amount", Double.parseDouble(osc.getMoney()));
		//币种
		data.put("currency", DictUtils.getDictSingleName(osc.getMoneyCurrencyCode()));
		//合同期限
		data.put("contracTermt", osc.getStartDate());
		data.put("contracTermtEnd", osc.getEndDate());
		//单位全称
		data.put("signAffi", DictUtils.getCorpName(osc.getTraderName()));
		//联系方式
		data.put("phone", osc.getTraderContact());
		//地址
		WoodAffiBaseInfo affi = bus.getAffiBaseInfoObjByCode(osc.getTraderName());
		data.put("address", affi.getAddress());
		//主要内容
		data.put("mainContent", mainContentSB.toString());
		//合同审查
		data.put("traceList", traceList);
		
		Map<String, Object> export = new HashMap<String, Object>();
		export.put("title", "订船合同" + StringUtils.replaceEach(osc.getContractNo(), new String[]{" ", ","}, new String[]{"-", ""}));
		export.put("data", data);
		return export;
	}

	public List<OrderShipContract> getOrderShipContractBak(Long id) {
		OrderShipContract orderShipContract = contractDao.get(id);
		return contractDao.getOrderShipContractBak(orderShipContract.getSourceId(),orderShipContract.getId());
	}

	public String getShipNameById(Long id) {
		return contractDao.getShipNameById(id);
	}
}
