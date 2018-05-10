package com.cbmie.lh.relation.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.feedback.entity.FeedbackTheme;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleDelivery;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.stock.entity.InStock;
import com.cbmie.lh.stock.entity.OutStock;
import com.cbmie.system.entity.User;

/**
 * 关联-DAO
 */
@Repository
public class RelationDao extends HibernateDao<PurchaseContract, Long> {

	
	public List<LhBills> findByInnerContractNo(String innerContractNo) {
		String sql = "select * from lh_bills bill join lh_bills_goods billgoods on billgoods.parent_id = bill.id where billgoods.inner_contract_no in("+innerContractNo+") GROUP BY bill.id";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(LhBills.class);
		return sqlQuery.list();
	}
	
	public List<PaymentConfirm> findPayByInner(String innerContractNo) {
		String sql = "select * from lh_payment_confirm join lh_payment_confirm_child on lh_payment_confirm_child.payment_confirm_id = lh_payment_confirm.id where lh_payment_confirm_child.code  in("+innerContractNo+") GROUP BY lh_payment_confirm.id ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PaymentConfirm.class);
		return sqlQuery.list();
	}

	
	public List<User> findUserByOrg(Integer orgId) {
		String sql = "select * from user u where u.org_id='"+orgId+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(User.class);
		return sqlQuery.list();
	}

	public List<PayApply> findCreditByInner(String innerContractNo) {
		String sql = "select * from lh_pay_apply where inte_contract_no  in("+innerContractNo+") ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PayApply.class);
		return sqlQuery.list();
	}

	public List<SaleContract> findSaleByInner(String innerContractNo) {
		String sql = " select * from lh_sale_contract where lh_sale_contract.change_state=1 and lh_sale_contract.state=1 and contract_no='"+innerContractNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SaleContract.class);
		return sqlQuery.list();
	}

	public List<PurchaseContract> findPurchaseByInner(String innerContractNo) {
		String sql = "select * from lh_purchase_contract where change_state=1 and state=1 and inner_contract_no  in("+innerContractNo+") ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseContract.class);
		return sqlQuery.list();
	}

	public List<InStock> findInStockByInner(String innerContractNo) {
		String sql = "select * from lh_in_stock join lh_in_stock_goods on lh_in_stock.id = lh_in_stock_goods.parent_id where lh_in_stock_goods.inner_contract_no  in("+innerContractNo+") GROUP BY lh_in_stock.id ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(InStock.class);
		return sqlQuery.list();
	}

	public List<SaleDelivery> findDeliveryByInner(String innerContractNo) {
		String sql = "select * from lh_sale_delivery join lh_sale_delivery_goods on lh_sale_delivery.id=lh_sale_delivery_goods.sale_delivery_id where lh_sale_delivery_goods.contract_no  in("+innerContractNo+") GROUP BY lh_sale_delivery.id ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SaleDelivery.class);
		return sqlQuery.list();
	}

	public List<OutStock> findOutStockByInner(String innerContractNo) {
		String sql = "select * from lh_out_stock join lh_sale_delivery_goods on lh_out_stock.id = lh_sale_delivery_goods.out_stock_id where lh_sale_delivery_goods.contract_no  in("+innerContractNo+") GROUP BY lh_out_stock.id ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(OutStock.class);
		return sqlQuery.list();
	}

	public List<ShipTrace> findShipTrackByInner(String innerContractNo) {
		String sql = "select * from lh_ship_trace where inner_contract_no like '%"+innerContractNo+"%'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(ShipTrace.class);
		return sqlQuery.list();
	}

	public List<SaleInvoice> findSaleInvoiceByInner(String innerContractNo) {
		String sql = "select * from lh_sale_invoice JOIN lh_sale_invoice_sub on lh_sale_invoice.id = lh_sale_invoice_sub.sale_invoice_id where lh_sale_invoice_sub.sale_contract_no  in("+innerContractNo+") GROUP BY lh_sale_invoice.id ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SaleInvoice.class);
		return sqlQuery.list();
	}

	public List<FeedbackTheme> findFeeBackByInner(String innerContractNo) {
		String sql = "select a.* from lh_feedback_theme a join lh_feedback_file b on a.id=b.theme_id join lh_purchase_contract c on b.file_target=c.purchase_contract_no "+
					 "where c.inner_contract_no="+innerContractNo;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(FeedbackTheme.class);
		return sqlQuery.list();
	}
}
