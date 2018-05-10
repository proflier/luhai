package com.cbmie.lh.logistic.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.logistic.dao.ShipTraceDao;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleSettlement;
@Service
public class ShipTraceService extends BaseService<ShipTrace, Long> {
	@Autowired
	private ShipTraceDao shipTraceDao;
	@Override
	public HibernateDao<ShipTrace, Long> getEntityDao() {
		return shipTraceDao;
	}

	public boolean checkShipNoUnique(ShipTrace shipTrace){
		return shipTraceDao.checkShipNoUnique(shipTrace);
	}
	
	public List<ShipTrace> getOuterShipTrace(){
		return shipTraceDao.getOuterShipTrace();
	}
	
	public List<ShipTrace> getAllShipTrace(){
		return shipTraceDao.getAllShipTrace();
	}
	public boolean checkIfUsed(String shipNo){
		if(StringUtils.isBlank(shipNo)){
			return false;
		}else{
			return shipTraceDao.checkIfUsed(shipNo);
		}
		
	}
	
	public ShipTrace getShipByNo(String shipNo){
		return shipTraceDao.getShipByNo(shipNo);
	}

	public List<SaleContract> getSaleContracts(String shipNo) {
		return shipTraceDao.getSaleContracts(shipNo);
	}

	public List<PurchaseContract> getPurchaseContracts(String shipNo) {
		return shipTraceDao.getPurchaseContracts(shipNo);
	}

	public List<PaymentConfirm> getPaymentConfirms(String shipNo) {
		return shipTraceDao.getPaymentConfirms(shipNo);
	}

	public List<SaleSettlement> getSaleSettlements(String shipNo) {
		return shipTraceDao.getSaleSettlements(shipNo);
	}
}
