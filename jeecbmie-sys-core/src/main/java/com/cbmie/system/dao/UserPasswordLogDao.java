package com.cbmie.system.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.system.entity.UserPasswordLog;


/**
 * 账号密码log
 */
@Repository
public class UserPasswordLogDao extends HibernateDao<UserPasswordLog, Long>{
	
}
