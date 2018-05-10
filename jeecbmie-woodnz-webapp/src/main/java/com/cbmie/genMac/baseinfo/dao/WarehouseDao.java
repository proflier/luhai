package com.cbmie.genMac.baseinfo.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.baseinfo.entity.Warehouse;

/**
 * 仓库DAO
 */
@Repository
public class WarehouseDao extends HibernateDao<Warehouse, Long>{

}
