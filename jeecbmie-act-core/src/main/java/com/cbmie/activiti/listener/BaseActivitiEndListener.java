package com.cbmie.activiti.listener;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.activiti.service.ActivitiService;
import com.cbmie.common.entity.BaseEntity;
import com.cbmie.common.utils.AbolishReason;
import com.cbmie.common.utils.Reflections;
public class BaseActivitiEndListener implements ExecutionListener {

	private static final long serialVersionUID = 8104452527298193591L;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ActivitiService activitiService;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String processInstanceId = execution.getProcessInstanceId();
		Map<String,String> key_map = activitiService.getKeys(processInstanceId);
		String businessKey = key_map.get("businessKey");
		String processKey = key_map.get("processKey");
		BaseEntity baseEntity = activitiService.getReflectionObj(processKey, Long.parseLong(businessKey));
		if(null != execution.getVariable(Enum.valueOf(AbolishReason.class, "INITABOLISH").getValue())){
			Reflections.invokeSetter(baseEntity, "state", "0");
		} else { 
			Reflections.invokeSetter(baseEntity, "state", "1");
		}
		sessionFactory.getCurrentSession().saveOrUpdate(baseEntity);
	}

}
