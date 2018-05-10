package com.cbmie.lh.baseInfo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.baseInfo.entity.GoodsInformation;
@Repository
public class GoodsInformationDao extends HibernateDao<GoodsInformation, Long> {

	@Autowired
	protected SessionFactory sessionFactory;
	
	public boolean checkCodeUique(GoodsInformation info){
		boolean result = true;
		String hql = "from GoodsInformation a where a.infoCode=:code ";
		if(info.getId() != null){
			hql += " and a.id<>"+info.getId();
		}
		List list = this.createQuery(hql).setParameter("code", info.getInfoCode()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}

	public GoodsInformation getEntityByCode(String code) {
		String hql = "from GoodsInformation a where a.infoCode=:code ";
		List<GoodsInformation> list = this.createQuery(hql).setParameter("code", code).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<GoodsInformation> getListByCode(String code) {
		String hql = "from GoodsInformation a where a.infoCode=:code  and status=1";
		List<GoodsInformation> list = this.createQuery(hql).setParameter("code", code).list();
		if(list!=null && list.size()>0){
			return list;
		}else{
			return null;
		}
	}

	public List<Map<String, String>> getGoodsInfoTree() {
		String sql =" select a.info_code as id, a.goods_type_code as pid,a.info_name as name from lh_goodsinformation a where a.status =1 UNION ALL "
				+ " select b.type_code as id, null as pid ,b.type_name as name from lh_goodstype b where b.status =1 ";
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for(Object obj : sqlQuery.list()) {
			Object[] obj_array = (Object[])obj;
			
			Map<String, String> row = new HashMap<String, String>();
			//code
			row.put("id", obj_array[0].toString());
			//pid
			row.put("pid", obj_array[1]!=null?obj_array[1].toString():null);
			//name
			row.put("name", obj_array[2].toString());
			data.add(row);
		}
		return data;
	}

	public GoodsInformation findByInfoName(GoodsInformation goodsInformation) {
		Criteria criteria = getSession().createCriteria(GoodsInformation.class);
		if (goodsInformation.getId() != null) {
			criteria.add(Restrictions.ne("id", goodsInformation.getId()));
		}
		criteria.add(Restrictions.eq("infoName", goodsInformation.getInfoName()));
		return (GoodsInformation) criteria.uniqueResult();
	}
}
