package com.cbmie.lh.baseInfo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.baseInfo.entity.GoodsIndex;
@Repository
public class GoodsIndexDao extends HibernateDao<GoodsIndex, Long> {
	
	public boolean checkCodeUique(GoodsIndex goodsIndex){
		boolean result = true;
		String hql = "from GoodsIndex a where a.indexCode=:code ";
		if(goodsIndex.getId() != null){
			hql += " and a.id<>"+goodsIndex.getId();
		}
		List list = this.createQuery(hql).setParameter("code", goodsIndex.getIndexCode()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}

	public GoodsIndex findByIndexName(GoodsIndex goodsIndex) {
		Criteria criteria = getSession().createCriteria(GoodsIndex.class);
		if (goodsIndex.getId() != null) {
			criteria.add(Restrictions.ne("id", goodsIndex.getId()));
		}
		criteria.add(Restrictions.eq("indexName", goodsIndex.getIndexName()));
		return (GoodsIndex) criteria.uniqueResult();
	}
}
