package com.cbmie.lh.baseInfo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.baseInfo.entity.GoodsType;
@Repository
public class GoodsTypeDao extends HibernateDao<GoodsType, Long> {
	
	public boolean checkCodeUique(GoodsType goodsType){
		boolean result = true;
		String hql = "from GoodsType a where a.typeCode=:code ";
		if(goodsType.getId() != null){
			hql += " and a.id<>"+goodsType.getId();
		}
		List list = this.createQuery(hql).setParameter("code", goodsType.getTypeCode()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
	
	public GoodsType getEntityByCode(String code){
		String hql = "from GoodsType a where a.typeCode=:code ";
		List<GoodsType> list = this.createQuery(hql).setParameter("code", code).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public GoodsType findTypeName(GoodsType goodsType) {
		Criteria criteria = getSession().createCriteria(GoodsType.class);
		if (goodsType.getId() != null) {
			criteria.add(Restrictions.ne("id", goodsType.getId()));
		}
		criteria.add(Restrictions.eq("typeName", goodsType.getTypeName()));
		return (GoodsType) criteria.uniqueResult();
	}
}
