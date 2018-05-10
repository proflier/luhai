package com.cbmie.woodNZ.salesContract.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJk;
import com.cbmie.woodNZ.offerManagement.entity.ProflitLossAccounting;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSame;
import com.cbmie.woodNZ.salesContract.entity.PurchaseSaleSameSub;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContract;

 
/**
 * 采销同批主表DAO 
 */ 
@Repository
public class PurchaseSaleSameDao extends HibernateDao<PurchaseSaleSame, Long>{
	
	@SuppressWarnings("unchecked")
	public List<ProflitLossAccounting> findProflitLossByContractNO(String hth) {
			String sql = "SELECT * FROM WOOD_Proflit_Loss_Accounting WHERE 1=1 and contract_no='"+hth+"'";
			SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(ProflitLossAccounting.class);
			return sqlQuery.list();
	}

	public List<WoodCghtJk> getAllPurchaseList(Long mainId, String hth) {
		String sql="";
		String sql2="";
		if(hth!=null && !hth.equals("")){
			if(mainId != null && mainId!= 0){
				sql = "select * from wood_cght_jk where hth= '"+hth+"' and state='生效' and close_or_open='运行中' and htlb='147' and "
						+ "id not in (select wood_cght_jk_id FROM wood_purchase_sale_same_sub)"
					     +" UNION "
					      +"select * from wood_cght_jk  where hth= '"+hth+"' and id in (select wood_cght_jk_id FROM wood_purchase_sale_same_sub where parent_id='"+mainId+"')";
			}else{
				sql = "select * from wood_cght_jk where hth= '"+hth+"'and  state='生效' and close_or_open='运行中' and htlb='147' and "
						+ "id not in (select wood_cght_jk_id FROM wood_purchase_sale_same_sub)";
			}
			sql2="select * from wood_cght_jk where hth= '"+hth+"' and state='生效' and close_or_open='运行中' and htlb='147' and "
					+ "id in (select wood_cght_jk_id from wood_purchase_sale_same_sub where parent_id in "
					 + "(select id from wood_purchase_sale_same where state='作废'))";
		}
		else if(hth == null || hth.equals("")){
			if(mainId != null && mainId!= 0){
				sql = "select * from wood_cght_jk where state='生效' and close_or_open='运行中' and htlb='147' and "
						+ "id not in (select wood_cght_jk_id FROM wood_purchase_sale_same_sub)"
					     +" UNION "
					      +"select * from wood_cght_jk  where id in (select wood_cght_jk_id FROM wood_purchase_sale_same_sub where parent_id='"+mainId+"')";
			}else{
				sql = "select * from wood_cght_jk where state='生效' and close_or_open='运行中' and htlb='147' and "
						+ "id not in (select wood_cght_jk_id FROM wood_purchase_sale_same_sub)";
			}
			sql2="select * from wood_cght_jk where state='生效' and close_or_open='运行中' and htlb='147' and "
					+ "id in (select wood_cght_jk_id from wood_purchase_sale_same_sub where parent_id in "
					 + "(select id from wood_purchase_sale_same where state='作废'))";
		}
			
		SQLQuery sqlQuery1 = getSession().createSQLQuery(sql).addEntity(WoodCghtJk.class);
		SQLQuery sqlQuery2 = getSession().createSQLQuery(sql2).addEntity(WoodCghtJk.class);
		List<WoodCghtJk> result = new ArrayList<WoodCghtJk>();
		List<WoodCghtJk> list1 = sqlQuery1.list();
		List<WoodCghtJk> list2 = sqlQuery2.list();
		result.addAll(list1);
		result.addAll(list2);
		return result;
	}
	
