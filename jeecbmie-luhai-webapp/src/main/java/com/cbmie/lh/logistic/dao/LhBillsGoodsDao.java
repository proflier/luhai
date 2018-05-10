package com.cbmie.lh.logistic.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.LhBillsGoods;
@Repository
public class LhBillsGoodsDao extends HibernateDao<LhBillsGoods, Long> {

	public void deleteEntityByParentId(String parentId){
		String sql = "delete from LhBillsGoods a where a.parentId=:parentId";
		this.createQuery(sql).setParameter("parentId", parentId).executeUpdate();
	}
}
