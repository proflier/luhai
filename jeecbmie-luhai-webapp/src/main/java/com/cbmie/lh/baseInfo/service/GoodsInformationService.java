package com.cbmie.lh.baseInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.baseInfo.dao.GoodsInformationDao;
import com.cbmie.lh.baseInfo.entity.GoodsInformation;
@Service
public class GoodsInformationService extends BaseService<GoodsInformation, Long> {

	@Autowired
	private GoodsInformationDao infoDao;
	
	@Override
	public HibernateDao<GoodsInformation, Long> getEntityDao() {
		return infoDao;
	}

	public boolean checkCodeUique(GoodsInformation goodsInfo){
		return infoDao.checkCodeUique(goodsInfo);
	}

	public GoodsInformation getEntityByCode(String code) {
		return infoDao.getEntityByCode(code);
	}
	
	public List<GoodsInformation> getListByCode(String code) {
		return infoDao.getListByCode(code);
	}

	public List<Map<String, String>> getGoodsInfoTree() {
		return infoDao.getGoodsInfoTree();
	}

	public GoodsInformation findByInfoName(GoodsInformation goodsInformation) {
		return infoDao.findByInfoName(goodsInformation);
	}
}
