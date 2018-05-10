package com.cbmie.woodNZ.logistics.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.logistics.entity.WoodBillsSub;

 
/**
 * 到单子表 - 箱单DAO 
 */ 
@Repository
public class BillsSubDao extends HibernateDao<WoodBillsSub, Long>{

	/**
	 * @param id
	 * @return
	 */
	public List<WoodBillsSub> getByParentId(Long id) {
		String sql = "SELECT * FROM wood_bills_sub WHERE  parent_id='"+String.valueOf(id)+"' ORDER BY box_no,goods_no";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(WoodBillsSub.class);
		return sqlQuery.list();
	}
	
} 
 