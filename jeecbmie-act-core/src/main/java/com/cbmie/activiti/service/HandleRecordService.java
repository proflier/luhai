package com.cbmie.activiti.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.activiti.dao.HandleRecordDao;
import com.cbmie.activiti.entity.HandleRecord;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.utils.UserUtil;

import eu.bitwalker.useragentutils.UserAgent;

@Service
public class HandleRecordService extends BaseService<HandleRecord, Long> {

	@Autowired
	private HandleRecordDao entityDao;
	
	@Override
	public HibernateDao<HandleRecord, Long> getEntityDao() {
		return entityDao;
	}
	
	public void save(TaskEntity task, String os) {
		HandleRecord hr = new HandleRecord();
		hr.setProcessKey(task.getProcessDefinitionId().split(":")[0]);
		hr.setTaskName(task.getName());
		hr.setTransactor(UserUtil.getCurrentUser().getLoginName());
		hr.setCreateDate(new Date());
		hr.setOs(os);
		entityDao.save(hr);
	}
	
}
