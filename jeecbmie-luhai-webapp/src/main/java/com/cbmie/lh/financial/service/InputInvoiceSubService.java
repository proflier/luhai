package com.cbmie.lh.financial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.financial.dao.InputInvoiceSubDao;
import com.cbmie.lh.financial.entity.InputInvoiceSub;
@Service
public class InputInvoiceSubService extends BaseService<InputInvoiceSub, Long> {

	@Autowired
	private InputInvoiceSubDao intDao;
	@Override
	public HibernateDao<InputInvoiceSub, Long> getEntityDao() {
		return intDao;
	}

	@Transactional(readOnly=true)
	public List<InputInvoiceSub> getInputInvoiceSubBySaleNo(String saleConctractNo){
		return	intDao.getInputInvoiceSubBySaleNo(saleConctractNo);
	}
}
