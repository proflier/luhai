package com.cbmie.lh.relation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.feedback.entity.FeedbackTheme;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.purchase.dao.PurchaseContractDao;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.relation.dao.RelationDao;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.entity.OutStock;
import com.cbmie.system.entity.User;

/**
 * 关联-service
 */
@Service
@Transactional
public class RelationService extends BaseService<PurchaseContract, Long> {

	@Autowired
	private PurchaseContractDao purchaseContractDao;

	@Autowired
	private RelationDao relationDao;

	@Override
	public HibernateDao<PurchaseContract, Long> getEntityDao() {
		return purchaseContractDao;
	}

	public List<LhBills> findByInnerContractNo(String innerContractNo) {
		return relationDao.findByInnerContractNo(transToSqlString(innerContractNo));
	}

	public List<User> findUserByOrg(Integer orgId) {
		return relationDao.findUserByOrg(orgId);
	}

	public List<PaymentConfirm> findPayByInner(String innerContractNo) {
		return relationDao.findPayByInner(transToSqlString(innerContractNo));
	}

	public List<PayApply> findCreditByInner(String innerContractNo) {
		return relationDao.findCreditByInner(transToSqlString(innerContractNo));
	}

	public List<SaleContract> findSaleByInner(String innerContractNo) {
		return relationDao.findSaleByInner(transToSqlString(innerContractNo));
	}

	public List<PurchaseContract> findPurchaseByInner(String innerContractNo) {
		return relationDao.findPurchaseByInner(transToSqlString(innerContractNo));
	}

	public List<InStock> findInStockByInner(String innerContractNo) {
		return relationDao.findInStockByInner(transToSqlString(innerContractNo));
	}

	public List<SaleDelivery> findDeliveryByInner(String innerContractNo) {
		return relationDao.findDeliveryByInner(transToSqlString(innerContractNo));
	}

	public List<OutStock> findOutStockByInner(String innerContractNo) {
		return relationDao.findOutStockByInner(transToSqlString(innerContractNo));
	}

	public List<ShipTrace> findShipTrackByInner(String innerContractNo) {
		return relationDao.findShipTrackByInner(innerContractNo);	
	}

	public List<SaleInvoice> findSaleInvoiceByInner(String innerContractNo) {
		return relationDao.findSaleInvoiceByInner(transToSqlString(innerContractNo));
	}
	
	private String transToSqlString(String orgin){
		String dest = "'"+orgin.replace(",", "','")+"'";
		return dest;
	}

	public List<FeedbackTheme> findFeeBackByInner(String innerContractNos) {
		return relationDao.findFeeBackByInner(transToSqlString(innerContractNos));
	}
	

}
