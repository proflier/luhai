package com.cbmie.woodNZ.reportAccount.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.woodNZ.reportAccount.dao.PayableAccountDao;

 

@Service
@Transactional
public class PayableAccountService {
	
	@Autowired 
	private PayableAccountDao payableAccountDao;
	
	public Map<String, Object> search(Map<String, Object> map) {
		return payableAccountDao.getDataQuery(map);
	}

}
