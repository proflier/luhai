package com.cbmie.lh.baseInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.baseInfo.dao.GoodsIndexDao;
import com.cbmie.lh.baseInfo.entity.GoodsIndex;
@Service
public class GoodsIndexService extends BaseService<GoodsIndex, Long> {

	@Autowired
	private GoodsIndexDao indexDao;
	
	@Override
	public HibernateDao<GoodsIndex, Long> getEntityDao() {
		return indexDao;
	}
	public boolean checkCodeUique(GoodsIndex goodsIndex){
		return indexDao.checkCodeUique(goodsIndex);
	}
	public List<GoodsIndex> getEffectGoodsIndex() {
		return indexDao.findBy("status", 1);
	}
	public GoodsIndex findByIndexName(GoodsIndex goodsIndex) {
		return indexDao.findByIndexName(goodsIndex);
	}


}
