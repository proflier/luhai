package com.cbmie.lh.financial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.financial.dao.InputInvoiceDao;
import com.cbmie.lh.financial.entity.InputInvoice;
import com.cbmie.lh.financial.entity.InputInvoiceSub;

@Service
@Transactional
public class InputInvoiceService extends BaseService<InputInvoice, Long> {

	@Autowired
	private InputInvoiceDao inputInvoiceDao;

	@Override
	public HibernateDao<InputInvoice, Long> getEntityDao() {
		return inputInvoiceDao;
	}

	public void saveRelLoginNames(InputInvoiceSub inputInvoiceSub) {
		InputInvoice inputInvoice = inputInvoiceDao.get(inputInvoiceSub.getInputInvoiceSubId());
		List<InputInvoiceSub> inputInvoiceSubList = inputInvoice.getSettleSubs();
		String relLoginNames ="";
		for(InputInvoiceSub ini : inputInvoiceSubList){
			if(relLoginNames!=""){
				relLoginNames = relLoginNames +","+ini.getRelLoginNames();
			}else{
				relLoginNames = ini.getRelLoginNames();
			}
		}
		inputInvoice.setRelLoginNames(relLoginNames);
		inputInvoiceDao.save(inputInvoice);
	}

}
