package com.cbmie.genMac.foreignTrade.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.foreignTrade.entity.Commodity;

/**
 * 进口商品DAO
 */
@Repository
public class CommodityDao extends HibernateDao<Commodity, Long> {

}
