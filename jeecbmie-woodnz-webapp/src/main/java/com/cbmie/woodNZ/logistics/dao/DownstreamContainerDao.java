package com.cbmie.woodNZ.logistics.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.logistics.entity.DownstreamContainer;

 
/**
 * 下游到单子表 - 箱单DAO 
 */ 
@Repository
public class DownstreamContainerDao extends HibernateDao<DownstreamContainer, Long>{

	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DownstreamContainer> getByParentId(String id) {
		String sql = "SELECT * FROM WOOD_DOWNSTREAM_CONTAINER WHERE  parent_id='"+id+"' ORDER BY box_no,goods_no";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(DownstreamContainer.class);
		return sqlQuery.list();
	}
	
	/**
	 * 批量删除箱单
	 * @param parent_id
	 */
	public void deleteByParentId(String id ){
		String sql = "delete from WOOD_DOWNSTREAM_CONTAINER  WHERE  parent_id="+id;
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(DownstreamContainer.class);
		sqlQuery.executeUpdate();
	}
	
} 
 