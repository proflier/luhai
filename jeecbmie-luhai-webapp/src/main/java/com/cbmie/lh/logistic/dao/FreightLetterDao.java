package com.cbmie.lh.logistic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.logistic.entity.FreightLetter;
@Repository
public class FreightLetterDao extends HibernateDao<FreightLetter, Long> {
	public boolean checkCodeUique(FreightLetter letter){
		boolean result = true;
		String hql = "from FreightLetter a where a.letterNo=:no ";
		if(letter.getId() != null){
			hql += " and a.id<>"+letter.getId();
		}
		@SuppressWarnings("rawtypes")
		List list = this.createQuery(hql).setParameter("no", letter.getLetterNo()).list();
		if(list!=null && list.size()>0){
			result = false;
		}
		return result;
	}
}