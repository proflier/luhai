package com.cbmie.activiti.service;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.activiti.dao.ProcessConfigDao;
import com.cbmie.activiti.entity.ProcessConfig;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
@Service
public class ProcessConfigService extends BaseService<ProcessConfig, Long> {
	
	@Autowired
	private ProcessConfigDao entityDao;
	@Override
	public HibernateDao<ProcessConfig, Long> getEntityDao() {
		return entityDao;
	}
	
	public ProcessConfig findEffectConfict(String entityView) {
		Criteria criteria = entityDao.getSession().createCriteria(ProcessConfig.class);
		criteria.add(Restrictions.eq("entityView", entityView));
		criteria.add(Restrictions.eq("state", "1"));
		return (ProcessConfig) criteria.uniqueResult();
	}

}
