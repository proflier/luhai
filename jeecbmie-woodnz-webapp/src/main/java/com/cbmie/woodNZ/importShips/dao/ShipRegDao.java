package com.cbmie.woodNZ.importShips.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.woodNZ.importShips.entity.ShipReg;
/**
 *  船舶信息登记
 * @author linxiaopeng
 * 2016年7月6日
 */
@Repository
public class ShipRegDao extends HibernateDao<ShipReg, Long>{
	
	public ShipReg findByNo(ShipReg shipReg) {
		Criteria criteria = getSession().createCriteria(ShipReg.class);
		if (shipReg.getId() != null) {
			criteria.add(Restrictions.ne("id", shipReg.getId()));
		}
		criteria.add(Restrictions.eq("shipNo", shipReg.getShipNo()));
		return (ShipReg)criteria.uniqueResult();
	}
	
}
