package com.cbmie.lh.sale.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.sale.dao.SaleSettlementOutRelDao;
import com.cbmie.lh.sale.entity.SaleSettlementOutRel;
@Service
public class SaleSettlementOutRelService extends BaseService<SaleSettlementOutRel, Long> {

	@Autowired
	private SaleSettlementOutRelDao relDao;
	@Override
	public HibernateDao<SaleSettlementOutRel, Long> getEntityDao() {
		return relDao;
	}
	
	public List<Map<String,Object>> getOutStackList(Long settlementId){
		return relDao.getOutStackList(settlementId);
	}
	
	public List<Map<String,Object>> getRemainOutStackList(String saleContractNo,Long settlementId){
		return relDao.getRemainOutStackList(saleContractNo,settlementId);
	}

}
