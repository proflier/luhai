package com.cbmie.activiti.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.activiti.entity.ProcessConfig;
import com.cbmie.common.persistence.HibernateDao;
@Repository
public class ProcessConfigDao extends HibernateDao<ProcessConfig, Long> {

}
