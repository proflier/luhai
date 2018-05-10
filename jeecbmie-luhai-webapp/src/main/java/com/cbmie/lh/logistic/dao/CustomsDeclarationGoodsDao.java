package com.cbmie.lh.logistic.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.CustomsDeclarationGoods;

@Repository
public class CustomsDeclarationGoodsDao extends HibernateDao<CustomsDeclarationGoods, Long> {

	@SuppressWarnings("unchecked")
	public List<CustomsDeclarationGoods> getByPid (Long pid) {
		String sql = "select * from LH_CUSTOMS_DECLARATION_GOODS where pid = :pid";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql).addEntity(CustomsDeclarationGoods.class);
		sqlQuery.setParameter("pid", pid);
		return sqlQuery.list();
	}
	
}
