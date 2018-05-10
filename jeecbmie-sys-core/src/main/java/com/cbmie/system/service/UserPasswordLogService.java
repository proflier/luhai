package com.cbmie.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.system.dao.UserPasswordLogDao;
import com.cbmie.system.entity.UserPasswordLog;

/**
 * 账号密码log service
 */
@Service
@Transactional(readOnly = true)
public class UserPasswordLogService extends BaseService<UserPasswordLog, Long> {

	@Autowired
	private UserPasswordLogDao logDao;

	@Override
	public HibernateDao<UserPasswordLog, Long> getEntityDao() {
		return logDao;
	}

	public List<UserPasswordLog> getByUsername(String username) {
		return logDao.findBy("loginName", username);
	}

	public void updateLogin(UserPasswordLog log) {
		log.setLoginCount(log.getLoginCount()+1);
		log.setLastVisit(new Date());
		logDao.save(log);
		logDao.getSession().flush();
	}
	
}
