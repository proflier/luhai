package com.cbmie.lh.sale.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.lh.sale.entity.SaleSettlementGoods;
@Repository
public class SaleSettlementGoodsDao extends HibernateDao<SaleSettlementGoods, Long> {

	public List<SaleSettlementGoods> getSaleSettlementGoodsByPid(Long saleSettlementId){
		if(saleSettlementId == null){
			return null;
		}
		String hql = "from SaleSettlementGoods a where a.saleSettlementId=:saleSettlementId";
		List<SaleSettlementGoods> result = this.createQuery(hql).setParameter("saleSettlementId", saleSettlementId).list();
		return result;
	}
}
