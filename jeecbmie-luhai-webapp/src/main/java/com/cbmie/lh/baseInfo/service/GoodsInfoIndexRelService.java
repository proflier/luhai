package com.cbmie.lh.baseInfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.persistence.Page;
import com.cbmie.common.service.BaseService;
import com.cbmie.lh.baseInfo.dao.GoodsInfoIndexRelDao;
import com.cbmie.lh.baseInfo.entity.GoodsInfoIndexRel;
@Service
public class GoodsInfoIndexRelService extends BaseService<GoodsInfoIndexRel, Long> {

	@Autowired
	private GoodsInfoIndexRelDao relDao;
	@Override
	public HibernateDao<GoodsInfoIndexRel, Long> getEntityDao() {
		return relDao;
	}

	public void getRelByInfo(Page page,Long infoId){
		relDao.getRelByInfo(page, infoId);
	}
	
	public List<Long> getIndexsByInfoId(Long infoId){
		return relDao.getIndexsByInfoId(infoId);
	}
}
