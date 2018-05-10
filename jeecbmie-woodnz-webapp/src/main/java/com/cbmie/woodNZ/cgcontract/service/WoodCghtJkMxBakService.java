package com.cbmie.woodNZ.cgcontract.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmie.common.persistence.HibernateDao;
import com.cbmie.common.service.BaseService;
import com.cbmie.woodNZ.cgcontract.dao.WoodCghtJkMxBakDao;
import com.cbmie.woodNZ.cgcontract.entity.WoodCghtJkMxBak;

/**
 * 采购合同-进口-子表信息service
 */
@Service
@Transactional
public class WoodCghtJkMxBakService extends BaseService<WoodCghtJkMxBak, Long> {

	@Autowired
	private WoodCghtJkMxBakDao woodCghtJkMxBakDao;

	@Override
	public HibernateDao<WoodCghtJkMxBak, Long> getEntityDao() {
		return woodCghtJkMxBakDao;
	}
	
	public List<WoodCghtJkMxBak> findAll(Long id){
		List<WoodCghtJkMxBak> woodCghtJkMxBakList = woodCghtJkMxBakDao.findAll();
		List<WoodCghtJkMxBak> returnWoodCghtJkMxBakList = new ArrayList<WoodCghtJkMxBak>();
		if(woodCghtJkMxBakList.size()>0){
			Iterator<WoodCghtJkMxBak> it = woodCghtJkMxBakList.iterator();
			while (it.hasNext()) {
				WoodCghtJkMxBak woodCghtJkMxBak = (WoodCghtJkMxBak) it.next();
				if(woodCghtJkMxBak.getParentId()!=null&&String.valueOf(id).equals(woodCghtJkMxBak.getParentId())){
					returnWoodCghtJkMxBakList.add(woodCghtJkMxBak);
				}
			}
		}
		return returnWoodCghtJkMxBakList;
	}
	
	
}
