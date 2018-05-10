package com.cbmie.lh.sale.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.lh.credit.entity.PayApply;
import com.cbmie.lh.financial.entity.InputInvoice;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.logistic.entity.LhBills;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleInvoice;
import com.cbmie.lh.sale.entity.SaleSettlement;
@Repository
public class SaleContractDao extends HibernateDao<SaleContract, Long> {

	public boolean checkContractNoUnique(SaleContract SaleContract) {
		Criteria criteria = getSession().createCriteria(SaleContract.class);
		if (SaleContract.getId() != null) {
			criteria.add(Restrictions.ne("id", SaleContract.getId()));
		}
		criteria.add(Restrictions.eq("contractNo", SaleContract.getContractNo()));
		List list = criteria.list();
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	public SaleContract getSaleContractByNo(String contractNo) {
		String hql = "from SaleContract a where a.contractNo = :contractNo";
		List<SaleContract> list = this.createQuery(hql).setParameter("contractNo", contractNo).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<SaleContract> getAllEffectSaleContract() {
		String sql = "SELECT * FROM LH_SALE_CONTRACT WHERE  closed_flag=0 and state=1  ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SaleContract.class);
		return sqlQuery.list();
	}

	public SaleContract findEffectByNo(String contractNo) {
		String sql = "SELECT * FROM LH_SALE_CONTRACT WHERE change_state=1 and contract_no='"+contractNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(SaleContract.class);
		return (SaleContract) sqlQuery.list().get(0);
	}
	
	public void getEffectSaleContractSubs(Page<SaleContract> page,Map<String,Object> param){
		String hql = "select a from SaleContract a left join fetch a.saleContractSubList where a.closedFlag='0' and a.state=1 "
				+ " order by a.id desc";
		Query query = this.createQuery(hql);
		query.setFirstResult((page.getPageNo()-1)*page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setResult(query.list());
	}
	
	public List<SaleContract> getSaleContractHistory(Long sourceId,Long curId){
		String sql = "select * from LH_SALE_CONTRACT a where a.change_State!='2'"
				+ " and a.source_Id=:sourceId and a.id<>:curId order by id asc";
		List<SaleContract> list = this.createSQLQuery(sql).addEntity(SaleContract.class)
				.setParameter("sourceId", sourceId).setParameter("curId", curId).list();
		return list;
	}

	public String getContractNoCustomer(String contractNo) {
		String sql = "select CONCAT(contract_no,(select wood_affi_base_info.customer_name from wood_affi_base_info where wood_affi_base_info.customer_code =purchaser)) from lh_sale_contract where change_state=1 and state=1 and contract_no='"+contractNo+"'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return (String) sqlQuery.uniqueResult();
	}

	public List<String> findHavePermission(String loginName) {
		String sql = "SELECT id FROM LH_SALE_CONTRACT WHERE  REL_LOGIN_NAMES like '%"+loginName+"%'";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		List<String > returnVlaue = new ArrayList<String >();
		if(sqlQuery.list().size()>0){
			for(Object o : sqlQuery.list()){
				returnVlaue.add(o.toString());
			}
		}
		return returnVlaue;
	}

	public List<Map<String, Object>> getSaleDelarys(String contractNo) {
		String sql = " select a.delivery_release_no, w.customer_name,CONCAT(t.ship_no,t.ship_name),g.info_name,b.quantity,a.createrdate "
					+" from lh_sale_delivery a "
					+" join lh_sale_delivery_goods b on a.id = b.sale_delivery_id "
					+" join wood_affi_base_info w on b.port = w.customer_code "
					+" join lh_ship_trace t on b.voy = t.ship_no "
					+" join lh_goodsinformation g on b.goods_name= g.info_code "
					+" where sale_contract_no=:contractNo ";
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		sqlQuery.setParameter("contractNo", contractNo);
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			
			Map<String, Object> row_ = new HashMap<String, Object>();
			//id
			row_.put("deliveryReleaseNo", obj_array[0].toString());
			row_.put("customerName", obj_array[1].toString());
			row_.put("shipNo", obj_array[2].toString());
			row_.put("goodsName", obj_array[3].toString());
			row_.put("quantity", obj_array[4].toString());
			row_.put("createrdate", obj_array[5].toString());
			data.add(row_);
		}
		return data;
	}

	public List<LhBills> getBills(String contractNo) {
		String sql = " select g.* from lh_sale_contract a "+
						" join lh_sale_delivery b on a.contract_no = b.sale_contract_no "+
						" join lh_sale_delivery_goods c on b.id = c.sale_delivery_id "+
						" join lh_ship_trace d on d.ship_no = c.voy "+
						" join lh_purchase_contract e on e.inner_contract_no = d.inner_contract_no "+
						" join lh_bills_goods f on f.inner_contract_no = e.inner_contract_no "+
						" join lh_bills g on f.parent_id = g.id "+
						" where a.contract_no=:contractNo GROUP BY g.id ";
		List<LhBills> list = this.createSQLQuery(sql).addEntity(LhBills.class).setParameter("contractNo", contractNo).list();
		return list;
	}

	public List<ShipTrace> getShipTracts(String contractNo) {
		String sql = " select d.* from lh_sale_contract a "+
						" join lh_sale_delivery b on a.contract_no = b.sale_contract_no "+
						" join lh_sale_delivery_goods c on b.id = c.sale_delivery_id "+
						" join lh_ship_trace d on d.ship_no = c.voy "+
						" where a.contract_no=:contractNo ";
		List<ShipTrace> list = this.createSQLQuery(sql).addEntity(ShipTrace.class).setParameter("contractNo", contractNo).list();
		return list;
	}

	public List<PurchaseContract> getPurchaseContracts(String contractNo) {
		String sql = " select e.* from lh_sale_contract a "
						+" join lh_sale_delivery b on a.contract_no = b.sale_contract_no "
						+" join lh_sale_delivery_goods c on b.id = c.sale_delivery_id "
						+" join lh_ship_trace d on d.ship_no = c.voy "
						+" join lh_purchase_contract e on e.inner_contract_no = d.inner_contract_no "
						+" where a.contract_no=:contractNo  ";
		List<PurchaseContract> list = this.createSQLQuery(sql).addEntity(PurchaseContract.class).setParameter("contractNo", contractNo).list();
		return list;
	}

	public List<SaleSettlement> getSaleSettlements(String contractNo) {
		String sql = " select * from lh_sale_settlement where sale_contract_no=:contractNo  ";
		List<SaleSettlement> list = this.createSQLQuery(sql).addEntity(SaleSettlement.class).setParameter("contractNo", contractNo).list();
		return list;
	}

	public List<PayApply> getPayApplys(String contractNo) {
		String sql = " select f.* from lh_sale_contract a "
					+" join lh_sale_delivery b on a.contract_no = b.sale_contract_no "
					+" join lh_sale_delivery_goods c on b.id = c.sale_delivery_id "
					+" join lh_ship_trace d on d.ship_no = c.voy "
					+" join lh_purchase_contract e on e.inner_contract_no = d.inner_contract_no " 
					+" join lh_pay_apply f on f.inte_contract_no = e.inner_contract_no "
					+" where a.contract_no=:contractNo  ";
		List<PayApply> list = this.createSQLQuery(sql).addEntity(PayApply.class).setParameter("contractNo", contractNo).list();
		return list;
	}

	public List<SaleInvoice> getSaleInvoices(String contractNo) {
		String sql = " select * from lh_sale_invoice where sale_contract_no=:contractNo   ";
		List<SaleInvoice> list = this.createSQLQuery(sql).addEntity(SaleInvoice.class).setParameter("contractNo", contractNo).list();
		return list;
	}

	public List<PaymentConfirm> getPaymentConfirms(String contractNo) {
		String sql = " select f.* from lh_sale_contract a "
						+" join lh_sale_delivery b on a.contract_no = b.sale_contract_no "
						+" join lh_sale_delivery_goods c on b.id = c.sale_delivery_id "
						+" join lh_ship_trace d on d.ship_no = c.voy "
						+" join lh_payment_confirm_child e on e.code = d.ship_no "
						+" join lh_payment_confirm f on f.id = e.payment_confirm_id "
						+" where a.contract_no=:contractNo  GROUP BY f.id ";
		List<PaymentConfirm> list = this.createSQLQuery(sql).addEntity(PaymentConfirm.class).setParameter("contractNo", contractNo).list();
		return list;
	}

	public List<InputInvoice> getInputInvoices(String contractNo) {
		String sql = " select b.* from lh_input_invoicesub a join lh_input_invoice b on a.input_invoice_sub_id=b.id "
					+" WHERE a.related_sales_order=:contractNo   ";
		List<InputInvoice> list = this.createSQLQuery(sql).addEntity(InputInvoice.class).setParameter("contractNo", contractNo).list();
		return list;
	}

	public String getShipNameById(Long id) {
		String sql ="select GROUP_CONCAT(CONCAT(b.ship_no,IFNULL(c.ship_name,''))) from lh_sale_contract a "+
					"join lh_sale_contract_goods b on a.id = b.salecontract_id "+
					"left join lh_ship_trace c on b.ship_no= c.ship_no "+
					"where a.id="+id ;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		return (String) sqlQuery.uniqueResult();
	}
	
	
}
