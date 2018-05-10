package com.cbmie.lh.baseInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.baseInfo.dao.GoodsTypeDao;
import com.cbmie.lh.baseInfo.entity.GoodsType;
@Service
public class GoodsTypeService extends BaseService<GoodsType, Long> {

	@Autowired
	private GoodsTypeDao typeDao;
	@Override
	public HibernateDao<GoodsType, Long> getEntityDao() {
		return typeDao;
	}
	
	public boolean checkCodeUique(GoodsType goodsType){
		return typeDao.checkCodeUique(goodsType);
	}
	
	public GoodsType getEntityByCode(String code){
		return typeDao.getEntityByCode(code);
	}

	public List<GoodsType> getEffectGoodsType() {
		return typeDao.findBy("status", 1);
	}

	public GoodsType findTypeName(GoodsType goodsType) {
		return typeDao.findTypeName(goodsType);
	}
}
