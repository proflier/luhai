package com.cbmie.lh.baseInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.baseInfo.entity.GoodsType;
import com.cbmie.lh.baseInfo.entity.Signet;
@Repository
public class SignetDao extends HibernateDao<Signet, Long> {
	public boolean checkCodeUique(Signet signet){
		boolean result = true;
		String hql = "from Signet a where a.signetCode=:code ";
		if(signet.getId() != null){
			hql += " and a.id<>"+signet.getId();
		}
		List list = this.createQuery(hql).setParameter("code", signet.getSignetCode()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
	
	public Signet getEntityByCode(String code){
		String hql = "from Signet a where a.signetCode=:code ";
		List<Signet> list = this.createQuery(hql).setParameter("code", code).list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
