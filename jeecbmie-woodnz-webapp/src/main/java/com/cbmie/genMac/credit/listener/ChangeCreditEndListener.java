package com.cbmie.genMac.credit.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.common.utils.AbolishReason;
import com.cbmie.genMac.credit.entity.ChangeCredit;
import com.cbmie.genMac.credit.entity.OpenCredit;
import com.cbmie.genMac.credit.entity.OpenCreditHistory;
import com.cbmie.genMac.credit.service.ChangeCreditService;
import com.cbmie.genMac.credit.service.OpenCreditHistoryService;
import com.cbmie.genMac.credit.service.OpenCreditService;

public class ChangeCreditEndListener implements ExecutionListener {
	
	private static final long serialVersionUID = -3749719423876966373L;

	@Autowired
	ChangeCreditService changeCreditService;
	
	@Autowired
	OpenCreditService openCreditService;
	
	@Autowired
	OpenCreditHistoryService openCreditHistoryService;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String deleteReason = null;
		if (execution instanceof ExecutionEntity) {
			ExecutionEntity executionEntity = (ExecutionEntity) execution;
			deleteReason = executionEntity.getDeleteReason();
		}
		ChangeCredit changeCredit = changeCreditService.get(Long.parseLong(execution.getProcessBusinessKey()));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			changeCredit.setState("作废");
		} else if (null != deleteReason) {
			
		} else {
			// 获取要改的开证
			OpenCredit openCredit = openCreditService.get(changeCredit.getChangeId());
			// 把要改还没改的开证信息存入历史表
			OpenCreditHistory openCreditHistory = new OpenCreditHistory();
			BeanUtils.copyProperties(openCreditHistory, openCredit);
			openCreditHistory.setChangeId(openCredit.getId());
			openCreditHistoryService.save(openCreditHistory);
			// 把改证信息置入开证保存
			openCredit.setContractNo(changeCredit.getContractNo());
			openCredit.setApplicant(changeCredit.getApplicant());
			openCredit.setBeneficiary(changeCredit.getBeneficiary());
			openCredit.setBank(changeCredit.getBank());
			openCredit.setCreditValidity(changeCredit.getCreditValidity());
			openCredit.setCreditType(changeCredit.getCreditType());
			openCredit.setExpectDate(changeCredit.getExpectDate());
			openCredit.setPrompt(changeCredit.getPrompt());
			openCredit.setCreditTotalMoney(changeCredit.getCreditTotalMoney());
			openCredit.setTotalChineseNo(changeCredit.getTotalChineseNo());
			openCredit.setCreditDate(changeCredit.getCreditDate());
			openCredit.setCreditNo(changeCredit.getCreditNo());
			openCredit.setCreditMoney(changeCredit.getCreditMoney());
			openCredit.setChineseNo(changeCredit.getChineseNo());
			openCreditService.update(openCredit);
			// 修改改证表的状态，并存入历史id
			changeCredit.setState("生效");
			changeCredit.setHistoryId(openCreditHistory.getId());
		}
		changeCreditService.save(changeCredit);
	}

}
