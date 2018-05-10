package com.cbmie.lh.administration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.administration.dao.SealApprovalDao;
import com.cbmie.lh.administration.entity.SealApproval;

/**
 * 印章管理
 * @author wxz
 *
 */
@Service
public class SealApprovalService extends BaseService<SealApproval, Long> {

	@Autowired
	private SealApprovalDao sealApprovalDao;

	@Override
	public HibernateDao<SealApproval, Long> getEntityDao() {
		return sealApprovalDao;
	}
	
	
}
