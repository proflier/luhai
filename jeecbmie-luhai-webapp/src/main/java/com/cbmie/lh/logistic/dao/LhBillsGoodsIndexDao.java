package com.cbmie.lh.logistic.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.LhBillsGoodsIndex;
@Repository
public class LhBillsGoodsIndexDao extends HibernateDao<LhBillsGoodsIndex, Long> {

	public void deleteEntityByParentId(String parentId){
		String sql = "delete from LhBillsGoodsIndex a where a.parentId=:parentId";
		this.createQuery(sql).setParameter("parentId", parentId).executeUpdate();
	}
}
