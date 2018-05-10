package com.cbmie.woodNZ.reportAccount.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.cbmie.woodNZ.reportAccount.dao.PrepaidAccountDao;

 

@Service
@Transactional
public class PrepaidAccountService {
	
	@Autowired 
	private PrepaidAccountDao prepaidAccountDao;
	
	public Map<String, Object> search(Map<String, Object> map) {
		return prepaidAccountDao.getDataQuery(map);
	}

}
