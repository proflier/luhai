package com.cbmie.woodNZ.salesContract.dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.salesContract.entity.WoodSaleContractSub;

 
/**
 * 销售子表DAO 
 */ 
@Repository
public class SaleContractSubDao extends HibernateDao<WoodSaleContractSub, Long>{

		@SuppressWarnings("unchecked")
		public List<WoodSaleContractSub> getdataListByParentId(Long id) {
			String sql = "SELECT * FROM wood_sale_contract_sub WHERE 1=1 and parent_id='"+id+"'";
			SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodSaleContractSub.class);
			return sqlQuery.list();
		}
	
} 
 