package com.cbmie.woodNZ.logistics.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.logistics.entity.WoodBills;

 
/**
 * 到单DAO 
 */ 
@Repository
public class BillsDao extends HibernateDao<WoodBills, Long>{

	public boolean validateBillNo(String billNo, Long id) {
		Criteria criteria = getSession().createCriteria(WoodBills.class);
		if (id != null) {
			criteria.add(Restrictions.ne("id", id));
		}
		criteria.add(Restrictions.eq("billNo", billNo));
		List list = criteria.list();
		if(list!=null && list.size()>0){//当前提单号和其他提单号重复
			return true;//提单号重复
		}
		return false;//提单号不重复
	}
} 
 