package com.cbmie.woodNZ.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.credit.dao.PayApplyDao;
import com.cbmie.woodNZ.credit.entity.PayApply;

/**
 * 付款申请service
 */
@Service
@Transactional
public class PayApplyService extends BaseService<PayApply, Long> {
	
	@Autowired
	private PayApplyDao payApplyDao;

	@Override
	public HibernateDao<PayApply, Long> getEntityDao() {
		return payApplyDao;
	}

	/**
	 * @param payApply
	 * @return
	 */
	public PayApply findByNo(PayApply payApply) {
		return payApplyDao.findByNo(payApply);
	}
	
	
}
