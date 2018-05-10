package com.cbmie.genMac.baseinfo.dao;

import org.springframework.stereotype.Repository;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.genMac.baseinfo.entity.GoodsManage;

/**
 * 商品管理DAO
 */
@Repository
public class GoodsManageDao extends HibernateDao<GoodsManage, Long> {

}
