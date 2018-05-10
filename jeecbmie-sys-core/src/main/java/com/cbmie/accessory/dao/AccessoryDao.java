package com.cbmie.accessory.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.accessory.entity.Accessory;
import com.cbmie.common.persistence.HibernateDao;

/**
 * 附件DAO
 */
@Repository
public class AccessoryDao extends HibernateDao<Accessory, Long>{

	@SuppressWarnings("unchecked")
	public List<Accessory> getListByPidAndEntity(String accParentId,String accParentEntity){
		String sql = "from Accessory a where a.accParentId=:accParentId and a.accParentEntity=:accParentEntity order by a.id asc";
		return this.createQuery(sql).setParameter("accParentId", accParentId).setParameter("accParentEntity", accParentEntity).list();
	}
}
