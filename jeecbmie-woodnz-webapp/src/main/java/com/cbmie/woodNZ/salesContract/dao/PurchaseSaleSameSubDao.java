package com.cbmie.woodNZ.salesContract.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSameSub;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;

 
/**
 * 采销同批子表DAO 
 */ 
@Repository
public class PurchaseSaleSameSubDao extends HibernateDao<PurchaseSaleSameSub , Long>{

	/**
	 * @param string
	 * @return  PurchaseSaleSameSub
	 */
	@SuppressWarnings("unchecked")
	public List<WoodCghtJk> searchBySaleId(String id) {
		List<WoodCghtJk> woodCghtJkList = new ArrayList<WoodCghtJk>();
		String sql = "SELECT * FROM wood_purchase_sale_same_sub WHERE  wood_sale_contract_id="+Long.valueOf(id);
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseSaleSameSub.class);
		List<PurchaseSaleSameSub> purchaseSaleSameSubList = sqlQuery.list();
		if(purchaseSaleSameSubList.size()>0){
			for(PurchaseSaleSameSub purchaseSaleSameSub :purchaseSaleSameSubList){
				woodCghtJkList.add(purchaseSaleSameSub.getWoodCghtJk());
			}
		}
		return woodCghtJkList;
	}

	public List<PurchaseSaleSameSub> findBySaleId(String saleId,String mainId) {
		String sql="";
		if(mainId != null && !mainId.equals(""))
			sql = "SELECT * FROM wood_purchase_sale_same_sub WHERE  wood_sale_contract_id="+Long.valueOf(saleId)+" and parent_id !="+Long.valueOf(mainId);
		else
			sql = "SELECT * FROM wood_purchase_sale_same_sub WHERE  wood_sale_contract_id="+Long.valueOf(saleId);
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseSaleSameSub.class);
		List<PurchaseSaleSameSub> purchaseSaleSameSubList = sqlQuery.list();
		return purchaseSaleSameSubList;
	}

	public List<PurchaseSaleSameSub> findByPurchaseId(String purchaseId,String mainId) {
		String sql="";
		if(mainId != null && !mainId.equals(""))
			sql = "SELECT * FROM wood_purchase_sale_same_sub WHERE  wood_cght_jk_id="+Long.valueOf(purchaseId)+" and parent_id !="+Long.valueOf(mainId);
		else
			sql = "SELECT * FROM wood_purchase_sale_same_sub WHERE  wood_cght_jk_id="+Long.valueOf(purchaseId);
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseSaleSameSub.class);
		List<PurchaseSaleSameSub> purchaseSaleSameSubList = sqlQuery.list();
		return purchaseSaleSameSubList;
	}
	
	@SuppressWarnings("unchecked")
	public List<PurchaseSaleSameSub> getBySaleId(Long id) {
		String sql = "SELECT * FROM wood_purchase_sale_same_sub WHERE  wood_sale_contract_id="+id;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseSaleSameSub.class);
		return sqlQuery.list();
	}

	public void updateCxState(WoodSaleContract saleContract) {
		String sql = "update wood_sale_contract set state_cx=1  WHERE  id="+saleContract.getId();
		getSession().createSQLQuery(sql).executeUpdate();
	}

	public void updateCxStateCancel(WoodSaleContract saleContract) {
		String sql = "update wood_sale_contract set state_cx=0  WHERE  id="+saleContract.getId();
		getSession().createSQLQuery(sql).executeUpdate();
	}

	public void updateCxStateCancel(WoodCghtJk c) {
		String sql = "update wood_cght_jk set state_cx=0  WHERE  id="+c.getId();
		getSession().createSQLQuery(sql).executeUpdate();
	}

	public void updateCxStateCg(WoodCghtJk woodCghtJk) {
		String sql = "update wood_cght_jk set state_cx=1  WHERE  id="+woodCghtJk.getId();
		getSession().createSQLQuery(sql).executeUpdate();
	}


	
} 
 