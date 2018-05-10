package com.cbmie.lh.baseInfo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.lh.baseInfo.entity.GoodsInfoIndexRel;
@Repository
public class GoodsInfoIndexRelDao extends HibernateDao<GoodsInfoIndexRel, Long> {

	public void getRelByInfo(Page page,Long infoId){
		String hql = "select rel from GoodsInfoIndexRel rel  left join fetch rel.goodsIndex index where rel.infoId=?0 ";
		Query query= createQuery(hql, infoId);
		page.setTotalCount(query.list().size());
		page.setResult(query.setFirstResult(page.getFirst()-1).setMaxResults(page.getPageSize()).list());
	}
	
	public List<Long> getIndexsByInfoId(Long infoId){
		String sql = "select a.index_id from LH_GOODSINFOINDEXREL a where a.info_Id=:infoId";
		List<Object> list = this.createSQLQuery(sql).setParameter("infoId", infoId).list();
		List<Long> result = new ArrayList<Long>();
		for(Object id : list){
			result.add(Long.parseLong(id.toString()));
		}
		return result;
	}
}
