package com.cbmie.lh.purchase.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.purchase.entity.PurchaseContractGoods;

/**
 * 采购合同-子表DAO
 */
@Repository
public class PurchaseContractGoodsDao extends HibernateDao<PurchaseContractGoods, Long>{

	public List<PurchaseContractGoods> getByInnerContractNo(String innerContractNo) {
		String sql = " SELECT * FROM LH_PURCHASE_CONTRACT_GOODS a join lh_purchase_contract b ON a.purchase_contract_id= b.id "
                      +"where b.inner_contract_no ='"+innerContractNo+"' and state=1 AND b.state = 1 AND b.close_or_open=1 AND b.change_state =1 group by a.id ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(PurchaseContractGoods.class);
		return sqlQuery.list();
	}
	
}
