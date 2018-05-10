package com.cbmie.activiti.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.activiti.entity.ProcessEntity;
import com.cbmie.common.persistence.HibernateDao;
@Repository
public class ProcessEntityDao extends HibernateDao<ProcessEntity, Long> {

}
