package com.cbmie.lh.logistic.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.financial.entity.PaymentConfirm;
import com.cbmie.lh.logistic.entity.ShipTrace;
import com.cbmie.lh.purchase.entity.PurchaseContract;
import com.cbmie.lh.sale.entity.SaleContract;
import com.cbmie.lh.sale.entity.SaleSettlement;
@Repository
public class ShipTraceDao extends HibernateDao<ShipTrace, Long> {

	public boolean checkShipNoUnique(ShipTrace shipTrace){
		Criteria shipTraceC = this.getSession().createCriteria(ShipTrace.class);
		if (shipTrace.getId() != null) {
			shipTraceC.add(Restrictions.ne("id", shipTrace.getId()));
		}
		shipTraceC.add(Restrictions.eq("shipNo", shipTrace.getShipNo()));
		ShipTrace shipTrace_t =  (ShipTrace) shipTraceC.uniqueResult();
		if(shipTrace_t!=null){
			return false;
		}else{
			return true;
		}
	}
	
	public List<ShipTrace> getOuterShipTrace(){
		String sql = "select * from LH_SHIP_TRACE a where a.trade_Category='10850002' order by id desc "; 
		return this.createSQLQuery(sql).addEntity(ShipTrace.class).list();
	}
	
	public List<ShipTrace> getAllShipTrace(){
		String sql = "select * from LH_SHIP_TRACE a order by id desc "; 
		return this.createSQLQuery(sql).addEntity(ShipTrace.class).list();
	}
	
	public boolean checkIfUsed(String shipNo){
		String sql = "select * from LH_ORDERSHIP_CONTRACTSUB a where a.ship_No=:shipNo";
		String sql1 = "select * from LH_BILLS a where a.ship_No=:shipNo";
		List list1 = this.createSQLQuery(sql).setParameter("shipNo", shipNo).list();
		List list2 = this.createSQLQuery(sql1).setParameter("shipNo", shipNo).list();
		if((list1!=null && list1.size()>0)||(list2!=null && list2.size()>0)){
			return true;
		}else{
			return false;
		}
	}
	
	public ShipTrace getShipByNo(String shipNo){
		String hql = "from ShipTrace a where a.shipNo=:shipNo";
		List<ShipTrace> list = this.createQuery(hql).setParameter("shipNo", shipNo).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<SaleSettlement> getSaleSettlements(String shipNo) {
		String sql = " select e.* from lh_sale_settlement e "
					+" join lh_sale_contract a on e.sale_contract_no = a.contract_no "
					+" join lh_sale_delivery b on a.contract_no = b.sale_contract_no "
					+" join lh_sale_delivery_goods c on b.id = c.sale_delivery_id "
					+" join lh_ship_trace d on d.ship_no = c.voy "
					+" where d.ship_no=:shipNo  group by e.id ";
		List<SaleSettlement> list = this.createSQLQuery(sql).addEntity(SaleSettlement.class).setParameter("shipNo", shipNo).list();
		return list;
	}

	public List<SaleContract> getSaleContracts(String shipNo) {
		String sql = " select a.* from lh_sale_contract a "+
				" join lh_sale_delivery b on a.contract_no = b.sale_contract_no "+
				" join lh_sale_delivery_goods c on b.id = c.sale_delivery_id "+
				" join lh_ship_trace d on d.ship_no = c.voy "+
				" where d.ship_no=:shipNo and a.change_state!=0 group by a.id ";
		List<SaleContract> list = this.createSQLQuery(sql).addEntity(SaleContract.class).setParameter("shipNo", shipNo).list();
		return list;
	}

	public List<PurchaseContract> getPurchaseContracts(String shipNo) {
		String sql = " select a.* from lh_purchase_contract a "+
				" join lh_ship_trace b on a.inner_contract_no=b.inner_contract_no "+
				" where b.ship_no=:shipNo and a.change_state!=0 group by a.id ";
		List<PurchaseContract> list = this.createSQLQuery(sql).addEntity(PurchaseContract.class).setParameter("shipNo", shipNo).list();
		return list;
	}

	public List<PaymentConfirm> getPaymentConfirms(String shipNo) {
		String sql = " select a.* from lh_payment_confirm a "+
					" join lh_payment_confirm_child b on a.id = b.payment_confirm_id "+
					" where b.code =:shipNo group by a.id ";
		List<PaymentConfirm> list = this.createSQLQuery(sql).addEntity(PaymentConfirm.class).setParameter("shipNo", shipNo).list();
		return list;
	}
}
