package com.cbmie.lh.financial.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.financial.dao.SerialDao;
import com.cbmie.lh.financial.entity.Serial;

/**
 * 流水service
 */
@Service
@Transactional
public class SerialService extends BaseService<Serial, Long> {

	@Autowired
	private SerialDao serialDao;

	@Override
	public HibernateDao<Serial, Long> getEntityDao() {
		return serialDao;
	}

	public Serial findByNo(Serial serial) {
		return serialDao.findByNo(serial);
	}

	public List<Serial> findByParentId(String id) {
		return serialDao.findBy("splitStatus", id);
	}

	public Double sumAcceptance(String contractNo) {
		return serialDao.sumAcceptance(contractNo);
	}

	public List<Serial> getSerialBySaleNo(String saleConctractNo){
		return serialDao.getSerialBySaleNo(saleConctractNo);
	}

	public void findEntityList(Page<Serial> page, Map<String, Object> params) {
		serialDao.findEntityList(page, params);
	}
}
