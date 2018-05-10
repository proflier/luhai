package com.cbmie.activiti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.activiti.dao.ProcessPassDao;
import com.cbmie.activiti.entity.ProcessPass;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
@Service
public class ProcessPassService extends BaseService<ProcessPass, Long> {
	@Autowired
	private ProcessPassDao passDao;
	@Override
	public HibernateDao<ProcessPass, Long> getEntityDao() {
		return passDao;
	}
	
	public void save(ProcessPass pass){
		super.save(pass);
	}
	
	public void readProPass(String detailId){
		passDao.readProPass(detailId);
	}
	
	public List<String> getPassUsersByTaskId(String taskId){
		return passDao.getPassUsersByTaskId(taskId);
	}

}