	public List<WoodSaleContract> getAllSaleList(Long mainId, String hth) {
		String sql="";
		String sql2="";
		if(hth!=null && !hth.equals("")){
			if(mainId != null && mainId!= 0){
				sql = "select * from wood_sale_contract where contract_no='"+hth+"' and state='生效' and state_ht='运行中' and trade_property='147' and "
						+ "id not in (select wood_sale_contract_id FROM wood_purchase_sale_same_sub)"
					     +" UNION "
					      +"select * from wood_sale_contract  where contract_no='"+hth+"' and id in (select wood_sale_contract_id FROM wood_purchase_sale_same_sub where parent_id='"+mainId+"')";
			}else{
				sql = "select * from wood_sale_contract where contract_no='"+hth+"' and state='生效' and state_ht='运行中' and trade_property='147' and  "
						+ "id not in (select wood_sale_contract_id FROM wood_purchase_sale_same_sub)";
			}
				sql2="select * from wood_sale_contract where contract_no='"+hth+"' and state='生效' and state_ht='运行中' and trade_property='147' and "
					+ "id in (select wood_sale_contract_id from wood_purchase_sale_same_sub where parent_id in "
					 + "(select id from wood_purchase_sale_same where state='作废'))";
		}
		else if(hth == null || hth.equals("")){
			if(mainId != null && mainId!= 0){
				sql = "select * from wood_sale_contract where state='生效' and state_ht='运行中' and trade_property='147' and "
						+ "id not in (select wood_sale_contract_id FROM wood_purchase_sale_same_sub)"
					     +" UNION "
					      +"select * from wood_sale_contract  where id in (select wood_sale_contract_id FROM wood_purchase_sale_same_sub where parent_id='"+mainId+"')";
			}else{
				sql = "select * from wood_sale_contract where state='生效' and state_ht='运行中' and trade_property='147' and  "
						+ "id not in (select wood_sale_contract_id FROM wood_purchase_sale_same_sub where parent_id in )"
						 + "(select id from wood_purchase_sale_same where state !='作废'))";
			}
				sql2="select * from wood_sale_contract where state='生效' and state_ht='运行中' and trade_property='147' and "
					+ "id in (select wood_sale_contract_id from wood_purchase_sale_same_sub where parent_id in "
					 + "(select id from wood_purchase_sale_same where state='作废'))";
		}
		SQLQuery sqlQuery1 = getSession().createSQLQuery(sql).addEntity(WoodSaleContract.class);
		SQLQuery sqlQuery2 = getSession().createSQLQuery(sql2).addEntity(WoodSaleContract.class);
		List<WoodSaleContract> result = new ArrayList<WoodSaleContract>();
		List<WoodSaleContract> list1 = sqlQuery1.list();
		List<WoodSaleContract> list2 = sqlQuery2.list();
//		if(list1){
//			
//		}
		result.addAll(list1);
		result.addAll(list2);
		return result;
	}

	public List<WoodSaleContract> getCount4Cancel() {
		String sql="select * from wood_sale_contract where state='生效' and state_ht='运行中' and trade_property='147' and state_cx='1' and "
					+ "id in (select wood_sale_contract_id from wood_purchase_sale_same_sub where parent_id in "
					 + "(select id from wood_purchase_sale_same where state='作废'))";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodSaleContract.class);
		return sqlQuery.list();
	}

	public List<PurchaseSaleSameSub> judge4Cancel(Long id) {
		String sql="select * from wood_purchase_sale_same_sub where wood_sale_contract_id ='"+id+"' and parent_id in "
				 + "(select id from wood_purchase_sale_same where state!='作废')";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseSaleSameSub.class);
		return sqlQuery.list();
	}

	public List<WoodCghtJk> getCount4CancelCg() {
		String sql="select * from wood_cght_jk where state='生效' and close_or_open='运行中' and htlb='147' and state_cx='1' and "
				+ "id in (select wood_cght_jk_id from wood_purchase_sale_same_sub where parent_id in "
				 + "(select id from wood_purchase_sale_same where state='作废'))";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodCghtJk.class);
		return sqlQuery.list();
	}

	public List<PurchaseSaleSameSub> judge4CancelCg(Long id) {
		String sql="select * from wood_purchase_sale_same_sub where wood_cght_jk_id ='"+id+"' and parent_id in "
				 + "(select id from wood_purchase_sale_same where state!='作废')";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseSaleSameSub.class);
		return sqlQuery.list();
	}
	
} 
 