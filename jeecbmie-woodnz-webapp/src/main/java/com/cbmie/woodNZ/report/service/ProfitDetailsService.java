package com.cbmie.woodNZ.report.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.woodNZ.report.dao.ProfitDetailsDao;

@Service
@Transactional
public class ProfitDetailsService {

	@Autowired 
	private ProfitDetailsDao profitDetailsDao;
	
	public Map<String, Object> search(Map<String, Object> map) {
		return profitDetailsDao.getDataQuery(map);
	}
}
